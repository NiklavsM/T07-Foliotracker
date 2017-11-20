package Controller;

import Model.IPortfolio;
import View.IPortfolioPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PortfolioController implements ActionListener {

    private IPortfolioPanel portfolioPanel;
    private IPortfolio portfolio;

    public PortfolioController(IPortfolioPanel pp, IPortfolio portfolio) {
        portfolioPanel = pp;
        this.portfolio = portfolio;
        //portfolio.notifyChanges();
    }

    private void tryToBuyStock(){
        Double shareAmount = 0.0;
        String tickerSymbol = portfolioPanel.getBuyTickerName().getText().toLowerCase();
        try {
            shareAmount = Double.valueOf(portfolioPanel.getBuyShareAmount().getText());
            if (!portfolio.buyStock(tickerSymbol, shareAmount)) {
                portfolioPanel.popupErrorMessage("Problems occurred when trying to access stock: " + tickerSymbol + ". Please check spelling and internet connection");
            }
        } catch (NumberFormatException nfEx) {
            portfolioPanel.popupErrorMessage("Buy amount has to be positive integer");
            return;
        }
        if (shareAmount <= 0) {
            portfolioPanel.popupErrorMessage("Buy amount has to be positive integer");
        }
    }
    private void tryToSellStock(){
        Double shareAmount = null;
        String tickerSymbol = portfolioPanel.getSellTickerName().getText().toLowerCase();
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
        if (!portfolio.sellStock(tickerSymbol, shareAmount)) {
            portfolioPanel.popupErrorMessage("You do not own this stock");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(portfolioPanel.getAddButton())) {
            tryToBuyStock();
        } else if (e.getSource().equals(portfolioPanel.getSellButton())) {
            tryToSellStock();
        }
    }
}
