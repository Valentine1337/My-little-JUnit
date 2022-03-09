package testsForLessonSix;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.*;


import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Класс с простыми тестами")
public class SimpleTest {

    @BeforeEach
    void browserSettingsBegin() {
        System.out.println("Размер браузера установлен 1920х1080");
    }

    @AfterEach
    void browserSettingsEnd() {
        System.out.println("Браузер закрыт");
    }

    @Test
    @DisplayName("Ожидаемо зеленый тест")
    void simpleGreenTest() {
        assertTrue(3 > 2);
    }

    @Test
    @DisplayName("Ожидаемо красный тест")
    void simpleRedTest() {
        assertTrue(3 < 2);
    }

    @Test
    @Disabled("bug: JIRA-12232")
    void simpleBrokenTest() {
        throw new IllegalStateException("Broken :(");
    }
}
