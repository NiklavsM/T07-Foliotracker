package Model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.lang.Thread.sleep;

public class SharePriceUpdaterThread implements Runnable, Serializable {


    private PortfolioContainer portfolioContainer;
    private Map<String, Double> sharePrices;

    public SharePriceUpdaterThread(PortfolioContainer portfolioContainer, Map<String, Double> sharePrices) {
        this.portfolioContainer = portfolioContainer;
        this.sharePrices = sharePrices;
    }

    @Override
    public void run() {
        while (true) {
            try {
            Set<String> keySet = new HashSet<>(sharePrices.keySet());
            for (String key : keySet) {
                portfolioContainer.setSharePrice(key,Double.valueOf(StrathQuoteServer.getLastValue(key)));
            }
                portfolioContainer.updateShareValues();
            System.out.println(Thread.currentThread().getId() + "   Updating values...");

                sleep(10000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
