import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Сергей on 08.12.13.
 */
public class Deque<Item> implements Iterable<Item> {

    private Node firstNode;
    private Node lastNode;
    private int size;

    public Deque() {
    }

    public boolean isEmpty() {
        return size == 0 ? true : false;
    }

    public int size() {
        return size;
    }
    public void addFirst(Item item) {

        if (item == null) {
            throw new NullPointerException();
        }

        size++;
        if (firstNode == null) {
            firstNode = new Node(item);
            lastNode = firstNode;
        } else {
            Node node = new Node(item);
            node.nextNode = firstNode;
            firstNode.prevNode = node;
            firstNode = node;
        }
    }

    public void addLast(Item item) {

        if (item == null) {
            throw new NullPointerException();
        }

        size++;
        if (firstNode == null) {
            firstNode = new Node(item);
            lastNode = firstNode;
        } else {
            Node node = new Node(item);
            lastNode.nextNode = node;
            node.prevNode = lastNode;
            lastNode = node;
        }
    }

    public Item removeFirst() {

        if (size == 0) {
            throw new NoSuchElementException();
        }

        size--;
        Node tempFirstNodeRef = firstNode;
        if (size == 0) {
            firstNode = null;
            lastNode = null;
            return tempFirstNodeRef.item;
        } else {
            firstNode.nextNode.prevNode = null;
            firstNode = firstNode.nextNode;
            return tempFirstNodeRef.item;
        }
    }

    public Item removeLast() {

        if (size == 0) {
            throw new NoSuchElementException();
        }

        size--;
        Node tempLastNodeRef = lastNode;
        if (size == 0) {
            firstNode = null;
            lastNode = null;
            return tempLastNodeRef.item;
        } else {
            lastNode.prevNode.nextNode = null;
            lastNode = lastNode.prevNode;
            return tempLastNodeRef.item;
        }
    }

    public Iterator<Item> iterator() {

        Iterator<Item> iterator = new Iterator<Item>() {

            private Node currentNode = firstNode;

            @Override
            public boolean hasNext() {
                return currentNode != null;
            }

            @Override
            public Item next() {
                if (currentNode == null) {
                    throw new NoSuchElementException();
                }
                Node tempNode = currentNode;
                currentNode = tempNode.nextNode;
                return tempNode.item;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };

        return iterator;
    }

    private class Node {

        private Node nextNode;
        private Node prevNode;
        private Item item;

        public Node(Item item) {
            this.item = item;
        }

    }

}