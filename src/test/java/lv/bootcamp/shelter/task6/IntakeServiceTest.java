package lv.bootcamp.shelter.task6;

import lv.bootcamp.shelter.model.Animal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Task 7: Mocking a dependency
 *
 * Practice:
 * - @Mock and @InjectMocks
 * - when(...).thenReturn(...)
 * - verify(...) and verify(..., never())
 * - Testing with mocked dependencies
 *
 * Instructions:
 * Write tests for IntakeService. The AnimalRepository is mocked — you control what it returns.
 * Focus on verifying that IntakeService calls the repository correctly.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("IntakeService")
class IntakeServiceTest {

    @Mock
    private AnimalRepository repository;

    @InjectMocks
    private IntakeService service;

    private final Animal buddy = new Animal("Buddy", "Dog", 3, true, LocalDate.of(2026, 1, 15));

    // ==================== intake() ====================

    @Nested
    @DisplayName("intake")
    class Intake {

        @Test
        @DisplayName("saves valid animal and returns it")
        void shouldSaveValidAnimal() {
            when(repository.save(buddy)).thenReturn(buddy);
            Animal returnedAnimal = service.intake(buddy);
            assertThat(returnedAnimal.getName()).isEqualTo("Buddy");
            verify(repository).save(buddy);
        }

        @Test
        @DisplayName("throws for null animal without calling repository")
        void shouldThrowForNullAnimal() {
            assertThrows(NullPointerException.class, () -> service.intake(null));
            verify(repository, never()).save(any());
        }

        @Test
        @DisplayName("throws for invalid animal without calling repository")
        void shouldThrowForInvalidAnimal() {
            Animal invalid = new Animal("", "Dog", 3, true, LocalDate.now());
            assertThrows(IllegalArgumentException.class, () -> service.intake(invalid));
            verify(repository, never()).save(any());
        }
    }

    // ==================== findByName() ====================

    @Nested
    @DisplayName("findByName")
    class FindByName {

        @Test
        @DisplayName("returns animal when repository finds it")
        void shouldReturnAnimalWhenFound() {
            when(repository.findByName("Buddy")).thenReturn(Optional.of(buddy));
            Animal result = service.findByName("Buddy");
            assertThat(result).isNotNull();
            assertThat(result.getName()).isEqualTo("Buddy");
        }

        @Test
        @DisplayName("returns null when repository does not find it")
        void shouldReturnNullWhenNotFound() {
            // TODO: Stub repository.findByName("Ghost") to return Optional.empty()
            // TODO: Call service.findByName("Ghost")
            // TODO: Assert result is null
        }

        @Test
        @DisplayName("throws for blank name without calling repository")
        void shouldThrowForBlankName() {
            // TODO: Call service.findByName("")
            // TODO: Assert it throws IllegalArgumentException
            // TODO: Verify repository.findByName(any()) was NEVER called
        }
    }

    // ==================== findBySpecies() ====================

    @Nested
    @DisplayName("findBySpecies")
    class FindBySpecies {

        @Test
        @DisplayName("returns list from repository for valid species")
        void shouldReturnAnimalsForValidSpecies() {
            // TODO: Stub repository.findBySpecies("Dog") to return List.of(buddy)
            // TODO: Call service.findBySpecies("Dog")
            // TODO: Assert result has size 1 and contains buddy
        }

        @Test
        @DisplayName("returns empty list for blank species without calling repository")
        void shouldReturnEmptyForBlankSpecies() {
            // TODO: Call service.findBySpecies("")
            // TODO: Assert result is empty
            // TODO: Verify repository.findBySpecies(any()) was NEVER called
        }

        @Test
        @DisplayName("returns empty list for null species without calling repository")
        void shouldReturnEmptyForNullSpecies() {
            // TODO: Call service.findBySpecies(null)
            // TODO: Assert result is empty
            // TODO: Verify repository.findBySpecies(any()) was NEVER called
        }
    }

    // ==================== count() ====================

    @Nested
    @DisplayName("count")
    class Count {

        @Test
        @DisplayName("returns the size of all animals from repository")
        void shouldReturnCountFromRepository() {
            // TODO: Stub repository.findAll() to return a list of 3 animals
            // TODO: Call service.count()
            // TODO: Assert result equals 3
        }

        @Test
        @DisplayName("returns 0 when repository is empty")
        void shouldReturnZeroWhenEmpty() {
            // TODO: Stub repository.findAll() to return List.of()
            // TODO: Call service.count()
            // TODO: Assert result equals 0
        }
    }
}
