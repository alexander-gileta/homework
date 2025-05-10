import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BracketClusterizer {

    public static List<String> clusterize(String s) {
        List<String> clusters = new ArrayList<>();
        Stack<Integer> stack = new Stack<>();
        StringBuilder currentCluster = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            currentCluster.append(c);

            if (c == '(') {
                stack.push(i);
            } else if (c == ')') {
                stack.pop();
                if (stack.isEmpty()) {
                    clusters.add(currentCluster.toString());
                    currentCluster.setLength(0);
                }
            }
        }

        return clusters;
    }

    public static void main(String[] args) {
        System.out.println(clusterize("()()()"));
        System.out.println(clusterize("((()))"));
        System.out.println(clusterize("((()))(())()()(()())"));
        System.out.println(clusterize("((())())(()(()()))"));
    }
}
