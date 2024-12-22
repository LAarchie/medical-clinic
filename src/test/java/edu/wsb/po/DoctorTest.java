package edu.wsb.po;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DoctorTest {
    Doctor doctor;

    @BeforeEach
    void setUp() {
        LocalDate dateOfBirth = LocalDate.of(1990, 5, 15);
        Set<String> specialties = new HashSet<>();
        doctor = new Doctor("John", "Doe", "12345678901",
                dateOfBirth, "111222333",
                "jd@doc.com", "jd001", specialties);
    }

    @Test
    void shouldAddOneSpecialty() {
        doctor.addSpecialty("UROLOGIST");

        assertFalse(doctor.getSpecialties().isEmpty());
        assertEquals(1, doctor.getSpecialties().size());
    }

    @Test
    void shouldNotDuplicate() {
        doctor.addSpecialty("UROLOGIST");
        doctor.addSpecialty("UROLOGIST");
        assertNotEquals(2, doctor.getSpecialties().size());
        assertFalse(doctor.getSpecialties().isEmpty());
        assertEquals(1, doctor.getSpecialties().size());
    }
}