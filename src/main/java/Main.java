import Tokenizers.SimpleTokenizer;
import Parsers.Parser;
import CorpusReader.CorpusReader;
import Documents.Document;
import Parsers.QueryParser;
import Indexers.IndexerReader;
import Scoring.QueryScoring;
import Tokenizers.CompleteTokenizer;
import Utils.Filter;
import Utils.Key;
import Utils.Pair;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * IR, October 2017
 *
 * Assignment 1 
 *
 * @author Tiago Faria, 73714, tiagohpf@ua.pt
 * @author David dos Santos Ferreira, 72219, davidsantosferreira@ua.pt
 * 
 */
public class Main {

    /**
     * Read files and options from arguments. After that, show the answers.
     * 
     * The files have to be unique.
     * If you want to use the Simple Tokenizer, insert [filename] [t1] [filename2].
     * If you want to use the Complete Tokenizer and create a indexer, insert [filename] [t2] [filename2].
     * 
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {         
        if (args.length == 4) {
            IndexerReader indexReader = new IndexerReader(args[0]);
            File file = new File(args[1]);
            if (!file.exists()) {
                System.err.println("ERROR: File not found");
                System.exit(1);
            }
            Parser parser = new Parser(new QueryParser());
            CorpusReader corpusReader = new CorpusReader();
            if (file.isDirectory())
                corpusReader.setDocuments(parser.parseDir(file));
            else
                corpusReader.setDocuments((List<Document>)parser.parseFile(file));
            List<Document> documents = corpusReader.getDocuments();
            String tokenizerType = indexReader.getTokenizerType();
            List<Pair<String, Integer>> terms;
            Map<String, List<Pair<Integer, Integer>>> indexer = indexReader.getIndexer();
            QueryScoring score;
            System.out.println("***********************************************************************");
            if (tokenizerType.equals(SimpleTokenizer.class.getName())) {
                SimpleTokenizer simpleTokenizer = new SimpleTokenizer();
                System.out.println("\t\tQueries with Simple Tokenizer");
                simpleTokenizer.tokenize(documents);
                terms = simpleTokenizer.getTerms();
                showResults(indexer, terms, documents.size(), args[2], args[3]);
            } else if (tokenizerType.equals(CompleteTokenizer.class.getName())) {
                CompleteTokenizer completeTokenizer = new CompleteTokenizer();
                System.out.println("\t\tQueries with Complete Tokenizer");
                completeTokenizer.tokenize(documents);
                terms = completeTokenizer.getTerms();
                Filter filter = new Filter();
                terms = filter.stopwordsFiltering(terms);
                terms = filter.stemmingWords(terms);
                showResults(indexer, terms, documents.size(), args[2], args[3]);           
            } else {
                System.err.println("ERROR: Invalid type of Tokenizer!");
                System.exit(1);
            }
        } else {
            System.err.println("ERROR: Invalid number of arguments!");
            System.out.println("USAGE: <indexer file> <queries file> <file result 1> <file result 2>");
            System.exit(1);
        }
    }
    
    private static void showResults(Map<String, List<Pair<Integer, Integer>>> indexer, List<Pair<String, Integer>> terms, 
            int size, String firstFile, String secondFile) {
        System.out.println("***********************************************************************");
        QueryScoring score = new QueryScoring(indexer, terms, size);
        score.calculateScores();
        score.writeNumberOfWords(new File(firstFile));
        Map<Key, Integer> numberOfTerms = score.getNumberOfTerms();
        System.out.println("1. Number of query's words that appear in documents");
        System.out.println("------------------------------------------------------------------------");
        System.out.println(numberOfTerms);
        score.writeWordsFrequency(new File(secondFile));
        Map<Key, Integer> termsFrequency = score.getTermsFrequency();
        System.out.println("------------------------------------------------------------------------");
        System.out.println("2. Total frequency of query words in the documents");
        System.out.println("------------------------------------------------------------------------");
        System.out.println(termsFrequency);
    } 
}
