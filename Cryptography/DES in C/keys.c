/*
	Michael Kochell

	All round keys are calculated here
*/

#include "permutation.h"
#include "keys.h"
#include <stdio.h>


static const unsigned int mask = 0X0FFFFFFF; // used to mask keys (28 bits)

/*
	gets all round keys and stores them in keyArray

	takes the original key, the keyArray, and the number of rounds
*/
void obtainRoundKeys(unsigned long long key, unsigned long long* keyArray, int numRounds)
{
	int currentRound;
	unsigned long long roundKey;
 
  	// perform initial permutation for original key
	unsigned long long initialKeyPerm = performLongToLongPermutation(key, keyPermInitial, keyPermInitialSize);


  	// prepare left and right bits
	unsigned long long leftBits = initialKeyPerm >> 28;
	unsigned long long rightBits = initialKeyPerm & mask;

	unsigned long long c, d;

	// find keys for each round
	for(currentRound=0; currentRound<numRounds; currentRound++)
	{
		// do initial shifts for round key
		c = shiftKeyLeft(leftBits, leftShiftsPerRound[currentRound]);
		d = shiftKeyLeft(rightBits, leftShiftsPerRound[currentRound]);

		// combine left and right side
		roundKey = (c << 28) | d;

		// get final permutation for round key
		roundKey = performLongToLongPermutation(roundKey, keyPermFinal, keyPermFinalSize);

		// store round key
		keyArray[currentRound] = roundKey;
	}
}

/*
	"wraparound" left shift
*/
static const int inputSize = 28;
unsigned long long shiftKeyLeft(unsigned long long toShift, int numShifts)
{
  unsigned long long result;

  // collect bits that wrap around
  result = toShift >> (inputSize - numShifts);

  // collect bits that do not wrap around
  result = result | ((toShift << numShifts) & mask);

  return result;
}