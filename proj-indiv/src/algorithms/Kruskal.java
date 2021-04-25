package algorithms;

import java.awt.Point;
import java.util.ArrayList;

public class Kruskal {


	public static ArrayList<Edge> calculSteiner(ArrayList<Point> hitPoints, double[][] distance, ArrayList<Point> point) {
	    //KRUSKAL ALGORITHM, NOT OPTIMAL FOR STEINER!
	    ArrayList<Edge> edges = new ArrayList<Edge>();
	   
	    for (Point p: hitPoints) {
	      for (Point q: hitPoints) {
	        if (p.equals(q) || contains(edges,p,q)) continue;
	        edges.add(new Edge(p,q, distance[point.indexOf(p)][point.indexOf(q)]));
	      }
	    }
	    edges = sort(edges);

	    ArrayList<Edge> kruskal = new ArrayList<Edge>();
	    Edge current;
	    NameTag forest = new NameTag(hitPoints);
	    while (edges.size()!=0) {
	      current = edges.remove(0);
	      if (forest.tag(current.p)!=forest.tag(current.q)) {
	        kruskal.add(current);
	        forest.reTag(forest.tag(current.p),forest.tag(current.q));
	      }
	    }

	    return kruskal;
	  }
	  
	private static boolean contains(ArrayList<Edge> edges,Point p,Point q){
	    for (Edge e:edges){
	      if (e.p.equals(p) && e.q.equals(q) ||
	          e.p.equals(q) && e.q.equals(p) ) return true;
	    }
	    return false;
	  }
	
	public static Tree2D edgesToTree(ArrayList<Edge> edges, Point root) {
	    ArrayList<Edge> remainder = new ArrayList<Edge>();
	    ArrayList<Point> subTreeRoots = new ArrayList<Point>();
	    Edge current;
	    while (edges.size()!=0) {
	      current = edges.remove(0);
	      if (current.p.equals(root)) {
	        subTreeRoots.add(current.q);
	      } else {
	        if (current.q.equals(root)) {
	          subTreeRoots.add(current.p);
	        } else {
	          remainder.add(current);
	        }
	      }
	    }

	    ArrayList<Tree2D> subTrees = new ArrayList<Tree2D>();
	    for (Point subTreeRoot: subTreeRoots) subTrees.add(edgesToTree((ArrayList<Edge>)remainder.clone(),subTreeRoot));

	    return new Tree2D(root, subTrees);
	  }
	 
	private static ArrayList<Edge> sort(ArrayList<Edge> edges) {
	    if (edges.size()==1) return edges;

	    ArrayList<Edge> left = new ArrayList<Edge>();
	    ArrayList<Edge> right = new ArrayList<Edge>();
	    int n=edges.size();
	    for (int i=0;i<n/2;i++) { left.add(edges.remove(0)); }
	    while (edges.size()!=0) { right.add(edges.remove(0)); }
	    left = sort(left);
	    right = sort(right);

	    ArrayList<Edge> result = new ArrayList<Edge>();
	    while (left.size()!=0 || right.size()!=0) {
	      if (left.size()==0) { result.add(right.remove(0)); continue; }
	      if (right.size()==0) { result.add(left.remove(0)); continue; }
	      if (left.get(0).distance() < right.get(0).distance()) result.add(left.remove(0));
	      else result.add(right.remove(0));
	    }
	    return result;
	  }
	

	public static ArrayList<Edge> calculSteinerP(Edge e, int[][] path, ArrayList<Point> points){
		 ArrayList<Edge> edges = new ArrayList<Edge>();
		 
		 Point p = e.p;
		 Point q = e.q;
		 int index_1 = points.indexOf(p);
		 int index_2 = points.indexOf(q); 
		 int i= path[index_1][index_2];
		 Edge edge = new Edge(points.get(index_1), points.get(i), 0);
		 edges.add(edge);
		 //i=index_1 le but est donc d'arriver au dernier point. Possibilité de faire un do while
		 while(i != index_2) {
            index_1 = i;
			 i = path[index_1][index_2];
			 edge = new Edge(points.get(index_1), points.get(i), 0);
			 edges.add(edge);
		 }
		 
		 return edges;
	}
}

class Edge {
	  public Point p,q;
	  public double weight;
	  public Edge(Point p,Point q, double weight){ this.p=p; this.q=q; this.weight = weight; }
	  public double distance() {  return p.distance(q) ; }
	}


class NameTag {
    private ArrayList<Point> points;
    private int[] tag;

    protected NameTag(ArrayList<Point> points) {
        this.points = (ArrayList<Point>) points.clone();
        tag = new int[points.size()];
        for (int i = 0; i < points.size(); i++)
            tag[i] = i;
    }

    protected void reTag(int j, int k) {
        for (int i = 0; i < tag.length; i++)
            if (tag[i] == j)
                tag[i] = k;
    }

    protected int tag(Point p) {
        for (int i = 0; i < points.size(); i++)
            if (p.equals(points.get(i)))
                return tag[i];
        return 0xBADC0DE;
    }

}