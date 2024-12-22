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

        assertEquals(patient, patientManager.findPatientByPesel(patient.getPesel()));
        assertEquals(patients, patientManager.findPatientsBySurname(patient.getSurname()));

    }
}


