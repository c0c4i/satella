# Wilderness weather station

Si vuole sviluppare il software di controllo di una stazione meteo presente nei monti della Lessinia.



## Use Cases



#### 1. Prima configurazione

1. Tira su un webserver
2. Prendere le coordinate GPS
3. Se presente il file `config.json` allora lo legge e crea i sensori a database
4. Invio al satellite della configurazione
5. Rimane in attesa del satellite
6. Ok, dopo l'arrivo del satellite



#### 2. Configurazione sensori

L'utente attraverso una pagina del portale potrà attivare e disattivare i sensori presenti e visualizzare le caratteristiche.

Può anche settare le soglie per cui i sensori debbano essere disattivati.



#### 3. Visualizzazione storico

Il tipo del rifugio entra in `192.168.10.0` e vede lo storico dei dati raccolti dalla stazione meteo.

Può visualizzare la dashboard con la visualizzazione settimanale/giornaliera di temperature/vento/pressione e altro in base ai sensori presenti.



#### 4. Visualizzazione real time

Il tipo del rifugio entra in `192.168.10.0` e premendo il pulsante "Real time", accederà ad una pagina in cui si vedono in tempo reale i dati raccolti dalla stazione.



#### 5. Invio dati a `http://www.meteotrento.it/`

La stazione meteo ogni 5 secondi riceve i dati del sensore.

La stazione meteo ogni minuto invia al satellite i dati raccolti e una loro prima analisi.



#### 6. Reset

Il tipo del rifugio entra in `192.168.10.0` e premendo il pulsante "Riavvia" nelle impostazioni riavvierà la stazione meteo reimpostando i sensori allo stato base.



#### 7. Emergenza

C'è un meccanismo che scatena un allarme da comunicare via satellite.

L'utente può chiedere aiuto dal portale.






