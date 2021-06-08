package Client;

import View.ClientGUI;
import Thread.*;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

    public static void main(String[] args) {
        ClientGUI GUI;
        Socket socket = null;

        String host_ip;
        Scanner scan = new Scanner(System.in);

        System.out.print("접속할 서버 IP를 입력하세요 (로컬 IP 127.0.0.1) : ");
        host_ip = scan.nextLine();


        try{
            socket = new Socket(host_ip,50023);
            System.out.println("연결에 성공했습니다");
            GUI = new ClientGUI(socket);
            new ReceiveTh(socket, GUI).start();
        }catch(IOException e){
            System.out.println("연결 실패!");
        }
    }
}