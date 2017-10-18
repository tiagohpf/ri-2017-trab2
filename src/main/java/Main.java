import Tokenizers.SimpleTokenizer;
import CorpusReader.Parser;
import CorpusReader.CorpusReader;
import CorpusReader.Document;
import CorpusReader.QueryParser;
import Indexer.IndexerReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

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
        //TODO: args <indexer file> <query file>  <file out nº1> <file out nº2>
        IndexerReader reader = new IndexerReader("indexerT1.txt");
    }
}
