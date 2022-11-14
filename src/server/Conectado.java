package server;

import java.net.*;

public class Conectado {
    String nome;
    Inet4Address ipv4;
    int porta;
    public Conectado(String nome, Inet4Address ip, int porta){
        this.nome = nome;
        this.ipv4 = ip;
        this.porta = porta;
    }

    public Inet4Address getIpv4() {
        return ipv4;
    }
    public String getNome() {
        return nome;
    }
    public int getPorta() {
        return porta;
    }
}
