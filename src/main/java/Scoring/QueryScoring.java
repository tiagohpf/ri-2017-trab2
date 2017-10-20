package Scoring;

import Utils.Key;
import Utils.Pair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryScoring {
    private final Map<String, List<Pair<Integer, Integer>>> indexer;
    private final List<Pair<String, Integer>> queries;
    private final Map<Key, Integer> numberOfTerms;
    private final Map<Key, Integer> termsFrequency;
    private final int size;
    
    public QueryScoring(Map<String, List<Pair<Integer, Integer>>> indexer, List<Pair<String, Integer>> queries, int size) {
        this.indexer = indexer;
        this.queries = queries;
        this.size = size;
        numberOfTerms = new HashMap<>();
        termsFrequency = new HashMap<>();
        calculateScores();
    }
    
    public Map<Key, Integer> getNumberOfTerms() {
        return numberOfTerms;
    }
    
    public Map<Key, Integer> getTermsFrequency() {
        return termsFrequency;
    }
    
    private void calculateScores() {
        for (int i = 1; i <= size; i++) {
            List<String> terms = new ArrayList<>(getTermsOfQuery(i));
            for (String term : terms) {
                List<Pair<Integer, Integer>> docId_freq = indexer.get(term);
                if (docId_freq != null)
                    addQueryIdToScores(i, docId_freq);
            }
        }
    }
    
    private List<String> getTermsOfQuery(int id) {
        return queries.stream()
                .filter(query -> query.getValue() == id)
                .map(query -> query.getKey())
                .collect(Collectors.toList()); 
    }
    
    private void addQueryIdToScores(int queryId, List<Pair<Integer, Integer>> docId_freq) {
        for (int i = 0; i < docId_freq.size(); i++) {
           Pair<Integer, Integer> pair = docId_freq.get(i);
           int docId = pair.getKey();
           if (numberOfTerms.get(new Key(queryId, docId)) == null)
               numberOfTerms.put(new Key(queryId, docId), 1);
           else {
               int value = numberOfTerms.get(new Key(queryId, docId)) + 1;
               numberOfTerms.put(new Key(queryId, docId), value);
           }
           int frequency = pair.getValue();
           if (termsFrequency.get(new Key(queryId, docId)) == null)
               termsFrequency.put(new Key(queryId, docId), frequency);
           else {
               int value = termsFrequency.get(new Key(queryId, docId)) + frequency;
               termsFrequency.put(new Key(queryId, docId), value);
           }
       }
    }
    
    private void writeNumberOfWords(String filename) {
        
    }
    
    private void writeWordsFrequency(String filename) {
        
    }
}
