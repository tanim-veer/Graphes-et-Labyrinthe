package dijkstra;

import graph.Graph;
import graph.ShortestPath;

import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class Dijkstra<T> implements ShortestPath<T> {

	@Override
	public Distances<T> compute(Graph<T> g, T src, Animator<T> animator) throws IllegalArgumentException {
		// Init données
		Map<T, Integer> distances = new HashMap<>();
		Map<T, T> predecesseurs = new HashMap<>();
		Set<T> fixe = new HashSet<>();
    }
}
