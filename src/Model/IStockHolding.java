package Model;

public interface IStockHolding {
    String getTickerSymbol();

  //  void setTickerSymbol(String tickerSymbol);

    Double getNumberOfShares();

 //   void setNumberOfShares(String numberOfShares);

    Double getShareValue();

 //   void setShareValue(String shareValue);

    Double getValueOfHolding();

  //  void setValueOfHolding(String valueOfHolding);

    void sellShares(Double amount);
    void buyShares(Double amount) throws WebsiteDataException;
     void updateShareValue() throws WebsiteDataException;


     void updateValueOfHolding();
}
