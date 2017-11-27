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

    //    requires: tickerSymbol != null && amount > 0 && amount != null
    //    modifies: this
    //    effects: If subtracting amount will result in balance < 0 delete stock else sells the specified amount in both cases returns true,
//             if stock is not in portfolio returns false.
    public boolean sellStock(String tickerSymbol, Double amount) {

        assert (amount != null) : "The amount cannot be null";
        assert (amount > 0) : "The amount of " + amount + " is negative, it must be a positive value";
        assert (tickerSymbol != null) : "The ticker symbol is null, it must contain a value";

        StockHolding stockHolding = stocks.get(tickerSymbol);
        if (stockHolding != null) {
            if (Double.compare(stockHolding.getNumberOfShares() - amount, 0.0) < 0) {
                removeStock(tickerSymbol);
            } else {
                stockHolding.sellShares(amount);
            }
            notifyChanges();
            return true;
        }
        return false;
    }

    //    effects: updates the share values.
    void updateShareValues() {
        for (String tickerSymbol : stocks.keySet()) {
            stocks.get(tickerSymbol).setShareValue(sharePrices.get(tickerSymbol));
        }
        notifyChanges();
    }


    //    effects: returns the total value of the stock.
    public Double getTotalValue() {
        Double totalValue = 0.0;
        for (String tickerSymbol : stocks.keySet()) {
            totalValue += stocks.get(tickerSymbol).getValueOfHolding();
        }
        assert (totalValue >= 0.0) : "The value " + totalValue + " is negative, the total value of the stock must be 0 or greater";
        return totalValue;
    }

    private void notifyChanges() {
        setChanged();
        notifyObservers();
    }

    //     requires: tickerSymbol != null
    //     modifies: this
    //     effects: removes stock from the stock map by using the tickerSymbol,
    private void removeStock(String tickerSymbol) {
        assert (tickerSymbol != null) : "The ticker symbol must not be null";
        if (stocks.containsKey(tickerSymbol)) {
            stocks.remove(tickerSymbol);
            notifyChanges();
        }
    }

    //     requires: tickerSymbol != null && shareName != null
    //     modifies: this
    //     effects: adds stock from the stock map by using the tickerName and the shareName
    //              returns true if the stock was added else if stock map already contained stock returns false,
    //              throws WebsiteDataException if fails to get stock price
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
    //                returns false if there is no stock with this ticker symbol in portfolio
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
    //      effects: returns socks copy
    public Map<String, IStockHolding> getStocks() {
        return new ConcurrentHashMap<>(stocks);
    }
    //      effects: returns portfolio name
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Portfolio portfolio = (Portfolio) o;

        if (!stocks.equals(portfolio.stocks)) return false;
        if (!name.equals(portfolio.name)) return false;
        return sharePrices.equals(portfolio.sharePrices);
    }

    @Override
    public int hashCode() {
        int result = stocks.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + sharePrices.hashCode();
        return result;
    }
}
