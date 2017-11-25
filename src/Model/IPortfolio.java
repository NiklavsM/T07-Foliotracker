package Model;

import java.util.Map;
import java.util.Observer;

public interface IPortfolio {
    String getName();

    void addObserver(Observer o);

    boolean buyStock(String tickerSymbol, Double numberOfShares);

    Map<String,IStockHolding> getStocks();

    void notifyChanges();

    boolean sellStock(String tickerSymbol, Double amount);

    Double getTotalValue();

    boolean addStock(String tickerSymbol, String shareName) throws WebsiteDataException;
}
