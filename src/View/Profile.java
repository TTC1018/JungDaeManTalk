package View;

import Thread.SendTh;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class Profile extends JFrame implements ActionListener {
    static JTextField tField = new JTextField(10);
    JLabel nickname = new JLabel("닉네임");
    JLabel BGLabel;
    ImageIcon BGImg;
    JButton btn = new JButton("입력");
    URL imageURL = getClass().getClassLoader().getResource("img/tears.png");

    SendTh Sth;
    ClientGUI GUI;

    public Profile(){}
    public Profile(SendTh Sth, ClientGUI GUI) {
        this.Sth = Sth;
        this.GUI = GUI;
        //BGImg = new ImageIcon(ImageDir.NickImg);
        BGImg = new ImageIcon(imageURL);
        BGLabel = new JLabel(BGImg);

        setTitle("닉네임 입력");
        setVisible(true);
        setSize(300,200);
        setDefaultCloseOperation(3);
        setLocationRelativeTo(null);
        setLayout(null);
        
        tField.setBorder(new LineBorder(Color.blue, 2));
        tField.setHorizontalAlignment(SwingConstants.CENTER);
        tField.setForeground(Color.blue);
        nickname.setForeground(Color.white);
        btn.setBackground(Color.blue);
        btn.setForeground(Color.white);

        nickname.setBounds(30,10,50,20);
        tField.setBounds(90,10,100,20);
        btn.setBounds(200,10,70,20);
        BGLabel.setBounds(0,0,300,200);

        add(nickname);
        add(tField);
        add(btn);
        add(BGLabel);

        btn.addActionListener(this);

        setAlwaysOnTop(true);
        toFront();
        requestFocus();
    }

    public void actionPerformed(ActionEvent e) {
        Sth.sendMsg();
        GUI.setInitialized(true);
        GUI.setVisible(true);
        this.dispose();
    }
    public static String getNickname(){
        return tField.getText();
    }

}