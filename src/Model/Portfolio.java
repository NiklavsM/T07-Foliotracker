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
        Thread updater = new Thread(new PortfoloUpdaterThread(this));
        //updater.start(); //uncommented for now, will use latter
    }

    public List<IStockHolding> getStocks() {
        return stocks;
    }

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

    private IStockHolding getStockFromContainer(String tickerSymbol) {
        for (IStockHolding sh : stocks) {
            if (sh.getTickerSymbol().equals(tickerSymbol)) {
                return sh;
            }
        }
        return null;
    }


    private void removeStock(String tickerSymbol) {
        stocks.remove(getStockFromContainer(tickerSymbol));
    }

    public boolean buyStock(String tickerName, Double shareAmount) {
        System.out.println("TEST2.1");
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
