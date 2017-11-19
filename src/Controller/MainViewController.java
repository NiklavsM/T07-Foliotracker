package Controller;

import Model.IPortfolioContainer;
import View.IMainView;

public class MainViewController {
    private IMainView mainView;
    private IPortfolioContainer portfolioContainer;

    public MainViewController(IPortfolioContainer pc, IMainView mv) {
        mainView = mv;
        portfolioContainer = pc;
        setupCreateNew();
        setupOpen();
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
            s = s.toLowerCase();
            tabCount = mainView.getTabs().getTabCount();
            for (int i = 0; i < tabCount; i++) {
                if (mainView.getTabs().getTitleAt(i).equals(s)) {
                    mainView.popupErrorMessage("Portfolio is already opened");
                    mainView.getTabs().setSelectedIndex(i);
                    return;
                }
            }
            mainView.getClosedTabs().remove(s);
            mainView.update(null,null);
        });
    }

    private void setupCreateNew() {
        mainView.getCreateNew().addActionListener(e -> {
            String s = mainView.getPortfolioNamePopup(null);
            if ((s == null) || (s.length() <= 0)) {
                mainView.popupErrorMessage("Please enter portfolio's name");
                return;
            }
            s = s.toLowerCase();
            if (portfolioContainer.containsPortfolio(s)) {
                mainView.popupErrorMessage("Portfolio already exists");
                return;
            }
            portfolioContainer.addToPortfolioList(s);
        });
    }

    private void setupRemoveButton() {
        mainView.getDeleteButton().addActionListener(e -> {
            portfolioContainer.deletePortfolio(mainView.getTabs().getTitleAt(mainView.getTabs().getSelectedIndex()));
        });
    }

}
