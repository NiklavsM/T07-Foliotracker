package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Portfolio extends Observable implements IPortfolio {

    private List<IStockHolding> stocks = new ArrayList<>();
    private String name;
    private Lock stockLock = new ReentrantLock();

    public Portfolio(String name) {
        this.name = name;
        //Thread updater = new Thread(new PortfolioUpdaterThread(this));
        //updater.start(); //uncommented for now, will use later
    }

    public List<IStockHolding> getStocks() {
        return stocks;
    }

    /**
     * @requires: tickerSymbol != null && amount > 0
     * @modifies: this
     * @effects: returns true if the stock is sold using
     *           using the amount else if stock is not in portfolio returns false.
     */
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

    /**
     * @effects: updates the share values. Prints stack trace if it
     *           catches Exception e.
     */
    public void updateShareValues() {
        stockLock.lock();
        try {
            for (IStockHolding sh : stocks) {
                sh.updateShareValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stockLock.unlock();
        }


        notifyChanges();
    }


    /**
     * @effects: returns the total value of the stock.
     */
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

    /**
     * @requires: tickerSymbol != null
     * @effects: returns stock if stock's TickerSymbol == tickerSymbol else returns null.
     */
    private IStockHolding getStockFromContainer(String tickerSymbol) {
        for (IStockHolding sh : stocks) {
            if (sh.getTickerSymbol().equals(tickerSymbol)) {
                return sh;
            }
        }
        return null;
    }

    /**
     * @requires: tickerSymbol != null
     * @effects: removes stock from the List by using the tickerSymbol, returns true if stock was remnoved returns false if
     */

    private boolean removeStock(String tickerSymbol) {
        IStockHolding sh = getStockFromContainer(tickerSymbol);
        if(sh != null) {
            stocks.remove(sh);
            return true;
        }else {
            return false;
        }
    }

    /**
     * @requires: tickerName != null && shareAmount > 0
     * @effects: returns true if the stock is bought using
     *           the shareAmount. Catches
     *           WebsiteDataException if there are problems with the website hence couldnd buy shares and returns false.
     *
     */
    public boolean buyStock(String tickerName, Double shareAmount) {
        IStockHolding sh = getStockFromContainer(tickerName);
        try {
            if (sh != null) {
                sh.buyShares(shareAmount);
            } else {
                IStockHolding newStock = new StockHolding(tickerName, shareAmount);
                newStock.updateShareValue();
                newStock.updateValueOfHolding();
                stocks.add(newStock);
            }
            notifyChanges();
            return true;
        } catch (WebsiteDataException wdEx) {
            return false;
        }
    }


    public String getName() {
        return name;
    }
}
