#include "sdefl.h"

#include <string.h> /* memcpy */

static const unsigned char sdefl_mirror[256] = {
    #define R2(n) n, n + 128, n + 64, n + 192
    #define R4(n) R2(n), R2(n + 32), R2(n + 16), R2(n + 48)
    #define R6(n) R4(n), R4(n +  8), R4(n +  4), R4(n + 12)
    R6(0), R6(2), R6(1), R6(3),
};
static int
sdefl_npow2(int n)
{
    n--;
    n |= n >> 1;
    n |= n >> 2;
    n |= n >> 4;
    n |= n >> 8;
    n |= n >> 16;
    return (int)++n;
}
static int
sdefl_ilog2(int n)
{
    #define lt(n) n,n,n,n, n,n,n,n, n,n,n,n ,n,n,n,n
    static const char tbl[256] = {-1,0,1,1,2,2,2,2,3,3,3,3,
        3,3,3,3,lt(4),lt(5),lt(5),lt(6),lt(6),lt(6),lt(6),
        lt(7),lt(7),lt(7),lt(7),lt(7),lt(7),lt(7),lt(7)
    }; int tt, t;
    if ((tt = (n >> 16)))
        return (t = (tt >> 8)) ? 24+tbl[t]: 16+tbl[tt];
    else return (t = (n >> 8)) ? 8+tbl[t]: tbl[n];
    #undef lt
}
static unsigned
sdefl_uload32(const void *p)
{
    /* hopefully will be optimized to an unaligned read */
    unsigned int n = 0;
    memcpy(&n, p, sizeof(n));
    return n;
}
static unsigned
sdefl_hash32(const void *p)
{
    unsigned n = sdefl_uload32(p);
    return (n*0x9E377989)>>(32-SDEFL_HASH_BITS);
}
static unsigned char*
sdefl_put(unsigned char *dst, struct sdefl *s, int code, int bitcnt)
{
    s->bits |= (code << s->cnt);
    s->cnt += bitcnt;
    while (s->cnt >= 8) {
        *dst++ = (unsigned char)(s->bits & 0xFF);
        s->bits >>= 8;
        s->cnt -= 8;
    } return dst;
}
static unsigned char*
sdefl_match(unsigned char *dst, struct sdefl *s, int dist, int len)
{
    static const short lxmin[] = {0,11,19,35,67,131};
    static const short dxmax[] = {0,6,12,24,48,96,192,384,768,1536,3072,6144,12288,24576};
    static const short lmin[] = {11,13,15,17,19,23,27,31,35,43,51,59,67,83,99,115,131,163,195,227};
    static const short dmin[] = {1,2,3,4,5,7,9,13,17,25,33,49,65,97,129,193,257,
        385,513,769,1025,1537,2049,3073,4097,6145,8193,12289,16385,24577};

    /* length encoding */
    int lc = len;
    int lx = sdefl_ilog2(len - 3) - 2;
    if (!(lx = (lx < 0) ? 0: lx)) lc += 254;
    else if (len >= 258) lx = 0, lc = 285;
    else lc = ((lx-1) << 2) + 265 + ((len - lxmin[lx]) >> lx);

    if (lc <= 279)
        dst = sdefl_put(dst, s, sdefl_mirror[(lc - 256) << 1], 7);
    else dst = sdefl_put(dst, s, sdefl_mirror[0xc0 - 280 + lc], 8);
    if (lx) dst = sdefl_put(dst, s, len - lmin[lc - 265], lx);

    /* distance encoding */
    {int dc = dist - 1;
    int dx = sdefl_ilog2(sdefl_npow2(dist) >> 2);
    if ((dx = (dx < 0) ? 0: dx))
        dc = ((dx + 1) << 1) + (dist > dxmax[dx]);
    dst = sdefl_put(dst, s, sdefl_mirror[dc << 3], 5);
    if (dx) dst = sdefl_put(dst, s, dist - dmin[dc], dx);}
    return dst;
}
static unsigned char*
sdefl_lit(unsigned char *dst, struct sdefl *s, int c)
{
    if (c <= 143)
        return sdefl_put(dst, s, sdefl_mirror[0x30+c], 8);
    else return sdefl_put(dst, s, 1 + 2 * sdefl_mirror[0x90 - 144 + c], 9);
}
extern int
sdeflate(struct sdefl *s, unsigned char *out,
    const unsigned char *in, int in_len, int lvl)
{
    int p = 0;
    int max_chain = (lvl < 8) ? (1<<(lvl+1)): (1<<13);
    unsigned char *q = out;

    s->bits = s->cnt = 0;
    for (p = 0; p < SDEFL_HASH_SIZ; ++p)
        s->tbl[p] = SDEFL_NIL;

    p = 0;
    q = sdefl_put(q, s, 0x01, 1); /* block */
    q = sdefl_put(q, s, 0x01, 2); /* static huffman */
    while (p < in_len) {
        int run, best_len = 0, dist = 0;
        int max_match = ((in_len-p)>SDEFL_MAX_MATCH) ? SDEFL_MAX_MATCH:(in_len-p);
        if (max_match > SDEFL_MIN_MATCH) {
            int limit = ((p-SDEFL_WIN_SIZ)<SDEFL_NIL)?SDEFL_NIL:(p-SDEFL_WIN_SIZ);
            int chain_len = max_chain;
            int i = s->tbl[sdefl_hash32(&in[p])];
            while (i > limit) {
                if (in[i+best_len] == in[p+best_len] &&
                    (sdefl_uload32(&in[i]) == sdefl_uload32(&in[p]))){
                    int n = SDEFL_MIN_MATCH;
                    while (n < max_match && in[i+n] == in[p+n]) n++;
                    if (n > best_len) {
                        best_len = n;
                        dist = p - i;
                        if (n == max_match)
                            break;
                    }
                }
                if (!(--chain_len)) break;
                i = s->prv[i&SDEFL_WIN_MSK];
            }
        }
        if (lvl >= 5 && best_len >= SDEFL_MIN_MATCH && best_len < max_match){
            const int x = p + 1;
            int tar_len = best_len + 1;
            int limit = ((x-SDEFL_WIN_SIZ)<SDEFL_NIL)?SDEFL_NIL:(x-SDEFL_WIN_SIZ);
            int chain_len = max_chain;
            int i = s->tbl[sdefl_hash32(&in[p])];
            while (i > limit) {
                if (in[i+best_len] == in[x+best_len] &&
                    (sdefl_uload32(&in[i]) == sdefl_uload32(&in[x]))){
                    int n = SDEFL_MIN_MATCH;
                    while (n < tar_len && in[i+n] == in[x+n]) n++;
                    if (n == tar_len) {
                        best_len = 0;
                        break;
                    }
                }
                if (!(--chain_len)) break;
                i = s->prv[i&SDEFL_WIN_MSK];
            }
        }
        if (best_len >= SDEFL_MIN_MATCH) {
            q = sdefl_match(q, s, dist, best_len);
            run = best_len;
        } else {
            q = sdefl_lit(q, s, in[p]);
            run = 1;
        }
        while (run-- != 0) {
            unsigned h = sdefl_hash32(&in[p]);
            s->prv[p&SDEFL_WIN_MSK] = s->tbl[h];
            s->tbl[h] = p++;
        }
    }
    /* zlib partial flush */
    q = sdefl_put(q, s, 0, 7);
    q = sdefl_put(q, s, 2, 10);
    q = sdefl_put(q, s, 2, 3);
    return (int)(q - out);
}
extern int
sdefl_bound(int len)
{
    int a = 128 + (len * 110) / 100;
    int b = 128 + len + ((len / (31 * 1024)) + 1) * 5;
    return (a > b) ? a : b;
}

