import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.List;

import opennlp.tools.util.InvalidFormatException;

public class TextGeneration {
	/*
	 * 	Tester main
	 * 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Set<String> Vocabulary=new HashSet<String>();
		
		final int noOfLines = 20;
		
		Author tolstoy = DiGram.getBooks();
		System.out.println("Vocabulary created");
		Vocabulary=tolstoy.Dictionary;
		final  int avgWords=18;
		System.out.println("Starting Paragraph Generation");
		System.out.println("-------------------------------------");
		System.out.println(RandomParagraphGenerationDiGram(Vocabulary,avgWords,noOfLines,tolstoy));
		System.out.println("-------------------------------------");
		System.out.println("Done");
		
		
	}

	/*
	 * Function: RandomParagraphGeneration
	 * Input : Set of strings in all books
	 * Output : Printing the paragraph
	 * 
	 */
	private static String RandomParagraphGenerationDiGram(Set<String> vocabulary, int avgWords, int noOfLines, Author a) throws InvalidFormatException, IOException {
		// TODO Auto-generated method stub
		
		String para="";
		ArrayList<String> keys = new ArrayList<String>(vocabulary);
		Random random = new Random();
		
		
		DiGram di = new DiGram(a);

		List<String> startList = di.getStartList(di.diGram,"START!");
		
		List<String> stopList = di.getStopList(di.diGram,"STOP!");
		
		
		for(int i=0;i<noOfLines;i++)
		{
			String word = startList.get( random.nextInt(startList.size()));
			para+=word+" ";
			boolean flag = false;
			for(int j=0;j<avgWords;j++)
			{
				while(di.getNextWord(word)==null) {
					word=keys.get( random.nextInt(keys.size()));
					para+=word+" ";
					j++;
					if(stopList.contains(word)) {
						flag=true;
						break;
					}
				}
				if(flag) {
					break;
				}
				String nextWord = di.getNextWord(word);
				para+=nextWord+" ";
				word= nextWord;
				if(stopList.contains(word)) {
					break;
				}
			}
			para+="\n";
		}
		return para;
	}
	
	/*
	 * Function: RandomParagraphGeneration
	 * Input : Set of strings in all books
	 * Output : Printing the paragraph
	 * 
	 */
	private static String RandomParagraphGenerationRandom(Set<String> vocabulary, int avgWords, int noOfLines, Author a) throws InvalidFormatException, IOException {
		// TODO Auto-generated method stub
		
		String para="";
		ArrayList<String> keys = new ArrayList<String>(vocabulary);
		Random random = new Random();
		
		String word = keys.get( random.nextInt(keys.size()));
		para+=word+" ";
		DiGram di = new DiGram(a);

		for(int i=0;i<noOfLines;i++)
		{
			for(int j=0;j<avgWords;j++)
			{
				while(di.getNextWord(word)==null) {
					word=keys.get( random.nextInt(keys.size()));
					para+=word+" ";
					j++;
				}
				String nextWord = di.getNextWord(word);
				para+=nextWord+" ";
				word= nextWord;
			}
			para+="\n";
		}
		return para;
	}
}
