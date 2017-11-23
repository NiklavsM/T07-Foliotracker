package View;

import Controller.MainViewController;
import Controller.PortfolioController;
import Model.IPortfolio;
import Model.IPortfolioContainer;
import Model.Portfolio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;

public class MainView implements Observer, IMainView {

    private JFrame mainFrame;
    private JPanel mainPanel;
    private JTabbedPane tabs;
    private JMenuBar menuBar;
    private JMenu optionsMenu;
    private JMenuItem createNew;
    private JMenuItem openNew;
    private JMenuItem saveNew;
    private JMenuItem loadFromFile;

    private JMenuItem exitApp;
    private JButton closeButton;
    private JButton deleteButton;

    private IPortfolioContainer portfolioContainer;
    private List<String> closedTabs = new ArrayList<>();

    public MainView(IPortfolioContainer portfolioContainer) {
        this.portfolioContainer = portfolioContainer;
        mainFrame = new JFrame();
        mainFrame.setTitle("FolioTracker");

        mainPanel = new JPanel(new CardLayout());

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
        ActionListener mainViewController = new MainViewController(this, portfolioContainer);

        menuBar = new JMenuBar();
        menuBar.setName("menuBar");
        optionsMenu = new JMenu("Options");
        optionsMenu.setMnemonic(KeyEvent.VK_M);
        menuBar.add(optionsMenu);

        createNew = new JMenuItem("Create New");
        createNew.addActionListener(mainViewController);
        createNew.setMnemonic(KeyEvent.VK_N);
        createNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
        optionsMenu.add(createNew);

        openNew = new JMenuItem("Open");
        openNew.setMnemonic(KeyEvent.VK_O);
        openNew.addActionListener(mainViewController);
        openNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.ALT_MASK));
        optionsMenu.add(openNew);

        saveNew = new JMenuItem("Save");
        saveNew.setMnemonic(KeyEvent.VK_S);
        saveNew.addActionListener(mainViewController);
        saveNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
        optionsMenu.add(saveNew);

        loadFromFile = new JMenuItem("Load");
        loadFromFile.setMnemonic(KeyEvent.VK_L);
        loadFromFile.addActionListener(mainViewController);
        loadFromFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK));
        optionsMenu.add(loadFromFile);

        exitApp = new JMenuItem("Exit");
        exitApp.setMnemonic(KeyEvent.VK_X);
        exitApp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.ALT_MASK));
        exitApp.addActionListener(e -> {
            mainFrame.dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING));
        });
        optionsMenu.add(exitApp);

        deleteButton = new JButton("Delete");
        deleteButton.setBounds(400, 580, 120, 20);
        deleteButton.setVisible(false);
        deleteButton.addActionListener(mainViewController);
        mainFrame.add(deleteButton);

        mainFrame.setJMenuBar(menuBar);

    }

    public List<String> getClosedTabs() {
        return closedTabs;
    }

    private void setClose() {
        closeButton = new JButton("Close");
        closeButton.setBounds(250, 580, 120, 20);
        closeButton.addActionListener(e -> {
            closeTab();
        });
        closeButton.setVisible(false);
        mainFrame.add(closeButton);
    }

    public JTabbedPane getTabs() {
        return tabs;
    }

    public JButton getCloseButton() {
        return closeButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public String getPortfolioNamePopup(Object[] selectionValues,String title) {
        return (String) JOptionPane.showInputDialog(
                mainFrame,
                "Portfolio name:",
                title,
                JOptionPane.PLAIN_MESSAGE,
                null,
                selectionValues, null);
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
            getCloseButton().setVisible(false);
            getDeleteButton().setVisible(false);
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
        for (IPortfolio portfolio : portfolioContainer.getPortfolioList()) {
            if (!tabIsOpened(portfolio.getName()) && !closedTabs.contains(portfolio.getName())) {
                PortfolioPanel newTab = new PortfolioPanel(portfolio);
                portfolio.addObserver(newTab);
                tabs.addTab(portfolio.getName(), newTab);
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
        System.out.println("portfolioContainer.getPortfolioList().size() " + portfolioContainer.getPortfolioList().size());
        if (portfolioContainer.getPortfolioList().size() > tabs.getTabCount()) {
            System.out.println("Gets here11");
            addPortfolioTab();
        }

        removeDeadTabs();
        enableActionButtons(portfolioContainer.getPortfolioList().size() > 0);

    }
}
