import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class Book {
	
	private List<String> book;
	
	public Book() {
		book = null;
	}
	public Book(String filename) throws IOException {
		book = readFile(filename);
	}
	
	/*
	 * Function: readFile
	 * Input : Path of the file (String)
	 * Output : List of Strings containing all lines of the file.
	 * 
	 */
	private static List<String> readFile(String filePath) throws IOException{
		List<String> book = new ArrayList<>();
		book = (List<String>) Files.readAllLines(Paths.get(filePath));
		return book;
	}
	
	/*
	 * Function: getTopNWords
	 * Input : Map with word frequence (Map), Number of words to give as output (Integer)
	 * Output : List of Strings top N words.
	 * 
	 */
	private List<String> getTopNWords(Map<Integer,List<String>> wordFrequency, int N) {
		return null;
	}
	
	/*
	 * Function: getWordFrequency
	 * Input : All lines of the file (List<String>)
	 * Output : Mapping of word to their frequency
	 * 
	 */
	private Map<String,Integer> getWordFrequency(List<String> book) {
		return null;
	}
	
	/*
	 * Function: readFile
	 * Input : Path of the file (String)
	 * Output : List of Strings containing all lines of the file.
	 * 
	 */
	private Integer getAverageSentenceLength(List<String> book) {
		return 0;
	}
}