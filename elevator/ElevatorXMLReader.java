package elevator;

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

public class ElevatorXMLReader {
	
	private ElevatorXMLReader() {
			
	}
		
	public static ArrayList<String> elevatorReader()/*throws InvalidArgumentException*/{
		
		ArrayList<String> data = new ArrayList<>();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = dbf.newDocumentBuilder();
			Document doc = builder.parse("XMLElevator.xml");
			
			NodeList elevatorList = doc.getElementsByTagName("elevator");
			
			for(int i = 0; i<elevatorList.getLength(); i++){
				Node b = elevatorList.item(i);
				if(b.getNodeType() == Node.ELEMENT_NODE) {
					Element elevator = (Element)b;
					NodeList timePerFloorList = elevator.getChildNodes();
					for(int j = 0; j<timePerFloorList.getLength(); j++) {
						Node f = timePerFloorList.item(j);
						if(f.getNodeType() == Node.ELEMENT_NODE) {
							data.add(elevator.getElementsByTagName("timeperfloor").item(0).getTextContent());
							data.add(elevator.getElementsByTagName("maxriders").item(0).getTextContent());
							data.add(elevator.getElementsByTagName("maxidle").item(0).getTextContent());
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
