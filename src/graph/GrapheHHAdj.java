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
		// TODO Auto-generated method stub
		
	}

}
