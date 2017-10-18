package CorpusReader;

/**
 * IR, October 2017
 *
 * Assignment 1 
 *
 * @author Tiago Faria, 73714, tiagohpf@ua.pt
 * @author David dos Santos Ferreira, 72219, davidsantosferreira@ua.pt
 * 
 */
public class XMLDocument extends Document {
    private String title;
    private String author;
    
    /**
     * Class that extends Document abstract class.
     * Class that represents XML Files.
     * Constructor.
     */
    public XMLDocument() { }
    
    /**
     * Constructor with Document's id, title, text and author.
     * @param id
     * @param title
     * @param text
     * @param author
     */
    public XMLDocument(int id, String title, String text, String author) {
        super(id, text);
        this.title = title;
        this.author = author;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    @Override
    public String toString() {
        return "XML: {id: " + super.getId() + "; title: " + title + 
                "; text: " + super.getText() + "; author: " + author + "}";
    }
}
