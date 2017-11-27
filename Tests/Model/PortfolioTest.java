package Model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({StrathQuoteServer.class})
public class PortfolioTest {
    private String tickerSymbol;
    private String sharename;
    private Double shareAmount;
    private Portfolio portFolio;
    private Portfolio portFolioSame;
    private Portfolio portFolioSame2;

    @Before
    public void setUp() throws WebsiteDataException {
        mockStatic(StrathQuoteServer.class);
        when(StrathQuoteServer.getLastValue("MSFT")).thenReturn("50.00");
        Map<String, Double> sharePrices = new HashMap<>();
        tickerSymbol = "MSFT";
        sharename = "share";
        shareAmount = 10.0;
        portFolio = new Portfolio("portfolio1", sharePrices);
        portFolioSame = new Portfolio("portfolio1", sharePrices);
        portFolioSame2 = new Portfolio("portfolio1", sharePrices);
    }

    //Tests selling stock that is not owned
    @Test
    public void sellStockNonExisting() {
        assertFalse(portFolio.sellStock(tickerSymbol, shareAmount));
    }

    //Tests selling stock that is owned
    @Test
    public void sellStock() throws WebsiteDataException {
        portFolio.addStock(tickerSymbol, sharename);
        portFolio.buyStock(tickerSymbol, 100.0);
        portFolio.sellStock(tickerSymbol, 90.0);
        assertTrue(portFolio.getTotalValue() == 500.0);
    }

    //Tests deletion of share if the value is less than 0
    @Test
    public void deleteStock() throws WebsiteDataException {

        portFolio.addStock(tickerSymbol, sharename);
        portFolio.sellStock(tickerSymbol, 100.0);
        assertTrue(portFolio.getStocks().size() == 0);
    }

    //Add stock that is already in the portfolio
    @Test
    public void addStockThatExists() throws WebsiteDataException {

        portFolio.addStock(tickerSymbol, sharename);
        assertFalse(portFolio.addStock(tickerSymbol, sharename));
    }

    @Test
    public void getTotalValue() throws WebsiteDataException {
        portFolio.addStock(tickerSymbol, sharename);
        portFolio.buyStock(tickerSymbol, 100.0);
        assertTrue(portFolio.getTotalValue() == 5000.0);
    }

    @Test
    public void addStock() throws WebsiteDataException {

        assertTrue(portFolio.addStock(tickerSymbol, sharename));
    }

    //Try to buy stock that is not in portfolio
    @Test
    public void buyStockNotOwned() {
        assertFalse(portFolio.buyStock(tickerSymbol, 100.0));
    }

    // Tests get name
    @Test
    public void getName() {
        assertEquals("portfolio1", portFolio.getName());
    }
    //Tests equals methods transitivity
    @Test
    public void equalsTransitive() throws Exception {
        assertTrue(portFolio.equals(portFolioSame) && portFolioSame.equals(portFolioSame2) && portFolio.equals(portFolioSame2));

    }
    //Tests if equals method is symmetric
    @Test
    public void equalsSymmetric() throws Exception {
        assertTrue(portFolio.equals(portFolioSame) && portFolioSame.equals(portFolio));

    }
    //Tests if equals method is consistent
    @Test
    public void equalsConsistent() throws Exception {
        assertTrue(portFolio.equals(portFolioSame) && portFolio.equals(portFolioSame));

    }
    //Tests if equals method is reflexive
    @Test
    public void equalsReflexive() throws Exception {
        assertTrue(portFolio.equals(portFolio));

    }
    //Tests if portfolio is not equal to null
    @Test
    public void testEqualsNull() throws Exception {
        assertFalse(portFolio.equals(null));
    }
    //Tests if portfolio is not equal to object that is not of class Portfolio
    @Test
    public void testNoEM() throws Exception {
        Object o = new Object();
        assertFalse(portFolio.equals(o));

    }

    //Tests if equal objects has the same hashcode
    @Test
    public void sameHashCode() throws Exception {
        assertTrue(portFolio.hashCode() == portFolioSame.hashCode());

    }

}