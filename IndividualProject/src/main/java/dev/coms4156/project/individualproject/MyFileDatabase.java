package dev.coms4156.project.individualproject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages department mappings using a file-based storage system.
 */
public class MyFileDatabase {

  /**
   * Initializes the database with content from a specified file.
   *
   * @param mode      Flag to determine if the database should load data from file.
   * @param filePath  The path to the file that stores the department mappings.
   * @throws IllegalArgumentException if the file path is invalid.
   */
  public MyFileDatabase(int mode, String filePath) {
    if (filePath == null || filePath.trim().isEmpty()) {
      throw new IllegalArgumentException("File path must be provided.");
    }
    this.filePath = filePath;
    this.departmentMapping = (mode == 0) ? deserializeDataFromFile() : new HashMap<>();
  }

  /**
   * Stores the department mapping into the file, overwriting any existing data.
   */
  public void saveContentsToFile() {
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
      out.writeObject(departmentMapping);
      System.out.println("Data has been serialized to file.");
    } catch (IOException e) {
      System.err.println("An error occurred while writing to the file: " + e.getMessage());
      e.printStackTrace();
    }
  }

  /**
   * Loads the department mapping from a file if it exists.
   *
   * @return A map containing department mappings.
   */
  Map<String, Department> deserializeDataFromFile() {
    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
      Object readObject = in.readObject();
      if (readObject instanceof Map<?, ?>) {
        return (Map<String, Department>) readObject;
      }
      throw new IllegalArgumentException("The file does not contain a valid department mapping.");
    } catch (IOException e) {
      System.err.println("An error occurred while reading from the file: " + e.getMessage());
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      System.err.println("The serialized class is not found: " + e.getMessage());
      e.printStackTrace();
    }
    return new HashMap<>();
  }

  /**
   * Updates the internal department mapping with new data.
   *
   * @param mapping The new mapping to be set.
   */
  public void setMapping(Map<String, Department> mapping) {
    this.departmentMapping = mapping;
  }

  /**
   * Retrieves the current department mapping.
   *
   * @return An unmodifiable view of the current department mapping.
   */
  public Map<String, Department> getDepartmentMapping() {
    return new HashMap<>(departmentMapping);
  }

  /**
   * Provides a textual representation of the current department mappings.
   *
   * @return A string that describes the contents of the database.
   */
  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    departmentMapping.forEach((department, details) ->
            stringBuilder.append("For the ").append(department).append(" department: \n")
                    .append(details.toString())
    );
    return stringBuilder.toString();
  }

  /** Path to the file where the serialized department mappings are stored. */
  private String filePath;

  /** Current department mappings. */
  private Map<String, Department> departmentMapping;
}