/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author RZ09
 */
public class HighScore extends JFrame {

    private static final String FILE_NAME = "scores.txt";
    
    private String scoreName;
    private int highScore;

    private HighScore(String name, int score) {
        this.scoreName = name;
        this.highScore = score;
    }

    public String getScoreName() {
        return scoreName;
    }

    public int getHighScore() {
        return highScore;
    }
    
    public static List<HighScore> retrieveHighScore() {
        List<HighScore> highScoreList = new ArrayList<>();
        try {
            if (!new File(FILE_NAME).exists()) {
                BufferedWriter bufferedWriter = new BufferedWriter(
                        new FileWriter(FILE_NAME));
                bufferedWriter.write("Goku 9001\r\n" + "Vegeta 9000\r\n"
                        + "Gohan 5000\r\n" + "Piccolo 4000\r\n" + "Yamcha 1");
                bufferedWriter.close();
            }

            BufferedReader bufferedReader = new BufferedReader(
                    new FileReader(FILE_NAME));
            String string;
            while (((string = bufferedReader.readLine()) != null)
                    || string.isEmpty()) {
                String[] highScore = string.split(" ");
                highScoreList.add(new HighScore(highScore[0],
                        Integer.parseInt(highScore[1])));
            }
            bufferedReader.close();
        } catch (IOException ex) {
            Logger.getLogger(HighScore.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return highScoreList;
        }
    }

    static void modifyFile(String oldString, String newString) {
        File fileToBeModified = new File(FILE_NAME);

        String oldContent = "";

        BufferedReader reader = null;

        FileWriter writer = null;

        try {
            reader = new BufferedReader(new FileReader(fileToBeModified));

            //Reading all the lines of input text file into oldContent
            String line = reader.readLine();

            while (line != null) {
                oldContent = oldContent + line + System.lineSeparator();

                line = reader.readLine();
            }

            //Replacing oldString with newString in the oldContent
            String newContent = oldContent.replaceAll(oldString, newString);

            //Rewriting the input text file with newContent
            writer = new FileWriter(fileToBeModified);

            writer.write(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //Closing the resources

                reader.close();

                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void checkScore() throws FileNotFoundException, IOException {
        FileReader fileReader = new FileReader(FILE_NAME);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        int count = 0;
        String line = null;
        int scoreNumber;
        HighScore[] players = new HighScore[5];
        while ((line = bufferedReader.readLine()) != null) {
            String[] player = line.split(" ");
            scoreNumber = Integer.parseInt(player[1]);
            //players[count] = new HighScore(player[0], scoreNumber);
            if (MainWindow.score > scoreNumber) {
                /**
                 * for (int i2 = 0; i2 > 5; i2++){ players[count] = new
                 * HighScore("USER INPUT", MainWindow.score); BufferedWriter
                 * bufferedWriter; bufferedWriter = new BufferedWriter(new
                 * FileWriter("score.txt", false));
                 * bufferedWriter.write(players[0].scoreName);
                 * bufferedWriter.close(); } *
                 */
                String name = JOptionPane.showInputDialog("Please enter your name: ");
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
