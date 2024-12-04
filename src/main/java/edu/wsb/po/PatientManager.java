package edu.wsb.po;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class PatientManager {
    private final Map<String, Patient> patientsByPesel = new HashMap<>();
    private final Map<String, List<Patient>> patientsBySurname = new HashMap<>();
    private final Scanner scanner = new Scanner(System.in);
    private final String patientFilePath = getClass().getClassLoader().getResource("patients.csv").getFile();

    public PatientManager() {
        loadPatientFromFile();
    }
    private void loadPatientFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(patientFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length != 6) continue;
                String name = tokens[0];
                String surname = tokens[1];
                String pesel = tokens[2];
                LocalDate dateOfBirth = LocalDate.parse(tokens[3]);
                String phoneNumber = tokens[4];
                String email = tokens[5];

                Patient patient = new Patient(name, surname, pesel, dateOfBirth, phoneNumber, email);
                addPatientToMaps(patient);
            }
        } catch (IOException e){
            System.out.println("Error loading patient from file" + e.getMessage());
        }
    }
    public void savePatientToFile(Patient patient) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(patientFilePath, true))){
            String line = String.join(",",
                    patient.getName(),
                    patient.getSurname(),
                    patient.getPesel(),
                    patient.getDateOfBirth().toString(),
                    patient.getPhoneNumber(),
                    patient.getEmail());
            bw.write(line);
            bw.newLine();
        } catch (IOException e){
            System.out.println("Error saving patient to file" + e.getMessage());
        }
    }

    private void addPatientToMaps(Patient patient){
        patientsByPesel.put(patient.getPesel(), patient);
        String surname = patient.getSurname();
        List<Patient> patientsWithSameSurname = patientsBySurname.get(surname);

        if (patientsWithSameSurname == null) {
            patientsWithSameSurname = new ArrayList<>();
            patientsBySurname.put(surname, patientsWithSameSurname);
        }
        patientsWithSameSurname.add(patient);
    }
    public void addPatient(Patient patient) {
        // Checks if patient with this pesel already exists
        if (patientsByPesel.containsKey(patient.getPesel())) {
            System.out.println("Patient with Pesel: " + patient.getPesel() + " already exists");
            return;
        }

        // If it's a new patient - adds it to the pesel and sameSurname maps and saves the data to the csv file
        addPatientToMaps(patient);
        savePatientToFile(patient);
        System.out.println("Patient added successfully");
    }

    public void findPatientByPesel() {
        System.out.println("Enter PESEL number: ");
        String pesel = scanner.nextLine();

        //Check if pesel exists in the map
        if (patientsByPesel.containsKey(pesel)) {
            Patient patient = patientsByPesel.get(pesel);
            System.out.println(patient.toString());
        } else {
            System.out.println("Invalid PESEL number");
        }

    }

    public void findPatientsBySurname() {
        System.out.println("Enter surname: ");
        String surname = scanner.nextLine();

        //List of patients with the provided surname, if it doesn't match - gives an empty List to avoid an Error
        List<Patient> patientsWithSameSurname = patientsBySurname.getOrDefault(surname, new ArrayList<>());

        if (patientsWithSameSurname.isEmpty()){
            System.out.println("No patient found with surname: " + surname);
        }
        else {
            System.out.println("Patients with surname '" + surname + "':");

            //Iterate over the Patients list and print the info of each patient
            for (Patient patient : patientsWithSameSurname) {
                System.out.println(patient);
            }
        }
    }

    public void interactiveAddPatient() {
        System.out.print("Enter patient's name: ");
        String name = scanner.nextLine();

        System.out.print("Enter patient's surname: ");
        String surname = scanner.nextLine();

        System.out.print("Enter patient's PESEL: ");
        String pesel = scanner.nextLine();

        System.out.print("Enter patient's date of birth (YYYY-MM-DD): ");
        String birthDateStr = scanner.nextLine();
        LocalDate dateOfBirth = LocalDate.parse(birthDateStr);

        System.out.print("Enter patient's phone number: ");
        String phoneNumber = scanner.nextLine();

        System.out.print("Enter patient's email: ");
        String email = scanner.nextLine();

        Patient patient = new Patient(name, surname, pesel, dateOfBirth, phoneNumber, email);
        addPatient(patient);
    }

}
