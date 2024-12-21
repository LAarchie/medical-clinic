package edu.wsb.po;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    @Test
    void getAge() {
        LocalDate dateOfBirth = LocalDate.of(2010, 11, 10);

        int age = Person.getAge(dateOfBirth);

        LocalDate currentDate = LocalDate.now();
        int expectedAge = currentDate.getYear() - dateOfBirth.getYear();
        if (currentDate.getDayOfYear() < dateOfBirth.getDayOfYear()) {
            expectedAge--;
        }
        assertEquals(expectedAge, age);
    }
}
