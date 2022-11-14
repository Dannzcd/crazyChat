package teste;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Server{
    public static void main(String[] args) throws IOException, UnknownHostException{
        Socket socket = null;
        InputStreamReader isr = null;
        OutputStreamWriter osr = null;
        BufferedReader br = null;
        BufferedWriter bw = null;
        ServerSocket serversocket = new ServerSocket(8080);

        try{
            socket = serversocket.accept();
            isr = new InputStreamReader(socket.getInputStream());
            osr = new OutputStreamWriter(socket.getOutputStream());
                
            br = new BufferedReader(isr);
            bw = new BufferedWriter(osr);

            while (true){
                String mensagem = br.readLine();
                System.out.println("Mensagem recebida: de "+ socket.getInetAddress().getHostAddress()+ " :" + mensagem);
                
                bw.write("Mensagem recebida pelo servidor !");
                bw.newLine();
                bw.flush();
                bw.write("Retorno: " + mensagem.toUpperCase());
                bw.newLine();
                bw.flush();

                if (mensagem.equals("Finalizar"))
                    break;
            }

            
        }

        catch (IOException e){
            System.out.printf("Erro: %s\n", e.getMessage());
        }

        finally{
            socket.close();
            serversocket.close();
            isr.close();
            osr.close();
            System.exit(0);
        }
    }
}
