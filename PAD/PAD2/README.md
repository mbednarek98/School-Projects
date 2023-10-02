Zadanie 1 (1 pkt)

Napisz program w Pythonie, który utworzy funkcję lambda, która dodaje 15 do podanej liczby przekazanej jako argument, stwórz również funkcję lambda, która mnoży argument x przez argument y i wypisz wynik.

Zadanie 2 (1 pkt)

Napisz program w Pythonie do sortowania listy słowników za pomocą Lambda.
[{'make': 'Nokia', 'model': 216, 'color': 'Black'}, {'make': 'Mi Max', 'model': 2, 'color': 'Gold'}, {'make': 'Samsung', 'model': 7, 'color': 'Blue'}]

Zadanie 3 (1 pkt)

Napisz program w Pythonie, który podniesie do kwadratu i sześcianu każdą liczbę z podanej listy liczb całkowitych za pomocą Lambda.
[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]

Zadanie 4*(3 punkty)

Stwórz klasę Game, która będzie klasą bazową, z metodą z play, która ma w nazwie wskazywać, że nie należy jej modyfikować (metoda chroniona), oraz która wyświetla informację o rozpoczęciu gry. Dodatkowo ma zawierać liczbę graczy.

Stwórz drugą klasę Hangman, która dziedziczy po klasie Game i pozwoli zagrać w grę wisielec, gdzie gracz musi odgadnąć słowo, podając pojedyncze litery.
Hangman ma 1 albo 2 graczy, przy 2 – jedna osoba wpisuje słowo do odgadnięcia.

Gra ma umożliwiać wybór poziomu trudności (beginner, intermediate, advanced) i w zależności od wybranego poziomu, dostępna liczba błędnie odgadniętych liter ma być mniejsza. Np. dla poziomu beginner gracz może pomylić się 8 razy, intermediate 5 razy, a advanced 3 razy.

W grze mamy też mieć możliwość wyboru liczby graczy, jeżeli jest jeden to słowo losowane jest z listy, a jeżeli dwóch graczy, to jeden wpisuje słowo do odgadnięcia, a drugi je zgaduje.

Gra może być stworzona dla dowolnego języka, ale jeden język na grę (np. angielski).

Zadanie 4 należy oddać w formacie .py, tak, żeby była możliwość uruchomienia i grania w terminalu.

Do pobierania słów/znaków od użytkownika skorzystaj z metody gotowej metody input(). 

Dodatkowo pamiętaj o obsłudze wyjątków, jeżeli np. to co wpisał w terminalu gracz jest spoza dopuszczalnej listy znaków.

