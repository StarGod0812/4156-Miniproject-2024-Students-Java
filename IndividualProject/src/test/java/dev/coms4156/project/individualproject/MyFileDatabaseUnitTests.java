package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link MyFileDatabase} class to validate its functionality.
 */
public class MyFileDatabaseUnitTests {

  /** The MyFileDatabase instance used for testing. */
  private MyFileDatabase myFileDatabase;

  /** Path to the test data file. */
  private String filePath = "./test_data.txt"; // Path to the test data file

  /**
   * Sets up the test environment before each test.
   * Initializes the database with a predefined mapping and saves it to the file.
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
   * Cleans up the test environment after each test.
   * Deletes the test data file to maintain a clean state for subsequent tests.
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
   * Verifies that data is correctly saved to the file and loaded back.
   */
  @Test
  public void testSerializationAndDeserialization() {
    // Deserialize the database and verify the initial department mapping
    MyFileDatabase deserializedDb = new MyFileDatabase(0, filePath);
    Map<String, Department> mapping = deserializedDb.getDepartmentMapping();
    Assertions.assertNotNull(mapping, "The deserialized mapping should not be null.");
    assertEquals(2, mapping.size(), "The deserialized mapping should contain two departments.");
    assertEquals("Dr. Smith", mapping.get("CS")
            .getDepartmentChair(), "The department chair for "
            + "CS should be correctly deserialized.");
    assertEquals("Dr. Johnson", mapping.get("Math")
            .getDepartmentChair(), "The department chair for "
            + "Math should be correctly deserialized.");
  }

  /**
   * Tests the {@code setMapping} and {@code getDepartmentMapping} methods.
   * Verifies that the mapping is correctly updated and retrieved.
   */
  @Test
  public void testSetMappingAndGetMapping() {
    // Update the department mapping and verify the changes
    Map<String, Department> newMapping = new HashMap<>();
    newMapping.put("Physics", new Department(
            "Physics", new HashMap<>(), "Dr. Brown", 60));

    myFileDatabase.setMapping(newMapping);
    Map<String, Department> retrievedMapping = myFileDatabase.getDepartmentMapping();
    assertEquals(1, retrievedMapping.size(),
            "The updated mapping should contain only one department.");
    assertEquals("Dr. Brown", retrievedMapping.get(
                    "Physics").getDepartmentChair(),
            "The department chair for Physics should be correctly set.");
  }

  /**
   * Tests the {@code toString} method.
   * Verifies that the string representation contains the expected content.
   */
  @Test
  public void testToString() {
    // Verify the string representation of the current database state
    String actualOutput = myFileDatabase.toString();

    // Since toString depends on courses content, we simplify the check
    assertTrue(actualOutput.contains("For the CS department: \n"));
    assertTrue(actualOutput.contains("For the Math department: \n"));
  }

  /**
   * Tests the constructor with a {@code null} file path.
   * Expects an {@link IllegalArgumentException} to be thrown.
   */
  @Test
  public void testConstructorWithNullFilePath() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new MyFileDatabase(1, null);
    });
    assertEquals("File path must be provided.", exception.getMessage());
  }

  /**
   * Tests the constructor with an empty file path.
   * Expects an {@link IllegalArgumentException} to be thrown.
   */
  @Test
  public void testConstructorWithEmptyFilePath() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new MyFileDatabase(1, "   ");
    });
    assertEquals("File path must be provided.", exception.getMessage());
  }

  /**
   * Tests the deserialization process when the data file does not exist.
   * Expects an empty mapping to be returned.
   */
  @Test
  public void testDeserializeDataFromFileFileNotFound() {
    // Delete the file to simulate file not found
    File file = new File(filePath);
    if (file.exists()) {
      file.delete();
    }

    // Initialize database to trigger deserialization
    MyFileDatabase db = new MyFileDatabase(0, filePath);

    // Verify that an empty mapping is returned
    Map<String, Department> mapping = db.getDepartmentMapping();
    assertNotNull(mapping);
    assertEquals(0, mapping.size(), "Mapping should be empty when file does not exist.");
  }

  /**
   * Tests the deserialization process when the data file contains invalid data.
   * Expects an {@link IllegalArgumentException} to be thrown.
   *
   * @throws IOException if an I/O error occurs while writing invalid data.
   */
  @Test
  public void testDeserializeDataFromFileInvalidData() throws IOException {
    // Write invalid data to the file
    File file = new File(filePath);
    if (!file.exists()) {
      file.createNewFile();
    }

    try (FileOutputStream fos = new FileOutputStream(file);
         ObjectOutputStream oos = new ObjectOutputStream(fos)) {
      oos.writeObject("Invalid Data");
    }

    // Expect an IllegalArgumentException when initializing the database
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new MyFileDatabase(0, filePath);
    });

    assertEquals("The file does not contain a valid department mapping.", exception.getMessage());
  }

  /**
   * Tests that {@code getDepartmentMapping} returns a copy of the mapping.
   * Verifies that modifications to the returned map do not affect the original mapping.
   */
  @Test
  public void testGetDepartmentMappingReturnsCopy() {
    Map<String, Department> mapping = myFileDatabase.getDepartmentMapping();
    assertNotNull(mapping);

    // Modify the returned map
    mapping.put("NewDept", new Department("NewDept",
            new HashMap<>(), "Dr. New", 10));

    // Get the mapping again and verify that the original mapping is unchanged
    Map<String, Department> originalMapping = myFileDatabase.getDepartmentMapping();
    assertEquals(2, originalMapping.size(),
            "Original mapping should not be affected by external modifications.");
    assertFalse(originalMapping.containsKey("NewDept"),
            "Original mapping should not contain 'NewDept'.");
  }

}
