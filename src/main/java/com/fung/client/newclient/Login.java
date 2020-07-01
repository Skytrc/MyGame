package com.fung.client.newclient;

import com.fung.client.newclient.messagehandle.ChatClientWriteMessage;
import com.fung.client.newclient.entity.ClientChatPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

/**
 * @author skytrc@163.com
 * @date 2020/6/23 15:09
 */
@Component
public class Login  {

    @Autowired
    private ChatClientWriteMessage chatClientWriteMessage;

    @Autowired
    private MainPage mainPage;

    @Autowired
    private ClientChatPlayer clientChatPlayer;

    private static final Logger LOGGER = LoggerFactory.getLogger(Login.class);

    private JFrame jFrame;
    private JButton loginButton;
    private JTextField jTextField;
    private JPasswordField passwordField;

    public void loginInit() {
        jFrame = new JFrame();
        jFrame.setTitle("用户登录界面");
        jFrame.setSize(250,150);
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        //设置为流式布局
        panel.setLayout(new FlowLayout());
        JLabel label = new JLabel("玩家名");
        JLabel label2 = new JLabel("密码");
        loginButton = new JButton("登录");
        //监听事件
        loginButton.addActionListener((e) -> {
            if (e.getSource()==loginButton) {
                chatClientWriteMessage.sendLoginInfo(jTextField.getText(), String.valueOf(passwordField.getPassword()));
            }
        });
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

        //实现面板panel
        jFrame.add(panel);

        //设置可见
        jFrame.setVisible(true);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public void openMainPage(String playerName, String password) {
        jFrame.dispose();
        // TODO 初始玩家角色
        clientChatPlayer.setPlayerName(playerName);
        mainPage.mainPageInit();
    }
}
