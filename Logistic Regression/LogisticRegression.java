import java.io.*;
import java.util.*;

public class LogisticRegression
{
	
	public static void main(String[] args)
	{
		double theta0 = 0;
		double theta1 = 0;
		double theta2 = 0;
		
		// number of training examples
		int m;
		
		// learning rate
		double alfa = 0.00001;
		
		// read text file
		List<Double> listOfX1 = new ArrayList<Double>();
		List<Double> listOfX2 = new ArrayList<Double>();
		List<Double> listOfY = new ArrayList<Double>();
		
		File file = new File("ex2data1.txt");
		File result = new File("resultLogisticRegression.txt");
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
			
			double sum = 0;
			
			
			
			
			for (int i = 0; i < 150; i ++) // number of iterations 150
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
			
			System.out.println("\nCheck out the resultLogisticRegression.txt file !");
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
			sum = sum + listOfY.get(index) * Math.log(hypothesis(theta0, theta1, theta2, listOfX1.get(index), listOfX2.get(index)))
						+ (1 - listOfY.get(index)) * Math.log(1 - hypothesis(theta0, theta1, theta2, listOfX1.get(index), listOfX2.get(index)));
		} // for
		
		return -sum / numberOfTrainingExamples;
	} // costFunction
	
	private static double hypothesis(double theta0, double theta1, double theta2, double x1, double x2) // hypothesis function becomes sigmoid function
	{
		//sigmoid function is like 1 / (1 + Math.exp(-z));
		//and z is -> row matrix of thetas multiplied by column matrix of x's
		
		//first calculate z
		double z = theta0 + theta1 * x1 + theta2 * x2; // we have two x values and one y values.
		
		return (1 / (1 + Math.exp(-z))); // return value of sigmoid function
	} // hypothesis
} // class LogisticRegression