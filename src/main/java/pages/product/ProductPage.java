package pages.product;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;

import java.util.ArrayList;
import java.util.List;

public class ProductPage extends BasePage {
    private final By titleField = By.className("title");
    private final By allProductsLocator = By.cssSelector("button.btn_inventory");
    private final By allProductsCarts = By.cssSelector(".inventory_item");
    private final By shoppingCartBadge = By.className("shopping_cart_badge");
    private final By shoppingCartButton = By.className("shopping_cart_link");
    private final By dropdown = By.cssSelector("[data-test='product-sort-container']");

    public static List <String> productIds = new ArrayList<>();

    public ProductPage( WebDriver driver){
        super(driver);
    }

    public WebElement getTitle(){
        return findElement(titleField);
    }
    public WebElement getShoppingCartBadge(){
        return findElement(shoppingCartBadge);
    }
    public WebElement getShoppingCartButton(){return findElement(shoppingCartButton);}
    public WebElement getFilterButton(){return findElement(dropdown);}

    public List<WebElement> getAllButtons(){return findElements(allProductsLocator);}
    public List<WebElement> getAllProducts(){return findElements(allProductsCarts);}


    public List<String> addSelectedProducts(){
        String[] substrings = new String [] {"backpack","bike","t-shirt","jacket"};

        for (String sub: substrings){
            List<WebElement> elements = getAllButtons();
            for (WebElement el: elements){
                String id = el.getAttribute("id");
                if (id.toLowerCase().contains(sub)){
                    WebElement priceEl = el.findElement(By.xpath("preceding-sibling::div[@class='inventory_item_price']"));
                    String price = priceEl.getText();
                    System.out.println(price);
                    el.click();
                    productIds.add(id.replace("add-to-cart-",""));
                }
            }
        }
        return productIds;
    }

    public double price(){
        String[] substrings = new String [] {"backpack","bike","t-shirt","jacket"};

        double price = 0 ;
        for (String sub: substrings){
            List<WebElement> elements = getAllButtons();
            for (WebElement el: elements){
                String id = el.getAttribute("id");
                if (id.toLowerCase().contains(sub)){
                    WebElement priceEl = el.findElement(By.xpath("preceding-sibling::div[@class='inventory_item_price']"));
                    System.out.println(priceEl.getText());
                    price += parseDouble(priceEl);
                    el.click();
                }
            }
        }
        return price;
    }

    public void clickCartButton(){
        getShoppingCartButton().click();
    }

    public boolean checkRemoveDisplayedLogic(){
        List<WebElement> carts = getAllProducts();

        for (WebElement cart: carts)
            cart.findElement(By.tagName("button")).click();

        int range = carts.size();
        int idx = (int) (Math.random() * range);
        System.out.println(idx);
        carts.get(idx).findElement(By.tagName("a")).click();

        WebElement btn = findElement(By.id("remove"));

        return btn != null && btn.isDisplayed();
    }

    public boolean takeDecision(String str) {
        List<WebElement> carts = getAllProducts();
        List<String> titles = new ArrayList<>();
        List<Double> prices = new ArrayList<>();

        for (WebElement cart : carts){
            titles.add(cart.findElement(By.className("inventory_item_name")).getText());
            prices.add(parseDouble(cart.findElement(By.className("inventory_item_price"))));
        }

        switch (str) {
            case "<": {
                for (int i = 0; i < titles.size() - 1; i++) {
                    if (titles.get(i).compareTo(titles.get(i + 1)) > 0) return false;
                }
                return true;
            }
            case ">": {
                for (int i = 0; i < titles.size() - 1; i++)
                    if (titles.get(i).compareTo(titles.get(i + 1)) < 0) return false;
                return true;
            }
            case "n<": {
                for (int i = 0; i < prices.size() - 1; i++)
                    if (prices.get(i) > prices.get(i + 1)) return false;
                return true;
            }
            case "n>": {
                for (int i = 0; i < prices.size() - 1; i++) {
                    if (prices.get(i) < prices.get(i + 1)) return false;
                }
                return true;
            }
            default: return false;
        }
    }

    public boolean checkFilterAToZ(){
        return takeDecision("<");
    }
    public boolean checkFilterZToA(){
        return takeDecision(">");
    }
    public boolean checkFilterLowToHighPrice(){
        return takeDecision("n<");
    }

    public boolean checkFilterHighToLowPrice(){
        return takeDecision("n>");
    }

    public boolean checkFilter(int idx){
        return switch (idx) {
            case 0 -> checkFilterAToZ();
            case 1 -> checkFilterZToA();
            case 2 -> checkFilterLowToHighPrice();
            case 3 -> checkFilterHighToLowPrice();
            default -> throw new IllegalStateException("Unexpected value: " + idx);
        };
    }

    public boolean cartIconLogic(){
        List<WebElement> buttons = getAllButtons();

        buttons.get(0).click();
        buttons.get(1).click();

        WebElement cartContainer = driver.findElement(By.className("shopping_cart_container"));
        for (int i = 0 ; i < 2 ; i++){
            System.out.println(cartContainer.findElement(By.tagName("a")).getAttribute("aria-label"));
            buttons = getAllButtons();
            buttons.get(i).click();
        }
        return cartContainer.findElement(By.tagName("a")).getAttribute("aria-label").contains("empty");
    }

}
