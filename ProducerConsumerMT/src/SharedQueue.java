import java.util.LinkedList;

public class SharedQueue<E> {

	   private LinkedList<E> list;
	    private int size;

	    public SharedQueue() {
	    }

	    public SharedQueue(int size) {
	        this.list = new LinkedList<>();
	        this.size = size;
	    }

	    public void add(E e) {
	        list.add(e);
	    }

	    public E remove() {
	        return list.remove(0);
	    }

	    public boolean isFull() {
	        return list.size() >= size;
	    }

	    public boolean isEmpty() {
	        return list.isEmpty();
	    }

	    public int getSize() {
	        return list.size();
	    }
}
