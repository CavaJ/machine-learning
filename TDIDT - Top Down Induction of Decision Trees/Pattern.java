//Instance of this class will describe one pattern

import java.util.*;

public class Pattern
{
	//attributes for the pattern.
	private ArrayList<Attribute> attributes;
	//class label determines which class the pattern belongs to.
	private int classLabel;
	
	//Constructor
	public Pattern(int classLabel, ArrayList<Attribute> attributes)
	{
		this.attributes = attributes;
		this.classLabel = classLabel;
	} // Pattern
	
	public Attribute getAttributeAtIndex(int index)
	{
		return attributes.get(index);
	} // getAttributeAtIndex
	
	public ArrayList<Attribute> getAttributes()
	{
		return attributes;
	} // getAttributes
	
	public void setAttributes(ArrayList<Attribute> attributes)
	{
		this.attributes = attributes;
	} // setAttributes
	
	public int getClassLabel()
	{
		return classLabel;
	} // getClassLabel
	
	public void setClassLabel(int classLabel)
	{
		this.classLabel = classLabel;
	} // setClassLabel
	
	public String toString()
	{
		String result = "Class: " + classLabel;
		
		for (Attribute attribute : attributes)
			result += attribute;
			
		return result;
	} // toString
} // class Pattern