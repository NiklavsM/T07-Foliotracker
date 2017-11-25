package Model;

import java.io.Serializable;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;

public class Portfolio extends Observable implements IPortfolio, Serializable {

    private Map<String, StockHolding> stocks = new ConcurrentHashMap<>();
    private String name;
    private Map<String, Double> sharePrices;

    public Portfolio(String name, Map<String, Double> sharePrices) {
        this.name = name;
        this.sharePrices = sharePrices;
    }

    //    requires: tickerSymbol != null && amount > 0
//    modifies: this
//    effects: returns true if the stock is sold using
//             the amount else if stock is not in portfolio returns false.
    public boolean sellStock(String tickerSymbol, Double amount) {

        assert (amount > 0) : "The amount of" + "" + amount + "" + " is negative, it must be a positive value";
        assert (tickerSymbol != null) : "The ticker symbol is null, it must contain a value";

        StockHolding stockHolding = stocks.get(tickerSymbol);
        if (stockHolding != null) {
            stockHolding.sellShares(amount);
            if (stockHolding.getNumberOfShares() <= 0) {
                removeStock(tickerSymbol);
            }
            notifyChanges();
            return true;
        }
        return false;
    }


    //    effects: updates the share values.

    public void updateShareValues() {
        for (String tickerSymbol : stocks.keySet()) {
            stocks.get(tickerSymbol).setShareValue(sharePrices.get(tickerSymbol));
        }
        notifyChanges();
    }


    //    effects: returns the total value of the stock.
    @Override
    public Double getTotalValue() {
        Double totalValue = 0.0;
        for (String tickerSymbol : stocks.keySet()) {
            totalValue += stocks.get(tickerSymbol).getValueOfHolding();
        }
        assert (totalValue >= 0.0) : "The value" + "" + totalValue + "" + "is negative, the total value of the stock must be 0 or greater";
        return totalValue;
    }

    public void notifyChanges() {
        setChanged();
        notifyObservers();
    }

    //     requires: tickerSymbol != null
//     effects: removes stock from the List by using the tickerSymbol,
//              returns true if stock was removed else returns false.
    private boolean removeStock(String tickerSymbol) {
        assert (tickerSymbol != null) : "The ticker symbol must not be null";
        if (!stocks.containsKey(tickerSymbol)) {
            stocks.remove(tickerSymbol);
            return true;
        } else {
            return false;
        }
    }

    //     requires: tickerSymbol != null && shareName != null
    //     effects: adds stock from the List by using the tickerName and the shareName
    //              returns true if the stock was added else false

    public boolean addStock(String tickerName, String shareName) throws WebsiteDataException {
        assert (tickerName != null) : "The ticker name must not be null";
        assert (shareName != null) : "The share name must not be null";
        if (stocks.containsKey(tickerName)) {
            return false;
        } else {
            if (!sharePrices.containsKey(tickerName)) {
                sharePrices.put(tickerName, Double.valueOf(StrathQuoteServer.getLastValue(tickerName)));
            }
            stocks.put(tickerName, new StockHolding(tickerName, shareName, 0.0, sharePrices.get(tickerName)));
            notifyChanges();
            return true;
        }
    }

    //      requires: tickerName != null && shareAmount != null && shareAmount > 0
//      effects: returns true if the stock is bought using the shareAmount.
//                Catches WebsiteDataException if there are problems with
//                the website hence could not buy shares and returns false.
    public boolean buyStock(String tickerName, Double shareAmount) {

        assert (tickerName != null) : "The ticker symbol must not be null";
        assert (shareAmount != null) : "The ticker symbol must not be null";
        assert (shareAmount > 0) : "The ticker symbol" + "" + shareAmount + "" + "" + "must be greater than 0";
        StockHolding stockHolding = stocks.get(tickerName);
        if (stockHolding != null) {
            stockHolding.buyShares(shareAmount);
            notifyChanges();
            return true;
        } else {
            return false;
        }
    }

    public Map<String, IStockHolding> getStocks() {
        return new ConcurrentHashMap<>(stocks);
    }

    public String getName() {
        return name;
    }
}
