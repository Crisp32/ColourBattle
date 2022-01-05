package com.connell.colourbattle.networking.server;

import java.net.Socket;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.connell.colourbattle.networking.Packet;

public class RoomHandler {
	private LinkedList<ClientHandler> clients;
	private ExecutorService clientPool;
	
	private int maxClientCount;
	
	public RoomHandler(int maxClientCount) {
		this.setMaxClientCount(maxClientCount);
		
		this.setClients(new LinkedList<ClientHandler>());
		this.setClientPool(Executors.newFixedThreadPool(this.getMaxClientCount()));
	}
	
	public void acceptClient(Socket clientSocket) {
		ClientHandler client = new ClientHandler(clientSocket, this);
		this.getClients().add(client);
		
		clientPool.execute(client);

		client.sendData(new Packet("max_client_count", this.getMaxClientCount() + ""));
		this.sendDataToAll(new Packet("update_current_client_count", this.getClientCount() + ""));
		
		System.out.println(this.getClientCount() + " Client(s) in Room");
		
		if (this.getClientCount() >= this.getMaxClientCount()) {
			this.sendDataToAll(new Packet("game_start"));
		}
	}
	
	public void sendDataToAll(Packet message) {
		for (ClientHandler client : this.getClients()) {
			try {				
				client.sendData(message);
			}
			catch (Exception e) {
				System.out.println("Failed to Send Data");
			}
		}
	}
	
	public void stop() {
		for (ClientHandler client : this.getClients()) {
			client.stop();
		}
	}
	
	public boolean isFull() {
		return (this.getClientCount() >= this.getMaxClientCount());
	}
	
	public int getClientCount() {
		return this.getClients().size();
	}

	public LinkedList<ClientHandler> getClients() {
		return clients;
	}

	public void setClients(LinkedList<ClientHandler> clients) {
		this.clients = clients;
	}

	public int getMaxClientCount() {
		return maxClientCount;
	}

	public void setMaxClientCount(int maxClientCountCount) {
		this.maxClientCount = maxClientCountCount;
	}

	public ExecutorService getClientPool() {
		return clientPool;
	}

	public void setClientPool(ExecutorService clientPool) {
		this.clientPool = clientPool;
	}	
}
