package Model;

import static java.lang.Thread.sleep;

public class PortfoloUpdaterThread implements Runnable {


    private Portfolio portfolio;
    public PortfoloUpdaterThread(Portfolio portfolio) {
        this.portfolio = portfolio;
    }
    @Override
    public void run() {
        while(true) {
            portfolio.updateShareValues();
            System.out.println(Thread.currentThread().getId() + "   Updating values...");
            try {
                sleep(10000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
