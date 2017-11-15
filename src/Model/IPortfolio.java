package Model;

import java.util.Observer;

public interface IPortfolio {
    String getName();
    void addObserver(Observer o);
    void addStock(String tickerSymbol, String numberOfShares);
}
