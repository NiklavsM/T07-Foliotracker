package Model;

public interface IQuoteServer {
    String getLastValue(String tickerSymbol)throws WebsiteDataException, NoSuchTickerException;
}
