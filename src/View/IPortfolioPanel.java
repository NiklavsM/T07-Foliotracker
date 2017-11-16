package View;

import javax.swing.*;
import java.awt.*;

public interface IPortfolioPanel {

    AbstractButton getAddButton();

    TextField getInputTickerName();

    TextField getInputShareAmount();

    AbstractButton getSellButton();

    TextField getSellTickerName();

    TextField getSellTickerShareAmount();

    void popupErrorMessage(String s);
}
