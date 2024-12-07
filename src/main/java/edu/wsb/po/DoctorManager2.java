package edu.wsb.po;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class DoctorManager {
    // Mapa do przechowywania lekarzy po ID
    private final Map<String, Doctor> doctorsById = new HashMap<>();

    // Mapa do przechowywania lekarzy według specjalizacji
    private final Map<String, List<Doctor>> doctorsBySpecialty = new HashMap<>();

    // Lista predefiniowanych specjalizacji
    private final List<String> predefinedSpecialties = Arrays.asList("CARDIOLOGIST", "DERMATOLOGIST", "OPTOMETRIST",
            "UROLOGIST", "ONCOLOGIST");

    // Ścieżka do pliku z danymi lekarzy
    private final String doctorFilePath = getClass().getClassLoader().getResource("doctors.csv").getFile();

    // Scanner do odczytu danych wejściowych
    private final Scanner scanner = new Scanner(System.in);

    // Mapa do przechowywania harmonogramów lekarzy (przechowuje daty i godziny pracy)
    private final Map<String, Map<LocalDate, List<LocalTime>>> schedulesByDoctor = new HashMap<>();

    // Konstruktor inicjalizujący dane w mapach i wczytujący lekarzy z pliku
    public DoctorManager() {
        // Inicjalizowanie pustej listy lekarzy dla każdej predefiniowanej specjalizacji
        for (String specialty : predefinedSpecialties) {
            doctorsBySpecialty.put(specialty, new ArrayList<>());
        }
        loadDoctorFromFile(); // Wczytanie lekarzy z pliku
    }

    // Metoda wczytująca dane lekarzy z pliku CSV
    private void loadDoctorFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(doctorFilePath))) {
            String line;
            // Odczyt kolejnych wierszy z pliku
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length != 8) continue; // Sprawdzamy, czy wiersz zawiera wszystkie wymagane dane
                // Parsowanie danych lekarza
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
                addDoctorToMaps(doctor); // Dodajemy lekarza do odpowiednich map
            }
        } catch (IOException e) {
            System.out.println("Error loading doctor from file" + e.getMessage());
        }
    }

    // Metoda zapisująca jednego lekarza do pliku
    private void saveDoctorToFile(Doctor doctor) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(doctorFilePath, true))) {
            String specialties = String.join(";", doctor.getSpecialties()); // Łączenie specjalizacji lekarza w jeden ciąg
            // Tworzenie wiersza do zapisania w pliku
            String line = String.join(",",
                    doctor.getName(),
                    doctor.getSurname(),
                    doctor.getPesel(),
                    doctor.getDateOfBirth().toString(),
                    doctor.getPhoneNumber(),
                    doctor.getEmail(),
                    doctor.getId(),
                    specialties);
            bw.write(line); // Zapisujemy wiersz do pliku
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error saving doctor to file" + e.getMessage());
        }
    }

    // Metoda zapisująca wszystkich lekarzy do pliku
    private void saveAllDoctorsToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(doctorFilePath))) {
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
                bw.write(line); // Zapisujemy wiersz do pliku
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving doctors to file" + e.getMessage());
        }
    }

    // Metoda dodająca lekarza do odpowiednich map
    public void addDoctorToMaps(Doctor doctor) {
        doctorsById.put(doctor.getId(), doctor); // Dodajemy lekarza do mapy po ID

        Set<String> specialties = doctor.getSpecialties();
        for (String specialty : specialties) {
            specialty = specialty.toUpperCase(); // Specjalizacje zapisujemy w dużych literach
            if (!doctorsBySpecialty.containsKey(specialty)) {
                doctorsBySpecialty.put(specialty, new ArrayList<>());
            }
            doctorsBySpecialty.get(specialty).add(doctor); // Dodajemy lekarza do mapy po specjalizacji
        }
    }

    // Metoda dodająca nowego lekarza
    public void addDoctor(Doctor doctor) {
        // Sprawdzamy, czy specjalizacje są poprawne
        for (String specialty : doctor.getSpecialties()) {
            if (!predefinedSpecialties.contains(specialty)) {
                System.out.println("Warning! The specialty: " + specialty + " is not recognized");
            }
        }
        addDoctorToMaps(doctor); // Dodajemy lekarza do map
        saveDoctorToFile(doctor); // Zapisujemy lekarza do pliku
        System.out.println("Doctor added successfully");
    }

    // Metoda znajdująca lekarza po ID
    public Doctor findDoctorById() {
        System.out.println("Enter Doctor's ID: ");
        String id = scanner.nextLine();
        if (doctorsById.containsKey(id)) {
            return doctorsById.get(id); // Zwracamy lekarza, jeśli ID jest w mapie
        } else {
            System.out.println("Doctor with ID: " + id + " not found");
            return null;
        }
    }

    // Metoda znajdująca lekarzy po specjalizacji
    public List<Doctor> findDoctorsBySpecialty() {
        // Wyświetlamy dostępne specjalizacje
        for (int i = 0; i < predefinedSpecialties.size(); i++) {
            System.out.println((i + 1) + ". " + predefinedSpecialties.get(i));
        }
        int choice = -1;
        while (choice < 1 || choice > predefinedSpecialties.size()) {
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
        String specialty = predefinedSpecialties.get(choice - 1); // Wybieramy specjalizację

        List<Doctor> doctors = doctorsBySpecialty.get(specialty); // Pobieramy lekarzy danej specjalizacji
        if (doctors == null || doctors.isEmpty()) {
            System.out.println("No doctors found with specialty: " + specialty);
            return Collections.emptyList(); // Zwracamy pustą listę, jeśli nie znaleziono lekarzy
        }
        for (Doctor doctor : doctors) {
            System.out.println(doctor.toString()); // Wyświetlamy szczegóły każdego lekarza
        }
        return doctors;
    }

    // Interaktywna metoda dodająca nowego lekarza
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
        for (String spec : predefinedSpecialties) {
            System.out.println(predefinedSpecialties.indexOf(spec) + 1 + ". " + spec);
        }
        String input = scanner.nextLine();
        String[] inputs = input.split(",");
        for (String choice : inputs) {
            try {
                int specialtyIndex = Integer.parseInt(choice.trim()) - 1;

                if (specialtyIndex >= 0 && specialtyIndex < predefinedSpecialties.size()) {
                    selectedSpecialties.add(predefinedSpecialties.get(specialtyIndex).trim().toUpperCase());
                } else {
                    System.out.println("Invalid choice: " + choice.trim());
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input: " + choice.trim());
            }
        }

        // Tworzymy nowego lekarza
        Doctor doctor = new Doctor(name, surname, pesel, dateOfBirth, phoneNumber,
                email, id, selectedSpecialties);
        addDoctor(doctor); // Dodajemy go do systemu
    }

    // Metoda dodająca specjalizację do istniejącego lekarza
    public void interactiveAddSpecialty() {
        Doctor doctor = findDoctorById(); // Szukamy lekarza po ID
        if (doctor == null) {
            System.out.println("Doctor not found. Cannot add specialty.");
            return;
        }
        System.out.println("Choose a specialty: ");
        for (int i = 0; i < predefinedSpecialties.size(); i++) {
            System.out.println((i + 1) + ". " + predefinedSpecialties.get(i));
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
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please enter a number: ");
                scanner.nextLine();
            }
        }

        String selectedSpecialty = predefinedSpecialties.get(choice - 1).toUpperCase();

        if (doctor.getSpecialties().contains(selectedSpecialty)) {
            System.out.println("This doctor already has this specialty");
        } else {
            doctor.addSpecialty(selectedSpecialty);
            System.out.println("Specialty " + selectedSpecialty + " added successfully to doctor with ID: " + doctor.getId());
        }
        saveAllDoctorsToFile(); // Zapisujemy wszystkich lekarzy do pliku po zmianie
    }

    // Metoda tworząca harmonogram dla lekarza
    public void createScheduleForDoctor(String doctorId, LocalDate date, List<LocalTime> workingHours) {
        // Inicjalizowanie mapy harmonogramów, jeśli jeszcze nie istnieje
        schedulesByDoctor.putIfAbsent(doctorId, new HashMap<>());
        Map<LocalDate, List<LocalTime>> doctorSchedule = schedulesByDoctor.get(doctorId);

        // Dodawanie godzin pracy do harmonogramu dla danego lekarza i daty
        doctorSchedule.put(date, workingHours);
    }

    // Metoda wyświetlająca harmonogram dla lekarza
    public void displayWeeklyScheduleForDoctor(String doctorId) {
        Map<LocalDate, List<LocalTime>> doctorSchedule = schedulesByDoctor.get(doctorId);
        if (doctorSchedule == null || doctorSchedule.isEmpty()) {
            System.out.println("No schedule found for doctor with ID: " + doctorId);
            return;
        }

        System.out.println("Schedule for Doctor " + doctorId + ":");
        // Wyświetlamy harmonogram na każdą datę
        for (Map.Entry<LocalDate, List<LocalTime>> entry : doctorSchedule.entrySet()) {
            LocalDate date = entry.getKey();
            List<LocalTime> hours = entry.getValue();
            System.out.println("Date: " + date + ", Hours: " + hours);
        }
    }
}

