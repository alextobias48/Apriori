import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class apriori {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader reader = new BufferedReader(new FileReader(args[0]));

		double min_support = Double.parseDouble(args[1]);
		double min_confidence = Double.parseDouble(args[2]);

		int numRows = 0;
		int numAttributes = 0;
		Double count = 0.0;

		Object[][] data = new Object[500][50];
		Attribute[] columnNames = new Attribute[50];

		while (reader.ready()) {
			String line = reader.readLine();
			if (line.startsWith("@relation")) {
				continue;
			} else if (line.startsWith("@attribute")) {

				Attribute attr = new Attribute(line.split(" ")[1]);
				String[] potValues = line.split(" ")[2].replaceAll("\\{", "").replaceAll("\\}", "").split(",");
				attr.potentialValues = new ArrayList<String>(Arrays.asList(potValues));
				attr.attributeName = line.split(" ")[1];

				columnNames[numAttributes] = attr;
				// potentialValues[numPotential]= potValues;

				numAttributes++;

			} else if (line.startsWith("@data")) {
				continue;
			} else if (line.isEmpty()) {
				continue;
			} else {
				String[] rowValues = line.split(",");
				for (int i = 0; i < rowValues.length; i++) {
					data[numRows][i] = rowValues[i];

				}
				numRows++;
			}
		}


		Map<String, Double> C1 = new HashMap<String, Double>();

		for (int a = 0; a < numAttributes; a++) {
			for (int j = 0; j < columnNames[a].potentialValues.size(); j++) {
				
				for (int b = 0; b < numRows; b++) {

				
					if ((data[b][a]).toString().equalsIgnoreCase(columnNames[a].potentialValues.get(j))) {
						count++;

						//Integer x = C1.get(columnNames[a].potentialValues.get(j));
						//if (x != null) {
							//C1.put(columnNames[a].potentialValues.get(j), x+count);
						//} else 
						//{
							//C1.put(columnNames[a].potentialValues.get(j), count);
						//}

					}
					
				}
				C1.put(columnNames[a].attributeName + ":" + columnNames[a].potentialValues.get(j), count);
					count = 0.0;


			}

		}
		
		Map<String, Double> L1 = new HashMap<String, Double>();

		
		for (Map.Entry<String, Double> entry : C1.entrySet()) {
			if(entry.getValue() / numRows > min_support){
				L1.put(entry.getKey(), entry.getValue());
			}
		}
		
		Map<String, Double> C2 = new HashMap<String, Double>();
		
		for(Map.Entry<String, Double> entry : L1.entrySet()){
			for(Map.Entry<String, Double> entry2 : L1.entrySet()){
				count = 0.0;
				if(entry != entry2){
					String colName = entry.getKey().split(":")[0];
					String value = entry.getKey().split(":")[1];
										
					String col2Name = entry2.getKey().split(":")[0];
					String value2 = entry2.getKey().split(":")[1];
					
					int col1 = -1;
					int col2 = -1;
									
					for(int k = 0; k < numAttributes; k++){
						if(columnNames[k].attributeName.equalsIgnoreCase(colName)){
							col1 = k;
						}else if(columnNames[k].attributeName.equalsIgnoreCase(col2Name)){
							col2 = k;
						}
					}
					
					for (int b = 0; b < numRows; b++) {
						
						
						if(String.valueOf(data[b][col1]).equalsIgnoreCase(value)){
							if(String.valueOf(data[b][col2]).equalsIgnoreCase(value2)){
								count++;
							}
						}
						
					}
					
					C2.put(entry.getKey() + ">" + entry2.getKey(), count);
				}
			}
		}
		
		Map<String, Double> L2 = new HashMap<String, Double>();
		
		for (Map.Entry<String, Double> entry : C2.entrySet()) {
			
			if(entry.getValue() / numRows > min_support){
				if((entry.getValue() / numRows) / (L1.get(entry.getKey().split(">")[0]) / numRows) > min_confidence){
					L2.put(entry.getKey(), entry.getValue());
				}
			}
		}

		for(Map.Entry<String, Double> entry : L2.entrySet()){
			System.out.printf("Key : %s and Value: %f %n", entry.getKey(), entry.getValue());

		}
		
		System.out.println("Number of Rows L2: " + L2.size());
		System.out.println("Number of Attributes: " + numAttributes);


	}

	public static class Itemset {

		public Itemset(String attrName, int cnt) {
			attributeName = attrName;
			count = cnt;
		}

		String attributeName;
		int count;
	}

	public static class Attribute {
		public Attribute(String attrName) {
			attributeName = attrName;
			potentialValues = new ArrayList<String>();
		}

		String attributeName;
		ArrayList<String> potentialValues;
	}
}
