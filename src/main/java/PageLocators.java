import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class PageLocators {
    public static By searchLine =
            By.cssSelector("#text");
    public static By suggestTable =
            By.cssSelector(".suggest2 .suggest2__content");
    public static By suggestElement =
            By.cssSelector(".suggest2 .suggest2__content .suggest2-item");
}
