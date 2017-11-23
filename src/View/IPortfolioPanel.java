package View;

import javax.swing.*;
import java.awt.*;

public interface IPortfolioPanel {

    TextField getBuyTickerName();

    TextField getBuyShareAmount();

    TextField getSellTickerName();

    TextField getSellTickerShareAmount();

    void popupErrorMessage(String s);

}
