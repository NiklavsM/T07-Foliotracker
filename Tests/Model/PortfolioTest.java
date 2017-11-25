package Model;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class PortfolioTest {
    private Map<String, Double> sharePrices;
    private String tickerSymbol;
    private String sharename;
    private Double amount;
    private Double shareamount;
    private IPortfolio portFolio;

    @Before
    public void setUp() {
        sharePrices = new HashMap<String,Double>();
        tickerSymbol = "MSFT";
        sharename = "share";
        amount = 5000.00;
        shareamount = 6000.00;
        portFolio = new Portfolio("portfolio1",sharePrices);
    }

    @Test
    public void sellStock() {
        assertFalse(portFolio.sellStock(tickerSymbol,amount));
    }

//    @Test
//    public void updateShareValues() {
//    }

    @Test
    public void getTotalValue() {
        assertTrue(portFolio.getTotalValue() >= 0.0);
    }

//    @Test
//    public void notifyChanges() {
//    }

    @Test
    public void addStock() throws WebsiteDataException{
        assertTrue(portFolio.addStock(tickerSymbol,sharename));
    }

    @Test(expected = WebsiteDataException.class)
    public void addStockNonExistingStock() throws WebsiteDataException{
        portFolio.addStock("fakestockabc",sharename);
    }

    @Test
    public void buyStockNotOwned() {
        assertFalse(portFolio.buyStock(tickerSymbol, shareamount));
    }

    @Test
    public void buyStock() throws WebsiteDataException {
        portFolio.addStock(tickerSymbol,sharename);
        assertTrue(portFolio.buyStock(tickerSymbol, shareamount));
    }

//    @Test
//    public void getStocks()  {
//    }

    @Test
    public void getName()  {
        assertEquals("portfolio1", portFolio.getName());
    }

}