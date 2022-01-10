package com.connell.colourbattle.graphics.view;

import com.connell.colourbattle.graphics.RenderingManager;
import com.connell.colourbattle.graphics.ui.Text;
import com.connell.colourbattle.graphics.ui.button.StopServerButton;
import com.connell.colourbattle.networking.client.ClientSocketStream;
import com.connell.colourbattle.networking.client.SocketClientManager;
import com.connell.colourbattle.utilities.Colour;
import com.connell.colourbattle.utilities.Vector2;

public class WaitingRoom extends View {
	private Text mainMessage;
	private Text failMessage;
	
	private StopServerButton stopButton;

	private int currentClientCount;
	private int maxClientCount;

	@Override
	public void load() {
		Vector2 screenCenter = RenderingManager.getScreenCenter();
		float scale = RenderingManager.getScale();
		
		this.currentClientCount = 1;
		this.maxClientCount = 1;
		
		this.mainMessage = createStandardText(this.generateMainMessage(), 50, new Colour(255, 255, 255), screenCenter);
		this.failMessage = createStandardText("Failed To Connect", 50, new Colour(255, 0, 0), screenCenter);
		
		this.stopButton = new StopServerButton(new Vector2(screenCenter.getX(), screenCenter.getY() + (2 * scale)), "Cancel", 1.6f, RenderingManager.getMainFont());
	}

	@Override
	public void render() {
		ClientSocketStream stream = SocketClientManager.getClient();
		
		if (stream.isRunning()) {
			this.currentClientCount = stream.getCurrentServerClientsCount();
			this.maxClientCount = stream.getMaxServerClientsCount();
			
			this.mainMessage.setBody(this.generateMainMessage());
			this.mainMessage.render();
		}
		else {
			this.failMessage.render();
		}
		
		this.stopButton.render();
	}
	
	private String generateMainMessage() {
		return "Waiting For Player(s) [" + this.currentClientCount + "/" + this.maxClientCount + "]...";
	}
}
