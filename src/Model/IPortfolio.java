package Model;

import java.util.List;
import java.util.Observer;

public interface IPortfolio {
    String getName();
    void addObserver(Observer o);
    void buyStock(String tickerSymbol, Double numberOfShares) throws WebsiteDataException;
    List<IStockHolding> getStocks();

    boolean sellStock(String tickerSymbol, Double amount);
}
