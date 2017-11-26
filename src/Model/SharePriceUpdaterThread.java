package Model;

import java.util.Map;

import static java.lang.Thread.sleep;

public class SharePriceUpdaterThread implements Runnable {


    private PortfolioContainer portfolioContainer;
    private Map<String, Double> sharePrices;

    SharePriceUpdaterThread(PortfolioContainer portfolioContainer, Map<String, Double> sharePrices) {
        this.portfolioContainer = portfolioContainer;
        this.sharePrices = sharePrices;
    }

    @Override
    public void run() {
        while (true) {
            try {
                for (String key : sharePrices.keySet()) {
                    portfolioContainer.setSharePrice(key, Double.valueOf(StrathQuoteServer.getLastValue(key)));
                }
                portfolioContainer.updateShareValues();
                sleep(10000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
