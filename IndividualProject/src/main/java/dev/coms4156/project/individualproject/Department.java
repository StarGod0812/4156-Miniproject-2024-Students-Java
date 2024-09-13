package dev.coms4156.project.individualproject;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * Represents a department within an educational institution.
 * This class manages departmental data such as its code, course offerings,
 * department chair, and the number of majors.
 */
public class Department implements Serializable {

  /**
   * Initializes a new instance of the Department class with the specified parameters.
   *
   * @param deptCode         The unique identifier of the department.
   * @param courses          A map containing the courses offered by the department.
   * @param departmentChair  The name of the department's head.
   * @param numberOfMajors   The total number of students majoring in this department.
   */
  public Department(String deptCode, Map<String, Course> courses, String departmentChair,
                    int numberOfMajors) {
    this.deptCode = deptCode;
    this.courses = courses;
    this.departmentChair = departmentChair;
    this.numberOfMajors = numberOfMajors;
  }

  /**
   * Retrieves the number of students majoring in the department.
   *
   * @return The number of majors.
   */
  public int getNumberOfMajors() {
    return numberOfMajors;
  }

  /**
   * Retrieves the name of the department's head.
   *
   * @return The name of the department's head.
   */
  public String getDepartmentChair() {
    return departmentChair;
  }

  /**
   * Retrieves the map of courses offered by the department.
   *
   * @return A map of courses.
   */
  public Map<String, Course> getCourseSelection() {
    return courses;
  }

  /**
   * Increments the number of majors in the department by one.
   */
  public void addPersonToMajor() {
    numberOfMajors++;
  }

  /**
   * Decrements the number of majors in the department by one if the count is positive.
   */
  public void dropPersonFromMajor() {
    if (numberOfMajors > 0) {
      numberOfMajors--;
    }
  }

  /**
   * Adds a new course to the department's course selection.
   *
   * @param courseId The ID of the course to add.
   * @param course   The Course object to add.
   */
  public void addCourse(String courseId, Course course) {
    courses.put(courseId, course);
  }

  /**
   * Creates a new course and adds it to the department's course selection.
   *
   * @param courseId       The ID of the new course.
   * @param instructorName The name of the instructor teaching the course.
   * @param courseLocation The location where the course is held.
   * @param courseTimeSlot The time slot of the course.
   * @param capacity       The maximum number of students that can enroll in the course.
   */
  public void createCourse(String courseId, String instructorName, String courseLocation,
                           String courseTimeSlot, int capacity) {
    Course newCourse = new Course(instructorName, courseLocation, courseTimeSlot, capacity);
    addCourse(courseId, newCourse);
  }

  /**
   * Provides a string representation of the department, including its code and the courses it offers.
   *
   * @return A string representing the department.
   */
  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    for (Map.Entry<String, Course> entry : courses.entrySet()) {
      String key = entry.getKey();
      Course value = entry.getValue();
      result.append(deptCode).append(" ").append(key).append(": ").append(value.toString())
              .append("\n");
    }

    return result.toString();
  }

  @Serial
  private static final long serialVersionUID = 198286L;
  private Map<String, Course> courses;
  private String departmentChair;
  private String deptCode;
  private int numberOfMajors;
}