package View;

import Controller.PortfolioContainerListener;
import Model.IPortfolioContainer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class PortfolioContainerView implements Observer, IPortfolioContainerView {

    private JFrame mainFrame;
    private JTabbedPane tabs;

    private JButton closeButton;
    private JButton deleteButton;

    private IPortfolioContainer portfolioContainer;
    private List<String> closedTabs = new ArrayList<>();

    public PortfolioContainerView(IPortfolioContainer portfolioContainer) {
        this.portfolioContainer = portfolioContainer;
        mainFrame = new JFrame();
        mainFrame.setTitle("FolioTracker");

        JPanel mainPanel = new JPanel(new CardLayout());

        setComponentsWithActionListener();
        setClose();
        tabs = new JTabbedPane();

        mainPanel.add(tabs);
        mainFrame.add(mainPanel);
        mainFrame.setBounds(500, 200, 825, 710);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void setComponentsWithActionListener() {
        ActionListener mainViewController = new PortfolioContainerListener(this, portfolioContainer);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setName("menuBar");
        JMenu optionsMenu = new JMenu("Options");
        optionsMenu.setMnemonic(KeyEvent.VK_M);
        menuBar.add(optionsMenu);

        JMenuItem createNew = new JMenuItem("Create New");
        createNew.addActionListener(mainViewController);
        createNew.setMnemonic(KeyEvent.VK_N);
        createNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.ALT_MASK));
        optionsMenu.add(createNew);

        JMenuItem openNew = new JMenuItem("Open");
        openNew.setMnemonic(KeyEvent.VK_O);
        openNew.addActionListener(mainViewController);
        openNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.ALT_MASK));
        optionsMenu.add(openNew);

        JMenuItem saveNew = new JMenuItem("Save");
        saveNew.setMnemonic(KeyEvent.VK_S);
        saveNew.addActionListener(mainViewController);
        saveNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.ALT_MASK));
        optionsMenu.add(saveNew);

        JMenuItem loadFromFile = new JMenuItem("Load");
        loadFromFile.setMnemonic(KeyEvent.VK_L);
        loadFromFile.addActionListener(mainViewController);
        loadFromFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.ALT_MASK));
        optionsMenu.add(loadFromFile);

        JMenuItem exitApp = new JMenuItem("Exit");
        exitApp.setMnemonic(KeyEvent.VK_X);
        exitApp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.ALT_MASK));
        exitApp.addActionListener(e -> mainFrame.dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING)));
        optionsMenu.add(exitApp);

        deleteButton = new JButton("Delete");
        deleteButton.setBounds(400, 580, 120, 20);
        deleteButton.setVisible(false);
        deleteButton.addActionListener(mainViewController);
        mainFrame.add(deleteButton);

        mainFrame.setJMenuBar(menuBar);

    }

    public void emptyTabs() {
        tabs.removeAll();
        closedTabs.clear();
        enableActionButtons(false);

    }

    public List<String> getClosedTabs() {
        return closedTabs;
    }

    private void setClose() {
        closeButton = new JButton("Close");
        closeButton.setBounds(250, 580, 120, 20);
        closeButton.addActionListener(e -> closeTab());
        closeButton.setVisible(false);
        mainFrame.add(closeButton);
    }

    public JTabbedPane getTabs() {
        return tabs;
    }

    public String getPortfolioNamePopup(Object[] selectionValues, String title) {
        return (String) JOptionPane.showInputDialog(
                mainFrame,
                "Portfolio name:",
                title,
                JOptionPane.PLAIN_MESSAGE,
                null,
                selectionValues, null);
    }

    public int conformationPopup(String message, String title) {
        //Delete confirmation
        return JOptionPane.showConfirmDialog(mainFrame, message, title, JOptionPane.YES_NO_OPTION);
    }

    public void popupErrorMessage(String errorText) {
        JOptionPane.showMessageDialog(mainFrame, errorText);
    }

    private void enableActionButtons(boolean enable) {
        closeButton.setVisible(enable);
        deleteButton.setVisible(enable);
    }

    private void closeTab() {
        closedTabs.add(tabs.getTitleAt(tabs.getSelectedIndex()));
        tabs.remove(getTabs().getSelectedIndex());
        if (tabs.getSelectedIndex() == -1) {
            closeButton.setVisible(false);
            deleteButton.setVisible(false);
        }
    }

    private boolean tabIsOpened(String name) {
        int tabCount = tabs.getTabCount();
        for (int i = 0; i < tabCount; i++) {
            if (tabs.getTitleAt(i).equals(name))
                return true;
        }
        return false;
    }

    private void addPortfolioTab() {

        for (String portfolio : portfolioContainer.getPortfolios().keySet()) {
            if (!tabIsOpened(portfolio) && !closedTabs.contains(portfolio)) {
                PortfolioPanel newTab = new PortfolioPanel(portfolioContainer.getPortfolios().get(portfolio));
                portfolioContainer.getPortfolios().get(portfolio).addObserver(newTab);
                tabs.addTab(portfolio, newTab);
                tabs.setSelectedIndex(tabs.getTabCount() - 1);
            }
        }
    }

    private void removeDeadTabs() {
        int tabCount = tabs.getTabCount();
        for (int i = 0; i < tabCount; i++) {
            if (!portfolioContainer.containsPortfolio(tabs.getTitleAt(i))) {
                tabs.remove(i);
                tabCount = tabs.getTabCount();
            }

        }
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("tabs.getTabCount() " + tabs.getTabCount());
        System.out.println("portfolioContainer.getPortfolioList().size() " + portfolioContainer.getPortfolios().size());
        if (portfolioContainer.getPortfolios().size() > tabs.getTabCount()) {
            System.out.println("Gets here11");
            addPortfolioTab();
        }

        removeDeadTabs();
        enableActionButtons(portfolioContainer.getPortfolios().size() > 0 && tabs.getTabCount() > 0);

    }
}
