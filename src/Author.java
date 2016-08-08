package src;
import java.util.List;
import java.util.ArrayList;

class Author {
	
	protected String name;
	protected List<Book> books;
	protected List<String> commonTopWords;
	protected double averageSentenceLength;
	protected double averagePunctuationDensity;
	protected double averageParagraphLength;
	/*	Constructors
	 *	parameterized constructor for file reading and initialization
	 */
	public Author(String name) {
		this.name = name;
		this.books = new ArrayList<>();
		
		commonTopWords = new ArrayList<String>();
		averageSentenceLength = 0;
		averagePunctuationDensity = 0;
		averageParagraphLength = 0;	
	}
	
	/*
	 * Function: addBook
	 * Input : Book b
	 * Output : None
	 * 
	 */
	protected void addBook(Book b) {
		books.add(b);
		
		int sentenceCount = 0;
		int wordCount = 0;
		int paragraphCount = 0;
		int punctuationCount = 0;
		int charCount = 0;
		for(Book book: books) {
			charCount = book.getBook().length();
			sentenceCount += book.getSentenceCount();
			wordCount += book.getWordCount();
			paragraphCount+=book.getParaList().size();
			punctuationCount+=book.getPunctuationCount(book.getBook());
		}
		averageSentenceLength = wordCount/sentenceCount;
		averagePunctuationDensity = punctuationCount/wordCount;
		averageParagraphLength = wordCount/paragraphCount;
	}

	/*
	 * Function: similarity
	 * Input : Author a
	 * Output : Euclidean distance between authors based on metrics
	 * 
	 */
	protected double similarity(Author a) {
		double similarity = 0;
		
		double diffSentenceLength = Math.abs(this.averageSentenceLength-a.averageSentenceLength);
		double diffParagraphLength = Math.abs(this.averageParagraphLength-a.averageParagraphLength);
		double diffPunctuationDensity = Math.abs(this.averagePunctuationDensity-a.averagePunctuationDensity);
		
		return Math.sqrt(Math.pow(diffParagraphLength, 2)+Math.pow(diffSentenceLength, 2)+Math.pow(diffPunctuationDensity, 2));
	}
	
}