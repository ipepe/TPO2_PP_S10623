# TPO2_PP_S10623
Java New IO chat that is based on SocketChannels and Selector

# Tresc zadania:
Napisać czat z użyciem kanałów gniazd i selektorów (NIO).

Aplikacje winna składać się z:

    serwera (klasa Server),  który rejestruje (loguje) i derejestruje (wylogowuje) klientów, odbiera wiadomości i rozsyła do zalogowanych klientów,
     programu klienckiego (klasa Client) z prostym GUI, które zapewnia:
        logowanie do czatu,
        posyłanie wiadomości tekstowych (mała JTextArea)
        oraz widok wiadomości czatu (lista lub tabela lub duża JTextArea).
    klasy Main, która uruchamia Server i dwóch przykładowych klientów (z metody main).

Uwaga: użycie NIO (multipleksowania kanałów za pomocą selektorów) jest obowiązkowe. Bez tego uzyskujemy 0 punktów.
Zestaw klas aplikacji jest obowiązkowy (aplikacja musi mieć co najmniej w/w trzy klasy).
W każdej z klas winna znajdować się metoda main (w klasie Main jej rola została opisana, w klasach Server i Client służyć będzie do izolowanego uruchamiania serwera i klientów).
