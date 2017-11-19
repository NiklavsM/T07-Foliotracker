package Model;

public class StockHolding implements IStockHolding {

    private String tickerSymbol;
    private Double numberOfShares;
    private Double shareValue;
    private Double valueOfHolding;

    public StockHolding(String tickerSymbol, Double numberOfShares){
        try {
            System.out.println(Thread.currentThread().getId() + "  " + tickerSymbol);
            this.tickerSymbol = tickerSymbol;
            this.numberOfShares = Double.valueOf(numberOfShares);
//            updateShareValue();
//            updateValueOfHolding();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buyShares(Double amount){
        numberOfShares += amount;
        updateValueOfHolding();
    }

    public void updateShareValue() throws WebsiteDataException {
            shareValue = Double.valueOf(StrathQuoteServer.getLastValue(tickerSymbol));
    }

    public void updateValueOfHolding() {
        valueOfHolding = shareValue * numberOfShares;
    }

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
