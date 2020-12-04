package sample;

import java.util.*;

public class DirGraph {

   private ArrayList<GrNode> nodes = new ArrayList<>();

   public ArrayList<Path> findPaths(int start, int end) {
      ArrayList<Path> paths = new ArrayList<>();
      if (start < nodes.size() && end < nodes.size() && start >= 0 && end >= 0 && start != end) {
         Path path = new Path(nodes.get(start));
         findPath(paths, new Path(path), nodes.get(start), nodes.get(end));
      }
      return paths;
   }

   private void findPath( ArrayList<Path> paths, Path path, GrNode start, GrNode end) {
      for (Link link : start.getLinks()) {
         Path p = new Path(path);
         p.add(link);
         if (link.getDest() == end)
            paths.add(p);
         else findPath(paths, p , link.getDest(), end);
      }
   }

   public GrNode findCentral() {
      int min = -1;
      for (int i = 0; i < nodes.size(); i++) {
         if (min == -1) min = i;
         else
            min = Math.min(max(dijkstra(i)), min);
      }
      return nodes.get(min);
   }

   private int max(int[] arr) {
      int max = -1;
      for (int i = 0; i < arr.length; i++) {
         if (max == -1 || arr[max] < arr[i]) {
            max = i;
         }
      }
      return max;
   }

   int[] dijkstra(int index) {
      //init
      int[] out = new int[nodes.size()];
      for (int i = 0; i < nodes.size(); i++) out[i] = Integer.MAX_VALUE;
      out[index] = 0;
      for (Link link : nodes.get(index).getLinks())
         out[link.getDest().getIndex()] = link.getDist();
      HashSet<Integer> passed = new HashSet<>();
      passed.add(index);
      //
      for (int i = 0; i < out.length - 1; i++) {
         int min = -1;
         for (int j = 0; j < out.length; j++) {
            if (!passed.contains(j))
               if (min == -1 || out[j] < out[min])
                  min = j;
         }
         passed.add(min);
         for (Link link : nodes.get(min).getLinks()) {
            int chInd = link.getDest().getIndex();
            out[chInd] = Math.min(out[chInd], out[min] + link.getDist());
         }
      }
      return out;
   }

   public void add(Vertex v) {
      nodes.add(new GrNode(nodes.size(),v));
   }

   public Link addLink(int start, int end, int weight) {
      if (start < nodes.size() && end < nodes.size() && start >= 0 && end >= 0)
         return nodes.get(start).addLink(nodes.get(end), weight);
      return null;
   }

   public ArrayList<GrNode> getNodes() {
      return nodes;
   }
}

class GrNode {

   private int index;
   private ArrayList<Link> links = new ArrayList<>();
   private Vertex vis;

   public Link addLink(GrNode child, int weight) {
      Link r = new Link(weight, child);
      links.add(r);
      return r;
   }

   public GrNode(int index, Vertex vis) {
      this.index = index;
      this.vis = vis;
   }

   public int getIndex() {
      return index;
   }

   public Vertex getVertex() {
      return vis;
   }

   public ArrayList<Link> getLinks() {
      return links;
   }
}

class Link {
   private int dist;
   private GrNode dest;

   public Link(int dist, GrNode to) {
      this.dist = dist;
      this.dest = to;
   }

   public int getDist() {
      return dist;
   }

   public void setDist(int dist) {
      this.dist = dist;
   }

   public GrNode getDest() {
      return dest;
   }
}

class Path implements Comparable<Path>{

   private GrNode src;
   private ArrayList<Link> links = new ArrayList<>();
   private int dist = 0;

   public Path(GrNode src) {
      this.src = src;
   }

   public void add(Link link) {
      links.add(link);
      dist+=link.getDist();
   }

   public String toString() {
      String out ="";

      out+=src.getIndex() + " ";
      for (Link l : links) {
         out+=l.getDest().getIndex() + " ";
      }
      return out;
   }

   public ArrayList<Link> getLinks() {
      return links;
   }

   public GrNode getSrc() {
      return src;
   }

   public int getDist() {
      return dist;
   }

   public Path (Path path) {
      this.src = path.getSrc();
      this.links.addAll(path.getLinks());
      this.dist = path.getDist();
   }

   @Override
   public int compareTo(Path path) {
      return Integer.compare(dist,path.getDist());
   }
}
