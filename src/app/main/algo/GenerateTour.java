package app.main.algo;

import app.main.model.*;

import java.util.*;

public class GenerateTour {
    private Tour tour;
    private Courier courier;
    private boolean isTourPossible = true;
    private Carte map;
    static double speed = 15.0/3600;// en m/ms

    public GenerateTour(Courier courier, Carte map) {
        this.courier = courier;
        this.map = map;
        if (courier.getRequestList().size() < 1){
            //this.isTourPossible = false;
        } else {
            runTSP();
        }
    }

    public Tour getTour() {
        if (isTourPossible()) return tour;
        else return null;
    }

    public boolean isTourPossible() {
        return isTourPossible;
    }

    private void runTSP (){
        HashMap<Long, Integer> correspondanceIndex = new HashMap<>();
        HashMap<Integer, Long> correspondanceId = new HashMap<>();
        List<Long> etapes = new ArrayList<>();
        correspondanceIndex.put(map.getWarehouse().getId(),0);
        correspondanceId.put(0,map.getWarehouse().getId());
        etapes.add(map.getWarehouse().getId());
        List<Request> listRequest = courier.getRequestList();
        for (int i = 0; i<listRequest.size(); i++){
            long intersectionId = listRequest.get(i).getIntersectionId();
            correspondanceIndex.put(intersectionId, i+1);
            correspondanceId.put(i+1,intersectionId);
            etapes.add(listRequest.get(i).getIntersectionId());
        }
        double[][] arcImpossibles = setImpossibleArcs(correspondanceIndex, listRequest);
        Graph g = new CompleteGraph(etapes, correspondanceIndex, arcImpossibles);
        g.FillGraph(map);
        TSP tsp = new TSP1();
        tsp.searchSolution(80000, g, g.getCouts());
        List<Segment> path = new ArrayList<>();
        List<Delivery> deliveries = new ArrayList<>();
        long arrivedTime = 7*60*60*1000; // pour 8h00
        long leftTime = 7*60*60*1000;;
        for (int i=0; i<etapes.size()-1; i++) {
            if (i > 0) { //pour ne pas considérer le départ
                if (arrivedTime > ((courier.getTimeWindowById(correspondanceId.get(tsp.getSolution(i)))) * 60 * 60 * 1000)) {
                    this.isTourPossible = false;
                    break;
                }
            }
            List<Node> chemins = g.getCheminsIntermediares(tsp.getSolution(i), tsp.getSolution(i + 1));
            if (tsp.getSolution(i) != null && tsp.getSolution(i + 1) != null && chemins != null) {
                for (int j = 0; j < chemins.size() - 1; j++) {
                    Segment segment = this.map.getSegmentByIds(chemins.get(j).id, chemins.get(j + 1).id);
                    path.add(segment);
                }
                long idIntersection = chemins.get(0).id;
                long waitingTime = 0;
                if (i > 0) {
                    long barriereHoraire = (courier.getTimeWindowById(correspondanceId.get(tsp.getSolution(i))) - 1) * 60 * 60 * 1000;
                    if (arrivedTime < barriereHoraire) {
                        waitingTime = barriereHoraire - arrivedTime;
                    }
                    leftTime = arrivedTime + waitingTime + 5 * 60 * 1000;
                }
                Delivery delivery = new Delivery(idIntersection, new Date(arrivedTime), new Date(leftTime), waitingTime);
                deliveries.add(delivery);
                arrivedTime = leftTime + (long) (g.getCout(tsp.getSolution(i), tsp.getSolution(i + 1)) / speed);
            }else this.isTourPossible = false;
        }
        //Ajout de la dernière livraison
        long waitingTime = 0;
        long barriereHoraire = (courier.getTimeWindowById(correspondanceId.get(tsp.getSolution(etapes.size()-1)))-1)*60*60*1000;
        if (arrivedTime < barriereHoraire){
            waitingTime = barriereHoraire - arrivedTime;
        }
        if (arrivedTime > ((courier.getTimeWindowById(correspondanceId.get(tsp.getSolution(etapes.size()-1))))*60*60*1000)){
            this.isTourPossible = false;
        }
        leftTime = arrivedTime + waitingTime + 5*60*1000;
        Delivery lastDelivery = new Delivery(correspondanceId.get(tsp.getSolution(etapes.size()-1)),new Date(arrivedTime),new Date(leftTime), waitingTime);
        deliveries.add(lastDelivery);
        //Retour à l'entrepot depuis la dernière intersection
        List<Node> chemins = g.getCheminsIntermediares(tsp.getSolution(etapes.size() - 1), 0);
        if(tsp.getSolution(etapes.size()-1)!= null && chemins != null) {

            for (int j = 0; j < chemins.size() - 1; j++) {
                Segment segment = this.map.getSegmentByIds(chemins.get(j).id, chemins.get(j + 1).id);
                path.add(segment);
            }
            arrivedTime = leftTime + (long) (g.getCout(tsp.getSolution(etapes.size() - 1), 0) / speed);
            if (arrivedTime > (11 * 60 * 60 * 1000)) { //si le livreur rentre après 12h
                this.isTourPossible = false;
            }
            Delivery delivery = new Delivery(map.getWarehouse().getId(), new Date(arrivedTime), new Date(0), 0);
            deliveries.add(delivery);
            this.tour = new Tour(deliveries, path, courier);
        }else this.isTourPossible = false;
    }

    private double[][] setImpossibleArcs (HashMap<Long, Integer> correspondanceIndex, List<Request> requests){
        double[][] arcImpossibles = new double[correspondanceIndex.size()][correspondanceIndex.size()];
        for (Request request1 : requests){
            for (Request request2 : requests){
                if (request1 == request2){
                    arcImpossibles[correspondanceIndex.get(request1.getIntersectionId())][correspondanceIndex.get(request2.getIntersectionId())] = Double.MAX_VALUE;
                }
                else if (request2.getTimeWindow() < request1.getTimeWindow()){
                    arcImpossibles[correspondanceIndex.get(request1.getIntersectionId())][correspondanceIndex.get(request2.getIntersectionId())] = Double.MAX_VALUE;
                }
                else {}
            }
        }
        return arcImpossibles;
    }
}

