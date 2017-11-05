/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.text.DefaultCaret;


/**
 *
 * @author RZ09
 */
public class HighScore extends JFrame {
    
    
    private HighScore(String name, int score){
        String scoreName = name;
        int highScore = score;
    }
    
    public static void createFrame() throws IOException
    {
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                try 
                {
                    JFrame frame = new JFrame("Test");
                    String fileName = "scores.txt";
                    String line = null;
                    File scoreFile = new File(fileName);
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    JPanel panel = new JPanel();
                    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                    panel.setOpaque(true);
                    JTextArea textArea = new JTextArea(15, 50);
                    textArea.setWrapStyleWord(true);
                    textArea.setEditable(false);
                    textArea.setFont(Font.getFont(Font.SANS_SERIF));
                    JScrollPane scroller = new JScrollPane(textArea);
                    scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                    scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                    panel.add(scroller);
                    frame.getContentPane().add(BorderLayout.CENTER, panel);
                    frame.pack();
                    frame.setLocationByPlatform(true);
                    frame.setVisible(true);
                    frame.setResizable(false);
                    HighScore[] players = new HighScore[5];
                    
                    if(!scoreFile.exists()){
                        BufferedWriter bufferedWriter;
                        bufferedWriter = new BufferedWriter(new FileWriter(fileName));
                        bufferedWriter.write("Goku 9001\r\n" + "Vegeta 9000\r\n" + 
                                "Gohan 5000\r\n" + "Piccolo 4000\r\n" + "Yamcha 1");
                        bufferedWriter.close();
                        players[0] = new HighScore("Goku", 9001);
                        players[1] = new HighScore("Vegeta", 9000);
                        players[2] = new HighScore("Gohan", 5000);
                        players[3] = new HighScore("Piccolo", 4000);
                        players[4] = new HighScore("Yamcha", 1);
                    }
                    
                    FileReader fileReader = new FileReader(fileName);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    int count = 0;
                    int scoreNumber;
                    while ((line = bufferedReader.readLine()) != null){
                        textArea.append(line + System.getProperty("line.separator"));
                        String[] player = line.split(" ");
                        scoreNumber = Integer.parseInt(player[1]);
                        players[count] = new HighScore(player[0], scoreNumber);
                        count++;
                    }
                    fileReader.close();
                    bufferedReader.close();
                    }
                catch (IOException ex) {
                    Logger.getLogger(HighScore.class.getName()).log(Level.SEVERE, null, ex);
                }                
                catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        });
    }
    
    	static void modifyFile(String oldString, String newString)
	{
		File fileToBeModified = new File("scores.txt");
		
		String oldContent = "";
		
		BufferedReader reader = null;
		
		FileWriter writer = null;
		
		try 
		{
			reader = new BufferedReader(new FileReader(fileToBeModified));
			
			//Reading all the lines of input text file into oldContent
			
			String line = reader.readLine();
			
			while (line != null) 
			{
				oldContent = oldContent + line + System.lineSeparator();
				
				line = reader.readLine();
			}
			
			//Replacing oldString with newString in the oldContent
			
			String newContent = oldContent.replaceAll(oldString, newString);
			
			//Rewriting the input text file with newContent
			
			writer = new FileWriter(fileToBeModified);
			
			writer.write(newContent);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				//Closing the resources
				
				reader.close();
				
				writer.close();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
                }
        }
    
public static void checkScore() throws FileNotFoundException, IOException{
        FileReader fileReader = new FileReader("scores.txt");
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    int count = 0;
                    String line = null;
                    int scoreNumber;
                    HighScore[] players = new HighScore[5];
                    while ((line = bufferedReader.readLine()) != null){
                        String[] player = line.split(" ");
                        scoreNumber = Integer.parseInt(player[1]);
                        //players[count] = new HighScore(player[0], scoreNumber);
                        if(MainWindow.score > scoreNumber){
                         /**   for (int i2 = 0; i2 > 5; i2++){
                            players[count] = new HighScore("USER INPUT", MainWindow.score);
                            BufferedWriter bufferedWriter;
                            bufferedWriter = new BufferedWriter(new FileWriter("score.txt", false));
                            bufferedWriter.write(players[0].scoreName);
                            bufferedWriter.close();
                            } **/
                         String name= JOptionPane.showInputDialog("Please enter your name: ");
                         String newScore = name + " " + MainWindow.score;
                         fileReader.close();
                         bufferedReader.close();
                         modifyFile(line, newScore);
                         
                MainWindow.score = 0;
                MainWindow.coins = 0;
                break;
                        }
                        //count++;
                    }
                    //fileReader.close();
                    }
    
}
