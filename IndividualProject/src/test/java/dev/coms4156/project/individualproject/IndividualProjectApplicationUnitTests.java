package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Unit tests for the IndividualProjectApplication class.
 */
@SpringBootTest
public class IndividualProjectApplicationUnitTests {

  private IndividualProjectApplication application;
  private MyFileDatabase mockDatabase;

  /**
   * Sets up the test environment before each test method.
   */
  @BeforeEach
  public void setUp() {
    application = new IndividualProjectApplication();
    mockDatabase = Mockito.mock(MyFileDatabase.class);
    IndividualProjectApplication.overrideDatabase(mockDatabase);
  }

  /**
   * Cleans up after each test.
   */
  @AfterEach
  public void tearDown() {
    IndividualProjectApplication.myFileDatabase = null;
  }

  /**
   * Tests the run method with the "setup" argument.
   */
  @Test
  public void testRunWithSetupArgument() {
    // Given
    String[] args = {"setup"};

    // When
    application.run(args);

    // Then
    assertNotNull(IndividualProjectApplication.myFileDatabase);
    verify(mockDatabase, never()).saveContentsToFile();
    System.out.println("testRunWithSetupArgument passed");
  }

  /**
   * Tests the run method without the "setup" argument.
   */
  @Test
  public void testRunWithoutSetupArgument() {
    // Given
    String[] args = {};

    // When
    application.run(args);

    // Then
    assertNotNull(IndividualProjectApplication.myFileDatabase);
    verify(mockDatabase, never()).saveContentsToFile();
    System.out.println("testRunWithoutSetupArgument passed");
  }

  /**
   * Tests the resetDataFile method to ensure it correctly sets up the database mapping.
   */
  @Test
  public void testResetDataFile() {
    // Given
    doNothing().when(mockDatabase).setMapping(any());

    // When
    application.resetDataFile();

    // Then
    verify(mockDatabase, times(1)).setMapping(any());
    System.out.println("testResetDataFile passed");
  }

  /**
   * Tests the onTermination method when saveData is true.
   */
  @Test
  public void testOnTerminationWithSaveDataTrue() {
    // Given
    IndividualProjectApplication.saveData = true;  // 确保 saveData 被设置为 true
    IndividualProjectApplication.myFileDatabase = mockDatabase;  // mockDatabase 作为数据库实例
    doNothing().when(mockDatabase).saveContentsToFile();  // 不执行实际的保存操作

    // When
    application.onTermination();  // 调用 onTermination 方法

    // Then
    verify(mockDatabase, times(1)).saveContentsToFile();  // 验证 saveContentsToFile 被调用了一次
    System.out.println("testOnTerminationWithSaveDataTrue passed");
  }

  /**
   * Tests the onTermination method when saveData is false.
   */
  @Test
  public void testOnTerminationWithSaveDataFalse() {
    // Given
    IndividualProjectApplication.saveData = false;
    IndividualProjectApplication.myFileDatabase = mockDatabase;

    // When
    application.onTermination();

    // Then
    verify(mockDatabase, never()).saveContentsToFile();
    System.out.println("testOnTerminationWithSaveDataFalse passed");
  }

  /**
   * Tests the overrideDatabase method.
   */
  @Test
  public void testOverrideDatabase() {
    // Given
    MyFileDatabase testDatabase = Mockito.mock(MyFileDatabase.class);

    // When
    IndividualProjectApplication.overrideDatabase(testDatabase);

    // Then
    assertNotNull(IndividualProjectApplication.myFileDatabase);
    verify(testDatabase, never()).saveContentsToFile();
    System.out.println("testOverrideDatabase passed");
  }
}
