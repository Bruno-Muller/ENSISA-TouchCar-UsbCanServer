package server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import can.CanMessage;

public class Client implements Runnable {

	private Server server;
	private Socket socket;
	private boolean terminating;

	public Client(Socket socket, Server server) {
		this.socket = socket;
		this.server = server;
		this.terminating = false;
	}

	public void run() {
		try {
			byte bytes[] = new byte[6];
			while(this.socket.getInputStream().read(bytes,0,6) != -1) {
				this.server.receivedCanMessage(new CanMessage(bytes));
			}
		} catch (IOException e) {
			if (!this.terminating)
				e.printStackTrace();
		} 
		if (!this.terminating)
			this.server.clientDeconnection(this);
		System.out.println("A client thread has been terminated.");
	}

	public void send(CanMessage canMessage) {
		try {
			OutputStream outputStream = this.socket.getOutputStream();
			outputStream.write(canMessage.getBytes());
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void close() throws IOException {
		this.terminating = true;
		this.socket.close();
		System.out.println("A client has been closed");
	}
}
