import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

class GroceryListController {
		
	public static final String groceryListFilename = "groceryList.list";

	private FileOutputStream oStream;
	private FileInputStream iStream;
	private BufferedReader bReader;

	public GroceryListController(){
		try{
			oStream = openFileOutput(groceryListFilename, Context.MODE_PRIVATE); //open an output file, an android method that won't build on windows
			iStream = openFileInput(groceryListFilename);
		} catch(IOException e){
			e.printStackTrace();
		}
		bReader = new BufferedReader(new InputStreamReader(iStream));
	}

	public String getLine(int i){
		for (int j = 0; j < i - 1; ++j) {
			bReader.readLine();
		}
		return bReader.readLine();
	}


}