package edu.wsb.po;

import java.util.*;

public class DoctorManager {
    private final Map<String, Doctor> doctorsById = new HashMap<>();
    private final Map<String, Set<Doctor>> doctorsBySpecialty = new HashMap<>();

    public void addDoctor(Doctor doctor) {
        doctorsById.put(doctor.getId(), doctor);



    }



}
