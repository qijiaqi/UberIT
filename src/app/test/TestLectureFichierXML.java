package app.test;

import app.main.model.Carte;
import org.junit.Test;
import java.io.File;
import static org.junit.Assert.assertEquals;

public class TestLectureFichierXML {

    @Test
    public void testSmallMap() throws Exception {
        File file = new File("fichiersXML2022/smallMap.xml");
        Carte carte = new Carte();
        carte.loadMap(file);

        assertEquals("ERREUR AVEC LES INTERSECTIONS : La plus grande longitude devrait être 4.879188",4.879188, carte.calcMaxLong(), 4.879188-carte.calcMaxLong());
        assertEquals("ERREUR AVEC LES INTERSECTIONS : La plus petite longitude devrait être 4.8568363", 4.8568363, carte.calcMinLong(), 4.8568363-carte.calcMaxLong());
        assertEquals("ERREUR AVEC LES INTERSECTIONS : La plus grande latitude devrait être 45.762775", 45.762775, carte.calcMaxLat(), 45.762775-carte.calcMaxLat());
        assertEquals("ERREUR AVEC LES INTERSECTIONS : La plus petite latitude devrait être 45.74706", 45.74706, carte.calcMinLat(), 45.74706-carte.calcMinLat());
        assertEquals("ERREUR AVEC LE NOMBRE DE SEGMENTS : Il devrait y avoir 616 segments", 616 , carte.getSegmentList().size());
        assertEquals("ERREUR AVEC LE NOMBRE D'INTERSECTIONS : Il devrait y avoir 308 intersections", 308 , carte.getIntersectionList().size());
    }

    @Test
    public void testMediumMap() throws Exception {
        File file = new File("fichiersXML2022/mediumMap.xml");
        Carte carte = new Carte();
        carte.loadMap(file);

        assertEquals("ERREUR AVEC LES INTERSECTIONS : La plus grande longitude devrait être 4.9075384",4.9075384, carte.calcMaxLong(), 4.9075384-carte.calcMaxLong());
        assertEquals("ERREUR AVEC LES INTERSECTIONS : La plus petite longitude devrait être 4.8568363", 4.8568363, carte.calcMinLong(), 4.8568363-carte.calcMaxLong());
        assertEquals("ERREUR AVEC LES INTERSECTIONS : La plus grande latitude devrait être 45.762775", 45.762775, carte.calcMaxLat(), 45.762775-carte.calcMaxLat());
        assertEquals("ERREUR AVEC LES INTERSECTIONS : La plus petite latitude devrait être 45.727352", 45.727352, carte.calcMinLat(), 45.727352-carte.calcMinLat());
        assertEquals("ERREUR AVEC LE NOMBRE DE SEGMENTS : Il devrait y avoir 3076 segments", 3097 , carte.getSegmentList().size());
        assertEquals("ERREUR AVEC LE NOMBRE D'INTERSECTIONS : Il devrait y avoir 1448 intersections", 1448 , carte.getIntersectionList().size());
    }

    @Test
    public void testLargeMap() throws Exception {
        File file = new File("fichiersXML2022/largeMap.xml");
        Carte carte = new Carte();
        carte.loadMap(file);

        assertEquals("ERREUR AVEC LES INTERSECTIONS : La plus grande longitude devrait être 4.9075384",4.9075384, carte.calcMaxLong(), 4.9075384-carte.calcMaxLong());
        assertEquals("ERREUR AVEC LES INTERSECTIONS : La plus petite longitude devrait être 4.8314376", 4.8314376, carte.calcMinLong(), 4.8314376-carte.calcMaxLong());
        assertEquals("ERREUR AVEC LES INTERSECTIONS : La plus grande latitude devrait être 45.780518", 45.780518, carte.calcMaxLat(), 45.780518-carte.calcMaxLat());
        assertEquals("ERREUR AVEC LES INTERSECTIONS : La plus petite latitude devrait être 45.727352", 45.727352, carte.calcMinLat(), 45.727352-carte.calcMinLat());
        assertEquals("ERREUR AVEC LE NOMBRE DE SEGMENTS : Il devrait y avoir 7772 segments", 7811 , carte.getSegmentList().size());
        assertEquals("ERREUR AVEC LE NOMBRE D'INTERSECTIONS : Il devrait y avoir 3736 intersections", 3736 , carte.getIntersectionList().size());
    }

    @Test
    public void testRequetesSmallMap() throws Exception {
    }

    @Test
    public void testRequetesMeduimMap() throws Exception {
    }

    @Test
    public void testRequetesLargeMap() throws Exception {
    }
}
