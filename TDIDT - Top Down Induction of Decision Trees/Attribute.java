//The class that describes an attribute and its value

public class Attribute
{
	//id will desribe index of attribute, and value would be 0 or 1.
	private int id;
	private int value;
	
	//default empty constructor
	public Attribute() 
	{
	
	} // Attribute
	
	//Constructor
	public Attribute(int id, int value)
	{
		this.id = id;
		this.value = value;
	} // Attribute
	
	public int getID()
	{
		return id;
	} // getID
	
	public int getValue()
	{
		return value;
	} // getValue
	
	public void setID(int id)
	{
		this.id = id;
	} // setID
	
	public void setValue(int value)
	{
		this.value = value;
	} // setValue
	
	public String toString()
	{
		return " F" + id + ": " + value;
	} // toString
} // class Attribute