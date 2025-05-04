package graph;

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
				throw new IllegalArgumentException("L'arc existe déjà");
			}
		}

		arcs.add(new Arc<>(valeur, destination));
	}
}
