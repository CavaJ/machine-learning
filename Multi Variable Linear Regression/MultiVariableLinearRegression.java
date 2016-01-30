import java.io.*;
import java.util.*;

public class MultiVariableLinearRegression 
{
	
	public static void main(String[] args)
	{
		double theta0 = 0;
		double theta1 = 0;
		double theta2 = 0;
		
		// number of training examples
		int m;
		
		// learning rate
		double alfa = 0.1;
		
		// read text file
		List<Double> listOfX1 = new ArrayList<Double>();
		List<Double> listOfX2 = new ArrayList<Double>();
		List<Double> listOfY = new ArrayList<Double>();
		
		File file = new File("ex1data2.txt");
		File result = new File("resultMultiVariableLinearRegression.txt");
		PrintWriter writer = null;
		BufferedReader reader = null;
		
		String line = null;
		//int index = 0;
		
		try {
			reader = new BufferedReader(new FileReader(file));
			result.createNewFile();
			writer = new PrintWriter(result);
			while((line = reader.readLine()) != null) {
				String[] lineComponents = line.split(",");
				listOfX1.add(Double.parseDouble(lineComponents[0]));
				listOfX2.add(Double.parseDouble(lineComponents[1]));
				listOfY.add(Double.parseDouble(lineComponents[2]));
				//index ++;
			} // while
			
			//inintialize number of training examples.
			m = listOfX1.size();
			
			// ***** Normalization start *****
			
			double maxX1 = listOfX1.get(0);
			double minX1 = listOfX1.get(0);
			
			// --- find max and min of list1
			for (int index = 0; index < listOfX1.size(); index ++)
			{
				if (listOfX1.get(index) > maxX1)
					maxX1 = listOfX1.get(index);
				
				if (listOfX1.get(index) < minX1)
					minX1 = listOfX1.get(index);
			} // for
			
			double sumToFindAverage = 0;
			
			// --- find average of listOfX1
			for (int index = 0; index < listOfX1.size(); index ++)
			{
				sumToFindAverage += listOfX1.get(index);
			} // for
			
			double averageX1 = sumToFindAverage / listOfX1.size();
			
			// <><><><><> New Code
			
			//finding sigma for X1...
			double sum = 0;

            for (int index = 0; index < listOfX1.size(); index ++)
            {
                sum += (listOfX1.get(index) - averageX1) * (listOfX1.get(index) - averageX1);
            } // for
			
			double varianceX1 = sum / (listOfX1.size() - 1); // sample variance
            double standardDeviationX1 = Math.sqrt(varianceX1);
			
			
			for (int index = 0; index < listOfX1.size(); index ++)
			{
			    double temporaryNormalResult = (listOfX1.get(index) - averageX1) / standardDeviationX1;
				listOfX1.set(index, temporaryNormalResult);
			} // for
			
			
			// <><><><><><><><><> end New code
			
			/*
			for (int index = 0; index < listOfX1.size(); index ++)
			{
			    double temporaryNormalResult = (listOfX1.get(index) - averageX1) / (maxX1 - minX1);
				listOfX1.set(index, temporaryNormalResult);
			} // for
			*/
			
			// ------------------------------------------------------
			
			double maxX2 = listOfX2.get(0);
			double minX2 = listOfX2.get(0);
			
			// --- find max and min of list2
			for (int index = 0; index < listOfX2.size(); index ++)
			{
				if (listOfX2.get(index) > maxX2)
					maxX2 = listOfX2.get(index);
				
				if (listOfX2.get(index) < minX2)
					minX2 = listOfX2.get(index);
			} // for
			
			sumToFindAverage = 0;
			
			// --- find average of listOfX2
			for (int index = 0; index < listOfX2.size(); index ++)
			{
				sumToFindAverage += listOfX2.get(index);
			} // for
			
			double averageX2 = sumToFindAverage / listOfX2.size();
			
			// <><><><><> New Code
			
			//finding sigma for X2...
			sum = 0;

            for (int index = 0; index < listOfX2.size(); index ++)
            {
                sum += (listOfX2.get(index) - averageX2) * (listOfX2.get(index) - averageX2);
            } // for
			
			double varianceX2 = sum / (listOfX2.size() - 1); // sample variance
            double standardDeviationX2 = Math.sqrt(varianceX2);
			
			
			for (int index = 0; index < listOfX2.size(); index ++)
			{
			    double temporaryNormalResult = (listOfX2.get(index) - averageX2) / standardDeviationX2;
				listOfX2.set(index, temporaryNormalResult);
			} // for
			
			
			// <><><><><><><><><> end New code
			
			/*
			for (int index = 0; index < listOfX2.size(); index ++)
			{
			    double temporaryNormalResult = (listOfX2.get(index) - averageX2) / (maxX2 - minX2);
				listOfX2.set(index, temporaryNormalResult);
			} // for
			*/
			
			
			// ***** Normalization end *****
			
			for (int i = 0; i < 50; i ++) // number of iterations 50
			{
				// for updating theta0
				sum = 0;
		
				for (int indexOf = 0; indexOf < m; indexOf ++)
				{
					sum = sum + (hypothesis(theta0, theta1, theta2, listOfX1.get(indexOf), listOfX2.get(indexOf)) - listOfY.get(indexOf));
				} // for
				
				double tempTheta0 = theta0 - (alfa / m) * sum;
				
				//for updating theta1
				sum = 0;
				
				for (int indexOf = 0; indexOf < m; indexOf ++)
				{
					sum = sum + (hypothesis(theta0, theta1, theta2, listOfX1.get(indexOf), listOfX2.get(indexOf)) - listOfY.get(indexOf)) * listOfX1.get(indexOf);
				} // for
				
				double tempTheta1 = theta1 - (alfa / m) * sum;
				
				
				// for updating theta2
				sum = 0;
				
				for (int indexOf = 0; indexOf < m; indexOf ++)
				{
					sum = sum + (hypothesis(theta0, theta1, theta2, listOfX1.get(indexOf), listOfX2.get(indexOf)) - listOfY.get(indexOf)) * listOfX2.get(indexOf);
				} // for
				
				double tempTheta2 = theta2 - (alfa / m) * sum;
				
				//now update the values of theta0, theta1, theta2
				theta0 = tempTheta0;
				theta1 = tempTheta1;
				theta2 = tempTheta2;
				
				//output the value of theta0, theta1, theta2 and costFunction
				System.out.print("-----------------------------------------------------\n");
				System.out.println("Theta0: " + theta0);
				System.out.println("Theta1: " + theta1);
				System.out.println("Theta2: " + theta2);
				
				double valueOfCostFunction = costFunction(theta0, theta1, theta2, m, listOfX1, listOfX2, listOfY);
				System.out.println("Value of cost function: " + valueOfCostFunction);
				
				writer.write("\n-----------------------------------------------------\n"
								+ "Theta0: " + theta0 + "\n"
								+ "Theta1: " + theta1 + "\n"
								+ "Theta2: " + theta2 + "\n"
								+ "Training Examples: " + m + "\n"
								+ "Value of cost function: " + valueOfCostFunction);
				
			} // for
			
			System.out.println("\nCheck out the resultMultiVariableLinearRegression.txt file !");
		}
		catch(Exception e) {
			System.err.println("Error while reading a file \"ex1data2.txt\": " + e.getMessage());
		}
		finally {
			try {
			
				if (reader != null)
					reader.close();
			  } // try
			  catch(Exception e) {
			  e.printStackTrace();
			  }
			
			if (writer != null)
			  writer.close();
		}
	} // main
	
	private static double costFunction(double theta0, double theta1, double theta2, int numberOfTrainingExamples, List<Double> listOfX1, 
									   List<Double> listOfX2, List<Double> listOfY) 
	{
		
		double sum = 0;
		
		for (int index = 0; index < numberOfTrainingExamples; index ++)
		{
			sum = sum + (hypothesis(theta0, theta1, theta2, listOfX1.get(index), listOfX2.get(index)) - listOfY.get(index))
						* (hypothesis(theta0, theta1, theta2, listOfX1.get(index), listOfX2.get(index)) - listOfY.get(index));
		} // for
		
		return sum / (2 * numberOfTrainingExamples);
	} // costFunction
	
	private static double hypothesis(double theta0, double theta1, double theta2, double x1, double x2)
	{
		return theta0 + theta1 * x1 + theta2 * x2;
	} // hypothesis
} // class MultiVariableLinearRegression