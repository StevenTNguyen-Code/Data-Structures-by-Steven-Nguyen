package data_structures;

/**
 * Write a description of class Sequence here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Sequence   //USED to check which number came in first and has highest priority. 
{
    private class PrioritizedItem implements Comparable<PrioritizedItem>{
        private int priority;
        private int itemNumber;

        public PrioritizedItem(int p, int n) 
        {
            priority = p;
            itemNumber = n;
        }

        public int compareTo(PrioritizedItem item) 
        {
            return priority - item.priority;
        }

        public String toString() {
            return "Priority: " + priority + " Item Number: " + itemNumber;
        }

        public int getPriority() 
        {
            return priority;
        }

        public int getSequenceNumber() 
        {
            return itemNumber;
        }
    }
}
