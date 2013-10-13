package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import can.CanMessage;

public class Server implements Runnable {

	public static final int DEFAULT_PORT = 50885;
	private ServerSocket serverSocket;
	private ArrayList<Client> clients;
	private boolean terminating;

	Server(int port) throws IOException {
		this.serverSocket = new ServerSocket(port);
		this.clients = new ArrayList<Client>();
		this.terminating = false;
	}

	public void run() {
		System.out.println("Server is running on port " + String.valueOf(this.serverSocket.getLocalPort()) + "...");
		try {
			while (!this.serverSocket.isClosed())
				clientConnection(this.serverSocket.accept());

		} catch (IOException e) {
			if (!this.terminating)
				e.printStackTrace();
		}
		System.out.println("Server thread has been terminated");
	}

	public void close() throws IOException {
		this.terminating = true;
		System.out.println("Exiting... ");
		this.serverSocket.close();
		System.out.println("Server has been closed");
		Iterator<Client> iterator = clients.iterator();
		while (iterator.hasNext()) {
			iterator.next().close();
		}
	}

	public void clientConnection(Socket socket) {
		System.out.print("New connection from IP ");
		System.out.println(socket.getInetAddress());
		Client client = new Client(socket, this);
		this.clients.add(client);
		new Thread(client).start();
	}

	public void clientDeconnection(Client client) {
		this.clients.remove(client);
		System.out.println("A client has been removed.");
	}

	public void receivedCanMessage(CanMessage canMessage) {
		if (!canMessage.isUseless()) {
			System.out.println("Recieved " + canMessage.toString());
			this.sendCanMessageToAll(canMessage);
		}

	}

	public void sendCanMessageToAll(CanMessage canMessage) {
		Iterator<Client> iterator = clients.iterator();
		while (iterator.hasNext()) {
			iterator.next().send(canMessage);
		}
	}

	public boolean isClosed() {
		return this.serverSocket.isClosed();
	}

}