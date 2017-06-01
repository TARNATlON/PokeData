package me.hugmanrique.pokedata.compression.huff;

import lombok.Getter;

import java.util.Iterator;

/**
 * @author Hugmanrique
 * @since 29/05/2017
 */
public class LinkedList<E> implements Iterable<E> {
    @Getter
    private LinkedListNode<E> head, tail;

    public LinkedList() {}

    public void addFirst(LinkedListNode<E> node) throws Exception {
        if (head != null || tail != null) {
            throw new Exception("Both head and tails must be null");
        }

        head = tail = node;
        node.setNext(null);
        node.setPrevious(null);
    }

    public void addFirst(E value) throws Exception {
        addFirst(new LinkedListNode<>(value));
    }

    public void removeFirst() {
        if (head == null) {
            return;
        }

        LinkedListNode<E> newHead = head.getNext();
        head.remove();
        this.head = newHead;
    }

    public void addLast(LinkedListNode<E> node) throws Exception {
        if (head != null || tail != null) {
            throw new Exception("Both head and tails must be null");
        }

        head = tail = node;
        node.setNext(null);
        node.setPrevious(null);
    }

    public void addLast(E value) throws Exception {
        addLast(new LinkedListNode<>(value));
    }

    public void removeLast() {
        if (tail == null) {
            return;
        }

        LinkedListNode<E> newTail = tail.getPrevious();

        tail.remove();
        this.tail = newTail;
    }

    public void clear() {
        while (!this.isEmpty()) {
            this.removeFirst();
        }
    }

    public boolean isEmpty() {
        return this.head == null;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            LinkedListNode<E> current = getHead();
            boolean removed = false;

            @Override
            public boolean hasNext() {
                return current != null && current.getNext() != null;
            }

            @Override
            public E next() {
                removed = false;

                if (current != null) {
                    current = current.getNext();
                }

                return current != null ? current.getValue() : null;
            }

            @Override
            public void remove() {
                if (removed || this.current == null) {
                    return;
                }

                removed = true;
                current.remove();
            }
        };
    }
}
