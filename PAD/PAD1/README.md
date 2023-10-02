Zadanie 1

a)	Stwórz klasy dog i cat, które dziedziczyć będą po klasie animal.
Klasa animal ma następujące atrybuty:
    isAlive = True
    gender = Do wyboru Male/Female (domyślnie Female)
    genus = nazwa gatunkowa (Canis dla psa, Felis  - dla kota)
    klasa animal posiada też metodę breed o parametrze partner. 
Metoda ta ma zwracać instancję klasy animal, typu tego samego, co instancja, w której wywołujemy metodę breed pod warunkiem, że instancja dla której wywołujemy metodę:
    a) Jest samicą
    b) Partner jest samcem
    c) Instancja oraz partner należą do jednego gatunku
Klasa dog posiada metodę woof, zwracającą string woof woof.
Klasa cat posiada metodę purr, zwracającą string purr.
    b)	Stwórz instancję klas dog i cat, za pomocą metody breed. Do metody breed instrukcję przechwytywania wyjątku, która wyświetli string attribute not found, jeżeli podany w parametrze partner nie posiada niezbędnych atrybutów.


Zadanie 2

Stwórz klasę worker.
Klasa ma atrybut salary, zwracającą wynagrodzenie.
Stwórz instancje klasy, o danych wartościach:
n.  Name    Age	    Salary
1	Adam	1983	1500
2	Anna	1981	1700
3	Błażej	1990	1800
4	Beata	1992	1600
5	Czesław	1980	2000
6	Cecylia	1983	2100
7	Daniel	1976	1900

Napisz instrukcję, które:
- policzy średnie zarobki w firmie
- porówna średni zarobek wśród osób młodszych niż 30 lat i starszych.
