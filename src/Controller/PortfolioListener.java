package Controller;

import Model.IPortfolio;
import Model.WebsiteDataException;
import View.IPortfolioPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PortfolioListener implements ActionListener {

    private IPortfolioPanel portfolioPanel;
    private IPortfolio portfolio;

    public PortfolioListener(IPortfolioPanel pp, IPortfolio portfolio) {
        portfolioPanel = pp;
        this.portfolio = portfolio;
    }

    private void tryToBuyStock() {
        Double shareAmount;
        String tickerSymbol = portfolioPanel.getBuySellTickerSymbol().getText().toLowerCase();
        try {
            if(tickerSymbol.equals("")){
                portfolioPanel.popupErrorMessage("Please enter the ticker symbol");
                return;
            }
            shareAmount = Double.valueOf(portfolioPanel.getBuySellShareAmount().getText());
            if (!portfolio.buyStock(tickerSymbol, shareAmount)) {
                portfolioPanel.popupErrorMessage("You dont own this stock");
            }
        } catch (NumberFormatException nfEx) {
            portfolioPanel.popupErrorMessage("Buy amount has to be positive integer");
            return;
        }
        if (shareAmount <= 0) {
            portfolioPanel.popupErrorMessage("Buy amount has to be positive integer");
        }
    }

    private void tryToAddStock() {
        String tickerSymbol = portfolioPanel.getAddShareTickerSymbol().getText().toLowerCase();
        String shareName = portfolioPanel.getAddShareName().getText();
        try {
            if(tickerSymbol.equals("")){
                portfolioPanel.popupErrorMessage("Please enter the ticker symbol");
                return;
            }
            if (!portfolio.addStock(tickerSymbol, shareName)) {
                portfolioPanel.popupErrorMessage("You already own this stock");
            }
        } catch (WebsiteDataException webEx) {
            portfolioPanel.popupErrorMessage("Problems occurred when trying to access stock: " + tickerSymbol + ". Please check spelling and internet connection");
        }
    }

    private void tryToSellStock() {
        Double shareAmount;
        String tickerSymbol = portfolioPanel.getBuySellTickerSymbol().getText().toLowerCase();
        try {
            if(tickerSymbol.equals("")){
                portfolioPanel.popupErrorMessage("Please enter the ticker symbol");
                return;
            }
            shareAmount = Double.valueOf(portfolioPanel.getBuySellShareAmount().getText());
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
        if (e.getActionCommand().equals("Add")) {
            tryToAddStock();
        } else if (e.getActionCommand().equals("Buy")) {
            tryToBuyStock();
        } else if (e.getActionCommand().equals("Sell")) {
            tryToSellStock();
        }
    }
}
