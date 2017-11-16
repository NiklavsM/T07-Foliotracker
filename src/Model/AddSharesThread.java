//package Model;
//
//public class AddSharesThread implements Runnable {
//
//    IPortfolio portfolio;
//    String tickerSymbol;
//    Double shareAmount;
//
//    public AddSharesThread(IPortfolio portfolio, String tickerSymbol, Double shareAmount) {
//        this.portfolio = portfolio;
//        this.tickerSymbol = tickerSymbol;
//        this.shareAmount = shareAmount;
//    }
//
//    @Override
//    public void run() {
//        portfolio.buyStock(tickerSymbol,shareAmount);
//    }
//}
