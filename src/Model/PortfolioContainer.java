package Model;

import java.io.*;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;


public class PortfolioContainer extends Observable implements IPortfolioContainer {
    private Map<String, Portfolio> portfolioMap = new ConcurrentHashMap<>();
    private Map<String, Double> sharePrices = new ConcurrentHashMap<>();

    public PortfolioContainer() {
        Thread updater = new Thread(new SharePriceUpdaterThread(this, sharePrices));
        updater.start();
    }

    public Map<String, IPortfolio> getPortfolios() {
        return new ConcurrentHashMap<>(portfolioMap);
    }

    void setSharePrice(String tickerSymbol, Double value) {
        assert (tickerSymbol != null) : "The ticker symbol must not be null";
        assert (value != null) : "The value must not be null";

        sharePrices.put(tickerSymbol, value);

    }


    //      requires: portfolio != null
    //      modifies: this
    //      effects: returns true if portfolio is added else returns false if portfolio with this name already exists
    public boolean addPortfolio(String portfolio) {

        if (portfolioMap.get(portfolio) == null) {
            Portfolio newPortfolio1 = new Portfolio(portfolio, sharePrices);
            portfolioMap.put(portfolio, newPortfolio1);
            setChangedAndNotify();
            return true;
        }
        return false;
    }

    void updateShareValues() {
        for (String key : portfolioMap.keySet()) {
            portfolioMap.get(key).updateShareValues();
        }
    }

    //      requires: filePath != null
    //      modifies: this
    //      effects: sets portfolioMap to the portfolioMap loaded from the file, throws IOException and ClassNotFoundException if fails to load from the file

    public void loadFromFile(String filePath) throws IOException, ClassNotFoundException {
        ObjectInputStream newStateO = new ObjectInputStream(new FileInputStream(filePath));
        portfolioMap = (Map<String, Portfolio>) newStateO.readObject();
        newStateO.close();
        setChangedAndNotify();
    }

    //      requires: filePath != null
    //      modifies: this
    //      effects: saves portfolioMap to the specified file, throws IOException if fails to output stream
    public void saveToFile(String filePath) throws IOException {
        ObjectOutputStream stateObj = new ObjectOutputStream(
                new FileOutputStream(filePath + ".bin"));
        stateObj.writeObject(portfolioMap);
        stateObj.close();

    }


    private void setChangedAndNotify() {
        setChanged();
        notifyObservers();
    }


    //      requires: name != null
    //      modifies: this
    //      effects: returns true if the portfolio is removed from the portfolioMap else if portfolioMap didn't contain portfolio returns false.
    public boolean deletePortfolio(String name) {

        if (portfolioMap.containsKey(name)) {
            portfolioMap.remove(name);
            setChangedAndNotify();
            return true;
        }
        return false;
    }

    //      requires: name != null
    //      effects: returns true if the portfolioMap contains this portfolio else returns false.
    public boolean containsPortfolio(String name) {
        return portfolioMap.containsKey(name);
    }


}
