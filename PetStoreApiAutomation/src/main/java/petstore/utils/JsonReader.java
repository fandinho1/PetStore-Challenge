package petstore.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.Gson;

public class JsonReader {

	private static final String DATA_PATH = "src/test/resources/data/";

	public static <T> T readJsonAsClass(String fileName, Class<T> className) {
		Gson gson = new Gson();

		try {
			FileReader reader = new FileReader(DATA_PATH.concat(fileName));
			return gson.fromJson(reader, className);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}
