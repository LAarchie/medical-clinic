package edu.wsb.po;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class DoctorManager {
    private final Map<String, Doctor> doctorsById = new HashMap<>();
    private final Map<String, List<Doctor>> doctorsBySpecialty = new HashMap<>();
    private final List<String> predefinedSpecialties = Arrays.asList("CARDIOLOGIST", "DERMATOLOGIST", "OPTOMETRIST",
            "UROLOGIST", "ONCOLOGIST");
    private final String doctorFilePath = getClass().getClassLoader().getResource("doctors.csv").getFile();
    private final Scanner scanner = new Scanner(System.in);
    private final Map<String, Map<LocalDate, List<LocalTime>>> schedules = new HashMap<>();

    public DoctorManager() {
        //Initialize an empty list (for doctors) for each predefined specialty
        for (String specialty : predefinedSpecialties) {
            doctorsBySpecialty.put(specialty, new ArrayList<>());
        }
        loadDoctorFromFile();
    }

    private void loadDoctorFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(doctorFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length != 8) continue;
                String name = tokens[0];
                String surname = tokens[1];
                String pesel = tokens[2];
                LocalDate dateOfBirth = LocalDate.parse(tokens[3]);
                String phoneNumber = tokens[4];
                String email = tokens[5];
                String id = tokens[6];
                List<String> specialtiesArr = Arrays.asList(tokens[7].split(";"));
                Set<String> specialties = new HashSet<>(specialtiesArr);
                Doctor doctor = new Doctor(name, surname, pesel, dateOfBirth,
                        phoneNumber, email, id, specialties);
                addDoctorToMaps(doctor);
            }
        }
        catch (IOException e) {
            System.out.println("Error loading doctor from file" + e.getMessage());
        }
    }

    private void saveDoctorToFile(Doctor doctor) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(doctorFilePath, true))){
            String specialties = String.join(";", doctor.getSpecialties());
            String line = String.join(",",
                    doctor.getName(),
                    doctor.getSurname(),
                    doctor.getPesel(),
                    doctor.getDateOfBirth().toString(),
                    doctor.getPhoneNumber(),
                    doctor.getEmail(),
                    doctor.getId(),
                    specialties);
            bw.write(line);
            bw.newLine();
        } catch (IOException e){
            System.out.println("Error saving doctor to file" + e.getMessage());
        }
    }

    private void saveAllDoctorsToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(doctorFilePath))){
            for (Doctor doctor : doctorsById.values()) {
                String specialties = String.join(";", doctor.getSpecialties());
                String line = String.join(",",
                        doctor.getName(),
                        doctor.getSurname(),
                        doctor.getPesel(),
                        doctor.getDateOfBirth().toString(),
                        doctor.getPhoneNumber(),
                        doctor.getEmail(),
                        doctor.getId(),
                        specialties);
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e){
            System.out.println("Error saving doctors to file" + e.getMessage());
        }
    }

    public void addDoctorToMaps(Doctor doctor) {
        doctorsById.put(doctor.getId(), doctor);
        Set<String> specialties = doctor.getSpecialties();
        for (String specialty : specialties) {
            specialty = specialty.toUpperCase();
            if(!doctorsBySpecialty.containsKey(specialty)){
                doctorsBySpecialty.put(specialty, new ArrayList<>());
            }
            doctorsBySpecialty.get(specialty).add(doctor);


        }

    }

    public void addDoctor(Doctor doctor) {
        for (String specialty : doctor.getSpecialties()) {
            if(!predefinedSpecialties.contains(specialty)) {
                System.out.println("Warning! The specialty: " + specialty + " is not recognized");
            }
        }
        addDoctorToMaps(doctor);
        saveDoctorToFile(doctor);

        System.out.println("Doctor added successfully");
    }

    //Find a doctor by ID
    public Doctor findDoctorById() {
        loadDoctorFromFile();
        System.out.println("Enter Doctor's ID: ");
        String id = scanner.nextLine();
        if (doctorsById.containsKey(id)) {
            return doctorsById.get(id);
        } else{
            System.out.println("Doctor with ID: "+ id +" not found");
            return null;
        }
    }
    //Find a list of doctors with the same specialty
    public List<Doctor> findDoctorsBySpecialty() {
        //loadDoctorFromFile();
        //Display the specialties to the interface
        for (int i=0; i < predefinedSpecialties.size(); i++) {
            System.out.println((i+1) + ". " + predefinedSpecialties.get(i));
        }
        int choice = -1;
        while(choice < 1 || choice > predefinedSpecialties.size()) {
            System.out.println("Enter the number that corresponds to the specialty: ");
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice < 1 || choice > predefinedSpecialties.size()) {
                    System.out.println("Invalid choice, try again");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please enter a number: ");
                scanner.nextLine();
            }
        }
        String specialty = predefinedSpecialties.get(choice - 1);

        List<Doctor> doctors = doctorsBySpecialty.get(specialty);
        if(doctors == null || doctors.isEmpty()) {
            System.out.println("No doctors found with specialty: " + specialty);
            return Collections.emptyList();
        }
        for (Doctor doctor : doctors) {
            System.out.println(doctor.toString());
        }
        return doctors;
    }


    public void interactiveAddDoctor() {
        System.out.print("Enter doctor's name: ");
        String name = scanner.nextLine();

        System.out.print("Enter doctor's surname: ");
        String surname = scanner.nextLine();

        System.out.print("Enter doctor's PESEL: ");
        String pesel = scanner.nextLine();

        System.out.print("Enter doctor's date of birth (YYYY-MM-DD): ");
        String birthDateStr = scanner.nextLine();
        LocalDate dateOfBirth = LocalDate.parse(birthDateStr);

        System.out.print("Enter doctor's phone number: ");
        String phoneNumber = scanner.nextLine();

        System.out.println("Enter doctor's email: ");
        String email = scanner.nextLine();

        System.out.println("Enter doctor's ID: ");
        String id = scanner.nextLine();

        Set<String> selectedSpecialties = new HashSet<>();

        System.out.println("Choose specialties to assign - separated by comma: ");
        for ( String spec : predefinedSpecialties) {
            System.out.println(predefinedSpecialties.indexOf(spec)+1 + ". " + spec);
        }
        String input = scanner.nextLine();
        String[] inputs = input.split(",");
        for (String choice : inputs) {
            try{
                int specialtyIndex = Integer.parseInt(choice.trim()) - 1;

                if (specialtyIndex >= 0 && specialtyIndex < predefinedSpecialties.size()) {
                    selectedSpecialties.add(predefinedSpecialties.get(specialtyIndex).trim().toUpperCase());
                } else {
                    System.out.println("Invalid choice: " + choice.trim());
                }}
            catch (NumberFormatException e) {
                System.out.println("Invalid input: " + choice.trim());
                }

        }
        Doctor doctor = new Doctor (name, surname, pesel, dateOfBirth, phoneNumber,
                email, id, selectedSpecialties);
        //System.out.println("doctor: " + doctor.toString());
        addDoctor(doctor);
    }


    public void interactiveAddSpecialty() {
        Doctor doctor = findDoctorById();
        if (doctor == null) {
            System.out.println("Doctor not found. Cannot add specialty.");
            return;
        }
        System.out.println("Choose a specialty: ");
        for (int i=0; i<predefinedSpecialties.size(); i++){
            System.out.println((i+1) + ". " + predefinedSpecialties.get(i));
        }

        int choice = -1;
        while (choice < 1 || choice > predefinedSpecialties.size()) {
            System.out.println("Enter specialty number: ");
            try {
                choice = scanner.nextInt();
                scanner.nextLine();

                if (choice < 1 || choice > predefinedSpecialties.size()) {
                    System.out.println("Invalid choice, pick a number between 1 and " + predefinedSpecialties.size());
                }
            } catch(InputMismatchException e) {
                System.out.println("Invalid input, please enter a number: ");
                scanner.nextLine();
            }
        }

        String selectedSpecialty = predefinedSpecialties.get(choice-1).toUpperCase();

        if (doctor.getSpecialties().contains(selectedSpecialty)) {
            System.out.println("This doctor already has this specialty");
        } else {
            doctor.addSpecialty(selectedSpecialty);
            System.out.println("Specialty "+ selectedSpecialty +" added successfully " + "to doctor with ID: "+
                    doctor.getId());}
            saveAllDoctorsToFile(); //re-write the whole file of Doctors after adding a specialty
    }
}

