package app.main.model;

import app.main.observer.Observable;
import app.main.xml.XMLdeserializer;
import java.util.Random;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class Carte
 */
public class Carte extends Observable {
    private double minLat;
    private double maxLat;
    private double minLong;
    private double maxLong;
    private Intersection warehouse;
    private String fichierSource;
    private Map<Long, Intersection> mapIntersections;
    private Map<Long, Intersection> badIntersections;
    private List<Segment> segmentList;
    private List<Request> requestList;
    private Map<Long, Courier> courierMap;

    /**
     * Constructeur par défaut de Carte
     * créer les objets de la Carte
     * Créer 3 couriers
     */
    public Carte() {
        this.warehouse = null;
        this.mapIntersections = new HashMap<>();
        this.segmentList = new ArrayList<>();
        this.requestList = new LinkedList<>();
        this.courierMap = new HashMap<>();
        // reqList3.add(request3);
        Courier courier1 = new Courier(1);
        Courier courier2 = new Courier(2);
        Courier courier3 = new Courier(3);
        this.courierMap.put(courier1.getId(), courier1);
        this.courierMap.put(courier2.getId(), courier2);
        this.courierMap.put(courier3.getId(), courier3);
    }

    /**
     * Surcharge du constructeur de Carte
     * 
     * @param mapIntersections
     * @param segmentList
     */
    public Carte(Map<Long, Intersection> mapIntersections, List<Segment> segmentList) {
        this.mapIntersections = mapIntersections;
        this.segmentList = segmentList;
    }

    /**
     * Méthode loadMap qui fait affecte les attributs de la Classe Carte à partir
     * d'un fichier XML passer en paramètre.
     * Le fichier est désérialisé par la classe {@link XMLdeserializer}
     * 
     * @param xmlFile
     * @throws Exception
     */
    public void loadMap(File xmlFile) throws Exception {
        XMLdeserializer xmLdeserializer = new XMLdeserializer();
        this.mapIntersections = xmLdeserializer.loadIntersectionDataFromXml(xmlFile);
        List<Segment> segmentValide = new ArrayList<>();
        segmentValide = xmLdeserializer.loadSegmentDataFromXml(xmlFile);
        for (Segment segment : segmentValide) {
            if (mapIntersections.containsKey(segment.getIdDestination())
                    && mapIntersections.containsKey(segment.getIdOrigin())) {
                segmentList.add(segment);
            }
        }
        this.warehouse = xmLdeserializer.loadWarehouseDataFromXml(xmlFile, this);
        boolean correctIntersection;
        badIntersections = new HashMap<>();
        resetCouriers();
        resetRequests();
        for (Long id : mapIntersections.keySet()) {
            correctIntersection = false;
            for (Segment seg : segmentList) {
                if (seg.getIdOrigin() == id) {
                    correctIntersection = true;
                }
            }
            if (!correctIntersection) {
                badIntersections.put(id, getIntersectionById(id));
            }
        }
        // Set<Segment> badSegments = new HashSet<>();
        // for (Long id : badIntersection) {
        // badSegments.addAll(getSegmentsByDestinationId(id));
        // }
        // for (Segment seg : badSegments) {
        // segmentList.remove(seg);
        // }

        minLat = calcMinLat();
        minLong = calcMinLong();
        maxLat = calcMaxLat();
        maxLong = calcMaxLong();
        this.requestList.clear();

        this.notifyObservers();

        List<Intersection> randomList = genererRandomRequests(20);
    }

    /**
     * Méthode loadRequest qui affecte à la requestList de la Carte des requêtes.
     * Les requêtes sont charger à partir d'un fichier XML passer en paramètre et
     * désérialisé par la classe {@link XMLdeserializer}
     * 
     * @param xmlFile
     * @throws Exception
     */
    public void loadRequest(File xmlFile) throws Exception {
        XMLdeserializer xmLdeserializer = new XMLdeserializer();
        List<Request> requestListAdded = xmLdeserializer.loadRequestDataFromXml(xmlFile);
        Courier currentCourier;
        int i = 1;
        for (Request request : requestListAdded) {
            if (courierMap.containsKey(request.getCourierId())
                    && mapIntersections.containsKey(request.getIntersectionId())
                    && (request.getTimeWindow() == 8 || request
                            .getTimeWindow() == 9 || request
                                    .getTimeWindow() == 10
                            || request
                                    .getTimeWindow() == 11)) {
                this.requestList.add(request);
                currentCourier = courierMap.get(request.getCourierId());
                currentCourier.addRequestCourier(request);
            } else {
                System.out.println("Request " + i + " on the XML invalid because one attribute is wrong or missing");
            }
            i++;
        }
        this.notifyObservers();
    }

    /**
     * @param id
     * @return
     */
    public Intersection getIntersectionById(Long id) {
        return mapIntersections.get(id);
    }

    /**
     * @param idOrigin
     * @param idDestination
     * @return
     */
    public Segment getSegmentByIds(Long idOrigin, Long idDestination) {
        for (Segment segment : this.segmentList) {
            if (segment.getIdOrigin() == idOrigin && segment.getIdDestination() == idDestination) {
                return segment;
            }
        }
        return null;
    }

    /**
     * @param id
     * @return
     */
    public Courier getCourierById(Long id) {
        return courierMap.get(id);
    }

    public Intersection getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Intersection warehouse) {
        this.warehouse = warehouse;
    }

    public String getFichierSource() {
        return fichierSource;
    }

    public void setFichierSource(String fichierSource) {
        this.fichierSource = fichierSource;
    }

    public Map<Long, Intersection> getIntersectionList() {
        return mapIntersections;
    }

    public void setIntersectionMap(Map<Long, Intersection> mapIntersections) {
        this.mapIntersections = mapIntersections;
    }

    public List<Segment> getSegmentList() {
        return segmentList;
    }

    public Map<Long, Intersection> getMapIntersections() {
        return mapIntersections;
    }

    public void setSegmentList(List<Segment> segmentList) {
        this.segmentList = segmentList;
    }

    /**
     * @return
     */
    public double calcMinLat() {
        double minLat = Double.MAX_VALUE;
        for (Intersection intersection : mapIntersections.values()) {
            if (intersection.getLatitude() < minLat) {
                minLat = intersection.getLatitude();
            }
        }
        return minLat;
    }

    /**
     * @return
     */
    public double calcMaxLat() {
        double maxLat = 0;
        for (Intersection intersection : mapIntersections.values()) {
            if (intersection.getLatitude() > maxLat) {
                maxLat = intersection.getLatitude();
            }
        }
        return maxLat;
    }

    /**
     * @return
     */
    public double calcMinLong() {
        double minLong = Double.MAX_VALUE;
        for (Intersection intersection : mapIntersections.values()) {
            if (intersection.getLongitude() < minLong) {
                minLong = intersection.getLongitude();
            }
        }
        return minLong;
    }

    /**
     * @return
     */
    public double calcMaxLong() {
        double maxLong = 0;
        for (Intersection intersection : mapIntersections.values()) {
            if (intersection.getLongitude() > maxLong) {
                maxLong = intersection.getLongitude();
            }
        }
        return maxLong;
    }

    public double getMinLat() {
        return minLat;
    }

    public double getMaxLat() {
        return maxLat;
    }

    public double getMinLong() {
        return minLong;
    }

    public double getMaxLong() {
        return maxLong;
    }

    public List<Request> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<Request> requestList) {
        this.requestList = requestList;
    }

    public void addRequest(Request request) {
        this.requestList.add(request);
    }

    public void addCourier(Courier courier) {
        this.courierMap.put(courier.getId(), courier);
    }

    /**
     * @param id
     */
    public void deleteCourier(long id) {
        this.courierMap.remove(id);
        int idx = 0;

        while (idx < requestList.size()) {
            if (requestList.get(idx).getCourierId() == id) {
                // Remove item
                requestList.remove(idx);

            } else {
                ++idx;
            }
        }

    }

    /**
     * @param intersectionId
     * @return
     */
    public Set<String> getStreetsIntersection(Long intersectionId) {
        Set<String> streets = new HashSet<>();
        for (Segment segment : segmentList) {
            if (segment.getIdDestination() == intersectionId || segment.getIdOrigin() == intersectionId) {
                streets.add(segment.getStreet());
            }
        }
        return streets;
    }

    public Set<Segment> getSegmentsByDestinationId(Long destinationId) {
        Set<Segment> segments = new HashSet<Segment>();
        for (Segment segment : segmentList) {
            if (segment.getIdDestination() == destinationId) {
                segments.add(segment);
            }
        }
        return segments;
    }

    public Map<Long, Courier> getCourierMap() {
        return courierMap;
    }

    public void addCourier(Long idCourier, Courier courier) {
        this.courierMap.put(idCourier, courier);
    }

    public void deleteRequest(Request request) {
        requestList.remove(request);
    }

    public void resetCouriers() {
        for (Map.Entry<Long, Courier> courierKV : courierMap.entrySet()) {
            Courier courier = courierKV.getValue();
            courier.reset();
            courierMap.put(courierKV.getKey(), courier);
        }
    }

    public void resetRequests() {
        this.requestList = new LinkedList<>();
    }

    /**
     * Genere aléatoirement une liste de n intersections parmi celle disponible dans
     * la carte chargée.
     * 
     * @param nb
     * @return
     */
    public List<Intersection> genererRandomRequests(int nb) {
        List<Intersection> intersectionRandom = new ArrayList<>();
        Collection<Intersection> values = mapIntersections.values();
        ArrayList<Intersection> listOfValues = new ArrayList<>(values);
        int size = listOfValues.size();

        for (int i = 0; i < nb; i++) {
            double rand = Math.random();
            Intersection intersection = new Intersection();
            intersection = listOfValues.get((int) (size * rand + 1));
            intersectionRandom.add(intersection);
        }

        return intersectionRandom;
    }

    /**
     * @return
     */
    public boolean hasTours() {
        boolean hasValues = false;
        for (Map.Entry<Long, Courier> courierKV : courierMap.entrySet()) {
            Courier courier = courierKV.getValue();
            if (courier.getTour() != null) {
                hasValues = true;
            }
        }
        return hasValues;
    }

    public Request getRequestByIntersectionId(long id) {
        Request result;
        for (Request r : this.requestList) {
            if (r.getIntersectionId() == id) {
                return r;
            }
        }
        return null;
    }
}