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
            System.out.println("Doctor with ID: "+ id +" not found");
            return null;
        }

    }

    public List<Doctor> findDoctorsBySpecialty() {

        for ( String spec : predefinedSpecialties) {
            System.out.println(predefinedSpecialties.indexOf(spec)+1 + ". " + spec);
        }
        int choice = scanner.nextInt();
        scanner.nextLine();
        String specialty = predefinedSpecialties.get(choice-1);

        if(!predefinedSpecialties.contains(specialty)) {
            System.out.println("Specialty " + specialty + " is not recognized");
            return Collections.emptyList();
        }
        List<Doctor> doctors = doctorsBySpecialty.get(specialty);
        if (doctors.isEmpty()) {
            System.out.println("No doctors found with specialty: " + specialty);
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

        System.out.print("Enter doctor's date of birth (YYYY-MM-DD: ");
        String birthDateStr = scanner.nextLine();
        LocalDate dateOfBirth = LocalDate.parse(birthDateStr);

        System.out.print("Enter doctor's phone number: ");
        String phoneNumber = scanner.nextLine();

        System.out.println("Enter doctor's email: ");
        String email = scanner.nextLine();

        Set<String> selectedSpecialties = new HashSet<>();

        System.out.println("Choose specialties to assign - separated by comma: ");
        for ( String spec : predefinedSpecialties) {
            System.out.println(predefinedSpecialties.indexOf(spec)+1 + ". " + spec);
        }
        String input = scanner.nextLine();


    }






    public void interactiveAddSpecialty() {
        DoctorManager doctorManager = new DoctorManager();

        Doctor doctor = doctorManager.findDoctorById();

        System.out.println("Choose a specialty: ");
        //System.out.println("1. Cardiologist");
        //System.out.println("2. Dermatologist");
        //System.out.println("3. Optometrist");
        //System.out.println("4. Urologist");
        //System.out.println("5. Oncologist");
        for ( String spec : predefinedSpecialties) {
            System.out.println(predefinedSpecialties.indexOf(spec)+1 + ". " + spec);
        }
        int choice = -1;

        while (true){
            System.out.println("Enter specialty number: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            if (choice >= 1 && choice <= doctor.getSpecialties().size()) {
                break;
            } else {
                System.out.println("Invalid choice, please try again");
            }
        }

        String selectedSpecialty = predefinedSpecialties.get(choice-1);

        if (doctor.getSpecialties().contains(selectedSpecialty)) {
            System.out.println("This doctor already has this specialty");
            return;
        }
        doctor.addSpecialty(selectedSpecialty);
        System.out.println("Specialty "+ selectedSpecialty +" added successfully " + "to doctor with ID: "+
                doctor.getId());

    }
}
