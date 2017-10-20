package Scoring;

import Utils.Pair;
import Utils.Triple;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class QueryScoring {
    private Map<String, List<Pair<Integer, Integer>>> indexer;
    private List<Pair<String, Integer>> queries;
    private List<Triple<Integer, Integer, Integer>> numberOfWords;
    private List<Triple<Integer, Integer, Integer>> wordsFrequency;
    private int size;
    
    public QueryScoring(Map<String, List<Pair<Integer, Integer>>> indexer, List<Pair<String, Integer>> queries, int size) {
        this.indexer = indexer;
        this.queries = queries;
        this.size = size;
        numberOfWords = new ArrayList<>();
        wordsFrequency = new ArrayList<>();
        calculateScores();
    }
    
    public List<Triple<Integer, Integer, Integer>> getNumberOfWords() {
        return numberOfWords;
    }
    
    public List<Triple<Integer, Integer, Integer>> getWordsFrequency() {
        return wordsFrequency;
    }
    
    private void calculateScores() {
        for (int i = 1; i <= size; i++) {
            Set<String> terms = new HashSet<>(getTermsOfQuery(i));
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
        System.out.println(queryId + " " + docId_freq.size());
        for (int i = 0; i < docId_freq.size(); i++) {
           Pair<Integer, Integer> pair = docId_freq.get(i);
           int docId = pair.getKey();
           if (filterByQueryId(queryId, docId, numberOfWords).isEmpty())
               numberOfWords.add(new Triple<>(queryId, docId, 1));
           else {
               int value = pair.getValue() + 1;
               numberOfWords.set(i, new Triple<>(queryId, docId, value));
           }
           int frequency = pair.getValue();
           if (filterByQueryId(queryId, docId, wordsFrequency).isEmpty())
               wordsFrequency.add(new Triple<>(queryId, docId, frequency));
           else {
               int value = pair.getValue() + frequency;
               wordsFrequency.set(i, new Triple<>(queryId, docId, value));
           }
       }
    }
    
    private List<Triple<Integer, Integer, Integer>> filterByQueryId(int queryId, int docId, 
            List<Triple<Integer, Integer, Integer>> scorer) {
        
        return scorer.stream()
                .filter(triple -> triple.getKey() == queryId && triple.getFirstValue() == docId)
                .collect(Collectors.toList());
    }
    
    private void writeNumberOfWords(String filename) {
        
    }
    
    private void writeWordsFrequency(String filename) {
        
    }
}
