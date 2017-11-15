package Model;


import java.util.List;
import java.util.Observer;

public interface IPortfolioContainer {
    List<Portfolio> getPortfolioList();

    void addToPortfolioList(String portfolio);

    void addObserver(Observer o);
}
