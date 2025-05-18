package partie1.graphe;

/**
 * Interface pour manipuler des graphes orientés et valués avec des sommets de type String.
 *
 * Étend {@link Graph} avec des méthodes pour ajouter des sommets et des arcs, ainsi qu'une méthode
 * par défaut pour peupler un graphe à partir d'une chaîne de caractères.
 */
public interface VarGraph extends Graph<String> {
	/**
	 * Ajoute un sommet au graphe s'il n'est pas déjà présent.
	 *
	 * @param noeud Le nom du sommet à ajouter.
	 */
	void ajouterSommet(String noeud);

	/**
	 * Ajoute un arc orienté et valué entre deux sommets.
	 *
	 * Ajoute les sommets source et destination s'ils ne sont pas déjà présents.
	 *
	 * @param source      Le sommet source de l'arc.
	 * @param destination Le sommet destination de l'arc.
	 * @param valeur      La valuation (poids) de l'arc.
	 * @throws IllegalArgumentException si l'arc est déjà présent dans le graphe.
	 */
	void ajouterArc(String source, String destination, Integer valeur);

	/**
	 * Construit un graphe à partir d'une chaîne de caractères décrivant les arcs.
	 *
	 * Format attendu : "A-B(5), A-C(10), B-C(3), ..." où chaque arc est de la forme
	 * "source-destination(valeur)". Les arcs sont séparés par des virgules.
	 *
	 * @param str La chaîne décrivant les arcs du graphe.
	 * @throws IllegalArgumentException si le format de la chaîne est invalide ou si un arc est mal formé.
	 */
	default void peupler(String str) {
		String[] arcs = str.split(",\\s*");
		for (String arc : arcs) {
			arc = arc.trim();
			java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("([^-]+)-([^(]+)\\((-?\\d+)\\)")
					.matcher(arc);
			if (matcher.matches()) {
				String src = matcher.group(1).trim();
				String dest = matcher.group(2).trim();
				int val = Integer.parseInt(matcher.group(3).trim());
				ajouterSommet(src);
				ajouterSommet(dest);
				ajouterArc(src, dest, val);
			} else {
				throw new IllegalArgumentException("Format d'arc invalide : " + arc);
			}
		}
	}
}