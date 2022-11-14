package server;

import java.util.ArrayList;
import java.net.*;

public class Server {
    ArrayList<Conectado> listaDeConectados;
    private DatagramSocket socketServer; 
    
    
    public Server(){
        this.listaDeConectados = new ArrayList<Conectado>();
        try {
            this.socketServer = new DatagramSocket(3300);    
        } catch (Exception e) {
            System.out.printf("Erro ao iniciar o socket, %s",e.toString());
        }
    }
    
    public void escutarMensagem(){
        byte[] msg = new byte[1024] ;
        while (true) {
            DatagramPacket escutarLista = new DatagramPacket(msg, 1024);
            try {
                socketServer.receive(escutarLista);    
            } catch (Exception e) {
                System.out.println("Falha ao escutar nova mensagem");    
            }

            if(msg.toString().startsWith("msg:")){
                tratarAcenos(escutarLista);
            }
            else if(msg.toString().equals("tchau")){
                tratarDesconexoes(escutarLista);
            }
            else{
                fazerBroadcast(msg.toString());
            }
        }

        
    }
    private String formataConectados(){
        String lista = "";
        for (Conectado conec : listaDeConectados) {
            lista+=conec.getNome();
            lista+=",";
        }
        return lista;
    }


    private void tratarAcenos(DatagramPacket pacote){
        String novoNome = "";
        String msg = pacote.getData().toString();
        for(int i = "newConnec:".length(); i<msg.length();i++){
            novoNome+= msg.charAt(i);
        }


        Conectado novo = new Conectado(novoNome, 
                        (Inet4Address)pacote.getAddress(), 
                        pacote.getPort());
        
        String conexaoFeita = String.format("newConnec:%s",novoNome );
        fazerBroadcast(conexaoFeita);
        
        
        listaDeConectados.add(novo);
        String lista = formataConectados();
        DatagramPacket enviarLista = new DatagramPacket(lista.getBytes(), lista.getBytes().length,
                                                    novo.getIpv4(),novo.getPorta());
        try {
            socketServer.send(enviarLista);    
        } catch (Exception e) {
    
        }
                                                            
    }
    
    //Envia out:
    private void tratarDesconexoes(DatagramPacket pacote){
        String nome = "";
        for (int i = 0; i < listaDeConectados.size(); i++) {
            if(listaDeConectados.get(i).getIpv4() == (Inet4Address)pacote.getAddress()){
                listaDeConectados.remove(i);
                nome = listaDeConectados.get(i).getNome();
                return;
            }
        }
        String msg = String.format("out:%s",nome);
        fazerBroadcast(msg);
    }

    private void fazerBroadcast(String msg){
        for (Conectado destinario : listaDeConectados) {
            DatagramPacket pacoteBroadcast = new DatagramPacket(msg.getBytes(),
            msg.getBytes().length,
            destinario.getIpv4() ,destinario.getPorta());
            try {
                socketServer.send(pacoteBroadcast);    
            } catch (Exception e) {
            }
        }
    }
}
