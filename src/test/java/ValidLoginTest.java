import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;

public class ValidLoginTest extends DataProvider{
    ChromeDriver driver;

    @BeforeTest
    public void setUp(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @Test(dataProvider = "validCredentials", dataProviderClass = DataProvider.class)
    public void validLogin(String username, String password){
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();
        Assert.assertEquals(driver.findElement(By.className("title")).getText(),"Products");
    }

    @Test(dataProvider = "validCredentials", dataProviderClass = DataProvider.class)
    public List<String> addToCart(String username, String password) {
        validLogin(username, password);

        String[] substrings = new String [] {"backpack","bike","t-shirt","jacket"};

        SoftAssert softAssert = new SoftAssert();

        List<String> addedItemsIds = new ArrayList<>();

        int cnt = 0 ;
        for (String sub: substrings){
            List<WebElement> elements = driver.findElements(By.cssSelector("button.btn_inventory"));
            for (WebElement el: elements){
                String id = el.getAttribute("id");
                if (id.toLowerCase().contains(sub)){
                    cnt++;
                    el.click();
                    addedItemsIds.add(id.replace("add-to-cart-",""));
                }
            }
        }

        int nItems = Integer.parseInt(driver.findElement(By.className("shopping_cart_badge")).getText());
        softAssert.assertTrue(cnt == nItems);
        softAssert.assertAll();

        driver.findElement(By.className("shopping_cart_link")).click();
        Assert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/cart.html");

        return addedItemsIds;

    }

    @Test(dataProvider = "validCredentials", dataProviderClass = DataProvider.class)
    public void checkout(String username, String password) {
        List<String> addedItems = addToCart(username, password);
        int count = 0;
        for (String id: addedItems){
            for (WebElement checkItem: driver.findElements(By.xpath("//*[@class='btn btn_secondary btn_small cart_button']"))){
                if(id.equals(checkItem.getAttribute("id").replace("remove-",""))){
                    count++;
                    break;
                }
            }
        }

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(count==addedItems.size());

        driver.findElement(By.id("checkout")).click();

        softAssert.assertTrue(driver.getCurrentUrl().equals("https://www.saucedemo.com/checkout-step-one.html"));
        softAssert.assertAll();
    }

    @Test(dataProvider = "validCredentials", dataProviderClass = DataProvider.class)
    public void checkoutInfo(String username, String password){
        checkout(username, password);

        driver.findElement(By.id("first-name")).sendKeys("Ibrahim");
        driver.findElement(By.id("last-name")).sendKeys("Kaldish");
        driver.findElement(By.id("postal-code")).sendKeys("13612");

        driver.findElement(By.id("continue")).click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-two.html");

    }

    @Test(dataProvider = "validCredentials", dataProviderClass = DataProvider.class)
    public void overview(String username, String password) {
        checkoutInfo(username, password);

        driver.findElement(By.id("finish")).click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-complete.html");
    }

    @Test(dataProvider = "validCredentials", dataProviderClass = DataProvider.class)
    public void generatePDF(String username, String password) {
        overview(username, password);
        driver.findElement(By.id("generate-pdf-order")).click();

    }

    @AfterTest
    public void tearDown() throws InterruptedException{
        Thread.sleep(3000);
        driver.quit();
    }
}
