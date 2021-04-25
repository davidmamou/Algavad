package algorithms;

import java.awt.Point;
import java.util.ArrayList;

//Le path correspond au chemin de deux points juqu'a arriver au point final(I-x-y-z-J)
public class DefaultTeam {
  public Tree2D calculSteiner(ArrayList<Point> points, int edgeThreshold, ArrayList<Point> hitPoints) {
    //REMOVE >>>>>
//    Tree2D leafX = new Tree2D(new Point(700,400),new ArrayList<Tree2D>());
//    Tree2D leafY = new Tree2D(new Point(700,500),new ArrayList<Tree2D>());
//    Tree2D leafZ = new Tree2D(new Point(800,450),new ArrayList<Tree2D>());
//    ArrayList<Tree2D> subTrees = new ArrayList<Tree2D>();
//    subTrees.add(leafX);
//    subTrees.add(leafY);
//    subTrees.add(leafZ);
//    Tree2D steinerTree = new Tree2D(new Point(750,450),subTrees);
    //<<<<< REMOVE
	  FloydWarshall floyd = FloydWarshall.calculShortestPaths(points, edgeThreshold);
	  
	  double[][] distance = floyd.getDist();
	  int[][] path = floyd.getPath();
	  
	  ArrayList<Edge> steinerTree = Kruskal.calculSteiner(hitPoints,distance, points); 
	  
	  
	  ArrayList<Edge> arret = new ArrayList<Edge>();
	  
	  for(Edge e : steinerTree) {
		   arret.addAll(Kruskal.calculSteinerP(e, path, points));
	  }
	  
    return Kruskal.edgesToTree(arret, hitPoints.get(0));
  }
  public Tree2D calculSteinerBudget(ArrayList<Point> points, int edgeThreshold, ArrayList<Point> hitPoints) {
    //REMOVE >>>>>
   
    //<<<<< REMOVE

    FloydWarshall floyd = FloydWarshall.calculShortestPaths(points, edgeThreshold);
    
    double[][] distance = floyd.getDist();
	  int[][] path = floyd.getPath();
    
    int budget_limit = 1664;
    int budget_2 = budget_limit + 1;
    
    ArrayList<Edge> poid = new ArrayList<Edge>();
    
    while(budget_2 > budget_limit) {
    	budget_2 = 0;
    	poid = Kruskal.calculSteiner(hitPoints,distance, points);
    	for(Edge e : poid) {
    		budget_2 += e.weight;
    	}
    	hitPoints.remove(0);
    }
    
    ArrayList<Edge> arret = new ArrayList<Edge>();
	  
	  for(Edge e : poid) {
		   arret.addAll(Kruskal.calculSteinerP(e, path, points));
	  }
    
    return Kruskal.edgesToTree(arret,hitPoints.get(0));
  }
}
