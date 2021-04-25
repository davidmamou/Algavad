package algorithms;

import java.awt.Point;
import java.util.ArrayList;

public class FloydWarshall {

		private int[][] path;
		private double[][] distance;
				
	public FloydWarshall(int[][] path, double[][] distance) {
		this.path = path;
		this.distance = distance;
	}
				
  public static FloydWarshall calculShortestPaths(ArrayList<Point> points, int edgeThreshold) {
    int[][] paths=new int[points.size()][points.size()];
    for (int i=0;i<paths.length;i++) for (int j=0;j<paths.length;j++) paths[i][j]=i;

    double[][] distance=new double[points.size()][points.size()];

    for (int i=0;i<paths.length;i++) {
      for (int j=0;j<paths.length;j++) {
        if (i==j) {distance[i][i]=0; continue;}
        if (points.get(i).distance(points.get(j))<=edgeThreshold) distance[i][j]=points.get(i).distance(points.get(j)); else distance[i][j]=Double.POSITIVE_INFINITY;
        paths[i][j]=j;
      }
    }

    for (int k=0;k<paths.length;k++) {
      for (int i=0;i<paths.length;i++) {
        for (int j=0;j<paths.length;j++) {
          if (distance[i][j]>distance[i][k] + distance[k][j]){
            distance[i][j]=distance[i][k] + distance[k][j];
            paths[i][j]=paths[i][k];

          }
        }
      }
    }
    return new FloydWarshall(paths, distance);
  }
  public double[][] getDist(){
  	return this.distance;
  }
  public int[][] getPath(){
	  return this.path;
  }
  
}