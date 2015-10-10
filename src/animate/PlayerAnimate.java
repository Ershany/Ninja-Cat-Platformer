package animate;

import input.MouseMaster;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import entity.Player;
import gfx.Sprite;

public class PlayerAnimate {

	private Player player;
	
	private BufferedImage currentSprite;
	private boolean leftHeld, rightHeld;
	private boolean rightLastHeld;
	private int anim;
	private int animSpeed = 20; //lower the value, faster the animation
	
	public PlayerAnimate(Player player) {
		this.player = player;
		init();
	}
	
	public void init() {
		currentSprite = Sprite.playerRightIdle.getImage();
		anim = 0;
	}
	
	public void update() {
		//if the user is moving, up the anim variable, if they are not moving, reset it!
		if(player.getSwingLeft() || player.getSwingRight()) {
			if(player.getSwingLeft()) {
				currentSprite = Sprite.playerLeftAttack.getImage();
			}
			else if(player.getSwingRight()) {
				currentSprite = Sprite.playerRightAttack.getImage();
			}
		}
		else if(player.getJumping()) {
			if(rightHeld) currentSprite = Sprite.playerRightFall.getImage();
			else if(leftHeld) currentSprite = Sprite.playerLeftFall.getImage();
			else if(rightLastHeld) currentSprite = Sprite.playerRightFall.getImage();
			else if(!rightLastHeld) currentSprite = Sprite.playerLeftFall.getImage();
		}
		else if(leftHeld || rightHeld) {
				anim++;
				if(rightHeld) {
					if(anim % animSpeed == 0 || anim == 1) {
						currentSprite = Sprite.playerRightWalk1.getImage();
					}
					else if(anim % (animSpeed/2) == 0) {
						currentSprite = Sprite.playerRightWalk2.getImage();
					}
				} 
				else if(leftHeld) {
					if(anim % animSpeed == 0 || anim == 1) {
						currentSprite = Sprite.playerLeftWalk1.getImage();
					}
					else if(anim % (animSpeed/2) == 0) {
						currentSprite = Sprite.playerLeftWalk2.getImage();
					}
				}	
		} else {
			anim = 0;
			if(rightLastHeld) {
				currentSprite = Sprite.playerRightIdle.getImage();
			}
			else {
				currentSprite = Sprite.playerLeftIdle.getImage();
			} 
		}
	}
	
	public void render(Graphics2D g, int xOffset, int yOffset) {
		g.drawImage(currentSprite, player.getPos().getIntX() - xOffset, player.getPos().getIntY() - yOffset, null);
	}
	
	
	public void keyPressed(int k) {
		switch(k) {
		case KeyEvent.VK_D: rightHeld = true; rightLastHeld = true; break;
		case KeyEvent.VK_A: leftHeld = true; rightLastHeld = false; break;
		}
	}
	
	public void keyReleased(int k) {
		switch(k) {
		case KeyEvent.VK_D: rightHeld = false; currentSprite = Sprite.playerRightIdle.getImage(); break;
		case KeyEvent.VK_A: leftHeld = false; currentSprite = Sprite.playerLeftIdle.getImage(); break;
		}
	}
	
}
