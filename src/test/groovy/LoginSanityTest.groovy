import com.mikewarren.ezsoftwaresolutionsinterview.constants.Constants
import com.mikewarren.ezsoftwaresolutionsinterview.pages.LoginPage
import com.mikewarren.ezsoftwaresolutionsinterview.utils.EncryptDecryptHelper
import com.mikewarren.ezsoftwaresolutionsinterview.utils.WebDriverUtils
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait

class LoginSanityTest {
    @Test
    void shouldLoginSuccessfully() {
        LoginPage page = new LoginPage();
        page.go();

        page.login("mwarren04011990@gmail.com", EncryptDecryptHelper.GetInstance().decrypt("x9tGVUkIneposoDbEKMbZQ=="));

        assert new WebDriverWait(WebDriverUtils.GetWebDriver(), Constants.PageLoadDuration)
            .until({ WebDriver driver ->
                return driver.getCurrentUrl() != page.getUrl();
            })
    }

    @AfterAll
    static void close() {
        WebDriverUtils.CloseWebDriver();
    }
}
