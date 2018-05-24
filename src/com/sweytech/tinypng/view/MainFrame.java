package com.sweytech.tinypng.view;

import com.sweytech.tinypng.util.PropertiesManager;
import com.sweytech.tinypng.util.TinyPNGManager;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        TinyPNGManager.init();
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
        label.setText("Drag your images here");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        new DropTarget(label, DnDConstants.ACTION_COPY_OR_MOVE, new DropTargetAdapter() {
            @Override
            public void drop(DropTargetDropEvent dtde) {
                try {
                    if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                        dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);//接收拖拽来的数据
                        String result = dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor).toString();
                        List<String> dragList = parseDragList(result);
                        TinyPNGManager.compressImages(dragList);

                        dtde.dropComplete(true);
                    } else {
                        dtde.rejectDrop();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

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

    /**
     * parse and filter image files
     *
     * @param dragString
     * @return
     */
    private List<String> parseDragList(String dragString) {
        String data = dragString.substring(1, dragString.length() - 1);
        if (data == null || data.length() == 0) {
            return null;
        }
        String[] items = data.split(",");
        List<String> list = new ArrayList<>(Arrays.asList(items));
        int listSize = list.size();
        if (listSize == 0) {
            return null;
        }
        for (int i = listSize - 1; i > 0; i--) {
            String fileName = list.get(i).toLowerCase();
            if (!(fileName.endsWith("png") || fileName.endsWith("jpg") || fileName.endsWith("jpeg"))) {
                list.remove(i);
            }
        }
        return list;
    }
}
