import character.Player;
import org.xml.sax.SAXException;
import xml.UNHandler;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Player player;
        Scanner keyboard = new Scanner(System.in);
        try {
            // Path to the resource folder
            String resourcePath = "/UticaNationalMap.xml";

            // Load the file as a resource stream
            InputStream input = Main.class.getResourceAsStream(resourcePath);
            if (input == null) {
                throw new IllegalArgumentException("File not found in resources: " + resourcePath);
            }

            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser saxParser = spf.newSAXParser();
            UNHandler theHandler = new UNHandler();
            saxParser.parse(input, theHandler);
            player = theHandler.getThePlayer();

        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }

        player.play(keyboard);
    }
}
