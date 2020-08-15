package net.melonl;


public class Stack<E> {

    private int size;
    private Node<E> header;

    public Stack() {
        header = null;
        size = 0;
    }

    public void push(E e) {
        header = new Node(e, header);
        ++size;
    }

    public E pop() {
        if (size == 0 || header == null) throw new RuntimeException("pop() failed: stack is empty!");
        E retdata = header.data;
        header = header.next;
        --size;
        return retdata;
    }

    public E top() {
        if (size == 0 || header == null) throw new RuntimeException("top() failed: stack is empty!");
        else return header.data;
    }

    public int getSize() {
        return this.size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node p = header;
        while (p != null) {
            sb.append(p.data.toString());
            if (p.next != null) sb.append(", ");
            p = p.next;
        }
        sb.append("] size = " + this.size);
        return sb.toString();
    }

    private class Node<E> {
        public E data;
        public Node next;

        public Node(E e, Node next) {
            data = e;
            this.next = next;
        }

        public Node(E e) {
            this(e, null);
        }

        public Node() {
            this(null, null);
        }
    }
}
