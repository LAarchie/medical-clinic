package edu.wsb.po;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class ScheduleManager {
    public final Map<String, Map<LocalDate, List<LocalTime>>> schedulesByDoctor = new HashMap<>();
    private final Path schedulesFilePath;

    public ScheduleManager(Path filePath) {
        this.schedulesFilePath = Objects.requireNonNull(filePath);

        // Initialize the file path using the resource loader
        loadSchedulesFromFile();
    }

    public void createScheduleForDoctor(String doctorId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        if (startTime.isBefore(LocalTime.of(8, 0)) || endTime.isAfter(LocalTime.of(20, 0))) {
            System.out.println("Working hours must be between 08:00 and 20:00.");
            return;
        }

        schedulesByDoctor.putIfAbsent(doctorId, new HashMap<>());
        Map<LocalDate, List<LocalTime>> doctorSchedule = schedulesByDoctor.get(doctorId);

        List<LocalTime> hours = Arrays.asList(startTime, endTime);
        doctorSchedule.put(date, hours);

        saveSchedulesToFile();
    }


    // Display a schedule for a doctor
    public void displayWeeklyScheduleForDoctor(String doctorId) {
        loadSchedulesFromFile();
        Map<LocalDate, List<LocalTime>> doctorSchedule = schedulesByDoctor.get(doctorId);
        if (doctorSchedule == null || doctorSchedule.isEmpty()) {
            System.out.println("No schedule found for doctor with ID: " + doctorId);
            return;
        }

        System.out.println("Schedule for Doctor " + doctorId + ":");
        List<LocalDate> sortedData = new ArrayList<>(doctorSchedule.keySet());
        Collections.sort(sortedData);

        for (LocalDate date : sortedData) {
            List<LocalTime> hours = doctorSchedule.get(date);
            System.out.println("Date: " + date + " Hours: " + hours.get(0) + "-" + hours.get(1));
        }
    }


    public void saveSchedulesToFile() {
        try (BufferedWriter writer = Files.newBufferedWriter(schedulesFilePath,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            for (Map.Entry<String, Map<LocalDate, List<LocalTime>>> doctorEntry : schedulesByDoctor.entrySet()) {
                String doctorId = doctorEntry.getKey();
                for (Map.Entry<LocalDate, List<LocalTime>> scheduleEntry : doctorEntry.getValue().entrySet()) {
                    LocalDate date = scheduleEntry.getKey();
                    List<LocalTime> hours = scheduleEntry.getValue();
                    writer.write(doctorId + "," + date + "," + hours.get(0) + "," + hours.get(1));
                    writer.newLine();
                }
            }
            //System.out.println("Schedules saved to file successfully.");
        } catch (IOException e) {
            System.err.println("Error saving schedules to file: " + e.getMessage());
        }
    }


    private void loadSchedulesFromFile() {
        try (BufferedReader reader = Files.newBufferedReader(schedulesFilePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 4) continue;

                String doctorId = parts[0];
                LocalDate date = LocalDate.parse(parts[1]);
                LocalTime startTime = LocalTime.parse(parts[2]);
                LocalTime endTime = LocalTime.parse(parts[3]);

                createScheduleForDoctor(doctorId, date, startTime, endTime);
            }
            //System.out.println("Schedules loaded from file.");
        } catch (FileNotFoundException e) {
            System.out.println("Schedule file not found. Starting fresh.");
        } catch (IOException e) {
            System.err.println("Error reading schedules from file: " + e.getMessage());
        }
    }
}
