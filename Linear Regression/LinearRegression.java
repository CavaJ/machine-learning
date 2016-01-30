import java.io.*;
import java.util.*;

public class LinearRegression
{
	
	public static void main(String[] args)
	{
		double theta0 = 0;
		double theta1 = 0;
		
		// number of training examples
		int m;
		
		// learning rate
		double alfa = 0.01;
		
		// read text file
		List<Double> listOfX = new ArrayList<Double>();
		List<Double> listOfY = new ArrayList<Double>();
		
		File file = new File("ex1data1.txt");
		File result = new File("resultLinearRegression.txt");
		PrintWriter writer = null;
		BufferedReader reader = null;
		
		String line = null;
		int index = 0;
		
		try {
			reader = new BufferedReader(new FileReader(file));
			result.createNewFile();
			writer = new PrintWriter(result);
			while((line = reader.readLine()) != null) {
				String[] lineComponents = line.split(",");
				listOfX.add(Double.parseDouble(lineComponents[0]));
				listOfY.add(Double.parseDouble(lineComponents[1]));
				index ++;
			} // while
			
			//inintialize number of training examples.
			m = listOfX.size();
			
			for (int i = 0; i < 1500; i ++)
			{
				// for updating theta0
				double sum = 0;
		
				for (int indexOf = 0; indexOf < m; indexOf ++)
				{
					sum = sum + (hypothesis(theta0, theta1, listOfX.get(indexOf)) - listOfY.get(indexOf));
				} // for
				
				double tempTheta0 = theta0 - (alfa / m) * sum;
				
				//for updating theta1
				sum = 0;
				
				for (int indexOf = 0; indexOf < m; indexOf ++)
				{
					sum = sum + (hypothesis(theta0, theta1, listOfX.get(indexOf)) - listOfY.get(indexOf)) * listOfX.get(indexOf);
				} // for
				
				double tempTheta1 = theta1 - (alfa / m) * sum;
				
				//now update the values of theta0 and theta1
				theta0 = tempTheta0;
				theta1 = tempTheta1;
				
				//output the value of theta0 and theta1 and costFunction
				System.out.print("-----------------------------------------------------\n");
				System.out.println("Theta0: " + theta0);
				System.out.println("Theta1: " + theta1);
				
				double valueOfCostFunction = costFunction(theta0, theta1, m, listOfX, listOfY);
				System.out.println("Value of cost function: " + valueOfCostFunction);
				
				writer.write("\n-----------------------------------------------------\n"
								+ "Theta0: " + theta0 + "\n"
								+ "Theta1: " + theta1 + "\n"
								+ "Value of cost function: " + valueOfCostFunction);
				
			} // for
			
			System.out.println("\nCheck out the resultLinearRegression.txt.txt file !");
		}
		catch(Exception e) {
			System.err.println("Error while reading a file \"ex1data1.txt\": " + e.getMessage());
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
	
	private static double costFunction(double theta0, double theta1, int numberOfTrainingExamples, List<Double> listOfX, List<Double> listOfY) {
		
		double sum = 0;
		
		for (int index = 0; index < numberOfTrainingExamples; index ++)
		{
			sum = sum + (hypothesis(theta0, theta1, listOfX.get(index)) - listOfY.get(index)) * (hypothesis(theta0, theta1, listOfX.get(index)) - listOfY.get(index));
		} // for
		
		return sum / (2 * numberOfTrainingExamples);
	}
	
	private static double hypothesis(double theta0, double theta1, double x)
	{
		return theta0 + theta1 * x;
	} // hypothesis
} // class LinearRegression