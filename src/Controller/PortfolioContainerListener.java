package Controller;

import Model.IPortfolioContainer;
import View.IPortfolioContainerView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class PortfolioContainerListener implements ActionListener {
    private IPortfolioContainerView mainView;
    private IPortfolioContainer portfolioContainer;

    public PortfolioContainerListener(IPortfolioContainerView mv, IPortfolioContainer pc) {
        mainView = mv;
        portfolioContainer = pc;
    }

    private boolean loadFromFile() {
        String path;
        try {
            path = mainView.getFilePath();
            if (path != null) {
                portfolioContainer.loadFromFile(path);
                return true;
            }
            return false;
        } catch (IOException e) {
            mainView.popupMessage("Failed to load the file");
        } catch (ClassNotFoundException e) {
            mainView.popupMessage("Failed to load the file");
        }
        return true;
    }

    private void saveToFile() {
        String path;
        try {
            path = mainView.getFilePath();
            if (path != null) {
                portfolioContainer.saveToFile(mainView.getFilePath());
                mainView.popupMessage("File has been saved");
            }
        } catch (IOException e) {
            mainView.popupMessage("Failed to save the file");
        }
    }

    private void tryToAddPortfolio() {
        String s = mainView.getPortfolioNamePopup(null, "New Portfolio");
        if ((s == null) || (s.length() <= 0)) {
            return;
        }
        if (!portfolioContainer.addPortfolio(s)) {
            mainView.popupMessage("Portfolio already exists");
        }

    }

    private String[] getPortfolioNames() {
        String[] portfolioNames = new String[portfolioContainer.getPortfolios().size()];
        int i = 0;
        for (String name : portfolioContainer.getPortfolios().keySet()) {
            portfolioNames[i] = name;
            i++;
        }
        return portfolioNames;
    }

    private void tryToOpenPortfolio() {
        int tabCount;

        String s = mainView.getPortfolioNamePopup(getPortfolioNames(), "Open Portfolio");
        if ((s == null) || (s.length() <= 0)) {
            return;
        }
        tabCount = mainView.getTabs().getTabCount();
        for (int i = 0; i < tabCount; i++) {
            if (mainView.getTabs().getTitleAt(i).equals(s)) {
                mainView.popupMessage("Portfolio is already opened");
                mainView.getTabs().setSelectedIndex(i);
                return;
            }
        }
        mainView.getClosedTabs().remove(s);
        mainView.update(null, null);
    }

    private void tryDeletePortfolio() {
        if (mainView.conformationPopup("Are you sure you want to delete the current portfolio?", "Delete Portfolio?")) {
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
            saveToFile();
        } else if (e.getActionCommand().equals("Load")) {
            mainView.emptyTabs();
            if (!loadFromFile()) {
                mainView.update(null, null);
            }

        }

    }

}
