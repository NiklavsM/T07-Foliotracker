package Model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StockHoldingTest {

    private StockHolding stockholding;
    private StockHolding stockholdingSame;
    private StockHolding stockholdingSame2;
    private Double sharevalue;
    private String tickerSymbol;
    private Double shareAmount;
    private String sharename;


    @Before
    public void setUp() {
        sharevalue = 100.0;
        tickerSymbol = "MSFT";
        shareAmount = 0.0;
        sharename = "sharename";
        stockholding = new StockHolding(tickerSymbol, sharename, shareAmount, sharevalue);
        stockholdingSame = new StockHolding(tickerSymbol, sharename, shareAmount, sharevalue);
        stockholdingSame2 = new StockHolding(tickerSymbol, sharename, shareAmount, sharevalue);

    }

    //Test buying shares
    @Test
    public void buyShares() {
        stockholding.buyShares(10.0);
        assertEquals(new Double(1000.0), stockholding.getValueOfHolding());

    }

    //Test setting share value
    @Test
    public void setShareValue() {
        stockholding.buyShares(10.0);
        stockholding.setShareValue(50.0);
        assertTrue(stockholding.getValueOfHolding() == 500.0);
    }

    // Test sell all shares that stock holding has
    @Test
    public void sellShares() {
        stockholding.buyShares(40.0);
        stockholding.sellShares(40.0);
        assertEquals(new Double(0.0), stockholding.getValueOfHolding());
    }

    // Test sell more shares that stock holding has
    @Test
    public void sellMoreShares() {
        stockholding.buyShares(40.0);
        stockholding.sellShares(100.0);
        assertTrue(stockholding.getValueOfHolding() == 0.0);
    }

    //Test getting shares ticker symbol
    @Test
    public void getTickerSymbol() {
        assertEquals("MSFT", stockholding.getTickerSymbol());
    }

    //Test getting number of shares
    @Test
    public void getNumberOfShares() {
        stockholding.buyShares(100.0);
        assertTrue(stockholding.getNumberOfShares() == 100.0);
    }

    //Test getting share value
    @Test
    public void getShareValue() {
        assertEquals(sharevalue, stockholding.getShareValue());
    }

    //Tests getting shares name
    @Test
    public void getShareName() {
        assertEquals(sharename, stockholding.getShareName());
    }

    //Tests equals methods transitivity
    @Test
    public void equalsTransitive() throws Exception {
        assertTrue(stockholding.equals(stockholdingSame) && stockholdingSame.equals(stockholdingSame2) && stockholding.equals(stockholdingSame2));

    }

    //Tests if equals method is symmetric
    @Test
    public void equalsSymmetric() throws Exception {
        assertTrue(stockholding.equals(stockholdingSame) && stockholdingSame.equals(stockholding));

    }

    //Tests if equals method is consistent
    @Test
    public void equalsConsistent() throws Exception {
        assertTrue(stockholding.equals(stockholdingSame) && stockholding.equals(stockholdingSame));

    }

    //Tests if equals method is reflexive
    @Test
    public void equalsReflexive() throws Exception {
        assertTrue(stockholding.equals(stockholding));

    }

    //Tests if portfolio is not equal to null
    @Test
    public void testEqualsNull() throws Exception {
        assertFalse(stockholding.equals(null));
    }

    //Tests if portfolio is not equal to object that is not of class Portfolio
    @Test
    public void testNoEM() throws Exception {
        Object o = new Object();
        assertFalse(stockholding.equals(o));

    }

    //Tests if equal objects has the same hashcode
    @Test
    public void sameHashCode() throws Exception {
        assertTrue(stockholding.hashCode() == stockholdingSame.hashCode());

    }
}