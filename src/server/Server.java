package server;
import java.io.*;
import java.net.*;

public class Server{

    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }

    public void encerrarProcesso(){
        try{
            if (serverSocket != null)
                serverSocket.close();
        }
        catch (IOException e){
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void rodar(){
        try{
            while(!serverSocket.isClosed()){
                System.out.println("Alguem entrou no chat: ");
                ManipuladorCliente mc = new ManipuladorCliente(serverSocket.accept());
                Thread thread = new Thread(mc);
                thread.start();
            }
        }
        catch(IOException e){
            System.out.println("Erro rodar: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException, UnknownHostException{
        try{
            ServerSocket serverSocket = new ServerSocket(8080);
            Server s = new Server(serverSocket);
            s.rodar();
        }
        catch(IOException e){
            System.out.println("Erro: " + e.getMessage());
            System.exit(0);
        }
    }
}
