import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class Page {
    ChromeDriver driver;
    String url;
    WebDriverWait wait;

    public Page(ChromeDriver driver, String url) {
        this.url = url;
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
        open(url);
    }

    public WebElement find(By locator) {
        try {
            WebElement element =
                    wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return element;
        }
        catch (Exception e) {
            System.out.println(String.format(
                    "Element not found: %s\nError occured: %s",
                    locator.toString(),
                    e.toString())
                    );
            driver.quit();
            return null;
        }
    }

    public void open(String url) {
        driver.get(url);
    }

    public void fillSearchLine(String query) {
        WebElement searchLine = find(PageLocators.searchLine);
        searchLine.sendKeys(query);
    }

    public void clearSearchLine() {
        WebElement searchLine = find(PageLocators.searchLine);
        searchLine.clear();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(PageLocators.suggestTable));
    }

    public WebElement getSuggestionsTable() {
        WebElement table = find(PageLocators.suggestTable);
        return table;
    }

    public ArrayList<String> extractSuggests(WebElement suggestsTable) {
        try {
            List<WebElement> suggests =
                    suggestsTable.findElements(PageLocators.suggestElement);
            ArrayList<String> suggestsText = new ArrayList<>();
            for (WebElement suggest : suggests) {
                suggestsText.add(suggest.getText());
            }
            return suggestsText;
        }
        catch (Exception e) {
            driver.quit();
            return null;
        }
    }
}
