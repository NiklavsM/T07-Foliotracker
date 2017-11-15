import Controller.Controller;
import Model.PortfolioContainer;
import View.MainView;

public class FolioTrackerApp {
    public static void main(String[] args){
        PortfolioContainer pc = new PortfolioContainer();
        MainView mainView = new MainView(pc);
        Controller controller = new Controller(pc,mainView);
    }
}
