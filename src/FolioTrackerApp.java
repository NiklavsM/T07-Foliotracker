import Controller.MainViewController;
import Model.PortfolioContainer;
import View.MainView;

public class FolioTrackerApp {
    public static void main(String[] args){
        PortfolioContainer pc = new PortfolioContainer();
        MainView mainView = new MainView(pc);
        pc.addObserver(mainView);
        MainViewController controller = new MainViewController(pc,mainView);
    }
}
