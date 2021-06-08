package Thread;

import View.ClientGUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReceiveTh extends Thread{ //서버가 보낸 메세지를 전송받는 스레드
    ClientGUI GUI;
    Socket socket;


    public ReceiveTh(Socket socket, ClientGUI GUI) {
        this.GUI = GUI;
        this.socket = socket;
    }

    public void run() {
        BufferedReader br = null;
        try{
            //서버로부터 전송된 메시지 읽는 스트림
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while(true){
                String msg = br.readLine();//소켓으로부터 메세지 읽어옴
                if(msg == null){
                    System.out.println("접속이 끊겼음");
                    break;
                }
                GUI.tArea.append(msg + "\n");
            }
        }catch(IOException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(br != null)
                    br.close();
                if(socket != null)
                    socket.close();
            }catch(IOException e){
                System.out.println(e.getMessage());
            }
        }
    }
}