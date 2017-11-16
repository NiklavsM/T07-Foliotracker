package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Portfolio extends Observable implements IPortfolio {

    List<IStockHolding> stocks = new ArrayList<>();
    String name;
    Lock stockLock = new ReentrantLock();

    public Portfolio(String name) {
        this.name = name;
    }

//    public String[][] stockListToArray() {
//        int portfolioSize = getStocks().size();
//        String[][] portfolioDataArray = new String[portfolioSize][4];
//        int i = 0;
//        for (StockHolding sh : getStocks()) {
//            portfolioDataArray[i][0] = sh.getTickerSymbol();
//            portfolioDataArray[i][1] = sh.getNumberOfShares();
//            portfolioDataArray[i][2] = sh.getShareValue();
//            portfolioDataArray[i][3] = sh.getValueOfHolding();
//            i++;
//        }
//        return portfolioDataArray;
//    }

    public List<IStockHolding> getStocks() {
        return stocks;
    }

    public boolean sellStock(String tickerSymbol, Double amount) {
        IStockHolding stockHolding = getStockFromContainer(tickerSymbol);
        if(stockHolding!=null) {
            stockHolding.sellShares(Double.valueOf(amount));
            if (stockHolding.getNumberOfShares() <= 0) {
                removeStock(tickerSymbol);
            }
            setChanged();
            notifyObservers(this);
            return true;
        }
        return false;
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

    public void buyStock(String tickerName, Double shareAmount) throws WebsiteDataException {
        System.out.println("TEST2.1");
        IStockHolding sh = getStockFromContainer(tickerName);
        if (sh != null) {
            sh.buyShares(shareAmount);
        } else {
            IStockHolding newStock = new StockHolding(tickerName, shareAmount);
            newStock.updateShareValue();
            newStock.updateValueOfHolding();
            stocks.add(newStock);
        }
        setChanged();
        notifyObservers(this);
    }

//    private String[][] stockListToArrayDel() {
//        int stocksSize = stocks.size();
//        String[][] sockArray = new String[stocks.size()][4];
//        for (int i = 0; i < stocksSize; i++) {
//            sockArray[i][0] = stocks.get(i).getTickerSymbol();
//            sockArray[i][1] = stocks.get(i).getNumberOfShares();
//            sockArray[i][2] = stocks.get(i).getShareValue();
//            sockArray[i][3] = stocks.get(i).getValueOfHolding();
//        }
//
//        return null;
//    }

    public String getName() {
        return name;
    }
}
