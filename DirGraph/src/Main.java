import org.w3c.dom.ls.LSOutput;

import java.util.Arrays;
import java.util.Collections;

public class Main {

    public static void main(String[] args) {
        DirGraph dg = new DirGraph();
        dg.add("1");
        dg.add("2");
        dg.add("3");
        dg.add("4");
        dg.add("5");
        dg.addLink(0,1,10);
        dg.addLink(1,2,50);
        dg.addLink(2,4,10);
        dg.addLink(3,2,20);
        dg.addLink(0,3,30);
        dg.addLink(3,4,60);
        dg.addLink(0,4,100);
        System.out.println(Arrays.toString(dg.dijkstra(0)));
        for (Path p : dg.findPaths(0,4)) {
            System.out.println(p.toString());
        }
    }

}
