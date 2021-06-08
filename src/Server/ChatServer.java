package Server;
import Thread.SpreadTh;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;


public class ChatServer {
    public static void main(String[] args) {
        ServerSocket server;
        Socket socket;
        Vector<Socket> sockets = new Vector<Socket>(); //클라이언트와 연결된 소켓들을 저장

        try{
            server = new ServerSocket(50023);
            while(true){
                System.out.println("접속 대기중 입니다...");
                socket = server.accept();//클라이언트와 연결된 소켓을 벡터에 add
                sockets.add(socket);
                new SpreadTh(socket, sockets).start(); //스레드 start
                System.out.println("새 연결 생성 되었음");
            }
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}