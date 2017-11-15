package Controller;

import Model.IPortfolio;
import View.PortfolioPanel;

public class PortfolioController {

    private PortfolioPanel portfolioPanel;
    private IPortfolio portfolio;

    public PortfolioController(PortfolioPanel pp, IPortfolio portfolio) {
        System.out.println("TEST2.09");
        portfolioPanel = pp;
        this.portfolio = portfolio;
        this.portfolio.addObserver(pp);
        setUpAddButton();
    }

    void setUpAddButton() {
        portfolioPanel.getAddButton().addActionListener(e -> {
            portfolio.addStock(portfolioPanel.getInputTickerName().getText(), portfolioPanel.getInputShareAmount().getText());
        });
    }
}
