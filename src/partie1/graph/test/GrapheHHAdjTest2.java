package partie1.graph.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import partie1.graph.GrapheHHAdj;
import partie1.graph.Graph.Arc;

import java.util.List;

public class GrapheHHAdjTest2 {

    @Test
    public void testPeuplerAvecCycle() {
        GrapheHHAdj graphe = new GrapheHHAdj();
        graphe.peupler("A-B(4), A-C(2), B-C(1), B-D(5), C-D(2)");

        // Vérifie le nombre d'arcs par sommet
        assertEquals(2, graphe.getSucc("A").size(), "A devrait avoir 2 arcs sortants");
        assertEquals(2, graphe.getSucc("B").size(), "B devrait avoir 2 arcs sortants");
        assertEquals(1, graphe.getSucc("C").size(), "C devrait avoir 1 arc sortant");
        assertEquals(0, graphe.getSucc("D").size(), "D devrait avoir 0 arc sortant");

        // Vérifie les arcs de A
        List<Arc<String>> arcsDeA = graphe.getSucc("A");
        assertTrue(arcsDeA.stream().anyMatch(arc -> arc.dst().equals("B") && arc.val() == 4), "Arc A->B(4) manquant");
        assertTrue(arcsDeA.stream().anyMatch(arc -> arc.dst().equals("C") && arc.val() == 2), "Arc A->C(2) manquant");

        // Vérifie les arcs de B
        List<Arc<String>> arcsDeB = graphe.getSucc("B");
        assertTrue(arcsDeB.stream().anyMatch(arc -> arc.dst().equals("C") && arc.val() == 1), "Arc B->C(1) manquant");
        assertTrue(arcsDeB.stream().anyMatch(arc -> arc.dst().equals("D") && arc.val() == 5), "Arc B->D(5) manquant");

        // Vérifie l'arc de C
        List<Arc<String>> arcsDeC = graphe.getSucc("C");
        assertEquals("D", arcsDeC.get(0).dst(), "Arc C->D manquant");
        assertEquals(2, arcsDeC.get(0).val(), "Poids de C->D incorrect");

        // Affiche le graphe
        System.out.println("Affichage du graphe avec cycle :");
        System.out.println(graphe.toString());
    }

    @Test
    public void testPeuplerSommetIsole() {
        GrapheHHAdj graphe = new GrapheHHAdj();
        graphe.peupler("A-B(3), A-C(5), B-D(2), E-");

        // Vérifie le nombre d'arcs par sommet
        assertEquals(2, graphe.getSucc("A").size(), "A devrait avoir 2 arcs sortants");
        assertEquals(1, graphe.getSucc("B").size(), "B devrait avoir 1 arc sortant");
        assertEquals(0, graphe.getSucc("C").size(), "C devrait avoir 0 arc sortant");
        assertEquals(0, graphe.getSucc("D").size(), "D devrait avoir 0 arc sortant");
        assertEquals(0, graphe.getSucc("E").size(), "E devrait avoir 0 arc sortant");

        // Vérifie les arcs de A
        List<Arc<String>> arcsDeA = graphe.getSucc("A");
        assertTrue(arcsDeA.stream().anyMatch(arc -> arc.dst().equals("B") && arc.val() == 3), "Arc A->B(3) manquant");
        assertTrue(arcsDeA.stream().anyMatch(arc -> arc.dst().equals("C") && arc.val() == 5), "Arc A->C(5) manquant");

        // Vérifie l'arc de B
        List<Arc<String>> arcsDeB = graphe.getSucc("B");
        assertEquals("D", arcsDeB.get(0).dst(), "Arc B->D manquant");
        assertEquals(2, arcsDeB.get(0).val(), "Poids de B->D incorrect");

        // Affiche le graphe
        System.out.println("Affichage du graphe avec sommet isolé :");
        System.out.println(graphe.toString());
    }

    @Test
    public void testArcExistantErreur() {
        GrapheHHAdj graphe = new GrapheHHAdj();
        graphe.peupler("A-B(4), A-C(2)");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            graphe.ajouterArc("A", "B", 3);
        });
        assertTrue(exception.getMessage().contains("existe déjà"), "Message d'erreur incorrect pour arc existant");
    }
}