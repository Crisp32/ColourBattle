package com.connell.colourbattle.networking.client;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.connell.colourbattle.graphics.RenderingManager;
import com.connell.colourbattle.networking.SocketEvent;
import com.connell.colourbattle.networking.SocketHandler;

public class ClientSocketStream extends SocketHandler {
	private BufferedReader in;
	private PrintWriter out;
	
	private int port;
	private String ip;
	
	private boolean isRunning;
	
	public ClientSocketStream(String ip, int port) {
		super();
		
		this.setRunning(false);		
		this.setPort(port);
		this.setIp(ip);
	}
	
	@Override
	public void start() {
		this.listen(new SocketEvent("console_message") {
			@Override
			public void call(String data) {
				System.out.println("Client Received: " + data);
			}
		});
		
		this.listen(new SocketEvent("game_start") {
			@Override
			public void call(String data) {
				System.out.println("Game Starting");
				RenderingManager.setActiveViewIndex(2);
			}
		});
	}
	
	@Override
	public void run() {
		try {
			this.setClientSocket(new Socket(this.getIp(), this.getPort()));
			this.initIOStream();
			
			System.out.println("Connected to Server on: " + this.getIp() + ":" + this.getPort());
			
			this.setRunning(true);
			
			while (this.isRunning()) {
				this.handleDataReceival();
			}
		}
		catch (Exception e) {
			this.handleDisconnect();
		}
	}
	
	@Override
	public void handleDisconnect() {
		System.out.println("Disconnected From Server");
		SocketClientManager.stopClient();
	}
	
	@Override
	public void stop() {
		try {			
			this.setRunning(false);
			
			this.getIn().close();
			this.getOut().close();
			
			this.getClientSocket().close();
			
			System.out.println("Closed Socket Client");
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to Stop Client Socket");
		}
	}
	
	public BufferedReader getIn() {
		return in;
	}
	
	public void setIn(BufferedReader in) {
		this.in = in;
	}
	
	public PrintWriter getOut() {
		return out;
	}
	
	public void setOut(PrintWriter out) {
		this.out = out;
	}
	
	public int getPort() {
		return port;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
}
