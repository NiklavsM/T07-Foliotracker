package Model;

public class StockHolding implements IStockHolding {

    private String tickerSymbol;
    private Double numberOfShares;
    private Double shareValue;
    private Double valueOfHolding;

    public StockHolding(String tickerSymbol, Double numberOfShares, Double shareValue) {
        try {
            System.out.println(Thread.currentThread().getId() + "  " + tickerSymbol);
            this.tickerSymbol = tickerSymbol;
            this.numberOfShares = Double.valueOf(numberOfShares);
            this.shareValue = shareValue;
            updateValueOfHolding();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @requires: amount > 0
     * @effects: buy share using the amount and update the value.
     */
    public void buyShares(Double amount) {
        numberOfShares += amount;
        updateValueOfHolding();
    }

    public void setShareValue(Double shareValue) {
        this.shareValue = shareValue;
        updateValueOfHolding();
    }

    /**
     * @effects: calculates and updates value of holding.
     */
    public void updateValueOfHolding() {
        valueOfHolding = shareValue * numberOfShares;
    }

    /**
     * @requires: amount > 0
     * @effects: sell share using the amount and update the value.
     */
    public void sellShares(Double amount) {
        this.numberOfShares -= amount;
        updateValueOfHolding();
    }

    public String getTickerSymbol() {
        return tickerSymbol;
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
