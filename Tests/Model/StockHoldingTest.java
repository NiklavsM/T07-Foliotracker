package Model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StockHoldingTest {

    private IStockHolding stockholding;
    private Double amount;
    private Double sharevalue;
    private String tickerSymbol;
    private Double noOfshares;
    private String sharename;



    @Before
    public void setUp() {
        amount = 5000.00;
        sharevalue = 4000.00;
        tickerSymbol = "MSFT";
        noOfshares = 20.00;
        sharename = "share";
        stockholding = new StockHolding(tickerSymbol,sharename, noOfshares, sharevalue);

    }

    @Test
    public void buyShares() {
        assertTrue(amount > 0);
        stockholding.buyShares(amount);

    }

    @Test
    public void setShareValue()  {
        assertTrue(sharevalue > 0);
        stockholding.setShareValue(sharevalue);
    }

//    @Test
//    public void updateValueOfHolding() {
//    }

    @Test
    public void sellShares() {
        assertTrue(amount > 0);
        stockholding.sellShares(amount);
    }

    @Test
    public void getTickerSymbol() {
        assertEquals("MSFT", stockholding.getTickerSymbol());
    }

    @Test
    public void getNumberOfShares() {
        assertEquals(noOfshares, stockholding.getNumberOfShares());
    }

    @Test
    public void getShareValue() {
        assertEquals(sharevalue, stockholding.getShareValue());
    }

//    @Test
//    public void getValueOfHolding() {
//
//    }

}