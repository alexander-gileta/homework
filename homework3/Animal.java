import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AnimalStream {
    public static void main(String[] args) {
        List<Animal> animals = List.of(
            new Animal("Cat", Animal.Type.CAT, Animal.Sex.F, 2, 30, 4, true),
            new Animal("Dog", Animal.Type.DOG, Animal.Sex.M, 3, 50, 10, false),
            new Animal("Bird", Animal.Type.BIRD, Animal.Sex.F, 1, 15, 1, false),
            new Animal("Spider", Animal.Type.SPIDER, Animal.Sex.M, 4, 5, 0, true),
            new Animal("Fish", Animal.Type.FISH, Animal.Sex.F, 1, 5, 0, false),
            new Animal("Big Black Dog", Animal.Type.DOG, Animal.Sex.M, 3, 70, 20, false),
            new Animal("Giant Red Spider", Animal.Type.SPIDER, Animal.Sex.M, 5, 15, 5, true)
        );

        List<Animal> sortedByHeight = animals.stream()
            .sorted((a1, a2) -> Integer.compare(a1.height(), a2.height()))
            .collect(Collectors.toList());

        System.out.println("Animals by height:");
        sortedByHeight.forEach(animal -> System.out.println(animal.name() + " - " + animal.height()));

        int k = 3;
        List<Animal> topKByWeight = animals.stream()
            .sorted((a1, a2) -> Integer.compare(a2.weight(), a1.weight()))
            .limit(k)
            .collect(Collectors.toList());

        System.out.println("\nTop " + k + " animals by weight:");
        topKByWeight.forEach(animal -> System.out.println(animal.name() + " - " + animal.weight()));

        Map<Animal.Type, Integer> animalCountByType = animals.stream()
            .collect(Collectors.groupingBy(Animal::type, Collectors.summingInt(e -> 1)));

        System.out.println("\nAnimal count by type:");
        animalCountByType.forEach((type, count) -> System.out.println(type + ": " + count));

        Animal animalWithLongestName = animals.stream()
            .max((a1, a2) -> Integer.compare(a1.name().length(), a2.name().length()))
            .orElseThrow();

        System.out.println("\nAnimal with the longest name:");
        System.out.println(animalWithLongestName.name() + " - " + animalWithLongestName.name().length());

        long maleCount = animals.stream()
            .filter(animal -> animal.sex() == Animal.Sex.M)
            .count();

        long femaleCount = animals.stream()
            .filter(animal -> animal.sex() == Animal.Sex.F)
            .count();

        System.out.println("\nMore animals are:");
        if (maleCount > femaleCount) {
            System.out.println("Males");
        } else if (femaleCount > maleCount) {
            System.out.println("Females");
        } else {
            System.out.println("Equal");
        }

        Map<Animal.Type, Animal> heaviestAnimalByType = animals.stream()
            .collect(Collectors.groupingBy(
                Animal::type,
                Collectors.maxBy((a1, a2) -> Integer.compare(a1.weight(), a2.weight()))
            ))
            .entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> entry.getValue().orElseThrow()
            ));

        System.out.println("\nHeaviest animal by type:");
        heaviestAnimalByType.forEach((type, animal) -> 
            System.out.println(type + ": " + animal.name() + " - " + animal.weight()));

        int kOldest = 2;
        Animal kthOldestAnimal = animals.stream()
            .sorted((a1, a2) -> Integer.compare(a2.age(), a1.age()))
            .skip(kOldest - 1)
            .findFirst()
            .orElseThrow();

        System.out.println("\n" + kOldest + "-oldest animal");
        System.out.println(kthOldestAnimal.name() + " - " + kthOldestAnimal.age());

        Optional<Animal> heaviestAnimalUnderKcm = animals.stream()
            .filter(animal -> animal.height() < k)
            .max((a1, a2) -> Integer.compare(a1.weight(), a2.weight()));

        System.out.println("\nHeaviest animal under " + k + " cm:");
        heaviestAnimalUnderKcm.ifPresent(animal -> 
            System.out.println(animal.name() + " - " + animal.weight()));

        int totalPaws = animals.stream()
            .mapToInt(Animal::paws)
            .sum();

        System.out.println("\nTotal number of paws:");
        System.out.println(totalPaws);

        List<Animal> animalsWithMismatchAgeAndPaws = animals.stream()
            .filter(animal -> animal.age() != animal.paws())
            .collect(Collectors.toList());

        System.out.println("\nAnimals with mismatched age and paws:");
        animalsWithMismatchAgeAndPaws.forEach(animal -> 
            System.out.println(animal.name()));

        List<Animal> animalsThatCanBiteAndAreTallerThan100cm = animals.stream()
            .filter(animal -> animal.bites() && animal.height() > 100)
            .collect(Collectors.toList());

        System.out.println("\nAnimals that can bite and are taller than 100 cm:");
        animalsThatCanBiteAndAreTallerThan100cm.forEach(animal -> 
            System.out.println(animal.name() + " - Height: " + animal.height() + ", Can bite: " + animal.bites()));

        long countAnimalsWithWeightGreaterThanHeight = animals.stream()
            .filter(animal -> animal.weight() > animal.height())
            .count();

        System.out.println("\nNumber of animals with weight greater than height:");
        System.out.println(countAnimalsWithWeightGreaterThanHeight);

        List<Animal> animalsWithNamesMoreThanTwoWords = animals.stream()
            .filter(animal -> animal.name().split("\\s+").length > 2)
            .collect(Collectors.toList());

        System.out.println("\nAnimals with names consisting of more than two words:");
        animalsWithNamesMoreThanTwoWords.forEach(animal -> 
            System.out.println(animal.name()));

        boolean hasDogTallerThanK = animals.stream()
            .filter(animal -> animal.type() == Animal.Type.DOG)
            .anyMatch(dog -> dog.height() > k);

        System.out.println("\nIs there a dog taller than " + k + " cm?");
        System.out.println(hasDogTallerThanK ? "Yes" : "No");

        int lowerAge = 2;
        int upperAge = 4;
        Map<Animal.Type, Integer> totalWeightByTypeInAgeRange = animals.stream()
            .filter(animal -> animal.age() >= lowerAge && animal.age() <= upperAge)
            .collect(Collectors.groupingBy(
                Animal::type, 
                Collectors.summingInt(Animal::weight)
            ));

        System.out.println("\nTotal weight of animals by type (age " + lowerAge + " to " + upperAge + "):");
        totalWeightByTypeInAgeRange.forEach((type, totalWeight) -> 
            System.out.println(type + ": " + totalWeight));

        List<Animal> sortedByTypeSexAndName = animals.stream()
            .sorted(Comparator.comparing(Animal::type)
                    .thenComparing(Animal::sex)
                    .thenComparing(Animal::name))
            .collect(Collectors.toList());

        System.out.println("\nAnimals sorted by type, sex, and name:");
        sortedByTypeSexAndName.forEach(animal -> 
            System.out.println(animal.name() + " - " + animal.type() + " - " + animal.sex()));

        boolean spidersBiteMoreThanDogs = animals.stream()
            .filter(animal -> animal.type() == Animal.Type.SPIDER)
            .mapToInt(animal -> animal.bites() ? 1 : 0)
            .sum() > animals.stream()
            .filter(animal -> animal.type() == Animal.Type.DOG)
            .mapToInt(animal -> animal.bites() ? 1 : 0)
            .sum();

        System.out.println("\nDo spiders bite more than dogs?");
        System.out.println(spidersBiteMoreThanDogs ? "Yes" : "No");

        List<Animal> fishList1 = List.of(new Animal("Goldfish", Animal.Type.FISH, Animal.Sex.F, 1, 5, 1, false));
        List<Animal> fishList2 = List.of(new Animal("Shark", Animal.Type.FISH, Animal.Sex.M, 5, 100, 500, true));

        Animal heaviestFish = Stream.concat(fishList1.stream(), fishList2.stream())
            .max(Comparator.comparingInt(Animal::weight))
            .orElseThrow();

        System.out.println("\nHeaviest fish in multiple lists:");
        System.out.println(heaviestFish.name() + " - " + heaviestFish.weight());

        Map<String, Set<ValidationError>> errors = animals.stream()
            .collect(Collectors.toMap(
                Animal::name,
                animal -> validateAnimal(animal)
            ));

        System.out.println("\nValidation errors:");
        errors.forEach((name, errorList) -> {
            if (!errorList.isEmpty()) {
                System.out.println(name + " - Errors: " + errorList);
            }
        });

        Map<String, String> errorsReadable = errors.entrySet().stream()
            .filter(entry -> !entry.getValue().isEmpty())
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> entry.getValue().stream()
                    .map(ValidationError::getFieldName)
                    .collect(Collectors.joining(", "))
            ));

        System.out.println("\nReadable validation errors:");
        errorsReadable.forEach((name, fieldNames) -> 
            System.out.println(name + " - Fields with errors: " + fieldNames));
    }

    private static Set<ValidationError> validateAnimal(Animal animal) {
        Set<ValidationError> errors = new HashSet<>();
        if (animal.age() <= 0) errors.add(new ValidationError("age"));
        if (animal.height() <= 0) errors.add(new ValidationError("height"));
        if (animal.weight() <= 0) errors.add(new ValidationError("weight"));
        if (animal.name().isEmpty()) errors.add(new ValidationError("name"));
        return errors;
    }

    static class ValidationError {
        private final String fieldName;

        public ValidationError(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getFieldName() {
            return fieldName;
        }

        @Override
        public String toString() {
            return "Invalid field: " + fieldName;
        }
    }
}

public record Animal(
        String name,
        Type type,
        Sex sex,
        int age,
        int height,
        int weight,
        boolean bites
) {
    enum Type {
        CAT, DOG, BIRD, FISH, SPIDER
    }

    enum Sex {
        M, F
    }

    public int paws() {
        return switch (type) {
            case CAT, DOG -> 4;
            case BIRD -> 2;
            case FISH -> 0;
            case SPIDER -> 8;
        };
    }
}
