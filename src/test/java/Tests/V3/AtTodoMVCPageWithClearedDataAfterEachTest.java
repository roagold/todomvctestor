package Tests.V3;


import org.junit.After;
import org.junit.Before;

import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.open;

/**
 * Created by oryabinskiy on 11/3/2015.
 */
public class AtTodoMVCPageWithClearedDataAfterEachTest  extends Tests.V3.BaseTest {
    @Before
    public void openPage(){
        open("https://todomvc4tasj.herokuapp.com/");
    }

    @After
    public void clearData(){
        executeJavaScript("localStorage.clear()");

    }
}

