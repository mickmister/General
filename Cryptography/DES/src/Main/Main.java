package Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Main 
{
	
	public static void main(String[] args) throws IOException
	{
		File inputFile = new File("desinput.txt");
		InputStream stream = new FileInputStream(inputFile);
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		
		String line;
		line = reader.readLine();
		int numIterations = Integer.parseInt(line);
		line = reader.readLine();
		int numRounds = Integer.parseInt(line);
		line = reader.readLine();
		String input = line;
		line = reader.readLine();
		String key = line;
		reader.close();
		numIterations = 100000;
		
		
		
		execute(input, key, numRounds, numIterations);
		
		
	}

	static void execute(String input, String key, int numRounds,
			int numIterations) 
	{
		KeyHandler keyHandler = new KeyHandler(key, numRounds);
		String cipher = "0000000000000000000000000000000000000000000000000000000000000000";
		
		for(int iteration = 0; iteration < numIterations; iteration++)
		{
			String inputXORCipher = ExpandAndXOR.xorStrings(input, cipher);
			String initialPerm = Permutations.performInitialPermutation(inputXORCipher);
			
			String leftString = initialPerm.substring(0, 32);
			String rightString = initialPerm.substring(32);
			
			for(int round = 0; round < numRounds; round++)
			{
				String roundKey = keyHandler.getKey(round);
				String tempLeft = rightString;
				String functionOutput = mainFunction(rightString, roundKey);
				rightString = ExpandAndXOR.xorStrings(leftString, functionOutput);
				leftString = tempLeft;
			}
			cipher = Permutations.performInitialPermutationInverse(rightString + leftString);
		}
		
		System.out.println(cipher.equals("1011110111110011010000010001011011101000010101100100101110101110"));
		
		
		
	}
	
	static String mainFunction(String rightString, String roundKey)
	{
		String expandedRight = ExpandAndXOR.performExpandRight(rightString);
		
		String xor = ExpandAndXOR.xorStrings(expandedRight, roundKey);
		
		String sboxCalculation = SBox.performTotalSBoxCalculation(xor);
		
		return sboxCalculation;
	}
	

	
	
	

}
