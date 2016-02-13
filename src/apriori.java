import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class apriori {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader reader = new BufferedReader(new FileReader(args[0]));
		
		double min_support = Double.parseDouble(args[1]);
		double min_confidence = Double.parseDouble(args[2]);
		
		int numRows = 0;
		int numAttributes = 0;
		
		Object [][] data = new Object[500][50];
		Attribute[] columnNames = new Attribute[50];
		
		
		while(reader.ready()){
			String line = reader.readLine();
			if(line.startsWith("@relation")){
				continue;
			}else if(line.startsWith("@attribute")){
				
				Attribute attr = new Attribute(line.split(" ")[1]);
				String[] potValues = line.split(" ")[2].replaceAll("{", "").replaceAll("}", "").split(",");
				attr.potentialValues = new ArrayList<String>(Arrays.asList(potValues));
				attr.attributeName = line.split(" ")[1];
				
				columnNames[numAttributes] = attr;
				numAttributes++;
				
			}else if(line.startsWith("@data")){
				continue;
			}else if(line.isEmpty()){
				continue;
			}else{
				String[] rowValues = line.split(",");
				for(int i = 0; i < rowValues.length; i++){
					data[numRows][i] = rowValues[i];
					
				}
				numRows++;			
			}
		}
		
		//Construct C1
		
		
		
		System.out.println("Number of Rows: " + numRows);
		System.out.println("Number of Attributes: " + numAttributes);
		System.out.print(data[400][16]);
		
		}
	
	
	public class Itemset {
		String attributeName;
		int count;
	}
	
	public static class Attribute {
		
		public Attribute(String attrName){
			attributeName = attrName;
			potentialValues = new ArrayList<String>();
		}
		
		String attributeName;
		ArrayList<String> potentialValues;
	}

}
