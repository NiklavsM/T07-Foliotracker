package View;

import javax.swing.*;
import java.awt.*;

public class MainView {

    JFrame mainFrame;
    JPanel mainPanel;
    JTabbedPane tabs;
    JMenuBar menuBar;
    JMenu optionsMenu;
    JMenuItem createNew;
    JMenuItem openNew;
    JButton refreshPrice;
    JLabel currentPriceTest;

    public MainView() {
        mainFrame = new JFrame();
        mainPanel = new JPanel(new CardLayout());
        refreshPrice = new JButton("Refresh price");

        initializeMenuBar();
        tabs = new JTabbedPane();

        currentPriceTest = new JLabel("No price");// just testing delete latter

        addTab("Portfolio 1");
        mainPanel.add(tabs);
        mainPanel.add(refreshPrice);
        mainFrame.add(mainPanel);
        mainFrame.setBounds(500, 200, 825, 710);
        mainFrame.setVisible(true);
    }

    private void initializeMenuBar() {
        menuBar = new JMenuBar();
        menuBar.setName("menuBar");
        optionsMenu = new JMenu("Options");
        menuBar.add(optionsMenu);
        createNew = new JMenuItem("Create New");
        createNew.addActionListener(e -> {
            String s = (String) JOptionPane.showInputDialog(
                    mainFrame,
                    "Portfolio name:",
                    "New Portfolio",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null, null);

            if ((s != null) && (s.length() > 0)) {
                addTab(s);
                return;
            }
        });
        optionsMenu.add(createNew);
        openNew = new JMenuItem("Open");
        optionsMenu.add(openNew);
        mainFrame.setJMenuBar(menuBar);

    }

    public void addTab(String name) {
        PortfolioPanel newTab = new PortfolioPanel();
        tabs.addTab(name, newTab);
    }

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

}
