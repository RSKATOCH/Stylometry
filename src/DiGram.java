import java.util.List;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;

class DiGram {

	protected Map<String,Map<String,Integer>> diGram;

	/*	Constructors
	 *	parameterized constructor for file reading and initialization
	 */
	public DiGram(Author a) throws InvalidFormatException, IOException {
		diGram = getDiGramFrequencyTable(a);
	}


	public static String BLANK = ""; 

	/*
	 * Function: getDiGramFrequencyTable
	 * Input : Author a whose frequency table we need to create
	 * Output : Mapping from previous word to next word along with its frequency
	 * 
	 */
	public Map<String,Map<String,Integer>> getDiGramFrequencyTable(Author a) throws InvalidFormatException, IOException {
		Map<String,Map<String,Integer>> digram = new HashMap<>();

		InputStream is = new FileInputStream("utils/en-token.bin");

		TokenizerModel model = new TokenizerModel(is);

		Tokenizer tokenizer = new TokenizerME(model);

		final String START = "START!";
		final String STOP = "STOP!";

		for(Book book : a.books) {

			Map<String,Integer> freqs=new HashMap<String,Integer>();
			String[] tokens;

			for(String s:book.sentences){
				String prevToken = START;
				tokens = tokenizer.tokenize(s);
				for (String token : tokens) {
					/*Remove punctuation */
					updateMap(digram,prevToken,token);
					prevToken = token;

				}
				updateMap(digram,prevToken,STOP);

			}
		}
		return digram;
	}

	/*
	 * Function: updateMap
	 * Input : digram map, previous String and String
	 * Output : updated map with frequencies added
	 * 
	 */

	public void updateMap(Map<String,Map<String,Integer>> digram, String prevToken, String token) {
		//if(!token.toString().matches(",|\\.|\"|!|\'|\\?|\'s|;|:|\'d||\\]|\\[|\\(|\\)|\\}|\\{")) {
		if(!digram.containsKey(prevToken)) {
			digram.put(prevToken,new HashMap<String,Integer>());
		} else if (digram.get(prevToken).containsKey(token)) {
			digram.get(prevToken).put(token, digram.get(prevToken).get(token) + 1);

		} else {

			digram.get(prevToken).put(token, 1);
		}
		//}

	}

	/*
	 * Function: getStartList
	 * Input : Mapping
	 * Output : List of words that come after Start
	 * 
	 */

	public List<String> getStartList(Map<String,Map<String,Integer>> digram, String START) {
		List<String> startList = new ArrayList<>();
		if(digram.containsKey(START) && !digram.get(START).isEmpty())
			startList.addAll(digram.get(START).keySet());
		return startList;
	}
	
	/*
	 * Function: getStartList
	 * Input : Mapping
	 * Output : List of words that come after Start
	 * 
	 */

	public List<String> getStopList(Map<String,Map<String,Integer>> digram, String STOP) {
		List<String> stopList = new ArrayList<>();
		for(Map.Entry<String,Map<String,Integer>> entry : digram.entrySet()) {
			if(entry.getValue().containsKey(STOP))
				stopList.add(entry.getKey());
			
		}
		stopList.remove(",");
		return stopList;
	}

	/*
	 * Function: getNextWord
	 * Input : word
	 * Output : next word found after the provided word based on probability distribution
	 * 
	 */

	public String getNextWord(String word) {
		if(!diGram.get(word).isEmpty()) {
			double p = Math.random();
			int totalFrequency=0;
			double cumulativeProbability = 0.0;
			for (Map.Entry<String, Integer> item : diGram.get(word).entrySet()) {
				totalFrequency += Integer.parseInt(item.getValue().toString());
			}
			for (Map.Entry<String, Integer> item : diGram.get(word).entrySet()) {
				cumulativeProbability += Integer.parseInt(item.getValue().toString());
				if (p <= cumulativeProbability/totalFrequency) {
					return item.getKey();
				}
			}
		}
		return null;
	}

	/*
	 * This one is for most frequent word
	 * 
	public String getNextWord(String word) {
		if(!diGram.get(word).isEmpty()) {
			String s=FrequentWords.mostFrequentWords(1,diGram.get(word)).get(0);
				return s;
		}
		return null;
	}
	 */

	/*
	 * Function: getRandomWord
	 * Input : Author A
	 * Output : most frequent word found after the provided word
	 * 
	 */
	public String getRandomWord(Author a) {

		int size = a.Dictionary.size();
		int item = new Random().nextInt(size); // In real life, the Random object should be rather more shared than this
		int i = 0;
		for(String obj : a.Dictionary)
		{
			if (i == item)
				return obj;
			i = i + 1;
		}
		return null;
	}

	public static Author getBooks() throws IOException {
		final String filenames[] = {"utils/WarAndPeace.txt","utils/AnnaKarenina.txt","utils/Resurrection.txt"};
		final String authorname[] = {"Tolstoy","Tolstoy","Tolstoy"};
		int i=0;
		Author author = new Author("Tolstoy");
		for(String filename: filenames) {
			Book b=new Book(filename,authorname[i]);
			i++;
			if(author.name.equals(b.authorName)) {
				author.addBook(b);
			}
		}
		author.getDictionary();
		return author;
	}

	/*
	 * Function: debug
	 * Input : none
	 * Output : Sample input provided which is checked
	 * 
	 */
	public static void debug() throws InvalidFormatException, IOException{

		Author tolstoy = getBooks();
		DiGram gram = new DiGram(tolstoy);
		int counter=0;
		for(Map.Entry<String,Integer> entry : gram.diGram.get("START!").entrySet()) {
			System.out.println("------------------");
			System.out.println(entry.getKey());
			System.out.println("Most Frequent:" + gram.getNextWord(entry.getKey()) );
		}
		

	}

	/*
	 * Tester main
	 */
	public static void main(String[] args) throws IOException {
		debug();
	}

}