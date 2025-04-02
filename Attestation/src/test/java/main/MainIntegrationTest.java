package main;

import com.gridnine.testing.Main;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainIntegrationTest {

    @Test
    @DisplayName("Должен корректно фильтровать стандартный набор перелетов")
    void main_IntegrationTestWithDefaultFlights() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Main.main(new String[]{});

        String output = outContent.toString();

        assertTrue(output.contains("Исходный список перелетов:"));
        assertTrue(output.contains("Отфильтрованный список перелетов:"));

        assertTrue(output.split("Filtered Flights:").length > 1);
    }

    @Test
    @DisplayName("Должен корректно работать без аргументов командной строки")
    void main_ShouldWorkWithNoArguments() {
        assertDoesNotThrow(() -> Main.main(new String[]{}));
    }

    @Test
    @DisplayName("Должен игнорировать аргументы командной строки")
    void main_ShouldIgnoreCommandLineArguments() {
        assertDoesNotThrow(() -> Main.main(new String[]{"test", "123"}));
    }

}