package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
}