package View;

import javax.swing.*;
import java.awt.*;

public interface IPortfolioPanel {

    AbstractButton getAddButton();

    TextField getBuyTickerName();

    TextField getBuyShareAmount();

    AbstractButton getSellButton();

    TextField getSellTickerName();

    TextField getSellTickerShareAmount();

    void popupErrorMessage(String s);

}
