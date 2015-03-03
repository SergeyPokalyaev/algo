import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created with IntelliJ IDEA.
 * User: spokalyaev
 * Date: 09.12.13
 * Time: 12:13
 * To change this template use File | Settings | File Templates.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Object[] array;
    private int size;

    public RandomizedQueue() {
        array = new Object[4];
    }

    public boolean isEmpty() {
        return size == 0 ? true : false;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        size++;
        if (size == array.length) {
            Object[] tempRefArray = array;
            array = new Object[tempRefArray.length * 2];
            for (int i = 0; i < tempRefArray.length; i++) {
                array[i] = tempRefArray[i];
            }
        }
        array[size - 1] = item;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        randomizeArray();

        Object ref = array[size - 1];
        size--;

        if (size >= 4 && size * 4 == array.length) {
            Object[] tempRefArray = array;
            array = new Object[size * 2];
            for (int i = 0; i < size; i++) {
                array[i] = tempRefArray[i];
            }
        }

        return (Item) ref;
    }

    private void randomizeArray() {
        int randPlace = StdRandom.uniform(size);
        Object tempRef = array[randPlace];
        array[randPlace] = array[size - 1];
        array[size - 1] = tempRef;
    }

    public Item sample() {
        randomizeArray();
        return (Item) array[size - 1];
    }

    public Iterator<Item> iterator() {

        Iterator<Item> iterator = new Iterator<Item>() {

            int cursor = 0;

            @Override
            public boolean hasNext() {
                return cursor != size;
            }

            @Override
            public Item next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                cursor++;
                return (Item)array[cursor - 1];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };

        return iterator;
    }
}