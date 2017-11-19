package Model;

public interface IStockHolding {
    String getTickerSymbol();


    Double getNumberOfShares();


    Double getShareValue();


    Double getValueOfHolding();


    void sellShares(Double amount);

    void buyShares(Double amount) throws WebsiteDataException;

    void updateShareValue() throws WebsiteDataException;


    void updateValueOfHolding();
}
