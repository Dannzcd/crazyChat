package teste;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ManipuladorCliente implements Runnable{

    private Socket cliente;
    private BufferedReader br;
    private PrintWriter pw;
    
    public ManipuladorCliente(Socket cliente){
        this.cliente = cliente;
    }

    @Override
    public void run() {
        
    }
}
