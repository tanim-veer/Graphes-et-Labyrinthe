package partie2.applications;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import partie1.graph.Graph;
import partie1.graph.ShortestPath.Distances;
import maze.panel.MazeView;
import maze.regular.RegularMaze;
import partie2.adaptator.GraphMaze;
import partie1.dijkstra.Dijkstra;

/**
 * Affiche une animation du calcul des plus courts chemins dans un labyrinthe.
 *
 * Utilise l'algorithme de Dijkstra pour trouver le chemin le plus court entre l'entrée
 * et la sortie d'un labyrinthe, et affiche le processus dans une interface graphique.
 */
public class Animation {

	/**
	 * Anime le calcul des plus courts chemins dans un labyrinthe.
	 *
	 * Affiche le labyrinthe, annote les distances calculées pour chaque case,
	 * et trace le chemin final de l'entrée à la sortie.
	 *
	 * @param mazeFile Le chemin vers le fichier contenant le labyrinthe (.maze).
	 */
	public static void animation(String mazeFile) {
		RegularMaze maze = null;
		try {
			maze = RegularMaze.readMaze(mazeFile);
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("fichier '" + mazeFile + "' manquant ou au mauvais format");
			return;
		}
		Graph<Integer> graph = new GraphMaze<>(maze);
		MazeView<Integer> view = MazeView.view(maze, mazeFile);
		int start = 0;
		int end = maze.width() * maze.height() - 1;
		view.highlight(start, Color.CYAN);
		view.highlight(end, Color.PINK);
		view.repaint();
		Distances<Integer> dst = new Dijkstra<Integer>().compute(graph, start,
				(pos, dist) -> {
					view.annotate(pos, Integer.toString(dist));
					view.repaint();
					pause(100);
				}
		);
		if (dst.dist().get(end) == null)
			System.out.println("La sortie n'est pas accessible depuis l'entrée.");
		else {
			int current = end;
			while (current != start) {
				int pred = dst.pred().get(current);
				view.addPath(List.of(current, pred), Color.BLUE);
				current = pred;
			}
			view.highlight(start, Color.CYAN);
			view.highlight(end, Color.PINK);
			view.repaint();
		}
	}

	/**
	 * Point d'entrée principal.
	 *
	 * Lit tous les fichiers .maze dans le répertoire "bench" et appelle la méthode
	 * {@link #animation} pour chacun d'eux.
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
			animation(mazeFile.getPath());
		});
	}

	/**
	 * Pause l'exécution du programme pendant une durée spécifiée.
	 *
	 * @param duration La durée de la pause en millisecondes.
	 */
	private static void pause(long duration) {
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}