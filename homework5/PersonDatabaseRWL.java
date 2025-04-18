import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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
    
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public void add(Person person) {
        lock.writeLock().lock();
        try {
            personById.put(person.id(), person);
            personByName.computeIfAbsent(person.name(), k -> new HashSet<>()).add(person);
            personByAddress.computeIfAbsent(person.address(), k -> new HashSet<>()).add(person);
            personByPhone.computeIfAbsent(person.phoneNumber(), k -> new HashSet<>()).add(person);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void delete(int id) {
        lock.writeLock().lock();
        try {
            Person person = personById.remove(id);
            if (person != null) {
                personByName.get(person.name()).remove(person);
                personByAddress.get(person.address()).remove(person);
                personByPhone.get(person.phoneNumber()).remove(person);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public List<Person> findByName(String name) {
        lock.readLock().lock();
        try {
            return new ArrayList<>(personByName.getOrDefault(name, Collections.emptySet()));
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public List<Person> findByAddress(String address) {
        lock.readLock().lock();
        try {
            return new ArrayList<>(personByAddress.getOrDefault(address, Collections.emptySet()));
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public List<Person> findByPhone(String phone) {
        lock.readLock().lock();
        try {
            return new ArrayList<>(personByPhone.getOrDefault(phone, Collections.emptySet()));
        } finally {
            lock.readLock().unlock();
        }
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
