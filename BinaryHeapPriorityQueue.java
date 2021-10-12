//Steven Nguyen
//cssc1505
package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class BinaryHeapPriorityQueue <E extends Comparable<E>> implements PriorityQueue<E>
{   
    private int size;//For our size
    private long modcounter;
    private Wrapper<E> [] array;//Our original array of items. 
    private int entryNumber;

    public BinaryHeapPriorityQueue()
    {
        array= new Wrapper[DEFAULT_MAX_CAPACITY];
        size=0;
        modcounter=0;
        entryNumber=0;
    }

    public BinaryHeapPriorityQueue(int size1)
    {
        array=new Wrapper[size1];
        size=0;
        modcounter=0;
        entryNumber=0;
    }
    // Inserts a new object into the priority queue. Returns true if
    // the insertion is successful. If the PQ is full, the insertion
    // is aborted, and the method returns false.
    public boolean insert(E object)
    {
        if(isFull())    //If the priority queue is full, return false
        {
            return false;
        }
        array[size++]=new Wrapper<E>(object);//The end of the array
        modcounter++;
        trickleUp(size-1);//Index of last element in array. 
        return true; 
        //WORKS!
    }
    // Removes the object of highest priority that has been in the
    // PQ the longest, and returns it. Returns null if the PQ is empty.
    public E remove()
    {
        if(isEmpty())
        {
            return null;
        }
        E dataholder=array[0].data;
        //array[0] = array[--size];//Pre decrement since array out of bounds as post will get index that doesn't exist. 
        trickleDown(0);
        size--;
        modcounter++;
        return dataholder;
        //WORKS!
    }
    // Deletes all instances of the parameter obj from the PQ if found, and
    // returns true. Returns false if no match to the parameter obj is found.
    public boolean delete(E obj)
    {
        boolean itemfound=false;//Boolean holder so when we run our loop, we don't want to return true right away and stop there. We want to keep going. 
        if(isEmpty())
        {
            return false;
        }

        for (int i=size-1; i>=0;i--)
        {

            if(obj.compareTo(array[i].data)==0)
            {

                trickleDown(i);
                size--;
                modcounter++;
                itemfound=true; //WORKS!. Start loop from end to deal with last element matching condition
            }

        }
        return itemfound;

    }
    // Returns the object of highest priority that has been in the
    // PQ the longest, but does NOT remove it.
    // Returns null if the PQ is empty.
    public E peek()
    {
        if(isEmpty())
        {
            return null;//WORKS!
        }
        return array[0].data;  // It should return the very first element we inserted as that was in there the longest. 

    }
    // Returns true if the priority queue contains the specified element
    // false otherwise.
    public boolean contains(E obj)
    {
        if(isEmpty())
        {
            return false;
        }
        for(int i=0; i<size;i++)
        {
            if(obj.compareTo(array[i].data)==0)
            {
                return true;    //WORKS!
            }

        }
        return false;
    }
    // Returns the number of objects currently in the PQ.
    public int size()
    {
        return size;//WORKS!
    }
    // Returns the PQ to an empty state.
    public void clear()
    {
        size=0;
        modcounter++;//WORKS!

    }
    // Returns true if the PQ is empty, otherwise false
    public boolean isEmpty()
    {
        return size==0;//WORKS!
    }
    // Returns true if the PQ is full, otherwise false. List based
    // implementations should always return false.
    public boolean isFull()
    {
        return size==array.length;//WORKS!
    }
    // Returns an iterator of the objects in the PQ, in no particular
    // order.
    public Iterator<E> iterator()
    {
        return new IteratorHelper();//WORKS!

    }

    class IteratorHelper implements Iterator<E> 
    {
        private int interIndex;
        private long modcountercheck;
        private Wrapper<E> [] sortingarray;//Array where we copy contents of original array into and sort using shell sort. 
        private Wrapper<E> holder;

        public IteratorHelper()
        {
            interIndex=0;
            modcountercheck=modcounter;
            
            sortingarray=array.clone();//Copies original array contents into the new array so we can sort and won't mess with original contents. 
            
            int h=1;
            int size2=size;//Using our original size. 
            int out;
            int in;
            //The sorting algorithm we use is the shell sort. 
            while(h<=size2/3)
            {
                h=h*3+1;
            }
            
            while(h>0)
            {
                for(out=h; out<size2;out++)
                {
                    holder=sortingarray[out];
                    in=out;
                    while(in > h-1 && sortingarray[in-h].compareTo (holder)>= 0)
                    {
                        sortingarray[in]=sortingarray[in-h];
                        in-=h;
                    }
                    sortingarray[in]=holder;
                }
                h=(h-1)/3;
            }

        }
        public boolean hasNext()
        {
            if(modcountercheck!=modcounter)
            {
                throw new ConcurrentModificationException();

            }
            return interIndex<size;
        }

        public E next()
        {
            if(!hasNext())
            {
                throw new NoSuchElementException();
            }
            return sortingarray[interIndex++].data;  ///We get our auxilliary array and call it's remove and return it. make sure the interindex starts at 0. 
        }

        public void remove()
        {
            throw new UnsupportedOperationException();
        }

    }
    public void print()//Used to print out the tree heap structure and then the iterator prints out the values in sorted order. 
    {
        for(int i=0; i<size;i++)
        {
            System.out.print(array[i].data + " ");
        }
    }

    protected class Wrapper<E> implements Comparable <Wrapper<E>>//CORRECT!
    {
        long number;    //Keeps priority number.
        E data;

        public Wrapper(E d)
        {
            number=entryNumber++;//sequence number
            data=d;

        }

        public int compareTo(Wrapper<E> a)
        {
            if(((Comparable<E>)data).compareTo(a.data)==0)  //If duplicates, return sequence number to know which duplicate came 1st. 
            {
                return (int)(number-a.number);
            }
            return ((Comparable<E>)data).compareTo(a.data);//Return normal compare to between 2 object if not duplicates

        }
    }

    private void trickleUp(int newIndex)//CORRECT!
    {

        int parentIndex=(newIndex-1) >>1;
        Wrapper<E> newValue=array[newIndex];
        while(parentIndex >=0 && newValue.compareTo(array[parentIndex])<0) 
        {
            array[newIndex]=array[parentIndex];
            newIndex=parentIndex;
            parentIndex=(parentIndex-1) >> 1;

        }
        array[newIndex]=newValue;

    }

    private void trickleDown(int current)//CORRECT!
    {

        int child=getNextChild(current);
        while (child!=-1 && array[current].compareTo(array[child])<0 && array[child].compareTo(array[size-1])<0)
        {
            array[current]=array[child];
            current=child;
            child=getNextChild(current);

        }
        array[current]=array[size-1];

    }

    private int getNextChild(int current)//CORRECT
    {
        int left=(current<<1)+1;
        int right= left+1;
        if(right<size)
        {
            if(array[left].compareTo(array[right])<0)
                return left;
            return right;
        }
        if(left<size)
            return left;
        return -1;

    }
}


