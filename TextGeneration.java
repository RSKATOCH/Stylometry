import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

public class TextGeneration {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Map<String,Integer> Vocabulary=new HashMap<String,Integer>();
		int avgWords=18;
		String author="TOlstoy,Leo";
		
		Book b=new Book("utils/WarAndPeace.txt","Tolstoy");
		Vocabulary=b.getMapOfWords();
		
		RandomWordGeneration(Vocabulary,avgWords);
	}

	private static void RandomWordGeneration(Map<String, Integer> vocabulary, int avgWords) {
		// TODO Auto-generated method stub
		for(int i=0;i<20;i++)
		{
		  for(int j=0;j<avgWords;j++)
		  {
			Random random = new Random();
			ArrayList<String> keys = new ArrayList<String>(vocabulary.keySet());
			System.out.print(keys.get( random.nextInt(keys.size()))+" ");
		  }
		  System.out.println("\n");
		}
	}

}
