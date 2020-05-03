package com.fung.client;

import com.fung.protobuf.InstructionPack;
import io.netty.channel.Channel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author skytrc@163.com
 * @date 2020/4/28 10:50
 */
public class GUIClient extends JFrame{

    boolean windowClosed = false;

    private Channel channel;

    private JTextArea jTextArea;

    private static GUIClient instace = new GUIClient();

    private GUIClient(){
        JPanel p1 = new JPanel();
        JPanel cards = new JPanel(new CardLayout());
        setTitle("My First Game");
        // 输入框设置
        JTextField jTextField = new JTextField("", 30);

        // 文本域设置
        JTextArea jTextArea = new JTextArea("",20, 30);
        setjTextArea(jTextArea);
        jTextArea.setLineWrap(true);
        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        Dimension size = jTextArea.getPreferredSize();
        jScrollPane.setBounds(0, 0, size.width, size.height);

        // 发送按钮设置
        JButton jButton = new JButton("发送");
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String instruction = jTextField.getText();
                jTextArea.append("发送指令: " + instruction);
                jTextArea.append("\n");
                // 滚动条自动滚动，滚动到倒数第二行
                jTextArea.setSelectionStart(jTextArea.getText().length());
                send(instruction);
            }
        });

        p1.add(new JLabel("指令"));
        p1.add(jTextField);
        p1.add(jButton);
        p1.add(jScrollPane);
        cards.add(p1, "card1");
        CardLayout c1 = (CardLayout) cards.getLayout();
        c1.show(cards, "card1");

        add(cards);
        setBounds(600, 300, 500, 400);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                windowClosed = true;
            }
        });
    }

    public static GUIClient getInstace() {
        return instace;
    }

    private InstructionPack pack = new InstructionPack();

    public void response(String s) {
        jTextArea.append(s);
    }

    public void setjTextArea(JTextArea jTextArea) {
        this.jTextArea = jTextArea;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }


    public void send(String instruction) {
        channel.writeAndFlush(pack.decode(instruction));
    }
}
