package cliente;

import javax.swing.JPanel;

public class TelaCliente extends JPanel {
	private Cliente client;
	public Thread escutar;
	/**
	 * Create the panel.
	 */
	public TelaCliente() {
		client = new Cliente(3300,null,"teste");
	}

}
