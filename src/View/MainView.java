package View;

import Controller.PortfolioController;
import Model.IPortfolio;
import Model.IPortfolioContainer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MainView implements Observer, IMainView {

    private JFrame mainFrame;
    private JPanel mainPanel;
    private JTabbedPane tabs;
    private JMenuBar menuBar;
    private JMenu optionsMenu;
    private JMenuItem createNew;
    private JMenuItem openNew;
    private JMenuItem exitApp;
    private JButton refreshPrice;
    private JLabel currentPriceTest;
    private JButton closeButton;
    private JButton deleteButton;
    private List<PortfolioController> portfolioControllers = new ArrayList<>();
    private int tabIndex = 0;

    public MainView() {
        mainFrame = new JFrame();
        mainFrame.setTitle("FolioTracker");

        mainPanel = new JPanel(new CardLayout());

        refreshPrice = new JButton("Refresh price");

        initializeMenuBar();
        setDeleteAndClose();
        tabs = new JTabbedPane();

        currentPriceTest = new JLabel("No price");// just testing delete latter

        mainPanel.add(tabs);
        mainPanel.add(refreshPrice);
        mainFrame.add(mainPanel);
        mainFrame.setBounds(500, 200, 825, 710);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeMenuBar() {
        menuBar = new JMenuBar();
        menuBar.setName("menuBar");
        optionsMenu = new JMenu("Options");
        menuBar.add(optionsMenu);
        createNew = new JMenuItem("Create New");
        optionsMenu.add(createNew);
        openNew = new JMenuItem("Open");
        optionsMenu.add(openNew);
        exitApp = new JMenuItem("Exit");
        exitApp.setMnemonic(KeyEvent.VK_X);
        exitApp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.ALT_MASK));
        exitApp.addActionListener(e -> {
            mainFrame.dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING));
        });
        optionsMenu.add(exitApp);
        mainFrame.setJMenuBar(menuBar);

    }
    private void setDeleteAndClose(){
        closeButton = new JButton("Close");
        closeButton.setBounds(250, 580, 120, 20);
        closeButton.setVisible(false);
        deleteButton = new JButton("Delete");
        deleteButton.setBounds(400, 580, 120, 20);
        deleteButton.setVisible(false);
        mainFrame.add(deleteButton);
        mainFrame.add(closeButton);
    }
//    public void addTab(String name) {
//        PortfolioPanel newTab = new PortfolioPanel();
//        tabs.addTab(name, newTab);
//    }

    public JFrame getMainFrame() {
        return mainFrame;
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


    public JMenu getOptionsMenu() {
        return optionsMenu;
    }


    public JMenuItem getCreateNew() {
        return createNew;
    }


    public JMenuItem getOpenNew() {
        return openNew;
    }


    public JButton getRefreshPrice() {
        return refreshPrice;
    }

    public void setRefreshPrice(JButton refreshPrice) {
        this.refreshPrice = refreshPrice;
    }

    public JLabel getCurrentPriceTest() {
        return currentPriceTest;
    }
    public String getPortfolioNamePopup(Object[] selectionValues){
        return (String) JOptionPane.showInputDialog(
                mainFrame,
                "Portfolio name:",
                "New Portfolio",
                JOptionPane.PLAIN_MESSAGE,
                null,
                selectionValues, null);
    }

    public void popupErrorMessage(String errorText){
        JOptionPane.showMessageDialog(mainFrame, errorText);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof IPortfolio) {
            PortfolioPanel newTab = new PortfolioPanel();
            tabs.addTab(((IPortfolio) arg).getName(), newTab);
            tabs.setSelectedComponent(newTab);
            closeButton.setVisible(true);
            deleteButton.setVisible(true);
            ((IPortfolio) arg).addObserver(newTab);
            PortfolioController portfolioController = new PortfolioController(newTab, (IPortfolio) arg);
            portfolioControllers.add(portfolioController);
        }
    }
}
