import java.io.File;
import java.io.FileInputStream;
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

		final String filenames[] = {"utils/WarAndPeace.txt","utils/AnnaKarenina.txt","utils/Resurrection.txt","utils/Hamlet.txt","utils/Julius Caesar.txt","utils/Macbeth.txt","utils/Othello.txt","utils/Romeo Juliet.txt"};
		final String authorname[] = {"Tolstoy","Tolstoy","Tolstoy","Shakespeare","Shakespeare","Shakespeare","Shakespeare","Shakespeare"};
		int i=0;
		for(String filename: filenames) {
			Book b=new Book(filename,authorname[i]);
			i++;
			b.debug();
		}
	}
}
