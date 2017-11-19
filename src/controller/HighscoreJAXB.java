package controller;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import view.MainWindow;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "highscores")
public class HighscoreJAXB {

    private static final String FILE_NAME = "highscores.txt";

    private static HighscoreJAXB highscoreJAXB = new HighscoreJAXB();

    @XmlElement(name = "highscore")
    private List<Highscore> highscoreList;

    public static HighscoreJAXB getHighscoreJAXB() {
        return highscoreJAXB;
    }

    public static void setHighscoreJAXB(HighscoreJAXB highscoreJAXB) {
        HighscoreJAXB.highscoreJAXB = highscoreJAXB;
    }

    public List<Highscore> getHighscoreList() {
        return highscoreList;
    }

    public void setHighscoreList(List<Highscore> highscoreList) {
        this.highscoreList = highscoreList;
    }

    public static void checkScore() {
        List<Highscore> highscoreList = highscoreJAXB.getHighscoreList();

        if (MainWindow.score > highscoreList.get(highscoreList.size() - 1)
                .getScore()) {
            String name = JOptionPane.showInputDialog("Please enter your name:");

            if (name.equals("")) {
                name = "Unknown";
            }

            highscoreList.add(new Highscore(name, MainWindow.score));
            highscoreList.sort((highscore1, highscore2) -> {
                return highscore1.getScore() > highscore2.getScore() ? -1
                        : highscore1.getScore() < highscore2.getScore() ? 1 : 0;
            });
            highscoreList.remove(highscoreList.size() - 1);
            highscoreJAXB.setHighscoreList(highscoreList);
            marshalHighscoreList();
        }
    }

    public static void marshalHighscoreList() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(HighscoreJAXB.class);
            Marshaller marshaller = jaxbContext.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal(highscoreJAXB, new File(FILE_NAME));
        } catch (JAXBException ex) {
            Logger.getLogger(HighscoreJAXB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void unmarshalHighscoreList() {
        try {
            File file = new File(FILE_NAME);

            if (!file.exists()) {
                highscoreJAXB.setHighscoreList(Arrays.asList(
                        new Highscore("Goku", 5),
                        new Highscore("Vegeta", 4),
                        new Highscore("Gohan", 3),
                        new Highscore("Piccolo", 2),
                        new Highscore("Yamcha", 1)));
                marshalHighscoreList();
            }

            JAXBContext jaxbContext = JAXBContext.newInstance(HighscoreJAXB.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            highscoreJAXB = (HighscoreJAXB) unmarshaller.unmarshal(file);
        } catch (JAXBException ex) {
            Logger.getLogger(HighscoreJAXB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
