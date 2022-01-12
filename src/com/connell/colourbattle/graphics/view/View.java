package com.connell.colourbattle.graphics.view;

import java.util.LinkedList;

import com.connell.colourbattle.graphics.RenderingManager;
import com.connell.colourbattle.graphics.ui.*;
import com.connell.colourbattle.networking.SocketEvent;
import com.connell.colourbattle.networking.client.SocketClientManager;
import com.connell.colourbattle.utilities.*;

import processing.core.PConstants;

public abstract class View {
	private static LinkedList<SocketEvent> events = new LinkedList<SocketEvent>();
	
	public static void registerViews() {
		RenderingManager.addView(new Menu());
		RenderingManager.addView(new WaitingRoom());
		RenderingManager.addView(new Game());
	}
	
	public static void loadViews() {
		for (View v : RenderingManager.getViews()) {
			v.load();
		}
	}
	
	public static void loadEvents() {
		for (SocketEvent e : getEvents()) {
			SocketClientManager.getClient().listen(e);
		}
	}
	
	protected static void addClientSocketEvent(SocketEvent event) {
		getEvents().add(event);
	}
	
	public abstract void load();
	public abstract void render();
	
	protected static Text createStandardText(String body, float scale, Colour colour, Vector2 screenPosition) {
		return new Text(screenPosition, colour, scale, body, RenderingManager.getMainFont(), PConstants.CENTER, PConstants.CENTER);
	}

	protected static InputField createStandardInputField(float scale, Vector2 screenPosition) {
		return new InputField(screenPosition, new Colour(0, 0, 0), new Colour(171, 171, 171), new Colour(255, 255, 255), scale, RenderingManager.getMainFont(), 17);
	}

	protected static LinkedList<SocketEvent> getEvents() {
		return events;
	}

	protected static void setEvents(LinkedList<SocketEvent> events) {
		View.events = events;
	}
}
