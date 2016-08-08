package src;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

	protected String filename;
	protected String authorName;
	private String book;
	private String[] sentences;
	private int sentenceCount;
	private int wordCount;
	private int punctuationCount;
	private double punctuationDensity;
	
	private final static String TRAINING_FILE = "utils/en-sent.bin";
	
	
	SentenceModel model ;
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
	public Book() throws opennlp.tools.util.InvalidFormatException, IOException {
		filename = null;
		authorName = null;
		book = null;
		sentences = null;
		sentenceCount = 0;
		wordCount = 0;
		wordFrequency=null;
		paras = null;
		InputStream is = new FileInputStream(TRAINING_FILE);
		model = new SentenceModel(is);
		is.close();
	}
	
	public Book(String filename, String authorName) throws IOException {
		
		this.filename = filename;
		this.authorName = authorName;
		book = readFile(filename);
		
		InputStream is = new FileInputStream(TRAINING_FILE);
		model = new SentenceModel(is);
		is.close();
		
		sentences = SentenceDetect(book);
		paras = getParas(book);
		
		sentenceCount=sentences.length;
		wordFrequency=getWordFrequency(sentences); 
		wordCount = calculateWordCount(wordFrequency);
		
		punctuationCount=getPunctuationCount(book);
		punctuationDensity=getPunctuationDensity();
		
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
	protected String[] getSentenceList() {
		return this.sentences;
	}

	/*
	 * Function: getSentenceCount
	 * Input : None
	 * Output : Sentence Count of this book
	 * 
	 */
	protected int getSentenceCount() {
		return this.sentenceCount;
	}

	/*
	 * Function: getWordCount
	 * Input : None
	 * Output : Word Count of this book
	 * 
	 */
	protected int getWordCount() {
		return this.wordCount;
	}

	/*
	 * Function: getWordCount
	 * Input : None
	 * Output : Word Count of this book
	 * 
	 */
	protected double getPunctuationDensity() {
		return (double)this.punctuationCount/this.wordCount;
	}
	
	/*
	 * Function: getParaList
	 * Input : None
	 * Output : List of paragraphs in this book
	 * 
	 */
	protected List<Paragraph> getParaList() {
		return this.paras;
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
				 /*Remove punctuation */
				 if(!token.toString().matches(",|\\.|\"|!|\'|\\?|\'s|;|:|\'d||\\]|\\[|\\(|\\)|\\}|\\{")) {
				 		if (freqs.containsKey(token)) {
				 			freqs.put(token, freqs.get(token) + 1);
				 		} else {
				 			freqs.put(token, 1);
				 		}
				 }
			 }	
		}
		return freqs;
	}

	/*
	 * Function: getAverageSentenceLengthChars
	 * Input : Variable containing the entire file
	 * Output : Average number of characters in each sentence
	 * 
	 */
	protected int getAverageSentenceLengthChars() {
		return this.book.length()/this.sentenceCount;
	}
	
	/*
	 * Function: getAverageSentenceLengthWord
	 * Input : Variable containing the entire file
	 * Output : List of Strings containing all lines of the file.
	 * 
	 */
	private int getAverageSentenceLengthWord(String book) {
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
		
		SentenceDetectorME sdetector = new SentenceDetectorME(model);

		String sentences[] = sdetector.sentDetect(data);

		//System.out.println(sentences.length);
		
		
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
	 * Function: getAverageParaLengthChars
	 * Input : None
	 * Output : No of characters/count of paragraphs
	 * 
	 */
	protected int getAverageParaLengthChars()	{
		
		int count=0;
		for(Paragraph para : paras) {
			count+=para.para.length();
		}
		
		return count/paras.size();
	}
	
	/*
	 * Function: getAverageParaLengthWords
	 * Input : None
	 * Output : No of words/count of paragraphs
	 * 
	 */
	private int getAverageParaLengthWord()	{
		
		int count=0;
		for(Paragraph para : paras) {
			count+=para.wordCount;
		}
		
		return count/paras.size();
	}
	
	/*
	 * Function: getAverageParaLengthWords
	 * Input : None
	 * Output : No of sentences/count of paragraphs
	 * 
	 */
	private int getAverageParaLengthSentences()	{
		
		int count=0;
		for(Paragraph para : paras) {
			count+=para.sentenceCount;
		}
		
		return count/paras.size();
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
	
	/*
	 * Function: debug
	 * Input : None
	 * Output : Debug log stating Filename, word Count, Sentence Count, Paragraph Count etc
	 * 
	 */
	public void debug() {
		final String Boundary = "-----------------------------";
		System.out.println(Boundary);
		System.out.println("Filename = "+filename);
		System.out.println("Filename = "+authorName);
		
		System.out.println("Word Count Total = "+getWordCount());
		System.out.println("Sentence Count Total = "+getSentenceCount());
		System.out.println("Paragraph Count = "+paras.size());
		
		System.out.println("Average Sentence Length (Characters) = "+getAverageSentenceLengthChars());
		System.out.println("Average Sentence Length (Words) = "+getAverageSentenceLengthWord(book));
		System.out.println("Punctuation Density = "+punctuationDensity);
		System.out.println("Average Paragraph Length (Characters) = "+getAverageParaLengthChars());
		System.out.println("Average Paragraph Length (Words) = "+getAverageParaLengthWord());
		System.out.println("Average Paragraph Length (Sentences) = "+getAverageParaLengthSentences());
		
		System.out.println("Top 20 words");
		for(String word : getTopNWords(20)) {
			System.out.print(word+" ");
		}
		System.out.println();
	}

}