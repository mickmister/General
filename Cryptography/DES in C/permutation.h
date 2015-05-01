/*
	Michael Kochell

	These permutations would probably be faster if they were all inlined, but the code would be huge and messy.
	This is one spot I caved in favor of elegance. Hopefully it's too much of a drawback efficiency-wise.
*/

/*
	used to permutate a 64-bit number, outputs a 64-bit number
*/
unsigned long long performLongToLongPermutation(unsigned long long input, const int* pattern, const int patternSize);
 
/*
	used to permutate a 32-bit number, outputs a 64-bit number
*/
unsigned long long performIntToLongPermutation(unsigned int input, const int* pattern, const int patternSize);

/*
	used to permutate a 32-bit number, outputs a 32-bit number
*/
unsigned int performIntToIntPermutation(unsigned int input, const int* pattern, const int patternSize);

/*
	Most of the permutation numbers have been changed in these ways:
	1. values were decremented by one to become zero-indexed
	2. reversed and inversed (subtract value from highest number of perm)
		to work with the least significant bit of the binary string first

	These changes were made in an attempt to minimize index and shifting calculations
*/

/*
	The initial permutation for the original key
*/
static const int keyPermInitial[56] = 
{
	60,	52,	44,	36,	59,	51,	43,	35,	27,	19,	11,	3,	58,	50,
	42,	34,	26,	18,	10,	2,	57,	49,	41,	33,	25,	17,	9,	1,
	28,	20,	12,	4,	61,	53,	45,	37,	29,	21,	13,	5,	62,	54,
	46,	38,	30,	22,	14,	6,	63,	55,	47,	39,	31,	23,	15,	7
}; 
static const int keyPermInitialSize = 56;

/*
	The final permutation for each round key
*/
static const int keyPermFinal[48] =
{
	24,	27,	20,	6,	14,	10,	3,	22,	0,	17,	7,	12,
	8,	23,	11,	5,	16,	26,	1,	9,	19,	25,	4,	15,
	54,	43,	36,	29,	49,	40,	48,	30,	52,	44,	37,	33,
	46,	35,	50,	41,	28,	53,	51,	55,	32,	45,	39,	42
};
static const int keyPermFinalSize = 48;

/*
	The initial permutation for the original message
*/
static int initialPerm[64] = 
{
	57,	49,	41,	33,	25,	17,	9,	1,	59,	51,	43,	35,	27,	19,	11,	3,
	61,	53,	45,	37,	29,	21,	13,	5,	63,	55,	47,	39,	31,	23,	15,	7,
	56,	48,	40,	32,	24,	16,	8,	0,	58,	50,	42,	34,	26,	18,	10,	2,
	60,	52,	44,	36,	28,	20,	12,	4,	62,	54,	46,	38,	30,	22,	14,	6
};
static const int initialPermSize = 64;

/*
	The inverse of the initial permutation
*/
static const int initialPermInv[64] = 
{
	39, 7, 47, 15, 55, 23, 63, 31, 38, 6, 46, 14, 54, 22, 62, 30,
	37, 5, 45, 13, 53, 21, 61, 29, 36, 4, 44, 12, 52, 20, 60, 28,
	35, 3, 43, 11, 51, 19, 59, 27, 34, 2, 42, 10, 50, 18, 58, 26,
	33, 1, 41,  9, 49, 17, 57, 25, 32, 0, 40,  8, 48, 16, 56, 24 
};

/*
	The permutation for expanding the right side at the beginning of the main key function
*/
static const int expandRight[48] = 
{
	31,	0,	1,	2,	3,	4,	3,	4,	5,	6,	7,	8,
	7,	8,	9,	10,	11,	12,	11,	12,	13,	14,	15,	16,
	15,	16,	17,	18,	19,	20,	19,	20,	21,	22,	23,	24,
	23,	24,	25,	26,	27,	28,	27,	28,	29,	30,	31,	0
};
static const expandRightSize = 48;

/*
	The final permutation for C at the end of a sbox calculation
*/
static const int finalSboxPerm[32] = 
{
	7,	28,	21,	10,	26,	2,	19,	13,	23,	29,	5,	0,	18,	8,	24,	30,
	22,	1,	14,	27,	6,	9,	17,	31,	15,	4,	20,	3,	11,	12,	25,	16
};
static const int finalSboxPermSize = 32;