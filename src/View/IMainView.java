package View;

import javax.swing.*;
import java.awt.*;

public interface IMainView {
    
    AbstractButton getCreateNew();
    AbstractButton getOpenNew();

    Component getMainFrame();

    AbstractButton getCloseButton();

    AbstractButton getDeleteButton();
    JTabbedPane getTabs();

    void popupErrorMessage(String errorText);
    String getPortfolioNamePopup(Object[] selectionValues);
}
