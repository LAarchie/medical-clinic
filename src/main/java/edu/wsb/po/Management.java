package edu.wsb.po;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class Management {
    private final Scanner scanner;
    private final ScheduleManager scheduleManager;
    private final DoctorManager doctorManager;

    public Management(DoctorManager doctorManager, ScheduleManager scheduleManager) {
        this.scanner = new Scanner(System.in);
        this.doctorManager = doctorManager;
        this.scheduleManager = scheduleManager;
    }
    public void start(){
        while(true){
            //Menu options
            System.out.println("-----Manager Interface-----");
            System.out.println("1. Create a Schedule for a doctor");
            System.out.println("2. Exit");
            System.out.println("Choose and option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch(choice){
                case 1:
                    System.out.println("Enter Doctor's ID: ");
                    String id = scanner.nextLine();
                    Doctor doctor = doctorManager.findDoctorById(id); //Doctor object
                    if(doctor == null){
                        System.out.println("Doctor not found. Returning to the previous menu...");
                        return;
                    }
                    String doctorId = doctor.getId();
                    System.out.println();
                    System.out.println("Enter the date (YYYY-MM-DD): "); //provide the date
                    String dateString = scanner.nextLine();
                    LocalDate date = LocalDate.parse(dateString); //date

                    System.out.println("Enter the starting time (HH:mm:ss): "); //provide the starting time
                    String startTimeString = scanner.nextLine();
                    LocalTime startTime = LocalTime.parse(startTimeString); //starting time

                    System.out.println("Enter the ending time (HH:mm:ss): "); //provide the ending time
                    String endTimeString = scanner.nextLine();
                    LocalTime endTime = LocalTime.parse(endTimeString); //ending time


                    scheduleManager.createScheduleForDoctor(doctorId, date, startTime, endTime);
                    System.out.println("Schedule added for doctor " + doctorId + " on " + date);
                    break;
                    case 2:
                        System.out.println("Exiting...");
                        return;
                        default:
                            System.out.println("Invalid choice, try again");
            }
        }
    }
}
