package dev.coms4156.project.individualproject;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a course entity with details such as the instructor, location, timeslot, capacity, and number of enrolled students.
 */
public class Course implements Serializable {

  @Serial
  private static final long serialVersionUID = 200812L;

  // The maximum number of students the course can accommodate
  private final int enrollmentCapacity;

  // The current number of students enrolled
  private int enrolledStudentCount;

  // The location where the course is taught
  private String courseLocation;

  // The name of the instructor
  private String instructorName;

  // The timeslot during which the course is scheduled
  private String courseTimeSlot;

  /**
   * Creates a new course object with the specified parameters. The initial number of enrolled students is set to zero.
   *
   * @param instructorName The name of the instructor.
   * @param courseLocation The location where the course is taught.
   * @param timeSlot The timeslot during which the course is scheduled.
   * @param capacity The maximum number of students the course can accommodate.
   */
  public Course(String instructorName, String courseLocation, String timeSlot, int capacity) {
    this.instructorName = instructorName;
    this.courseLocation = courseLocation;
    this.courseTimeSlot = timeSlot;
    this.enrollmentCapacity = capacity;
    this.enrolledStudentCount = 0;
  }

  /**
   * Checks if the course is fully enrolled.
   *
   * @return true if the course has reached its maximum capacity, false otherwise.
   */
  public boolean isCourseFull() {
    return this.enrollmentCapacity <= this.enrolledStudentCount;
  }

  /**
   * Attempts to enroll a new student in the course if there is still room available.
   *
   * @return true if the enrollment was successful, false if the course is already full.
   */
  public boolean enrollStudent() {
    if (!isCourseFull()) {
      this.enrolledStudentCount++;
      return true;  // Enrollment successful
    }
    return false;  // Course is full
  }

  /**
   * Attempts to drop a student from the course if there are currently enrolled students.
   *
   * @return true if the drop was successful, false if there are no students enrolled.
   */
  public boolean dropStudent() {
    if (this.enrolledStudentCount > 0) {
      this.enrolledStudentCount--;
      return true;  // Drop successful
    }
    return false;  // No students to drop
  }

  /**
   * Gets the location of the course.
   *
   * @return The location of the course.
   */
  public String getCourseLocation() {
    return this.courseLocation;
  }

  /**
   * Gets the name of the instructor.
   *
   * @return The name of the instructor.
   */
  public String getInstructorName() {
    return this.instructorName;
  }

  /**
   * Gets the timeslot of the course.
   *
   * @return The timeslot of the course.
   */
  public String getCourseTimeSlot() {
    return this.courseTimeSlot;
  }

  /**
   * Reassigns the instructor of the course.
   *
   * @param newInstructorName The new name of the instructor.
   */
  public void reassignInstructor(String newInstructorName) {
    this.instructorName = newInstructorName;
  }

  /**
   * Reassigns the location of the course.
   *
   * @param newLocation The new location of the course.
   */
  public void reassignLocation(String newLocation) {
    this.courseLocation = newLocation;
  }

  /**
   * Reassigns the timeslot of the course.
   *
   * @param newTime The new timeslot of the course.
   */
  public void reassignTime(String newTime) {
    this.courseTimeSlot = newTime;
  }

  /**
   * Sets the number of students currently enrolled in the course.
   *
   * @param count The number of students to set.
   */
  public void setEnrolledStudentCount(int count) {
    this.enrolledStudentCount = count;
  }

  @Override
  public String toString() {
    return "\nInstructor: " + this.instructorName + "; Location: " + this.courseLocation + "; Time: " + this.courseTimeSlot;
  }
}