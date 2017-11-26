package Model;

import java.io.*;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;


public class PortfolioContainer extends Observable implements IPortfolioContainer, Serializable {
    private Map<String, Portfolio> portfolioList = new ConcurrentHashMap<>();
    private Map<String, Double> sharePrices = new ConcurrentHashMap<>(); //maybe add when load

    public PortfolioContainer() {
        Thread updater = new Thread(new SharePriceUpdaterThread(this, sharePrices));
        updater.start();
    }

    public Map<String, IPortfolio> getPortfolios() {
        return new ConcurrentHashMap<>(portfolioList);
    }

    private Map<String, Portfolio> getPortfolioMap() {
        return new ConcurrentHashMap<>(portfolioList);
    }


    //      requires: tickerSymbol != null && value != null
//      modifies: this
//      effects: Sets the specified share price
    public void setSharePrice(String tickerSymbol, Double value) {
        assert (tickerSymbol != null) : "The ticker symbol must not be null";
        assert (value != null) : "The value must not be null";

        sharePrices.put(tickerSymbol, value);

    }


    //      requires: portfolio != null
//      modifies: this
//      effects: returns true if portfolio is added else returns false if portfolio with this name already exists
    public boolean addPortfolio(String portfolio) {

        assert (portfolio != null) : "Portfolio must not be null";
        if (portfolioList.get(portfolio) == null) {
            Portfolio newPortfolio1 = new Portfolio(portfolio, sharePrices);
            portfolioList.put(portfolio, newPortfolio1);
            setChangedAndNotify();
            return true;
        }
        return false;
    }

    //      modifies: this
//      effects: Updates all portfolios share prices.
    void updateShareValues() {
        for (String key : portfolioList.keySet()) {
            portfolioList.get(key).updateShareValues();
        }
    }

    public boolean loadFromFile(String filePath) throws IOException, ClassNotFoundException {
        System.out.println(filePath);

        ObjectInputStream newStateO = new ObjectInputStream(new FileInputStream(filePath));
        portfolioList = ((PortfolioContainer) newStateO.readObject()).getPortfolioMap();
        newStateO.close();
        setChangedAndNotify();
        return true;


    }

    public void saveToFile(String filePath) throws IOException {
        System.out.println(filePath);

        ObjectOutputStream stateObj = new ObjectOutputStream(
                new FileOutputStream(filePath + ".bin"));
        stateObj.writeObject(this);
        stateObj.close();

    }


    private void setChangedAndNotify() {
        setChanged();
        notifyObservers();
    }


    //      requires: name != null
//      modifies: this
//      effects: returns true if the portfolio is removed from the List else return false.
    public boolean deletePortfolio(String name) {

        assert (name != null) : "The name must not be null";
        if (portfolioList.containsKey(name)) {
            portfolioList.remove(name);
            setChangedAndNotify();
            return true;
        }
        return false;
    }

    //      requires: name != null
//      effects: returns true if the List contains this portfolio else return false.
    public boolean containsPortfolio(String name) {
        assert (name != null) : "The name must not contain an null value";
        return portfolioList.containsKey(name);
    }


}
