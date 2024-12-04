package edu.wsb.po;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.*;

public class DoctorManager {
    private final Map<String, Doctor> doctorsById = new HashMap<>();
    private final Map<String, List<Doctor>> doctorsBySpecialty = new HashMap<>();
    private final List<String> predefinedSpecialties = Arrays.asList("Cardiologist", "Dermatologist", "Optometrist",
            "Urologist", "Oncologist");
    private final Scanner scanner = new Scanner(System.in);

    public DoctorManager() {
        //Initialize an empty list (for doctors) for each predefined specialty
        for (String specialty : predefinedSpecialties) {
            doctorsBySpecialty.put(specialty, new ArrayList<>());
        }
    }


    public void addDoctor(Doctor doctor) {
        doctorsById.put(doctor.getId(), doctor);

        for (String specialty : doctor.getSpecialties()) {
            if(!predefinedSpecialties.contains(specialty)) {
                System.out.println("Warning! The specialty: " + specialty + " is not recognized");
                continue;
            }
            doctorsBySpecialty.get(specialty).add(doctor);
        }
        System.out.println("Doctor added successfully");
    }

    //Find a doctor by ID
    public Doctor findDoctorById() {
        System.out.println("Enter Doctor's ID: ");
        String id = scanner.nextLine();
        if (doctorsById.containsKey(id)) {
            return doctorsById.get(id);
        } else{
            System.out.println("Doctor not found");
            return null;
        }

    }

    public List<Doctor> findDoctorsBySpecialty() {
        String specialty = "";
        System.out.println("Choose a specialty: ");
        System.out.println("1. Cardiologist");
        System.out.println("2. Dermatologist");
        System.out.println("3. Optometrist");
        System.out.println("4. Urologist");
        System.out.println("5. Oncologist");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                specialty = "Cardiologist";
                break;
                case 2:
                    specialty = "Dermatologist";
                    break;
                    case 3:
                        specialty = "Optometrist";
                        break;
                        case 4:
                            specialty = "Urologist";
                            break;
                            case 5:
                                specialty = "Oncologist";
                                break;
                                default:
                                    System.out.println("Invalid choice, try again");
        }

        if(!predefinedSpecialties.contains(specialty)) {
            System.out.println("Specialty " + specialty + " is not recognized");
            return Collections.emptyList();
        }
        List<Doctor> doctors = doctorsBySpecialty.get(specialty);
        if (doctors.isEmpty()) {
            System.out.println("No doctors found for specialty " + specialty);
        }
        return doctors;
    }
    public void addSpecialty(String specialty) {

    }

    public void interactiveAddDoctor() {
        System.out.print("Enter doctor's name: ");
        String name = scanner.nextLine();

        System.out.print("Enter doctor's surname: ");
        String surname = scanner.nextLine();

        System.out.print("Enter doctor's PESEL: ");
        String pesel = scanner.nextLine();

        System.out.print("Enter doctor's date of birth (YYYY-MM-DD: ");
        String birthDateStr = scanner.nextLine();
        LocalDate dateOfBirth = LocalDate.parse(birthDateStr);

        System.out.print("Enter doctor's phone number: ");
        String phoneNumber = scanner.nextLine();

        System.out.println("Enter doctor's email: ");
        String email = scanner.nextLine();


    }



}
