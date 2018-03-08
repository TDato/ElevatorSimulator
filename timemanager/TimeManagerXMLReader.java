package timemanager;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
public class TimeManagerXMLReader {

		private TimeManagerXMLReader() {
			
		}
		
		public static ArrayList<String> timeManagerReader()/*throws InvalidArgumentException*/{
			
			ArrayList<String> data = new ArrayList<>();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			try {
				DocumentBuilder builder = dbf.newDocumentBuilder();
				Document doc = builder.parse("XMLTimeManager.xml");
				
				NodeList timeManagerList = doc.getElementsByTagName("time");
				
				for(int i = 0; i<timeManagerList.getLength(); i++){
					Node t = timeManagerList.item(i);
					if(t.getNodeType() == Node.ELEMENT_NODE) {
						Element timeManager = (Element)t;
						NodeList timeList = timeManager.getChildNodes();
						for(int j = 0; j<timeList.getLength(); j++) {
							Node e = timeList.item(j);
							if(e.getNodeType() == Node.ELEMENT_NODE) {
								data.add(timeManager.getElementsByTagName("peopleperminute").item(0).getTextContent());
								data.add(timeManager.getElementsByTagName("duration").item(0).getTextContent());
							}
						}
						
					}
				}

			} catch (ParserConfigurationException | SAXException | IOException | DOMException e) {
	            e.printStackTrace();
	        }
			
			return new ArrayList<String>(data);
		}
	}

