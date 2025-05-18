package partie1.dijkstra;

import partie1.graphe.Graph;
import partie1.graphe.ShortestPath;

import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Implémentation de l'algorithme de Dijkstra pour calculer les plus courts chemins dans un graphe.
 *
 * Implémente l'interface {@link ShortestPath} pour fournir une méthode de calcul des distances
 * minimales à partir d'un sommet source vers tous les sommets accessibles.
 *
 * @param <T> Identifiant des sommets. Le type doit être "hachable".
 */
public class Dijkstra<T> implements ShortestPath<T> {

	/**
	 * Classe interne représentant un nœud dans la file de priorité.
	 *
	 * Utilisée pour gérer les sommets à explorer dans l'algorithme de Dijkstra, triés par distance.
	 *
	 * @param <T> Identifiant des sommets.
	 */
	private static class Noeud<T> implements Comparable<Noeud<T>> {
		T sommet;
		int distance;

		/**
		 * Construit un nœud avec un sommet et une distance.
		 *
		 * @param sommet   Le sommet associé au nœud.
		 * @param distance La distance actuelle au sommet source.
		 */
		Noeud(T sommet, int distance) {
			this.sommet = sommet;
			this.distance = distance;
		}

		/**
		 * Compare deux nœuds en fonction de leur distance.
		 *
		 * @param other L'autre nœud à comparer.
		 * @return Un entier négatif, zéro ou positif si la distance de ce nœud est respectivement
		 *         inférieure, égale ou supérieure à celle de l'autre nœud.
		 */
		@Override
		public int compareTo(Noeud<T> other) {
			return Integer.compare(this.distance, other.distance);
		}
	}

	/**
	 * Calcule les plus courts chemins à partir d'un sommet source dans un graphe.
	 *
	 * @param g        Le graphe pour lequel le calcul est demandé.
	 * @param src      Le sommet source à partir duquel les plus courts chemins sont calculés.
	 * @param animator L'animateur du parcours, invoqué chaque fois qu'une distance est connue.
	 * @return Une instance de {@link Distances} contenant les distances minimales et les prédécesseurs.
	 * @throws IllegalArgumentException si un arc de valuation négative est rencontré pendant l'exploration.
	 */
	@Override
	public Distances<T> compute(Graph<T> g, T src, Animator<T> animator) throws IllegalArgumentException {
		// Init données
		Map<T, Integer> distances = new HashMap<>();
		Map<T, T> predecesseurs = new HashMap<>();
		Set<T> fixe = new HashSet<>();
		PriorityQueue<Noeud<T>> file = new PriorityQueue<>();

		// Init sommet à 0
		distances.put(src, 0);
		predecesseurs.put(src, null);
		file.add(new Noeud<>(src, 0));

		while (!file.isEmpty()) {
			Noeud<T> courant = file.poll();
			T u = courant.sommet;

			// Ignorer si sommet déjà fixé
			if (fixe.contains(u)) {
				continue;
			}

			fixe.add(u);
			animator.accept(u, distances.get(u));

			for (Graph.Arc<T> arc : g.getSucc(u)) {
				T v = arc.dst();
				int poids = arc.val();

				if (poids < 0) {
					throw new IllegalArgumentException("Poids négatif détecté");
				}

				int nouvelleDistance = distances.get(u) + poids;

				if (!distances.containsKey(v) || nouvelleDistance < distances.get(v)) {
					distances.put(v, nouvelleDistance);
					predecesseurs.put(v, u);
					file.add(new Noeud<>(v, nouvelleDistance));
				}
			}
		}
		return new Distances<>(distances, predecesseurs);
	}
}