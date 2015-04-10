package Main;

import java.util.ArrayList;
import java.util.List;

public class KeyHandler 
{
	String originalKey;
	int numRounds;
	
	List<String> keyList;
	
	static int[] leftShiftsPerRound = new int[]
	{
		1,	2,	4,	6,	8,	10,	12,	14,	15,	17,	19,	21,	23,	25,	27,	0
	};


	public KeyHandler(String key, int numRounds)
	{
		this.originalKey = key;
		this.numRounds = numRounds;
		keyList = new ArrayList<String>();
		createKeys();
	}

	private void createKeys() 
	{
		String withoutParities = removeParityBits(originalKey);
		String realKey = Permutations.performKeyPermutationInitial(withoutParities);
		String leftKey = realKey.substring(0, 28);
		String rightKey = realKey.substring(28);
		for(int round = 0; round < numRounds; round++)
		{
			createKey(round, leftKey, rightKey);
		}
	}

	private void createKey(int round, String leftKey, String rightKey) 
	{
		int shiftAmount = leftShiftsPerRound[round];
		String leftKeyShifted = shiftLeft(leftKey, shiftAmount);
		String rightKeyShifted = shiftLeft(rightKey, shiftAmount);
		String permutated = Permutations.performKeyPermutationFinal(leftKeyShifted + rightKeyShifted);
		keyList.add(permutated);
	}

	private String shiftLeft(String realKey, int shiftAmount) 
	{
		return realKey.substring(shiftAmount) + realKey.substring(0, shiftAmount);
	}

	private String removeParityBits(String key) 
	{
		return key.substring(0, 7) + key.substring(8, 15) + key.substring(16, 23) + key.substring(24, 31)
				+ key.substring(32, 39) + key.substring(40, 47) + key.substring(48, 55) + key.substring(56, 63);
	}
	
	public String getKey(int index)
	{
		return keyList.get(index);
	}

}
