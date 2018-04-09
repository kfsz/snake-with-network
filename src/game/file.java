package game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class file {
Path file;
String file2;
int score = 0;

file()
	{ 
	
	file = Paths.get(System.getProperty("user.dir")+"\\highscore.txt");
	file2 = System.getProperty("user.dir")+"\\highscore.txt";
		
		try {
		    // empty file / later write 10
		    Files.createFile(file);
		    		write(10);
		} catch (FileAlreadyExistsException x) {
// file already exists
		} catch (IOException x) {
// some kind of error.
		    System.err.format("createFile error: %s%n", x);
		}
	};
	
public int read() throws IOException
	{
		Charset charset = Charset.forName("US-ASCII");
		try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
		    String result = null;
		    while ((result = reader.readLine()) != null) {
		    	score = Integer.parseInt(result);
		    }
		} catch (IOException x) {
		    System.err.format("IOException: %s%n", x);
		}
		return score;
	};
	
public void write(int score) throws IOException
	{
		Charset charset = Charset.forName("US-ASCII");
		String newscore = Integer.toString(score);
		try (BufferedWriter writer = Files.newBufferedWriter(file, charset)) {
		    writer.write(newscore, 0, newscore.length());
		} catch (IOException x) {
		    System.err.format("IOException: %s%n", x);
		}	
	}
	
}
