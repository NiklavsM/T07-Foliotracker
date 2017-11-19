package Model;


import java.util.List;

public interface IPortfolioContainer {
    List<Portfolio> getPortfolioList();

    boolean addToPortfolioList(String portfolio);

    //IPortfolio getPortfolioBytName(String name);
    boolean containsPortfolio(String name);

    String[] getPortfolioNames();

    boolean deletePortfolio(String portfolioName);
}
