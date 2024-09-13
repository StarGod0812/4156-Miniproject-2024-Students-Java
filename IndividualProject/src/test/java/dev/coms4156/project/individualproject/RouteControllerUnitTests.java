package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/** Test for RouteController. */
@SpringBootTest
public class RouteControllerUnitTests {

    private RouteController routeController;
    private MyFileDatabase mockDatabase;

    /** Before each test, set up the necessary mocks and objects. */
    @BeforeEach
    public void setUp() {
        routeController = new RouteController();
        mockDatabase = Mockito.mock(MyFileDatabase.class);
        IndividualProjectApplication.overrideDatabase(mockDatabase);
    }

    @Test
    public void testRetrieveDepartmentSuccess() {
        // Arrange
        Course course = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
        Map<String, Department> departmentMap = new HashMap<>();
        Department mockDept = new Department("COMS", new HashMap<>(), "Department Chair", 2700);
        mockDept.addCourse("101", course);
        departmentMap.put("COMS", mockDept);
        when(mockDatabase.getDepartmentMapping()).thenReturn(departmentMap);

        // Act
        ResponseEntity<String> response = (ResponseEntity<String>) routeController.retrieveDepartment("COMS");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("COMS"));
        verify(mockDatabase, times(1)).getDepartmentMapping();
    }

    @Test
    public void testRetrieveDepartmentNotFound() {
        // Arrange
        Map<String, Department> departmentMap = new HashMap<>();
        when(mockDatabase.getDepartmentMapping()).thenReturn(departmentMap);

        // Act
        ResponseEntity<String> response = (ResponseEntity<String>) routeController.retrieveDepartment("MATH");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Department Not Found", response.getBody());
    }

    @Test
    public void testRetrieveCourseSuccess() {
        // Arrange
        Map<String, Course> coursesMap = new HashMap<>();
        Course mockCourse = new Course("Instructor", "Room 101", "10:00-11:00", 100);
        coursesMap.put("1004", mockCourse);

        Department mockDept = new Department("COMS", coursesMap, "Chair", 2700);
        Map<String, Department> departmentMap = new HashMap<>();
        departmentMap.put("COMS", mockDept);

        when(mockDatabase.getDepartmentMapping()).thenReturn(departmentMap);

        // Act
        ResponseEntity<String> response = (ResponseEntity<String>) routeController.retrieveCourse("COMS", 1004);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Instructor"));
    }

    @Test
    public void testRetrieveCourseNotFound() {
        // Arrange
        Map<String, Department> departmentMap = new HashMap<>();
        Department mockDept = new Department("COMS", new HashMap<>(), "Chair", 2700);
        departmentMap.put("COMS", mockDept);
        when(mockDatabase.getDepartmentMapping()).thenReturn(departmentMap);

        // Act
        ResponseEntity<String> response = (ResponseEntity<String>) routeController.retrieveCourse("COMS", 1234);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Course Not Found", response.getBody());
    }

    @Test
    public void testIsCourseFull() {
        // Arrange
        Course mockCourse = Mockito.mock(Course.class);
        when(mockCourse.isCourseFull()).thenReturn(true);

        Map<String, Course> coursesMap = new HashMap<>();
        coursesMap.put("1004", mockCourse);

        Department mockDept = new Department("COMS", coursesMap, "Chair", 2700);
        Map<String, Department> departmentMap = new HashMap<>();
        departmentMap.put("COMS", mockDept);

        when(mockDatabase.getDepartmentMapping()).thenReturn(departmentMap);

        // Act
        ResponseEntity<Boolean> response = (ResponseEntity<Boolean>) routeController.isCourseFull("COMS", 1004);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
    }

    @Test
    public void testRetrieveDepartmentThrowsException() {
        // Arrange
        when(mockDatabase.getDepartmentMapping()).thenThrow(new RuntimeException("Database error"));

        // Act
        ResponseEntity<?> response = routeController.retrieveDepartment("COMS");

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An Error has occurred", response.getBody());
    }

    @Test
    public void testRetrieveDepartmentWithNullDeptCode() {
        // Act
        ResponseEntity<?> response = routeController.retrieveDepartment(null);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An Error has occurred", response.getBody());
    }
}