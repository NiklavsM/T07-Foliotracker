package Model;

public class StockHolding {

    private String tickerSymbol;
    private String numberOfShares;
    private String shareValue;
    private String valueOfHolding;

    public StockHolding(String tickerSymbol, String numberOfShares, String shareValue, String valueOfHolding) {
        this.tickerSymbol = tickerSymbol;
        this.numberOfShares = numberOfShares;
        this.shareValue = shareValue;
        this.valueOfHolding = valueOfHolding;
    }


    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public void setTickerSymbol(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }

    public String getNumberOfShares() {
        return numberOfShares;
    }

    public void setNumberOfShares(String numberOfShares) {
        this.numberOfShares = numberOfShares;
    }

    public String getShareValue() {
        return shareValue;
    }

    public void setShareValue(String shareValue) {
        this.shareValue = shareValue;
    }

    public String getValueOfHolding() {
        return valueOfHolding;
    }

    public void setValueOfHolding(String valueOfHolding) {
        this.valueOfHolding = valueOfHolding;
    }


}
