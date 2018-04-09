package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

public class keyListener extends KeyAdapter{
		
		public void keyPressed(KeyEvent keyEvent){	
			switch(keyEvent.getKeyCode()){
				case KeyEvent.VK_W : if(mainGame.velY!=1){mainGame.velY=-1;} mainGame.velX=0; break;
				case KeyEvent.VK_A : if(mainGame.velX!=1){mainGame.velX=-1;} mainGame.velY=0; break;
				case KeyEvent.VK_S : if(mainGame.velY!=-1){mainGame.velY=1;} mainGame.velX=0; break;
				case KeyEvent.VK_D : if(mainGame.velX!=-1){mainGame.velX=1;} mainGame.velY=0; break;
				
				case KeyEvent.VK_R : System.exit(0); break;
				case KeyEvent.VK_ESCAPE : System.exit(0); break;
			}
		}
		public void keyTyped(KeyEvent keyEvent){};
}

