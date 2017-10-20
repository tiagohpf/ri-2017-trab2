package Utils;

/**
 * IR, October 2017
 *
 * Assignment 1 
 *
 * @author Tiago Faria, 73714, tiagohpf@ua.pt
 * @author David dos Santos Ferreira, 72219, davidsantosferreira@ua.pt
 * @param <K>
 * @param <V1>
 * @param <V2>
 * 
 */

/*
* Pair.
* Structure to manage pairs.
*/
public class Triple<K, V1, V2> {
    private K key;
    private V1 firstValue;
    private V2 secondValue;

    /**
     * Constructor that receives a key and a value.
     * @param key
     * @param firstValue
     * @param secondValue
     */
    public Triple(K key, V1 firstValue, V2 secondValue) {
        this.key = key;
        this.firstValue = firstValue;
        this.secondValue = secondValue;
    }

    /**
     * Get the key of pair.
     * @return key
     */
    public K getKey() {
        return key;
    }

    /**
     * Get the first value of pair.
     * @return value
     */
    public V1 getFirstValue() {
        return firstValue;
    }
    
    /**
     * Get the second value of pair.
     * @return value
     */
    public V2 getSecondValue() {
        return secondValue;
    }

    /**
     * Set a new key in pair.
     * @param key
     */
    public void setKey(K key) {
        this.key = key;
    }

    /**
     * Set a new fist value in pair.
     * @param firstValue
     */
    public void setFirstValue(V1 firstValue) {
        this.firstValue = firstValue;
    }
    
    /**
     * Set a new fist value in pair.
     * @param secondValue
     */
    public void setSecondValue(V2 secondValue) {
        this.secondValue = secondValue;
    }

    /**
     * Get the string of pair.
     * @return string
     */
    @Override
    public String toString() {
        return "(" + key + ", " + firstValue + ", " + secondValue + ")";
    }
}