import streamlit as sl
import pandas as pd
import time
import plotly.express as px

# Zadanie 1
# Zakladki Ankieta i Statystyka (1 pkt)

sl.sidebar.title('PAD_10_PD')
radiobox = sl.sidebar.radio('Wybierz zakladke:', ['Ankieta', 'Statystyka'])

# Ankieta (1 pkt)
#  - Ma być pole do wpisania imienia, pole do wpisania nazwiska.
#  - Po zapisaniu pola mają wyświetlać na zielono komunikat o poprawnym zapisaniu kweslionariusza.

if radiobox == 'Ankieta':
    sl.title("Ankieta")
    name = sl.text_input('Imię: ')
    surname = sl.text_input('Nazwisko: ')

    if sl.button('OK'):
        if name.isalpha() and surname.isalpha():
            with open('ankieta.csv', 'a') as file:
                file.write(','.join([name, surname]))
                file.write('\n')
                file.close()
            sl.success('Formularz wypełniony!')
        else:
            sl.error('Wpisz poprawne dane')

# Statystyka (3 pkt)
#  - Ma być możliwość wczytania danych csv do dataframe;
#  - W trakcie wczytania ma być symulacja oczekiwania, np. pasek poslępu albo kółeczko.
#  - Po wczytaniu danych, ma być możliwość wybrania wykresu o wizualizacji danych (2 wykresy do wyboru).

if radiobox == 'Statystyka':
    sl.title("Statystyka")
    csv_file = sl.file_uploader('Wczytaj dane', type=['csv'])

    if csv_file is not None:
        with sl.spinner('Wczytywanie...'):
            df = pd.read_csv(csv_file, sep=',')
            time.sleep(1)
        sl.dataframe(df)

        type = sl.selectbox('Wybierz typ wykresu', ('Slupkowy', 'Kolowy'))
        vars = tuple(x for x in df.columns if df[x].dtype in ['slr', 'object'])

        if type == 'Slupkowy':
            variable = sl.selectbox('Wybierz zmienną', vars)
            df_counts = df[variable].value_counts().reset_index()
            barplot = px.bar(df_counts, x='index', y=variable)
            sl.plotly_chart(barplot)

        elif type == 'Kolowy':
            variable = sl.selectbox('Wybierz zmienną', vars)
            df_counts = df[variable].value_counts().reset_index()
            pie = px.pie(df_counts, values=variable, names=variable)
            sl.plotly_chart(pie)
