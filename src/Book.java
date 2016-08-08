import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.media.sound.InvalidFormatException;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

class Book {

	private String book;
	private String[] sentences;
	private int sentenceCount;
	private int wordCount;
	Map<String,Integer> wordFrequency=new HashMap<String,Integer>();

	/*	Constructors
	 * 	default constructor for initialization
	 *	parameterized constructor for file reading
	 */
	public Book() {
		book = null;
	}
	
	public Book(String filename) throws IOException {
		book = readFile(filename);
		sentences = SentenceDetect(book);
		sentenceCount=sentences.length;
		wordFrequency=getWordFrequency(sentences); 	
	}

	/*
	 * Function: getSentenceList
	 * Input : None
	 * Output : List of Strings containing all lines of the file.
	 * 
	 */
	private String[] getSentenceList() {
		return this.sentences;
	}

	/*
	 * Function: getSentenceCount
	 * Input : None
	 * Output : Sentence Count of this book
	 * 
	 */
	private int getSentenceCount() {
		return this.sentenceCount;
	}

	/*
	 * Function: getWordCount
	 * Input : None
	 * Output : Word Count of this book
	 * 
	 */
	private int getWordCount() {
		return this.wordCount;
	}

	/*
	 * Function: readFile
	 * Input : Path of the file (String)
	 * Output : List of Strings containing all lines of the file.
	 * 
	 */
	public static String readFile(String path) throws IOException{
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		byte[] data = new byte[(int) file.length()];
		fis.read(data);
		fis.close();
		String str = new String(data, "UTF-8");
		return str;
	}

	/*
	 * Function: getTopNWords
	 * Input : Map with word frequency (Map), Number of words to give as output (Integer)
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
	private Map<String,Integer> getWordFrequency(String[] sentences) throws InvalidFormatException, IOException {
		InputStream is = new FileInputStream("utils/en-token.bin");
		 
		TokenizerModel model = new TokenizerModel(is);
	 
		Tokenizer tokenizer = new TokenizerME(model);
		
		Map<String,Integer> freqs=new HashMap<String,Integer>();
		String[] tokens;
		
		for(String s:sentences){
		  tokens = tokenizer.tokenize(s);
			 for (String token : tokens) {
				    if (freqs.containsKey(token)) {
				      freqs.put(token, freqs.get(token) + 1);
				    } else {
				      freqs.put(token, 1);
				    }
			 }	
		}
		return freqs;
	}

	/*
	 * Function: getAverageSentenceLength
	 * Input : Path of the file (String)
	 * Output : List of Strings containing all lines of the file.
	 * 
	 */
	private int getAverageSentenceLength(String book) {
		return wordCount/sentenceCount;
	}
	
	/*
	 * Function: getSentenceDetect
	 * Input : The book variable containing all the lines
	 * Output : List of strings that are the lines of the book
	 * 
	 */
	private String[] SentenceDetect(String data) throws InvalidFormatException,IOException {
		
		// always start with a model, a model is learned from training data
		InputStream is = new FileInputStream("utils/en-sent.bin");
		SentenceModel model = new SentenceModel(is);
		SentenceDetectorME sdetector = new SentenceDetectorME(model);

		String sentences[] = sdetector.sentDetect(data);

		//System.out.println(sentences.length);
		
		is.close();
		return sentences;
	}

	 
}