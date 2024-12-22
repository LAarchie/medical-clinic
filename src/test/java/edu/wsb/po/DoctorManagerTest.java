package edu.wsb.po;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DoctorManagerTest {
    DoctorManager doctorManager;
    Doctor doctor;

    @BeforeEach
    void setUp() {
        doctorManager = new DoctorManager();
        LocalDate dateOfBirth = LocalDate.of(1970, 10, 10);

        doctor = new Doctor("John", "Doe", "12345678901",
                dateOfBirth, "111222333",
                "jd@doc.com", "jd001", Set.of("UROLOGIST"));


    }
    @Test
    void shouldAddDoctor(){
        doctorManager.addDoctorToMaps(doctor);
        assertEquals(doctor, doctorManager.findDoctorById(doctor.getId()));
    }

    @Test
    void shouldFindDoctorById(){
        doctorManager.doctorsById.put("jd001", doctor);
        Doctor result = doctorManager.findDoctorById("jd001");
        assertNotNull(result, "Doctor should be found");
        assertEquals("jd001", result.getId(), "The ID should match");
        assertEquals("John", result.getName(), "The Name should match");
    }

    @Test
    void shouldNotFindDoctorById(){
        doctorManager.doctorsById.clear();

        Doctor result = doctorManager.findDoctorById("jd0");
        assertNull(result, "Doctor should be Null if the ID doesn't exist");
    }
}
