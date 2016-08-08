import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Stylometry {
	
	private final static int BookCount = 3;
	
	public static void main(String args[]) throws IOException {
		final String filename = "C:/GitRepo/Stylometry/utils/WarAndPeace.txt";
		Book b=new Book(filename);
	}
}