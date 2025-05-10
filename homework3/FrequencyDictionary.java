import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrequencyDictionary {

    public static <T> Map<T, Integer> freqDict(List<T> list) {
        Map<T, Integer> freqMap = new HashMap<>();
        for (T item : list) {
            freqMap.put(item, freqMap.getOrDefault(item, 0) + 1);
        }
        return freqMap;
    }

    public static void main(String[] args) {
        System.out.println(freqDict(List.of("a", "bb", "a", "bb")));
        System.out.println(freqDict(List.of("this", "and", "that", "and")));
        System.out.println(freqDict(List.of("код", "код", "код", "bug")));
        System.out.println(freqDict(List.of(1, 1, 2, 2)));
    }
}
