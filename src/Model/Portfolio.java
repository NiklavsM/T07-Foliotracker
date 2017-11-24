package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Portfolio extends Observable implements IPortfolio, Serializable {

    private List<IStockHolding> stocks = new ArrayList<>();
    private String name;
    private Lock stockLock = new ReentrantLock();
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
        IStockHolding stockHolding = getStockFromContainer(tickerSymbol);
        assert(amount > 0):"The amount of" + "" + amount + "" + " is negative, it must be a positive value";
        assert(tickerSymbol == null): "The ticker symbol" + "" + tickerSymbol + "" + "is null, it must contain a value";
        if (stockHolding != null) {
            stockHolding.sellShares(Double.valueOf(amount));
            if (stockHolding.getNumberOfShares() <= 0) {
                removeStock(tickerSymbol);
            }
            notifyChanges();
            return true;
        }
        return false;
    }


//    effects: updates the share values. Prints stack trace if it
//    catches Exception e.
    public void updateShareValues() {
        stockLock.lock();
        try {
            for (IStockHolding sh : stocks) {
                sh.setShareValue(sharePrices.get(sh.getTickerSymbol()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stockLock.unlock();
        }

        notifyChanges();
    }



//    effects: returns the total value of the stock.
    @Override
    public Double getTotalValue() {
        Double totalValue = 0.0;
        for (IStockHolding sh : stocks) {
            totalValue += sh.getValueOfHolding();
        }
        assert(totalValue >= 0.0): "The value" + "" + totalValue + "" + "is negative, the total value of the stock must be 0 or greater";
        return totalValue.doubleValue();
    }

    public void notifyChanges() {
        setChanged();
        notifyObservers();
    }


//      requires: tickerSymbol != null
//      effects: returns stock if stock's TickerSymbol == tickerSymbol else returns null.
    private IStockHolding getStockFromContainer(String tickerSymbol) {
        assert(tickerSymbol == null): "The ticker symbol" + "" + tickerSymbol + "" + "" + "must not contain a null value";
        for (IStockHolding sh : stocks) {
            if (sh.getTickerSymbol().equals(tickerSymbol)) {
                assert(sh.getTickerSymbol().equals(tickerSymbol)): "The stocks's TickerSymbol is not equal to the tickerSymbol;";
                assert(tickerSymbol.equals(sh.getTickerSymbol())): "The TickerSymbol is not equal to the stock's tickerSymbol;";
                return sh;
            }
        }
        return null;
    }


//     requires: tickerSymbol != null
//     effects: removes stock from the List by using the tickerSymbol,
//              returns true if stock was removed else returns false.
    private boolean removeStock(String tickerSymbol) {
        IStockHolding sh = getStockFromContainer(tickerSymbol);
        assert(tickerSymbol == null): "The ticker symbol" + "" + tickerSymbol + "" + "" + "must not contain a null value";
        if (sh != null) {
            stocks.remove(sh);
            return true;
        } else {
            return false;
        }
    }
    //     requires: tickerSymbol != null && shareName != null
//     effects: adds stock from the List by using the tickerName and the shareName
    //          returns true if the stock was added else false

    public boolean addStock(String tickerName, String shareName) {
        assert(tickerName == null): "The ticker name" + "" + tickerName + "" + "must not be null";
        assert(shareName == null) : "The share name" + "" + shareName + "" + "must not be null";
        IStockHolding sh = getStockFromContainer(tickerName);
        try {
            if (sh != null) {
                return false;
            } else {
                if (!sharePrices.containsKey(tickerName)) {
                    sharePrices.put(tickerName, Double.valueOf(StrathQuoteServer.getLastValue(tickerName)));
                }
                stocks.add(new StockHolding(tickerName, shareName, 0.0, sharePrices.get(tickerName)));
            }
            notifyChanges();
            return true;
        } catch (WebsiteDataException wdEx) {
            return false;
        }
    }

//      requires: tickerName != null && shareAmount != null && shareAmount > 0
//      effects: returns true if the stock is bought using the shareAmount.
//                Catches WebsiteDataException if there are problems with
//                the website hence could not buy shares and returns false.
    public boolean buyStock(String tickerName, Double shareAmount) {
        System.out.println("shareTAble  " + sharePrices.size() + "Portfolio " + getName());
        IStockHolding sh = getStockFromContainer(tickerName);
        assert(tickerName == null): "The ticker symbol" + "" + tickerName + "" + "" + "must not contain a null value";
        assert(shareAmount == null): "The ticker symbol" + "" + shareAmount+ "" + "" + "must not contain a null value";
        assert(shareAmount > 0): "The ticker symbol" + "" + shareAmount + "" + "" + "must be greater than 0";
        if (sh != null) {
            sh.buyShares(shareAmount);
        } else {
            return false;
        }
        notifyChanges();
        return true;

    }

    public List<IStockHolding> getStocks() {
        return stocks;
    }

    public String getName() {
        return name;
    }
}
