package partie1.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrapheHHAdj implements VarGraph {
	private final Map<String, List<Arc<String>>> adjacence;

	public GrapheHHAdj() {
		this.adjacence = new HashMap<>();
	}

	@Override
	public List<Arc<String>> getSucc(String s) {
		return adjacence.getOrDefault(s, new ArrayList<>());
	}

	@Override
	public void ajouterSommet(String noeud) {
		if (!adjacence.containsKey(noeud)) {
			adjacence.put(noeud, new ArrayList<>());
		}
	}

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