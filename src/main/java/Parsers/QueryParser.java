package Parsers;

import Documents.Document;
import Documents.QueryDocument;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class QueryParser implements Strategy<List<Document>>{
    private int queryId = 0;
    private static Scanner sc;
    
    @Override
    public List<Document> parseFile(File file) {
        List<Document> queries = new ArrayList<>();
        try {
            sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                queries.add(new QueryDocument(++queryId, line));
            }
            return queries;
        } catch (FileNotFoundException ex) {
            System.err.println("ERROR: File not found!");
            System.exit(1);
        }
        return null;
    }

    @Override
    public List<Document> parseDir(File file) {
        File []files = file.listFiles();
        Arrays.sort(files);
        List<Document> documents = new ArrayList<>();
        for (File f : files)
             documents.addAll(parseFile(f));
        return documents;
    }
    
}
