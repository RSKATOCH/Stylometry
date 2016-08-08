import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Stylometry {
	private final static int BookCount = 3;
	public static List<String> readFile(String filePath) throws IOException{
		List<String> book = new ArrayList<>();
		book = (List<String>) Files.readAllLines(Paths.get(filePath));
		return book;
	}
	public static void main(String args[]) {
		
	}
}