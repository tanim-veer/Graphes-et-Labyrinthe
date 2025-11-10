# 🧩 Graphes-et-Labyrinthe

Projet étudiant – **BUT1 Informatique** – Groupe **108**  
Réalisé par : **VEER Tanim**, **NGUELET Ilan**, **BEN KHELIFA Elyes**, **MEBALEY KAHEL Ethan**

---

## 🎯 Objectif

L’objectif de ce projet est de **modéliser des graphes** et de **générer / résoudre des labyrinthes** à l’aide de techniques algorithmiques classiques :
- Parcours de graphes (BFS/DFS)
- Recherche de plus court chemin (ex. Dijkstra)
- Animation visuelle des étapes

Le but est à la fois **pédagogique** (structures de données, algorithmie, optimisation) et **applicatif** (interface graphique, visualisation et animation).

---

## 🚀 Démos / Captures
- `docs/screenshot-maze.png`
- `docs/screenshot-graph.png`
- `docs/animation-dijkstra.gif`

---

## 🛠️ Technologies & Architecture

- **Langage :** Java (100 %)  
- **Outils :** IntelliJ IDEA, Git, Java JDK  
- **Versioning :** GitHub

---

## ▶️ Lancement rapide
- Prérequis : JDK 17 (ou version compatible)  
- git clone https://github.com/tanim-veer/Graphes-et-Labyrinthe.git  
- cd Graphes-et-Labyrinthe
- Depuis IntelliJ : ouvrir le projet, configurer le SDK, puis exécuter la classe principale  

---

## ✅ Fonctionnalités clés
- Génération de labyrinthes via algorithmes de graphe
- Construction et affichage de graphes (noeuds, arêtes)
- Algorithme de plus-court chemin (ex. Dijkstra’s algorithm)
- Animation de parcours (visuel)
- Mesure de performance via bench (benchmarks)
- Architecture modulaire (séparation graphe / labyrinthe / UI)

---

## 🔍 Points techniques intéressants
- Implémentation de Dijkstra pour le plus court chemin
- Adaptation de la structure de graphe à la génération de labyrinthe
- Utilisation de patrons de conception (ex. Adapter, Strategy) pour flexibilité
- Visualisation interactive : UI qui montre étape par étape l’algorithme
-  Benchmarks comparant différentes tailles de graphe/maze (bench/)
   
---

## 📚 Notes
- Contraintes : interface graphique simple mais fonctionnelle, génération rapide de labyrinthes, code documenté.
- Limites / améliorations futures : améliorer l’ergonomie de l’UI, ajouter d’autres algorithmes (A*, BFS/DFS animés), export de labyrinthes en image ou impression, version web.
