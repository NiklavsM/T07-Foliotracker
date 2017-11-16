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

public class MainView implements Observer,IMainView {

    JFrame mainFrame;
    JPanel mainPanel;
    JTabbedPane tabs;
    JMenuBar menuBar;
    JMenu optionsMenu;
    JMenuItem createNew;
    JMenuItem openNew;
    JMenuItem exitApp;
    JButton refreshPrice;
    JLabel currentPriceTest;
    List<PortfolioController> portfolioControllers = new ArrayList<>();

    public MainView(IPortfolioContainer pc) {
        mainFrame = new JFrame();
        mainFrame.setTitle("FolioTracker");

        mainPanel = new JPanel(new CardLayout());

        refreshPrice = new JButton("Refresh price");

        initializeMenuBar();
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
        exitApp.addActionListener(e->{
            mainFrame.dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING));
        });
        optionsMenu.add(exitApp);
        mainFrame.setJMenuBar(menuBar);

    }

//    public void addTab(String name) {
//        PortfolioPanel newTab = new PortfolioPanel();
//        tabs.addTab(name, newTab);
//    }

    public JFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(JFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public JTabbedPane getTabs() {
        return tabs;
    }

    public void setTabs(JTabbedPane tabs) {
        this.tabs = tabs;
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }

    public void setMenuBar(JMenuBar menuBar) {
        this.menuBar = menuBar;
    }

    public JMenu getOptionsMenu() {
        return optionsMenu;
    }

    public void setOptionsMenu(JMenu optionsMenu) {
        this.optionsMenu = optionsMenu;
    }

    public JMenuItem getCreateNew() {
        return createNew;
    }

    public void setCreateNew(JMenuItem createNew) {
        this.createNew = createNew;
    }

    public JMenuItem getOpenNew() {
        return openNew;
    }

    public void setOpenNew(JMenuItem openNew) {
        this.openNew = openNew;
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

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof IPortfolio) {
            PortfolioPanel newTab = new PortfolioPanel(tabs);
            tabs.addTab(((IPortfolio) arg).getName(), newTab);
            newTab.setParentTabPane(tabs);
            System.out.println("TEST2.08");
            ((IPortfolio) arg).addObserver(newTab);
            PortfolioController portfolioController = new PortfolioController(newTab, (IPortfolio) arg);
            portfolioControllers.add(portfolioController);
        }
//        if (arg instanceof IPortfolioContainer) {
//            for (IPortfolio portfolio : ((IPortfolioContainer)arg).getPortfolioList()) {
//                PortfolioPanel newTab = new PortfolioPanel();
//                tabs.addTab(portfolio.getName(), newTab);
//                System.out.println("TEST2.08");
//                PortfolioController portfolioController = new PortfolioController(newTab,portfolio);
//                portfolioControllers.add(portfolioController);
//            }
//        }
    }
}
