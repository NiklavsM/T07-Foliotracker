package Model;


import java.util.ArrayList;
import java.util.List;
import java.util.Observable;


public class PortfolioContainer extends Observable implements IPortfolioContainer {
    private List<Portfolio> portfolioList = new ArrayList<>();

    public List<Portfolio> getPortfolioList() {
        return new ArrayList<>(portfolioList);
    }

    /**
     * @requires: portfolio != null
     * @effects: returns true if portfolio is added else return false.
     */
    public boolean addToPortfolioList(String portfolio) {
        Portfolio pf = getPortfolioBytName(portfolio);
        if (pf == null) {
            Portfolio newPortfolio1 = new Portfolio(portfolio);
            portfolioList.add(newPortfolio1);
            setChangedAndNotify();
            return true;
        }
        return false;
    }

    /**
     * @requires: name != null
     * @effects: returns portfolio if portfolio's Name == name else return null.
     */
    private Portfolio getPortfolioBytName(String name) {
        for (Portfolio portfolio : portfolioList) {
            if (portfolio.getName().equals(name)) {
                return portfolio;
            }

        }
        return null;
    }

    private void setChangedAndNotify() {
        setChanged();
        notifyObservers();
    }

    /**
     * @requires: name != null
     * @effects: returns true if the portfolio is removed from the List else return false.
     */
    public boolean deletePortfolio(String name) {
        Portfolio portToDel = getPortfolioBytName(name);
        if (portToDel != null) {
            portfolioList.remove(portToDel);
            setChangedAndNotify();
            return true;
        }
        return false;
    }

    /**
     * @requires: name != null
     * @effects: returns true if the List contains this portfolio else return false.
     */
    public boolean containsPortfolio(String name)
    {
        return getPortfolioBytName(name) != null;
    }

    /**
     * @effects: returns the name of each portfolio in the portfolioList.
     */
    public String[] getPortfolioNames() {
        String[] portfolioNames = new String[portfolioList.size()];
        int i = 0;
        for (Portfolio portfolio : portfolioList) {
            portfolioNames[i] = portfolio.getName();
            i++;
        }
        return portfolioNames;
    }

}
