package edu.wsb.po;

import java.time.LocalDate;
import java.time.Period;

public class Person {
    private String name;
    private String surname;
    private String pesel;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String email;

    public Person(String name, String surname, String pesel,
                   LocalDate dateOfBirth, String phoneNumber, String email) {
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public String getPesel() {
        return pesel;
    }
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getEmail() {
        return email;
    }

    public static int getAge(LocalDate dateOfBirth){
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(dateOfBirth, currentDate);
        return period.getYears();
    }
}
