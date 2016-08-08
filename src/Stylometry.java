package src;

import java.io.IOException;
import java.util.ArrayList;

class Stylometry {
	
	private final static int BookCount = 3;
	
	public static void main(String args[]) throws IOException {


		ArrayList<Book> booksTolstoy=new ArrayList<Book>();
		ArrayList<Book> booksShakes=new ArrayList<Book>();
		ArrayList<Author> authorAverage=new ArrayList<Author>();
		
		final String filenames[] = {"utils/WarAndPeace.txt","utils/AnnaKarenina.txt","utils/Resurrection.txt","utils/Julius Caesar.txt","utils/Macbeth.txt","utils/Othello.txt","utils/Romeo Juliet.txt"};
		final String authorname[] = {"Tolstoy","Tolstoy","Tolstoy","Shakespeare","Shakespeare","Shakespeare","Shakespeare","Shakespeare"};
		int i=0;
		Author tolstoy = new Author("Tolstoy");
		Author shakespeare = new Author("Shakespeare");
		
		
		for(String filename: filenames) {
			Book b=new Book(filename,authorname[i]);
			if(authorname[i].equals("Tolstoy"))
				booksTolstoy.add(b);
			else
				booksShakes.add(b);
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
		
		authorAverage.add(tolstoy);
		authorAverage.add(shakespeare);
		
		Plot p=new Plot();
		p.init(booksTolstoy,booksShakes,authorAverage);
	    
	}
}
