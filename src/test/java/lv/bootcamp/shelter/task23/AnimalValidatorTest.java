package lv.bootcamp.shelter.task23;

import lv.bootcamp.shelter.model.Animal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tasks 2 & 3: Parameterized tests and exception tests
 *
 * Practice:
 * - @ParameterizedTest with @CsvSource
 * - @ValueSource and @NullAndEmptySource
 * - assertThrows with message checks
 * - AssertJ assertThatThrownBy
 *
 * Instructions:
 * Write tests for AnimalValidator. Each TODO describes one test to write.
 */
@DisplayName("AnimalValidator")
class AnimalValidatorTest {

    private AnimalValidator validator;

    @BeforeEach
    void setUp() {
        validator = new AnimalValidator();
    }

    // ==================== Task 2: Parameterized tests ====================

    @Nested
    @DisplayName("validateName")
    class ValidateName {

        @ParameterizedTest
        @ValueSource(strings = {"Buddy", "Luna", "Mr. Whiskers", "X"})
        @DisplayName("accepts valid names")
        void shouldAcceptValidNames(String name) {
            assertDoesNotThrow(() -> validator.validateName(name));
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"   ", "\t", "\n"})
        @DisplayName("rejects blank or null names")
        void shouldRejectBlankNames(String name) {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> validator.validateName(name));
            assertTrue(exception.getMessage().contains("must not be blank"));
        }

        @Test
        @DisplayName("rejects name longer than 100 characters")
        void shouldRejectOverlyLongName() {
            String longString = "A".repeat(101);
            Exception e = assertThrows(IllegalArgumentException.class, () -> validator.validateName(longString));
            assertTrue(e.getMessage().contains("100 characters"));
        }
    }

    @Nested
    @DisplayName("validateAge")
    class ValidateAge {

        @ParameterizedTest
        @CsvSource({
                "0",
                "1",
                "10",
                "50"
        })
        @DisplayName("accepts valid ages")
        void shouldAcceptValidAges(int age) {
            assertDoesNotThrow(() -> validator.validateAge(age));
        }

        @ParameterizedTest
        @CsvSource({
                "-1, must not be negative",
                "-100, must not be negative",
                "51, seems unrealistic",
                "999, seems unrealistic"
        })
        @DisplayName("rejects invalid ages with correct message")
        void shouldRejectInvalidAges(int age, String expectedMessagePart) {
            Exception e = assertThrows(IllegalArgumentException.class, () -> validator.validateAge(age));
            assertTrue(e.getMessage().contains(expectedMessagePart));
        }
    }

    // ==================== Task 3: Exception tests ====================

    @Nested
    @DisplayName("validate (full animal)")
    class ValidateFullAnimal {

        @Test
        @DisplayName("throws NullPointerException for null animal")
        void shouldThrowForNullAnimal() {
            Exception e = assertThrows(NullPointerException.class, () -> validator.validate(null));
            assertTrue(e.getMessage().contains("must not be null"));
        }

        @Test
        @DisplayName("throws for animal with blank name")
        void shouldThrowForBlankName() {
            Animal blankNameAnimal = new Animal(
                    "",
                    "Cat",
                    2, true,
                    LocalDate.of(2026, Month.JULY,2));
            assertThrows(IllegalArgumentException.class, () -> validator.validate(blankNameAnimal));
        }

        @Test
        @DisplayName("throws for animal with blank species")
        void shouldThrowForBlankSpecies() {
            Animal blankSpeciesAnimal = new Animal(
                    "Meowingtons",
                    "",
                    2, true,
                    LocalDate.of(2026, Month.JULY,2));
            assertThrows(IllegalArgumentException.class, () -> validator.validate(blankSpeciesAnimal));
        }

        @Test
        @DisplayName("throws for animal with negative age")
        void shouldThrowForNegativeAge() {
            Animal negativeAgeAnimal = new Animal(
                    "Wooffingtons",
                    "Dog",
                    -2, true,
                    LocalDate.of(2026, Month.JULY,2));
            assertThrows(IllegalArgumentException.class, () -> validator.validate(negativeAgeAnimal));
        }

        @Test
        @DisplayName("does not throw for fully valid animal")
        void shouldPassForValidAnimal() {
            Animal validAnimal = new Animal(
                    "Buddy",
                    "Dog",
                    3, true,
                    LocalDate.of(2026, Month.JULY,2));
            assertDoesNotThrow(() -> validator.validate(validAnimal));
        }
    }
}
