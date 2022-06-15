package tests;

import org.junit.jupiter.api.*;


import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Класс с простыми тестами")
public class SimpleTest {

    @BeforeEach
    public void browserSettingsBegin() {
        System.out.println("Размер браузера установлен 1920х1080");
    }

    @Test
    @DisplayName("Ожидаемо зеленый тест")
    public void simpleGreenTest() {
        assertTrue(3 > 2);
    }

    @Test
    @DisplayName("Ожидаемо красный тест")
    public void simpleRedTest() {
        assertTrue(3 < 2);
    }

    @Test
    @DisplayName("Ожидаемо сломанный тест")
    public void simpleBrokenTest() {
        throw new IllegalStateException();
    }
}
