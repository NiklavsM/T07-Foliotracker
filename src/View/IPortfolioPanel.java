package View;

import javax.swing.*;
import java.awt.*;

public interface IPortfolioPanel {

    TextField getAddShareTickerSymbol();

    TextField getAddShareName();

    TextField getBuySellTickerSymbol();

    TextField getBuySellShareAmount();

    void popupErrorMessage(String s);

}
