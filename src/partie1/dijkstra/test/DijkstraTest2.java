package partie1.dijkstra.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import partie1.graph.Graph;
import partie1.graph.GrapheHHAdj;
import partie1.graph.ShortestPath.Distances;
import partie1.graph.VarGraph;
import org.junit.jupiter.api.Test;

import partie1.dijkstra.Dijkstra;

public class DijkstraTest2 {
    private static final String GRAPH_CYCLE = "A-B(4), A-C(2), B-C(1), B-D(5), C-D(2)";
    private static final String FROM_CYCLE = "A";
    private static final String TO_CYCLE = "D";
    private static final int EXPECTED_DIST_CYCLE = 4;
    private static final List<String> EXPECTED_PATH_CYCLE = List.of("D", "C", "A"); // in pred order
    private static final Map<String, Integer> EXPECTED_DISTANCES_CYCLE = Map.of(
            "A", 0, "B", 4, "C", 2, "D", 4
    );

    private static final String GRAPH_ISOLE = "A-B(3), A-C(5), B-D(2)";
    private static final String FROM_ISOLE = "A";
    private static final String TO_ISOLE = "D";
    private static final int EXPECTED_DIST_ISOLE = 5;
    private static final List<String> EXPECTED_PATH_ISOLE = List.of("D", "B", "A"); // in pred order
    private static final Map<String, Integer> EXPECTED_DISTANCES_ISOLE = Map.of(
            "A", 0, "B", 3, "C", 5, "D", 5
    );

    private static final Dijkstra<String> dijkstra = new Dijkstra<>();

    @Test
    public void testAvecCycle() {
        VarGraph g = new GrapheHHAdj();
        g.peupler(GRAPH_CYCLE);
        Distances<String> dst = dijkstra.compute(g, FROM_CYCLE);

        // Vérifie toutes les distances
        for (Map.Entry<String, Integer> entry : EXPECTED_DISTANCES_CYCLE.entrySet()) {
            assertEquals(entry.getValue(), dst.dist().get(entry.getKey()),
                    "Distance incorrecte pour " + entry.getKey());
        }

        // Vérifie le chemin A->D
        assertEquals(EXPECTED_DIST_CYCLE, dst.dist().get(TO_CYCLE), "Distance A->D incorrecte");
        String c = EXPECTED_PATH_CYCLE.get(0);
        for (String s : EXPECTED_PATH_CYCLE) {
            assertEquals(s, c, "Prédécesseur incorrect dans le chemin A->D");
            c = dst.pred().get(c);
        }
        assertNull(c, "Le prédécesseur de A devrait être null");

        // Affichage pour inspection
        System.out.println("Graphe avec cycle : \n" + g);
        System.out.println("Distances de A : " + dst.dist());
        System.out.println("Predecesseurs : " + dst.pred());
        System.out.println("Distance de A à D : " + dst.dist().get(TO_CYCLE));
        System.out.print("Chemin de A à D : ");
        String sommet = TO_CYCLE;
        Deque<String> pile = new ArrayDeque<>();
        while (sommet != null) {
            pile.push(sommet);
            sommet = dst.pred().get(sommet);
        }
        while (!pile.isEmpty()) {
            System.out.print(pile.pop() + " ");
        }
        System.out.println();
    }

    @Test
    public void testSommetInatteignable() {
        GrapheHHAdj g = new GrapheHHAdj();
        g.peupler(GRAPH_ISOLE);
        // Ajouter le sommet isolé E manuellement
        g.ajouterSommet("E"); // Assumes GrapheHHAdj has ajouterSommet method
        Distances<String> dst = dijkstra.compute(g, FROM_ISOLE);

        // Vérifie toutes les distances
        for (Map.Entry<String, Integer> entry : EXPECTED_DISTANCES_ISOLE.entrySet()) {
            assertEquals(entry.getValue(), dst.dist().get(entry.getKey()),
                    "Distance incorrecte pour " + entry.getKey());
        }

        // Vérifie que E est inatteignable
        assertFalse(dst.dist().containsKey("E"), "E devrait être inatteignable");

        // Vérifie le chemin A->D
        assertEquals(EXPECTED_DIST_ISOLE, dst.dist().get(TO_ISOLE), "Distance A->D incorrecte");
        String c = EXPECTED_PATH_ISOLE.get(0);
        for (String s : EXPECTED_PATH_ISOLE) {
            assertEquals(s, c, "Prédécesseur incorrect dans le chemin A->D");
            c = dst.pred().get(c);
        }
        assertNull(c, "Le prédécesseur de A devrait être null");

        // Affichage pour inspection
        System.out.println("Graphe avec sommet isolé : \n" + g);
        System.out.println("Distances de A : " + dst.dist());
        System.out.println("Predecesseurs : " + dst.pred());
        System.out.println("Distance de A à D : " + dst.dist().get(TO_ISOLE));
        System.out.print("Chemin de A à D : ");
        String sommet = TO_ISOLE;
        Deque<String> pile = new ArrayDeque<>();
        while (sommet != null) {
            pile.push(sommet);
            sommet = dst.pred().get(sommet);
        }
        while (!pile.isEmpty()) {
            System.out.print(pile.pop() + " ");
        }
        System.out.println();
    }
}