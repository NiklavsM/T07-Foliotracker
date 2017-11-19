package Model;

public class sharePriceUpdater implements Runnable {
    private StockHolding stockHolding;

    public sharePriceUpdater(StockHolding stockHolding) {
        this.stockHolding = stockHolding;
    }

    @Override
    public void run() {
        try {
            stockHolding.updateShareValue();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
