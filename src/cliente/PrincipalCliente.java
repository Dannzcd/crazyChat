package cliente;

import java.net.*;

public class PrincipalCliente {
    public static void main(String[] args) {
        InetAddress endereco = null;
        int porta = 3300;
        String nome = "Jobson";
        try {
            endereco =  Inet4Address.getByName("192.168.0.1");    
        } catch (Exception e) {
            System.out.println("Erro");
        }
        
        Cliente cliente = new Cliente(porta, (Inet4Address)endereco,nome);
        Thread escutar = new Thread(() ->cliente.escutarMensagem());
        escutar.start();

    }
}
