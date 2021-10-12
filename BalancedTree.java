package data_structures;

/* Steven Nguyen
 * cssc1505
 */
import java.util.Iterator;
import java.util.TreeMap;

public class BalancedTree <K extends Comparable<K>, V extends Comparable<V> > implements DictionaryADT<K, V>
{
    private TreeMap<K,V> rbTree;
    
    
    public BalancedTree()
    {
        rbTree=new TreeMap();
        
    }
    // Returns true if the dictionary has an object identified by
    // key in it, otherwise false.
    public boolean contains(K key)
    {
        return rbTree.containsKey(key);//WORKS!
    }
    // Adds the given key/value pair to the dictionary. Returns
    // false if the dictionary is full, or if the key is a duplicate.
    // Returns true if addition succeeded.
    public boolean add(K key, V value)
    {
        if(contains(key))//If it has the key already, return false. Don't need to worry about full as never full. 
        {
            return false;
        }
        rbTree.put(key,value);
        return true;//WORKS!
    }
    // Deletes the key/value pair identified by the key parameter.
    // Returns true if the key/value pair was found and removed,
    // otherwise false.
    public boolean delete(K key)
    {
        if(isEmpty())
        {
            return false;
        }
        return rbTree.remove(key)!=null;//WORKS!

    }
    // Returns the value associated with the parameter key. Returns
    // null if the key is not found or the dictionary is empty.
    public V getValue(K key)
    {
        if(isEmpty() || !contains(key))
        {
            return null;
        }
        return (V)rbTree.get(key);//WORKS!

    }
    // Returns the key associated with the parameter value. Returns
    // null if the value is not found in the dictionary. If more
    // than one key exists that matches the given value, returns the
    // first one found.
    public K getKey(V value)
    {

        for(K keys: rbTree.keySet())//keySet lets me see all of the keys. 
        {
            if(((Comparable<V>)rbTree.get(keys)).compareTo(value)==0)//If the values are the same, return the key. Same format as hashtable a bit. 
            {
                return keys;//WORKS!
            }
        }
        
        return null;

    }
    // Returns the number of key/value pairs currently stored
    // in the dictionary
    public int size()
    {
        return rbTree.size();//WORKS!
    }
    // Returns true if the dictionary is at max capacity
    public boolean isFull()
    {
        return false;//WORKS!
    }
    // Returns true if the dictionary is empty
    public boolean isEmpty()
    {
        return rbTree.size()==0;//WORKS!
    }
    // Returns the Dictionary object to an empty state.
    public void clear()
    {
        rbTree.clear();//WORKS!

    }
    // Returns an Iterator of the keys in the dictionary, in ascending
    // sorted order. The iterator must be fail-fast.
    public Iterator<K> keys()
    {
        return rbTree.navigableKeySet().iterator();

    }
    // Returns an Iterator of the values in the dictionary. The
    // order of the values must match the order of the keys.
    // The iterator must be fail-fast.
    public Iterator<V> values()
    {
        return rbTree.values().iterator();

    }
    
    
}
