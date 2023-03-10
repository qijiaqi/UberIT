package app.main.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import app.main.model.Request;

public class XMLserializer {

    private Element requestRoot;

    private Document document;
    private static XMLserializer instance = null;

    public XMLserializer() {
    }

    public static XMLserializer getInstance() {
        if (instance == null)
            instance = new XMLserializer();
        return instance;
    }

    /**
     * Open an XML file and write an XML description of the requests in it
     * 
     * @param requests the list of requests to serialise
     * @throws ParserConfigurationException
     * @throws TransformerFactoryConfigurationError
     * @throws TransformerException
     * @throws ExceptionXML
     */
    public void save(List<Request> requests, File xml)
            throws ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException {
        StreamResult result = new StreamResult(xml);
        document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        requestRoot = document.createElement("requestRoot");
        document.appendChild(requestRoot);
        for (Request myRequest : requests) {
            requestRoot.appendChild(createRequestElt(myRequest));
        }

        DOMSource source = new DOMSource(document);
        Transformer xformer = TransformerFactory.newInstance().newTransformer();
        xformer.setOutputProperty(OutputKeys.INDENT, "yes");
        xformer.transform(source, result);
    }

    private Element createRequestElt(Request request) {
        Element req = document.createElement("request");
        createAttribute(req, "intersectionId", Long.toString(request.getIntersectionId()));
        createAttribute(req, "timeWindow", Long.toString(request.getTimeWindow()));
        createAttribute(req, "courierId", Long.toString(request.getCourierId()));

        return req;
    }

    private void createAttribute(Element root, String name, String value) {
        Attr attribut = document.createAttribute(name);
        root.setAttributeNode(attribut);
        attribut.setValue(value);
    }

}
