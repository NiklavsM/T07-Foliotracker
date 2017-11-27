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


    //      requires: amount > 0 && amount != null
    //      modifies: this
    //      effects: buy share using the amount and update the value.
    public void buyShares(Double amount) {
        assert (amount != null) : "The amount cannot be null";
        assert (amount > 0) : "The amount of " + amount + " must be a positive value";
        numberOfShares += amount;
        updateValueOfHolding();
    }

    //      requires: shareValue != null && shareValue > 0
    //      modifies: this
    //      effects: sets new shareValue and updates total amount of stock holding
    public void setShareValue(Double shareValue) {
        assert (shareValue != null) : " The shareValue cannot be null";
        assert (shareValue >= 0) : " The shareValue = " + shareValue + "; shareValue must be bigger or equal to 0";
        this.shareValue = shareValue;
        updateValueOfHolding();
    }
    
    private void updateValueOfHolding() {
        assert (shareValue >= 0) : "The shareValue of " + shareValue + " must be equal or bigger than 0";
        assert (numberOfShares >= 0) : "The numberOfShares of " + numberOfShares + " must be equal or bigger than 0";
        valueOfHolding = shareValue * numberOfShares;
    }


    //      requires: amount > 0 && amount != null
    //      modifies: this
    //      effects: sell share using the amount and update the value.
    public void sellShares(Double amount) {
        assert (amount != null) : "The amount cannot be null";
        assert (amount > 0) : "The amount of " + amount + " must be a positive value";
        if ((numberOfShares -= amount) < 0) {
            numberOfShares = 0.0;
        }
        updateValueOfHolding();
    }

    //      effects: returns tickerSymbol
    public String getTickerSymbol() {
        return tickerSymbol;
    }

    //      effects: returns shareName
    public String getShareName() {
        return shareName;
    }

    //      effects: returns numberOfShares
    public Double getNumberOfShares() {
        return numberOfShares;
    }

    //      effects: returns shareValue
    public Double getShareValue() {
        return shareValue;
    }

    //      effects: returns valueOfHolding
    public Double getValueOfHolding() {
        return valueOfHolding;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StockHolding that = (StockHolding) o;

        if (!tickerSymbol.equals(that.tickerSymbol)) return false;
        if (!shareName.equals(that.shareName)) return false;
        if (!numberOfShares.equals(that.numberOfShares)) return false;
        if (!shareValue.equals(that.shareValue)) return false;
        return valueOfHolding.equals(that.valueOfHolding);
    }

    @Override
    public int hashCode() {
        int result = tickerSymbol.hashCode();
        result = 31 * result + shareName.hashCode();
        result = 31 * result + numberOfShares.hashCode();
        result = 31 * result + shareValue.hashCode();
        result = 31 * result + valueOfHolding.hashCode();
        return result;
    }
}
