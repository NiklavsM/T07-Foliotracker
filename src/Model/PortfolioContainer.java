package Model;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class PortfolioContainer extends Observable implements IPortfolioContainer {
    private List<Portfolio> portfolioList = new ArrayList<>();
    private Map<String, Double> sharePrices;
    private Lock sharePriceLock = new ReentrantLock();

    public PortfolioContainer() {
        sharePrices = new Hashtable<>();
        Thread updater = new Thread(new SharePriceUpdaterThread(this, sharePrices));
        updater.start();
    }

    public List<Portfolio> getPortfolioList() {
        return new ArrayList<>(portfolioList);

    }

    /**
     * @requires: tickerSymbol != null 
     * @modifies: this
     * @effects: Sets the specified share price
     */
    public void setSharePrice(String tickerSymbol, Double value) {
        sharePriceLock.lock();
        try {
            sharePrices.put(tickerSymbol, value);
        } finally {
            sharePriceLock.unlock();
        }

    }

    /**
     * @requires: portfolio != null
     * @modifies: this
     * @effects: returns true if portfolio is added else return false.
     */
    public boolean addToPortfolioList(String portfolio) {
        Portfolio pf = getPortfolioBytName(portfolio);
        if (pf == null) {
            Portfolio newPortfolio1 = new Portfolio(portfolio, sharePrices);
            portfolioList.add(newPortfolio1);
            setChangedAndNotify();
            return true;
        }
        return false;
    }

    /**
     * @requires: name != null
     * @effects: returns portfolio if portfolio's Name == name else return null.
     */
    private Portfolio getPortfolioBytName(String name) {
        for (Portfolio portfolio : portfolioList) {
            if (portfolio.getName().equals(name)) {
                return portfolio;
            }

        }
        return null;
    }
    /**
     * @modifies: this
     * @effects: Updates all the portfolio share prices
     */
    public void updateShareValues() {
        for (Portfolio portfolio : portfolioList) {
            portfolio.updateShareValues();
        }
    }

    private void setChangedAndNotify() {
        setChanged();
        notifyObservers();
    }

    /**
     * @requires: name != null
     * @modifies: this
     * @effects: returns true if the portfolio is removed from the List else return false.
     */
    public boolean deletePortfolio(String name) {
        Portfolio portToDel = getPortfolioBytName(name);
        if (portToDel != null) {
            portfolioList.remove(portToDel);
            setChangedAndNotify();
            return true;
        }
        return false;
    }

    /**
     * @requires: name != null
     * @effects: returns true if the List contains this portfolio else return false.
     */
    public boolean containsPortfolio(String name) {
        return getPortfolioBytName(name) != null;
    }

    /**
     * @effects: returns the name of each portfolio in the portfolioList.
     */
    public String[] getPortfolioNames() {
        String[] portfolioNames = new String[portfolioList.size()];
        int i = 0;
        for (Portfolio portfolio : portfolioList) {
            portfolioNames[i] = portfolio.getName();
            i++;
        }
        return portfolioNames;
    }

}
