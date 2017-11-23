package Controller;

import Model.IPortfolioContainer;
import View.IMainView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

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
            //mainView.popupErrorMessage("Please enter portfolio's name");
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
        System.out.println("Success2" + portfolioContainer.getPortfolioNames());
        for (String s : portfolioContainer.getPortfolioNames()) {
            System.out.println("Name: " + s);
        }
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
        int deleteYES = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the current portfolio?", "Delete Portfolio?", JOptionPane.YES_NO_OPTION);
        if (deleteYES == JOptionPane.YES_OPTION) {
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
            mainView.getTabs().removeAll();
            portfolioContainer.loadFromFile();
        }
    }
}
