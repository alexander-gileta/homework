import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class ContactSorter {

    public static List<String> parseContacts(List<String> contacts, String order) {
        if (contacts == null || contacts.isEmpty()) {
            return new ArrayList<>();
        }

        Comparator<String> comparator = (contact1, contact2) -> {
            String lastName1 = getLastName(contact1);
            String lastName2 = getLastName(contact2);
            return lastName1.compareTo(lastName2);
        };

        if ("DESC".equals(order)) {
            comparator = comparator.reversed();
        }

        contacts.sort(comparator);
        return contacts;
    }

    private static String getLastName(String contact) {
        String[] parts = contact.split(" ");
        return parts.length > 1 ? parts[1] : parts[0];
    }

    public static void main(String[] args) {
        System.out.println(parseContacts(Arrays.asList("John Locke", "Thomas Aquinas", "David Hume", "Rene Descartes"), "ASC"));
        System.out.println(parseContacts(Arrays.asList("Paul Erdos", "Leonhard Euler", "Carl Gauss"), "DESC"));
        System.out.println(parseContacts(new ArrayList<>(), "DESC"));
        System.out.println(parseContacts(null, "DESC"));
    }
}
