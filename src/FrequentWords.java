package src;
import java.util.*;

public class FrequentWords {
    private static final Integer ONE_BILLION = 1000000000;
    
    /*
	 * Function: mostFrequentWords
	 * Input : Number of frequent words required (Integer), Mapping of String to Frequencies
	 * Output : List of N Strings with most frequencies 
	 * 
	 */
    static List<String> mostFrequentWords (int n, Map<String, Integer> wordMap) {
        List<String> mostFrequentWords = sortFrequentWords(wordMap);
        return mostFrequentWords.subList(0, n);
    }

    /*
	 * Function: sortFrequentWords
	 * Input : Mapping of String to Frequencies
	 * Output : List of Strings containing words in order of decreasing frequency
	 * 
	 */
    private static List<String> sortFrequentWords (Map<String, Integer> wordMap) {
        List<String> frequenciesWithWords = concatenateFrequenciesWithWords (wordMap);
        Collections.sort(frequenciesWithWords, Collections.reverseOrder());
        List<String> frequentWords = removeFrequencies(frequenciesWithWords);
        return frequentWords;
    }

    /*
	 * Function: concatenateFrequenciesWithWords
	 * Input : Mapping of String to Frequencies
	 * Output : Returns List of words with their frequencies prepend to them
	 * 
	 */
    private static List<String> concatenateFrequenciesWithWords (Map<String, Integer> wordMap) {
        List<String> frequenciesWithWords = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : wordMap.entrySet() ) {
            String frequencyWithWord = "";
            frequencyWithWord += Integer.toString(ONE_BILLION + entry.getValue());
            frequencyWithWord += entry.getKey();
            frequenciesWithWords.add(frequencyWithWord);
        }
        return frequenciesWithWords;
    }

    /*
	 * Function: removeFrequencies
	 * Input : List of Words with Frequencies prepended
	 * Output : List of Strings with frequencies removed
	 * 
	 */
    private static List<String> removeFrequencies (List<String> frequenciesWithWords) {
        List<String> wordsWithoutFrequencies = new ArrayList<> ();
        for (String word : frequenciesWithWords) {
            wordsWithoutFrequencies.add(word.substring(10));
        }
        return wordsWithoutFrequencies;
    }
}