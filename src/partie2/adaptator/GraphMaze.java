package partie2.adaptator;

import partie1.graph.Graph;
import maze.Maze;

import java.util.ArrayList;
import java.util.List;

/**
 * Adaptateur qui transforme un labyrinthe en graphe orienté et valué.
 *
 * Chaque case du labyrinthe devient un sommet, et les voisins accessibles (cases ouvertes)
 * deviennent des arcs de poids 1.
 *
 * @param <C> Le type des coordonnées des cases du labyrinthe (généralement Integer pour un RegularMaze).
 */
public class GraphMaze<C> implements Graph<C> {
    private Maze<C> maze;

    /**
     * Construit un graphe à partir d'un labyrinthe.
     *
     * @param maze Le labyrinthe à adapter.
     */
    public GraphMaze(Maze<C> maze) {
        this.maze = maze;
    }

    /**
     * Retourne la liste des arcs sortants d'un sommet (case) du labyrinthe.
     *
     * Chaque arc représente un voisin accessible (case ouverte) avec un poids de 1.
     *
     * @param node La case dont on veut les arcs sortants.
     * @return La liste des arcs sortants de {@code node}. Retourne une liste vide si la case n'a pas de voisins accessibles.
     */
    @Override
    public List<Arc<C>> getSucc(C node) {
        List<Arc<C>> arcs = new ArrayList<>();
        for (C neighbor : maze.openedNeighbours(node)) {
            arcs.add(new Arc<>(1, neighbor));
        }
        return arcs;
    }
}