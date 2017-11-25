package Model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PortfolioContainerTest {

    private IPortfolioContainer portContainer;
    private String tickerSymbol;
    private Double value;
    private String portfolio;


    @Before
    public void setUp() {
        portContainer = new PortfolioContainer();
        tickerSymbol = "MSFT";
        value = 4000.00;
        portfolio = "portfolio";
    }

//    @Test
//    public void getPortfolioList() {
//    }

    @Test
    public void setSharePrice() {
        assertNotNull(tickerSymbol);
        assertNotNull(value);
     // setSharePrice is not included in IPortfolioContainer
    }

    @Test
    public void addToPortfolioList() {
        assertTrue(portContainer.addPortfolio(portfolio));
    }

    @Test
    public void updateShareValues() {
    }

//    @Test
//    public void loadFromFile() {
//        assertTrue(portContainer.loadFromFile());
//    }

//    @Test
//    public void saveToFile() {
//        portContainer.saveToFile();
//    }

    @Test
    public void deletePortfolioNonExisting() {
        assertFalse(portContainer.deletePortfolio(portfolio));

    }

    @Test
    public void containsPortfolio() {
        assertFalse(portContainer.containsPortfolio(portfolio));
    }

//    @Test
//    public void getPortfolioNames() {
//
//    }

}