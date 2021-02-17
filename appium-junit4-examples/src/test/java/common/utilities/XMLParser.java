package common.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLParser {
	private Document document;

	public XMLParser(File file) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		document = builder.parse(file.getAbsolutePath());
		document.getDocumentElement().normalize();

	}

	public Document getRootNode() {
		return document;
	}

	public boolean validateXML(String fileName) {
		Schema schema = null;
		try {
			String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
			SchemaFactory factory = SchemaFactory.newInstance(language);
			schema = factory.newSchema(new File(fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
		Validator validator = schema.newValidator();
		try {
			validator.validate(new DOMSource(document));
			return true;
		} catch (SAXException | IOException e) {
			return false;
		}
	}

	public List<String> getAttributeSpecific(String xpathExpression, String tagName) throws XPathExpressionException {
		List<String> attributeLi = new LinkedList<String>();
		XPath xPath = XPathFactory.newInstance().newXPath();
		NodeList nodeList = (NodeList) xPath.compile(xpathExpression).evaluate(document, XPathConstants.NODESET);
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node nNode = nodeList.item(i);
			// System.out.println("\nCurrent Element :" + nNode.getNodeName());

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				attributeLi.add(eElement.getElementsByTagName(tagName).item(0).getTextContent());
			}
		}
		return attributeLi;
	}

	public List<List<String>> getAttributeValue(String xpathExpression, int expectedNodeCount)
			throws XPathExpressionException {
		List<String> li;
		List<List<String>> completeLi = new LinkedList<List<String>>();
		// String val = "";
		XPath xPath = XPathFactory.newInstance().newXPath();
		NodeList nodeList = (NodeList) xPath.compile(xpathExpression).evaluate(document, XPathConstants.NODESET);
		for (int i = 0; i < nodeList.getLength(); i++) {
			li = new LinkedList<String>();
			Node nNode = nodeList.item(i);
			// NodeList allChildNodes = nNode.getChildNodes();
			for (int j = 0; j < expectedNodeCount; j++) {
				try {
					li.add(nNode.getChildNodes().item(j).getTextContent());
				} catch (Exception e) {
					li.add("");
				}
			}
			completeLi.add(li);
		}
		return completeLi;
	}

	public List<List<String>> getAttributeValue(File file)
			throws XPathExpressionException, FileNotFoundException, XMLStreamException {
		List<String> li = new ArrayList<String>();
		List<List<String>> completeLi = new LinkedList<List<String>>();
		// String val = "";
		// XPath xPath = XPathFactory.newInstance().newXPath();
		// NodeList nodeList = (NodeList)
		// xPath.compile(xpathExpression).evaluate(document,
		// XPathConstants.NODESET);
		XMLInputFactory factory = XMLInputFactory.newInstance();
		factory.setProperty("javax.xml.stream.isCoalescing", true);
		XMLEventReader eventReader = factory.createXMLEventReader(new FileReader(file));
		String dataTag = "";
		while (eventReader.hasNext()) {

			XMLEvent event = eventReader.nextEvent();
			if (event.isStartElement()) {
				StartElement element = (StartElement) event;
				QName name = element.getName();

				dataTag = name.toString().replaceAll("\\{.*\\}", "");
				if(dataTag.contains("loc") && li.stream().anyMatch(s -> s.contains("loc"))){
					dataTag="imageloc";
				}
//				System.out.println(dataTag);

			}

			if (event.isEndElement()) {
				EndElement element = (EndElement) event;
				String data = element.getName().toString();
//				System.out.println(data);
				// System.out.println(element.getName().toString());
				if (data.contains("url")) {
					completeLi.add(li);
					li = new LinkedList<>();
				}
			}
			if (event.isCharacters()) {
				Characters element = (Characters) event;

				// Iterator for accessing the metadeta related
				// the tag started.
				// Here, it would name of the company
				String data = element.getData();

				li.add(dataTag + ":" + data);
				
			}
			// completeLi.add(li);
		}
		
		return completeLi;

	}

	public NamedNodeMap getAttributes(Element element) throws ParserConfigurationException, SAXException, IOException {
		return element.getAttributes();
	}

	public NodeList getSubElementsSpecific(Element element, String subElementName) {
		return element.getElementsByTagName(subElementName);
	}

	public NodeList getSubElements(Element element) {
		return element.getChildNodes();
	}
	
	public NodeList getSubElementCount(String tagName) {
		return getRootNode().getElementsByTagName(tagName);
	}
}
