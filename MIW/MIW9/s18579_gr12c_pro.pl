:- dynamic pozycja/1, przedmiot/2, na/2, pod/2, obok/2, w/2, plecak/1.

%JEZYK FAKTOW
dom([kuchnia,lazienka,salon,przedpokoj,rozwidlenie,mojpokoj,bratpokoj]).

pokoj(kuchnia, 'Kuchnia').
pokoj(lazienka, 'Lazienka').
pokoj(salon, 'Salon').
pokoj(przedpokoj, 'Przedpokoj').
pokoj(rozwidlenie, 'Rozwidlenie przedpokoju').
pokoj(mojpokoj, 'Moj Pokoj').
pokoj(bratpokoj, 'Pokoj Brata').

sciezka(przedpokoj,mojpokoj,lewo).
sciezka(mojpokoj,przedpokoj,tyl).

sciezka(przedpokoj,rozwidlenie, przod).
sciezka(rozwidlenie, przedpokoj, tyl).

sciezka(rozwidlenie,kuchnia, lewo).
sciezka(kuchnia, rozwidlenie, tyl).

sciezka(rozwidlenie,lazienka, przod).
sciezka(lazienka, rozwidlenie, tyl).

sciezka(rozwidlenie,salon, prawo).
sciezka(salon, rozwidlenie, tyl).

sciezka(salon,bratpokoj, prawo).
sciezka(bratpokoj,salon, tyl).


wcisk :- retractall(pozycja(_)),
                   retractall(przedmiot(_,_)),
                   retractall(w(_,_)),
                   retractall(na(_,_)),
                   retractall(obok(_,_)),
                   retractall(pod(_,_)),
                   retractall(plecak(_)),
                   assert(pozycja(przedpokoj)),
                   assert(w(szafa,posciel)),
                   assert(na(biurko, telefon)),
                   assert(obok(telewizor, roslinka)),
                   assert(pod(stol, swieczka)),
                   assert(w(sedes, niespodzianka)),
                   assert(w(lodowka, batonik)),
                   assert(pod(lozko,sztanga)),
                   assert(przedmiot(szafa,przedpokoj)),
                   assert(przedmiot(biurko,mojpokoj)),
                   assert(przedmiot(stol,salon)),
                   assert(przedmiot(telewizor,salon)),
                   assert(przedmiot(sedes,lazienka)),
                   assert(przedmiot(lodowka,kuchnia)),
                   assert(przedmiot(lozko, bratpokoj)).

instrukcja :- write('Instrukcja:'),nl, 
              write('-> Mozesz poruszac sie tylko w: przod, tyl, lewo, prawo'),nl,
              write('-> Wpisujac "gdziejestem" poznasz swoja pozycje oraz gdzie mozesz isc'),nl,
              write('-> Wpisujac "pokazDom" zobaczysz jakie pokoje sa w domu'),nl,
              write('-> Wpisujac "spojrz" zobaczysz przedmioty w danym pokoju'),nl,
              write('-> Wpisujac "spojrzNa(przedmiot)" zobaczysz co jest na przedmiocie'),nl,
              write('-> Wpisujac "spojrzPod(przedmiot)" zobaczysz co jest pod przedmiocie'),nl,
              write('-> Wpisujac "spojrzObok(przedmiot)" zobaczysz co jest obok przedmiocie'),nl,
              write('-> Wpisujac "spojrzW(przedmiot)" zobaczysz co jest w przedmiocie'),nl,
              write('-> Wpisujac "pokazPlecak" pokaze jakie przedmioty masz w plecaku'),nl,
              write('-> Wpisujac "wez(przedmiot)" wezmiesz przedmiot do plecaka'),nl,
              write('-> Wpisujac "odlozNa(przedmiot z plecaka, miejsce gdzie odkladasz)" odkladasz przedmiot z plecaka na wybrane miejsce'),nl,
              write('-> Wpisujac "odlozPod(przedmiot z plecaka, miejsce gdzie odkladasz)" odkladasz przedmiot z plecaka pod wybranym miejscem'),nl,
              write('-> Wpisujac "odlozObok(przedmiot z plecaka, miejsce gdzie odkladasz)" odkladasz przedmiot z plecaka obok wybranego miejsca'),nl,
              write('-> Wpisujac "odlozW(przedmiot z plecaka, miejsce gdzie odkladasz)" odkladasz przedmiot z plecaka w wybrane miejsce'),nl,
              write('-> Wpisujac lewo, prawo, przod, tyl idziesz w tym kierunku'),nl.

start :- wcisk,
	     write('Witaj w moim domu'),nl,nl,
         instrukcja,nl.

gdziejestem :-
    write('Jestes w '),
    pozycja(X), pokoj(X,Y),
    write(Y), nl,
    write('Mozesz pojsc: '), nl
	,sciezka(X,Z,Poz),pokoj(Z,O),write('--> '),write(O),write(' w kierunku '), write(Poz),nl,fail,nl.

idz(X) :- pozycja(Z), sciezka(Z,Y,X), retract(pozycja(Z)), assert(pozycja(Y)),nl,gdziejestem.
lewo :- idz(lewo).
prawo :- idz(prawo).
przod :- idz(przod).
tyl :- idz(tyl).

spojrz :-   write('W tym pokoju znajduje sie: '),pozycja(X), przedmiot(Y,X),nl,write('--> '),write(Y),fail.

spojrzNa(X) :- na(X,Y), write(Y), write(' znajduje sie na '),write(X),nl.
spojrzNa(_) :- write('Nic tu nie ma').

spojrzPod(X) :- pod(X,Y), write(Y), write(' znajduje sie pod '),write(X),nl.
spojrzPod(_) :- write('Nic tu nie ma').

spojrzObok(X) :- obok(X,Y), write(Y), write(' znajduje sie obok '),write(X),nl.
spojrzObok(_) :- write('Nic tu nie ma').

spojrzW(X) :- w(X,Y), write(Y), write(' znajduje sie w '),write(X),nl.
spojrzW(_) :- write('Nic tu nie ma').

wez(Y) :- na(X,Y), assert(plecak(Y)), write(Y) , write(' zabrales z nad '), write(X), retract(na(X,Y)).
wez(Y) :- pod(X,Y), assert(plecak(Y)), write(Y) , write(' zabrales z pod '), write(X), retract(pod(X,Y)).
wez(Y) :- obok(X,Y), assert(plecak(Y)), write(Y) , write(' zabrales z obok '), write(X), retract(obok(X,Y)).
wez(Y) :- w(X,Y), assert(plecak(Y)), write(Y) , write(' zabrales z '), write(X), retract(w(X,Y)).

pokazPlecak :- write('W plecaku masz: '),nl,plecak(X), write(X),nl,fail.

pokazDom :- dom(X), write(X).

odlozNa(X,Y) :- retract(plecak(X)), assert(na(Y,X)), write('Polozyles '), write(X), write(' na '), write(Y).
odlozPod(X,Y) :- retract(plecak(X)), assert(pod(Y,X)), write('Polozyles '), write(X), write(' pod '), write(Y).
odlozObok(X,Y) :- retract(plecak(X)), assert(obok(Y,X)), write('Polozyles '), write(X), write(' obok '), write(Y).
odlozW(X,Y) :- retract(plecak(X)), assert(w(Y,X)), write('Polozyles '), write(X), write(' w '), write(Y).

