package Main;

public class SBox 
{
	final static int sBoxInputSize = 6;
	final static int numberOfSBoxes = 8;
	
	public static String performTotalSBoxCalculation(String input)
	{
		StringBuilder builder = new StringBuilder();
				
		for(int sboxIndex = 0; sboxIndex < numberOfSBoxes; sboxIndex++)
		{
			String currentSBoxInput = input.substring(sboxIndex * sBoxInputSize, (sboxIndex + 1) * sBoxInputSize);
			String currentSBoxResult = performSBoxCalculation(currentSBoxInput, sboxIndex);
			builder.append(currentSBoxResult);
		}
		
		String toPermutate = builder.toString();
		
		return Permutations.performSBoxFinalPermutation(toPermutate);
	}

	private static String performSBoxCalculation(String input, int boxIndex)
	{
		int row = Integer.parseInt(input.charAt(0) + "" +  input.charAt(5), 2);
		int column = Integer.parseInt(input.substring(1, 5), 2);
		return sboxes[boxIndex][row][column];
	}
	
	
	static String[][][] sboxes = new String[][][]
	{
		//sbox1
		{
			// each of these 4 entries is one row, each consisting of 16 columns
			{
				"1110",	"0100",	"1101",	"0001",	"0010",	"1111",	"1011",	"1000",	"0011",	"1010",	"0110",	"1100",	"0101",	"1001",	"0000",	"0111"
			},
			{
				"0000",	"1111",	"0111",	"0100",	"1110",	"0010",	"1101",	"0001",	"1010",	"0110",	"1100",	"1011",	"1001",	"0101",	"0011",	"1000"
			},
			{
				"0100",	"0001",	"1110",	"1000",	"1101",	"0110",	"0010",	"1011",	"1111",	"1100",	"1001",	"0111",	"0011",	"1010",	"0101",	"0000"
			},
			{
				"1111",	"1100",	"1000",	"0010",	"0100",	"1001",	"0001",	"0111",	"0101",	"1011",	"0011",	"1110",	"1010",	"0000",	"0110",	"1101"
			}
		},
		
		//sbox2
		{
			{
				"1111",	"0001",	"1000",	"1110",	"0110",	"1011",	"0011",	"0100",	"1001",	"0111",	"0010",	"1101",	"1100",	"0000",	"0101",	"1010"
			},
			{
				"0011",	"1101",	"0100",	"0111",	"1111",	"0010",	"1000",	"1110",	"1100",	"0000",	"0001",	"1010",	"0110",	"1001",	"1011",	"0101"
			},
			{
				"0000",	"1110",	"0111",	"1011",	"1010",	"0100",	"1101",	"0001",	"0101",	"1000",	"1100",	"0110",	"1001",	"0011",	"0010",	"1111"
			},
			{
				"1101",	"1000",	"1010",	"0001",	"0011",	"1111",	"0100",	"0010",	"1011",	"0110",	"0111",	"1100",	"0000",	"0101",	"1110",	"1001"
			}
		},
		
		//sbox 3
		{
			{
				"1010",	"0000",	"1001",	"1110",	"0110",	"0011",	"1111",	"0101",	"0001",	"1101",	"1100",	"0111",	"1011",	"0100",	"0010",	"1000"
			},
			{
				"1101",	"0111",	"0000",	"1001",	"0011",	"0100",	"0110",	"1010",	"0010",	"1000",	"0101",	"1110",	"1100",	"1011",	"1111",	"0001"
			},
			{
				"1101",	"0110",	"0100",	"1001",	"1000",	"1111",	"0011",	"0000",	"1011",	"0001",	"0010",	"1100",	"0101",	"1010",	"1110",	"0111"
			},
			{
				"0001",	"1010",	"1101",	"0000",	"0110",	"1001",	"1000",	"0111",	"0100",	"1111",	"1110",	"0011",	"1011",	"0101",	"0010",	"1100"
			}
		},
		
		//sbox 4
		{
			{
				"0111",	"1101",	"1110",	"0011",	"0000",	"0110",	"1001",	"1010",	"0001",	"0010",	"1000",	"0101",	"1011",	"1100",	"0100",	"1111"
			},
			{
				"1101",	"1000",	"1011",	"0101",	"0110",	"1111",	"0000",	"0011",	"0100",	"0111",	"0010",	"1100",	"0001",	"1010",	"1110",	"1001"
			},
			{
				"1010",	"0110",	"1001",	"0000",	"1100",	"1011",	"0111",	"1101",	"1111",	"0001",	"0011",	"1110",	"0101",	"0010",	"1000",	"0100"
			},
			{
				"0011",	"1111",	"0000",	"0110",	"1010",	"0001",	"1101",	"1000",	"1001",	"0100",	"0101",	"1011",	"1100",	"0111",	"0010",	"1110"
			}
		},
		
		//sbox 5
		{
			{
				"0010",	"1100",	"0100",	"0001",	"0111",	"1010",	"1011",	"0110",	"1000",	"0101",	"0011",	"1111",	"1101",	"0000",	"1110",	"1001"
			},
			{
				"1110",	"1011",	"0010",	"1100",	"0100",	"0111",	"1101",	"0001",	"0101",	"0000",	"1111",	"1010",	"0011",	"1001",	"1000",	"0110"
			},
			{
				"0100",	"0010",	"0001",	"1011",	"1010",	"1101",	"0111",	"1000",	"1111",	"1001",	"1100",	"0101",	"0110",	"0011",	"0000",	"1110"
			},
			{
				"1011",	"1000",	"1100",	"0111",	"0001",	"1110",	"0010",	"1101",	"0110",	"1111",	"0000",	"1001",	"1010",	"0100",	"0101",	"0011"
			}
		},
		
		//sbox 6
		{
			{
				"1100",	"0001",	"1010",	"1111",	"1001",	"0010",	"0110",	"1000",	"0000",	"1101",	"0011",	"0100",	"1110",	"0111",	"0101",	"1011"
			},
			{
				"1010",	"1111",	"0100",	"0010",	"0111",	"1100",	"1001",	"0101",	"0110",	"0001",	"1101",	"1110",	"0000",	"1011",	"0011",	"1000"
			},
			{
				"1001",	"1110",	"1111",	"0101",	"0010",	"1000",	"1100",	"0011",	"0111",	"0000",	"0100",	"1010",	"0001",	"1101",	"1011",	"0110"
			},
			{
				"0100",	"0011",	"0010",	"1100",	"1001",	"0101",	"1111",	"1010",	"1011",	"1110",	"0001",	"0111",	"0110",	"0000",	"1000",	"1101"
			}
		},
		
		//sbox 7
		{
			{
				"0100",	"1011",	"0010",	"1110",	"1111",	"0000",	"1000",	"1101",	"0011",	"1100",	"1001",	"0111",	"0101",	"1010",	"0110",	"0001"
			},
			{
				"1101",	"0000",	"1011",	"0111",	"0100",	"1001",	"0001",	"1010",	"1110",	"0011",	"0101",	"1100",	"0010",	"1111",	"1000",	"0110"
			},
			{
				"0001",	"0100",	"1011",	"1101",	"1100",	"0011",	"0111",	"1110",	"1010",	"1111",	"0110",	"1000",	"0000",	"0101",	"1001",	"0010"
			},
			{
				"0110",	"1011",	"1101",	"1000",	"0001",	"0100",	"1010",	"0111",	"1001",	"0101",	"0000",	"1111",	"1110",	"0010",	"0011",	"1100"
			}
		},
		
		//sbox 8
		{
			{
				"1101",	"0010",	"1000",	"0100",	"0110",	"1111",	"1011",	"0001",	"1010",	"1001",	"0011",	"1110",	"0101",	"0000",	"1100",	"0111"
			},
			{
				"0001",	"1111",	"1101",	"1000",	"1010",	"0011",	"0111",	"0100",	"1100",	"0101",	"0110",	"1011",	"0000",	"1110",	"1001",	"0010"
			},
			{
				"0111",	"1011",	"0100",	"0001",	"1001",	"1100",	"1110",	"0010",	"0000",	"0110",	"1010",	"1101",	"1111",	"0011",	"0101",	"1000"
			},
			{
				"0010",	"0001",	"1110",	"0111",	"0100",	"1010",	"1000",	"1101",	"1111",	"1100",	"1001",	"0000",	"0011",	"0101",	"0110",	"1011"
			}
		}
	};
	

}
