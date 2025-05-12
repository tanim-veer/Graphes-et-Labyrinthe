package partie2.adaptator;

import partie1.graph.Graph;
import partie2.maze.Maze;
import java.util.HashMap;
import java.util.Map;

public class GraphMaze<C> implements Graph<C> {
    private Maze<C> maze;

    public GraphMaze(Maze<C> maze) {
        this.maze = maze;
    }

    @Override
    public Map<C, Integer> getSucc(C node) {
        Map<C, Integer> successors = new HashMap<>();
        for (C neighbor : maze.openedNeighbours(node)) {
            successors.put(neighbor, 1);
        }
        return successors;
    }
}