import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Client {
    private DatagramSocket udpsocket;
    private Inet4Address ip1;
    private byte[] buffer;
    Scanner in = new Scanner(System.in);

    public Client(DatagramSocket udpsocket, Inet4Address ip){
        this.udpsocket = udpsocket;
        this.ip1 = ip;
    }

    public void tratarPacote(){
        while (true){
            try{
                String mensagem = in.next();
                buffer = mensagem.getBytes();
                DatagramPacket udp2 = new DatagramPacket(buffer, buffer.length, ip1, 3000);
                udpsocket.send(udp2);
                udpsocket.receive(udp2);
                String mensagemResposta = new String(udp2.getData(), 0, udp2.getLength());
                System.out.println("Resposta:  " + mensagemResposta);
            }
            catch(IOException e1){
                e1.printStackTrace();
                break;
            }
        }    
    }

    public static void main(String[] args) throws SocketException, UnknownHostException{
        try{
            DatagramSocket udpsocket = new DatagramSocket();
            Inet4Address ipv4 = (Inet4Address)InetAddress.getByName("localhost");
            Client client = new Client(udpsocket, ipv4);
            System.out.println("Mande uma mensagem pro server (teste)");
            client.tratarPacote();
        }

        catch (SocketException | UnknownHostException e1){
            e1.printStackTrace();
        }
    }
}
