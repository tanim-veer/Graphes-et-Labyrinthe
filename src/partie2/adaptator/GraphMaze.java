package partie2.adaptator;

import partie1.graph.Graph;
import maze.Maze;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class GraphMaze<C> implements Graph<C> {
    private Maze<C> maze;

    public GraphMaze(Maze<C> maze) {
        this.maze = maze;
    }

    @Override
    public List<Arc<C>> getSucc(C node) {
        List<Arc<C>> arcs = new ArrayList<>();
        for (C neighbor : maze.openedNeighbours(node)) {
            arcs.add(new Arc<>(1, neighbor));
        }
        return arcs;
    }
}