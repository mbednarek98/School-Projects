# Mini-projekt 1



Zadanie polega na stworzeniu aplikacji mającej na celu zarządzanie oraz zapisywanie 
i odczytywanie listy zakupów przy wykorzystaniu androidowych metod przechowywania 
danych (SharedPreferences / DataStore, SQLite / Room, opcjonalnie Content Provider).

## Wymagania:

- Wykorzystanie kilku Activity (mogą być różne typy jak np. ListActivity, PreferenceActivity) 
oraz Intent do nawigacji pomiędzy widokami w aplikacji. Poniżej znajduje się minimalny 
zestaw Activities:

    - MainActivity: główne okno do nawigacji, znajdują się tu guziki do nawigacji do 
kolejnych komponentów graficznych.
    - ProductListActivity: zestawienie reprezentujące listę zakupów. Każdy element 
w liście powinien posiadać następujące informacje: nazwa produktu, cena, 
ilość, oznaczenie czy zostało już zakupione. Dodatkowo powinny znaleźć się 
elementy GUI (w dowolnym miejscu) odpowiedzialne za dodawanie nowych
produktów do listy, modyfikację oraz usuwanie istniejących. Rekomendowane 
zastosowanie RecyclerView.

    - OptionsActivity: ekran reprezentujący opcje związane z aplikacją. Co najmniej 
2 (np. rozmiar i kolor dowolnych elementów w apce).

- Należy zapisać wartości opcji za pomocą SharedPreferences lub DataStore. Przy ponownym 
odpaleniu apki odczytujemy poprzednio zapisane wartości. [3 pkt]
- Należy także zapisać listę produktów za pomocą bazy SQLite (Room). Stworzyć co 
najmniej jedną tabelę przechowującą wszystkie wartości wypisane w liście (nazwa produktu, 
cena, ilość i oznaczenie czy kupione). [7 pkt]
- (Opcjonalne, na 1 dodatkowy punkt) Utworzyć Content Providera umożliwiającego dostęp do 
znajdujących się w bazie danych informacji.

## Screenshots

<div align = "center">
</div>