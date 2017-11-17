package Controller;

import Model.IPortfolio;
import Model.NoSuchTickerException;
import Model.WebsiteDataException;
import View.IPortfolioPanel;

public class PortfolioController {

    private IPortfolioPanel portfolioPanel;
    private IPortfolio portfolio;

    public PortfolioController(IPortfolioPanel pp, IPortfolio portfolio) {
        System.out.println("TEST2.09");
        portfolioPanel = pp;
        this.portfolio = portfolio;
        setupAddButton();
        setupSellButton();
        portfolio.notifyChanges();
    }

    void setupAddButton() {
        portfolioPanel.getAddButton().addActionListener(e -> {
            Double shareAmount = 0.0;
            String tickerSymbol = portfolioPanel.getBuyTickerName().getText();
            try {
                shareAmount = Double.valueOf(portfolioPanel.getBuyShareAmount().getText());
//                Thread addShares = new Thread(new AddSharesThread(portfolio,portfolioPanel.getInputTickerName().getText(),shareAmount));
//                addShares.start();
                if(!portfolio.buyStock(tickerSymbol, shareAmount)){
                    portfolioPanel.popupErrorMessage("Problems occurred when trying to access stock: " + tickerSymbol + ". Please check spelling and internet connection");
                }
            }
            catch (NumberFormatException nfEx){
                portfolioPanel.popupErrorMessage("Buy amount has to be positive integer");
                return;
            }
            if (shareAmount <= 0) {
                portfolioPanel.popupErrorMessage("Buy amount has to be positive integer");
            }
        });
    }

    void setupSellButton() {
        portfolioPanel.getSellButton().addActionListener(e -> {
            Double shareAmount = null;
            try {
                shareAmount = Double.valueOf(portfolioPanel.getSellTickerShareAmount().getText());
            } catch (NumberFormatException ex) {
                portfolioPanel.popupErrorMessage("Sell amount has to be positive integer");
                return;
            }
            if (shareAmount <= 0) {
                portfolioPanel.popupErrorMessage("Sell amount has to be positive integer");
                return;
            }
            if (!portfolio.sellStock(portfolioPanel.getSellTickerName().getText(), shareAmount)) {
                portfolioPanel.popupErrorMessage("You do not own this stock");
            }
        });
    }

}
