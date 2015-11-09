package Tests.V3;

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
 * Created by oryabinskiy on 11/3/2015.
 */
public class TodoMVCTest extends Tests.V3.AtTodoMVCPageWithClearedDataAfterEachTest {
    @Test
    public void testTaskLifeCycle(){

        add("1");
        toggle("1");

        moveToActive();
        assertNoVisibleTasks();
        add("2", "3", "4");
        assertItemsLeft(3);
        startEdit("2", "New").sendKeys(Keys.ENTER);
        assertItemsLeft(3);
        assertVisible("New", "3", "4");
        toggleAll();
        assertItemsLeft(0);

        moveToCompleted();
        toggle("3");
        assertVisible("1", "New", "4");
        toggleAll();
        toggleAll();
        assertItemsLeft(4);

        moveToActive();
        assertVisible("1", "New", "3", "4");
        delete("New");
        toggleAll();

        moveToAll();
        toggle("4");
        clearCompleted();
        assertItemsLeft(1);
    }

    @Test
    public void testEditToEmpty(){

        add("1");
        assertVisible("1");
        startEdit("1", " ").sendKeys(Keys.ENTER);
        assertNoVisibleTasks();
        }

    @Test
    public void testCancelEdit(){

        add("1");
        assertVisible("1");
        startEdit("1", "Edited").sendKeys(Keys.ESCAPE);
        assertVisible("1");
        assertItemsLeft(1);
    }

    @Test
    public void testSaveByClickOutside(){

        add("1", "2");
        toggleAll();
        moveToCompleted();
        startEdit("2", "Renamed");
        tasks.find(exactText("1")).click();
        assertVisible("1", "Renamed");
        assertItemsLeft(0);

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

    @Step
    private void assertItemsLeft(int count){
        $("#todo-count strong").shouldHave(exactText(Integer.toString(count)));
    }
}