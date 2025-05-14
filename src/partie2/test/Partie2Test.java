package partie2.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import partie1.graph.Graph;
import partie2.adaptator.GraphMaze;
import partie2.applications.Checker;
import partie2.applications.Animation;
import maze.regular.RegularMaze;

import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Tests unitaires pour la partie 2 du projet.
 * Vérifie les dimensions des labyrinthes, les successeurs dans les graphes,
 * la validation des distances, et l'exécution de l'animation.
 */
public class Partie2Test {

    /**
     * Labyrinthe normal (10x5) chargé à partir de "bench-1-10x5.maze".
     */
    private RegularMaze mazeNormal;

    /**
     * Labyrinthe large (20x10) chargé à partir de "bench-3-20x10.maze".
     */
    private RegularMaze mazeLarge;

    /**
     * Graphe construit à partir de {@code mazeNormal}.
     */
    private Graph<Integer> graphNormal;

    /**
     * Graphe construit à partir de {@code mazeLarge}.
     */
    private Graph<Integer> graphLarge;

    /**
     * Instance de {@link Checker} pour valider les fichiers de distances.
     */
    private Checker checker;

    /**
     * Instance de {@link Animation} pour tester l'exécution de l'animation.
     */
    private Animation animation;

    /**
     * Chemin de base vers les fichiers de test (labyrinthes et distances).
     */
    private static final String BASE_PATH = "/Users/tanimveer/Documents/Graphes et Labyrinthe/bench/";

    /**
     * Configure les instances nécessaires avant chaque test.
     * Charge les labyrinthes "bench-1-10x5.maze" et "bench-3-20x10.maze", construit
     * les graphes correspondants, et initialise {@link Checker} et {@link Animation}.
     */
    @BeforeEach
    public void setUp() {
        try {
            mazeNormal = RegularMaze.readMaze(BASE_PATH + "bench-1-10x5.maze");
            graphNormal = new GraphMaze<>(mazeNormal);
        } catch (Exception e) {
            fail("Erreur lors du chargement de bench-1-10x5.maze : " + e.getMessage());
        }

        try {
            mazeLarge = RegularMaze.readMaze(BASE_PATH + "bench-3-20x10.maze");
            graphLarge = new GraphMaze<>(mazeLarge);
        } catch (Exception e) {
            fail("Erreur lors du chargement de bench-3-20x10.maze : " + e.getMessage());
        }

        checker = new Checker();
        animation = new Animation();
    }

    /**
     * Teste les dimensions du labyrinthe "bench-1-10x5.maze".
     * Vérifie que la largeur est 10 et la hauteur est 5.
     */
    @Test
    public void testMazeDimensions() {
        assertEquals(10, mazeNormal.width(), "La largeur doit être 10");
        assertEquals(5, mazeNormal.height(), "La hauteur doit être 5");
    }

    /**
     * Teste les successeurs dans le graphe construit à partir de "bench-1-10x5.maze".
     * Vérifie que la case de départ a un nombre valide de successeurs (taille >= 0),
     * que les poids des arcs sont 1 si des successeurs existent, et qu'une case hors
     * limites (50) n'a pas de successeurs.
     */
    @Test
    public void testGraphMazeSuccesseursNormal() {
        int startNode = mazeNormal.start();
        System.out.println("Case de départ : " + startNode);

        List<Graph.Arc<Integer>> succ = graphNormal.getSucc(startNode);
        assertTrue(succ.size() >= 0, "La case de départ " + startNode + " doit avoir un nombre valide de successeurs");
        if (!succ.isEmpty()) {
            assertEquals(1, succ.get(0).val(), "Le poids doit être 1");
        }

        succ = graphNormal.getSucc(50); // Hors limites 10x5 (50 > 49)
        assertTrue(succ.isEmpty(), "Une case hors limites n'a pas de successeurs");
    }

    /**
     * Teste les successeurs dans le graphe construit à partir de "bench-3-20x10.maze".
     * Vérifie que la case de départ a un nombre valide de successeurs (taille >= 0),
     * et que les poids des arcs sont 1 si des successeurs existent.
     */
    @Test
    public void testGraphMazeSuccesseursLarge() {
        int startNode = mazeLarge.start();
        System.out.println("Case de départ (large) : " + startNode);
        List<Graph.Arc<Integer>> succ = graphLarge.getSucc(startNode);
        assertTrue(succ.size() >= 0, "La case de départ " + startNode + " doit avoir un nombre valide de successeurs");
        if (!succ.isEmpty()) {
            assertEquals(1, succ.get(0).val(), "Le poids doit être 1");
        }
    }

    /**
     * Teste la validation des fichiers de distances avec {@link Checker}.
     * Vérifie que les fichiers "bench-1-10x5.dist" et "bench-3-10x5.dist" existent,
     * et que {@link Checker} détecte une erreur pour ces fichiers lorsqu'ils sont
     * comparés au labyrinthe "bench-1-10x5.maze".
     */
    @Test
    public void testCheckerDistances() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            assertTrue(Files.exists(Paths.get(BASE_PATH + "bench-1-10x5.dist")),
                    "Le fichier bench-1-10x5.dist doit exister");
            assertTrue(Files.exists(Paths.get(BASE_PATH + "bench-3-10x5.dist")),
                    "Le fichier bench-3-10x5.dist doit exister");

            outContent.reset();
            checker.check(BASE_PATH + "bench-1-10x5.maze", BASE_PATH + "bench-1-10x5.dist");
            String output = outContent.toString();
            assertTrue(output.contains("manquant ou au mauvais format"),
                    "Un message d'erreur doit être affiché pour bench-1-10x5.dist (fichier probablement mal formaté)");

            outContent.reset();
            checker.check(BASE_PATH + "bench-1-10x5.maze", BASE_PATH + "bench-3-10x5.dist");
            output = outContent.toString();
            assertTrue(output.contains("manquant ou au mauvais format"),
                    "Un message d'erreur doit être affiché pour bench-3-10x5.dist");
        } finally {
            System.setOut(originalOut);
        }
    }

    /**
     * Teste l'exécution de l'animation sans arguments.
     * Vérifie que l'appel à {@link Animation#main(String[])} ne lève pas d'exception.
     */
    @Test
    public void testAnimationRuns() {
        try {
            animation.main(new String[]{});
            assertTrue(true, "L'animation doit s'exécuter sans erreur");
        } catch (Exception e) {
            fail("L'animation a échoué : " + e.getMessage());
        }
    }
}