package server;

import java.io.IOException;
import java.util.Scanner;

public class Terminal {

	public static void main(String[] args) {

		int port = Server.DEFAULT_PORT;
		if (args.length > 0)
			port = Integer.valueOf(args[0]);
		try {
			Server server = new Server(port);
			new Thread(server).start();
			
			Scanner scanner = new Scanner(System.in);
			while (!server.isClosed()) {
				if (scanner.next().equalsIgnoreCase("exit"))
					server.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Terminal has been terminated");
	}
}
