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
   * Tests the functionality of dropping a major from the department.
   */
  @Test
  public void validateDroppingMajor() {
    int initialMajors = testDepartment.getNumberOfMajors();
    testDepartment.dropPersonFromMajor();
    assertEquals(initialMajors - 1, testDepartment.getNumberOfMajors());
  }

  /**
   * Tests the edge case of dropping a major when the department has zero majors.
   */
  @Test
  public void validateDroppingMajorWhenZero() {
    // Set the number of majors to 0
    testDepartment = new Department("CS", new HashMap<>(), "Dr. Smith", 0);
    testDepartment.dropPersonFromMajor();
    assertEquals(0, testDepartment.getNumberOfMajors(), "Number of majors should remain zero");
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

  /**
   * Tests the functionality of creating and adding a new course to the department.
   */
  @Test
  public void validateCreatingCourse() {
    testDepartment.createCourse("303", "Dr. Bob", "Room 303", "4:00-5:00", 60);
    Map<String, Course> courses = testDepartment.getCourseSelection();
    assertEquals(2, courses.size());
    assertTrue(courses.containsKey("303"));
  }

  /**
   * Tests adding a course when the course map is initially empty.
   */
  @Test
  public void validateAddingCourseToEmptyDepartment() {
    // Create a new department with no courses
    testDepartment = new Department("ENG", new HashMap<>(), "Dr. Taylor", 150);
    Course newCourse = new Course("Dr. Alice", "Room 202", "2:00-3:15", 100);
    testDepartment.addCourse("ENG101", newCourse);
    assertEquals(1, testDepartment.getCourseSelection().size());
    assertTrue(testDepartment.getCourseSelection().containsKey("ENG101"));
  }

  /**
   * Tests creating a course and ensuring that it is added properly to an empty department.
   */
  @Test
  public void validateCreatingCourseInEmptyDepartment() {
    // Create a new department with no courses
    testDepartment = new Department("ENG", new HashMap<>(), "Dr. Taylor", 150);
    testDepartment.createCourse("ENG101", "Prof. Martin", "Room 305", "9:00-10:15", 30);
    Map<String, Course> courses = testDepartment.getCourseSelection();
    assertEquals(1, courses.size());
    assertTrue(courses.containsKey("ENG101"));
  }

  /**
   * Tests the edge case where an existing course is
   * overwritten by adding a new course with the same ID.
   */
  @Test
  public void validateOverwritingCourse() {
    Course originalCourse = new Course("Dr. John", "Room 101", "10:00-11:00", 50);
    testDepartment.addCourse("101", originalCourse);

    Course newCourse = new Course("Dr. Jane", "Room 102", "11:00-12:00", 75);
    testDepartment.addCourse("101", newCourse);

    Map<String, Course> courses = testDepartment.getCourseSelection();
    assertEquals(1, courses.size()); // Still only 1 course, but now it should be the new one
    assertEquals("Dr. Jane", courses.get("101").getInstructorName());
  }
}
