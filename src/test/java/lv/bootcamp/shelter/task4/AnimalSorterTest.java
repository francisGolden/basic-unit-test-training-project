package lv.bootcamp.shelter.task4;

import lv.bootcamp.shelter.model.Animal;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * Task 4: Collection and sorting tests
 *
 * Practice:
 * - AssertJ list assertions (extracting, containsExactly)
 * - Testing sort order
 * - Testing null/empty input handling
 *
 * Instructions:
 * Write tests for AnimalSorter. Use AssertJ's extracting() and containsExactly()
 * to verify the order of results.
 */
@DisplayName("AnimalSorter")
class AnimalSorterTest {

    private AnimalSorter sorter;

    private Animal buddy;
    private Animal luna;
    private Animal max;
    private Animal bella;

    @BeforeEach
    void setUp() {
        sorter = new AnimalSorter();
        buddy = new Animal("Buddy", "Dog", 3, true, LocalDate.of(2026, 1, 15));
        luna = new Animal("Luna", "Cat", 2, true, LocalDate.of(2026, 1, 10));
        max = new Animal("Max", "Dog", 5, false, LocalDate.of(2026, 1, 20));
        bella = new Animal("Bella", "Cat", 1, true, LocalDate.of(2026, 1, 5));
    }

    // --- sortByAge ---

    @Test
    @DisplayName("sortByAge: returns animals ordered youngest to oldest")
    void shouldSortByAgeAscending() {
        List<Animal> result = sorter.sortByAge(List.of(buddy, luna, max, bella));
        assertThat(result).extracting(Animal::getName).containsExactly("Bella", "Luna", "Buddy", "Max");
    }

    @Test
    @DisplayName("sortByAge: returns empty list for null input")
    void shouldReturnEmptyForNullInput() {
        List<Animal> result = sorter.sortByAge(null);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("sortByAge: returns empty list for empty input")
    void shouldReturnEmptyForEmptyInput() {
        List<Animal> result = sorter.sortByAge(List.of());
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("sortByAge: does not modify the original list")
    void shouldNotModifyOriginalList() {
        List<Animal> animalList = List.of(buddy, luna, max, bella);
        sorter.sortByAge(animalList);
        assertThat(animalList).extracting(Animal::getName).containsExactly("Buddy", "Luna", "Max", "Bella");
    }

    // --- sortByName ---

    @Test
    @DisplayName("sortByName: returns animals in alphabetical order")
    void shouldSortByNameAlphabetically() {
        List<Animal> sortedAnimals = sorter.sortByName(List.of(buddy, luna, max, bella));
        assertThat(sortedAnimals).extracting(Animal::getName).containsExactly("Bella", "Buddy", "Luna", "Max");
    }

    @Test
    @DisplayName("sortByName: is case-insensitive")
    void shouldSortNamesCaseInsensitively() {
        // TODO: Create animals with mixed case names (e.g., "zebra", "Alpha")
        Animal bonny = new Animal(
                "Bonny",
                "rabbit",
                3,
                true,
                LocalDate.of(2026, Month.JULY, 2)
        );
        Animal donnie = new Animal(
                "donnie",
                "dog",
                3, false,
                LocalDate.of(2026, Month.JULY, 2)
        );
        List <Animal> originalList = List.of(bonny, donnie);
        assertThat(
                sorter.sortByName(originalList))
                .extracting(Animal::getName)
                .containsExactly("Bonny", "donnie");
    }

    // --- sortByIntakeDate ---

    @Test
    @DisplayName("sortByIntakeDate: returns animals from earliest to latest")
    void shouldSortByIntakeDateAscending() {
        List<Animal> sortedAnimalsByDate = sorter.sortByIntakeDate(List.of(buddy, luna, max, bella));
        assertThat(sortedAnimalsByDate).extracting(Animal::getIntakeDate).containsExactly(
                LocalDate.of(2026, Month.JANUARY, 5),
                LocalDate.of(2026, Month.JANUARY, 10),
                LocalDate.of(2026, Month.JANUARY, 15),
                LocalDate.of(2026, Month.JANUARY, 20)
        );
    }

    // --- sortBySpeciesThenAgeDescending ---

    @Test
    @DisplayName("sortBySpeciesThenAgeDescending: groups by species then orders by age desc")
    void shouldSortBySpeciesThenAgeDesc() {
        List<Animal> sortedAnimalsBySpecies = sorter.sortBySpeciesThenAgeDescending(List.of(buddy, luna, max, bella));
        assertThat(sortedAnimalsBySpecies)
                .extracting(Animal::getName)
                .containsExactly("Luna", "Bella", "Max", "Buddy");
    }
}
