package Model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.io.IOException;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({StrathQuoteServer.class})
public class PortfolioContainerTest {

    private IPortfolioContainer portContainer;
    private String tickerSymbol;
    private String shareName;
    private Double value;
    private Portfolio portfolio;


    @Before

    public void setUp() throws WebsiteDataException {
        mockStatic(StrathQuoteServer.class);
        when(StrathQuoteServer.getLastValue("MSFT")).thenReturn("70.00");
        portContainer = new PortfolioContainer();
        tickerSymbol = "MSFT";
        shareName = "share name";

    }


    //Tests adding portfolio
    @Test
    public void addPortfolio() {
        portContainer.addPortfolio("Portfolio1");
        assertTrue(portContainer.containsPortfolio("Portfolio1"));
    }

    //Adding portfolio that exists
    @Test
    public void addPortfolioThatExists() {
        portContainer.addPortfolio("Portfolio1");
        assertFalse(portContainer.addPortfolio("Portfolio1"));
    }

    //Tests whether stock prices updates when stocks value changes
    @Test
    public void updatePrices() throws WebsiteDataException, InterruptedException {
        portContainer.addPortfolio("Portfolio1");
        portContainer.getPortfolios().get("Portfolio1").addStock(tickerSymbol, shareName);
        portContainer.getPortfolios().get("Portfolio1").buyStock(tickerSymbol, 100.0);
        mockStatic(StrathQuoteServer.class);
        when(StrathQuoteServer.getLastValue("MSFT")).thenReturn("80.00");
        sleep(10001);
        assertEquals(new Double(8000.0), portContainer.getPortfolios().get("Portfolio1").getTotalValue());
    }

    //Removing portfolio
    @Test
    public void removePortfolio() {
        portContainer.addPortfolio("Portfolio1");
        portContainer.deletePortfolio("Portfolio1");
        assertTrue(portContainer.getPortfolios().size() == 0);
    }

    //Removing portfolio that doesn't exist
    @Test
    public void removePortfolioNonExisting() {
        assertFalse(portContainer.deletePortfolio("Portfolio1"));
    }

    //Saving and loading to a file
    @Test
    public void saveLoadFile() throws IOException, ClassNotFoundException {
        File currentDirectory = new File("test");
        portContainer.addPortfolio("TestPortfolio");
        portContainer.saveToFile(currentDirectory.getAbsolutePath());
        portContainer.deletePortfolio("TestPortfolio");
        portContainer.loadFromFile(currentDirectory.getAbsolutePath() + ".bin");
        assertTrue(portContainer.containsPortfolio("TestPortfolio"));
    }

}