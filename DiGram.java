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


		for(Book book : a.books) {

			Map<String,Integer> freqs=new HashMap<String,Integer>();
			String[] tokens;

			for(String s:book.sentences){
				String prevToken = BLANK;
				tokens = tokenizer.tokenize(s);
				for (String token : tokens) {
					/*Remove punctuation */
					//if(!token.toString().matches(",|\\.|\"|!|\'|\\?|\'s|;|:|\'d||\\]|\\[|\\(|\\)|\\}|\\{")) {
					if(!digram.containsKey(prevToken)) {
						digram.put(prevToken,new HashMap<String,Integer>());
					}
					else if (digram.get(prevToken).containsKey(token)) {
						digram.get(prevToken).put(token, digram.get(prevToken).get(token) + 1);

					} else {

						digram.get(prevToken).put(token, 1);
					}
					prevToken = token;
					//}
				}	
			}
		}
		digram.remove(BLANK);
		return digram;
	}
	
	/*
	 * Function: getNextWord
	 * Input : word
	 * Output : most frequent word found after the provided word
	 * 
	 */
	/*public String getNextWord(String word) {
		if(!diGram.get(word).isEmpty()) {
			String s=FrequentWords.mostFrequentWords(1,diGram.get(word)).get(0);
				return s;
		}
		return null;
	}*/
	
	public String getNextWord(String word) {
		if(!diGram.get(word).isEmpty()) {
			double p = Math.random();
			double cumulativeProbability = 0.0;
			for (Map.Entry<String, Integer> item : diGram.get(word).entrySet()) {
				cumulativeProbability += item.getValue();
				if (p <= cumulativeProbability) {
					return item.getKey();
				}
			}
		}
		return null;

	}
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
	
	/*
	 * Function: debug
	 * Input : none
	 * Output : Sample input provided which is checked
	 * 
	 */
	public static void debug() throws InvalidFormatException, IOException{
		final String filenames[] = {"utils/WarAndPeace.txt","utils/AnnaKarenina.txt","utils/Resurrection.txt"};
		final String authorname[] = {"Tolstoy","Tolstoy","Tolstoy"};
		int i=0;
		Author tolstoy = new Author("Tolstoy");
		for(String filename: filenames) {
			Book b=new Book(filename,authorname[i]);
			i++;
			if(tolstoy.name.equals(b.authorName)) {
				tolstoy.addBook(b);
			}
		}
		tolstoy.getDictionary();
		
		DiGram gram = new DiGram(tolstoy);
		int counter=0;
		for(Map.Entry<String, Map<String,Integer>> entry : gram.diGram.entrySet()) {
			System.out.println("------------------");
			System.out.println(entry.getKey());
			for(Map.Entry<String,Integer> entryIn : entry.getValue().entrySet()) {
				System.out.println(entryIn.getKey()+" "+entryIn.getValue());
			}
			System.out.println("Most Frequent:" + gram.getNextWord(entry.getKey()) );
			counter++;
			if(counter>10) {
				break;
			}
		}
		
		System.out.println("---------------------------------------------------");
		System.out.println("20 sentences");
		/*
		for(int j=0;j<tolstoy.averageParagraphLength;j++) {
			for(int i1=0;i1<tolstoy.averageSentenceLength;i1++) {

			}
		}*/
	}

	/*
	 * Tester main
	 */
	public static void main(String[] args) throws IOException {
		debug();
	}
	
}