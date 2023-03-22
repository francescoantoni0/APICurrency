import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        CurrencyChange c = new CurrencyChange();
        c.getChangeRate("prova.xml");
        c.parseXML("prova.xml");
    }
}