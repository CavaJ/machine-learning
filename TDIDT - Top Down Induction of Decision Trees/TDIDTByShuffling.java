//The main class to build the decision tree

import java.util.*;
import java.io.*;

public class TDIDTByShuffling
{
	private static int numberOfTrainingPatterns = 0;
	private static int numberOfTestPatterns = 0;
	private static int numberOfAttributes = 0;
	
	private static ArrayList<Integer> usedAttributeIndexes;
	private static ArrayList<Integer> splitValues;
	private static int nodeId;
	private static PrintWriter calcWriter;
	
	//static initialization block
	static 
	{
		try
		{
			calcWriter = new PrintWriter(new File("calculations.txt"));
		} // try
		catch(FileNotFoundException ex)
		{
			System.err.println("calculations.txt NOT FOUND!");
		} // catch
	} // static
	
	private static ArrayList<Pattern> createFromFile(File file)
	{	
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		BufferedReader reader = null;
		String line = null;
		
		try
		{
			//Read the test data
			reader = new BufferedReader(new FileReader(file));
			
			while((line = reader.readLine()) != null)
			{
				String[] lineComponents = line.split(",");
				
				//Attribute and Pattern creation step...
				int classLabel = Integer.parseInt(lineComponents[0]);
				
				ArrayList<Attribute> attributes = new ArrayList<Attribute>();
				
				for (int index = 1; index < lineComponents.length; index ++)
				{
					int value = Integer.parseInt(lineComponents[index]);
					
					//create an Attribute with the given id and value.
					Attribute attribute = new Attribute(index, value);
					
					attributes.add(attribute);
				} // for
				
				Pattern pattern = new Pattern(classLabel, attributes);
				patterns.add(pattern);
			} // while
			
			
		} // try
		catch(FileNotFoundException e) 
		{ 
			System.out.println("File Not Found");
		} // catch
		catch(ArrayIndexOutOfBoundsException e) { 
			System.out.println("Array index out of bounds");
		} // catch
		catch(IOException e) { 
			System.out.println("Error in reading input files: " + e.getMessage());
		} // catch
		catch(Exception ex) //general exception catching
		{
			System.err.println(ex.getMessage());
		} // catch
		finally
		{
			try
			{
				if (reader != null)
					reader.close();
			} // try
			catch(Exception ex)
			{
				ex.printStackTrace();
			} // catch
			
		} // finally
		
		return patterns;
	} //createFromFile
	
	public static void main(String[] args)
	{	
		File trainingFile = null;
		File testFile = null;
		
		try
		{
			trainingFile = new File(args[0]);
			testFile = new File(args[1]);
		} // try
		catch(Exception e)
		{
			System.err.println("Argument 1 must be train file, Argument 2 must be test file!");
			return;
		} // catch
		
		//write the output to output.txt file.
		PrintWriter writer = null;
		File output = new File("output.txt");
		
		try
		{
			writer = new PrintWriter(output);
		
			//we will repeat 10 times union, shuffling and dividing operation for the training and test data
			for(int i = 0; i < 10;  i ++)
			{
				usedAttributeIndexes = new ArrayList<Integer>();
				splitValues = new ArrayList<Integer>();
				nodeId = 1;
			
				//lists to hold the training patterns and test patterns
				ArrayList<Pattern> trainingPatterns = createFromFile(trainingFile);
				ArrayList<Pattern> testPatterns = createFromFile(testFile);
			
				//take the union and shuffle
				trainingPatterns.addAll(testPatterns);
				Collections.shuffle(trainingPatterns);
			
				//we will split all data: 2/3 part will be training data and 1/3 test data.
				int splitPoint =  (int) (trainingPatterns.size() * 2 / (double) 3);
			
				//now each one will get its proportion.
				//subList() returns the sublist from index to some other index.
				testPatterns = new ArrayList<Pattern>(trainingPatterns.subList(splitPoint, trainingPatterns.size()));
				trainingPatterns = new ArrayList<Pattern>(trainingPatterns.subList(0, splitPoint));
				
				System.out.println("---------------- NEW CALCULATION----------------------\n");
				System.out.println("Split Point On Whole data:" + splitPoint);
				
				calcWriter.write("---------------- NEW CALCULATION----------------------\n");
				calcWriter.write("Split Point On Whole data:" + splitPoint + "\n");
		
				numberOfTrainingPatterns = trainingPatterns.size();
				numberOfTestPatterns = testPatterns.size();
		
				numberOfAttributes = trainingPatterns.get(0).getAttributes().size();
		
				System.out.println("No. Of All Training Patterns: " + numberOfTrainingPatterns);
				System.out.println("No. Of All Test Patterns: " + numberOfTestPatterns + "\n");
		
				//write also to the file.
				calcWriter.write("No. Of All Training Patterns: " + numberOfTrainingPatterns + "\n");
				calcWriter.write("No. Of All Test Patterns: " + numberOfTestPatterns + "\n\n");
		
				recursivelyBuildDecisionTree(trainingPatterns);
		
				//Number of split values is always 1 less than number of used attributes
				for (int index = 0; index < usedAttributeIndexes.size(); index ++)
				{
					//it is root node
					if (nodeId == 1)
					{
						writer.write(nodeId + " root " + "F" + (usedAttributeIndexes.get(index) + 1) + " " + (nodeId + 1) + " " + (nodeId + 2) + "\n");
						//System.out.printf("%4d %6s F%d %4d %4d%n", nodeId, "root", (usedAttributeIndexes.get(index) + 1), (nodeId + 1), (nodeId + 2));
					} // if
					else
					{
						if (splitValues.get(index - 1) == 0)
						{
							writer.write(nodeId + "  no  " + "F" + (usedAttributeIndexes.get(index) + 1) + " " + (nodeId + 2) + " " + (nodeId + 3) + "\n");
							writer.write(++ nodeId + "  yes " + "\n");
						} // if
						else 		// splitValues.get(index - 1) == 1
						{
							writer.write(nodeId + "  no  " + "\n");
							writer.write(++ nodeId + "  yes " + "F" + (usedAttributeIndexes.get(index) + 1) + " " + (nodeId + 1) + " " + (nodeId + 2) + "\n");
						} // else
					} // else
			
					nodeId ++;
				} // for
				
				writer.write("------------- END ------------\n\n");
				
			} // for
			
		} // try
		catch(Exception ex)
		{
			System.err.println("Error in file writing");
		} // catch
		finally
		{
			if (writer != null)
			{
				writer.close();
			} // if
		} // finally	
		
		System.out.println("Check out the output.txt and calculations.txt file for further details!");
		
		//close the calcWriter
		calcWriter.close();
		
	} // main
	
	private static void recursivelyBuildDecisionTree(ArrayList<Pattern> patterns)
	{
		//stopping criteria
		if (usedAttributeIndexes.size() == numberOfAttributes)
			return;
		
		System.out.print("Split On: ");
		//write also to the file
		calcWriter.write("Split On: ");
		
		//lets calculate initial-entropy that is not based on any attribute
		double initialEntropy = computeEntropy(patterns, false);
		
		System.out.println("Split Node Entropy: " + initialEntropy);
		//write also to the file
		calcWriter.write("Split Node Entropy: " + initialEntropy + "\n");
		
		//collect information gains
		double[] infoGainOverAnAttribute = new double[numberOfAttributes];
		
		for (int attributeIndex = 0; attributeIndex < numberOfAttributes; attributeIndex ++)
		{
			//System.out.println("Weighted Average: " + weightedAverageOverAnAttribute(patterns, attributeIndex));
			infoGainOverAnAttribute[attributeIndex] = initialEntropy - weightedAverageOverAnAttribute(patterns, attributeIndex);
			
			//if the attribute has been alrady used in a tree construction, set the info gain on that attribute to -1.
			if (usedAttributeIndexes.contains(attributeIndex))
				infoGainOverAnAttribute[attributeIndex] = -1;
			
		} // for
		
		//find maximal information gain
		double max = infoGainOverAnAttribute[0];
		//choose the best attribute as a root node
		int bestAttributeIndex = -1;
		
		for (int attributeIndex = 0; attributeIndex < numberOfAttributes; attributeIndex ++)
		{
			//print those info gains
			if (! usedAttributeIndexes.contains(attributeIndex))
				//System.out.println("F" + (attributeIndex + 1) + " Gain: " + infoGainOverAnAttribute[attributeIndex]);
				calcWriter.write("F" + (attributeIndex + 1) + " Gain: " + infoGainOverAnAttribute[attributeIndex] + "\n");
			else
				//System.out.println("F" + (attributeIndex + 1) + " Gain: " + "UNUSED");
				calcWriter.write("F" + (attributeIndex + 1) + " Gain: " + "UNUSED" + "\n");
			
			
			if (infoGainOverAnAttribute[attributeIndex] >= max)
			{
				max = infoGainOverAnAttribute[attributeIndex];
				bestAttributeIndex = attributeIndex;
			} // if
			
		} // for
		
		usedAttributeIndexes.add(bestAttributeIndex);
		
		System.out.println("Max info gain is on F" + (bestAttributeIndex + 1) + ": " + max);
		
		//write also to the file
		calcWriter.write("Max info gain is on F" + (bestAttributeIndex + 1) + ": " + max + "\n");
		
		ArrayList<Pattern> subPatterns = splitAndGetNewSet(patterns, bestAttributeIndex);
		
		// ---- FOR demonstration purposes -----
		
		ArrayList<Pattern> noSplitSubPatterns = splitAndGetNewNonSplitSet(patterns, bestAttributeIndex);
		
		if (noSplitSubPatterns != null)
		{
			//System.out.print("No-Split: ");
			computeEntropy(noSplitSubPatterns, false);
		} // if	
		
		// ---- FOR demonstration purposes -----
		
		if (subPatterns != null)
			recursivelyBuildDecisionTree(subPatterns);
		else
		{
			System.out.println("-------------DONE------------------");
			return;
		} // else
		
	} // recursivelyBuildDecisionTree
	
	private static ArrayList<Pattern> splitAndGetNewNonSplitSet(ArrayList<Pattern> patterns, int attributeIndex)
	{
		//split the set into two subsets based on a value of attribute, which can be 0 or 1.
		ArrayList<Pattern> rightPatterns = new ArrayList<Pattern>();
		ArrayList<Pattern> leftPatterns = new ArrayList<Pattern>();
		
		for (Pattern pattern : patterns)
		{
			if (pattern.getAttributeAtIndex(attributeIndex).getValue() == 1) //collect patterns for the right subtree
				rightPatterns.add(pattern);
			else // .getValue() == 0, collect patterns for the left subtree
				leftPatterns.add(pattern);
		} // for
		
		double leftEntropy = computeEntropy(leftPatterns, true);
		double rightEntropy = computeEntropy(rightPatterns, true);
		
		//depending on entropies return the subset with the lower entropy
		if (rightEntropy > leftEntropy)
		{
			System.out.print("LEFT SIDE: ");
			
			//write also to the file
			calcWriter.write("LEFT SIDE: \n");
			
			return leftPatterns;
		}
		else if (rightEntropy < leftEntropy)
		{
			System.out.print("RIGHT SIDE: ");
			
			//write also to the file
			calcWriter.write("RIGHT SIDE: \n");
			
			return rightPatterns;
		}
		else if ((rightEntropy == leftEntropy) && (leftEntropy != 0 || rightEntropy != 0)) // or 
		{
			if (Math.random() >= 0.5)
			{
				System.out.print("LEFT SIDE: ");
				
				//write also to the file
				calcWriter.write("LEFT SIDE: \n");
				
				return leftPatterns;
			} // if
			else
			{
				System.out.println("RIGHT SIDE: ");
				
				//write also to the file
				calcWriter.write("RIGHT SIDE: \n");
				
				return rightPatterns;
			} // else
		} // else if
		else
		{
			//System.out.println("No Further Split is possible...\n");
			//computeEntropy(leftPatterns, false); // for printing
			//computeEntropy(rightPatterns, false); // for printing
			return null;
		} // else
	} // split
	
	private static ArrayList<Pattern> splitAndGetNewSet(ArrayList<Pattern> patterns, int attributeIndex)
	{
		//split the set into two subsets based on a value of attribute, which can be 0 or 1.
		ArrayList<Pattern> rightPatterns = new ArrayList<Pattern>();
		ArrayList<Pattern> leftPatterns = new ArrayList<Pattern>();
		
		for (Pattern pattern : patterns)
		{
			if (pattern.getAttributeAtIndex(attributeIndex).getValue() == 1) //collect patterns for the right subtree
				rightPatterns.add(pattern);
			else // .getValue() == 0, collect patterns for the left subtree
				leftPatterns.add(pattern);
		} // for
		
		double leftEntropy = computeEntropy(leftPatterns, true);
		double rightEntropy = computeEntropy(rightPatterns, true);
		
		//depending on entropies return the subset with the higher entropy
		if (rightEntropy > leftEntropy)
		{
			System.out.println("\nChoosed RIGHT to split");
			
			//write also to the file
			calcWriter.write("\nChoosed RIGHT to split\n");
			
			//if it is right then a split value is 1
			splitValues.add(1);
			
			return rightPatterns;
		}
		else if (rightEntropy < leftEntropy)
		{
			System.out.println("\nChoosed LEFT to split");
			
			//write also to the file
			calcWriter.write("\nChoosed LEFT to split\n");
			
			//if it is left then a split value is 0
			splitValues.add(0);
			
			return leftPatterns;
		} // else if
		else if ((rightEntropy == leftEntropy) && (leftEntropy != 0 || rightEntropy != 0)) // or 
		{
			if (Math.random() >= 0.5)
			{
				System.out.println("\nChoosed LEFT to split");
				
				//write also to the file
				calcWriter.write("\nChoosed LEFT to split\n");
				
				//if it is left then a split value is 0
				splitValues.add(0);
				
				return leftPatterns;
			} // if
			else
			{
				System.out.println("\nChoosed RIGHT to split");
				
				//write also to the file
				calcWriter.write("\nChoosed RIGHT to split\n");
				
				//if it is right then a split value is 1
				splitValues.add(1);
				
				return rightPatterns;
			} // else
		} // else if
		else
		{
			System.out.println("No Further Split is possible...\n");
			
			//write also to the file
			calcWriter.write("No Further Split is possible...\n\n");
			
			computeEntropy(leftPatterns, false); // for printing plusCount and minusCount
			computeEntropy(rightPatterns, false); // for printing --- the same ----
			return null;
		} // else
	} // split
	
	//computes the entropy on a given pattern set.
	private static double computeEntropy(ArrayList<Pattern> patterns, boolean isSplit)
	{
		int plusCount = 0;
		int minusCount = 0;
		
		for (Pattern pattern : patterns)
		{
			//System.out.println(pattern);
			
			if (pattern.getClassLabel() == 0)
				minusCount ++;
			else
				plusCount ++;
		} // for
		
		double total = (double) (minusCount + plusCount);
		double plusP = plusCount / total;
		double minusP = minusCount / total;
		
		if (minusCount == 0 && plusCount == 0) // plusP and minusP results in NaN, should be handled carefully.
		{	
			plusP = 0;
			minusP = 0;
		} // if
		
		if (!isSplit)
		{
			System.out.println("(" + plusCount + "+, " + minusCount + "-)");
			
			//write also to the file
			calcWriter.write("(" + plusCount + "+, " + minusCount + "-)\n");
			
			//pluses = plusCount;
			//minuses = minusCount;
		} // if
		
		double entropy = (-1.0 * plusP) * log2(plusP) + (-1.0 * minusP) * log2(minusP);
		
		return entropy;
	} // computeEntropy
	
	private static double weightedAverageOverAnAttribute(ArrayList<Pattern> patterns, int attributeIndex)
	{
		//split the set into two subsets based on a value of attribute, which can be 0 or 1.
		ArrayList<Pattern> rightPatterns = new ArrayList<Pattern>();
		ArrayList<Pattern> leftPatterns = new ArrayList<Pattern>();
		
		for (Pattern pattern : patterns)
		{
			if (pattern.getAttributeAtIndex(attributeIndex).getValue() == 1) //collect patterns for the right subtree
				rightPatterns.add(pattern);
			else // .getValue() == 0, collect patterns for the left subtree
				leftPatterns.add(pattern);
		} // for
		
		double leftEntropy = computeEntropy(leftPatterns, true);
		double rightEntropy = computeEntropy(rightPatterns, true);
		
		double weightedAverage = (leftPatterns.size() / (double) patterns.size()) * leftEntropy
								 + (rightPatterns.size() / (double) patterns.size()) * rightEntropy;
		
		return weightedAverage;
	} // weightedAverageOverAnAttribute
	
	private static double log2(double argument)
	{
		if (argument == 0) 
			return 0;
		
		return Math.log(argument) / Math.log(2);
	} // log2
	
	
} // class TDIDT