package edu.wsb.po;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;



class PatientManagerTest {
    PatientManager patientManager;
    Patient patient;
    Patient patient2;

    @BeforeEach
    void setUp() {
        patientManager = new PatientManager();
        LocalDate dateOfBirth = LocalDate.of(1990, 1, 1);
        patient = new Patient("John", "Doe", "123", dateOfBirth,
                "222333444", "jd@em.com");
        patient2 = new Patient("Tim", "Doe", "234", dateOfBirth,
                "333444555", "td@em.com");

    }


    @Test
    void shouldAddPatient(){
        List<Patient> patients = Arrays.asList(patient, patient2);
        patientManager.addPatientToMaps(patient);
        patientManager.addPatientToMaps(patient2);

        assertEquals(patient, patientManager.findPatientByPesel(patient.getPesel()), "Patient should be added to Pesel map");
        assertEquals(patients, patientManager.findPatientsBySurname(patient.getSurname()), "Patient should be added to Surname map"); //added to Surname map
    }
    @Test
    void shouldFindPatientByPesel(){
        patientManager.patientsByPesel.put("123", patient);
        Patient result = patientManager.findPatientByPesel("123");
        assertNotNull(result, "Patient should be found");
        assertEquals("123", result.getPesel(), "Pesel should match");
        assertEquals("John", result.getName(), "Name should match");
    }

    @Test
    void shouldNotFindPatientByPesel(){
        patientManager.patientsByPesel.clear();
        Patient result = patientManager.findPatientByPesel("555");
        assertNull(result, "Patient should be Null if Pesel doesn't exist");
    }

    @Test
    void shouldFindPatientsBySurname(){
        patientManager.patientsBySurname.put("Doe", Arrays.asList(patient, patient2));
        patientManager.patientsBySurname.put("Williams", new ArrayList<>()); //Empty

        List<Patient> patients = patientManager.findPatientsBySurname("Doe");
        assertNotNull(patients, "Patient list should not be null");
        assertEquals(2, patients.size(), "Should be 2 patients with surname Doe");
    }
    @Test
    void shouldNotFindPatientsBySurname(){
        List<Patient> patients = patientManager.findPatientsBySurname("Williams");
        assertTrue(patients.isEmpty(), "No patient with surname Williams");
    }
}


