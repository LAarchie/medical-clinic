package edu.wsb.po;

import java.time.LocalDate;
import java.util.*;


public class PatientManager {
    private final Map<String, Patient> patientsByPesel = new HashMap<>();
    private final Map<String, List<Patient>> patientsBySurname = new HashMap<>();
    private final Scanner sc = new Scanner(System.in);

    public void addPatient(Patient patient) {
        patientsByPesel.put(patient.getPesel(), patient); //Add to patientsByPesel
        //System.out.println("PatByPes: " + patientsByPesel);


        //Check if the surname already exists in patientsBySurname map
        if (!patientsBySurname.containsKey(patient.getSurname())) {

            //create a new list for this surname if it doesn't exist
            patientsBySurname.put(patient.getSurname(), new ArrayList<>());
        }

        List<Patient> patientsWithSameSurname = patientsBySurname.get(patient.getSurname());
        patientsWithSameSurname.add(patient);
        //.out.println("patBySurname: " + patientsBySurname);
    }

    public void findPatientByPesel() {
        System.out.println("Enter PESEL number: ");
        String pesel = sc.nextLine();
        Patient patient = patientsByPesel.get(pesel);
        System.out.println(patient.toString());

    }

    public void findPatientsBySurname() {
        System.out.println("Enter surname: ");
        String surname = sc.nextLine();

        //List of patients with the provided surname, if it doesn't match - gives an empty List to avoid an Error
        List<Patient> patientsWithSameSurname = patientsBySurname.getOrDefault(surname, new ArrayList<>());

        if (patientsWithSameSurname.isEmpty()){
            System.out.println("No patient found with surname: " + surname);
        }
        else {
            System.out.println("Patients with surname '" + surname + "':");

            //Iterate over the Patients list and print the info
            for (Patient patient : patientsWithSameSurname) {
                System.out.println(patient);
            }
        }
    }

    public void interactiveAddPatient() {
        System.out.print("Enter patient's name: ");
        String name = sc.nextLine();

        System.out.print("Enter patient's surname: ");
        String surname = sc.nextLine();

        System.out.print("Enter patient's PESEL: ");
        String pesel = sc.nextLine();

        System.out.print("Enter patient's date of birth (YYYY-MM-DD_: ");
        String birthDateStr = sc.nextLine();
        LocalDate dateOfBirth = LocalDate.parse(birthDateStr);

        System.out.print("Enter patient's phone number: ");
        String phoneNumber = sc.nextLine();

        System.out.print("Enter patient's email: ");
        String email = sc.nextLine();

        Patient patient = new Patient(name, surname, pesel, dateOfBirth, phoneNumber, email);
        addPatient(patient);
        System.out.println("Patient added successfully");
    }

}
