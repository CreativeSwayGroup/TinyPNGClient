package com.sweytech.tinypng.view;

import com.sweytech.tinypng.util.PropertiesManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Main
 * Created by arjinmc on 2018/5/23
 * Email: arjinmc@hotmail.com
 */
public class MainFrame extends JFrame {


    private SettingFrame settingFrame;

    public MainFrame() {
        init();
    }

    public void init() {

        this.setIconImage(getLogoImage());
        this.setTitle(PropertiesManager.getAppName() + " " + PropertiesManager.getVersionName());
        this.setSize(400, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        initMenuBar();
        initWorkSpace();
        initLogSpace();

    }

    /**
     * init menu bar
     */
    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu settingMenu = new JMenu();
        settingMenu.setText("Setting");

        JMenuItem apiKeyMenuItem = new JMenuItem();
        apiKeyMenuItem.setText("API Key");
        apiKeyMenuItem.addActionListener(e -> {
            if (settingFrame == null) {
                settingFrame = new SettingFrame();
            }
            settingFrame.setVisible(true);
        });

        settingMenu.add(apiKeyMenuItem);

        //// about menu start     /////

        JMenu aboutMenu = new JMenu();
        aboutMenu.setText("About");

        JMenuItem sourceMenuItem = new JMenuItem();
        sourceMenuItem.setText("Source");
        sourceMenuItem.addActionListener(e -> {
            openBrowser("https://github.com/CreativeSwayGroup/TinyPNGClient");
        });

        JMenuItem tinyPNGMenuItem = new JMenuItem();
        tinyPNGMenuItem.setText("About TinyPNG");
        tinyPNGMenuItem.addActionListener(e -> {
            openBrowser("https://tinypng.com/");
        });

        JMenuItem aboutMenuItem = new JMenuItem();
        aboutMenuItem.setText("About " + PropertiesManager.getAppName());
        aboutMenuItem.addActionListener(e -> showAbout());

        aboutMenu.add(sourceMenuItem);
        aboutMenu.add(tinyPNGMenuItem);
        aboutMenu.add(aboutMenuItem);

        //// about menu end    /////

        menuBar.add(settingMenu);
        menuBar.add(aboutMenu);
        setJMenuBar(menuBar);
    }

    private void initWorkSpace() {

        JLabel label = new JLabel();
        label.setBounds(0, 0, 400, 200);
        label.setText("Drop your images here");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);

        getContentPane().add(label);
    }

    private void initLogSpace() {

        JLabel logLabel = new JLabel("  Log:");
        logLabel.setBounds(0, 200, 400, 15);
        logLabel.setBackground(Color.BLACK);
        logLabel.setForeground(Color.WHITE);
        logLabel.setOpaque(true);
        logLabel.setHorizontalTextPosition(10);

        JTextArea textArea = new JTextArea();
        textArea.setBounds(0, 215, 400, 300);
        textArea.setEnabled(false);

        getContentPane().add(logLabel);
        getContentPane().add(textArea);
    }

    /**
     * show about dialog
     */
    private void showAbout() {
        JOptionPane.showMessageDialog(null, PropertiesManager.getAppName()
                        + "\t" + PropertiesManager.getVersionName() + "\n" +
                        "Copyright©2018 Creative Sway Group", "About", JOptionPane.PLAIN_MESSAGE
                , new ImageIcon(getLogoImage()));
    }

    /**
     * open browser
     *
     * @param url
     */
    private void openBrowser(String url) {
        try {
            URI uri = new URI(url);
            java.awt.Desktop.getDesktop().browse(uri);
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private Image getLogoImage() {
        return Toolkit.getDefaultToolkit().getImage("resource/ic_logo.png");
    }
}
