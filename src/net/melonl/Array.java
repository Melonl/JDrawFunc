package net.melonl;

import java.util.Iterator;

public class Array<E> implements Iterable<E> {
    private static final int DEFAULT_CAPACITY = 10;

    private int size;
    private E data[];
    private int capacity;

    private int current;

    public Array(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("capacity is illegal");
        data = (E[]) new Object[capacity];
        this.capacity = capacity;
    }

    public Array() {
        this(DEFAULT_CAPACITY);
    }

    public Array(E[] arr) {
        data = arr;
        capacity = arr.length;
        size = arr.length;
    }

    public int getSize() {
        return this.size;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public E getFirst() {
        if (size < 1) throw new RuntimeException("getFirst() failed: size = " + this.size);
        return data[0];
    }

    public void add(E e, int index) {
        checkParameter(index, false, false);
        if (size + 1 == capacity)
            resize((int) (capacity * 1.5));
        for (int i = size - 1; i >= index; --i)
            data[i + 1] = data[i];
        data[index] = e;
        ++size;
    }

    public void addLast(E e) {
        add(e, size);
    }

    public E get(int index) {
        checkParameter(index, false, false);
        return data[index];
    }

    public E remove(int index) {
        checkParameter(index, false, true);//会检查size为0的情况
        E ret = data[index];
        for (int i = index; i < size - 1; ++i)
            data[i] = data[i + 1];
        --size;

        if (size > 10 && size < capacity / 4)
            resize(capacity / 2);
        return ret;
    }

    public boolean contains(E e) {
        for (int i = 0; i < size; ++i)
            if (data[i] == e) return true;
        return false;
    }

    public int find(E e) {
        for (int i = 0; i < size; ++i)
            if (data[i] == e) return i;
        return -1;
    }

    public void clear() {
        for (int i = 0; i < size; ++i) data[i] = null;
        size = 0;
    }

    @Override
    public Iterator<E> iterator() {
        current = 0;
        return new Iterator<E>() {
            @Override
            public boolean hasNext() {
                return current < size - 1;
            }

            @Override
            public E next() {
                return data[current++];
            }

            @Override
            public void remove() {
                Array.this.remove(current);
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; ++i) {
            sb.append(data[i]);
            if (i != size - 1) sb.append(",");
        }
        sb.append("] size: ");
        sb.append(size);
        sb.append(", capacity: ");
        sb.append(capacity);
        return sb.toString();
    }

    private void resize(int newCapacity) {
        if (newCapacity <= 0) throw new IllegalArgumentException("resize() failed: newCapacity <= 0");
        else if (size == 0) return; // 啥也不用做
        E[] newData = (E[]) new Object[newCapacity];
        System.arraycopy(data, 0, newData, 0, size);
        this.data = newData;
        this.capacity = newCapacity;
    }

    private void checkParameter(int param, boolean checkZero, boolean checkWithSize) {
        if (param < 0 || param > size || (checkZero && param == 0) || (checkWithSize && param == size))
            throw new IllegalArgumentException("parameter " + param + " is illegal");
    }
}
