package partie1.graphe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implémentation d'un graphe orienté et valué utilisant une liste d'adjacence.
 *
 * Implémente l'interface {@link VarGraph} pour permettre l'ajout de sommets et d'arcs,
 * ainsi que la construction d'un graphe à partir d'une chaîne de caractères.
 */
public class GrapheHHAdj implements VarGraph {
	private final Map<String, List<Arc<String>>> adjacence;

	/**
	 * Construit un graphe vide.
	 */
	public GrapheHHAdj() {
		this.adjacence = new HashMap<>();
	}

	/**
	 * Retourne la liste des arcs sortants d'un sommet donné.
	 *
	 * @param s Le sommet dont on veut les arcs sortants.
	 * @return La liste des arcs sortants de {@code s}. Retourne une liste vide si le sommet n'existe pas.
	 */
	@Override
	public List<Arc<String>> getSucc(String s) {
		return adjacence.getOrDefault(s, new ArrayList<>());
	}

	/**
	 * Ajoute un sommet au graphe s'il n'est pas déjà présent.
	 *
	 * @param noeud Le nom du sommet à ajouter.
	 */
	@Override
	public void ajouterSommet(String noeud) {
		if (!adjacence.containsKey(noeud)) {
			adjacence.put(noeud, new ArrayList<>());
		}
	}

	/**
	 * Ajoute un arc orienté et valué entre deux sommets.
	 *
	 * Ajoute les sommets source et destination s'ils ne sont pas déjà présents.
	 *
	 * @param source      Le sommet source de l'arc.
	 * @param destination Le sommet destination de l'arc.
	 * @param valeur      La valuation (poids) de l'arc.
	 * @throws IllegalArgumentException si l'arc existe déjà dans le graphe.
	 */
	@Override
	public void ajouterArc(String source, String destination, Integer valeur) {
		ajouterSommet(source);
		ajouterSommet(destination);

		List<Arc<String>> arcs = adjacence.get(source);
		for (Arc<String> arc : arcs) {
			if (arc.dst().equals(destination)) {
				throw new IllegalArgumentException("L'arc de " + source + " à " + destination + " existe déjà");
			}
		}
		arcs.add(new Arc<>(valeur, destination));
	}

	/**
	 * Construit un graphe à partir d'une chaîne de caractères décrivant les arcs.
	 *
	 * Format attendu : "A-B(5), A-C(10), B-C(3), ..." où chaque arc est de la forme
	 * "source-destination(valeur)". Les arcs sont séparés par des virgules.
	 *
	 * @param arcs La chaîne décrivant les arcs du graphe.
	 * @throws IllegalArgumentException si la chaîne est vide ou si un arc est mal formé.
	 */
	@Override
	public void peupler(String arcs) {
		if (arcs == null || arcs.trim().isEmpty()) {
			throw new IllegalArgumentException("La chaîne est vide");
		}
		String[] listeArcs = arcs.split(",");
		for (String arc : listeArcs) {
			arc = arc.trim();
			int dashIndex = arc.indexOf("-");
			if (dashIndex == -1 || dashIndex == arc.length() - 1) {
				throw new IllegalArgumentException("Format d'arc incorrect : " + arc);
			}
			String source = arc.substring(0, dashIndex).trim();
			String reste = arc.substring(dashIndex + 1).trim();

			int debutPoids = reste.indexOf("(");
			int finPoids = reste.indexOf(")");
			if (debutPoids == -1 || finPoids == -1 || debutPoids >= finPoids) {
				throw new IllegalArgumentException("Format d'arc incorrect : " + arc);
			}
			String destination = reste.substring(0, debutPoids).trim();
			String poidsStr = reste.substring(debutPoids + 1, finPoids).trim();

			int poids;
			try {
				poids = Integer.parseInt(poidsStr);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("poids pas un nombre : " + poidsStr);
			}
			ajouterArc(source, destination, poids);
		}
	}

	/**
	 * Retourne une représentation textuelle du graphe.
	 *
	 * Chaque ligne décrit un sommet suivi de ses arcs sortants sous la forme
	 * "sommet -> destination1(valeur1) destination2(valeur2) ...".
	 *
	 * @return La représentation textuelle du graphe.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (Map.Entry<String, List<Arc<String>>> entry : adjacence.entrySet()) {
			String sommet = entry.getKey();
			List<Arc<String>> arcs = entry.getValue();

			sb.append(sommet).append(" -> ");
			for (Arc<String> arc : arcs) {
				sb.append(arc.dst()).append("(").append(arc.val()).append(") ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}