package dijkstra;

import graph.Graph;
import graph.ShortestPath;

import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class Dijkstra<T> implements ShortestPath<T> {

	private static class Noeud<T> implements Comparable<Noeud<T>> {
		T sommet;
		int distance;

		Noeud(T sommet, int distance) {
			this.sommet = sommet;
			this.distance = distance;
		}

		@Override
		public int compareTo(Noeud<T> other) {
			return Integer.compare(this.distance, other.distance);
		}
	}

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
