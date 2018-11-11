package pl.put.poznan.analyzer.logic;

import org.springframework.stereotype.Service;
import pl.put.poznan.analyzer.commons.Connection;
import pl.put.poznan.analyzer.commons.Data;
import pl.put.poznan.analyzer.commons.Node;
import pl.put.poznan.analyzer.commons.NodeType;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class DFS {
    
    public static List<Node> utworz() {
        List<Node> przykladowe = new ArrayList<>();

        List<Connection> c1o = new ArrayList<>();
        c1o.add(new Connection(0, 1, 2.0f));
        c1o.add(new Connection(0, 2, 3.0f));
        c1o.add(new Connection(0, 3, 5.0f));
        List<Connection> c1i = new ArrayList<>();
        przykladowe.add(new Node(0, "pariatur", NodeType.ENTRY, c1o, c1i));

        List<Connection> c2o = new ArrayList<>();
        c2o.add(new Connection(1, 4, 3.0f));
        c2o.add(new Connection(1, 2, 7.0f));
        List<Connection> c2i = new ArrayList<>();
        c2i.add(new Connection(0, 1, 2.0f));
        przykladowe.add(new Node(1, "non", NodeType.REGULAR, c2o, c2i));

        List<Connection> c3o = new ArrayList<>();
        c3o.add(new Connection(2, 3, 4.0f));
        c3o.add(new Connection(2, 6, 10.0f));
        List<Connection> c3i = new ArrayList<>();
        c3i.add(new Connection(0, 2, 3.0f));
        c3i.add(new Connection(1, 2, 7.0f));
        c3i.add(new Connection(4, 2, 2.0f));
        przykladowe.add(new Node(2, "cupi", NodeType.REGULAR, c3o, c3i));

        List<Connection> c4o = new ArrayList<>();
        c4o.add(new Connection(3, 5, 7.0f));
        List<Connection> c4i = new ArrayList<>();
        c4i.add(new Connection(0, 3, 5.0f));
        c4i.add(new Connection(2, 3, 4.0f));
        przykladowe.add(new Node(3, "elit", NodeType.REGULAR, c4o, c4i));

        List<Connection> c5o = new ArrayList<>();
        c5o.add(new Connection(4, 5, 3.0f));
        c5o.add(new Connection(4, 2, 2.0f));
        c5o.add(new Connection(4, 6, 8.0f));
        List<Connection> c5i = new ArrayList<>();
        c5i.add(new Connection(1, 4, 3.0f));
        przykladowe.add(new Node(4, "deser", NodeType.REGULAR, c5o, c5i));

        List<Connection> c6o = new ArrayList<>();
        c6o.add(new Connection(5, 6, 4.0f));
        List<Connection> c6i = new ArrayList<>();
        c6i.add(new Connection(3, 5, 7.0f));
        c6i.add(new Connection(4, 5, 3.0f));
        przykladowe.add(new Node(5, "culpa", NodeType.REGULAR, c6o, c6i));

        List<Connection> c7o = new ArrayList<>();
        List<Connection> c7i = new ArrayList<>();
        c7i.add(new Connection(2, 6, 10.0f));
        c7i.add(new Connection(4, 6, 8.0f));
        c7i.add(new Connection(5, 6, 4.0f));
        przykladowe.add(new Node(6, "koniec", NodeType.EXIT, c7o, c7i));

        return przykladowe;
    }

    
    
    public static List<Connection> run(List<Node> noList) {
        List <Node> przykladowe = utworz();
        float wartosc;
        int stala, noweId, stareIdKoncowe, dokad, skad, licznik = 0;

        licznik=przykladowe.size()-1;
        List<Node> grafNiewazony = new LinkedList<>();
        for (Node n : przykladowe) 
        {
            //Node n = przykladowe.get(0);
                //dla glownych wierzcholkow
            if(n.getNodeType() == NodeType.EXIT)
            {
                grafNiewazony.add(n);
                break;
            }
            List<Connection> glownePolaczenia = new LinkedList<>();
            glownePolaczenia.clear();
            //glownePolaczenia.clear();
            for (Connection con : n.getOutgoing()) 
            {                
                licznik++;
                wartosc = con.getValue();     //wartosc
                dokad = con.getTo();          //wierzcholek docelowy
                skad = con.getFrom();
                //con.setValue((float) 1.0);    //wartosc wychodzaca z glownego wierzcholka na 1.0
                if(wartosc > 1.0)
                    con.setTo(licznik);       //dla pierwszego wierzchołka powinniśmy otrzymać połączenie np. [0, 000100001            [0,1001,1]
                else con.setTo(dokad);
                glownePolaczenia.add(con);  
                
                //dla pozostalych polaczen
                
                while(wartosc > 1.0)
                {
                    wartosc--; 
                    Node n1 = new Node();
                    n1.setNodeType(NodeType.ADDITIONAL);
                    //noweId=skad *1000000 + dokad*1000 + stala;
                    n1.setId(licznik);
                    n1.setIncoming(n.getIncoming());
                    //stala++;
                    Connection co = new Connection(); //polaczenie wyjsciowe dla nowego wezla
                    if(wartosc == 1.0)
                    {
                        co = new Connection(licznik, dokad, (float) 1.0);
                    }
                    else
                    {
                        
                        co = new Connection(licznik, licznik+1, (float) 1.0);
                        licznik++;
                    }
                    List <Connection> coList = new LinkedList<>();
                    coList.add(co);
                    n1.setOutgoing(coList);
                    grafNiewazony.add(n1);
                    //System.out.println(n1.getId() + " " + n1.getName() + " " + n1.getOutgoing().toString() + n1.getNodeType());  
                }
            }            
            
            n.setOutgoing(glownePolaczenia);
            //grafNiewazony.add(new Node(n.getId(), n.getName(), n.getNodeType(), glownePolaczenia, null));
            grafNiewazony.add(n);
        }

        Collections.sort(grafNiewazony, new Comparator<Node>()
                {
                    public int compare(Node n1, Node n2)
                    {
                        return Integer.valueOf(n1.getId()).compareTo(n2.getId());
                    }
                });
        //grafNiewazony.sort(grafNiewazony.getId());
        
        /*
        for(Node nd: grafNiewazony)
        {
            System.out.println(nd.toString());            
        }*/

        System.out.println("................");
        Node vs = new Node(); 
        Node vk = new Node();
        for(Node node: grafNiewazony)
        {
            if(node.getNodeType()== NodeType.ENTRY){
                vs=node;
            }
            if(node.getNodeType()== NodeType.EXIT){
                vk=node;
            }
        }
        
        licznik++;
        boolean[] visited = new boolean[licznik];
        //Queue <Node> Q = new PriorityQueue<Node>();
        Deque <Node> Stos = new ArrayDeque<Node>();
        for(int i = 0; i <  licznik; i++)   // Tablicę visited zerujemy
            visited[i] = false;
        int[] sciezka = new int[licznik];          // Tworzymy tablicę ścieżki
        for(int i=0; i<licznik; i++)
        {
            sciezka[i]=-5;
        }
        sciezka[vs.getId()]=-1;  
        //wierzchołek startowy
        Stos.push(vs);
        visited[vs.getId()]=true;
        Node v1=null,u1=null,ostateczny=null;
        boolean found = false;
        while(!(Stos.isEmpty()))
        {
	    v1 = Stos.pop();
            //System.out.println(v1.toString());
            Node v = new Node(v1.getId(),v1.getName(),v1.getNodeType(),v1.getOutgoing(),v1.getIncoming());
            //v = Q.poll();         // Pobieramy z kolejki wierzchołek v
            if(v.getNodeType() == NodeType.EXIT)            // Sprawdzamy koniec ścieżki
            {
                found = true;        // Zaznaczamy sukces
                ostateczny=v;
                break;               // Przerywamy pętlę
            }
            // Przeglądamy sąsiadów wierzchołka v
            for(Connection conn: v.getOutgoing())
            {
                Connection con = new Connection(conn.getFrom(),conn.getTo(),conn.getValue());
                u1 = grafNiewazony.get(con.getTo());
                Node u = new Node(u1.getId(),u1.getName(),u1.getNodeType(),u1.getOutgoing(),u1.getIncoming());
                if(!visited[u.getId()])
                {
                    sciezka[u.getId()]=v.getId();
                    //System.out.println(u.toString() + " .");
                    Stos.push(new Node(u.getId(),u.getName(),u.getNodeType(),u.getOutgoing(),u.getIncoming()));
                    visited[u.getId()] = true;
                }
            }
            //ostateczny=v;
        }
        System.out.println("Oto wynik!");
        //return new ArrayList<>();
        int suma=0;
        List <Connection> listaKoncowa = new LinkedList<>();
       if(!found) 
       {
           System.out.println("BRAK ROZWIĄZANIA\n");
       }
       else
       {
            int index = ostateczny.getId();
            int index2=0;
            while(index >-1)
            {
               if(index < przykladowe.size())
               {
                    //System.out.println(grafNiewazony.get(index).toString() + "   ");
                    //listaKoncowa.add(grafNiewazony.get(index).ge)
                    for(Connection con: grafNiewazony.get(index2).getIncoming())
                    {
                        //System.out.println(index + " " + index2);
                        if((con.getFrom() == index) && (con.getTo() == index2))
                        {
                            System.out.println(con.toString());
                            listaKoncowa.add(con);
                        }
                    }
                    index2=index;
               }
                //System.out.println("1:" + index);
               index=sciezka[index];
                //System.out.println("2:" + index);
               
               suma++;
            }           
       }
        System.out.println("Wartosc: " + suma);
       return listaKoncowa;
    }
}


