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
import java.util.Set;
import java.util.TreeMap;

import com.sun.media.sound.InvalidFormatException;

import opennlp.tools.sentdetect.SentenceDetector;
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
	private int punctuationCount;
	private double punctuationDensity;
	Map<String,Integer> wordFrequency;
	
	/*
	 * 	class : Paragraph
	 * 	Usage : Paragraph can have multiple property so to incorporate that
	 * 
	 */
	private class Paragraph {
		int wordCount;
		int sentenceCount;
		int letterCount;
		String para;
	} 
	
	private List<Paragraph> paras;
	
	/*	Constructors
	 * 	default constructor for initialization
	 *	parameterized constructor for file reading and initialization
	 */
	public Book() {
		book = null;
		sentences = null;
		sentenceCount = 0;
		wordCount = 0;
		wordFrequency=null;
		paras = null;
	}
	
	public Book(String filename) throws IOException {
		book = readFile(filename);
		sentences = SentenceDetect(book);
		paras = getParas(book);
		sentenceCount=sentences.length;
		wordFrequency=getWordFrequency(sentences); 
		wordCount = calculateWordCount(wordFrequency);
		punctuationCount=getPunctuationCount(book);
		punctuationDensity=getPunctionDensity();
		//System.out.println(punctuationDensity);
	}

	/*
	 * Function: getSentenceList
	 * Input : None
	 * Output : List of Strings containing all lines of the file.
	 * 
	 */
	protected String getBook() {
		return this.book;
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
	 * Function: getWordCount
	 * Input : None
	 * Output : Word Count of this book
	 * 
	 */
	private double getPunctionDensity() {
		return (double)this.punctuationCount/this.wordCount;
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
	private List<String> getTopNWords(int N) {
		return FrequentWords.mostFrequentWords (N, wordFrequency);
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
	 * Function: calculateWordCount
	 * Input : Map with word frequency (Map)
	 * Output : Count of number of words in file
	 * 
	 */
	private int calculateWordCount(Map<String,Integer> map) {
		int count = 0;
		for(Map.Entry<String, Integer> entry : map.entrySet()) {
			count+=entry.getValue();
		}
		return count;
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

	
	/*
	 * Function: getParas
	 * Input : The book variable containing all the lines
	 * Output : List of strings that are the paragraphs of the book
	 * 
	 */
	private List<Paragraph> getParas(String data) throws InvalidFormatException, IOException {
		
		List<Paragraph> paraList = new ArrayList<>();
		for(String para: this.getBook().split(("\\r?\\n(\\r?\\n)+"))) {
			Paragraph p = new Paragraph();
			p.para = para;
			p.letterCount=p.para.length();
			for(String word : para.split(" ")) {
				p.wordCount++;
			}
			p.sentenceCount=SentenceDetect(para).length;
			paraList.add(p);
		}
		
		return paraList;
	}
	
	

	/*
	 * Function: getPunctionCount
	 * Input : Path of the file (String)
	 * Output :Number of punctuation used
	 * 
	 */
   	 
	private Integer getPunctuationCount(String data){
		int punc=0;
		 for (int a = 0; a < data.length(); a++) {
	            char c = data.charAt(a);
	            if (!Character.isLetterOrDigit(c) && !Character.isWhitespace(c)) {
	                punc++;
	            }
		 }
		return punc;
	}

}