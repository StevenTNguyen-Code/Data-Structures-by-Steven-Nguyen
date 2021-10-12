//Steven Nguyen
//cssc1505
package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;
public class Queue <E extends Comparable<E>> implements Iterable<E>
{
    private LinearList<E> list1;

    public Queue()
    {
        list1=new LinearList<E>();
    }

    /*inserts the object obj into the queue
     */
    public void enqueue(E obj)
    {
        list1.addLast(obj); //Add at bottom and then like movie line, pushes item to the top which is removed. WORKS!
    }

    /* removes and returns the object at the front of the queue
     */
    public E dequeue()
    {
        return list1.removeFirst();//WORKS!
    }

    /* returns the number of objects currently in the queue
     */
    public int size()
    {
        return list1.size();    //WORKS!
    }

    /* returns true if the queue is empty, otherwise false
     */
    public boolean isEmpty()
    {
        return list1.isEmpty(); //WORKS!

    }

    /* returns but does not remove the object at the front of the queue
     */
    public E peek()
    {
        return list1.peekFirst();//WORKS!
    }

    /* returns true if the Object obj is in the queue
     */
    public boolean contains(E obj)
    {
        return list1.contains(obj);//WORKS
    }

    /* returns the queue to an empty state
     */
    public void makeEmpty()
    {
        list1.clear();//WORKS!
    }

    /* removes the Object obj if it is in the queue and
     * returns true, otherwise returns false.
     */
    public boolean remove(E obj)
    {
        
        return list1.remove(obj)!=null;//WORKS!
    }

    /* returns an iterator of the elements in the queue. The elements
     * must be in the same sequence as dequeue would return them.
     */
    public Iterator<E> iterator()
    {
        return list1.iterator();//WORKS!
    }
}