package Model;

public class StockHolding implements IStockHolding {

    private String tickerSymbol;
    private Double numberOfShares;
    private Double shareValue;
    private Double valueOfHolding;

    public StockHolding(String tickerSymbol, Double numberOfShares){
        try {
            System.out.println(Thread.currentThread().getId() + "  " + tickerSymbol);
//            String shareValue = StrathQuoteServer.getLastValue(tickerSymbol);
//            Double totalValueInteger = Double.valueOf(numberOfShares) * Double.valueOf(shareValue);
//            System.out.println(Thread.currentThread().getId() + "  " + tickerSymbol);
//            StrathQuoteServer sqs = new StrathQuoteServer();
//            String shareValue = sqs.getLastValue(tickerSymbol);
//            Double totalValueInteger = Double.valueOf(numberOfShares) * Double.valueOf(shareValue);
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

    public void setTickerSymbol(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }

    public Double getNumberOfShares() {
        return numberOfShares;
    }

//    public void setNumberOfShares(String numberOfShares) {
//        this.numberOfShares = numberOfShares;
//    }

    public Double getShareValue() {
        return shareValue;
    }

//    public void setShareValue(String shareValue) {
//        this.shareValue = shareValue;
//    }

    public Double getValueOfHolding() {
        return valueOfHolding;
    }

//    public void setValueOfHolding(String valueOfHolding) {
//        this.valueOfHolding = valueOfHolding;
//    }


}
