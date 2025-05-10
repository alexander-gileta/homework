import java.util.*;

record Person(int id, String name, String address, String phoneNumber) {}

interface PersonDatabase {
    void add(Person person);
    void delete(int id);
    List<Person> findByName(String name);
    List<Person> findByAddress(String address);
    List<Person> findByPhone(String phone);
}

class CacheDatabase implements PersonDatabase {
    private final Map<Integer, Person> personById = new HashMap<>();
    private final Map<String, Set<Person>> personByName = new HashMap<>();
    private final Map<String, Set<Person>> personByAddress = new HashMap<>();
    private final Map<String, Set<Person>> personByPhone = new HashMap<>();

    @Override
    public synchronized void add(Person person) {
        personById.put(person.id(), person);
        personByName.computeIfAbsent(person.name(), k -> new HashSet<>()).add(person);
        personByAddress.computeIfAbsent(person.address(), k -> new HashSet<>()).add(person);
        personByPhone.computeIfAbsent(person.phoneNumber(), k -> new HashSet<>()).add(person);
    }

    @Override
    public synchronized void delete(int id) {
        Person person = personById.remove(id);
        if (person != null) {
            personByName.get(person.name()).remove(person);
            personByAddress.get(person.address()).remove(person);
            personByPhone.get(person.phoneNumber()).remove(person);
        }
    }

    @Override
    public synchronized List<Person> findByName(String name) {
        return new ArrayList<>(personByName.getOrDefault(name, Collections.emptySet()));
    }

    @Override
    public synchronized List<Person> findByAddress(String address) {
        return new ArrayList<>(personByAddress.getOrDefault(address, Collections.emptySet()));
    }

    @Override
    public synchronized List<Person> findByPhone(String phone) {
        return new ArrayList<>(personByPhone.getOrDefault(phone, Collections.emptySet()));
    }
}

public class Main {
    public static void main(String[] args) {
        PersonDatabase db = new CacheDatabase();

        db.add(new Person(1, "Alice", "123 Main St", "000"));
        db.add(new Person(2, "Bob", "456 Small St", "010"));
        db.add(new Person(3, "Charlie", "789 Big St", "100"));

        List<Person> peopleByName = db.findByName("Alice");
        System.out.println("People with name Alice: " + peopleByName);

        List<Person> peopleByAddress = db.findByAddress("456 Small St");
        System.out.println("People with address 456 Small St: " + peopleByAddress);

        List<Person> peopleByPhone = db.findByPhone("100");
        System.out.println("People with phone 100: " + peopleByPhone);

        db.delete(2);

        List<Person> peopleByNameAfterDelete = db.findByName("Bob");
        System.out.println("People with name Bob after delete: " + peopleByNameAfterDelete);
    }
}
