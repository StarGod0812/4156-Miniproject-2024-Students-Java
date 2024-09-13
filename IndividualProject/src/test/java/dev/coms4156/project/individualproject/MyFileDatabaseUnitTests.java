package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Comprehensive unit tests for the MyFileDatabase class to validate its functionality.
 */
public class MyFileDatabaseUnitTests {

    private MyFileDatabase myFileDatabase;
    private String filePath = "./data.txt";  // Path to the test data file

    /**
     * Prepares the test environment by initializing the database with a predefined mapping before each test.
     */
    @BeforeEach
    public void setup() {
        // Create an initial department mapping
        Map<String, Department> initialMapping = new HashMap<>();
        initialMapping.put("CS", new Department("CS", new HashMap<>(), "Dr. Smith", 100));
        initialMapping.put("Math", new Department("Math", new HashMap<>(), "Dr. Johnson", 80));

        // Initialize the database with the mapping and save it to the file
        myFileDatabase = new MyFileDatabase(1, filePath);
        myFileDatabase.setMapping(initialMapping);
        myFileDatabase.saveContentsToFile();
    }

    /**
     * Cleans up after each test by deleting the test data file to ensure a clean state for subsequent tests.
     */
    @AfterEach
    public void cleanup() {
        // Delete the test file after each test to maintain a clean test environment
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * Tests the serialization and deserialization process to ensure data integrity.
     */
    @Test
    public void testSerializationAndDeserialization() {
        // Deserialize the database and verify the initial department mapping
        MyFileDatabase deserializedDb = new MyFileDatabase(0, filePath);
        Map<String, Department> mapping = deserializedDb.getDepartmentMapping();
        assertNotNull(mapping, "The deserialized mapping should not be null.");
        assertEquals(2, mapping.size(), "The deserialized mapping should contain two departments.");
        assertEquals("Dr. Smith", mapping.get("CS").getDepartmentChair(), "The department chair for CS should be correctly deserialized.");
        assertEquals("Dr. Johnson", mapping.get("Math").getDepartmentChair(), "The department chair for Math should be correctly deserialized.");
    }

    /**
     * Verifies the setMapping and getMapping methods to ensure they handle department mappings correctly.
     */
    @Test
    public void testSetMappingAndGetMapping() {
        // Update the department mapping and verify the changes
        Map<String, Department> newMapping = new HashMap<>();
        newMapping.put("Physics", new Department("Physics", new HashMap<>(), "Dr. Brown", 60));

        myFileDatabase.setMapping(newMapping);
        Map<String, Department> retrievedMapping = myFileDatabase.getDepartmentMapping();
        assertEquals(1, retrievedMapping.size(), "The updated mapping should contain only one department.");
        assertEquals("Dr. Brown", retrievedMapping.get("Physics").getDepartmentChair(), "The department chair for Physics should be correctly set.");
    }

    /**
     * Validates the toString method to ensure it provides the correct string representation of the current state.
     */
    @Test
    public void testToString() {
        // Verify the string representation of the current database state
        String expectedOutput = "For the CS department: \n"
                + "For the Math department: \n";
        assertEquals(expectedOutput, myFileDatabase.toString(), "The toString method should accurately reflect the database state.");
    }
}