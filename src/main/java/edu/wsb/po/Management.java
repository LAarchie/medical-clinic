package edu.wsb.po;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class Management {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ScheduleManager scheduleManager = new ScheduleManager();
    private static final DoctorManager doctorManager = new DoctorManager();
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
                    Doctor doctor = doctorManager.findDoctorById(); //Doctor object
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
