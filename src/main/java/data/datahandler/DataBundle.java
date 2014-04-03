package main.java.data.datahandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.data.jsonhandler.JSONParser;
import main.java.data.jsonhandler.JSONSerializer;

/**
 * DataBundles are designed to store lists of objects mapped to the types of 
 * objects contained in the list.
 * Therefore, it is important to store information within objects rather than
 * just adding random pieces of data (ints, doubles, Strings, etc.) to the 
 * DataBundle since there won't be any way to discern the purposes of primitive
 * data types when they are stored in the map.
 * @author In-Young Jo
 *
 */
public class DataBundle {

	//dataMap maps lists to the classes of the objects that the lists are storing
	//NOTE: this assumes that lists hold uniform data
	private Map<Class<?>, List<Object>> dataMap;

	public DataBundle()	{
		dataMap = new HashMap<Class<?>, List<Object>>();
	}

	/**
	 * Method to add an object or object list to the dataBundle
	 * @param obj
	 */
	public void add(Object obj)	{
		
		List<Object> tempList = new ArrayList<Object>();
		
		//If obj = list, add all objects to tempList
		if(obj instanceof List<?>)	{
			for(int i = 0; i < ((List<?>) obj).size(); i++)	{
				tempList.add(((List<?>) obj).get(i));
			}
		}
		
		//If obj isn't a collection just add it to tempList
		else	{
			tempList.add(obj);
		}
		
		//Map tempList to the class of the objects the List stores
		//If there isn't a list containing the type of object in tempList:
		if(!dataMap.keySet().contains(tempList.get(0).getClass()))	{
			dataMap.put(obj.getClass(), tempList);
		}
		//If there is already a list containing the type of object in tempList:
		else	{
			tempList.addAll(dataMap.get(tempList.get(0).getClass()));
			dataMap.put(tempList.get(0).getClass(), tempList);
		}
	}
	
	/**
	 * Get the list associated with the type of object passed as a parameter
	 * @param obj
	 * @return
	 */
	public List<Object> getList(Object obj)	{
		List<Object> output = new ArrayList<Object>();
		if(dataMap.containsKey(obj.getClass()))	{
			output = dataMap.get(obj.getClass());
		}
		return output;
	}
	
	/**
	 * Returns the dataMap of the DataBundle
	 * @return
	 */
	public Map<Class<?>, List<Object>> getDataMap()	{
		return dataMap;
	}
	
	/**
	 * Saves the current DataBundle with the provided filename
	 * @param filename
	 * @throws FileNotFoundException 
	 */
	public void saveDataBundle(String filename) throws FileNotFoundException	{
		JSONSerializer s = new JSONSerializer();
		//TODO: develop JSONSerializer and implement methods here
		s.write(filename, this);
	}
	
	/**
	 * Fills the DataBundle with information retrieved from the file with the
	 * provided filename
	 * @param filename
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public void loadDataBundle(String filename) throws IOException, ClassNotFoundException	{
		JSONParser p = new JSONParser();
		//TODO: develop JSONParser and implement methods here
		p.readFile(filename);
	}
	
	public static void main(String[] args)	{
		DataBundle d = new DataBundle();
		d.add("a");
		d.add(1);
		List<String> l = new ArrayList<String>();
		l.add("b");
		l.add("c");
		d.add(l);
		System.out.println(d.getDataMap());
		System.out.println(d.getList(new String()));
	}
}
