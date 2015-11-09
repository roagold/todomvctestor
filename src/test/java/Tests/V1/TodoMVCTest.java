package Tests.V1;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

/**
 * Created by oryabinskiy on 11/3/2015.
 */
public class TodoMVCTest {
    @Test
    public void testTasksLifeCycle(){
        open("http://todomvc.com/examples/troopjs_require/");

        add("1");
        toggle("1");

        moveToActive();
        assertNoVisibleTasks();

        add("2");
        startEdit("2", "Renamed").sendKeys(Keys.ENTER);
        assertVisible("Renamed");

        toggleAll();
        assertNoVisibleTasks();

        moveToCompleted();
        assertVisible("1", "Renamed");

        toggle("1");
        clearCompleted();
        assertNoVisibleTasks();

        moveToAll();

        delete("1");
        assertNoVisibleTasks();

    }

    public ElementsCollection tasks = $$("#todo-list li");

    public void moveToAll(){
        $("a[href='#/']").click();
    }

    public void moveToActive(){
        $("a[href='#/active']").click();
    }

    public void moveToCompleted(){
        $("a[href='#/completed']").click();
    }

    public void toggleAll(){
        $("#toggle-all").click();
    }

    public void add(String name) {
        $("#new-todo").setValue(name).pressEnter();
    }

    public void delete(String name){
        tasks.find(exactText(name)).hover().find(".destroy").click();
    }

    public void toggle(String name){
        tasks.find(exactText(name)).find(".toggle").click();;
    }

    public void clearCompleted(){
        $("#clear-completed").click();
    }

    public SelenideElement startEdit(String name, String newName) {
        tasks.find(exactText(name)).find("label").doubleClick();
        return tasks.find(cssClass("editing")).find(".edit").setValue(newName);
    }

    public void assertVisible(String... names){
        tasks.filter(visible).shouldHave(exactTexts(names));
    }

    public void assertNoVisibleTasks() {
        tasks.filter(visible).shouldBe(empty);
    }
}