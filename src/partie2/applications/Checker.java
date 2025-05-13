package partie2.applications;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import partie1.graph.Graph;
import partie1.graph.ShortestPath.Distances;
import maze.regular.RegularMaze;
import partie2.adaptator.GraphMaze;
import partie1.dijkstra.Dijkstra;

/**
 * Vérifie la correction de l'algorithme de Dijkstra appliqué à des labyrinthes.
 *
 * Lit des fichiers de labyrinthes (.maze) et leurs distances attendues (.dist) dans le répertoire "bench",
 * calcule les plus courts chemins, et compare les résultats avec les attentes.
 */
public class Checker {

	/**
	 * Point d'entrée principal.
	 *
	 * Lit tous les fichiers .maze et .dist dans le répertoire "bench" et appelle la méthode
	 * {@link #check} pour chacun d'eux.
	 *
	 * @param args Les arguments de la ligne de commande (aucun attendu).
	 * @throws IllegalArgumentException si des arguments sont fournis.
	 */
	public static void main(String[] args) {
		if (args.length > 0)
			throw new IllegalArgumentException("aucun argument attendu");

		File benchDir = new File("bench");
		if (!benchDir.exists() || !benchDir.isDirectory()) {
			System.out.println("Le répertoire 'bench' est introuvable ou n'est pas un répertoire.");
			return;
		}

		File[] mazeFiles = benchDir.listFiles((dir, name) -> name.endsWith(".maze"));
		if (mazeFiles == null || mazeFiles.length == 0) {
			System.out.println("Aucun fichier .maze trouvé dans le répertoire 'bench'.");
			return;
		}

		Arrays.stream(mazeFiles).forEach(mazeFile -> {
			String baseName = mazeFile.getName().replace(".maze", "");
			File distFile = new File(benchDir, baseName + ".dist");
			if (distFile.exists()) {
				check(mazeFile.getPath(), distFile.getPath());
			} else {
				System.out.println("Fichier .dist correspondant introuvable pour : " + mazeFile.getName());
			}
		});
	}

	/**
	 * Vérifie la cohérence des prédécesseurs calculés par rapport aux distances attendues.
	 *
	 * Pour chaque sommet, vérifie que la distance au successeur est égale à la distance au prédécesseur plus 1.
	 *
	 * @param maze     Le labyrinthe utilisé pour le calcul.
	 * @param expected Les distances et prédécesseurs attendus.
	 * @param computed Les distances et prédécesseurs calculés.
	 * @return true si les prédécesseurs sont cohérents, false sinon.
	 */
	private static boolean checkPred(RegularMaze maze, Distances<Integer> expected, Distances<Integer> computed) {
		for (int i = 0; i < maze.height() * maze.width(); ++i) {
			Integer pred = computed.pred().get(i);
			if (pred == null) {
				if (expected.pred().get(i) != null)
					return false;
			} else if (computed.dist().get(i) != expected.dist().get(pred) + 1)
				return false;
		}
		return true;
	}

	/**
	 * Vérifie la correction d'un algorithme de calcul de plus court chemin.
	 *
	 * Compare les distances et prédécesseurs calculés par Dijkstra avec ceux attendus,
	 * lus dans les fichiers .dist.
	 *
	 * @param mazeFile Le nom du fichier contenant le labyrinthe (au format nom.maze).
	 * @param distFile Le nom du fichier contenant les distances attendues (au format nom.dist).
	 */
	public static void check(String mazeFile, String distFile) {
		RegularMaze maze = null;
		try {
			maze = RegularMaze.readMaze(mazeFile);
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("fichier '" + mazeFile + "' manquant ou au mauvais format");
			return;
		}
		Distances<Integer> expectedDist = null;
		try {
			expectedDist = Distances.readDist(distFile);
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("fichier '" + distFile + "' manquant ou au mauvais format");
			return;
		}
		Graph<Integer> graph = new GraphMaze<>(maze);
		Distances<Integer> dst = new Dijkstra<Integer>().compute(graph, maze.start());
		if (!dst.dist().equals(expectedDist.dist()) || !checkPred(maze, expectedDist, dst))
			System.out.println("echec" + " : " + mazeFile + " et " + distFile);
		else
			System.out.println("succes" + " : " + mazeFile + " et " + distFile);
	}
}