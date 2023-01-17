# Wilderness weather station

Si vuole sviluppare il software di controllo di una stazione meteo presente nei monti della Lessinia.



## Scenario



#### Scenario 1.1 - Setup iniziale

> Punto 3 del punto 1 delle user stories

| Azione                 | Descrizione                                                  |
| ---------------------- | ------------------------------------------------------------ |
| Assunzione iniziale    | La stazione è appena stata avviata                           |
| Funzionamento normale  | Il sistema cerca nella cartella `config/` un file `config.json`, lo legge e istanzia i sensori specificati tramite un seriale come identificativo.<br />Una volta istanziati vengono collegati al gestore dei sensori della stazione. |
| Cosa può andare storto | Se il file di configurazione non è presente allora la stazione si avvia normalmente senza sensori iniziali.<br />Se nella configurazione non è possibile riconoscere un tipo di sensore, non lo istanzierà nei sensori e inserirà un messaggio di errore nei log. |



#### Scenario 2.0 - Attivazione e disattivazione sensori

> Punto 2 delle user stories

| Azione                 | Descrizione                                                  |
| ---------------------- | ------------------------------------------------------------ |
| Assunzione iniziale    | La stazione è in funzione                                    |
| Funzionamento normale  | L'utente accede alla dashboard e clicca nella sezione `Sensori`.<br />L'utente vede la lista di sensori e clicca sul bottone per attivarlo/disattivarlo.<br />Una volta attivato/disarttivato verrà mostrato un messaggio di successo. |
| Cosa può andare storto | Il sensore può non rispondere al nostro comando, viene qundi visualizzato un messaggio di errore. |



#### Scenario 2.0 - Aggiunta di un nuovo sensore

> Punto 2 delle user stories, manca nelle user stories

| Azione                 | Descrizione                                                  |
| ---------------------- | ------------------------------------------------------------ |
| Assunzione iniziale    | La stazione è in funzione                                    |
| Funzionamento normale  | L'utente accede alla dashboard e clicca nella sezione `Sensori`.<br />L'utente clicca sul bottone di aggiunta di un sensore.<br />L'utente seleziona il modello di sensore e decide un nome.<br />Una volta aggiunto verrà mostrato un messaggio di successo e sarà il sensore sarà visibile nella lista completa di sensori. |
| Cosa può andare storto | Il sistema non riesce a connettere il sensore con le informazioni specificate dall'utente.<br />Viene qundi visualizzato un messaggio di errore ma l'utente ha la possibilità di cambiare le informazioni e riprovare o annullare l'aggiunta. |



#### Scenario 3.0 - Visualizzazione storico

> Punto 3 delle user stories

| Azione                 | Descrizione                                                  |
| ---------------------- | ------------------------------------------------------------ |
| Assunzione iniziale    | La stazione è in funzione                                    |
| Funzionamento normale  | L'utente accede alla dashboard e clicca nella sezione `Storico`.<br />L'utente vede il grafico dei dati giornalieri raccolti di tutti i sensori.<br />L'utente può cambiare l'intervallo di tempo dei grafici. |
| Cosa può andare storto | Non ci sono dati nel tipo di visualizzazione selezionata, viene quindi mostrato un avviso di mancanza dei dati.<br />Non ci sono dati per un determinato sensore, viene quindi visualizzato il grafico con i dati presenti e un messaggio che indica che non è aggiornato. |



#### Scenario 4.0 - Visualizzazione real time

> Punto 4 delle user stories

| Azione                 | Descrizione                                                  |
| ---------------------- | ------------------------------------------------------------ |
| Assunzione iniziale    | La stazione è in funzione                                    |
| Funzionamento normale  | L'utente accede alla dashboard.<br />L'utente vede per ogni sensore attivo, l'ultima misurazione eseguita entro l'ultimo minuto. |
| Cosa può andare storto | Non ci sono dati entro l'ultimo minuto, viene quindi mostrato un indicatore che indica la mancanza dei dati. |



#### Scenario 5.0 - Visualizzazione real time

> Punto 5 delle user stories

| Azione                 | Descrizione                                                  |
| ---------------------- | ------------------------------------------------------------ |
| Assunzione iniziale    | La stazione è in funzione                                    |
| Funzionamento normale  | Il sistema ogni minuto invia attraverso una richiesta `POST` a `http://www.meteotrento.it/` i dati raccolti. |
| Cosa può andare storto | La richiesta fallisce, il prossimo minuto riprova inviando gli ultimi `n` blocchi di dati non ancora inviati.<br />Nel caso non abbia la connessione per più di 30 minuti, il sistema mostrerà un messaggio di errore all'utente quando si collegherà alla dashboard. |



#### Scenario 7.0 - Emergenza

> Punto 7 delle user stories

| Azione                 | Descrizione                                                  |
| ---------------------- | ------------------------------------------------------------ |
| Assunzione iniziale    | La stazione è in funzione                                    |
| Funzionamento normale  | Il sistema rileva che i dati hanno superato una determinata soglia, il sistema invia tramite una richiesta `POST` a `http://www.poliziaforestale.it/` segnalando il tipo di emergenza legato al superamento di determinate soglie dei sensori.<br />Il sistema mostrerà un messaggio di errore all'utente appena si collegherà alla dashboard. |
| Cosa può andare storto | La richiesta fallisce, il sistema continua a riprovare la richiesta finché non va a buon fine.<br /> |

