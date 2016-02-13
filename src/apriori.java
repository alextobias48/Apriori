import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class apriori {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader reader = new BufferedReader(new FileReader(args[0]));
		
		double min_support = Double.parseDouble(args[1]);
		double min_confidence = Double.parseDouble(args[2]);
		
		while(reader.ready()){
			String line = reader.readLine();
			if(line.startsWith("@relation")){
				
			}else if(line.startsWith("@attribute")){
				
			}else if(line.startsWith("@data")){
				
			}else if(line.isEmpty()){
				
			}else{
				
			}
			
		}
	}

}
