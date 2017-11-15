package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Portfolio extends Observable implements IPortfolio {

    List<StockHolding> stocks = new ArrayList<>();
    String name;

    public Portfolio(String name) {
        this.name = name;
    }

    public String[][] stockListToArray() {
        int portfolioSize = getStocks().size();
        String[][] portfolioDataArray = new String[portfolioSize][4];
        int i = 0;
        for (StockHolding sh : getStocks()) {
            portfolioDataArray[i][0] = sh.getTickerSymbol();
            portfolioDataArray[i][1] = sh.getNumberOfShares();
            portfolioDataArray[i][2] = sh.getShareValue();
            portfolioDataArray[i][3] = sh.getValueOfHolding();
            i++;
        }
        return portfolioDataArray;
    }

    public List<StockHolding> getStocks() {
        return stocks;
    }

    public void addStock(String tickerName, String shareAmount) {
        System.out.println("TEST2.1");
        try {
            String shareValue = StrathQuoteServer.getLastValue(tickerName);
            Double totalValueInteger = Double.valueOf(shareAmount) * Double.valueOf(shareValue);
            StockHolding newStockHolding = new StockHolding(tickerName, shareAmount, shareValue, totalValueInteger.toString());
            stocks.add(new StockHolding(tickerName, shareAmount, shareValue, totalValueInteger.toString()));
            setChanged();
            notifyObservers(stockListToArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String[][] stockListToArrayDel() {
        int stocksSize = stocks.size();
        String[][] sockArray = new String[stocks.size()][4];
        for (int i = 0; i < stocksSize; i++) {
            sockArray[i][0] = stocks.get(i).getTickerSymbol();
            sockArray[i][1] = stocks.get(i).getNumberOfShares();
            sockArray[i][2] = stocks.get(i).getShareValue();
            sockArray[i][3] = stocks.get(i).getValueOfHolding();
        }

        return null;
    }

    public String getName() {
        return name;
    }
}
