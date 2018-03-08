package building;

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

public class BuildingXMLReader {
	
	private BuildingXMLReader() {
		
	}
	
	public static ArrayList<Integer> buildingReader()/*throws InvalidArgumentException*/{
		
		ArrayList<Integer> data = new ArrayList<>();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = dbf.newDocumentBuilder();
			Document doc = builder.parse("XMLBuilding.xml");
			
			NodeList buildingList = doc.getElementsByTagName("building");
			
			for(int i = 0; i<buildingList.getLength(); i++){
				Node b = buildingList.item(i);
				if(b.getNodeType() == Node.ELEMENT_NODE) {
					Element building = (Element)b;
					NodeList elevatorList = building.getChildNodes();
					for(int j = 0; j<elevatorList.getLength(); j++) {
						Node e = elevatorList.item(j);
						if(e.getNodeType() == Node.ELEMENT_NODE) {
							Element elevator = (Element)e;

							data.add(Integer.parseInt(building.getElementsByTagName("floor").item(0).getTextContent()));
							data.add(Integer.parseInt(building.getElementsByTagName("elevator").item(0).getTextContent()));
						}
					}
					
				}
			}

		} catch (ParserConfigurationException | SAXException | IOException | DOMException e) {
            e.printStackTrace();
        }
		
		return new ArrayList<Integer>(data);
	}
}