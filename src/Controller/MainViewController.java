package Controller;

import Model.IPortfolioContainer;
import View.IMainView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainViewController implements ActionListener {
    private IMainView mainView;
    private IPortfolioContainer portfolioContainer;

    public MainViewController(IMainView mv, IPortfolioContainer pc) {
        mainView = mv;
        portfolioContainer = pc;
    }

    private void tryToAddPortfolio() {
        String s = mainView.getPortfolioNamePopup(null, "New Portfolio");
        if ((s == null) || (s.length() <= 0)) {
            return;
        }
        if (portfolioContainer.containsPortfolio(s)) {
            mainView.popupErrorMessage("Portfolio already exists");
            return;
        }
        portfolioContainer.addToPortfolioList(s);
    }

    private void tryToOpenPortfolio() {
        int tabCount;

        String s = mainView.getPortfolioNamePopup(portfolioContainer.getPortfolioNames(), "Open Portfolio");
        if ((s == null) || (s.length() <= 0)) {
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
        mainView.getClosedTabs().remove(s);
        mainView.update(null, null);
    }

    private void tryDeletePortfolio() {
        //Delete confirmation
        int deleteYES = mainView.conformationPopup("Are you sure you want to delete the current portfolio?", "Delete Portfolio?");
        if (deleteYES == 0) {
            portfolioContainer.deletePortfolio(mainView.getTabs().getTitleAt(mainView.getTabs().getSelectedIndex()));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Delete")) {
            tryDeletePortfolio();
        } else if (e.getActionCommand().equals("Create New")) {
            tryToAddPortfolio();
        } else if (e.getActionCommand().equals("Open")) {
            tryToOpenPortfolio();
        } else if (e.getActionCommand().equals("Save")) {
            portfolioContainer.saveToFile();
        } else if (e.getActionCommand().equals("Load")) {
            mainView.emptyTabs();
            if (!portfolioContainer.loadFromFile()) {
                mainView.update(null, null);
            }

        }

    }

}
