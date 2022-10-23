import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.SocketException;

import javax.xml.crypto.Data;

public class Server {
    private DatagramSocket datagramSocket;
    private byte[] buffer = new byte[512];

    public Server(DatagramSocket d1){
        this.datagramSocket = d1;
    }

    public void tratarPacote(){
        while (true){
            try{
                DatagramPacket pacotededatagrama = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(pacotededatagrama);
                Inet4Address ipv4Remetente = (Inet4Address)pacotededatagrama.getAddress();
                int porta = pacotededatagrama.getPort();
                String mensagemRecebida = new String(pacotededatagrama.getData(), 0, pacotededatagrama.getLength());
                System.out.println("Mensagem recebida: " + mensagemRecebida);
                mensagemRecebida = mensagemRecebida.toUpperCase();
                System.out.println("Mensagem a enviar: " + mensagemRecebida);
                byte[] buffernovo = mensagemRecebida.getBytes();
                DatagramPacket pacoteResposta = new DatagramPacket(buffernovo, buffernovo.length, ipv4Remetente, porta);
                datagramSocket.send(pacoteResposta);
            }
            catch (Exception e1){
                System.out.println(e1.getMessage());
                e1.printStackTrace();
                break;
            }
        }
    }

    public static void main(String[] args) throws SocketException{
        try{
            DatagramSocket d2 = new DatagramSocket(3000);
            Server server = new Server(d2);
            server.tratarPacote();
        }
        catch(SocketException e1){
            e1.printStackTrace();
        }
    }
}
