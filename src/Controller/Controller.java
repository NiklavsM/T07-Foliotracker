package Controller;

import Model.IQuoteServer;
import Model.StrathQuoteServer;
import View.MainView;

public class Controller {
    IQuoteServer quoteServer;
    MainView mainView;

    public Controller(IQuoteServer sqs, MainView mv) {
        quoteServer = sqs;
        mainView = mv;
        setValue();
        setupCreateNew();
    }

    private void setValue() {
        try {
            mainView.getCurrentPriceTest().setText(quoteServer.getLastValue("MSFT"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupCreateNew() {
        mainView.getCreateNew().addActionListener(e -> {
            mainView.getCurrentPriceTest().setText("YEah");
        });
    }
}
