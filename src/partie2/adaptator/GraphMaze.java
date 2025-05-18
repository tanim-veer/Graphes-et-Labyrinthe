package partie2.adaptator;

import java.util.ArrayList;
import java.util.List;
import maze.Maze;
import partie1.graphe.Graph;

/**
 * Adaptateur pour transformer un labyrinthe ({@link Maze}) en un graphe ({@link Graph}).
 * Chaque nœud du graphe correspond à une position dans le labyrinthe, et les successeurs
 * sont déterminés par les voisins ouverts accessibles.
 *
 * @param <C> Identifiant des nœuds. Le type doit être compatible avec {@link Maze}.
 */
public class GraphMaze<C> implements Graph<C> {

    /**
     * Le labyrinthe utilisé pour construire le graphe.
     */
    private Maze<C> maze;

    /**
     * Construit un graphe à partir d'un labyrinthe donné.
     *
     * @param maze Le labyrinthe à adapter en graphe.
     * @throws IllegalArgumentException Si {@code maze} est {@code null}.
     */
    public GraphMaze(Maze<C> maze) {
        if (maze == null) {
            throw new IllegalArgumentException("Le labyrinthe ne peut pas être null");
        }
        this.maze = maze;
    }

    /**
     * Retourne la liste des successeurs d'un nœud donné dans le graphe.
     * Chaque successeur correspond à un voisin ouvert dans le labyrinthe, représenté
     * par un arc de poids 1. Si une erreur survient lors de l'accès aux voisins,
     * une liste vide est retournée.
     *
     * @param node Le nœud dont on veut les successeurs.
     * @return Une liste d'arcs représentant les successeurs, avec un poids de 1 chacun.
     */
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