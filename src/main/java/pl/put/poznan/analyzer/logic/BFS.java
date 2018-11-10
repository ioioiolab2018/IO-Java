package pl.put.poznan.analyzer.logic;

import org.springframework.stereotype.Service;
import pl.put.poznan.analyzer.commons.Connection;
import pl.put.poznan.analyzer.commons.Data;
import pl.put.poznan.analyzer.commons.Node;
import pl.put.poznan.analyzer.commons.NodeType;

import javax.swing.text.StyledEditorKit;
import java.util.*;

@Service
public class BFS {

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
        c7o.add(new Connection(6, 2, 4.0f));
        List<Connection> c7i = new ArrayList<>();
        c7i.add(new Connection(2, 6, 10.0f));
        c7i.add(new Connection(4, 6, 8.0f));
        c7i.add(new Connection(5, 6, 4.0f));
        przykladowe.add(new Node(6, "koniec", NodeType.EXIT, c7o, c7i));

        return przykladowe;
    }

    public static List<Connection> run(List<Node> nodesList) {

        System.out.println("WITAMY W BFS\n");

        List<Node> przykladowe = utworz();

        float wartosc;
        int stala, noweId, stareIdKoncowe, dokad, licznik = 0;

        List<Node> grafNiewazony = new LinkedList<>();
        for (Node n : przykladowe)
        {
            //Node n = przykladowe.get(0);
            //dla glownych wierzcholkow
            List<Connection> glownePolaczenia = new LinkedList<>();
            glownePolaczenia.clear();
            //glownePolaczenia.clear();
            for (Connection con : n.getOutgoing())
            {
                stala = 1000;
                wartosc = con.getValue(); //wartosc
                dokad = con.getTo();        //wierzcholek docelowy
                con.setValue((float) 1.0);   //wartosc wychodzaca z glownego wierzcholka na 1.0
                if(wartosc > 1.0)
                    con.setTo(stala + dokad);       //dla pierwszego wierzchołka powinniśmy otrzymać połączenie np. [0,1001,1]
                glownePolaczenia.add(con);

                //dla pozostalych polaczen

                while(wartosc > 1.0)
                {
                    wartosc--;
                    Node n1 = new Node();
                    n1 = n;
                    n1.setNodeType(NodeType.REGULAR);
                    noweId=con.getTo() + stala;
                    n1.setId(noweId);
                    stala+=1000;
                    Connection co = new Connection(); //polaczenie wyjsciowe dla nowego wezla
                    if(wartosc == 1.0)
                    {
                        co = new Connection(noweId, dokad, (float) 1.0);
                    }
                    else
                    {
                        co = new Connection(noweId, noweId+1000, (float) 1.0);
                    }
                    List <Connection> coList = new LinkedList<>();
                    coList.add(co);
                    n1.setOutgoing(coList);
                    grafNiewazony.add(n1);
                    System.out.println(n1.getId() + " " + n1.getName() + " " + n1.getOutgoing().toString() + n1.getNodeType());
                }
            }

            n.setOutgoing(glownePolaczenia);
            //grafNiewazony.add(new Node(n.getId(), n.getName(), n.getNodeType(), glownePolaczenia, null));
            grafNiewazony.add(n);



            //System.out.println(n.getId() + " " + n.getName() + " " + n.getOutgoing().toString() + " " + n.getNodeType());

            //}
        }
        for (Node nd : grafNiewazony) {
            System.out.println(nd.getId() + " " + nd.getName() + " " + nd.getOutgoing().toString() + " " + nd.getNodeType());
        }

        /*
        Map<Integer, Node> nodes = Data.getNodesMap(przykladowe);

        if (nodes == null) {
            return null;
        }
        //ObjectMapper mapper = new ObjectMapper();
        //Node n1 = mapper.readValue(new File("graf1.json"), Node.class);

        Map<Integer, Boolean> visited = new HashMap<>();
        Queue<Node> Q = new PriorityQueue<>();
        // Mape visited zerujemy
        for (Map.Entry<Integer, Node> entry : nodes.entrySet()) {
            int id = entry.getKey();
            visited.putIfAbsent(id, false);
        }
        // Tworzymy tablicę ścieżki
        List<Node> patchNode = new LinkedList<>();

        List<Connection> patchConnection = new LinkedList<>();

        Node vs = Data.getEnterNode(nodes); //wierzchołek startowy
        if (vs != null) {
            patchNode.add(vs);
        } else {
            return null;
        }

        Q.add(vs);
        visited.replace(vs.getId(), true);
        Node v;
        int u;
        boolean found = false;

        while (!(Q.isEmpty())) {
            v = Q.poll();
            // Pobieramy z kolejki wierzchołek v
            if (v.getNodeType() == NodeType.EXIT) {// Sprawdzamy koniec ścieżki
                found = true;        // Zaznaczamy sukces
                break;               // Przerywamy pętlę
            }
            // Przeglądamy sąsiadów wierzchołka v
            for (Connection con : v.getOutgoing()) {
                u = con.getTo();
                if (!visited.get(u)) {
                    patchConnection.add(con);
                    patchNode.add(nodes.get(u));
                    Q.add(Data.getNodeById(nodes, u));
                    visited.replace(u, true);
                    break;
                }
            }
        }
        if (found) {
            return patchConnection;//TODO
        }*/
        return null;
    }
}
