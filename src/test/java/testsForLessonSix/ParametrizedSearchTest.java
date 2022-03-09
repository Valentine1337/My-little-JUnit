package testsForLessonSix;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.by;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class ParametrizedSearchTest {

    @BeforeEach
    void precondition() {
        Configuration.browserSize = "1920x1080";
        Selenide.open("http://www.dota2protracker.com/");
    }

    @AfterEach
    void closeBrowser() {
        Selenide.closeWebDriver();
    }


    @ParameterizedTest(name = "Проверка отображения страницы со статистикой для героя \"{0}\"")
    @ValueSource(strings = {"Tiny", "Anti-Mage"})
    void easySearchTest(String heroName) {
        $(".hero_table #table_id").find(byText(heroName)).click();
        $("#role_Carry .role_box_left").shouldHave(text(("Carry")));
    }


    @ParameterizedTest(name = "Проверка отображения страницы со статистикой для героя \"{0}\"")
    @CsvSource(value = {
            "Primal Beast, Carry",
            "Rubick, Mid"
    })
    void easySearchTest(String heroName, String expectedSkill) {
        $(".hero_table #table_id").find(byText(heroName)).click();
        $(".role_box").shouldHave(text((expectedSkill)));
    }


    static Stream<Arguments> mixedArgumentsTestDataProvider() {
        return Stream.of(
                Arguments.of("ILTW", "Invoker", "Nigma.iLTW"),
                Arguments.of("Sumail","Storm Spirit", "OG.SumaiL")
        );
    }
    @MethodSource(value = "mixedArgumentsTestDataProvider")
    @ParameterizedTest(name = "Проверка ссылки на профиль дотабафф и корректного никнейма у игрока \"{0}\"")
    void addNewCustomerTest(String d2protrackerNickName, String heroName, String dotabuffNickName) {
        $("#search_box input").setValue(d2protrackerNickName);
        $("#search_results .players li").click();
        $(".player_stats div").$("a", 1).click();
        $(".col-8").$("section", 1).$(byText(heroName)).click();
        $(".header-content-title h1").shouldHave(text(dotabuffNickName));
    }
}
