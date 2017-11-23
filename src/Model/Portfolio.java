package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Portfolio extends Observable implements IPortfolio,Serializable {

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
        return totalValue.doubleValue();
    }

    public void notifyChanges() {
        setChanged();
        notifyObservers();
    }


//      requires: tickerSymbol != null
//      effects: returns stock if stock's TickerSymbol == tickerSymbol else returns null.
    private IStockHolding getStockFromContainer(String tickerSymbol) {
        for (IStockHolding sh : stocks) {
            if (sh.getTickerSymbol().equals(tickerSymbol)) {
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
        if (sh != null) {
            stocks.remove(sh);
            return true;
        } else {
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
        try {
            if (sh != null) {
                sh.buyShares(shareAmount);
            } else {
                if (!sharePrices.containsKey(tickerName)) {
                    sharePrices.put(tickerName, Double.valueOf(StrathQuoteServer.getLastValue(tickerName)));
                }
                stocks.add(new StockHolding(tickerName, shareAmount, sharePrices.get(tickerName)));
            }
            notifyChanges();
            return true;
        } catch (WebsiteDataException wdEx) {
            return false;
        }
    }

    public List<IStockHolding> getStocks() {
        return stocks;
    }

    public String getName() {
        return name;
    }
}
