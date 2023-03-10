package app.main.algo;
import app.main.model.Carte;
import app.main.model.Intersection;
import app.main.model.Segment;

import java.util.*;

public class Dijkstra {

    public List<Node> getSuccesseur(long origin, List<Segment> listSegment) {
        List<Node> listSuccesseur = new ArrayList<>();
        for (Segment s : listSegment) {
            if (origin == s.getIdOrigin()) {
                Node n = new Node(s.getIdDestination(), s.getLength());
                listSuccesseur.add(n);
            }
        }
        return listSuccesseur;
    }

    public List<List<Node>> dijkstra(List<Long> etapes, long depart, Carte map) {
        double INF = Double.MAX_VALUE;
        List<List<Node>> resultat = new ArrayList<>();
        HashMap<Long, List<Node>> adjacent = new HashMap<>();
        HashMap<Long, Double> distance = new HashMap<>();
        HashMap<Long, Long> predecesseur = new HashMap<>();
        HashMap<Long, Boolean> find = new HashMap<>();//always empty
        PriorityQueue<Node> pq = new PriorityQueue<>();
        //E1
        pq.add(new Node(depart, 0));
        for (Intersection intersection : map.getIntersectionList().values()) {
            distance.put(intersection.getId(), INF);
        }
        distance.replace(depart, (double) 0);
        find.replace(depart, true);
        predecesseur.put(depart, null);
        for (Intersection intersection : map.getIntersectionList().values()) {
            List<Node> listAdjacent = getSuccesseur(intersection.getId(), map.getSegmentList());
            adjacent.put(intersection.getId(), listAdjacent);
            find.put(intersection.getId(), false);
        }
        //E2
        Node noeudCourant;
        int nbStepBlack = 0;
        while (pq.peek() != null) {
            noeudCourant = pq.poll();
            double distanceActuelle = distance.get(noeudCourant.id);
            for (Node adj : adjacent.get(noeudCourant.id)) {
                if (!find.get(adj.id)) {
                    double newDistance = distanceActuelle + adj.distance;
                    if (newDistance < distance.get(adj.id)) {
                        distance.replace(adj.id,newDistance);
                        predecesseur.put(adj.id, noeudCourant.id);
                    }
                    pq.add(new Node(adj.id,newDistance));
                }

            }
            find.replace(noeudCourant.id,true);
            if (etapes.contains(noeudCourant.id)) nbStepBlack++;
        }
        for (long etape : etapes){
            List<Node> chemin = new LinkedList<>();
            long noeud = etape;
            while (predecesseur.get(noeud) != null){
                chemin.add(0,new Node(noeud, distance.get(noeud)));
                noeud = predecesseur.get(noeud);
            }
            chemin.add(0,new Node(depart, 0));
            resultat.add(chemin);
        }
        return resultat;
    }
}
