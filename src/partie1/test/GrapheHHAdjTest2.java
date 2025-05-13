package partie1.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import partie1.graph.GrapheHHAdj;
import partie1.graph.Graph.Arc;

import java.util.List;

/**
 * Classe de tests supplémentaires pour la classe {@link GrapheHHAdj}.
 *
 * Teste des cas particuliers comme les graphes avec cycles, les sommets isolés,
 * et les erreurs de format dans la construction du graphe.
 */
public class GrapheHHAdjTest2 {

    /**
     * Teste la construction d'un graphe simple à partir d'une chaîne.
     *
     * Vérifie que les arcs et les sommets sont correctement ajoutés.
     */
    @Test
    public void testPeuplerSimple() {
        GrapheHHAdj graphe = new GrapheHHAdj();
        graphe.peupler("A-B(3), A-C(5), B-C(2), C-D(4)");

        assertEquals(2, graphe.getSucc("A").size(), "A devrait avoir 2 arcs sortants");
        assertEquals(1, graphe.getSucc("B").size(), "B devrait avoir 1 arc sortant");
        assertEquals(1, graphe.getSucc("C").size(), "C devrait avoir 1 arc sortant");
        assertEquals(0, graphe.getSucc("D").size(), "D devrait avoir 0 arc sortant");

        List<Arc<String>> arcsDeA = graphe.getSucc("A");
        assertTrue(arcsDeA.stream().anyMatch(arc -> arc.dst().equals("B") && arc.val() == 3), "Arc A->B(3) manquant");
        assertTrue(arcsDeA.stream().anyMatch(arc -> arc.dst().equals("C") && arc.val() == 5), "Arc A->C(5) manquant");

        List<Arc<String>> arcsDeB = graphe.getSucc("B");
        assertEquals("C", arcsDeB.get(0).dst(), "Arc B->C manquant");
        assertEquals(2, arcsDeB.get(0).val(), "Poids de B->C incorrect");

        List<Arc<String>> arcsDeC = graphe.getSucc("C");
        assertEquals("D", arcsDeC.get(0).dst(), "Arc C->D manquant");
        assertEquals(4, arcsDeC.get(0).val(), "Poids de C->D incorrect");

        System.out.println("Affichage du graphe simple :");
        System.out.println(graphe.toString());
    }

    /**
     * Teste la construction d'un graphe contenant un cycle.
     *
     * Vérifie que les arcs et les sommets sont correctement ajoutés dans un graphe avec un cycle.
     */
    @Test
    public void testPeuplerCycle() {
        GrapheHHAdj graphe = new GrapheHHAdj();
        graphe.peupler("A-B(4), A-C(2), B-C(1), B-D(5), C-D(2)");

        assertEquals(2, graphe.getSucc("A").size(), "A devrait avoir 2 arcs sortants");
        assertEquals(2, graphe.getSucc("B").size(), "B devrait avoir 2 arcs sortants");
        assertEquals(1, graphe.getSucc("C").size(), "C devrait avoir 1 arc sortant");
        assertEquals(0, graphe.getSucc("D").size(), "D devrait avoir 0 arc sortant");

        System.out.println("Affichage du graphe avec cycle :");
        System.out.println(graphe.toString());
    }

    /**
     * Teste la construction d'un graphe avec un sommet isolé.
     *
     * Vérifie que le sommet isolé est correctement ajouté et n'a pas d'arcs sortants.
     */
    @Test
    public void testPeuplerSommetIsole() {
        GrapheHHAdj graphe = new GrapheHHAdj();
        graphe.peupler("A-B(3), A-C(5)");
        graphe.ajouterSommet("E");

        assertEquals(2, graphe.getSucc("A").size(), "A devrait avoir 2 arcs sortants");
        assertEquals(0, graphe.getSucc("B").size(), "B devrait avoir 0 arc sortant");
        assertEquals(0, graphe.getSucc("C").size(), "C devrait avoir 0 arc sortant");
        assertEquals(0, graphe.getSucc("E").size(), "E devrait être un sommet isolé");

        System.out.println("Affichage du graphe avec sommet isolé :");
        System.out.println(graphe.toString());
    }

    /**
     * Teste que la méthode peupler lève une exception pour un arc mal formé.
     */
    @Test
    public void testFormatArcInvalide() {
        GrapheHHAdj graphe = new GrapheHHAdj();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            graphe.peupler("A-B, A-C(2)");
        });
        assertTrue(exception.getMessage().contains("Format d'arc incorrect"), "Message d'erreur incorrect pour format d'arc");
    }
}