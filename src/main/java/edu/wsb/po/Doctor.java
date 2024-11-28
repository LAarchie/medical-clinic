package edu.wsb.po;

import java.time.LocalDate;
import java.util.Set;
import java.util.HashSet;

public class Doctor extends Person {

    private String id;
    private Set<String> specialties;

    public Doctor(String name, String surname, String pesel,
                  LocalDate dateOfBirth, String phoneNumber, String email, String id) {
        super(name, surname, pesel, dateOfBirth, phoneNumber, email);
        this.id = id;
        this.specialties = new HashSet<>();

    }

    public String getId(){
        return id;
    }

    public Set<String> getSpecialties(){
        return specialties;
    }

    public void addSpecialties(String specialty){
        specialties.add(specialty);
    }

    @Override
    public String toString() {
        return "--Doctor-- \n" +
                "Name: " + getName() + "\n" +
                "Surname: " + getSurname() + "\n" +
                "Pesel: " + getPesel() + "\n" +
                "Date of Birth (YYYY-MM-DD): " + getDateOfBirth() + "\n" +
                "Age: " + getAge(getDateOfBirth()) + "\n" +
                "Phone: " + getPhoneNumber() + "\n" +
                "Email: " + getEmail() + "\n" +
                "ID: " + id + "\n" +
                "Specialties: " + specialties +
                "###########";
    }


}

