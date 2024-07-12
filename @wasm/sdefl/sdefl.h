#ifndef SDEFL_H_INCLUDED
#define SDEFL_H_INCLUDED

#define SDEFL_MAX_OFF       (1 << 15)
#define SDEFL_WIN_SIZ       SDEFL_MAX_OFF
#define SDEFL_WIN_MSK       (SDEFL_WIN_SIZ-1)

#define SDEFL_MIN_MATCH     4
#define SDEFL_MAX_MATCH     258

#define SDEFL_HASH_BITS     19
#define SDEFL_HASH_SIZ      (1 << SDEFL_HASH_BITS)
#define SDEFL_HASH_MSK      (SDEFL_HASH_SIZ-1)
#define SDEFL_NIL           (-1)

#define SDEFL_LVL_MIN       0
#define SDEFL_LVL_DEF       5
#define SDEFL_LVL_MAX       8

struct sdefl {
    int bits, cnt;
    int tbl[SDEFL_HASH_SIZ];
    int prv[SDEFL_WIN_SIZ];
};
extern int sdefl_bound(int in_len);
extern int sdeflate(struct sdefl *s, unsigned char *out, const unsigned char *in, int in_len, int lvl);

#endif /* SDEFL_H_INCLUDED */
