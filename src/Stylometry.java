
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
				tolstoy.addBook(b);
			} else if(shakespeare.name.equals(b.authorName)) {
				shakespeare.addBook(b);
			}
		}
		
		Author unknown = new Author("Unknown");
		String testFileName = "utils/WarAndPeace.txt";
		Book b = new Book(testFileName,unknown.name);
		unknown.addBook(b);
		
		//debug(tolstoy,shakespeare);
		
		final double THRESHOLD = 25.0;
		
		//System.out.println(unknown.similarity(tolstoy)+" "+unknown.similarity(shakespeare));
		String author = "";
		//System.out.println(a.similarity(test) + b.similarity(test));
		if(unknown.similarity(tolstoy) < unknown.similarity(shakespeare) && unknown.similarity(tolstoy)<THRESHOLD) {
			author= tolstoy.name;
		} else if(unknown.similarity(shakespeare)<THRESHOLD) {
			author = shakespeare.name;
		} else {
			author = "Doesn't match authors in corpora";
		}
		
		System.out.println(author);
		
		authorAverage.add(tolstoy);
		authorAverage.add(shakespeare);
		
		Plot p=new Plot();
		p.init(booksTolstoy,booksShakes,authorAverage);
		
	}
	public static String getAuthor(Author test, Author a, Author b, double THRESHOLD) {
		return null;
	}
	public static void debug(Author a, Author b) {
		System.out.println("CORPORA");
		System.out.println(a.name);
		for(Book book : a.books) {
			System.out.println(book.filename + book.getPunctuationDensity());
		}
		System.out.println(b.name);
		for(Book book : b.books) {
			System.out.println(book.filename + book.getPunctuationDensity());
		}
    
	}
}
