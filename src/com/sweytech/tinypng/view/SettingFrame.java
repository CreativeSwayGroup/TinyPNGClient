package com.sweytech.tinypng.view;

import com.sun.istack.internal.Nullable;
import com.sweytech.tinypng.Entrance;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.net.URLDecoder;

/**
 * Setting
 * Created by arjinmc on 2018/5/23
 * Email: arjinmc@hotmail.com
 */
public class SettingFrame extends JDialog {

    private JLabel mTxtTitle;
    private JTextField mTxtKeyLicense;
    private JPanel mPanel1, mPanel2;
    private JButton mBtnOK;

    public SettingFrame() {
        init();
    }

    private void init() {
        mTxtTitle = new JLabel();
        mTxtTitle.setText("Enter your TinyPNG API Key:");
//        mTxtTitle.setBounds(50, 20, 200, 30);

        mTxtKeyLicense = new JTextField();
//        mTxtKeyLicense.setBounds(48, 55, 300, 30);

        mPanel1 = new JPanel();
        mPanel1.setLayout(new GridLayout(2, 1, 0, 10));
        mPanel1.setBorder(new EmptyBorder(10, 10, 10, 10));
        mPanel1.add(mTxtTitle, BorderLayout.NORTH);
        mPanel1.add(mTxtKeyLicense, BorderLayout.SOUTH);

        mPanel2 = new JPanel();
        mBtnOK = new JButton();
        mBtnOK.setText("OK");
        mPanel2.add(mBtnOK);

        mBtnOK.addActionListener(e -> {
            String input = mTxtKeyLicense.getText();
            if (input.isEmpty()) {
                showDialog("Please Enter your TinyPNG API Key");
                return;
            }

            if (createAndWriteConfig(input)) {
                setVisible(false);
                showDialog("config success");
            } else {
                showDialog("config failed");
            }
        });

        add(mPanel1, BorderLayout.CENTER);
        add(mPanel2, BorderLayout.SOUTH);

        this.setTitle("Setting");
        this.setMinimumSize(new Dimension(420, 140));
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setModalityType(ModalityType.APPLICATION_MODAL);
    }

    /**
     * get saved api key
     */
    @Nullable
    public static String getApiKey() {
        File file = new File(getProjectPath() + "/TinyPNGClient.config");
        if (file.exists()) {
            FileReader fileReader = null;
            try {
                StringBuilder key = new StringBuilder();
                fileReader = new FileReader(file);
                char[] buf = new char[1024];
                int num = 0;
                while ((num = fileReader.read(buf)) != -1) {
                    key.append(buf, 0, num);
                }
                return key.toString();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    /**
     * create new file(with jar path) if not exists and write string into
     *
     * @param key
     * @return
     */
    private boolean createAndWriteConfig(String key) {
        File file = new File(getProjectPath() + "/TinyPNGClient.config");
        boolean flag = true;
        if (!file.exists()) {
            try {
                flag = file.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        if (flag) {
            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(file, false);
                fileWriter.write(key);
                fileWriter.flush();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    /**
     * get code in path
     */
    @Nullable
    private static String getProjectPath() {
        URL url = Entrance.class.getProtectionDomain().getCodeSource().getLocation();
        String filePath = null;
        try {
            filePath = URLDecoder.decode(url.getPath(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (filePath != null) {
            if (filePath.endsWith(".jar")) {
                filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1);
            }
            File file = new File(filePath);
            filePath = file.getAbsolutePath();
            return filePath;
        }

        return null;
    }

    /**
     * show dialog
     */
    private void showDialog(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}