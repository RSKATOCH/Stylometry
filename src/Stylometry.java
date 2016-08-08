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
				tolstoy.addBook(b);
			} else if(shakespeare.name.equals(b.authorName)) {
				shakespeare.addBook(b);
			}
		}
		Author unknown = new Author("Unknown");
		String testFileName = "utils/Hamlet.txt";
		Book b = new Book(testFileName,unknown.name);
		unknown.addBook(b);
		
		//debug(tolstoy,shakespeare);
		
		final int THRESHOLD = 10;
		
		System.out.println(getAuthor(unknown,tolstoy,shakespeare,THRESHOLD));
		
	}
	public static String getAuthor(Author test, Author a, Author b, double THRESHOLD) {
		String author = "";
		if(a.similarity(test) < b.similarity(test) && a.similarity(test)<THRESHOLD) {
			return a.name;
		} else if(b.similarity(test)<THRESHOLD) {
			return b.name;
		} else {
			return "Doesn't match authors in corpora";
		}
	}
	public static void debug(Author a, Author b) {
		System.out.println("CORPORA");
		System.out.println(a.name);
		for(Book book : a.books) {
			System.out.println(book.filename + book.getPunctionDensity());
		}
		System.out.println(b.name);
		for(Book book : b.books) {
			System.out.println(book.filename + book.getPunctionDensity());
		}
		
	}
}
