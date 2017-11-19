package Model;

import java.util.List;
import java.util.Observer;

public interface IPortfolio {
    String getName();

    void addObserver(Observer o);

    boolean buyStock(String tickerSymbol, Double numberOfShares);

    List<IStockHolding> getStocks();

    void notifyChanges();

    boolean sellStock(String tickerSymbol, Double amount);

    Double getTotalValue();
}
