package me.hugmanrique.pokedata.compression.huff;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Hugmanrique
 * @since 29/05/2017
 */
@Getter
public class LinkedListNode<E> {
    @Setter
    private E value;
    private LinkedListNode<E> previous = null, next = null;

    public LinkedListNode(E value) {
        this.value = value;
    }

    public void setNext(LinkedListNode<E> next) {
        if (this.next != null && this.next.getPrevious() == this) {
            this.next.previous = null;
        }

        this.next = next;

        if (this.next != null) {
            this.next.previous = this;
        }
    }

    public void setPrevious(LinkedListNode<E> previous) {
        if (this.previous != null && this.previous.next == this) {
            this.previous.next = null;
        }

        this.previous = previous;

        if (this.previous != null) {
            this.previous.next = this;
        }
    }

    /**
     * Add this node after another node
     * @param node The node this node should be after
     */
    public void addAfter(LinkedListNode<E> node) {
        assert node != null : "Cannot add a node after null";

        LinkedListNode<E> next = node.next;
        setPrevious(node);
        setNext(next);
    }

    /**
     * Add this node before another node
     * @param node The node this node should be before
     */
    public void addBefore(LinkedListNode<E> node) {
        assert node != null : "Cannot add a node before null";

        LinkedListNode<E> prev = node.previous;

        setNext(node);
        setPrevious(prev);
    }

    public void remove() {
        if (previous != null) {
            previous.setNext(next);
        }

        if (next != null) {
            next.setPrevious(previous);
        }
    }
}
