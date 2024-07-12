#ifndef SINFL_H_INCLUDED
#define SINFL_H_INCLUDED

struct sinfl {
    int bits, bitcnt;
    unsigned lits[288];
    unsigned dsts[32];
    unsigned lens[19];
    int tlit, tdist, tlen;
};
extern int sinflate(unsigned char *out, const unsigned char *in, int size);

#endif /* SINFL_H_INCLUDED */

