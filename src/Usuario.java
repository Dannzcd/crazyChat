import java.net.Inet4Address;

public class Usuario {
    private char[] nomeUsuario = new char[50];
    private char[] email = new char[100];
    private char[] senha = new char[50];
    private String ipv4Address;
    Amigos amigos;

    public char[] getNomeUsuario() {
        return this.nomeUsuario;
    }

    public char[] getEmail(){
        return this.email;
    }

    //public void sendMessage()
}
