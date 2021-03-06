
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

class Author {
	
	protected String name;
	protected List<Book> books;
	protected List<String> commonTopWords;
	protected double averageSentenceLength;
	protected double averagePunctuationDensity;
	protected double averageParagraphLength;
	
	protected Set<String> Dictionary;
	
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
		Dictionary = null;
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
			commonTopWords.addAll(book.getTopNWords(20));
		}
		averageSentenceLength = wordCount/sentenceCount;
		averagePunctuationDensity = punctuationCount/wordCount;
		averageParagraphLength = wordCount/paragraphCount;
		Dictionary=getDictionary();
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
		double diffCommonWords = intersection(this.commonTopWords,a.commonTopWords);
		return Math.sqrt(Math.pow(diffCommonWords, 2)+Math.pow(diffParagraphLength, 2)+Math.pow(diffSentenceLength, 2)+Math.pow(diffPunctuationDensity, 2));
	}
	
	/*
	 * Function: getSquares
	 * Input : Real number a
	 * Output : a*a
	 * 
	 */
	protected double getSquares(double a) {
		return a*a;
	}
	

	/*
	 * Function: intersection
	 * Input : Two Lists
	 * Output : Common elements of the two lists
	 * 
	 */
	
	public int intersection(List<String> list1, List<String> list2) {
        int count=0;
        for (String t : list1) {
            if(list2.contains(t)) {
                count++;
            }
        }
        return count;
    }
	
	/*
	 * Function: getDictionary
	 * Input : None
	 * Output : Returns the dictionary of words for the author
	 * 
	 */
	
	public Set<String> getDictionary() {
        Set<String> wordSet = new HashSet<>();
		for(Book book : books) {
			wordSet.addAll(book.wordFrequency.keySet());
		}
        return wordSet;
    }

	

}