package Controller;

import Model.IPortfolioContainer;
import View.IMainView;

import javax.swing.*;

public class MainViewController {
    private IMainView mainView;
    private IPortfolioContainer portfolioContainer;

    public MainViewController(IPortfolioContainer pc, IMainView mv) {
        //    quoteServer = sqs;
        mainView = mv;
        portfolioContainer = pc;
        //setValue();
        setupCreateNew();
        setupOpen();
        setupCloseButton();
        setupRemoveButton();
    }

    private void setupOpen() {
        mainView.getOpenNew().addActionListener(e -> {
            int tabCount;
            String s = mainView.getPortfolioNamePopup(portfolioContainer.getPortfolioNames());
            if ((s == null) || (s.length() <= 0)) {
                mainView.popupErrorMessage("Please enter portfolio's name");
                return;
            }
            tabCount = mainView.getTabs().getTabCount();
            for (int i = 0; i < tabCount; i++) {
                if (mainView.getTabs().getTitleAt(i).equals(s)) {
                    mainView.popupErrorMessage("Portfolio is already opened");
                    mainView.getTabs().setSelectedIndex(i);
                    return;
                }
            }
                portfolioContainer.addToPortfolioList(s);
        });
    }

    private void setValue() {
        try {
            // mainView.getCurrentPriceTest().setText(quoteServer.getLastValue("MSFT"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupCreateNew() {
        mainView.getCreateNew().addActionListener(e -> {
            String s = mainView.getPortfolioNamePopup(null);
            if ((s == null) || (s.length() <= 0)) {
                mainView.popupErrorMessage("Please enter portfolio's name");
                return;
            }
            if (portfolioContainer.containsPortfolio(s)) {
                mainView.popupErrorMessage("Portfolio already exists");
                return;
            }
            portfolioContainer.addToPortfolioList(s);
        });
    }

    private void setupCloseButton() {

        mainView.getCloseButton().addActionListener(e -> {
            closeTab();
        });

    }

    private void setupRemoveButton() {
        mainView.getDeleteButton().addActionListener(e -> {
            portfolioContainer.deletePortfolio(mainView.getTabs().getTitleAt(mainView.getTabs().getSelectedIndex()));
            closeTab();
        });
    }

    private void closeTab() {
        mainView.getTabs().remove(mainView.getTabs().getSelectedIndex());
        if (mainView.getTabs().getSelectedIndex() == -1) {
            mainView.getCloseButton().setVisible(false);
            mainView.getDeleteButton().setVisible(false);
        }
    }

}
