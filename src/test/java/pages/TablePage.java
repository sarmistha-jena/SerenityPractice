package pages;

import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.pages.components.HtmlTable;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@DefaultUrl("https://the-internet.herokuapp.com/tables")
public class TablePage extends PageObject {
    private static final Logger logger = LoggerFactory.getLogger(ProductPage.class);

    @FindBy(xpath = "//table[@id='table1']")
    WebElement table1;

    public boolean isTableVisible(){
        waitABit(3000);
        return table1.isDisplayed();
    }
    public void getSearchResults() {

        List<Map<Object, String>> tableRows =
                HtmlTable.withColumns("First Name","Last Name", "Email","Due")
                        .readRowsFrom(table1);
        tableRows.forEach(row-> System.out.println(row.values()));
        Predicate<Map<Object,String>> matcher = tableRows::contains;

        logger.info(String.valueOf(matcher.test(tableRows.get(0))));
        tableRows.forEach(product -> {
            if (matcher.test(product))
                logger.info(String.valueOf(product.values()));
        });
    }
    public void printTableContent(){
        List<Map<Object, String>> tableRows =HtmlTable.inTable(table1).getRows();
        logger.info(String.valueOf(tableRows.size()));
        for(Map<Object, String> i:tableRows){
           logger.info(String.valueOf(i.values()));
        }
    }


}
