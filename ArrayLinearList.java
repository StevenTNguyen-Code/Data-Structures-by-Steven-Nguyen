/**
 *  Program #1
 *  This program is a circular array in which the user can add and remove values, such that if it reaches the a end, it may wrap around.  
 *  CS310-1
 *  Date: 2/16/19
 *  @author  Steven Nguyen cssc1505
 */
package data_structures;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;
public class ArrayLinearList<E extends Comparable<E> > implements LinearListADT<E> 
{
    private int counter;    //Essentially keeps track of our size of the array. Each element added causes this counter to increase which represents the size. 
    private int indexFront;
    private int indexRear;
    private E [] array;
    private long modcounter;
    public ArrayLinearList(int maxCapacity)
    {
        array=(E[]) new Comparable[maxCapacity];
        counter=0;
        indexFront=-1;
        indexRear=-1;
        modcounter=0;
    }

    public ArrayLinearList()
    {
        array= (E[]) new Comparable[DEFAULT_MAX_CAPACITY];
        counter=0;
        indexFront=-1;
        indexRear=-1;
        modcounter=0;
    }

    /* Outputs Front: indexFront Rear: indexRear
     */
    public void ends()
    {
        System.out.println("Front: " + indexFront + " " + "Rear: " + indexRear);
    }

    /* Adds the Object obj to the beginning of list and returns true if the list is not
     * full.
     * returns false and aborts the insertion if the list is full.
     */
    public boolean addFirst(E obj)
    {
        if (isEmpty())//Assuming the array is empty, if we add to the front, the front and rear would be the same. 
        {
            indexFront=0;
            indexRear=0;
            array[0]=obj;
            counter++;
            modcounter++;
            return true;
        }

        if(!isFull())
        {
            if(indexFront==0) //Checks if there is stuff on the left of the front to add to the end. 
            {
                indexFront=array.length-1; 
                array[indexFront]=obj;
                modcounter++;
                counter++;

            }
            else
            {
                array[indexFront-1]=obj;
                counter++;
                modcounter++;
                indexFront-=1;

            }
            return true;
        }
        return false;
    }

    /* Adds the Object obj to the end of list and returns true if the list is not full.
     * returns false and aborts the insertion if the list is full.
     */
    public boolean addLast(E obj)
    {
        if(isEmpty())
        {
            indexFront=array.length-1;
            indexRear=array.length-1;
            array[array.length-1]=obj;
            modcounter++;
            counter++;
            return true;
        }

        if(!isFull())
        {
            if (indexRear==array.length-1)  //If element at the end, move around the array to the other side and put the element there. 
            {
                indexRear=0;
                array[indexRear]=obj;
                modcounter++;
                counter++;

            }
            else    //If the end does not have an element, has space, we can put the element there. 
            {

                array[indexRear+1]=obj;
                counter++;
                modcounter++;
                indexRear+=1;

            }
            return true;
        }
        return false;

    }

    /* Removes and returns the parameter object obj in first position in list if the list
     * is not empty, null if the list is empty.
     */
    public E removeFirst()
    {
        E firstitem=null;
        if (!isEmpty())
        {

            if (indexFront==0)          //Don't need to loop through if it's at the ends. Just change indexes. This is for at the end go to begining again. 
            {
                counter--;
                modcounter++;
                firstitem=array[indexFront];
                indexFront=array.length-1;
                return firstitem;

            }
            if (indexFront==array.length-1)     //Shift ind
            {
                counter--;
                modcounter++;
                firstitem=array[indexFront];
                indexFront=0;
                return firstitem;
            }

            if (indexFront>0 && indexFront<array.length-1)        //Inbetween array then shift index only. 
            {
                counter--;
                modcounter++;
                firstitem=array[indexFront];
                indexFront++;
                return firstitem;

            }

        }
        return firstitem;

    }

    /* Removes and returns the parameter object obj in last position in list if the list
     * is not empty, null if the list is empty.
     */
    public E removeLast()
    {
        E lastitem=null;
        if (!isEmpty())
        {

            if (indexRear==array.length-1)          //Don't need to loop through if it's at the ends. Just change indexes. This is for at the end go to begining again. 
            {
                counter--;
                modcounter++;
                lastitem=array[indexRear];
                indexRear=0;
                return lastitem;

            }
            if (indexRear==0)
            {
                counter--;
                modcounter++;
                lastitem=array[indexRear];
                indexRear=array.length-1;
                return lastitem;
            }

            if (indexRear>0 && indexRear<array.length-1)        //Inbetwen array then shift index only. 
            {
                counter--;
                modcounter++;
                lastitem=array[indexRear];
                indexRear--;
                return lastitem;

            }

        }
        return lastitem;
    }

    /* Removes and returns the parameter object obj from the list if the list contains
     * it, null otherwise. The ordering of the list is preserved. The list may contain
     * duplicate elements. This method removes and returns the first matching element
     * found when traversing the list from first position.
     * Note that you may have to shift elements to fill in the slot where the deleted
     * element was located.
     */
    public E remove(E obj)
    {
        E item=null;
        int index=0;
        if(!isEmpty())          //This part of the remove deals with the middle elements only. Remove at first or last index is in the other methods. 
        {
            if (indexRear>indexFront) //Deals with shifting middle elements as there are 2 scenarios
            {
                for (int m=indexFront;m<indexRear;m++)
                {
                    if(array[m].compareTo(obj)==0)
                    {
                        item=array[m]; //Holds content if it matches
                        counter--;
                        modcounter++;
                        array[m]=array[m+1];
                        return item;
                        
                        
                        
                    }
                    
                }
                
                
                
            }
            if (indexFront>indexRear)
            {
                for (int k=indexFront; k<array.length;k++)
                {
                    if(k==array.length-1)   //If it's at the end, we gotta wrap idea. 
                    {

                        if (array[k].compareTo(obj)==0)
                        {
                            item=array[k];// Holds contents if it matches;
                            index=k;
                            counter--;
                            modcounter++;
                            indexRear--;
                            array[k]=array[0];
                            for (int c=0;c<indexRear;c++)
                            {
                                array[c]=array[c+1];
                                
                            }
                            return item;

                        }

                    }

                    if(array[k].compareTo(obj)==0)
                    {
                        item=array[k];//Holds the contents if it matches. 
                        index=k;
                        counter--;
                        modcounter++;
                        array[k]=array[k+1];
                        return item;

                    }
                }

                

                for (int j=0;j<indexRear;j++)
                {
                    if (array[j].compareTo(obj)==0)
                    {
                        item=array[j];
                        index=j;
                        counter--;
                        modcounter++;
                        array[j]=array[j+1];
                        return item;

                    }

                }

                

            }

        }
        return item; //Will be O(n) no matter what as it can be at the end or the front or middle
    }

    /* Returns the first element in the list, null if the list is empty.
     * The list is not modified.
     */
    public E peekFirst()
    {
        if (isEmpty())
        {
            return null;
        }
        else
        {
            return array[indexFront];
        }

    }

    /* Returns the last element in the list, null if the list is empty.
     * The list is not modified.
     */
    public E peekLast()
    {
        if (isEmpty())
        {
            return null;
        }
        else
        {
            return array[indexRear]; //WORKS!!!
        }

    }

    /* Returns true if the parameter object obj is in the list, false otherwise.
     * The list is not modified.
     */
    public boolean contains(E obj)
    {
        E itemholder=find(obj); //has either null or the object. 

        if(itemholder==null)
        {
            return false;
        }

        return true;

    }

    /* Returns the element matching obj if it is in the list, null otherwise.
     * In the case of duplicates, this method returns the element closest to front.
     * The list is not modified.
     */
    public E find(E obj)
    {
        if (!isEmpty())
        {

            if(indexFront==indexRear)// only 1 element in the array
            {
                return array[indexFront];

            }

            if (indexFront>indexRear)       // first case where we would have to wrap around if the front goes off the edge of the array. 
            {
                for(int i=indexFront;i<array.length;i++)
                {
                    if (array[i].compareTo(obj)==0)
                    {
                        return array[i];

                    }

                    for(int j=0;j<=indexRear;j++)
                    {
                        if (array[j].compareTo(obj)==0)
                        {
                            return array[j];

                        }

                    }

                }
            }

            if(indexRear>indexFront)    //If it's a normal straight forward array. 
            {
                for(int i=indexFront;i<=indexRear;i++)
                {
                    if (array[i].compareTo(obj)==0)
                    {
                        return array[i];

                    }

                }
            }

        }
        return null;
    }

    /* The list is returned to an empty state.
     */
    public void clear()
    {
        indexRear=-1;       //Garbage collector deals with the array so it empties it for us. 
        indexFront=-1;
        counter=0;
        modcounter=0;
    }

    /* Returns true if the list is empty, otherwise false
     */
    public boolean isEmpty()
    {
        return counter == 0;  //WORKS!
    }

    /* Returns true if the list is full, otherwise false
     */
    public boolean isFull()
    {
        return counter == array.length;   //WORKS!
    }

    /* Returns the number of Objects currently in the list.
     */
    public int size()
    {

        return counter; //Counter is our size. WORKS! 

    }

    /* Returns an Iterator of the values in the list, presented in
     * the same order as the underlying order of the list. (front first, rear last)
     */
    public Iterator<E> iterator()
    {
        return new IteratorHelper();

    }

    class IteratorHelper implements Iterator<E>
    {
        private int iterIndex;
        private long countmodcheck;
        private int count;
        public IteratorHelper()
        {
            iterIndex=indexFront;
            countmodcheck=modcounter;
            count=0;
        }

        public boolean hasNext()
        {
            if (countmodcheck!=modcounter)
            {
                throw new ConcurrentModificationException();
            }
            return count!=counter;   //iterator index is not the size, meaning there is stuff inside still. 

        }

        public E next()
        {
            E holder=null;
            if(!hasNext())
            {
                throw new NoSuchElementException();

            }

            if (iterIndex==array.length-1) //it's at the end of the array then loop around.
            {
                holder=array[iterIndex]; //Holds item before it goes back to the front.
                iterIndex=0;    //Goes back to front of array. 
                count++;
            }

            else //If it's not at the end we use it normal. 
            {
                holder=array[iterIndex];
                iterIndex++;
                count++;
            }

            return holder;
        }

        public void remove()
        {
            throw new UnsupportedOperationException();

        }

    }

    public void print()
    {
        for (int i=0; i<array.length;i++)
        {
            System.out.println(array[i]);
        }

    }
}