/* Steven Nguyen
 * cssc1505
 */
package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;
public class LinearList<E extends Comparable<E>> implements LinearListADT<E>
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
    private int counter;// For size
    private long modcounter;//For iterator fail-fast
    public LinearList()
    {
        head=null;
        tail=null;
        counter=0;
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
            counter++;
            modcounter++;
            return true;
        }
        else       //The head pointer would only change and the tail would be the same. Work for if it's only 1 item or more. 
        {
            newNode.next=head;
            head.prev=newNode;
            head=newNode;
            counter++;
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
            counter++;
            modcounter++;
            return true;
        }
        else    //The tail pointer would only change and the tail would be the same. Work for if it's only 1 item or more. 
        {
            tail.next=newNode;
            newNode.prev=tail;
            tail=newNode;
            counter++;
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
        if(head!=null && counter==1)// Only 1 element inside
        {
            //Store data before removing
            dataholder=head.data;
            head=null;
            tail=null;
            counter--;
            modcounter++;
            return dataholder;

        }
        if(head!=null && head.next!=null)//There is more than 1 element inside. 
        {
            dataholder=head.data;
            head.next.prev=null;
            head=head.next;
            counter--;
            modcounter++;
            return dataholder;

        }
        return null;
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
        if(tail!=null && counter==1)// Only 1 element inside
        {
            //Store data before removing
            dataholder=tail.data;
            head=null;
            tail=null;
            counter--;
            modcounter++;
            return dataholder;

        }
        if(tail!=null && tail.prev!=null)//There is more than 1 element inside. 
        {
            dataholder=tail.data;
            tail.prev.next=null;
            tail=tail.prev;
            counter--;
            modcounter++;
            return dataholder;

        }
        return null;

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
        if(counter==1)//Size is 1. Head and Tail point at same spot. WORKS!
        {
            head=null;
            tail=null;
            counter--;
            modcounter++;
            return first.data;
        }

        while(first!=null)  //Loops used to search through contents of the list. 
        {
            if(first.data.compareTo(obj)!=0)//Find the element first. 
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

            if(head.data.compareTo(obj)==0)//Element happens to be at the head.
            {
                E dataholder=head.data;
                head.next.prev=null;
                head=head.next;
                counter--;
                modcounter++;
                return dataholder;

            }
            if(tail.data.compareTo(obj)==0)//Element happens to be at the tail
            {
                E dataholder=tail.data;
                tail.prev.next=null;
                tail=tail.prev;
                counter--;
                modcounter++;
                return dataholder;

            }

            if(first.data.compareTo(obj)==0)//We found the node we want to remove. Element right after head scenario or 
            {
                beginning.next=afterRemove;
                first.prev=null;
                afterRemove.prev=beginning;
                first.next=null;

                counter--;
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
        if(head==null)
        {
            return null;

        }
        else    //If head!=null, this mean the list is not empty and has atleast 1 element in it. 
        {
            return head.data;
        }

    }

    /* Returns the last element in the list, null if the list is empty.
     * The list is not modified.
     */
    public E peekLast()
    {
        if(tail==null)//List is empty
        {
            return null;
        }
        else
        {
            return tail.data;
        }

    }

    /* Returns true if the parameter object obj is in the list, false otherwise.
     * The list is not modified.
     */
    public boolean contains(E obj)
    {
        E dataholder=find(obj);//Has either the data in the node or a null
        if(dataholder==null)//Element is not in there. 
        {
            return false;

        }
        return true;//return true if the element is in there. 
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
            if(beginning.data.compareTo(obj)==0)
            {
                dataholder=obj;
                return dataholder;

            }
            if(beginning.data.compareTo(obj)!=0 && beginning.next!=null)
            {
                beginning=beginning.next;   //Move pointer if there is a node to the right. 

            }
            if(beginning.data.compareTo(obj)!=0 && beginning.next==null)// We reached the end so the element isn't in there. Check last element before returning null
            {
                return null;

            }

        }
        return null;
    }

    /* The list is returned to an empty state.
     */
    public void clear()
    {
        head=null;
        tail=null;
        counter=0;
        modcounter=0;

    }

    /* Returns true if the list is empty, otherwise false
     */
    public boolean isEmpty()
    {
        return counter==0;

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
        return counter;
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

    public void print()
    {

    }
}
