package edu.wsb.po;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/**
 * Klasa reprezentująca system rejestracji wizyt, który pozwala na zarządzanie lekarzami,
 * pacjentami, wizytami i zapisami na wizyty.
 */
public class Reception {

    private final DoctorManager doctorManager;  // Obiekt do zarządzania lekarzami
    private final PatientManager patientManager;  // Obiekt do zarządzania pacjentami
    private final Map<String, List<Visit>> visitsByDoctor = new HashMap<>();  // Mapa przechowująca wizyty poszczególnych lekarzy

    private final Scanner scanner = new Scanner(System.in);  // Scanner do odczytu danych od użytkownika

    // Konstruktor inicjalizujący obiekty do zarządzania lekarzami i pacjentami
    public Reception(DoctorManager doctorManager, PatientManager patientManager) {
        this.doctorManager = doctorManager;
        this.patientManager = patientManager;
    }

    /**
     * Metoda uruchamiająca menu systemu rejestracji wizyt.
     */
    public void start() {
        while (true) {
            // Wyświetlenie menu dla użytkownika
            System.out.println("----- Reception Management Interface -----");
            System.out.println("1. Register a new visit");
            System.out.println("2. View doctor's schedule");
            System.out.println("3. View patient visits");
            System.out.println("4. Cancel a visit");
            System.out.println("5. Add a new doctor");
            System.out.println("6. Add a specialty to a doctor");
            System.out.println("7. Create a schedule for a doctor");
            System.out.println("8. View a doctor's weekly schedule");
            System.out.println("9. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();  // Pobranie wyboru użytkownika
            scanner.nextLine();  // Czyszczenie bufora

            switch (choice) {
                case 1:
                    registerVisit();  // Rejestracja wizyty
                    break;
                case 2:
                    System.out.print("Enter doctor's ID to view schedule: ");
                    String doctorId = scanner.nextLine();  // Pobranie ID lekarza
                    System.out.print("Enter date (YYYY-MM-DD): ");
                    LocalDate date = LocalDate.parse(scanner.nextLine());  // Pobranie daty wizyty
                    viewDoctorSchedule(doctorId, date);  // Wyświetlanie harmonogramu
                    break;
                case 3:
                    System.out.print("Enter patient PESEL to view visits: ");
                    String pesel = scanner.nextLine();  // Pobranie PESEL-u pacjenta
                    viewPatientVisits(pesel);  // Wyświetlanie wizyt pacjenta
                    break;
                case 4:
                    cancelVisit();  // Anulowanie wizyty
                    break;
                case 5:
                    doctorManager.interactiveAddDoctor();  // Dodawanie nowego lekarza
                    break;
                case 6:
                    doctorManager.interactiveAddSpecialty();  // Dodawanie specjalizacji do lekarza
                    break;
                case 7:
                    createScheduleForDoctor();  // Tworzenie grafiku dla lekarza
                    break;
                case 8:
                    viewWeeklyScheduleForDoctor();  // Wyświetlanie grafiku dla lekarza
                    break;
                case 9:
                    System.out.println("Exiting...");
                    return;  // Zakończenie programu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Metoda umożliwiająca rejestrację wizyty dla pacjenta.
     */
    public void registerVisit() {
        System.out.println("Choose a doctor to register visit:");
        List<Doctor> doctors = doctorManager.findDoctorsBySpecialty();  // Wyszukiwanie lekarzy wg specjalizacji
        if (doctors.isEmpty()) {
            System.out.println("No available doctors.");
            return;
        }

        // Wybór lekarza
        System.out.print("Enter doctor's ID: ");
        String doctorId = scanner.nextLine();

        Doctor doctor = doctorManager.findDoctorById(doctorId);  // Wyszukiwanie lekarza po ID
        if (doctor == null) {
            System.out.println("Doctor not found.");
            return;
        }

        // Wyszukiwanie pacjenta
        System.out.print("Enter patient's PESEL: ");
        String pesel = scanner.nextLine();
        Patient patient = patientManager.findPatientByPesel(pesel);  // Wyszukiwanie pacjenta po PESEL-u
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        // Wybór daty wizyty
        System.out.print("Enter visit date (YYYY-MM-DD): ");
        LocalDate visitDate = LocalDate.parse(scanner.nextLine());

        // Wybór godziny wizyty
        System.out.print("Enter visit time (HH:mm): ");
        String visitTimeStr = scanner.nextLine();
        LocalTime visitTime = LocalTime.parse(visitTimeStr);

        // Tworzenie wizyty
        Visit visit = new Visit(patient, doctor, visitDate, visitTime);
        addVisitToDoctor(doctorId, visit);  // Dodanie wizyty do lekarza

        System.out.println("Visit registered successfully.");
    }

    /**
     * Dodanie wizyty do harmonogramu lekarza.
     *
     * @param doctorId ID lekarza
     * @param visit    Obiekt wizyty
     */
    private void addVisitToDoctor(String doctorId, Visit visit) {
        visitsByDoctor.putIfAbsent(doctorId, new ArrayList<>());
        List<Visit> visits = visitsByDoctor.get(doctorId);
        visits.add(visit);  // Dodanie wizyty do listy
    }

    /**
     * Wyświetlenie harmonogramu wizyt lekarza w określonym dniu.
     *
     * @param doctorId ID lekarza
     * @param date     Data wizyty
     */
    public void viewDoctorSchedule(String doctorId, LocalDate date) {
        List<Visit> visits = visitsByDoctor.get(doctorId);
        if (visits == null || visits.isEmpty()) {
            System.out.println("No visits scheduled for this doctor.");
            return;
        }

        // Wyświetlenie wizyt dla lekarza w danym dniu
        System.out.println("Schedule for doctor " + doctorId + " on " + date + ":");
        for (Visit visit : visits) {
            if (visit.getVisitDate().equals(date)) {
                System.out.println(visit.toString());  // Wyświetlenie wizyty
            }
        }
    }

    /**
     * Wyświetlenie wizyt pacjenta na podstawie PESEL-u.
     *
     * @param pesel PESEL pacjenta
     */
    public void viewPatientVisits(String pesel) {
        Patient patient = patientManager.findPatientByPesel(pesel);  // Wyszukiwanie pacjenta po PESEL-u
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        // Wyświetlanie wizyt pacjenta
        System.out.println("All visits for patient with PESEL " + pesel + ":");
        for (Map.Entry<String, List<Visit>> entry : visitsByDoctor.entrySet()) {
            String doctorId = entry.getKey();
            List<Visit> visits = entry.getValue();
            for (Visit visit : visits) {
                if (visit.getPatient().getPesel().equals(pesel)) {
                    System.out.println("Doctor ID: " + doctorId + ", " + visit.toString());  // Wyświetlenie wizyt
                }
            }
        }
    }

    /**
     * Anulowanie wizyty pacjenta.
     */
    public void cancelVisit() {
        System.out.print("Enter patient's PESEL: ");
        String pesel = scanner.nextLine();
        Patient patient = patientManager.findPatientByPesel(pesel);  // Wyszukiwanie pacjenta
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        System.out.print("Enter doctor's ID: ");
        String doctorId = scanner.nextLine();
        List<Visit> visits = visitsByDoctor.get(doctorId);
        if (visits == null || visits.isEmpty()) {
            System.out.println("No visits scheduled with this doctor.");
            return;
        }

        // Szukanie wizyty do anulowania
        System.out.print("Enter visit date (YYYY-MM-DD): ");
        LocalDate visitDate = LocalDate.parse(scanner.nextLine());
        System.out.print("Enter visit time (HH:mm): ");
        LocalTime visitTime = LocalTime.parse(scanner.nextLine());

        Visit visitToCancel = null;
        for (Visit visit : visits) {
            if (visit.getPatient().getPesel().equals(pesel) &&
                visit.getVisitDate().equals(visitDate) &&
                visit.getVisitTime().equals(visitTime)) {
                visitToCancel = visit;
                break;
            }
        }

        if (visitToCancel != null) {
            visits.remove(visitToCancel);  // Usunięcie wizyty
            System.out.println("Visit canceled successfully.");
        } else {
            System.out.println("Visit not found.");
        }
    }

    /**
     * Tworzenie grafiku dla lekarza.
     */
    public void createScheduleForDoctor() {
        System.out.print("Enter doctor's ID to create schedule: ");
        String doctorId = scanner.nextLine();

        Doctor doctor = doctorManager.findDoctorById(doctorId);
        if (doctor == null) {
            System.out.println("Doctor not found.");
            return;
        }

        System.out.print("Enter start date of the schedule (YYYY-MM-DD): ");
        LocalDate startDate = LocalDate.parse(scanner.nextLine());

        System.out.print("Enter number of days for the schedule: ");
        int numberOfDays = scanner.nextInt();
        scanner.nextLine();  // Czyszczenie bufora

        System.out.println("Schedule created successfully.");
    }

    /**
     * Wyświetlanie cotygodniowego grafiku lekarza.
     */
    public void viewWeeklyScheduleForDoctor() {
        System.out.print("Enter doctor's ID to view weekly schedule: ");
        String doctorId = scanner.nextLine();

        Doctor doctor = doctorManager.findDoctorById(doctorId);
        if (doctor == null) {
            System.out.println("Doctor not found.");
            return;
        }

        System.out.println("Displaying weekly schedule for doctor " + doctorId);
        // Logika do wyświetlania harmonogramu dla danego lekarza
    }
}
