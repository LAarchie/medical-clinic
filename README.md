# Simple Medical Clinic Management System

## System consists of various functionalities for different departments. Written in Java.

The system is built for three separate departments: Reception, Management and Human Resources. Each of those departments
requires a different set of functionalities, which will be described below.

## Reception
Reception should be able to:
* Create a new Patient profile (Patient class)
* Search for a patient by PESEL
* Search for patients by surname (multiple patients possible)
* Search for a doctor by ID
* Search for doctor of a certain specialty (there is a predefined list of specialties available for doctors)
* Display doctor's schedule

## Human Resources
Human Resources should be able to:
* Create a new Doctor profile (Doctor class)
* Add a new specialty to an existing doctor

## Management 
Management should be able to:
* Create a schedule for a doctor for a specified day, with a time frame.

## 
All the information (patients and doctors profiles and schedules) should be written into corresponding csv files,
and read from them. 

Special interactive methods for each department were created for a nicer visual experience.


