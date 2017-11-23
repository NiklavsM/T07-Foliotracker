package View;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Observable;

public interface IMainView {

    JTabbedPane getTabs();

    void popupErrorMessage(String errorText);

    String getPortfolioNamePopup(Object[] selectionValues, String title);

    List<String> getClosedTabs();

    void update(Observable o, Object arg);
}
