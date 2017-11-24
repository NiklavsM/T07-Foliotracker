package Model;

public interface IStockHolding {
    String getTickerSymbol();

    String getShareName();

    Double getNumberOfShares();

    Double getShareValue();

    Double getValueOfHolding();

    void sellShares(Double amount);

    void buyShares(Double amount);

    void setShareValue(Double shareValue);
}
