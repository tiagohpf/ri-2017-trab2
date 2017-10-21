package Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import org.tartarus.snowball.ext.englishStemmer;

public class Filter {
    private final List<String> stopWords;
    
    public Filter() throws FileNotFoundException {
        stopWords = loadStopwords();
    }
    
    // Read the file of stopwords and load them to a list
    public List<String> loadStopwords() throws FileNotFoundException {
        List<String> words = new ArrayList<>();
        File file = new File ("stopwords.txt");
        Scanner sc = new Scanner(file);
        while (sc.hasNext()) {
            String word = sc.nextLine();
            // String.trim() to remove white spaces after text
            if (word.trim().length() > 0)
                words.add(word.trim());
        }
        return words;
    }
    
    /*
    * Apply the stopwording filtering.
    * If there's any term in the stopwording list, it'll be removed from terms list.
    */
    public List<Pair<String, Integer>> stopwordsFiltering(List<Pair<String, Integer>> terms) {
        return terms.stream()                                          // convert list to stream
                .filter(term -> !stopWords.contains(term.getKey()))   // filter words that stopwords's list hasn't
                .collect(Collectors.toList());                       // convert streams to List
    }
    
    // Stemmer for terms.
    public List<Pair<String, Integer>> stemmingWords(List<Pair<String, Integer>> terms) {
        englishStemmer stemmer = new englishStemmer();
        for (int i = 0; i < terms.size(); i++) {
            String term = terms.get(i).getKey();
            int docId = terms.get(i).getValue();
            stemmer.setCurrent(term);
            // If the term was stemmed, the terms list is updated.
            if (stemmer.stem())
                terms.set(i, new Pair<>(stemmer.getCurrent(), docId));
        }
        return terms;
    } 
}
