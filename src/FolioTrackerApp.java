import Model.PortfolioContainer;
import View.PortfolioContainerView;

import java.awt.*;

public class FolioTrackerApp {
    public static void main(String[] args){
        PortfolioContainer pc = new PortfolioContainer();
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                PortfolioContainerView portfolioContainerView = new PortfolioContainerView(pc);
                pc.addObserver(portfolioContainerView);
            }
        });


    }
}
