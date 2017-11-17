package Model;


import java.util.ArrayList;
import java.util.List;
import java.util.Observable;


public class PortfolioContainer extends Observable implements IPortfolioContainer {
    private List<Portfolio> portfolioList = new ArrayList<>();

    public List<Portfolio> getPortfolioList() {
        return new ArrayList<>(portfolioList);
    }

    public void addToPortfolioList(String portfolio) {
        Portfolio pf = getPortfolioBytName(portfolio);
        setChanged();
        if (pf == null) {
            Portfolio newPortfolio1 = new Portfolio(portfolio);
            portfolioList.add(newPortfolio1);
            notifyObservers(newPortfolio1);
        }else{
            notifyObservers(pf);
        }
        System.out.println("Gets here");
    }

    private Portfolio getPortfolioBytName(String name) {
        for (Portfolio portfolio : portfolioList) {
            if (portfolio.getName().equals(name)) {
                return portfolio;
            }

        }
        return null;
    }

    public void deletePortfolio(String name) {
        Portfolio portToDel = getPortfolioBytName(name);
        if (portToDel != null) {
            portfolioList.remove(portToDel);
        }

    }

    public boolean containsPortfolio(String name) {
        return getPortfolioBytName(name) != null;
    }

    public String[] getPortfolioNames(){
        String[] portfolioNames = new String[portfolioList.size()];
        int i = 0;
        for(Portfolio portfolio : portfolioList){
            portfolioNames[i]= portfolio.getName();
            i++;
        }
        return  portfolioNames;
    }

}
