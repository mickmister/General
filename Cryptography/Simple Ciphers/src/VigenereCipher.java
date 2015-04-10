import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Stack;


public class VigenereCipher 
{
	static int aPosition = 65;
	static int numLetters = 26;
	Stack<String> firstStack = new Stack<String>();
	Stack<String> secondStack = new Stack<String>();
	
	double alphaFreqs[] = new double[] {0.082, 0.015, 0.028, 0.043, 0.127, 0.022, 0.020, 0.061, 0.070, 0.002, 
			0.008, 0.040, 0.024, 0.067, 0.075, 0.019, 0.001, 0.060, 0.063, 0.091,
			0.028, 0.010, 0.023, 0.001, 0.020, 0.001};

	
	public VigenereCipher()
	{
		String input = "tzdfkvzqxtudarcxeetlnxetwektkpqmvzwtisgasffjvbjxdacivxusaktctilawekwvimdbbvxvqdeabrswrdrwxaokwaqexaxwgxmtjxobqigvarktbbhlxjviobzvspctxbvsbhmtvhnkwcyxjvftmhawvgsieldxwworrtzsslsukitegtuvgktetbhlsvyixfdxjiulpmbapliyveihbizifvemagisvceekfcgwhugevxgisxkfrpbhwwrqlkaiclwvvfhxlhggcceigmwgwtnrrxmdjjwwvhurhwwiogmkxgharkjxxkovwrvjtkbbrwwucibtfpuiuysfxoqgetulxkgisvuymivihlsfzeghtiziuksexbedepjxathrsrurzxasghiqgpxtbsjiukskxtgwiffqmhhwwkcceqr";
		input = input.toUpperCase();
		
		char[] cipherArray = input.toCharArray();
		
		int keyLength = getKeyLength(cipherArray);
		
		System.out.println("Key Length: " + keyLength);
		
		int[] key = new int[keyLength];
		
		
			
		
		for(int i=0; i<keyLength; i++)
		{
			int shiftAmount = getShift(cipherArray, i, keyLength);
			key[i] = shiftAmount;
		}
		
		System.out.print("Key: [ ");
		for(Integer shift : key)
		{
			System.out.print(shift + " ");
		}
		System.out.println("]");
		
		
		
		printDecryption(cipherArray, key);
		
		
	}
	
	private void printDecryption(char[] cipherArray, int[] key) 
	{
		int keyLength = key.length;
		int mod = cipherArray.length % keyLength;
		int chunks = cipherArray.length / keyLength;
		
		char[] decipherArray = new char[cipherArray.length];
		
		for(int keyIndex = 0; keyIndex < keyLength; keyIndex++)
		{
			int offset = key[keyIndex];
			int chunkIndex = 0;
			for(chunkIndex = 0; chunkIndex < chunks; chunkIndex++)
			{
				char c = cipherArray[chunkIndex * keyLength + keyIndex];
				int position = (c - aPosition);
				int newPosition = (position  - offset + numLetters) % numLetters;
				char newChar = (char) (newPosition + aPosition);
				decipherArray[chunkIndex * keyLength + keyIndex] = newChar;
			}
			if(mod > keyIndex) //extra chunks
			{
				char c = cipherArray[chunkIndex * keyLength + keyIndex];
				int position = (c - aPosition);
				int newPosition = (position + offset) % numLetters;
				char newChar = (char) (newPosition + aPosition);
				decipherArray[chunkIndex * keyLength + keyIndex] = newChar;
			}
		}
		
		
		PrintWriter writer = null;
		try 
		{
			writer = new PrintWriter("vigenere_decipher.txt", "UTF-8");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		for(Character c : decipherArray)
		{
			writer.print(c);
		}
		
		writer.close();
		
	}

	private int getShift(char[] cipherArray, int index, int keyLength)
	{
		int mod = cipherArray.length % keyLength;
		int chunks = cipherArray.length / keyLength;
		if(mod > index)
		{
			chunks++;
		}
		
		int[] freqs = new int[numLetters];
		for(int i = 0; i < numLetters; i++)
		{
			freqs[i] = 0;
		}	
		
		for(int i=0; i<chunks; i++)
		{
			char c = cipherArray[keyLength * i + index];
			freqs[c - aPosition]++;
		}
		
		double[] doubleFreqs = new double[numLetters];
		for(int i=0; i<numLetters; i++)
		{
			doubleFreqs[i] = ((double) freqs[i]) / ((double) chunks);
		}
		
		double maxDotProduct = 0;
		int currentShiftIndex = 0;
		
		for(int i=0; i<numLetters; i++)
		{
			double dotProduct = calculateDotProduct(doubleFreqs, i);
			if(dotProduct > maxDotProduct)
			{
				maxDotProduct = dotProduct;
				currentShiftIndex = i;
			}
		}		
		
		return currentShiftIndex;
	}

	private double calculateDotProduct(double[] doubleFreqs, int offset) 
	{
		double result = 0;
		for(int letterIndex = 0; letterIndex < numLetters; letterIndex++)
		{
			double first = doubleFreqs[letterIndex];
			double second = alphaFreqs[(letterIndex - offset + numLetters) % numLetters];
			result += first * second;
		}
		return result;
	}


	private int getKeyLength(char[] cipherArray)
	{
		int currentMax = 0;
		int currentKeyLength = 0;
		int length = cipherArray.length;
		for(int offset=1; offset<length; offset++)
		{
			if(offset > 10) break;
			int temp = getNumCoincidences(cipherArray, offset);
			if(temp > currentMax)
			{
				currentMax = temp;
				currentKeyLength = offset;
			}
		}
		return currentKeyLength;
	}

	private int getNumCoincidences(char[] cipherArray, int offset) 
	{
		int count = 0;
		int length = cipherArray.length;
		for(int i=0; i<length - offset; i++)
		{
			int comparisonIndex = i + offset;
			if(cipherArray[i] == cipherArray[comparisonIndex])
				count++;
		}
		return count;
	}

}
