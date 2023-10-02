import streamlit as st
from streamlit_option_menu import option_menu
import pandas as pd
import time
import plotly.express as px

# -- Inicializacja --
st.sidebar.title('Projekt z Programowania dla Analityki Danych')
with st.sidebar:
    selected = option_menu("Menu", ["Strona główna", 'Dane', 'Wykresy'], icons=[
                           'house', 'book', 'calculator'], default_index=0)
    df = pd.read_csv('PAD_project/Leagues_final.csv', sep=";")

# -- Ramka danych --
if selected == "Dane":

    st.title("Informacje o danych")
    st.write(
        "Jest to spis cen **Exalted Orb** z wszystkich notowanych lig w Path of Exile")
    st.dataframe(df)
    st.write("Dana ramka danych zostala webscrap'owana ze strony:")
    st.write(">https://poe-antiquary.xyz/Archnemesis/Currency/Exalted%20Orb/2")
    st.write("Zostala rowniez rozszerzona o dodatkowe dane w ramach projektu.")
    st.markdown("""---""")
    st.write("Dana ramka danych sklada sie z:")
    st.write("- **Hour** - Godzina aktualizacji ceny **Exalted Orb'a**")
    st.write(
        "- **Price** - Cena **Exalted Orb'a** wymnierzona mniejsza waluta (**Chaos Orb'em**)")
    st.write(
        "- **Day** - Dzien aktualizacji ceny **Exalted Orb'a** (Wyliczona na podstawie godziny)")
    st.write("- **League** - Nazwa ligi, z ktorej zostala pobrana cena")
    st.write("""
    - **League Type** - Typ ligi, gdzie:
        - **Standard** - domyslny tryb rozgrywki
        - **Hardcore** - tryb rozgrywki dla szukajacych wyzwania
        """)
    st.write("""
    - **League Length** - Dlugosc danej ligi, gdzie:
        - **Long** - oznacza zazwyczaj zwykla 3 miesieczna lige
        - **Short** - przeznaczona dla lig co trwaja od 1 do 3 tygodni
        """)
    st.write(
        "- **Week** - Tydzien aktualizacji ceny **Exalted Orb'a** (Wyliczona na podstawie dnia)")


# -- Wykresy --
elif selected == "Wykresy":
    st.title("Wykresy")
    st.write("Jako ze caly projekt opiera sie na cenie **Exalted Orba** duza czesc wykresow bedzie miala glowny czynnik jako Cene.")
    graph = st.selectbox("Wybierz rodzaj wykresu",
                         ["Wybierz...", "Cena do Typu i Długosci Ligi", "Cena do Nazwy Ligi", "Cena do Tygodni Lig",
                          "Srednia cena Ligi"])
    if graph == "Cena do Typu i Długosci Ligi":
        st.write("### Histogram Ceny do Długosci Ligi")
        st.write(
            "Zobaczmy czy istnieje związek między cena **Exalted Orb'a**, a typem i długoscią ligi ")
        fig = px.histogram(
            df, "Price", facet_col="League Type", color="League Length")
        fig.update_yaxes(matches=None, showticklabels=True)
        st.plotly_chart(fig)
        st.write(
            "Mozna zobaczyc, ze jest duza roznica cenowa miedzy liga typu **Standard** a liga typu **Hardcore**.")
        st.write("""
        - Mozliwe powody:
            - Na lidze **Hardcore** jest mniejsza spolecznosc niz na lidze typu **Standard**" 
            - Mniejsze uzycie **Exalted Orb** do tworzenia rzeczy na lidze typu **Hardcore**, co powoduje mniejsza chec kupna jej
            """)
        st.write("Rowniez mozna zobaczyc, ze krotkie ligi zazwyczaj maja o wiele mniejsza cene **Exalted Orb'a** w porownaniu do ich dluzszych odpowiednikow")

    if graph == "Cena do Nazwy Ligi":
        st.write("### Cena do Nazwy Ligi")
        st.write(
            "Zobaczmy czy istnieje związek między cena **Exalted Orb'a**, a nazwa ligi i jej typem ")
        fig = px.histogram(df, "Price", facet_col="League",
                           color="League Type", facet_col_wrap=6)
        fig.update_yaxes(matches=None, showticklabels=True)
        st.plotly_chart(fig)
        st.write(
            "Mozna zobaczyc, ze jest duza roznica cenowa miedzy liga typu **Standard** a liga typu **Hardcore**.")
        st.write("""
        - Mozliwe powody:
            - Na lidze **Hardcore** jest mniejsza spolecznosc niz na lidze typu **Standard**" 
            - Mniejsze uzycie **Exalted Orb** do tworzenia rzeczy na lidze typu **Hardcore**, co powoduje mniejsza chec kupna jej
            """)
        st.write("Rowniez mozna zobaczyc, ze cena **Exalted Orb'a** duzo rozni sie od danej ligi, co moze spowodowac w uczeniu maszynowego mniej precyzji modelu regresyjnego")

    if graph == "Cena do Tygodni Lig":
        st.write("### Cena do Tygodnia Lig")
        st.write(
            "Zobaczmy czy istnieje związek między cena **Exalted Orb'a**, a tygodniami lig")
        st.write(
            "Ramka czasu zostala zmniejszona do max 15 tygodni, jako, ze tylko jedna liga miala wiecej niz 16 tygodni")
        df_week = df[df["Week"] < 16]
        fig = px.histogram(df_week, "Price", facet_col="Week",
                           facet_row="League Type", color="League Length")
        fig.update_yaxes(matches=None, showticklabels=True)
        st.plotly_chart(fig)
        st.write(
            "Mozna zobaczyc, ze cena na lidze typu 'Standard' rosnie z tygodnia na tydzien. Natomiast na lidze typu 'Hardocore' po pierwszych tygodniach cena stabilizuje sie")

    if graph == "Srednia cena Ligi":
        st.write("### Srednia cena Ligi")

        st.write(
            "Zobaczmy jaka jest roznica cenowa **Exalted Orb'a** dla poszczegolnych typow i dlugosci Lig")

        # -- Long Standard --
        st.write("#### Srednia cena dla 3-miesiecznej Ligi Standard")
        df_standard = df[df["League Type"] == "Standard"]
        df_long = df_standard[df_standard["League Length"] == "Long"]
        df_avg = df_long.groupby(["League"], as_index=False)["Price"].mean()
        fig = px.pie(df_avg, values='Price',
                     names='League', width=600, height=600)
        fig.update_yaxes(matches=None, showticklabels=True)
        st.plotly_chart(fig)

        # -- Long Hardcore --
        st.write("#### Srednia cena dla 3-miesiecznej Ligi Hardcore")
        df_hardcore = df[df["League Type"] == "Hardcore"]
        df_long = df_hardcore[df_hardcore["League Length"] == "Long"]
        df_avg = df_long.groupby(["League"], as_index=False)["Price"].mean()
        fig = px.pie(df_avg, values='Price',
                     names='League', width=600, height=600)
        fig.update_yaxes(matches=None, showticklabels=True)
        st.plotly_chart(fig)

        # -- Short Standard --
        st.write("#### Srednia cena dla 2-tygodniowej Ligi Standard")
        df_standard = df[df["League Type"] == "Standard"]
        df_short = df_standard[df_standard["League Length"] == "Short"]
        df_avg = df_short.groupby(["League"], as_index=False)["Price"].mean()
        fig = px.pie(df_avg, values='Price',
                     names='League', width=600, height=600)
        fig.update_yaxes(matches=None, showticklabels=True)
        st.plotly_chart(fig)

        # -- Short Hardcore --
        st.write("#### Srednia cena dla 2-tygodniowej Ligi Hardcore")
        df_hardcore = df[df["League Type"] == "Hardcore"]
        df_short = df_hardcore[df_hardcore["League Length"] == "Short"]
        df_avg = df_short.groupby(["League"], as_index=False)["Price"].mean()
        fig = px.pie(df_avg, values='Price',
                     names='League', width=600, height=600)
        fig.update_yaxes(matches=None, showticklabels=True)
        st.plotly_chart(fig)

        st.write("Patrzac na te wykresy kolowe, mozna dosc do wniosku, ze im mniejsza srednia **Exalted Orb'a** jest bardzej lukratywna niz inne ligi dla ligi Standard")
        st.write(
            "Natomiast dla ligi Hardcore im mniejsza srednia ceny tym mechanika ligi latwiejsza.")

        # -- Strona glowna --
else:
    st.title("Strona główna")

    st.write("""
    - **Temat Projektu**:
        - **Analiza cen Exalted Orb na przestrzeni wszystkich lig w grze Path Of Exile**
    """)
    st.markdown("""---""")
    st.write("""
    - **Cel projektu**:
        - Celem projektu jest sprawdzenie czy na podstawie informacji o cenach **Exalted Orb** z wszystkich lig można przewidziec cene **Exalted Orb'a** nastepnej ligi tylko posiadajac dane z pierwszego tygodnia nowej ligi.
    """)
    st.markdown("""---""")
    st.write("""
    - **Uczenie maszynowe**:
        - Do uczenia maszynowego zostal wybrany **model regresyjny**, ktory pozwoli przewidziec zalozenie z pierwszego punktu celu projektu.
        - Uzyte zostaly rozne **modele regresyjne** z biblioteki sklearn (od zwyklej regresji liniowej do tworzenia drzewka regresyjnego lub uzycia SVR), aby miec wglad na rozne zmienne.
        - Srednia wynikow **R2** oscyluje w okolicach **20%** po przetestowaniu ich na danych testowych:
            - Ma to sens, gdyz nawet jesli po wyselekcjonowaniu danych, roztrzal cenowy jest zbyt duzy.
            - Tez trzeba patrzec na to, ze uzywam wielu modeli regresyjnych, co rowniez zaniza srednia R2.
    """)
    st.markdown("""---""")
    st.write("""
    - **Słownik**:
        - **Path of Exile** - Gra ARPG z duzym naciskiem na elementy wieloosobowe, gdzie gracze wymieniaja sie roznymi growymi walutami.
        - **Exalted Orb** - Głowna waluta w grze do wymiany z innymi graczami.
        - **Chaos Orb** - Podobnie do **Exalted Orb'a** tylko jest mniej warta, uzywana do wymiany na **Exalted Orb'a**
        - **League** - Jest to nowy sezon, gdzie wprowadzane sa nowe rzeczy do gry.
    """)
    st.markdown("""---""")
    st.write("### Analize danych jak i wykresy mozna znalezc w lewym panelu.")
