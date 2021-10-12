package data_structures;

/* Steven Nguyen
 * cssc1505
 */
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class BinarySearchTree<K extends Comparable<K>, V extends Comparable<V> > implements DictionaryADT<K, V>
{
    private int size;
    private long modcounter;
    private Node<K,V> root;
    

    private K keyholder1;//Holder for our findValue method so we can use our getKey method to check if the values match then store the key. 
    private V valueholder1;//Holder for our findValue that holds the value of the keyholder;

    private K keyholder2;//Holder for doPrint     
    private V valueholder2;//Holder for doPrint
    private Node nodeholder;

    public BinarySearchTree()
    {
        root=null;
        size=0;
        modcounter=0;
        
    }
    // Returns true if the dictionary has an object identified by
    // key in it, otherwise false.
    public boolean contains(K key)
    {
        if(isEmpty())
        {
            return false;
        }
        return find(key,root)!=null;//Code provided by Riggins.WORKS!
    }
    // Adds the given key/value pair to the dictionary. Returns
    // false if the dictionary is full, or if the key is a duplicate.
    // Returns true if addition succeeded.
    public boolean add(K key, V value)
    {
        //Code provided by Riggins.WORKS!
        if(contains(key))
        {
            return false;
        }
        if(root==null)
        {
            root=new Node<K,V>(key,value);
           

        }
        else
        {
            insert(key,value,root,null,false);
           
        }
        size++;
        modcounter++;
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
        Node n=findnodedelete(key,root);
        
        if(n.leftChild==null && n.rightChild==null)//0 Children case
        {
            n=null;
        }
        
       
        
        
        
        
        
        
         
        
        
        modcounter++;
        size--;
        return true;//Use get Value to check if key is in there and then 
    }
    // Returns the value associated with the parameter key. Returns
    // null if the key is not found or the dictionary is empty.
    public V getValue(K key)
    {
        if(isEmpty())
        {
            return null;
        }
        return find(key,root);//WORKS!
    }
    // Returns the key associated with the parameter value. Returns
    // null if the value is not found in the dictionary. If more
    // than one key exists that matches the given value, returns the
    // first one found.
    public K getKey(V value)
    {
        if(isEmpty())
        {
            return null;
        }

        findValue(value,root);//Helped us look through the whole tree. 
        if(valueholder1==null)//if Value not found at all. 
        {
            return null;
        }

        if(((Comparable<V>)valueholder1).compareTo(value)==0)
        {
            return keyholder1;
        }

        return null;
        //WORKS!

    }

    // Returns the number of key/value pairs currently stored
    // in the dictionary
    public int size()
    {
        return size;//WORKS!
    }
    // Returns true if the dictionary is at max capacity
    public boolean isFull()
    {
        return false;//Since never full.WORKS!
    }
    // Returns true if the dictionary is empty
    public boolean isEmpty()
    {
        return size==0;//WORKS!
    }
    // Returns the Dictionary object to an empty state.
    public void clear()
    {
        size=0;
        modcounter++;
        root=null;//WORKS!
    }
    // Returns an Iterator of the keys in the dictionary, in ascending
    // sorted order. The iterator must be fail-fast.
    public Iterator<K> keys()
    {
        return new KeyIteratorHelper();
    }
    // Returns an Iterator of the values in the dictionary. The
    // order of the values must match the order of the keys.
    // The iterator must be fail-fast.
    public Iterator<V> values()
    {
        return new ValueIteratorHelper();
    }

    abstract class IteratorHelper<E> implements Iterator<E>
    {
        protected int iterIndex;
        protected int index;//For our nodeholder array.
        protected long modcheck;
        protected Node<K,V> [] nodeholder ;//Auxilliary array of nodes. 
        public IteratorHelper()
        {
            iterIndex=0;
            index=0;
            modcheck=modcounter;
            nodeholder=new Node[size];
            inorderFillArray(root);
        }

        public boolean hasNext()
        {
            if(modcounter!=modcheck)
            {
                throw new ConcurrentModificationException();
            }
            return iterIndex<size;

        }

        public abstract E next();

        public void remove()
        {
            throw new UnsupportedOperationException();
        }

        private void inorderFillArray(Node<K,V> n)
        {
            //Code provided by Riggins
            if(n==null)
            {
                return;
            }
            inorderFillArray(n.leftChild);
            nodeholder[index++]=n;
            inorderFillArray(n.rightChild);
        }

    }

    class KeyIteratorHelper<K> extends IteratorHelper<K>
    {
        //Code provided by Riggins
        public KeyIteratorHelper()
        {
            super();
        }

        public K next()
        {
            if(!hasNext())
            {
                throw new NoSuchElementException();//FINE!
            }
            return (K)nodeholder[iterIndex++].key;
        }
    }

    class ValueIteratorHelper<V> extends IteratorHelper<V>
    {
        //Code provided by Riggins
        public ValueIteratorHelper()
        {
            super();
        }

        public V next()
        {
            if(!hasNext())
            {
                throw new NoSuchElementException();//FINE!
            }
            return (V)nodeholder[iterIndex++].value;
        }

    }

    private void inOrder(Node<K,V> n)
    {
        if(n!=null)
        {
            inOrder(n.leftChild);
            System.out.println(n.value);
            inOrder(n.rightChild);
        }
    }

    private class Node<K,V>
    {
        //Code provided by Riggins
        private K key;
        private V value;
        private Node<K,V> leftChild;
        private Node<K,V> rightChild;

        public Node(K k, V v)
        {
            key=k;
            value=v;
            leftChild=null;
            rightChild=null;
        }

    }

    private V find(K key, Node<K,V> n)
    {
        //Code provided by Riggins
        if(n==null) return null;
        if(((Comparable<K>) key).compareTo(n.key)< 0)
            return find(key, n.leftChild);  //Go left
        if(((Comparable<K>) key).compareTo(n.key)> 0)
            return find(key, n.rightChild); //Go right
        return (V)n.value;  //We found the value
    }
    
    private Node findnodedelete(K key,Node<K,V>n)
    {
        //Code provided by Riggins
        if(n==null) return null;
        if(((Comparable<K>) key).compareTo(n.key)< 0)
            return findnodedelete(key, n.leftChild);  //Go left
        if(((Comparable<K>) key).compareTo(n.key)> 0)
            return findnodedelete(key, n.rightChild); //Go right
        return n;  //We found the value
        
    }
    
   

    private void insert(K k, V v, Node<K,V> n, Node<K,V> parent, boolean wasLeft)
    {
        //Code provided by Riggins
        if(n==null){    //at a leaf node so do the insert
            if(wasLeft) parent.leftChild= new Node<K,V>(k,v);
            else parent.rightChild= new Node<K,V>(k,v);
        }
        else if (((Comparable<K>) k).compareTo((K)n.key) < 0)
            insert(k,v,n.leftChild,n,true);     //Go left
        else
            insert(k,v,n.rightChild, n, false); //Go right

    }

    
    

    private void findValue(V value2 ,Node n)//Takes in a node and stores the node and it's value in global value when found. 
    {
        //Code provided by Riggins. Used with my getKey methods to find the key easier. 
        if (n==null)
        {
            return;//Do nothing
        }
        if(((Comparable<V>)n.value).compareTo(value2)==0)
        {
            keyholder1=(K)n.key;//Key holder now has our key that has the value we want so we store it. 
            valueholder1=(V)n.value;//Store the value of that node since it matches;
        }
        findValue(value2,n.leftChild);
        findValue(value2,n.rightChild);

    }

    private void doPrint(K key2, Node n)
    {
        //Code provided by Riggins
        if (n==null)
        {
            return;//Do nothing
        }
        if(((Comparable<K>)n.key).compareTo(key2)==0)
        {
            keyholder2=(K)n.key;//Key holder now has our key that has the value we want so we store it. 
            valueholder2=(V)n.value;//Store the value of that node since it matches;
            nodeholder=n;
        }
        doPrint(key2,n.leftChild);
        doPrint(key2,n.rightChild);

    }   
    
   

}
