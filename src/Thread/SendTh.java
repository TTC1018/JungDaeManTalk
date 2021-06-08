package Thread;

import View.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;


public class SendTh{ // 메세지 입력받아 서버로 전송
    ClientGUI GUI;
    Socket socket;

    String msg;
    String nickname;

    public SendTh(ClientGUI GUI) {
        this.GUI  = GUI;
        this.socket = GUI.socket;
    }
    
    public void sendMsg() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); //키보드로부터 읽어오기 위한 스트림
        PrintWriter pw = null;

        try{
            pw = new PrintWriter(socket.getOutputStream(),true);//서버로 메시지 전송하는 스트림

            InetAddress myaddr = socket.getLocalAddress();
            String ip = myaddr.getHostAddress();

            if(GUI.isClosed()){
                msg = "[" + nickname + "]("+ip+") 님이 나가셨습니다. ";
            }
            else if(!GUI.isInitialized()) {  //상대방에게 닉네임, IP 전송
                getNickname();
                System.out.println("IP : " + ip + " | ID : " + "(" + nickname + ")");
                GUI.tArea.append("닉네임 [" + nickname + "](으)로 로그인\n");
                msg = "[" + nickname + "] 님 로그인 ("+ip+")";
            }else{
                msg = "[" + nickname + "] " + GUI.msgField.getText();
            }

            pw.println(msg); //메세지 서버로 전송
        }catch(IOException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(br != null)
                    br.close();
            }catch(IOException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public void getNickname(){
        this.nickname = Profile.getNickname();
    }

}