package Documents;

public class QueryDocument extends Document{
    
    public QueryDocument() { }
    
    public QueryDocument(int id, String text) {
        super(id, text);
    }
    
    @Override
    public String toString() {
        return "Query: {id: " + super.getId() + "; text: " + super.getText() + "}";
    }
}
