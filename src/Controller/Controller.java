package Controller;

import Model.IPortfolioContainer;
import View.MainView;

import javax.swing.*;

public class Controller {
    private MainView mainView;
    private IPortfolioContainer portfolioContainer;

    public Controller(IPortfolioContainer pc, MainView mv) {
    //    quoteServer = sqs;
        mainView = mv;
        portfolioContainer = pc;
        pc.addObserver(mv);
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
