import java.util.Arrays;

public class DynamicIntArray implements DynamicArray {
    private int[] array;
    private int size;

    public DynamicIntArray() {
        size = 0;
        array = new int[10];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(int element) {
        return indexOf(element) != -1;
    }

    @Override
    public boolean add(int e) {
        ensureCapacity(size + 1);
        array[size++] = e;
        return true;
    }

    @Override
    public boolean containsAll(DynamicArray c) {
        for (int i = 0; i < c.size(); i++) {
            if (!contains(c.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(DynamicArray c) {
        boolean modified = false;
        for (int i = 0; i < c.size(); i++) {
            if (add(c.get(i))) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean addAll(int index, DynamicArray c) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        ensureCapacity(size + c.size());
        System.arraycopy(array, index, array, index + c.size(), size - index);
        for (int i = 0; i < c.size(); i++) {
            array[index + i] = c.get(i);
        }
        size += c.size();
        return true;
    }

    @Override
    public boolean removeAll(DynamicArray c) {
        boolean modified = false;
        for (int i = 0; i < c.size(); i++) {
            while (remove(c.get(i)) != -1) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(DynamicArray c) {
        boolean modified = false;
        for (int i = 0; i < size; i++) {
            if (!c.contains(array[i])) {
                remove(i);
                modified = true;
                i--; 
            }
        }
        return modified;
    }

    @Override
    public void clear() {
        size = 0;
    }

    @Override
    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return array[index];
    }

    @Override
    public int set(int index, int element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        int oldValue = array[index];
        array[index] = element;
        return oldValue;
    }

    @Override
    public void add(int index, int element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        ensureCapacity(size + 1);
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = element;
        size++;
    }

    @Override
    public int remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        int removedElement = array[index];
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(array, index + 1, array, index, numMoved);
        }
        size--;
        return removedElement;
    }

    @Override
    public int indexOf(int element) {
        for (int i = 0; i < size; i++) {
            if (array[i] == element) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(int element) {
        for (int i = size - 1; i >= 0; i--) {
            if (array[i] == element) {
                return i; 
            }
        }
        return -1;
    }
}