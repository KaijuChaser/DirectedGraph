import java.util.*;

public class DirGraph {

   private ArrayList<Node> nodes = new ArrayList<>();

   public ArrayList<Path> findPaths(int start, int end) {
      ArrayList<Path> paths = new ArrayList<>();
      if (start < nodes.size() && end < nodes.size() && start >= 0 && end >= 0 && start != end) {
         Path path = new Path(nodes.get(start));
         findPath(paths, new Path(path), nodes.get(start), nodes.get(end));
      }
      return paths;
   }

   private void findPath( ArrayList<Path> paths, Path path, Node start, Node end) {
      for (Link link : start.getLinks()) {
         Path p = new Path(path);
         p.add(link);
         if (link.getDest() == end)
            paths.add(p);
         else findPath(paths, p , link.getDest(), end);
      }
   }

   public int findCentral() {
      int min = -1;
      for (int i = 0; i < nodes.size(); i++) {
         if (min == -1) min = i;
         else
            min = Math.min(max(dijkstra(i)), min);
      }
      return min;
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

   public void add(String name) {
      nodes.add(new Node(name, nodes.size()));
   }

   public void addLink(int start, int end, int weight) {
      if (start < nodes.size() && end < nodes.size() && start >= 0 && end >= 0)
         nodes.get(start).addLink(nodes.get(end), weight);
   }

}

class Node {

   private String name;
   private int index;
   private ArrayList<Link> links = new ArrayList<>();

   public void addLink(Node child, int weight) {
      links.add(new Link(weight, child));
   }

   public Node(String name, int index) {
      this.name = name;
      this.index = index;
   }

   public String getName() {
      return name;
   }

   public int getIndex() {
      return index;
   }

   public ArrayList<Link> getLinks() {
      return links;
   }
}

class Link {
   private int dist;
   private Node dest;

   public Link(int dist, Node to) {
      this.dist = dist;
      this.dest = to;
   }

   public int getDist() {
      return dist;
   }

   public Node getDest() {
      return dest;
   }
}

class Path {

   private Node src;
   private ArrayList<Link> links = new ArrayList<>();
   private int dist = 0;

   public Path(Node src) {
      this.src = src;
   }

   public void add(Link link) {
      links.add(link);
      dist+=link.getDist();
   }

   public String toString() {
      String out ="";

      out+=src.getName() + " ";
      for (Link l : links) {
         out+=l.getDest().getName() + " ";
      }
      return out;
   }

   public ArrayList<Link> getLinks() {
      return links;
   }

   public Node getSrc() {
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

}