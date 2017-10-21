package Scoring;

import Utils.Key;
import Utils.Pair;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class QueryScoring {
    private final Map<String, List<Pair<Integer, Integer>>> indexer;
    private final List<Pair<String, Integer>> queries;
    private Map<Key, Integer> numberOfTerms;
    private Map<Key, Integer> termsFrequency;
    private final int size;
    
    public QueryScoring(Map<String, List<Pair<Integer, Integer>>> indexer, List<Pair<String, Integer>> queries, int size) {
        this.indexer = indexer;
        this.queries = queries;
        this.size = size;
        numberOfTerms = new HashMap<>();
        termsFrequency = new HashMap<>();
    }
    
    public Map<Key, Integer> getNumberOfTerms() {
        return numberOfTerms;
    }
    
    public Map<Key, Integer> getTermsFrequency() {
        return termsFrequency;
    }
    
    public void calculateScores() {
        for (int i = 1; i <= size; i++) {
            List<String> terms = new ArrayList<>(getTermsOfQuery(i));
            for (String term : terms) {
                List<Pair<Integer, Integer>> docId_freq = indexer.get(term);
                if (docId_freq != null)
                    addQueryIdToScores(i, docId_freq);
            }
        }
        numberOfTerms = orderTerms(numberOfTerms);
        termsFrequency = orderTerms(termsFrequency);
    }
    
     public void writeNumberOfWords(File file) {
        if (file.exists()) {
            System.err.println("ERROR: File already exists");
            System.exit(1);
        }
        PrintWriter pw;
        try {
            pw = new PrintWriter(file);
            pw.write(String.format("%-20s %-20s %-20s\n", "query_id", "doc_id", "doc_score"));
            for (Map.Entry<Key, Integer> term : numberOfTerms.entrySet())
                pw.write(String.format("%-20d %-20d %-20d\n",
                        term.getKey().getFirstValue(), term.getKey().getSecondValue(), term.getValue()));
            pw.close();
        } catch (FileNotFoundException ex) {
            System.err.println("ERROR: File not found!");
            System.exit(1);
        }
    }

    public void writeWordsFrequency(File file) {
        if (file.exists()) {
            System.err.println("ERROR: File already exists");
            System.exit(1);
        }
        PrintWriter pw;
        try {
            pw = new PrintWriter(file);
            pw.write(String.format("%-20s %-20s %-20s\n", "query_id", "doc_id", "doc_score"));
            for (Map.Entry<Key, Integer> term : termsFrequency.entrySet())
                pw.write(String.format("%-20d %-20d %-20d\n",
                        term.getKey().getFirstValue(), term.getKey().getSecondValue(), term.getValue()));
            pw.close();
        } catch (FileNotFoundException ex) {
            System.err.println("ERROR: File not found!");
            System.exit(1);
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
    
    private Map<Key, Integer> orderTerms(Map<Key, Integer> terms) {
        List<Map.Entry<Key,Integer>> entries = new ArrayList<>(terms.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<Key,Integer>>() {
            @Override
            public int compare(Entry<Key, Integer> o1, Entry<Key, Integer> o2) {
                int res;
                int id1 = o1.getKey().hashCode();
                int id2 = o2.getKey().hashCode();
                if (id1 < id2)
                    res = -1;
                else
                    res = 1;
                return res;
            }
        });
        Map<Key, Integer> result = new LinkedHashMap<>();
        for (Map.Entry<Key, Integer> entry: entries)
            result.put(new Key(entry.getKey().getFirstValue(), entry.getKey().getSecondValue()), entry.getValue());
        return result;
    }
}
