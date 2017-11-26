package Model;


import java.io.IOException;
import java.util.Map;

public interface IPortfolioContainer {
    Map<String, IPortfolio> getPortfolios();

    boolean addPortfolio(String portfolio);

    boolean containsPortfolio(String name);

    boolean deletePortfolio(String portfolioName);

    boolean loadFromFile(String filePath) throws IOException, ClassNotFoundException;

    void saveToFile(String filePath) throws IOException;

 //   void setChangedAndNotify();

}
