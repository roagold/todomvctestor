package oleg.ryabinskiy;

import org.junit.After;
import org.junit.Before;

import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.open;

/**
 * Created by oryabinskiy on 10/28/2015.
 */
public class AtTodoMVCPageWithClearedDataAfterEachTest extends BaseTest {
    @Before
    public void OpenPage(){
        open("http://todomvc.com/examples/troopjs_require/");
    }

    @After
    public void clearData(){
        executeJavaScript("localStorage.clear()");

    }
}
