package View;

import Thread.SendTh;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.Socket;
import java.net.URL;

public class ClientGUI extends JFrame {

    public JLabel BGLabel;
    public ImageIcon BGImg;
    //String dir = System.getProperty("user.dir");
    URL imageURL = getClass().getClassLoader().getResource("img/BG.png");
    public JScrollPane sPane = new JScrollPane();
    public JTextArea tArea = new JTextArea();
    public JTextField msgField = new JTextField();
    private boolean initialized = false; //생성되었는가
    private boolean closed = false; //닫겼는가 안 닫겼는가 확인
    public SendTh sTh;
    public Socket socket;


    public ClientGUI(Socket socket) {

            //BGImg = new ImageIcon(ImageDir.BGImg_1);
            BGImg = new ImageIcon(imageURL);
            BGLabel = new JLabel(BGImg);

            this.setTitle("정대만톡");
            this.socket = socket;
            sTh = new SendTh(this);
            new Profile(sTh, this);

            BGLabel.setBounds(0, 0, BGImg.getIconWidth(), BGImg.getIconHeight());
            sPane.setBounds(12, 10, 300, 242);
            msgField.setBounds(12, 260, 300, 20);

            tArea.setOpaque(false);
            tArea.setEditable(false);
            tArea.setLineWrap(true); // 자동 줄 바꿈
            DefaultCaret caret = (DefaultCaret)tArea.getCaret();
            caret.setUpdatePolicy((DefaultCaret.ALWAYS_UPDATE)); //자동 스크롤 기능

            sPane.setOpaque(false);
            sPane.setBorder(new LineBorder(Color.black, 2));
            sPane.getViewport().setOpaque(false);
            sPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            msgField.setOpaque(false);

            msgField.setBorder(new LineBorder(Color.black, 2));

            sPane.setViewportView(tArea);
            add(sPane);
            add(msgField);
            add(BGLabel);

            //엔터 눌러서 메시지 전송
            msgField.addKeyListener(new KeyAdapter() { // 엔터 눌러서 채팅 전송
            public void keyPressed(KeyEvent e) {
                String id = Profile.getNickname();

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                    if (msgField.getText().equals("")) {
                        return;
                    }
                    tArea.append("[" + id + "] " + msgField.getText() + "\n");
                    sTh.sendMsg();
                    msgField.setText("");

                }
                 }
        });

            //창 닫히면 종료
            this.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    setClosed(true);
                    sTh.sendMsg();
                }
            });

        this.setSize(700, 520);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        this.setVisible(false);

            repaint();


    }


    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }
}