package com.sweytech.tinypng.view;

import com.sweytech.tinypng.util.TinyPNGManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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

        mTxtKeyLicense = new JTextField();
        mTxtKeyLicense.setText(TinyPNGManager.getApiKey());
        mTxtKeyLicense.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateOkBtnStatus();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateOkBtnStatus();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateOkBtnStatus();
            }
        });

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
            if (createAndWriteConfig(input)) {
                setVisible(false);
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
     * create new file(with jar path) if not exists and write string into
     *
     * @param key
     * @return
     */
    private boolean createAndWriteConfig(String key) {
        File file = new File(TinyPNGManager.getProjectPath() + "/TinyPNGClient.config");
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
     * show dialog
     */
    private void showDialog(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    /**
     * update ok button clickable
     */
    private void updateOkBtnStatus() {
        String licenseStr = mTxtKeyLicense.getText();
        if (licenseStr == null || licenseStr.trim().length() == 0) {
            mBtnOK.setEnabled(false);
        } else {
            mBtnOK.setEnabled(true);
        }
    }
}