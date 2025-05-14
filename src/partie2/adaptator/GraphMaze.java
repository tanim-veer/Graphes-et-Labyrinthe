package partie2.adaptator;

import java.util.ArrayList;
import java.util.List;
import maze.Maze;
import partie1.graph.Graph;

public class GraphMaze<C> implements Graph<C> {

    private Maze<C> maze;

    public GraphMaze(Maze<C> maze) {
        this.maze = maze;
    }

    @Override
    public List<Arc<C>> getSucc(C node) {
        List<Arc<C>> arcs = new ArrayList<>();
        try {
            for (C neighbor : maze.openedNeighbours(node)) {
                arcs.add(new Arc<>(1, neighbor));
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur dans openedNeighbours pour la case " + node + " : " + e.getMessage());
            return arcs;
        }
        return arcs;
    }
}