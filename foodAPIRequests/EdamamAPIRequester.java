import java.net.URL;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.google.gson.Gson;

public class EdamamAPIRequester {

	private final static String DEFAULT_PORTION = "large";
	private final static int DEFAULT_QUANTITY = 1;
	private final String USER_AGENT = "Mozilla/5.0";
	
	// No constructor, use standard super call to Object.
	
	// Create HTTP Request connection and formulate a GET request.
	private NutritionAPI getNutrition(String food, String portion, int quantity) {
		String url = "https://api.edamam.com/api/nutrition-data?" + 
				"app_id=45f3fb1f&app_key=37c77492528f52024ffdd002d0a5e3d9&ingr=" +
				quantity + "%20" + portion + "%20" + food;
		System.out.println(url);
		URL urlObj = null;
		try {
			urlObj = new URL (url);
		} catch (MalformedURLException e) {
			e.getMessage();
		}
		HttpURLConnection urlCon = null;
		try {
			urlCon = (HttpURLConnection) urlObj.openConnection();
		} catch (IOException e) {
			e.getMessage();
		}

		try {
			urlCon.setRequestMethod("GET");
			urlCon.setRequestProperty("User-Agent", USER_AGENT);
		} catch (ProtocolException e) {
			e.getMessage();
		}
		
		try {
			int responseCode = urlCon.getResponseCode();
			System.out.println("\nResponse Code: " + responseCode + "\n");
		} catch (IOException e) {
			e.getMessage();
		}

		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
		} catch (IOException e) {
			e.getMessage();
		}
		
		// Iterate through API request response and pack into NutritionAPI helper class for return.
		String line;
		StringBuffer getResponse = new StringBuffer();
		try {
			while((line = br.readLine()) != null) getResponse.append(line);
		} catch (IOException e) {
			e.getMessage();
		}
		Gson parser = new Gson();
		return parser.fromJson(getResponse.toString(), NutritionAPI.class);
	}

	private NutritionAPI getNutrition(String food) {
		return this.getNutrition(food, DEFAULT_PORTION, DEFAULT_QUANTITY);
	}

	public static void main(String[] args) {
		EdamamAPIRequester apiReq = new EdamamAPIRequester();
		NutritionAPI getObj = apiReq.getNutrition("apple");

		System.out.println(getObj.getIngredients().toString() + "\n");
		System.out.println(getObj.getDietLabels().toString() + "\n");
		System.out.println(getObj.getTotalDaily().toString() + "\n");
		System.out.println(getObj.getHealthLabels().toString() + "\n");
		System.out.println(getObj.getTotalNutrients().toString() + "\n");
		System.out.println(getObj.getCalories() + "\n");

	}

}
