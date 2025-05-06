package partie2.applications;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import partie1.graph.Graph;
import partie1.graph.ShortestPath.Distances;
import partie1.dijkstra.Dijkstra;
import partie2.maze.regular.RegularMaze;
import partie2.adaptator.GraphMaze;

public class Checker {
	/**
	 * Lis tous les fichiers au format x.maze et x.dist du repertoire bench
	 * et appelle la methode check sur chacun d'eux
	 **/
	public static void main(String[] args) {
		if (args.length >0)
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
	 * Vérifie la correction d'un algorithme de calcul de plus court chemin
	 * en comparant le resultat obtenu avec le resultat attendu
	 *
	 * @param mazeFile le nom du fichier contenant le labyrinthe
	 *                 (au format nom.maze)
	 * @param distFile le nom du fichier contenant les distances attendues
	 *                 (au format nom.dist)
	  **/

	public static void check(String mazeFile, String distFile) {

		RegularMaze maze = null;
		try {
			maze = RegularMaze.readMaze(mazeFile);
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("file '" + mazeFile + "' missing or in wrong format");
			return;
		}
		Distances<Integer> expectedDist = null;
		try {
			expectedDist = Distances.readDist(distFile);
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("file '" + distFile + "' missing or in wrong format");
			return;
		}		
		Graph<Integer> graph = new GraphMaze<>(maze);
		Distances<Integer> dst = new Dijkstra<Integer>().compute(graph, maze.start());
		if (!dst.equals(expectedDist))
			System.out.println("failed");
		else
			System.out.println("success");
	}
}
