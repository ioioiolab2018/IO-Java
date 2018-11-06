package pl.put.poznan.analyzer.logic;

import pl.put.poznan.analyzer.commons.Connection;
import pl.put.poznan.analyzer.commons.Node;

import java.util.*;

public class BFS {
    public static final int SIZE_OF_INT = 2147483647;


    public static List<Connection> run(Node vs, Node vk, char bd) {
        System.out.println("eheheh\n");

        //ListaWezlow.add(new Node(111, 'xd', NodeType nodeType, List<Connection> outgoing, List<Connection> incoming))

        boolean[] visited = new boolean[SIZE_OF_INT];
        Queue<Node> Q = new PriorityQueue<Node>();
        Deque<Node> S = new ArrayDeque<Node>();


        for(int i = 0; i <  SIZE_OF_INT; i++)   // Tablicę visited zerujemy
            visited[i] = false;

        int[] P = new int[SIZE_OF_INT];          // Tworzymy tablicę ścieżki
        P[vs.getId()]=-1;   //wierzchołek startowy
        if(bd=='b')  Q.add(vs);
        else         S.push(vs);
        visited[vs.getId()]=true;

        Node v=null,u=null;
        boolean found = false;

        while(!(Q.isEmpty() && S.isEmpty()) )
        {
            if(bd=='b') v = Q.poll();         // Pobieramy z kolejki wierzchołek v
            else        v = S.pop();

            if(v.getId() == vk.getId())            // Sprawdzamy koniec ścieżki
            {
                found = true;        // Zaznaczamy sukces
                break;               // Przerywamy pętlę
            }

            // Przeglądamy sąsiadów wierzchołka v
            for(Connection con: v.getOutgoing())
            //for()
            {
                u = con.getTo();
                if(!visited[u.getId()])
                {
                    P[u.getId()]=v.getId();
                    if(bd=='b')  Q.add(u);
                    else         S.push(u);
                    visited[u.getId()] = true;
                }
            }
        }

        return new ArrayList<>();
//        if(!found) System.out.println("BRAK ROZWIĄZANIA\n");
//        else
//            while(v.getId()>-1)
//            {
//                System.out.println(v + "   ");
//                v.setId(P[v.getId()]);
//            }

    }
}
