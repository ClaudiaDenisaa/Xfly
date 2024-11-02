# Xfly
Programare3, Proiect individual, Competente de antreprenoriat  (JAVA)



O aplicatie pentru o companie aeriana. Aplicatia din doua perspective, a administratorului si a unui pasager.
<br>
A)Administratorul:

1.Adaugare curse(ora plecare, sosire,destinatie,pret,tip avion, ruta, durata zbor)

2.Modificare cursa(întârzierea-mesaj către client,SMS,notificare in aplicație la b)3 )

3.Stergere cursa(dispare din lista de la client, apare mesaj daca s a făcut rezervare SMS si notificare ,obtiune de reprogramare pt client, restituire bani)


B)Clientul:

1.Informatii cursa:

-ora plecare sosire

-preț

-din x spre y


2.Cautare cursa după diferite chei

-oraș

-data

-ora plecare

-ora sosire


3.Rezervare zbor(in limita locurilor disponibile, alegere vizuala loc(clasa 1 clasa 2),plata(in funcție de clasa),selectare din meniu a mâncării si băuturii(in funcție de clasa),selectare in avans filme

+++ 4.Vizualizare in timp real a etapelor zborului(unde ești pe harta,intarzieri,)

5.Vizualizare locație bagaj după un mic localizator pus pe bagaj(se va percepe o taxa de 10 euro pt aceasta funcție)


Obiective:

Rezervare si informatii despre calatorie.



Arhitectura:

*IntelliJ IDEA
*Postman 
*Spring Boot 
*React




Functionalitati/Exemple utilizare:

Administrator:

-Adauga zbor

-Modifica zbor

-Anuleaza zbor


Client:

-rezervare zbor

--prin formular alegerea clasei,locului,meniului,bauturii,informatii bagaj

-modificare rezervare(ziua.data in functie de disponibilitatea din acea zi la acelasi zbor)

-anulare rezervare

