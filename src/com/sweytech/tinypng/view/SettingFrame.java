package com.sweytech.tinypng.view;

import javax.swing.*;
import java.awt.*;

/**
 * Setting
 * Created by arjinmc on 2018/5/23
 * Email: arjinmc@hotmail.com
 */
public class SettingFrame extends JFrame {

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
        mPanel1.setLayout(new BorderLayout(100,10));
        mPanel1.add(mTxtTitle,BorderLayout.NORTH);
        mPanel1.add(mTxtKeyLicense,BorderLayout.SOUTH);

        mPanel2 = new JPanel();
        mBtnOK = new JButton();
        mBtnOK.setText("OK");
        mPanel2.add(mBtnOK);

        mBtnOK.addActionListener(e -> {
            setVisible(false);
        });

        add(mPanel1, BorderLayout.CENTER);
        add(mPanel2, BorderLayout.SOUTH);

        this.setTitle("Setting");
        this.setSize(400, 110);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

    }

}
