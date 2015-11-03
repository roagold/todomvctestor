package Tests.V2;

import com.codeborne.selenide.Screenshots;
import com.google.common.io.Files;
import org.junit.After;
import ru.yandex.qatools.allure.annotations.Attachment;

import java.io.File;
import java.io.IOException;

/**
 * Created by oryabinskiy on 10/28/2015.
 */
public class BaseTest {
            @After
        public void postScreenschot() throws IOException {
            screenshot();
        }

        @Attachment(type = "image/png")
        public byte[] screenshot() throws IOException {
            File screenshot = Screenshots.getScreenShotAsFile();
            return Files.toByteArray(screenshot);
        }
    }

