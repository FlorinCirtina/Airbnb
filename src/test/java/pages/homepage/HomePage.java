package pages.homepage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.BasePage;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HomePage extends BasePage {
    private static final Logger LOG = LoggerFactory.getLogger(HomePage.class);
    public static HomePage instance;

    private final By anywhereButton = By.xpath("//div[contains(text(), 'Anywhere')]//parent::button");
    private final By inputField = By.xpath("//div[contains(text(), 'Where')]/following-sibling::input");
    String selectWhere = "//div[text()='%s']";
    private final By selectedCheckIn = By.xpath("//td[contains(@aria-label, 'Selected check-in date')]");
    private final By selectedCheckOut = By.xpath("//td[contains(@aria-label, 'Selected checkout date')]");
    static String past = "//div[@data-visible='true']//td[contains(@aria-label, '%s,' )][@tabindex='-1'][@aria-disabled='true']";

    private final By flexibleButton = By.xpath("//button[text()= \"I'm flexible\"]");
    private final By chooseDateButton = By.xpath("//button[text()= 'Choose dates']");
    private final By weekendOption = By.id("flexible_trip_lengths-weekend_trip");
    private final By searchButton = By.xpath("//button[@data-testid='structured-search-input-search-button']");
    private final By picture = By.xpath("//picture/img[1]");
    private final By priceHighlighted = By.xpath("//span[text()='selected']");
    private final By getPriceHighlighted = By.xpath("//span[text()='selected']//parent::div");

    private final By getCardName = By.xpath("//div[contains(@id, 'title')][1]");
    private final By getMapCardName = By.xpath("//div[@data-testid='map/GoogleMap']//div[contains(@id, 'title')]");
    private final By getCardPrice = By.xpath("//div[@itemprop='itemListElement']//div/span//span/following-sibling::span[1]");
    private final By getMapPrice = By.xpath("//div[@data-testid='map/GoogleMap']//div[contains(@style, '--pricing')]//span/following-sibling::span[1]");
    private final By getRatting = By.xpath("//div[@itemprop='itemListElement']//div/span/span/following-sibling::span");
    private final By getMapRatting = By.xpath("//div[@data-testid='map/GoogleMap']//div/div/span/span/following-sibling::span");
    private final By closeMapCard = By.xpath("//button[@aria-label='Close']");

    private final By filters = By.xpath("//span[text()= 'Filters']");
    String selectOption = "//input[@name='%s']";
    private final By showButton = By.xpath("//a[contains(text(), 'Show')]");

    private final By cardsList = By.xpath("//div[@itemprop='itemListElement']");
    private final By totalNumber = By.xpath("//section/h1/span");
    private final By nextPage = By.xpath("//a[contains(@aria-label, 'Next')]");
    private final By filterChanged = By.xpath("//div[@data-section-id=\"EXPLORE_STRUCTURED_PAGE_TITLE\"]//div/button/following-sibling::div/div");

    private HomePage() {
    }

    public static HomePage getInstance() {
        if (instance == null) {
            instance = new HomePage();
        }
        return instance;
    }

    public static void waitForElementDisplayed(By by) {
        LOG.debug("Waiting for element to be displayed:" + by);
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public void clickAnywhereButton() {
        LOG.info("Clicking the Anywhere button");
        waitForElementDisplayed(anywhereButton);
        driver.findElement(anywhereButton).click();
        sleep(5 * 1000L);
    }

    public void setWhereToGo(String where) {
        LOG.info("Insert text: {}, in Input Field.", where);
        driver.findElement(inputField).sendKeys(where);
        sleep(5 * 1000L);
    }

    public void selectLocation(String loction) {
        LOG.info("Select location {}, from dropdown", loction);
        driver.findElement(By.xpath(String.format(selectWhere, loction))).click();
        sleep(5 * 1000L);
    }

    public static String getCurrentDay() {
        LOG.info("Return the current day");
        //Create a Calendar Object
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        //Get Current Day as a number
        int todayInt = calendar.get(Calendar.DAY_OF_MONTH);
        //Integer to String Conversion
        String todayStr = Integer.toString(todayInt);
        return todayStr;
    }

    public static String getCurrentDayPlus(int days) {
        LOG.info("Return the current day plus: {} days", days);
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int todayInt = calendar.get(Calendar.DAY_OF_MONTH);
        int dayPlus = todayInt + days;
        String todayStr = Integer.toString(dayPlus);
        return todayStr;
    }

    public static String getCurrentDayMinus() {
        LOG.info("Return the current day minus 1 day");
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int todayInt = calendar.get(Calendar.DAY_OF_MONTH);
        int dayMinus = todayInt - 1;
        String todayStr = Integer.toString(dayMinus);
        return todayStr;
    }

    public static void clickGivenDay(String day) {
        LOG.info("Select the day: {}", day);
        //DatePicker is a table. Thus we can navigate to each cell
        //and if a cell matches with the current date then we will click it.
        List<WebElement> columns = driver.findElements(By.tagName("td"));
        for (WebElement cell : columns) {
            String cellText = cell.getText();
            if (cellText.contains(day)) {
                cell.click();
                break;
            }
        }
    }

    public boolean verifyPastDateIsDisabled() {
        LOG.info("Verify if previous date is disabled");
        return driver.findElement(By.xpath(String.format(past, getCurrentDayMinus()))).isDisplayed();
    }

    public boolean checkSelectedCheckIn() {
        LOG.info("Check if Interval is selected");
        return driver.findElement(selectedCheckIn).isDisplayed();
    }

    public boolean checkSelectedCheckOut() {
        LOG.info("Check if Interval is selected");
        return driver.findElement(selectedCheckOut).isDisplayed();
    }

    public void clickChooseDate() {
        LOG.info("Clicking the ChooseDate button");
        waitForElementDisplayed(chooseDateButton);
        driver.findElement(chooseDateButton).click();
    }

    public void clickImFlexible() {
        LOG.info("Clicking the I'm flexible button");
        waitForElementDisplayed(flexibleButton);
        driver.findElement(flexibleButton).click();
    }

    public void clickWeekendOption() {
        LOG.info("Clicking the Weekend option");
        waitForElementDisplayed(weekendOption);
        driver.findElement(weekendOption).click();
    }

    public void clickSearch() {
        LOG.info("Clicking the Search button");
        waitForElementDisplayed(searchButton);
        driver.findElement(searchButton).click();
        sleep(5 * 1000L);
    }

    public void hover() {
        LOG.info("Hover the moue over element");
        WebElement ele = driver.findElement(picture);
        //Creating object of an Actions class
        Actions action = new Actions(driver);
        //Performing the mouse hover action on the target element.
        action.moveToElement(ele).perform();
        sleep(5 * 1000L);
    }

    public boolean checkHighlightedPrice() {
        LOG.info("Check if Price is highlighted");
        sleep(3 * 1000L);
        return driver.findElement(priceHighlighted).isDisplayed();
    }

    public void clickHighlightedPrice() {
        LOG.info("Click the highlighted price");
        waitForElementDisplayed(getPriceHighlighted);
        driver.findElement(getPriceHighlighted).click();
        sleep(5 * 1000L);
    }

    public String getCardName() {
        LOG.info("Retrieve the card Name");
        WebElement cardName = driver.findElement(getCardName);
        return cardName.getText();
    }

    public String getMapCardName() {
        LOG.info("Retrieve the map card Name");
        WebElement cardName = driver.findElement(getMapCardName);
        return cardName.getText();
    }

    public String getCardPrice() {
        LOG.info("Retrieve the card Price");
        WebElement cardName = driver.findElement(getCardPrice);
        return cardName.getText();
    }

    public String getMapPrice() {
        LOG.info("Retrieve the map Price");
        WebElement cardName = driver.findElement(getMapPrice);
        return cardName.getText();
    }

    public String getCardRatting() {
        LOG.info("Retrieve the card Ratting");
        WebElement cardName = driver.findElement(getRatting);
        return cardName.getText();
    }

    public String getMapCardRatting() {
        LOG.info("Retrieve the map card Ratting");
        WebElement cardName = driver.findElement(getMapRatting);
        return cardName.getText();
    }

    public void clickMapCardClose() {
        LOG.info("Clicking the Close button");
        waitForElementDisplayed(closeMapCard);
        driver.findElement(closeMapCard).click();
        sleep(5 * 1000L);
    }

    public void clickFilterButton() {
        LOG.info("Clicking the Filter button");
        waitForElementDisplayed(filters);
        driver.findElement(filters).click();
        sleep(5 * 1000L);
    }

    public boolean checkOptionIsDisplayed(String optionMame) {
        LOG.info("Check if Option is displayed");
        return driver.findElement(By.xpath(String.format(selectOption, optionMame))).isDisplayed();
    }

    public void selectOption(String optionMame) {
        LOG.info("Select: {}, option", optionMame);
        if (!checkOptionIsDisplayed(optionMame)) {
            WebElement element = driver.findElement(By.xpath(String.format(selectOption, optionMame)));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            driver.findElement(By.xpath(String.format(selectOption, optionMame))).click();
            sleep(3 * 1000L);
        }
    }

    public void clickShowButton() {
        LOG.info("Clicking the Show button");
        waitForElementDisplayed(showButton);
        driver.findElement(showButton).click();
        sleep(5 * 1000L);
    }

    public static String RegexExamples(String text, String regex) {
        LOG.info("Return the number from the String");
        Pattern p = Pattern.compile(regex, Pattern.MULTILINE);
        Matcher m = p.matcher(text);
        if (m.find()) {
            if (m.groupCount() >= 1) return m.group(1);
            return m.group();
        } else return null;
    }

    public String getTotalNumber() {
        LOG.info("Retrieve the Total Number");
        WebElement cardName = driver.findElement(totalNumber);
        return cardName.getText();
    }

    public int listOfElement(int sizeExpected) {
        LOG.info("Get the number of item from each page");
        List<WebElement> list = driver.findElements(cardsList);
        List<WebElement> list2;
        int total = 0;

        while (total < sizeExpected) {
            driver.findElement(nextPage).click();
            sleep(3 * 1000);
            list2 = driver.findElements(cardsList);
            total = list.size() + list2.size();
        }
        return total;
    }

    public String getFilterButtonNumber() {
        LOG.info("Retrieve the Filters button number");
        WebElement cardName = driver.findElement(filterChanged);
        return cardName.getText();
    }

}
