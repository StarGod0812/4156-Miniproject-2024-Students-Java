package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit tests for the Course class.
 */
@ContextConfiguration
public class CourseUnitTests {

  private static Course testCourse;

  /**
   * Sets up the test course instance before running any tests.
   */
  @BeforeAll
  public static void initializeTestCourse() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
  }

  /**
   * Verifies that the toString method returns the correct string representation of the course.
   */
  @Test
  public void verifyToStringMethod() {
    String expectedOutput = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedOutput, testCourse.toString());
  }

  /**
   * Tests the retrieval of the instructor's name.
   */
  @Test
  public void checkInstructorNameRetrieval() {
    String expectedName = "Griffin Newbold";
    String actualName = testCourse.getInstructorName();
    assertEquals(expectedName, actualName);
  }

  /**
   * Tests the retrieval of the course location.
   */
  @Test
  public void checkLocationRetrieval() {
    String expectedLocation = "417 IAB";
    String actualLocation = testCourse.getCourseLocation();
    assertEquals(expectedLocation, actualLocation);
  }

  /**
   * Tests the retrieval of the course time slot.
   */
  @Test
  public void checkTimeSlotRetrieval() {
    String expectedTimeSlot = "11:40-12:55";
    String actualTimeSlot = testCourse.getCourseTimeSlot();
    assertEquals(expectedTimeSlot, actualTimeSlot);
  }

  /**
   * Tests the ability to reassign the instructor's name.
   */
  @Test
  public void validateInstructorReassignment() {
    testCourse.reassignInstructor("John Doe");
    assertEquals("John Doe", testCourse.getInstructorName());
    // Reassign back to original name to keep state consistent for other tests
    testCourse.reassignInstructor("Griffin Newbold");
  }

  /**
   * Tests the ability to reassign the course location.
   */
  @Test
  public void validateLocationReassignment() {
    testCourse.reassignLocation("510 IAB");
    assertEquals("510 IAB", testCourse.getCourseLocation());
    // Reassign back to original location to keep state consistent for other tests
    testCourse.reassignLocation("417 IAB");
  }

  /**
   * Tests the ability to reassign the course time slot.
   */
  @Test
  public void validateTimeSlotReassignment() {
    testCourse.reassignTime("1:00-2:15");
    assertEquals("1:00-2:15", testCourse.getCourseTimeSlot());
    // Reassign back to original time slot to keep state consistent for other tests
    testCourse.reassignTime("11:40-12:55");
  }

  /**
   * Tests the functionality of enrolling a student in the course.
   */
  @Test
  public void validateStudentEnrollment() {
    testCourse.setEnrolledStudentCount(200);
    // Check if the course is not yet full
    assertFalse(testCourse.isCourseFull());
    // Attempt to enroll a student and verify success
    assertTrue(testCourse.enrollStudent());
  }

  /**
   * Tests the functionality of dropping a student from the course.
   */
  @Test
  public void validateStudentDrop() {
    testCourse.setEnrolledStudentCount(200);
    // Check if the course is not yet full
    assertFalse(testCourse.isCourseFull());
    // Attempt to drop a student and verify success
    assertTrue(testCourse.dropStudent());
  }

  /**
   * Tests when the course is full and a student tries to enroll.
   */
  @Test
  public void testEnrollWhenCourseIsFull() {
    testCourse.setEnrolledStudentCount(250); // Set to full capacity
    assertTrue(testCourse.isCourseFull());
    // Attempt to enroll a student when the course is full
    assertFalse(testCourse.enrollStudent());
  }

  /**
   * Tests when no students are enrolled and a student tries to drop.
   */
  @Test
  public void testDropWhenNoStudentsEnrolled() {
    testCourse.setEnrolledStudentCount(0); // No students enrolled
    assertFalse(testCourse.dropStudent());
  }

  /**
   * Tests the edge case when enrollment is exactly one less than capacity.
   */
  @Test
  public void testEnrollOneBeforeFull() {
    testCourse.setEnrolledStudentCount(249); // One less than capacity
    assertFalse(testCourse.isCourseFull());
    // Enroll one more student and check if the course becomes full
    assertTrue(testCourse.enrollStudent());
    assertTrue(testCourse.isCourseFull());
  }

  /**
   * Tests reassigning instructor to an empty string.
   */
  @Test
  public void testReassignInstructorToEmpty() {
    testCourse.reassignInstructor(""); // Assign empty string as instructor name
    assertEquals("", testCourse.getInstructorName());
    // Reassign back to original instructor name
    testCourse.reassignInstructor("Griffin Newbold");
  }
}
