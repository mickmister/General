/*
	Michael Kochell
*/

#include <stdio.h>
#include "sbox.h"
#include "permutation.h"

/*
	All sbox calculations are done here, starting with the 48 bit input
*/
unsigned int performSboxCalculation(unsigned long long input)
{
	
	int dataChunkIndex, sboxIndex, row, column, offset; 
	unsigned int sboxResult;
	unsigned int chunk, sboxResultShifted, sboxTotal = 0;

	// loop iterates least significant bits first, so starts with sbox8
	for(dataChunkIndex = 0; dataChunkIndex < NUM_SBOXES; dataChunkIndex++)
	{
		//calculate current sbox
		sboxIndex = NUM_SBOXES - dataChunkIndex - 1;

		//get chunk of 6 bits we're working with
		offset = dataChunkIndex * SBOX_INPUT_SIZE;
		chunk = input >> offset;

		//extract row and column of sbox
		row = ((chunk >> 4) & 0x2) | (chunk & 0x1);
		column = (chunk & 0x1E) >> 1;

		//retrieve sbox result
		sboxResult = sboxes[sboxIndex][row][column];
		
		//accumulate sbox calculations
		sboxTotal = sboxTotal | sboxResult;
	}

	// perform final sbox permutation
	return performIntToIntPermutation(sboxTotal, finalSboxPerm, finalSboxPermSize);
}