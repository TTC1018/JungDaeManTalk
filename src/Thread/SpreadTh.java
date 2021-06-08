package Thread;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;


public class SpreadTh extends Thread{ //전송된 메시지를 받아 다른 Client에게 문자열을 보내주는 스레드
    Vector<Socket> sockets; // 소켓을 담을 벡터
    Socket socket;

    public SpreadTh(Socket socket, Vector<Socket> sockets){
        this.socket = socket;
        this.sockets = sockets;
    }

    public void run(){
        BufferedReader br = null;
        try{
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String msg = null;

            while(true){
                msg = br.readLine();//클라이언트로 부터 문자열 받기
                if(msg == null){ // 접속 끊겼을 때
                    sockets.remove(socket); //벡터에서 소켓 제거
                    break;
                }
                sendMsg(msg);//연결된 소켓들을 통해 다른 Client에게 메시지 전송
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

    public void sendMsg(String msg){ //전송받은 메세지 뿌리기
        try{
            for(Socket socket:sockets){
                if(socket != this.socket){ //본인은 제외한 소켓들에게 전송
                    PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
                    pw.println(msg);
                    pw.flush();
                }
            }
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}