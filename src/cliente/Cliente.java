package cliente;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {
    private Socket socket;
    private String nomeUsuario;
    private BufferedReader br;
    private BufferedWriter bw;
    private Scanner in;

    public Cliente(Socket socket, String nomeUsuario){
        try{
            this.socket = socket;
            this.nomeUsuario = nomeUsuario;
            this.bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch(IOException e){
            fecharSala(socket, br, bw);
        }
    }

    public void escutarMensagem(){
        new Thread(new Runnable() {
            @Override
            public void run(){
                String mensagem_do_grupo;
                
                while(socket.isConnected()){
                    try{
                        mensagem_do_grupo = br.readLine();
                        System.out.println(mensagem_do_grupo);
                        
                    }
                    catch(IOException e){
                        fecharSala(socket, br, bw);
                    }
                }
            }
        }).start();
    }

    public void enviarMensagem(){
        try{
            this.bw.write(this.nomeUsuario);
            this.bw.newLine();
            this.bw.flush();

            this.in = new Scanner(System.in);

            while (socket.isConnected()){
                String mensagem = in.nextLine();
                
                bw.write(this.nomeUsuario + ": " + mensagem);
                bw.newLine();
                bw.flush();
            }
        }
        catch (IOException e){
            System.out.println("Erro msg: " + e.getMessage());
        }
    }

    public void fecharSala(Socket socket, BufferedReader br, BufferedWriter bw){
        try{
            if (br != null)
                br.close();

            if (bw != null)
                bw.close();

            if (socket != null)
                socket.close();
        }
        catch(IOException e){
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException, UnknownHostException{
        Scanner leitor = new Scanner(System.in);

        System.out.println("Insira um nome de usuario: ");
        String nomeUsuario = leitor.next(); 
        Socket socket = new Socket("localhost", 8080);
        Cliente c = new Cliente(socket, nomeUsuario);

        c.escutarMensagem();
        c.enviarMensagem();
    }
}