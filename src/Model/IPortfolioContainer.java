package Model;


import java.util.Map;

public interface IPortfolioContainer {
    Map<String, IPortfolio> getPortfolios();

    boolean addPortfolio(String portfolio);

    boolean containsPortfolio(String name);

    boolean deletePortfolio(String portfolioName);

    boolean loadFromFile();

    void saveToFile();
}
