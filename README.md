# Network Analyzer - REST api umożliwiające zarządzanie sieciami
Aby uruchomic aplikacje należy posiadać uruchomioną bazę danych Oracle z hasłem do konta SYSTEM "Weblogic1"
Ustawienia bazy dancyh można zmienic w pliku "aplication.properties"

Można uruchomić aplikacje z bazą danych automatycznie tworzoną w pamięci operacyjnej, w tym celu należy:
Dodać do pom.xml zalezność: 
<dependency>
  <groupId>com.h2database</groupId>
  <artifactId>h2</artifactId>
  <scope>runtime</scope>
</dependency>

oraz usunąć ustawienia z pliku "aplication.properties" (wszystko oprócz 2 pierwszych lini).

Projekt najlepiej utruchamiać i budowac w programie IntelliJ, Java 8

Działające funckje: 
Wyszukanie najkrótszej ścieżki:
*Algorytm BFS
```
POST localhost/bfs/nodes
Sieć w Json w Body (przykładowe sieci w resources)
```
*Algorytm DFS 
```
POST localhost/dfs/nodes
Sieć w Json w Body (przykładowe sieci w resources)
```
*Algorytm zachłanny 
```
POST localhost/greedy/nodes
Sieć w Json w Body (przykładowe sieci w resources)
```

*Zapisanie sieci w bazie danych
```
POST localhost/saveNetwork
Sieć w Json w Body (przykładowe sieci w resources)
W odpowiedzi ID sieci
```
*Odczytanie sieci z bazy danych
```
GET localhost/getNetwork/{id}
id - id sieci (odpowiedż metody saveNetwork)
W odpowiedzi siec w formacie Json
```
*Usunięcie sieci z bazy danych
```
GET localhost/deleteNetwork/{id}
id - id sieci (odpowiedż metody saveNetwork)
```


