package server;
public class PrincipalServer {
    public static void main(String[] args) {
        Server servidor = new Server();
        while (true) {
            servidor.escutarMensagem();
        }
    }
}
