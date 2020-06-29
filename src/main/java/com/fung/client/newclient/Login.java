package com.fung.client.newclient;

import com.fung.client.newclient.messagehandle.WriteMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author skytrc@163.com
 * @date 2020/6/23 15:09
 */
public class Login extends JFrame implements ActionListener {

    private static Login login;

    private WriteMessage writeMessage = WriteMessage.getInstance();

    public static Login getInstance() {
        if (login == null) {
            login = new Login();
        }
        return login;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(Login.class);

    private JPanel panel;
    private JLabel label;
    private JLabel label2;
    private JButton loginButton;
    private JTextField jTextField;
    private JPasswordField passwordField;

    private Login() {
        this.setTitle("用户登录界面");
        this.setSize(250,150);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        //设置为流式布局
        panel.setLayout(new FlowLayout());
        label = new JLabel("玩家名");
        label2 = new JLabel("密码");
        loginButton = new JButton("登录");
        //监听事件
        loginButton.addActionListener(this);
        //设置文本框的长度
        jTextField = new JTextField(16);
        //设置密码框
        passwordField = new JPasswordField(16);

        //把组件添加到面板panel
        panel.add(label);
        panel.add(jTextField);
        panel.add(label2);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(label);

        //实现面板panel
        this.add(panel);

        //设置可见
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==loginButton) {
            LOGGER.info("\n玩家名: " + jTextField.getText() + "\n密码: " + String.valueOf(passwordField.getPassword()));
            writeMessage.sendLoginInfo(jTextField.getText(), String.valueOf(passwordField.getPassword()));
        }
    }

    public void showMessage(String message) {
        JOptionPane.showConfirmDialog(null, message);
    }

    public void openMainPage(String playerName, String password) {
        login.dispose();
        // TODO 初始玩家角色
        MainPage.getInstance();
    }
}
