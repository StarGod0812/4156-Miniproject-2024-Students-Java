package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit tests for the Department class.
 */
@ContextConfiguration
public class DepartmentUnitTests {

    /** The test department instance used for testing. */
    private static Department testDepartment;

    /**
     * Prepares the test environment by creating a new department instance before each test.
     */
    @BeforeEach
    public void setupDepartmentForTesting() {
        Map<String, Course> courses = new HashMap<>();
        courses.put("101", new Course("Dr. John", "Room 101", "10:00-11:00", 50));
        testDepartment = new Department("CS", courses, "Dr. Smith", 200);
    }

    /**
     * Verifies the string representation of the department.
     */
    @Test
    public void verifyToStringMethod() {
        String expectedResult =
                "CS 101: \nInstructor: Dr. John; Location: Room 101; Time: 10:00-11:00\n";
        String actualResult = testDepartment.toString();
        assertEquals(expectedResult, actualResult);
    }

    /**
     * Tests retrieving the department chair's name.
     */
    @Test
    public void checkDepartmentChairRetrieval() {
        String expectedResult = "Dr. Smith";
        String actualResult = testDepartment.getDepartmentChair();
        assertEquals(expectedResult, actualResult);
    }

    /**
     * Tests retrieving the number of majors in the department.
     */
    @Test
    public void checkNumberOfMajorsRetrieval() {
        int expectedResult = 200;
        int actualResult = testDepartment.getNumberOfMajors();
        assertEquals(expectedResult, actualResult);
    }

    /**
     * Tests retrieving the course selection of the department.
     */
    @Test
    public void checkCourseSelectionRetrieval() {
        Map<String, Course> courses = testDepartment.getCourseSelection();
        assertNotNull(courses);
        assertEquals(1, courses.size());
        assertTrue(courses.containsKey("101"));
    }

    /**
     * Tests the functionality of adding a major to the department.
     */
    @Test
    public void validateAddingMajor() {
        int initialMajors = testDepartment.getNumberOfMajors();
        testDepartment.addPersonToMajor();
        assertEquals(initialMajors + 1, testDepartment.getNumberOfMajors());
    }

    /**
     * Tests the functionality of adding a new course to the department.
     */
    @Test
    public void validateAddingCourse() {
        Course newCourse = new Course("Dr. Alice", "Room 202", "2:00-3:15", 100);
        testDepartment.addCourse("202", newCourse);
        Map<String, Course> courses = testDepartment.getCourseSelection();
        assertEquals(2, courses.size());
        assertTrue(courses.containsKey("202"));
    }
}