package Tests.V2;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.Test;
import org.openqa.selenium.Keys;
import ru.yandex.qatools.allure.annotations.Step;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static java.util.Arrays.asList;

/**
 * Created by oryabinskiy on 10/28/2015.
 */
public class TodoMVCTest extends AtTodoMVCPageWithClearedDataAfterEachTest {
    @Test
    public void testTaskLifeCycle(){

        add("1", "2", "3", "4");
        toggle("1");

        moveToCompleted();
        assertVisible("1");

        toggleAll();
        toggleAll();

        moveToActive();
        assertVisible("1", "2", "3", "4");

        startEdit("3", "New").sendKeys(Keys.ENTER);
        assertVisible("1", "2", "New", "4");

        toggleAll();

        moveToCompleted();
        toggle("4");
        assertVisible("1", "2", "New");
        clearCompleted();
        assertNoVisibleTasks();

        moveToAll();
        delete("4");
        assertNoVisibleTasks();

    }

    @Test
    public void editToEmpty(){

        add("1");
        assertVisible("1");
        startEdit("1", " ").sendKeys(Keys.ENTER);
        assertNoVisibleTasks();
    }

    @Test
    public void cancelEdit(){

        add("1");
        assertVisible("1");
        startEdit("1", "Edited").sendKeys(Keys.ESCAPE);
        assertVisible("1");
    }


    public ElementsCollection tasks = $$("#todo-list li");

    @Step
    public void moveToAll(){
        $("a[href='#/']").click();
    }

    @Step
    public void moveToActive(){
        $("a[href='#/active']").click();
    }

    @Step
    public void moveToCompleted(){
        $("a[href='#/completed']").click();
    }

    @Step
    public void toggleAll(){
        $("#toggle-all").click();
    }

    @Step
    public void add(String... names) {
        for (String name : asList(names)) {
            $("#new-todo").setValue(name).pressEnter();
        }
    }

    @Step
    public void delete(String name){
        tasks.find(exactText(name)).hover().find(".destroy").click();
    }

    @Step
    public void toggle(String name){
        tasks.find(exactText(name)).find(".toggle").click();;
    }
    @Step
    public void clearCompleted(){
        $("#clear-completed").click();
    }

    @Step
    public SelenideElement startEdit(String name, String newName) {
        tasks.find(exactText(name)).find("label").doubleClick();
        return tasks.find(cssClass("editing")).find(".edit").setValue(newName);
    }

    @Step
    public void assertVisible(String... names){
        tasks.filter(visible).shouldHave(exactTexts(names));
    }

    @Step
    public void assertNoVisibleTasks() {
        tasks.filter(visible).shouldBe(empty);
    }
}
