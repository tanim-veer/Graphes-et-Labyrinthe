package partie1.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import partie1.graph.GrapheHHAdj;
import partie1.graph.Graph.Arc;

import java.util.List;

/**
 * Classe de tests pour la classe {@link GrapheHHAdj}.
 *
 * Teste les fonctionnalités de base de la représentation du graphe, y compris l'ajout
 * de sommets, d'arcs, la construction à partir d'une chaîne, et l'affichage.
 */
public class GrapheHHAdjTest {

    /**
     * Teste l'ajout de sommets dans le graphe.
     *
     * Vérifie que les sommets sont ajoutés correctement et que leur liste d'arcs sortants est vide.
     */
    @Test
    public void testAjouterSommet() {
        GrapheHHAdj graphe = new GrapheHHAdj();
        graphe.ajouterSommet("A");
        graphe.ajouterSommet("B");

        // Vérifie sommets existent
        assertNotNull(graphe.getSucc("A"));
        assertNotNull(graphe.getSucc("B"));

        // Vérifie listes d'adjacence vides
        assertTrue(graphe.getSucc("A").isEmpty());
        assertTrue(graphe.getSucc("B").isEmpty());

        // Vérifie ajout sommet existant ne change rien
        graphe.ajouterSommet("A");
        assertTrue(graphe.getSucc("A").isEmpty());
    }

    /**
     * Teste l'ajout d'arcs dans le graphe.
     *
     * Vérifie que les arcs sont ajoutés correctement et que les sommets associés sont créés.
     */
    @Test
    public void testAjouterArc() {
        GrapheHHAdj graphe = new GrapheHHAdj();

        // Ajoute arcs
        graphe.ajouterArc("A", "B", 5);
        graphe.ajouterArc("A", "C", 10);
        graphe.ajouterArc("B", "C", 3);

        // Vérifie que sommets ont été ajoutés
        assertNotNull(graphe.getSucc("A"));
        assertNotNull(graphe.getSucc("B"));
        assertNotNull(graphe.getSucc("C"));

        // Vérifie arcs sortants de A
        List<Arc<String>> arcsDeA = graphe.getSucc("A");
        assertEquals(2, arcsDeA.size());
        boolean arcABTrouve = false;
        boolean arcACTrouve = false;

        for (Arc<String> arc : arcsDeA) {
            if (arc.dst().equals("B") && arc.val() == 5) {
                arcABTrouve = true;
            } else if (arc.dst().equals("C") && arc.val() == 10) {
                arcACTrouve = true;
            }
        }

        assertTrue(arcABTrouve, "Arc A->B non trouvé ou valeur incorrecte");
        assertTrue(arcACTrouve, "Arc A->C non trouvé ou valeur incorrecte");

        // Vérifie arcs sortants de B
        List<Arc<String>> arcsDeB = graphe.getSucc("B");
        assertEquals(1, arcsDeB.size());
        assertEquals("C", arcsDeB.get(0).dst());
        assertEquals(3, arcsDeB.get(0).val());

        // Vérifie que C a pas d'arcs sortants
        List<Arc<String>> arcsDeC = graphe.getSucc("C");
        assertTrue(arcsDeC.isEmpty());
    }

    /**
     * Teste qu'une exception est levée lors de l'ajout d'un arc déjà existant.
     */
    @Test
    public void testAjouterArcExistant() {
        GrapheHHAdj graphe = new GrapheHHAdj();
        graphe.ajouterArc("A", "B", 5);

        // Tenter ajout arc déjà existant
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            graphe.ajouterArc("A", "B", 10);
        });

        assertTrue(exception.getMessage().contains("existe déjà"));
    }

    /**
     * Teste la construction d'un graphe à partir d'une chaîne de caractères.
     *
     * Vérifie que les arcs sont correctement ajoutés et que les sommets ont le bon nombre d'arcs sortants.
     */
    @Test
    public void testPeupler() {
        GrapheHHAdj graphe = new GrapheHHAdj();
        graphe.peupler("A-B(5), A-C(10), B-C(3), C-D(8)");

        // Vérifie les arcs
        List<Arc<String>> arcsDeA = graphe.getSucc("A");
        assertEquals(2, arcsDeA.size());

        List<Arc<String>> arcsDeB = graphe.getSucc("B");
        assertEquals(1, arcsDeB.size());

        List<Arc<String>> arcsDeC = graphe.getSucc("C");
        assertEquals(1, arcsDeC.size());

        List<Arc<String>> arcsDeD = graphe.getSucc("D");
        assertEquals(0, arcsDeD.size());
    }

    /**
     * Affiche la représentation textuelle du graphe.
     */
    @Test
    public void testAffichageGraphe() {
        GrapheHHAdj graphe = new GrapheHHAdj();
        graphe.peupler("A-B(5), A-C(10), B-C(3), C-D(8)");

        System.out.println("Affichage du graphe :");
        System.out.println(graphe.toString());
    }
}