import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLOutput;
import java.util.HashMap;

public class CurrencyChange {
    private final String url = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";
    private HashMap<String, Double> cambi = new HashMap<>();

    public boolean getChangeRate(String XMLFile) throws IOException {
        int status;
        URL url = new URL(this.url);
        HttpsURLConnection service = (HttpsURLConnection) url.openConnection();
        service.setRequestProperty("Host", "www.ecb.europa.eu");
        service.setRequestProperty("Accept", "application/xml");
        service.setRequestProperty("Accept-Charset", "UTF-8");
        service.setRequestMethod("GET");
        service.setDoOutput(true);

        status = service.getResponseCode();
        if (status != 200) {
            return false;
        }
        Files.copy(service.getInputStream(), new File(XMLFile).toPath(), StandardCopyOption.REPLACE_EXISTING);
        return true;
    }

    public void parseXML(String XMLDocument) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory;
        DocumentBuilder builder;
        Document document;
        Element root, element;
        NodeList nodeList;
        String key, value;

        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
        document = builder.parse(XMLDocument);
        root = document.getDocumentElement();

        nodeList = root.getElementsByTagName("Cube");
        nodeList = nodeList.item(0).getChildNodes();
        nodeList = nodeList.item(0).getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++){
            element = (Element) nodeList.item(i);
            key = element.getAttribute("currency");
            value = element.getAttribute("rate");
            cambi.put(key, Double.parseDouble(value));
        }
        System.out.println(cambi);
    }


}
