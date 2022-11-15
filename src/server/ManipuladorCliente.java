package server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ManipuladorCliente implements Runnable{

    public static ArrayList<ManipuladorCliente> clientes = new ArrayList<>();
    private Socket socket;
    private BufferedReader br; //input
    private BufferedWriter bw; //output
    private String nomeUsuario; 

    public ManipuladorCliente(Socket socket) throws IOException{
        try{
            this.socket = socket;
            this.bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.nomeUsuario = br.readLine();
            clientes.add(this);
            realizarBroadcast("[SERVIDOR]: " + this.nomeUsuario + " entrou no chat");
        }
        catch (Exception e){
            System.out.println("Erro: " + e.getMessage());
            fecharSala(socket, br, bw);
        }
    }

    @Override
    public void run(){
        String mensagem;

        while (true){
            try{
                mensagem = br.readLine();
                realizarBroadcast(mensagem);
            }
            catch(Exception e){
                System.out.println(e.getMessage());
                fecharSala(socket, br, bw);
                break;
            }
        }
    }

    public void realizarBroadcast(String mensagem){
        for (ManipuladorCliente cliente : clientes){
            try{
                if (!cliente.nomeUsuario.equals(nomeUsuario)){
                    cliente.bw.write(mensagem);
                    cliente.bw.newLine();
                    cliente.bw.flush();
                }
            }
            catch (IOException e){
                System.out.println(e.getMessage());
                fecharSala(socket, br, bw);
            }
        }
    }

    public void removerUsuarioDoChat(){
        realizarBroadcast("[SERVIDOR]: " + this.nomeUsuario + " nao tankou e foi de comes e bebes");
        clientes.remove(this);
    }

    public void fecharSala(Socket socket, BufferedReader br, BufferedWriter bw){
        removerUsuarioDoChat();
        try{
            if (br != null)
                br.close();
            
            if (bw != null)
                bw.close();

            if (socket != null)
                socket.close();
        }
        catch (IOException e){
            System.out.println("Erro: ffdsfa" + e.getMessage());
        }
    }
}