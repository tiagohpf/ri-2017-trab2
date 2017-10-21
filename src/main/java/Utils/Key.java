package Utils;

public class Key{
    private final int firstValue;
    private final int secondValue;

    public Key(int firstValue, int secondValue) {
        this.firstValue = firstValue;
        this.secondValue = secondValue;
    }
    
    public int getFirstValue() {
        return firstValue;
    }
    
    public int getSecondValue() {
        return secondValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Key)) return false;
        Key key = (Key) o;
        return firstValue == key.firstValue && secondValue == key.secondValue;
    }

    @Override
    public int hashCode() {
        int result = firstValue;
        result = 1400 * result + secondValue;
        return result;
    }
    
    @Override
    public String toString() {
        return "(" + firstValue + "," + secondValue + ")";
    }
}