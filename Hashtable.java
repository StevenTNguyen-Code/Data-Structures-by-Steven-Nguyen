package data_structures;

/* Steven Nguyen
 * cssc1505
 */
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;
public class Hashtable<K extends Comparable<K>, V extends Comparable<V> > implements DictionaryADT<K, V>
{
    private int size;
    private int maxsize;
    private int tablesize;
    private long modcounter;
    private LinearList<DictionaryNode<K,V>> [] linkedlistarray;//Code provided by Riggins
    public Hashtable(int limit)
    {
        size=0; //Code provided by Riggins
        maxsize=limit;
        modcounter=0;
        tablesize=(int)(maxsize*1.3f);
        linkedlistarray=new LinearList[tablesize];
        for(int i=0;i<tablesize;i++)
        {
            linkedlistarray[i]=new LinearList<DictionaryNode<K,V>>();
        }
    }

    // Returns true if the dictionary has an object identified by
    // key in it, otherwise false.
    public boolean contains(K key)
    {
        return linkedlistarray[getHashCode(key)].contains(new DictionaryNode<K,V>(key,null));//Code provided my Riggins.WORKS!
    }
    // Adds the given key/value pair to the dictionary. Returns
    // false if the dictionary is full, or if the key is a duplicate.
    // Returns true if addition succeeded.
    public boolean add(K key, V value)
    {
        if(isFull())
        {
            return false;
        }
        if(linkedlistarray[getHashCode(key)].contains(new DictionaryNode<K,V>(key,null)))
        {
            return false;
        }
        linkedlistarray[getHashCode(key)].addFirst(new DictionaryNode<K,V>(key,value));//Debug this part by checking the values. 
        size++;
        modcounter++;
        return true;    //Code provided by Riggins.WORKS!
    }
    // Deletes the key/value pair identified by the key parameter.
    // Returns true if the key/value pair was found and removed,
    // otherwise false.
    public boolean delete(K key)
    {
        if(isEmpty() || !contains(key))//If it's empty or if the key is not at all inside, cannot delete so return false.
        {
            return false;//WORKS!
        }
        linkedlistarray[getHashCode(key)].remove(new DictionaryNode<K,V>(key,null));//Remove it because since the linked list is not empty or it has the key/value somewhere. 
        modcounter++;
        size--;
        return true;
    }
    // Returns the value associated with the parameter key. Returns
    // null if the key is not found or the dictionary is empty.
    public V getValue(K key)
    {
        DictionaryNode<K,V> holder=linkedlistarray[getHashCode(key)].get(linkedlistarray[getHashCode(key)].spot(new DictionaryNode<K,V>(key,null)));//Holds a value or a null. Get OBJ at index.
        if(isEmpty() ||!contains(key)|| holder==null)
        {
            return null;
        }

        return holder.value;
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
        for(int i=0; i<tablesize;i++) 
        {
            for(DictionaryNode<K,V> a: linkedlistarray[i])//Looks at each linked list at each array index and then compares the values and if it's the same, return the key. 
            {
                if(((Comparable<V>)a.value).compareTo(value)==0)
                {
                    return a.key;//WORKS!
                }
                
            }
        }

        return null;
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
        return size==maxsize;//WORKS!
    }
    // Returns true if the dictionary is empty
    public boolean isEmpty()
    {
        return size==0;//WORKS!
    }
    // Returns the Dictnary object to an empty state.
    public void clear()
    {
        size=0;
        modcounter++;
        for(int i=0; i<tablesize;i++)
        {
            linkedlistarray[i].clear();//WORKS!
        }
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
        protected DictionaryNode<K,V> [] nodes; //Code provided by Riggins
        protected int idx;
        protected long modcheck;

        public IteratorHelper()
        {
            nodes=new DictionaryNode[size];     //WRITE SHELL SORT METHOD. Code provided by Riggins.FINE!
            idx=0;
            int j=0;
            modcheck=modcounter;
            for(int i=0;i<tablesize;i++)

                for(DictionaryNode n: linkedlistarray[i])

                    nodes[j++]=n;
            nodes=(DictionaryNode<K,V>[]) shellsort(nodes);

        }

        public boolean hasNext()
        {
            if(modcheck!=modcounter)
            {
                throw new ConcurrentModificationException();
            }
            return idx<size;
        }

        public abstract E next();

        public void remove()
        {
            throw new UnsupportedOperationException();
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
            return (K)nodes[idx++].key;
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
            return (V)nodes[idx++].value;
        }

    }
    //Method that retrieves hashcode from key and returns the index in the array. 
    private int getHashCode(K key)
    {
        return (key.hashCode() & 0x7FFFFFFF)% tablesize;//Code provided by Riggins.FINE!
    }

    private class DictionaryNode<K,V> implements Comparable<DictionaryNode<K,V>>
    {
        K key;
        V value;

        public DictionaryNode(K key,V value)
        {
            this.key=key;
            this.value=value;
        }

        public int compareTo(DictionaryNode<K,V> node)
        {
            return ((Comparable<K>)key).compareTo((K)node.key);//Code provided my Riggins.FINE!
        }

    }

    private DictionaryNode<K,V>[] shellsort(DictionaryNode[] array)
    {
        DictionaryNode<K,V>[] n= array;     //Code provided by Riggins in reader.FINE!
        int in;
        int out;
        int h=1;
        int size2=n.length;
        DictionaryNode<K,V> holder=null;

        while(h<=size2/3)
            h=h*3+1;
        while(h>0)
        {
            for(out=h;out<size2;out++)
            {
                holder=n[out];
                in=out;
                while(in>h-1 && n[in-h].compareTo(holder)>=0)
                {
                    n[in]=n[in-h];
                    in-=h;

                }
                n[in]=holder;
            }
            h=(h-1)/3;
        }
        return n;

    }

    private class LinearList<E extends Comparable<E>> implements Iterable<E> 
    {

        private class Node<E>
        {
            E data;
            Node <E> next;
            Node <E> prev;

            public Node(E d)
            {
                data=d;
                next=null;
                prev=null;
            }

        }
        private Node<E> head;
        private Node<E> tail; 
        private int size;// For size
        private long modcounter;//For iterator fail-fast
        public LinearList()
        {
            head=null;
            tail=null;
            size=0;
            modcounter=0;
        }

        /* Adds the Object obj to the beginning of list and returns true if the list
         * is not full.
         * returns false and aborts the insertion if the list is full.
         */
        public boolean addFirst(E obj)
        {
            Node <E> newNode= new Node(obj);    //Don't need to check if it's full as we can keep adding no matter what. 

            if(head==null) //List is empty. We are adding 1st item now.
            {
                newNode.next=head;
                head=newNode;
                tail=head;
                size++;
                modcounter++;
                return true;
            }
            else       //The head pointer would only change and the tail would be the same. Work for if it's only 1 item or more. 
            {
                newNode.next=head;
                head.prev=newNode;
                head=newNode;
                size++;
                modcounter++;
                return true;
            }

        }

        /* Adds the Object obj to the end of list and returns true if the list is
         * not full.
         * returns false and aborts the insertion if the list is full.
         */
        public boolean addLast(E obj)
        {
            Node <E> newNode= new Node(obj);

            if(tail==null) //List is empty. We are adding 1st item now.
            {
                newNode.next=head;
                head=newNode;
                tail=head;
                size++;
                modcounter++;
                return true;
            }
            else    //The tail pointer would only change and the tail would be the same. Work for if it's only 1 item or more. 
            {
                tail.next=newNode;
                newNode.prev=tail;
                tail=newNode;
                size++;
                modcounter++;
                return true;
            }

        }

        /* Removes and returns the parameter object obj in first position in list
         * if the list is not empty, null if the list is empty.
         */
        public E removeFirst()
        {
            E dataholder;
            if(head==null)//Is empty
            {
                return null;

            }
            if(size==1)// Only 1 element inside
            {
                //Store data before removing
                dataholder=head.data;
                head=null;
                tail=null;
                size--;
                modcounter++;
                return dataholder;

            }
            else//There is more than 1 element inside. 
            {
                dataholder=head.data;
                head.next.prev=null;
                head=head.next;
                size--;
                modcounter++;
                return dataholder;

            }

        }

        /* Removes and returns the parameter object obj in last position in list if
         * the list is not empty, null if the list is empty.
         */
        public E removeLast()
        {
            E dataholder;
            if(tail==null)//Is empty
            {
                return null;

            }
            if(size==1)// Only 1 element inside
            {
                return removeFirst();
            }
            else//There is more than 1 element inside. 
            {

                dataholder=tail.data;
                tail.prev.next=null;
                tail=tail.prev;
                size--;
                modcounter++;
                return dataholder;
            }

        }

        /* Removes and returns the parameter object obj from the list if the list
         * contains it, null otherwise. The ordering of the list is preserved. 
         * The list may contain duplicate elements. This method removes and returns
         * the first matching element found when traversing the list from first
         * position. Note that you may have to shift elements to fill in the slot
         * where the deleted element was located.
         */
        public E remove(E obj)
        {

            if(isEmpty())   //Works
            {
                return null;
            }

            Node<E> beginning=null;//1st pointer 
            Node<E> first=head; //Node we are removing. 2nd pointer
            Node<E> afterRemove=first.next;// 3rd pointer 
            if(((Comparable<E>)obj).compareTo(first.data)==0 && size==1)//Size is 1. Head and Tail point at same spot. WORKS!
            {
                head=null;
                tail=null;
                size--;
                modcounter++;
                return first.data;
            }

            while(first!=null)  //Loops used to search through contents of the list. 
            {
                if(((Comparable<E>)obj).compareTo(first.data)!=0)//Find the element first. 
                {

                    first=first.next;
                    if(afterRemove!=null && afterRemove.next!=null)
                    {
                        afterRemove=afterRemove.next;

                    }
                    if(beginning==null)
                    {
                        beginning=head;

                    }
                    else
                    {
                        beginning=beginning.next;

                    }
                }

                else if (((Comparable<E>)obj).compareTo(head.data)==0)//Element happens to be at the head.
                {
                    return removeFirst();
                }
                else if(((Comparable<E>)obj).compareTo(tail.data)==0)//Element happens to be at the tail
                {
                    return removeLast();
                }

                else   
                {
                    beginning.next=afterRemove;
                    first.prev=null;
                    afterRemove.prev=beginning;
                    first.next=null;

                    size--;
                    modcounter++;
                    return first.data;

                }

            }
            return null;
        }

        /* Returns the first element in the list, null if the list is empty.
         * The list is not modified.
         */
        public E peekFirst()
        {
            return (head==null) ? null : head.data;
        }

        /* Returns the last element in the list, null if the list is empty.
         * The list is not modified.
         */
        public E peekLast()
        {
            return (tail==null)? null:tail.data;
        }

        /* Returns true if the parameter object obj is in the list, false otherwise.
         * The list is not modified.
         */
        public boolean contains(E obj)
        {
            return find(obj)!=null;
        }

        /* Returns the element matching obj if it is in the list, null otherwise.
         * In the case of duplicates, this method returns the element closest to
         * front. The list is not modified.
         */
        public E find(E obj)
        {
            Node<E> beginning=head;// Tmp variable so our head variable doens't move.
            E dataholder;

            while(beginning!=null)
            {
                if(((Comparable<E>)obj).compareTo(beginning.data)==0)
                {
                    dataholder=obj;
                    return dataholder;

                }
                if(((Comparable<E>)obj).compareTo(beginning.data)!=0 && beginning.next!=null)
                {
                    beginning=beginning.next;   //Move pointer if there is a node to the right. 

                }
                if(((Comparable<E>)obj).compareTo(beginning.data)!=0 && beginning.next==null)// We reached the end so the element isn't in there. Check last element before returning null
                {
                    return null;

                }

            }
            return null;
        }
        //Get's the value at that spot and if not returns null if the location is not valid or possible. 
        public E get(int spot)//Get's the value at that spot
        {
            if(isEmpty() || spot<1 || spot>size)
            {
                return null;//Not possible to find
            }

            int spotofitem=1;//Since we start at 1st spot of the linked list. 
            Node<E> start=head;//The nodes that goes through and find the spot where we want the value of.

            while(spotofitem<spot && start.next!=null)//Loops that finds the spot where we want the value
            {
                start=start.next;//Moves the pointer until we reach the spot of the item. 
                spotofitem++;
            }
            if (spot==-1)
            {
                return null;
            }

            return start.data;//start will stop at the spot where it found the value and return it. 
        }      
        //Returns the spot number of where the value is and returns -1 if it is not found inside. 
        public int spot(E obj)
        {
            if(isEmpty())
            {
                return -1;//Not possible to find
            }
            
            int location=1;//Since we start at the head.
            Node<E> start=head;
            
            while(start.next!=null && ((Comparable<E>) obj).compareTo(start.data) !=0)//While loop for if they aren't equal, keep transversing though until we find it and increment lcoation. 
            {
                start=start.next;
                location++;
            }
            if(obj.compareTo(start.data) !=0)//We didn't find the index or spot where the value was so return -1 and my get method says if the spot is -1, returns null so we didn't find it. 
            {
                return -1;
            }

            return location;
        }

        /* The list is returned to an empty state.
         */
        public void clear()
        {
            head=null;
            tail=null;
            size=0;
            modcounter++;

        }

        /* Returns true if the list is empty, otherwise false
         */
        public boolean isEmpty()
        {
            return size==0;

        }

        /* Returns true if the list is full, otherwise false
         */
        public boolean isFull()
        {
            return false;

        }

        /* Returns the number of Objects currently in the list.
         */
        public int size()
        {
            return size;
        }

        /* Returns an Iterator of the values in the list, presented in the same
         * order as the underlying order of the list. (front first, rear last)
         */
        public Iterator<E> iterator()
        {
            return new IteratorHelper();

        }
        class IteratorHelper implements Iterator<E>
        {
            Node<E> iterindex;
            private long countmodcheck;

            public IteratorHelper()
            {
                iterindex=head;
                countmodcheck=modcounter;
            }

            public boolean hasNext()
            {
                if (countmodcheck!=modcounter)
                {
                    throw new ConcurrentModificationException();
                }
                return iterindex!=null;

            }

            public E next()
            {
                if(!hasNext())
                {
                    throw new NoSuchElementException();

                }
                E dataofnode=iterindex.data;

                iterindex=iterindex.next;//Moves iterator pointer after storing the data of the old one.

                return dataofnode;

            }

            public void remove()
            {
                throw new UnsupportedOperationException();

            }
        }
    }
}



