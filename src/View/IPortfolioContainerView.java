package View;

import javax.swing.*;
import java.util.List;
import java.util.Observable;

public interface IPortfolioContainerView {

    JTabbedPane getTabs();

    void popupMessage(String errorText);

    String getPortfolioNamePopup(Object[] selectionValues, String title);

    List<String> getClosedTabs();

    void update(Observable o, Object arg);

    void emptyTabs();

    boolean conformationPopup(String message, String title);

    String getOpenFilePath();

    String getSaveFilePath();

}
