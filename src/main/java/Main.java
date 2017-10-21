import Tokenizers.SimpleTokenizer;
import Parsers.Parser;
import CorpusReader.CorpusReader;
import Documents.Document;
import Parsers.QueryParser;
import Indexers.IndexerReader;
import Scoring.QueryScoring;
import Tokenizers.CompleteTokenizer;
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
        IndexerReader indexReader = new IndexerReader(args[0]);
        if (args.length == 4) {
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
            if (tokenizerType.equals(SimpleTokenizer.class.getName())) {
                SimpleTokenizer simpleTokenizer = new SimpleTokenizer();
                System.out.println("**********************************************");
                System.out.println("Queries with Simple Tokenizer");
                System.out.println("**********************************************");
                simpleTokenizer.tokenize(documents);
                terms = simpleTokenizer.getTerms();
                score = new QueryScoring(indexer, terms, documents.size());
                score.calculateScores();
                score.writeNumberOfWords(new File(args[2]));
                score.writeWordsFrequency(new File(args[3]));
            } else if (tokenizerType.equals(CompleteTokenizer.class.getName())) {
                CompleteTokenizer completeTokenizer = new CompleteTokenizer();
                System.out.println("**********************************************");
                System.out.println("Queries with Complete Tokenizer");
                System.out.println("**********************************************");
                completeTokenizer.tokenize(documents);
                terms = completeTokenizer.getTerms();
                score = new QueryScoring(indexer, terms, documents.size());
                score.calculateScores();
                score.writeNumberOfWords(new File(args[2]));
                score.writeWordsFrequency(new File(args[3]));
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
}
