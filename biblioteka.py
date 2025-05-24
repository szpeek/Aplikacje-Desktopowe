import csv


class Ksiazka:
    def __init__(self, tytul, autor, rok, dostepna=True):
        self.tytul = tytul
        self.autor = autor
        self.rok = rok
        self.dostepna = dostepna

    def __str__(self):
        status = "Dostępna" if self.dostepna else "Wypożyczona"
        return f"{self.tytul} | {self.autor} | {self.rok} | {status}"


biblioteka_lista = {}
plik_csv = "biblioteka.csv"


def dodawanie_ksiazki():
    try:
        tytul = input("Podaj tytuł książki do dodania: ")
        autor = input("Dodaj autora książki: ")
        rok = input("Podaj rok wydania książki: ")
        biblioteka_lista[tytul] = Ksiazka(tytul, autor, rok)
        print(f"Dodano książkę: {biblioteka_lista[tytul]}")
    except Exception as e:
        print("Błąd podczas dodawania książki:", e)


def wypozycz_ksiazke():
    tytul = input("Podaj tytuł książki do wypożyczenia: ")
    ksiazka = biblioteka_lista.get(tytul)
    if ksiazka and ksiazka.dostepna:
        ksiazka.dostepna = False
        print(f"Wypożyczono książkę: {ksiazka}")
    elif ksiazka:
        print("Ta książka jest już wypożyczona.")
    else:
        print("Nie znaleziono takiej książki.")


def zwroc_ksiazke():
    tytul = input("Podaj tytuł książki do zwrotu: ")
    ksiazka = biblioteka_lista.get(tytul)
    if ksiazka and not ksiazka.dostepna:
        ksiazka.dostepna = True
        print(f"Zwrócono książkę: {ksiazka}")
    elif ksiazka:
        print("Ta książka nie była wypożyczona.")
    else:
        print("Nie znaleziono takiej książki.")


def lista_ksiazek():
    print("-" * 40)
    if not biblioteka_lista:
        print("Brak książek w bibliotece.")
        return
    
    dostepne = list(filter(lambda x: x.dostepna, biblioteka_lista.values()))
    if not dostepne:
        print("Brak dostępnych książek.")
    else:
        for ksiazka in dostepne:
            print(ksiazka)
    print("-" * 40)


def zapisz_ksiazke():
    try:
        with open(plik_csv, mode="w", newline="", encoding="utf-8") as plik:
            pisarz = csv.writer(plik)
            for ksiazka in biblioteka_lista.values():
                pisarz.writerow([ksiazka.tytul, ksiazka.autor, ksiazka.rok, ksiazka.dostepna])
        print("Zapisano książki do pliku.")
    except Exception as e:
        print("Błąd podczas zapisu:", e)


def wczytaj_ksiazki():
    try:
        biblioteka_lista.clear()
        with open(plik_csv, mode="r", newline="", encoding="utf-8") as plik:
            czytelnik = csv.reader(plik)
            for wiersz in czytelnik:
                if len(wiersz) == 4:
                    tytul, autor, rok, dostepna = wiersz
                    biblioteka_lista[tytul] = Ksiazka(tytul, autor, rok, dostepna == "True")
        print("Wczytano książki z pliku.")
    except FileNotFoundError:
        print("Plik nie istnieje. Zacznij od dodania książki.")
    except Exception as e:
        print("Błąd podczas wczytywania:", e)



while True:
    wybor = input("Wybierz :\n"
                  "1. Dodaj książkę\n"
                  "2. Wypożycz książkę\n"
                  "3. Zwróć książkę\n"
                  "4. Pokaż dostępne książki\n"
                  "5. Zapisz do pliku\n"
                  "6. Wczytaj z pliku\n"
                  "0. Wyjdź\n"
                  "")

    match wybor:
        case "1":
            dodawanie_ksiazki()
        case "2":
            wypozycz_ksiazke()
        case "3":
            zwroc_ksiazke()
        case "4":
            lista_ksiazek()
        case "5":
            zapisz_ksiazke()
        case "6":
            wczytaj_ksiazki()
        case "0":
            print("Wyjście z programu.")
            break
        case _:
            print("Nieprawidłowy wybór.")
