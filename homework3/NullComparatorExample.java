import java.util.Comparator;
import java.util.TreeMap;

import static org.assertj.core.api.Assertions.assertThat;

public class NullComparatorExample {

    public static void main(String[] args) {
        Comparator<String> nullFriendlyComparator = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (o1 == null && o2 == null) return 0;
                if (o1 == null) return -1;
                if (o2 == null) return 1;
                return o1.compareTo(o2);
            }
        };

        TreeMap<String, String> tree = new TreeMap<>(nullFriendlyComparator);
        
        tree.put(null, "test");

        assertThat(tree.containsKey(null)).isTrue();
    }
}
