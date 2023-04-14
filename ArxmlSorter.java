package Lap6;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ArxmlSorter {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java ArxmlSorter <input.arxml>");
            return;
        }

        String inputFileName = args[0];
        String outputFileName = getOutputFileName(inputFileName);

        try {
            validateFileExtension(inputFileName);
            
            File inputFile = new File(inputFileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            
            if (doc.getDocumentElement().getChildNodes().getLength() == 0) {
                throw new EmptyAutosarFileException("Input ARXML file is empty.");
            }

            NodeList containerList = doc.getElementsByTagName("CONTAINER");
            ArrayList<Element> containers = new ArrayList<Element>();
            for (int i = 0; i < containerList.getLength(); i++) {
                containers.add((Element) containerList.item(i));
            }

            Collections.sort(containers, new Comparator<Element>() {
                public int compare(Element e1, Element e2) {
                    String name1 = e1.getElementsByTagName("SHORT-NAME").item(0).getTextContent();
                    String name2 = e2.getElementsByTagName("SHORT-NAME").item(0).getTextContent();
                    return name1.compareTo(name2);
                }
            });

            Element rootElement = doc.getDocumentElement();
            for (Element container : containers) {
                rootElement.appendChild(container);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(outputFileName));
            transformer.transform(source, result);
            
            System.out.println("Containers sorted successfully. Output written to " + outputFileName);
        } catch (NotVaildAutosarFileException e) {
            System.err.println("Error: " + e.getMessage());
        }  catch (SAXException | IOException e) {
        	System.err.println("Error: Input file does not have any content" );
        }catch (EmptyAutosarFileException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getOutputFileName(String inputFileName) {
        int dotIndex = inputFileName.lastIndexOf(".");
        String fileNameWithoutExtension = inputFileName.substring(0, dotIndex);
        String fileExtension = inputFileName.substring(dotIndex);
        return fileNameWithoutExtension + "_mod" + fileExtension;
    }

    private static void validateFileExtension(String fileName) throws NotVaildAutosarFileException {
        if (!fileName.endsWith(".arxml")) {
            throw new NotVaildAutosarFileException("Input file does not have .arxml extension.");
        }
    }
}

@SuppressWarnings("serial")
class NotVaildAutosarFileException extends Exception {
    public NotVaildAutosarFileException(String message) {
        super(message);
    }
}

@SuppressWarnings("serial")
class EmptyAutosarFileException extends RuntimeException {
    public EmptyAutosarFileException(String message) {
        super(message);
    }
}
