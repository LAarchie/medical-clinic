package edu.wsb.po;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;
import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleManagerTest {
    ScheduleManager scheduleManager;

    @BeforeEach
    void setUp() {
        scheduleManager = new ScheduleManager();
    }

    @Test
    void createSchedule() {
        String doctorId = "ew001";
        LocalDate date = LocalDate.of(2024, 12, 20);
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(12, 0);


        scheduleManager.createScheduleForDoctor(doctorId, date, startTime, endTime);


        Map<LocalDate, List<LocalTime>> doctorSchedule = scheduleManager.schedulesByDoctor.get(doctorId);
        assertNotNull(doctorSchedule, "Doctor's schedule should not be null");
        assertTrue(doctorSchedule.containsKey(date), "Schedule should be added for the given date");
        assertEquals(Arrays.asList(startTime, endTime), doctorSchedule.get(date), "The schedule times should match");
    }

    @Test
    void testCreateScheduleForDoctorInvalidTimeAfter8pm() {

        String doctorId = "ew001";
        LocalDate date = LocalDate.of(2024, 12, 22);
        LocalTime startTime = LocalTime.of(19, 0);  // Valid start time
        LocalTime endTime = LocalTime.of(21, 0);    // Invalid end time


        scheduleManager.createScheduleForDoctor(doctorId, date, startTime, endTime);

        Map<LocalDate, List<LocalTime>> doctorSchedule = scheduleManager.schedulesByDoctor.get(doctorId);
        assertNull(doctorSchedule, "No schedule should be added for an invalid time");
    }

    @Test
    public void testCreateScheduleForDoctorInvalidTimeBefore8am() {

        String doctorId = "ew001";
        LocalDate date = LocalDate.of(2024, 12, 22);
        LocalTime startTime = LocalTime.of(7, 30);  // Invalid start time
        LocalTime endTime = LocalTime.of(9, 0);    // Valid end time


        scheduleManager.createScheduleForDoctor(doctorId, date, startTime, endTime);


        Map<LocalDate, List<LocalTime>> doctorSchedule = scheduleManager.schedulesByDoctor.get(doctorId);
        assertNull(doctorSchedule, "No schedule should be added for an invalid time");
    }

}