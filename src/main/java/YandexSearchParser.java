import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class YandexSearchParser {
    String baseUrl = "https://yandex.ru";
    ChromeDriver driver;
    WebDriverWait wait;
    String answerTemplate = "First suggestion for query \"%s\": %s";
    String queriesFilePath = "src/main/resources/queries";

    public YandexSearchParser() {
        this.driver = new ChromeDriver();
        setOutEncoding();
    }


    public void setOutEncoding() {
        String osType = System.getProperty("os.name");
        String encoding = "";
        if (osType.startsWith("Windows")) {
            encoding = "CP866";
        }
        else {
            encoding = "UTF-8";
        }
        try {
            System.setOut(new PrintStream( System.out, true, encoding));
        } catch (UnsupportedEncodingException e) {
            System.out.println(
                    String.format("Unsupported Encoding\nError occured: %s", e.toString())
                    );
        }
    }

    public String getFirstSuggestionBySearchQuery(Page page, String query) {
        page.fillSearchLine(query);
        WebElement suggestionsTable = page.getSuggestionsTable();
        ArrayList<String> suggestions = page.extractSuggests(suggestionsTable);
        return (suggestions.get(0));
    }

    public void getTaskAnswer() {
        Page yandexMain = new Page(this.driver, baseUrl);
        try {
            List<String> queries = Files.readAllLines(Paths.get(queriesFilePath));
            for (String query : queries) {
                yandexMain.clearSearchLine();
                String firstSuggestion =
                        getFirstSuggestionBySearchQuery(yandexMain, query).replaceAll("\n", " ");
                System.out.println(String.format(answerTemplate, query, firstSuggestion));
            }
        }
        catch (Exception e) {
            System.out.println(String.format("Error occured: %s", e.toString()));
        }
        finally {
            driver.quit();
        }
    }
}
