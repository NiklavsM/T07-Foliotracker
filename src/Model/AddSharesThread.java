package Model;

public class AddSharesThread implements Runnable {

    IPortfolio portfolio;
    String tickerSymbol;
    String shareAmount;

    public AddSharesThread(IPortfolio portfolio, String tickerSymbol, String shareAmount) {
        this.portfolio = portfolio;
        this.tickerSymbol = tickerSymbol;
        this.shareAmount = shareAmount;
    }

    @Override
    public void run() {
        portfolio.addStock(tickerSymbol,shareAmount);
    }
}
