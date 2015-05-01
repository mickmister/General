/*
	Michael Kochell
*/


/*
	gets all round keys and stores them in keyArray

	takes the original key, the keyArray, and the number of rounds
*/
void obtainRoundKeys(unsigned long long key, unsigned long long* keyArray, int numRounds);

/*
	"wraparound" left shift
*/
unsigned long long shiftKeyLeft(unsigned long long toShift, int numShifts); 

/*
	used for finding each round key

	altered make shift calculations static
*/
static const int leftShiftsPerRound[16] ={1, 2, 4, 6, 8, 10, 12, 14, 15, 17, 19, 21, 23, 25, 27, 0};