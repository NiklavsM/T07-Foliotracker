import Controller.Controller;
import Model.IQuoteServer;
import Model.StrathQuoteServer;
import View.MainView;

public class FolioTrackerApp {
    public static void main(String[] args){
        IQuoteServer sqs = new StrathQuoteServer();
        MainView mainView = new MainView();
        Controller controller = new Controller(sqs,mainView);
    }
}
