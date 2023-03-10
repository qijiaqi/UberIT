package app.test;

import app.main.algo.Dijkstra;
import app.main.algo.Node;
import app.main.model.Carte;
import app.main.model.Intersection;
import app.main.model.Segment;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class TestAlgorithmeDijkstra {
    
    @Test
    public void testAlgoDijkstra() throws Exception {
        List<Long> etapes = new ArrayList<>();
		etapes.add((long)0);
		etapes.add((long)1);
		etapes.add((long)2);
		etapes.add((long)3);
		etapes.add((long)4);
		etapes.add((long)5);
		Intersection intersection0 = new Intersection(0,0,0);
		Intersection intersection1 = new Intersection(1,0,0);
		Intersection intersection2 = new Intersection(2,0,0);
		Intersection intersection3 = new Intersection(3,0,0);
		Intersection intersection4 = new Intersection(4,0,0);
		Intersection intersection5 = new Intersection(5,0,0);
		Map<Long, Intersection> m = new HashMap<>();
		m.put((long)0,intersection0);
		m.put((long)1,intersection1);
		m.put((long)2,intersection2);
		m.put((long)3,intersection3);
		m.put((long)4,intersection4);
		m.put((long)5,intersection5);
		Segment segment0 = new Segment(0,1,null,4);
		Segment segment1 = new Segment(1,0,null,4);
		Segment segment2 = new Segment(0,2,null,1);
		Segment segment3 = new Segment(2,0,null,1);
		Segment segment4 = new Segment(1,2,null,10);
		Segment segment5 = new Segment(2,1,null,10);
		Segment segment6 = new Segment(1,3,null,1);
		Segment segment7 = new Segment(3,1,null,1);
		Segment segment8 = new Segment(2,3,null,5);
		Segment segment9 = new Segment(3,2,null,5);
		Segment segment10 = new Segment(2,4,null,1);
		Segment segment11 = new Segment(4,2,null,1);
		Segment segment12 = new Segment(0,4,null,3);
		Segment segment13 = new Segment(4,0,null,3);
		Segment segment14 = new Segment(4,5,null,1);
		Segment segment15= new Segment(5,4,null,1);
		Segment segment16 = new Segment(3,4,null,3);
		Segment segment17 = new Segment(4,3,null,3);
		Segment segment18 = new Segment(3,5,null,1);
		Segment segment19 = new Segment(5,3,null,1);
		List<Segment> seg = new ArrayList<>();
		seg.add(segment0);
		seg.add(segment1);
		seg.add(segment2);
		seg.add(segment3);
		seg.add(segment4);
		seg.add(segment5);
		seg.add(segment6);
		seg.add(segment7);
		seg.add(segment8);
		seg.add(segment9);
		seg.add(segment10);
		seg.add(segment11);
		seg.add(segment12);
		seg.add(segment13);
		seg.add(segment14);
		seg.add(segment15);
		seg.add(segment16);
		seg.add(segment17);
		seg.add(segment18);
		seg.add(segment19);
		Carte map = new Carte(m,seg);
        Dijkstra algo = new Dijkstra();
        List<List<Node>> cheminsTrouves = algo.dijkstra(etapes, 0, map);
        List<Long> predecesseurs0 = Arrays.asList((long)0);
        List<Long> predecesseurs1 = Arrays.asList((long)0, (long)1);
        List<Long> predecesseurs2 = Arrays.asList((long)0, (long)2);
        List<Long> predecesseurs3 = Arrays.asList((long)0, (long)2, (long)4, (long)5, (long)3);
        List<Long> predecesseurs4 = Arrays.asList((long)0, (long)2, (long)4);
        List<Long> predecesseurs5 = Arrays.asList((long)0, (long)2, (long)4, (long)5);
        List<List<Long>> resultatAttendus = Arrays.asList(predecesseurs0, predecesseurs1, predecesseurs2, predecesseurs3, predecesseurs4, predecesseurs5);
        for (int i=0; i<resultatAttendus.size(); i++){
            List<Long> chemins = resultatAttendus.get(i);
            for(int j=0; j<chemins.size()-1; j++){
                long etape = chemins.get(j+1);
                long predecesseur = chemins.get(j);
                assertEquals("ERREUR AVEC DIJKSTRA : le prédécesseur de "+etape+ "devrait être "+predecesseur+" !", predecesseur, cheminsTrouves.get(i).get(j).id);
            }
        }
    }
}
