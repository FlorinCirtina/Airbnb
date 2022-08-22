package test.homepageTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import test.BaseTest;

public class homepageTest extends BaseTest {
    private static final Logger LOG = LoggerFactory.getLogger(homepageTest.class);

    @Test
    public void performSearch() {
        String location = "Spain";

        LOG.info("Verify that the results match the selected criteria");
        homePage.clickAnywhereButton();
        homePage.setWhereToGo(location);
        homePage.selectLocation(location);
        homePage.clickGivenDay(homePage.getCurrentDay());
        Assert.assertTrue(homePage.checkSelectedCheckIn(), "The checkIn date is not selected");
        homePage.clickGivenDay(homePage.getCurrentDayPlus(4));
        Assert.assertTrue(homePage.checkSelectedCheckOut(), "The checkOut date is not selected");
        Assert.assertTrue(homePage.verifyPastDateIsDisabled(), "The previous day is not disabled");

        LOG.info("Verify that the results match the search criteria");
        homePage.clickImFlexible();
        homePage.clickWeekendOption();
        homePage.clickChooseDate();
        homePage.clickSearch();
        homePage.hover();
        Assert.assertTrue(homePage.checkHighlightedPrice(), "The price is not highlighted");

        LOG.info("Verify that the property displayed on the map matches some characteristics");
        homePage.clickHighlightedPrice();
        String cardName = homePage.getCardName();
        String mapCardName = homePage.getMapCardName();
        Assert.assertEquals(cardName, mapCardName, "The cardName: {} is not the same as map cardName: {}");
        String cardPrice = homePage.getCardPrice();
        String mapPrice = homePage.getMapPrice();
        Assert.assertEquals(cardPrice, mapPrice, "The card price: {} is not the same as map price: {}");
        String cardRatting = homePage.getCardRatting();
        String mapCardRatting = homePage.getMapCardRatting();
        Assert.assertEquals(cardRatting, mapCardRatting, "The cardRatting: {} is not the same as map mapCardRatting: {}");
        homePage.clickMapCardClose();

        LOG.info("Verify that the selected filters number is reflected on the listing and the results are\n" +
                "matching the filtered criteria");
        String filterNumber = homePage.getFilterButtonNumber();
        homePage.clickFilterButton();
        homePage.selectOption("Entire place");
        homePage.selectOption("Japanese");
        homePage.clickShowButton();
        int totalNumber = Integer.parseInt(homePage.RegexExamples(homePage.getTotalNumber(), "[0-9]{2}"));
        int item = homePage.listOfElement(totalNumber);
        Assert.assertEquals(item, totalNumber, "Number of list is not the same with the button number displayed");
        String currentFilterNumber = homePage.getFilterButtonNumber();
        Assert.assertNotSame(currentFilterNumber, filterNumber, "The filter number is the same");
    }
}
