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
    }

    private void tryToOpenPortfolio() {
        int tabCount;
        String s = mainView.getPortfolioNamePopup(portfolioContainer.getPortfolioNames());
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(mainView.getDeleteButton())) {
            portfolioContainer.deletePortfolio(mainView.getTabs().getTitleAt(mainView.getTabs().getSelectedIndex()));
        } else if (e.getSource().equals(mainView.getCreateNew())) {
            tryToAddPortfolio();
        } else if (e.getSource().equals(mainView.getOpenNew())) {
            tryToOpenPortfolio();
        }
    }
}
