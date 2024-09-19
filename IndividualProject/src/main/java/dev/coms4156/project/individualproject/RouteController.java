package dev.coms4156.project.individualproject;

import java.util.Locale;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class contains all the API routes for the system,
 * handling various requests related to department and course data.
 */
@RestController
public class RouteController {

  /**
   * Redirects to the homepage with instructions on how to use the API.
   *
   * @return A String containing instructions and an example URL for making API calls.
   */
  @GetMapping({"/", "/index", "/home"})
  public String index() {
    return "Welcome, in order to make an API call direct your browser or Postman to an endpoint "
            + "\n\n This can be done using the following format: \n\n http:127.0.0"
            + ".1:8080/endpoint?arg=value";
  }

  /**
   * Retrieves the details of the specified department.
   *
   * @param deptCode A {@code String} representing the department code to retrieve.
   * @return A {@code ResponseEntity} containing the department details or an error message.
   */
  @GetMapping(value = "/retrieveDept", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> retrieveDepartment(@RequestParam("deptCode") String deptCode) {
    try {
      Map<String, Department> departmentMapping;
      departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
      return departmentMapping.containsKey(deptCode.toUpperCase(Locale.ROOT))
              ? new ResponseEntity<>(departmentMapping.get(
                      deptCode.toUpperCase(Locale.ROOT)).toString(), HttpStatus.OK)
              : new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Retrieves the details of the specified course within a department.
   *
   * @param deptCode   A {@code String} representing the department code.
   * @param courseCode A {@code int} representing the course code to retrieve.
   * @return A {@code ResponseEntity} containing the course details or an error message.
   */
  @GetMapping(value = "/retrieveCourse", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> retrieveCourse(@RequestParam("deptCode") String deptCode,
                                          @RequestParam("courseCode") int courseCode) {
    try {
      if (retrieveDepartment(deptCode).getStatusCode() == HttpStatus.OK) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> coursesMapping;
        coursesMapping = departmentMapping.get(deptCode).getCourseSelection();
        return coursesMapping.containsKey(Integer.toString(courseCode))
                ? new ResponseEntity<>(coursesMapping.get(
                        Integer.toString(courseCode)).toString(), HttpStatus.OK)
                : new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
      }
      return new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Checks if the specified course has reached its enrollment capacity.
   *
   * @param deptCode   A {@code String} representing the department code.
   * @param courseCode A {@code int} representing the course code to check.
   * @return A {@code ResponseEntity} indicating whether the course is full or not.
   */
  @GetMapping(value = "/isCourseFull", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> isCourseFull(@RequestParam("deptCode") String deptCode,
                                        @RequestParam("courseCode") int courseCode) {
    try {
      if (retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> coursesMapping;
        coursesMapping = departmentMapping.get(deptCode).getCourseSelection();
        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        return new ResponseEntity<>(requestedCourse.isCourseFull(), HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Retrieves the number of majors in the specified department.
   *
   * @param deptCode A {@code String} representing the department code.
   * @return A {@code ResponseEntity} containing the number of majors or an error message.
   */
  @GetMapping(value = "/getMajorCountFromDept", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getMajorCtFromDept(@RequestParam("deptCode") String deptCode) {
    try {
      if (retrieveDepartment(deptCode).getStatusCode() == HttpStatus.OK) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        return new ResponseEntity<>("There are: " + departmentMapping.get(
                deptCode).getNumberOfMajors() + " majors in the department", HttpStatus.OK);
      }
      return new ResponseEntity<>("Department Not Found", HttpStatus.FORBIDDEN);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Retrieves the name of the department chair for the specified department.
   *
   * @param deptCode A {@code String} representing
   *                 the department code.
   * @return A {@code ResponseEntity} containing the
   *         name of the department chair or an error message.
   */
  @GetMapping(value = "/idDeptChair", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> identifyDeptChair(@RequestParam("deptCode") String deptCode) {
    try {
      if (retrieveDepartment(deptCode).getStatusCode() == HttpStatus.OK) {
        Map<String, Department> departmentMapping =
                IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        return new ResponseEntity<>(departmentMapping.get(
                deptCode).getDepartmentChair() + " is the department chair.", HttpStatus.OK);
      }
      return new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Retrieves the location of the specified course.
   *
   * @param deptCode   A {@code String} representing the department code.
   * @param courseCode A {@code int} representing the course code.
   * @return A {@code ResponseEntity} containing the course location or an error message.
   */
  @GetMapping(value = "/findCourseLocation", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> findCourseLocation(@RequestParam("deptCode") String deptCode,
                                              @RequestParam("courseCode") int courseCode) {
    try {
      if (retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK) {
        Map<String, Department> departmentMapping =
                IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> coursesMapping = departmentMapping.get(deptCode).getCourseSelection();
        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        return new ResponseEntity<>(requestedCourse.getCourseLocation()
                + " is where the course is located.", HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Retrieves the instructor of the specified course.
   *
   * @param deptCode   A {@code String} representing the department code.
   * @param courseCode A {@code int} representing the course code.
   * @return A {@code ResponseEntity} containing the instructor's name or an error message.
   */
  @GetMapping(value = "/findCourseInstructor", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> findCourseInstructor(@RequestParam("deptCode") String deptCode,
                                                @RequestParam("courseCode") int courseCode) {
    try {
      if (retrieveCourse(deptCode, courseCode)
              .getStatusCode() == HttpStatus.OK) {
        Map<String, Department> departmentMapping =
                IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> coursesMapping =
                departmentMapping.get(deptCode).getCourseSelection();
        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        return new ResponseEntity<>(requestedCourse
                .getInstructorName() + " is the instructor for the course.", HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Retrieves the time the specified course meets.
   *
   * @param deptCode   A {@code String} representing the department code.
   * @param courseCode A {@code int} representing the course code.
   * @return A {@code ResponseEntity} containing the course time or an error message.
   */
  @GetMapping(value = "/findCourseTime", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> findCourseTime(@RequestParam("deptCode") String deptCode,
                                          @RequestParam("courseCode") int courseCode) {
    try {
      if (retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK) {
        Map<String, Department> departmentMapping =
                IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> coursesMapping = departmentMapping.get(deptCode).getCourseSelection();
        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        return new ResponseEntity<>("The course meets at: "
                + requestedCourse.getCourseTimeSlot(), HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Adds a student to the specified department.
   *
   * @param deptCode A {@code String} representing the department code.
   * @return A {@code ResponseEntity} containing a success message or an error message.
   */
  @PatchMapping(value = "/addMajorToDept", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> addMajorToDept(@RequestParam("deptCode") String deptCode) {
    try {
      if (retrieveDepartment(deptCode).getStatusCode() == HttpStatus.OK) {
        Map<String, Department> departmentMapping =
                IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Department specifiedDept = departmentMapping.get(deptCode);
        specifiedDept.addPersonToMajor();
        return new ResponseEntity<>("Attribute was updated successfully", HttpStatus.OK);
      }
      return new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Removes a student from the specified department.
   *
   * @param deptCode A {@code String} representing the department code.
   * @return A {@code ResponseEntity} containing a success
   *         message or an error message.
   */
  @PatchMapping(value = "/removeMajorFromDept", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> removeMajorFromDept(@RequestParam("deptCode") String deptCode) {
    try {
      if (retrieveDepartment(deptCode)
              .getStatusCode() == HttpStatus.OK) {
        Map<String, Department> departmentMapping =
                IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Department specifiedDept = departmentMapping.get(deptCode);
        specifiedDept.dropPersonFromMajor();
        return new ResponseEntity<>("Attribute was updated or is at minimum", HttpStatus.OK);
      }
      return new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Drops a student from the specified course.
   *
   * @param deptCode   A {@code String} representing the department code.
   * @param courseCode A {@code int} representing the course code.
   * @return A {@code ResponseEntity} containing a success message,
   *         an error message, or information about the operation.
   */
  @PatchMapping(value = "/dropStudentFromCourse", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> dropStudent(@RequestParam("deptCode") String deptCode,
                                       @RequestParam("courseCode") int courseCode) {
    try {
      if (retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK) {
        Map<String, Department> departmentMapping =
                IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> coursesMapping =
                departmentMapping.get(deptCode).getCourseSelection();
        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        boolean isStudentDropped = requestedCourse.dropStudent();
        return isStudentDropped
                ? new ResponseEntity<>("Student has been dropped.", HttpStatus.OK)
                : new ResponseEntity<>("Student has not been dropped.", HttpStatus.BAD_REQUEST);
      } else {
        return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Updates the enrollment count for the specified course.
   *
   * @param deptCode  A {@code String} representing the department code.
   * @param courseCode A {@code int} representing the course code.
   * @param count     A {@code int} representing the new enrollment count.
   * @return A {@code ResponseEntity} containing a success message or an error message.
   */
  @PatchMapping(value = "/setEnrollmentCount", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> setEnrollmentCount(@RequestParam("deptCode") String deptCode,
                                              @RequestParam("courseCode") int courseCode,
                                              @RequestParam("count") int count) {
    try {
      if (retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK) {
        Map<String, Department> departmentMapping =
                IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> coursesMapping = departmentMapping
                .get(deptCode).getCourseSelection();
        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        requestedCourse.setEnrolledStudentCount(count);
        return new ResponseEntity<>("Attribute was updated successfully.", HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Changes the time of the specified course.
   *
   * @param deptCode  A {@code String} representing the department code.
   * @param courseCode A {@code int} representing the course code.
   * @param time      A {@code String} representing the new time for the course.
   * @return A {@code ResponseEntity} containing a success message or an error message.
   */
  @PatchMapping(value = "/changeCourseTime", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> changeCourseTime(@RequestParam("deptCode") String deptCode,
                                            @RequestParam("courseCode") int courseCode,
                                            @RequestParam("time") String time) {
    try {
      if (retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK) {
        Map<String, Department> departmentMapping =
                IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> coursesMapping =
                departmentMapping.get(deptCode).getCourseSelection();
        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        requestedCourse.reassignTime(time);
        return new ResponseEntity<>("Attribute was updated successfully.", HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Changes the instructor of the specified course.
   *
   * @param deptCode  A {@code String} representing the department code.
   * @param courseCode A {@code int} representing the course code.
   * @param teacher   A {@code String} representing the new instructor for the course.
   * @return A {@code ResponseEntity} containing a success message or an error message.
   */
  @PatchMapping(value = "/changeCourseTeacher", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> changeCourseTeacher(@RequestParam("deptCode") String deptCode,
                                               @RequestParam("courseCode") int courseCode,
                                               @RequestParam("teacher") String teacher) {
    try {
      if (retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK) {
        Map<String, Department> departmentMapping =
                IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> coursesMapping = departmentMapping
                .get(deptCode).getCourseSelection();
        Course requestedCourse = coursesMapping
                .get(Integer.toString(courseCode));
        requestedCourse.reassignInstructor(teacher);
        return new ResponseEntity<>("Attribute was updated successfully.", HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Changes the location of the specified course.
   *
   * @param deptCode  A {@code String} representing the department code.
   * @param courseCode A {@code int} representing the course code.
   * @param location  A {@code String} representing the new location for the course.
   * @return A {@code ResponseEntity} containing a success message or an error message.
   */
  @PatchMapping(value = "/changeCourseLocation", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> changeCourseLocation(@RequestParam("deptCode") String deptCode,
                                                @RequestParam("courseCode") int courseCode,
                                                @RequestParam("location") String location) {
    try {
      if (retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK) {
        Map<String, Department> departmentMapping =
                IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> coursesMapping =
                departmentMapping.get(deptCode).getCourseSelection();
        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        requestedCourse.reassignLocation(location);
        return new ResponseEntity<>("Attribute was updated successfully.", HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Handles exceptions by logging the error and returning a generic error response.
   *
   * @param e The exception that occurred.
   * @return A {@code ResponseEntity} containing an error message.
   */
  private ResponseEntity<?> handleException(Exception e) {
    System.out.println(e.toString());
    return new ResponseEntity<>("An Error has occurred", HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
