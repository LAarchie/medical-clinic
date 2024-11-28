package edu.wsb.po;

import java.time.LocalDate;
import java.time.Period;

public class Patient extends Person {
    public Patient(String name, String surname, String pesel,
                  LocalDate dateOfBirth, String phoneNumber, String email){
        super(name, surname, pesel, dateOfBirth, phoneNumber, email);
    }


    @Override
    public String toString() {
        return "--Patient-- \n" +
            "Name: " + getName() + "\n" +
            "Surname: " + getSurname() + "\n" +
            "Pesel: " + getPesel() + "\n" +
            "Date of Birth (YYYY-MM-DD): " + getDateOfBirth() + "\n" +
            "Age: " + getAge(getDateOfBirth()) + "\n" +
            "Phone: " + getPhoneNumber() + "\n" +
            "Email: " + getEmail() + "\n" +
            "###########";
    }
}
