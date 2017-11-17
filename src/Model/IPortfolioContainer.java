package Model;


import java.util.List;
import java.util.Observer;

public interface IPortfolioContainer {
    List<Portfolio> getPortfolioList();

    void addToPortfolioList(String portfolio);
    //IPortfolio getPortfolioBytName(String name);
    boolean containsPortfolio(String name);
    String[] getPortfolioNames();
    void deletePortfolio(String portfolioName);//change to String as i thnk it wont delete it
}
