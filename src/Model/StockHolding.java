package Model;

import java.io.Serializable;

public class StockHolding implements IStockHolding, Serializable {

    private String tickerSymbol;
    private String shareName;
    private Double numberOfShares;
    private Double shareValue;
    private Double valueOfHolding;

    public StockHolding(String tickerSymbol, String shareName, Double numberOfShares, Double shareValue) {
        this.shareName = shareName;
        this.tickerSymbol = tickerSymbol;
        this.numberOfShares = numberOfShares;
        this.shareValue = shareValue;
        updateValueOfHolding();
    }


    //      requires: amount > 0
//      effects: buy share using the amount and update the value.
    public void buyShares(Double amount) {
        assert (amount > 0) : "The amount of" + "" + amount + "" + "must be a positive value";
        numberOfShares += amount;
        updateValueOfHolding();
    }

    public void setShareValue(Double shareValue) {
        this.shareValue = shareValue;
        updateValueOfHolding();
    }

    //      effects: calculates and updates value of holding.
    private void updateValueOfHolding() {
        assert (shareValue >= 0) : "The shareValue of " + shareValue + " must be equal or bigger than 0";
        assert (numberOfShares >= 0) : "The numberOfShares of " + numberOfShares + " must be equal or bigger than 0";
        valueOfHolding = shareValue * numberOfShares;
    }


    //      requires: amount > 0
//      effects: sell share using the amount and update the value.
    public void sellShares(Double amount) {
        assert (amount > 0) : "The amount of " + amount + " must be a positive value";
        if((numberOfShares -= amount)<0){
            numberOfShares = 0.0;
        }
        updateValueOfHolding();
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public String getShareName() {
        return shareName;
    }

    public Double getNumberOfShares() {
        return numberOfShares;
    }

    public Double getShareValue() {
        return shareValue;
    }

    public Double getValueOfHolding() {
        return valueOfHolding;
    }


}
