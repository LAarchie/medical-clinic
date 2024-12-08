package edu.wsb.po;

import java.util.Scanner;

public class MainInterface {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        //Menu options
        while(true){
            System.out.println("Choose the interface to open: ");
            System.out.println("1. Reception");
            System.out.println("2. Human Resources");
            System.out.println("3. Management");
            System.out.println("4. Exit");


            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    Reception receptionInterface = new Reception();
                    receptionInterface.start();
                    break;
                    case 2:
                        HumanResources humanResourcesInterface = new HumanResources();
                        humanResourcesInterface.start();
                        break;
                        case 3:
                            Management managementInterface = new Management();
                            managementInterface.start();
                            break;
                            case 4:
                                System.out.println("Exiting...");
                                return;
                                default:
                                    System.out.println("Invalid choice, try again");
            }
    }
}}
