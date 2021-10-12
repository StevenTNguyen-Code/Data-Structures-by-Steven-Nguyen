//Steven Nguyen
//cssc1505

package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;
public class Stack <E extends Comparable<E>> implements Iterable<E>
{
    private LinearList<E> list2;
    
    public Stack()
    {
        list2=new LinearList<E>();
    }
    
    /* inserts the object obj into the stack
     */
    public void push(E obj)
    {
        list2.addFirst(obj);    //WORKS
    }

    /* pops and returns the element on the top of the stack
     */
    public E pop()
    {
        return list2.removeFirst();//WORKS!
    }

    /* returns the number of elements currently in the stack
     */
    public int size()
    {
        return list2.size();//WORKS!
    }

    /* return true if the stack is empty, otherwise false
     */
    public boolean isEmpty()
    {
        return list2.isEmpty();//WORKS!
        
    }

    /* returns but does not remove the element on the top of the stack
     */
    public E peek()
    {
        return list2.peekFirst();//WORKS
        
    }

    /* returns true if the object obj is in the stack,
     * otherwise false
     */
    public boolean contains(E obj)
    {
        return list2.contains(obj);//WORKS!
    }

    
    /* returns the stack to an empty state
     */
    public void makeEmpty()
    {
        list2.clear();//WORKS!
    }

    /* removes the Object obj if it is in the stack and
     * returns true, otherwise returns false.
     */
    public boolean remove(E obj)
    {
        return list2.remove(obj)!=null;
    }

    /* returns a iterator of the elements in the stack. The elements
     * must be in the same sequence as pop() would return them.
     */
    public Iterator<E> iterator()
    {
        return list2.iterator();//WORKS!
        
    }
}