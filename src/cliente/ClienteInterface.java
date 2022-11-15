package cliente;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteInterface{
    public static void main(String[] args) throws IOException, UnknownHostException{
        Socket socket = null;
        InputStreamReader isr = null;
        OutputStreamWriter osr = null;
        BufferedReader br = null;
        BufferedWriter bw = null;
        Scanner in = null;

        while (true){
            try{
                socket = new Socket("localhost", 8080);
                isr = new InputStreamReader(socket.getInputStream());
                osr = new OutputStreamWriter(socket.getOutputStream());
                br = new BufferedReader(isr);
                bw = new BufferedWriter(osr);
                in = new Scanner(System.in);
                
                while (true){
                    System.out.println("Digite uma acao: ");
                    String op = in.next();
                    
                    if (op.equals("sair")){
                        System.out.println("Encerrando o sistema...");
                        in.close();
                        socket.close();
                    }  
                    
                    else if (op.equals("enviar_msg")){
                        String mensagem = in.nextLine();
                        mensagem = mensagem.replaceFirst(" ", "");
                        
                        bw.write(mensagem);
                        bw.newLine();
                        bw.flush();

                        System.out.println("Resposta do server: " + br.readLine());
                        System.out.println("Resposta do server: " + br.readLine());
                    }

                    else
                        System.out.println("Erro COMANDO INVALIDO");
                }
            }

            catch (IOException e){
                System.out.printf("Erro: %s\n", e.getMessage());
                break;
            }

            finally{
               System.out.println("Finalizando..."); 
            }
        }
    }
}