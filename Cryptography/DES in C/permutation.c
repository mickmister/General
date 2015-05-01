/*
	Michael Kochell
*/

#include "permutation.h"
 
/*
	used to permutate a 64-bit number, outputs a 64-bit number
*/
unsigned long long performLongToLongPermutation(unsigned long long input, const int* pattern, const int patternSize)
{
	int index;
	unsigned long long currentBit, result = 0;	

	// iterate over chosen permutation template
	for(index=0; index<patternSize; index++)
	{
		// extract target bit from input
		currentBit = (input >> pattern[index]) & 0x1;

		// accumulate result
		result = result | (currentBit << (index));
	}

	return result;
}

/*
	used to permutate a 32-bit number, outputs a 64-bit number
*/
unsigned long long performIntToLongPermutation(unsigned int input, const int* pattern, const int patternSize)
{
	int index;
	unsigned long long currentBit, result = 0;	

	// iterate over chosen permutation template
	for(index=0; index<patternSize; index++)
	{
		// extract target bit from input
		currentBit = (input >> pattern[index]) & 0x1;

		// accumulate result
		result = result | (currentBit << (index));
	}

	return result;
}

/*
	used to permutate a 32-bit number, outputs a 32-bit number
*/
unsigned int performIntToIntPermutation(unsigned int input, const int* pattern, const int patternSize)
{
	int index;
	unsigned int currentBit, result = 0;	

	// iterate over chosen permutation template
	for(index=0; index<patternSize; index++)
	{
		// extract target bit from input
		currentBit = (input >> pattern[index]) & 0x1;

		// accumulate result
		result = result | (currentBit << (index));
	}

	return result;
}