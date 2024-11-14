package service;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class Parser {

        private File file;

        public Parser(File file){
            this.file = file;
        }

        public String parseXML(String word, String origLanguage, String destLanguage) throws FileNotFoundException {

            XMLInputFactory factory = XMLInputFactory.newInstance();
            try {
                XMLStreamReader streamReader = factory.createXMLStreamReader(new FileInputStream(file));

                boolean inWord = false;
                boolean inEquivalent = false;

                boolean foundOriginLanguage = false;
                boolean foundDestinationLanguage = false;

                boolean isFoundedOrigin = false;

                String destinationWord = null;

                while (streamReader.hasNext()) {
                    streamReader.next();

                    if (streamReader.getEventType() == XMLStreamReader.START_ELEMENT) {

                        String contentNode = null;
                        if (streamReader.getLocalName().equalsIgnoreCase("word-translate")) {
                            contentNode = streamReader.getElementText();
                            if (foundDestinationLanguage) {
                                destinationWord = contentNode;
                            }
                        }

                        if (streamReader.getLocalName().equalsIgnoreCase("word")) {
                            inWord = true;
                        }

                        if (streamReader.getLocalName().equalsIgnoreCase("equivalent")) {
                            inEquivalent = true;
                        }

                        if (streamReader.getLocalName().equalsIgnoreCase("language") && inWord) {
                            String contentNodeLanguage = streamReader.getElementText();
                            if (contentNodeLanguage.equalsIgnoreCase(origLanguage)) {
                                foundOriginLanguage = true;
                            } else if (contentNodeLanguage.equalsIgnoreCase(destLanguage)) {
                                foundDestinationLanguage = true;
                            }
                        }

                        if (contentNode != null) {
                            if (contentNode.equalsIgnoreCase(word) && foundOriginLanguage && inEquivalent) {
                                isFoundedOrigin = true;
                            }
                        }

                    }
                    if (streamReader.getEventType() == XMLStreamReader.END_ELEMENT) {

                        if (streamReader.getLocalName().equalsIgnoreCase("word")) {
                            if (isFoundedOrigin && destinationWord != null)
                                return destinationWord;

                            inWord = false;
                            isFoundedOrigin = false;
                        }

                        if (streamReader.getLocalName().equalsIgnoreCase("equivalent")) {
                            inEquivalent = false;
                            foundOriginLanguage = false;
                            foundDestinationLanguage = false;
                        }

                    }

                }
            } catch (XMLStreamException ex) {
                System.err.println(ex.getMessage());
                return null;
            }

            return null;
        }
    }


