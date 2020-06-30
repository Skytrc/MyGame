package com.fung.client.newclient;

import com.fung.client.newclient.messagehandle.ChatServerWriteMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

/**
 * @author skytrc@163.com
 * @date 2020/6/23 15:45
 */
@Component
public class MainPage {

    @Autowired
    private ChatServerWriteMessage chatServerWriteMessage;

    public void mainPageInit() {
        JFrame jFrame = new JFrame();
        jFrame.setTitle("Game");
        JPanel displayPanel = displayPanel();
        JPanel chatPanel = chatPanel();

        JPanel jPanel = new JPanel(new FlowLayout());
        jPanel.add(displayPanel);
        jPanel.add(chatPanel);

        jFrame.add(jPanel);
        jFrame.setBounds(200, 100, 600, 700);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * 指令 panel
     */
    public JPanel displayPanel() {
        JPanel displayPanel = new JPanel(new BorderLayout());
        JPanel northPanel = new JPanel();
        JPanel centerPanel = new JPanel();

        // 输入框设置
        JTextField displayTextField = new JTextField("", 30);

        // 文本域设置
        JTextArea displayTextArea = new JTextArea("", 15, 40);
        displayTextArea.setLineWrap(true);
        JScrollPane displayScrollPane = new JScrollPane(displayTextArea);
        Dimension size = displayTextArea.getPreferredSize();
        displayScrollPane.setBounds(0, 0, size.width, size.height);

        JButton displayButton = new JButton("发送");
        displayButton.addActionListener((actionEvent) -> {
            String instruction = displayTextField.getText();
            displayTextArea.append("\n 发送指令: " + instruction);
        });

        northPanel.add(new JLabel("指令"));
        northPanel.add(displayTextField);
        northPanel.add(displayButton);
        centerPanel.add(displayScrollPane);

        displayPanel.add(northPanel, BorderLayout.NORTH);
        displayPanel.add(centerPanel, BorderLayout.CENTER);
        return displayPanel;
    }

    /**
     * 聊天 panel
     */
    public JPanel chatPanel() {
        JPanel chatPanel = new JPanel(new BorderLayout());
        JPanel northPanel = new JPanel();
        JPanel centerPanel = new JPanel();

        // 输入框设置
        JTextField chatTextField = new JTextField("", 20);

        // 下拉框
        JComboBox<String> cmb=new JComboBox<>();
        cmb.addItem("公频");
        cmb.addItem("私聊");

        // 文本域设置
        JTextArea chatTextArea = new JTextArea("", 10, 40);
        chatTextArea.setLineWrap(true);
        JScrollPane chatScrollPane = new JScrollPane(chatTextArea);
        Dimension size1 = chatTextArea.getPreferredSize();
        chatScrollPane.setBounds(0, 0, size1.width, size1.height);

        JButton chatButton = new JButton("发送");
        chatButton.addActionListener((actionEvent) -> {
            String chatMessage = chatTextField.getText();
            chatServerWriteMessage.sendChatMessage("playerId", cmb.getItemCount(), "playerName",
                    "channelId", chatMessage);
            chatTextArea.append("\n 发送聊天: " + chatMessage);
        });

        northPanel.add(new JLabel("聊天"));
        northPanel.add(chatTextField);
        northPanel.add(cmb);
        northPanel.add(chatButton);
        centerPanel.add(chatScrollPane);

        chatPanel.add(northPanel, BorderLayout.NORTH);
        chatPanel.add(centerPanel, BorderLayout.CENTER);

        return chatPanel;
    }

}
