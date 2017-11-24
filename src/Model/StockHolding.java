package Model;

import java.io.Serializable;

public class StockHolding implements IStockHolding, Serializable {

    private String tickerSymbol;
    private String shareName;
    private Double numberOfShares;
    private Double shareValue;
    private Double valueOfHolding;

    public StockHolding(String tickerSymbol, String shareName, Double numberOfShares, Double shareValue) {
        try {
            this.shareName = shareName;
            this.tickerSymbol = tickerSymbol;
            this.numberOfShares = Double.valueOf(numberOfShares);
            this.shareValue = shareValue;
            updateValueOfHolding();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//      requires: amount > 0
//      effects: buy share using the amount and update the value.
    public void buyShares(Double amount) {
        numberOfShares += amount;
        updateValueOfHolding();
    }

    public void setShareValue(Double shareValue) {
        this.shareValue = shareValue;
        updateValueOfHolding();
    }

//      effects: calculates and updates value of holding.
    public void updateValueOfHolding() {
        valueOfHolding = shareValue * numberOfShares;
    }


//      requires: amount > 0
//      effects: sell share using the amount and update the value.
    public void sellShares(Double amount) {
        this.numberOfShares -= amount;
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
