package game;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class scoreNetwork {
	
	public int score;
	public String name, scoreString, toSend;
	public BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	scoreNetwork(int a){score = a; name = "";};
	
	public void send(){
		try {
			Socket so = new Socket("localhost", 50007);
			//here?
			toSend = scoreString + "\n" + name + "\n" + "poland"; //System.out.println(toSend);
			DataOutputStream out = new DataOutputStream(so.getOutputStream());
			out.write(toSend.getBytes());
			
			//get table
			DataInputStream dis = new DataInputStream(so.getInputStream());
			
			byte[] buffer = new byte[1024];
			dis.read(buffer);
			
			String str = new String(buffer, StandardCharsets.UTF_8);
			str = str.replaceAll(":", "<br>");
			str = str.replaceAll("\0", "");
			//System.out.println(str);
			
			//now table
			out.write(toSend.getBytes());
			
			//works!
			sendFile(so, str);
			
		} catch (UnknownHostException e) {
			return;
		} catch (IOException e) {
			return;
		}	
	}
	
	public void sendFile(Socket so, String str) throws IOException{
		DataInputStream dis = new DataInputStream(so.getInputStream());
		FileOutputStream fos = new FileOutputStream("reward.bmp");		
		
        //onto image
        
		int count;
		byte[] buffer = new byte[1024];
		while ((count = dis.read(buffer)) > 0){
		  fos.write(buffer, 0, count);
		}
		fos.close();
		
		BufferedImage img = ImageIO.read(new File("reward.bmp")); 
		
		
		//show windows
		JFrame frame = new JFrame();
		JLabel image = new JLabel(new ImageIcon(img));
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(image);
		frame.add(mainPanel);
		frame.setLocation(200, 0);
		frame.setSize(917, 569);
		frame.setVisible(true);
		
		//get text
		
		
		JFrame framtext = new JFrame();
		JPanel paneltext = new JPanel();
		JLabel labeltext = new JLabel("<html>" + str + "</html>");

		paneltext.add(labeltext);
		framtext.add(paneltext);
		framtext.setSize(200, 800);
		framtext.setVisible(true);
		
	}
	
	
	public void test() throws IOException{
	//	System.out.println("hello \nyour score is");
	//	System.out.println(score);
	//	System.out.println("please enter your name");
		//name = br.readLine();

		
		name= JOptionPane.showInputDialog("Your name?");
		
		//System.out.println(name);
		scoreString = String.valueOf(score);
//		System.out.println(name + " " + scoreString);
		
		send();
	}
	
}
