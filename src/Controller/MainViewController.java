package Controller;

import Model.IPortfolioContainer;
import View.IMainView;
import View.MainView;

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
            String s = (String) JOptionPane.showInputDialog(
                    mainView.getMainFrame(),
                    "Portfolio name:",
                    "New Portfolio",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null, null);

            if ((s != null) && (s.length() > 0)) {
                portfolioContainer.addToPortfolioList(s);
               // mainView.addTab(s);
                return;
            }
        });
    }

}
