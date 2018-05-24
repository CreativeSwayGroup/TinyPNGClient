package com.sweytech.tinypng.view;

import javax.swing.*;
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

        initMenuBar();

        this.setTitle("TinyPNG Client v1.0");
        this.setSize(400, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

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
        aboutMenuItem.setText("About TinyPNG Client");
        aboutMenuItem.addActionListener(e -> showAbout());

        aboutMenu.add(sourceMenuItem);
        aboutMenu.add(tinyPNGMenuItem);
        aboutMenu.add(aboutMenuItem);

        //// about menu end    /////

        menuBar.add(settingMenu);
        menuBar.add(aboutMenu);
        setJMenuBar(menuBar);
    }

    /**
     * show about dialog
     */
    private void showAbout() {
        JOptionPane.showMessageDialog(null, "TinyPNG Client v1.0" + "\n" +
                "Copyright©2018 Creative Sway Group");
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
}
