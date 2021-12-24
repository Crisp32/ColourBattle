package com.connell.colourbattle.networking.client;

public class SocketClientManager {

	private static ClientSocketStream client;
	private static Thread clientThread;
	
	private static boolean init(String ip, int port) {
		try {
			setClient(new ClientSocketStream(ip, port));
			return true;
		}
		catch (Exception e) {
			System.out.println("Failed to Connect to: " + ip + ":" + port);
		}
		
		return false;
	}
	
	public static void start(String ip, int port) {
		if (init(ip, port)) {
			setClientThread(new Thread(getClient()));
			getClientThread().start();
		}
	}

	public static ClientSocketStream getClient() {
		return client;
	}

	public static void setClient(ClientSocketStream client) {
		SocketClientManager.client = client;
	}

	public static Thread getClientThread() {
		return clientThread;
	}

	public static void setClientThread(Thread clientThread) {
		SocketClientManager.clientThread = clientThread;
	}
}
