package app.main.xml;

import app.main.model.Carte;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import app.main.model.Intersection;
import app.main.model.Request;
import app.main.model.Segment;

public class XMLdeserializer {
    private Map<Long, Intersection> mapIntersections;
    private List<Segment> segments = new ArrayList<>();
    private Intersection warehouse;
    private List<Request> requete;

    public XMLdeserializer() throws Exception {
        this.mapIntersections = new HashMap<>();
        this.segments = new ArrayList<>();
        this.warehouse = new Intersection();
        this.requete = new LinkedList<>();
    }

    public void main(String[] args) throws Exception {

        XMLdeserializer xmLdeserializer = new XMLdeserializer();

    }

    public LinkedList<Request> loadRequestDataFromXml(File file) throws Exception {

        // Crée un tableau dynamique.
        LinkedList<Request> requestList = new LinkedList<>();

        // Crée un objet Document qui représente les données du fichier XML
        // sous la forme d’une hiérarchie d’objets de type Node. Un objet de
        // type nœud peut représenter aussi bien un élément, qu’un nœud
        // de texte ou un attribut.
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(file);
        doc.getDocumentElement().normalize();

        // Recherche tous les elements <Intersection>
        NodeList requestNodeList = doc.getElementsByTagName("request");

        // Pour chaque élément XML de la liste
        for (int i = 0; i < requestNodeList.getLength(); i++) {
            Node node = requestNodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                // Récupère l'élément
                Element requestElement = (Element) node;

                // Crée un nouvel objet de type intersection
                Request request = new Request();

                if (requestElement.hasAttribute("intersectionId")) {
                    request.setIntersectionId(Long.parseLong(requestElement.getAttribute("intersectionId")));
                } else {
                    request.setIntersectionId(0);
                }
                if (requestElement.hasAttribute("timeWindow")) {
                    request.setTimeWindow(Long.parseLong(requestElement.getAttribute("timeWindow")));
                } else {
                    request.setTimeWindow(0);
                }
                if (requestElement.hasAttribute("courierId")) {
                    request.setCourierId(Long.parseLong(requestElement.getAttribute("courierId")));
                } else {
                    request.setCourierId(0);
                }

                // Ajoute la intersectionne à la liste
                requestList.add(request);
            }
        }

        // Renvoie la référence à l’objet intersectionList
        return requestList;
    }

    public Map<Long, Intersection> loadIntersectionDataFromXml(File file) throws Exception {

        // Crée un tableau dynamique.
        Map<Long, Intersection> intersectionMap = new HashMap<>();

        // Crée un objet Document qui représente les données du fichier XML
        // sous la forme d’une hiérarchie d’objets de type Node. Un objet de
        // type nœud peut représenter aussi bien un élément, qu’un nœud
        // de texte ou un attribut.
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(file);
        doc.getDocumentElement().normalize();

        // Recherche tous les elements <Intersection>
        NodeList intersectionNodeList = doc.getElementsByTagName("intersection");

        // Pour chaque élément XML de la liste
        for (int i = 0; i < intersectionNodeList.getLength(); i = i + 1) {
            Node node = intersectionNodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                // Récupère l'élément
                Element intersectionElement = (Element) node;

                // Crée un nouvel objet de type intersection
                Intersection intersection = new Intersection();
                if (intersectionElement.hasAttribute("id") && intersectionElement.hasAttribute("latitude")
                        && intersectionElement
                                .hasAttribute("longitude")) {
                    intersection.setId(Long.parseLong(intersectionElement.getAttribute("id")));
                    intersection.setLatitude(Double.parseDouble(intersectionElement.getAttribute("latitude")));

                    intersection.setLongitude(Double.parseDouble(intersectionElement.getAttribute("longitude")));

                    // Ajoute la intersectionne à la liste
                    intersectionMap.put(intersection.getId(), intersection);
                }
            }
        }

        // Renvoie la référence à l’objet intersectionList
        return intersectionMap;
    }

    public Intersection loadWarehouseDataFromXml(File file, Carte carte) throws Exception {

        // Initializer l'objet à retourner.
        Intersection warehouse = new Intersection();
        // Retrouver la map des intersctions de la carte
        Map<Long, Intersection> intersections = carte.getIntersectionList();

        // Crée un objet Document qui représente les données du fichier XML
        // sous la forme d’une hiérarchie d’objets de type Node. Un objet de
        // type nœud peut représenter aussi bien un élément, qu’un
        // de texte ou un attribut.
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(file);
        doc.getDocumentElement().normalize();

        // Recherche tous les elements <Intersection>
        NodeList warehouseNodeList = doc.getElementsByTagName("warehouse");

        // Pour chaque élément XML de la liste
        for (int i = 0; i < warehouseNodeList.getLength(); i = i + 1) {
            Node node = warehouseNodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                // Récupère l'élément
                Element warehouseElement = (Element) node;

                warehouse = intersections.get(Long.parseLong(warehouseElement.getAttribute("address")));
            }
        }
        return warehouse;
    }

    public List<Segment> loadSegmentDataFromXml(File file) throws Exception {

        // Crée un tableau dynamique.
        List<Segment> segmentList = new ArrayList<>();

        // Crée un objet Document qui représente les données du fichier XML
        // sous la forme d’une hiérarchie d’objets de type Node. Un objet de
        // type nœud peut représenter aussi bien un élément, qu’un nœud
        // de texte ou un attribut.
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(file);
        doc.getDocumentElement().normalize();

        // Recherche tous les elements <Segment>
        NodeList segmentNodeList = doc.getElementsByTagName("segment");

        // Pour chaque élément XML de la liste
        for (int i = 0; i < segmentNodeList.getLength(); i = i + 1) {
            Node node = segmentNodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                // Récupère l'élément
                Element segmentElement = (Element) node;

                // Crée un nouvel objet de type segment
                Segment segment = new Segment();
                if (segmentElement.hasAttribute("origin") && segmentElement.hasAttribute("destination")
                        && segmentElement
                                .hasAttribute("name")
                        && segmentElement
                                .hasAttribute("length")) {
                    segment.setIdOrigin(Long.parseLong(segmentElement.getAttribute("origin")));
                    segment.setIdDestination(Long.parseLong(segmentElement.getAttribute("destination")));

                    segment.setStreet(segmentElement.getAttribute("name"));
                    segment.setLength(Double.parseDouble(segmentElement.getAttribute("length")));

                    // Ajoute la segmentne à la liste
                    segmentList.add(segment);
                }

            }
        }

        // Renvoie la référence à l’objet segmentList
        return segmentList;
    }

    private void printIntersection(Intersection intersection) {
        System.out.println(intersection.toString());
    }

    public Map<Long, Intersection> getMapIntersections() {
        return mapIntersections;
    }

    public List<Segment> getSegments() {
        return segments;
    }

    public Intersection getWarehouse() {
        return warehouse;
    }
}
