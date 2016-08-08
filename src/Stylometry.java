package src;

import java.io.IOException;
import java.util.ArrayList;

class Stylometry {
	
	private final static int BookCount = 3;
	
	public static void main(String args[]) throws IOException {

		ArrayList<Book> booksTolstoy=new ArrayList<Book>();
		ArrayList<Book> booksShakes=new ArrayList<Book>();
		final String filenames[] = {"utils/WarAndPeace.txt","utils/AnnaKarenina.txt","utils/Resurrection.txt","utils/Hamlet.txt","utils/Julius Caesar.txt","utils/Macbeth.txt","utils/Othello.txt","utils/Romeo Juliet.txt"};
		final String authorname[] = {"Tolstoy","Tolstoy","Tolstoy","Shakespeare","Shakespeare","Shakespeare","Shakespeare","Shakespeare"};
		int i=0;
		for(String filename: filenames) {
			Book b=new Book(filename,authorname[i]);
			if(authorname[i].equals("Tolstoy"))
				booksTolstoy.add(b);
			else
				booksShakes.add(b);
			i++;
			b.debug();
		}
		Plot p=new Plot();
		p.init(booksTolstoy,booksShakes);
	    
	}
}
