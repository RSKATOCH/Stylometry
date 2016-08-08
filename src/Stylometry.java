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

		final String filenames[] = {"utils/WarAndPeace.txt","utils/AnnaKarenina.txt","utils/Resurrection.txt","utils/Julius Caesar.txt","utils/Macbeth.txt","utils/Othello.txt","utils/Romeo Juliet.txt"};
		final String authorname[] = {"Tolstoy","Tolstoy","Tolstoy","Shakespeare","Shakespeare","Shakespeare","Shakespeare","Shakespeare"};
		int i=0;
		Author tolstoy = new Author("Tolstoy");
		Author shakespeare = new Author("Shakespeare");
		for(String filename: filenames) {
			Book b=new Book(filename,authorname[i]);
			i++;
			if(tolstoy.name.equals(b.authorName)) {
				tolstoy.books.add(b);
			} else if(shakespeare.name.equals(b.authorName)) {
				shakespeare.books.add(b);
			}
		}
		Author unknown = new Author("Unknown");
		String testFileName = "utils/Hamlet.txt";
		Book b = new Book(testFileName,unknown.name);
		unknown.addBook(b);
		
		final int THRESHOLD = 20;
		
		System.out.println(tolstoy.similarity(unknown) + " " + shakespeare.similarity(unknown));
		
		if(tolstoy.similarity(unknown) > shakespeare.similarity(unknown) && shakespeare.similarity(unknown)<THRESHOLD) {
			System.out.println("Tolstoy");
		} else if(shakespeare.similarity(unknown)<THRESHOLD) {
			System.out.println("Shakespeare");
		} else {
			System.out.println("Outside threshold, unknown.");
		}
	}
}
