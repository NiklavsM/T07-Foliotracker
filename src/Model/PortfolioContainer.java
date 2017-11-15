package Model;


import java.util.ArrayList;
import java.util.List;
import java.util.Observable;


public class PortfolioContainer extends Observable implements IPortfolioContainer {
    List<Portfolio> portfolioList = new ArrayList<>();

    public List<Portfolio> getPortfolioList() {
        return new ArrayList<>(portfolioList);
    }

    public void addToPortfolioList(String portfolio) {
        Portfolio newPortfolio1 = new Portfolio(portfolio);
        portfolioList.add(newPortfolio1);
        setChanged();
        notifyObservers(this);
        System.out.println("Gets here");
    }


    public String[][] getData() {

        String[][] portfolioDataArray = new String[portfolioList.size()][4];
        for (int i = 0; i < portfolioList.size(); i++) {
           // portfolioDataArray[i][0] = portfolioList.get(i).ge
        }

        return null;
    }


}
