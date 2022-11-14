package cliente;

import java.net.*;
import java.util.ArrayList;


public class Cliente {
    private DatagramSocket socketCliente; 
    private String nome;
    private Inet4Address enderecoServidor;
    private int portaServidor;
    private ArrayList<String> listaDeConectados;
    
    public Cliente(int portaServidor, Inet4Address enderecoServidor, String nome){
        this.enderecoServidor = enderecoServidor;
        this.portaServidor = portaServidor;
        this.nome = nome;
        this.listaDeConectados = new ArrayList<String>();
        
        try {
            this.socketCliente = new DatagramSocket();    
        } catch (Exception e) {
            System.out.printf("Erro ao iniciar o socket, %s",e.toString());
        }

        acenoAoServidor();
    }

    private void acenoAoServidor(){
        byte[] oi= String.format("msg:%s",this.nome).getBytes();
        DatagramPacket pacoteOi = new DatagramPacket(oi,oi.length, enderecoServidor,portaServidor);
        try {
            socketCliente.send(pacoteOi);    
        } catch (Exception e) {
            System.out.println("Não foi possivel enviar o pacote de aceno");
        }
        receberListaDeConectados();
    }

    private void receberListaDeConectados(){
        byte[] buf = new byte[1024]; 

        DatagramPacket escutarLista = new DatagramPacket(buf, 1024);
        try {
            socketCliente.receive(escutarLista);    
        } catch (Exception e) {
            System.out.println("Não foi possivel listar os conectados");    
        }

        processarLista(buf);
    }          
    
    private void processarLista(byte[] buf){
        String[] lista = buf.toString().split(",");
        for (String s : lista) {
            listaDeConectados.add(s);
        }
    }
    public ArrayList<String> getListaDeConectados() {
        return listaDeConectados;
    }

    public void escutarMensagem(){
        byte[] msg = new byte[1024] ;
        while (true) {
            DatagramPacket escutarLista = new DatagramPacket(msg, 1024);
            try {
                socketCliente.receive(escutarLista);    
            } catch (Exception e) {
                System.out.println("Falha ao escutar nova mensagem");    
            }
            String msgStr = msg.toString();
            
            //Uma nova pessoa se conectou
            if(msgStr.startsWith("newConnec:")){
                tratarNovaConexao(msgStr);
            }
            //Uma pessoa se desconectou
            else if(msgStr.startsWith("out:")){
                tratarDesconexao(msgStr);
            }

            //Mensagem padrao
            else{
                
            }
            msg = new byte[1024];   
        }
    }

    private void tratarDesconexao(String msg){
        String nomeDesconectado = "";
            for(int i = "out:".length(); i<msg.length();i++){
                nomeDesconectado+= msg.charAt(i);
            }
        listaDeConectados.remove(nomeDesconectado);
    }

    private void tratarNovaConexao(String msg){
        String novoNome = "";
        for(int i = "newConnec:".length(); i<msg.length();i++){
            novoNome+= msg.charAt(i);
        }
        listaDeConectados.add(novoNome);
    }

    public void enviarMensagem(String msg){
        msg = String.format("%s:%s",this.nome,msg);
        DatagramPacket pacoteOi = new DatagramPacket(msg.getBytes(),
                                msg.getBytes().length,
                                this.enderecoServidor ,this.portaServidor);
        try {
            socketCliente.send(pacoteOi);    
        } catch (Exception e) {
            System.out.println("Não foi possivel enviar o pacote de aceno");
        }
    }

    public void desligarConexao(){
        String msg = "tchau";
        DatagramPacket tchau = new DatagramPacket(msg.getBytes(),
                                                  msg.getBytes().length,
                                                  this.enderecoServidor, this.portaServidor );
        try {
            socketCliente.send(tchau);
        } catch (Exception e) {
            System.out.println("Erro ao desligar a conexao");
        }
    }
}
