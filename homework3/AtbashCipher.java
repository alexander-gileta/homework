public class AtbashCipher {

    public static String atbash(String s) {
        StringBuilder result = new StringBuilder();
        
        for (char c : s.toCharArray()) {
            if (Character.isLowerCase(c)) {
                result.append((char) ('a' + ('z' - c)));
            } else if (Character.isUpperCase(c)) {
                result.append((char) ('A' + ('Z' - c)));
            } else {
                result.append(c);
            }
        }
        
        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println(atbash("Hello world!"));
        System.out.println(atbash("Any fool can write code that a computer can understand. Good programmers write code that humans can understand. ― Martin Fowler"));
    }
}