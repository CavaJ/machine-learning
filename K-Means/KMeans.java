import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class KMeans
{
	public static void main(String[] args) throws Exception
	{
		List<Double> listOfX = new ArrayList<Double>();
		List<Double> listOfY = new ArrayList<Double>();
		
		String line = "";
		
		//cluster centers
		double x1 = 3;
		double y1 = 3;
		
		double x2 = 6;
		double y2 = 2;
		
		double x3 = 8;
		double y3 = 5;
		
		//reading the dataset
		BufferedReader bfr = null;
		
		try
		{
			bfr = new BufferedReader(new FileReader(new File("data_for_exercise4.txt")));
			
			while((line = bfr.readLine()) != null)
			{
				String[] lineComponents = line.split("\t");
				listOfX.add(Double.parseDouble(lineComponents[0]));
				listOfY.add(Double.parseDouble(lineComponents[1]));
			} // while
			
			List<Double> tempClusteredListOfXforK1 = new ArrayList<Double>();
			List<Double> tempClusteredListOfYforK1 = new ArrayList<Double>();
			
			List<Double> tempClusteredListOfXforK2 = new ArrayList<Double>();
			List<Double> tempClusteredListOfYforK2 = new ArrayList<Double>();
			
			List<Double> tempClusteredListOfXforK3 = new ArrayList<Double>();
			List<Double> tempClusteredListOfYforK3 = new ArrayList<Double>();
			
			//System.out.println(listOfX.size());
			
			//here will be another for loop for defining number of iterations that will encompass all the code down to the closing bracket of try 
			
			for (int index = 0; index < listOfX.size(); index ++)
			{
				int classNumber = 1;
				
				double min = Math.sqrt( (listOfX.get(index) - x1) * (listOfX.get(index) - x1) + (listOfY.get(index) - y1) * (listOfY.get(index) - y1) );
				
				if ( Math.sqrt( (listOfX.get(index) - x2) * (listOfX.get(index) - x2) + (listOfY.get(index) - y2) * (listOfY.get(index) - y2) ) < min)
				{
					min = Math.sqrt( (listOfX.get(index) - x2) * (listOfX.get(index) - x2) + (listOfY.get(index) - y2) * (listOfY.get(index) - y2) );
					classNumber = 2;
				} // if
				
				if ( Math.sqrt( (listOfX.get(index) - x3) * (listOfX.get(index) - x3) + (listOfY.get(index) - y3) * (listOfY.get(index) - y3) ) < min)
				{
					min = Math.sqrt( (listOfX.get(index) - x3) * (listOfX.get(index) - x3) + (listOfY.get(index) - y3) * (listOfY.get(index) - y3) );
					classNumber = 3;
				} // if
				
				switch(classNumber)
				{
					case 1: 
					{
						tempClusteredListOfXforK1.add(listOfX.get(index));
						tempClusteredListOfYforK1.add(listOfY.get(index));
					} // case 1
					
					break;
					
					case 2:
					{
						tempClusteredListOfXforK2.add(listOfX.get(index));
						tempClusteredListOfYforK2.add(listOfY.get(index));
					} // case 2
					
					break;
					
					case 3:
					{
						tempClusteredListOfXforK3.add(listOfX.get(index));
						tempClusteredListOfYforK3.add(listOfY.get(index));
					} // case 3
				} // switch
				
				
				
			} // for
			
			double sumOfX1 = 0;
			double sumOfX2 = 0;
			double sumOfX3 = 0;
				
			double sumOfY1 = 0;
			double sumOfY2 = 0;
			double sumOfY3 = 0;
				
			for (int i = 0; i < tempClusteredListOfXforK1.size(); i ++)
			{
				sumOfX1 += tempClusteredListOfXforK1.get(i);
				sumOfY1 += tempClusteredListOfYforK1.get(i);
			} // for
				
			//update centroids
			x1 = sumOfX1 / tempClusteredListOfXforK1.size();
			y1 = sumOfY1 / tempClusteredListOfYforK1.size();
				
			//----------------------------------------------------
			for (int i = 0; i < tempClusteredListOfXforK2.size(); i ++)
			{
				sumOfX2 += tempClusteredListOfXforK2.get(i);
				sumOfY2 += tempClusteredListOfYforK2.get(i);
			} // for
				
			//update centroids
			x2 = sumOfX2 / tempClusteredListOfXforK2.size();
			y2 = sumOfY2 / tempClusteredListOfYforK2.size();
				
			//-----------------------------------------------------
			for (int i = 0; i < tempClusteredListOfXforK3.size(); i ++)
			{
				sumOfX3 += tempClusteredListOfXforK3.get(i);
				sumOfY3 += tempClusteredListOfYforK3.get(i);
			} // for
				
			//update centroids
			x3 = sumOfX3 / tempClusteredListOfXforK3.size();
			y3 = sumOfY3 / tempClusteredListOfYforK3.size();
				
			
			DecimalFormat df = new DecimalFormat("##.##");
			df.setRoundingMode(RoundingMode.FLOOR);
			
			System.out.printf("New centroids: (" + "%s" + ", " + "%s" + "), " + "(" + "%s" + ", " + "%s" + "), " + "(" + "%s" + ", " + "%s" + ")", 
							  df.format(x1), df.format(y1), df.format(x2), df.format(y2), df.format(x3), df.format(y3));
		}
		catch(Exception e)
		{
			System.err.println("IO Error: " + e);
		} // catch
		finally
		{
			bfr.close();
		}
	} // main
} // class KMeans