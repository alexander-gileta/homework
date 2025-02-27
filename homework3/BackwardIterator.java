import java.util.Iterator;
import java.util.List;

public class BackwardIterator<T> implements Iterator<T> {
    private List<T> list;
    private int index;

    public BackwardIterator(List<T> list) {
        this.list = list;
        this.index = list.size() - 1;
    }

    @Override
    public boolean hasNext() {
        return index >= 0;
    }

    @Override
    public T next() {
        if (hasNext()) {
            return list.get(index--);
        }
        return null;
    }

    public static void main(String[] args) {
        List<Integer> numbers = List.of(1, 2, 3);
        Iterator<Integer> backwardIterator = new BackwardIterator<>(numbers);

        while (backwardIterator.hasNext()) {
            System.out.println(backwardIterator.next());
        }
    }
}
