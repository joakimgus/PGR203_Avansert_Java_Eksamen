![Java CI with Maven](https://github.com/kristiania/pgr203eksamen-tinaeile/workflows/Java%20CI%20with%20Maven/badge.svg)
***
## PGR203 - Eksamen : Avansert Java

#### Beskrivelse av prosjektets funksjonalitet :
Vi startet med å opprette repository fra innlevering 3 da vi allerede hadde implementert databaseaksess, og funksjon for å kunne legge til members og tasks.

##### Bygg og test executable jar-fil :
1. Kjør mvn package i terminal. (Eventuelt Maven -> lifecycle -> package, for å bygge en jar fil.)
2. Når du har bygget jar-filen trenger du å legge pgr203.properties fila sammen med den for at programmet skal kunne kjøres. Denne fila må inneholde brukernavn, passord og din database URL.
3. Kjør java -jar target/http-client-1.0-SNAPSHOT.jar i terminal for å starte programmet.

##### Funksjonalitet :
Etter programmet har startet via jar-filen som beskrevet over, vil løsningen kunne aksesseres i browser via *localhost:8080/index.html, localhost:8080/, eller localhost:8080*.

**_NB!_** _Om jar filen kjøres i Windows vil ikke denne decodes med UTF-8, da windows operativsystemet ikke har UTF-8 som default. Det kan virke som UTF-8 må spesifiseres ved execution av .jar til tross for at vi implementerte UTF-8 decoding. Dette vil kun være et problem om det kjøres via .jar, mens alt ser ut til å fungere utmerket når det kjøres via f.eks. intelliJ._

Inne på siden vil man få opp en listemeny bestående av 4 hoveddeler (_members, tasks, status, og assign_):

##### **Members**:
- List members : *Listevisning av alle eksisterende members i databasen.*
- List members with tasks : *Listevisning av alle eksisterende members som er tildelt en eller flere tasks, og hvilken status hver task er tildelt. Denne visningen er sortert etter Members.*
- Add new member : *Side for å kunne legge til flere members i databasen med både navn og e-post.*

##### **Tasks**:
- List members : *Listevisning av alle eksisterende tasks i databasen.*
- List tasks with members: *Listevisning av alle eksisterende tasks tildelt members og status. Denne visningen er sortert etter Tasks.*
- Add new task : *Side for å kunne legge til flere tasks i databasen med både tittel og beskrivelse.*

##### **Status**:
- List status : *Listevisning av alle eksisterende statuses i databasen.*
- List statuses with tasks : *Listevisning sortert etter status, med alle tasks og members under hver status.*
- Add new status : *Side for å legge til ny status i databasen.*

##### **Assign**:
- Assign task to member : *Side for å tildele en task/member til member/task. Dette gjøres ved å legge member id og task id som en foreign key i MemberTask tabellen ved bruk av SQLinjection. På grunn av at vi valgte å opprette en helt ny tabell for å samle member og task id kan vi da sortere både etter member og task*
- Assign status to task : *Side for å tildele status/task til task/status, eller endre status på en task. Dette gjøres ved å legge status id som en foreign key i task tabellen ved bruk av SQLinjection.*

Alle POST requests omdirigerer deg tilbake til Menysiden, og alle sidene har en link i bunnen som også tar deg med tilbake til menyen.

**Ekstra funksjonalitet**:
- [x] God bruk av DAO-pattern
- [x] God bruk av Controller-pattern
- [x] Håndtering av request target "/"
- [x] Avansert datamodell (mer enn 3 tabeller)
- [x] Korrekt håndtering av norske tegn i HTTP
- [x] Link til video med demonstrasjon av parprogrammering
- [x] UML diagram som dokumenterer datamodell og/eller arkitektur (presentert i README.md)
- [x] Lar samme oppgave tildeles flere medlemmer, og liste oppgaver etter member eller member etter oppgave.
***

#### Beskrivelse av design

##### UML http :
![ClassDiagram](https://github.com/kristiania/pgr203eksamen-tinaeile/blob/master/docs/UMLHttp.png?raw=true)

##### UML Dao :

![ClassDiagram](https://github.com/kristiania/pgr203eksamen-tinaeile/blob/master/docs/UMLDao.png?raw=true)

##### UML Database :
![DatabaseDiagram](https://github.com/kristiania/pgr203eksamen-tinaeile/blob/master/docs/databaseDiagram.png?raw=true)
***

#### Egenevaluering
Vi føler begge vi har lært mye av prinsippene rundt HTTP og JBDC ved å jobbe med denne oppgaven. Vi har også lært mye om feilsøking i kode, da vi opp til flere ganger støtte på utfordringer.

Emnet introduserte oss tidlig for parprogrammering, noe vi har benyttet oss flittig av gjennom alle arbeidskravene og eksamen. Vi føler begge vi har fått godt utbytte av å programmere sammen, og bruk av gitHub og discord har vært vital for gjennomførelsen av prosjektet. Vi føler vi har utnyttet den tiden vi fikk til dispensasjon godt.

Link til parprogrammering:
*https://youtu.be/ly9azH3phkE*

Da vi først begynte å jobbe med prosjektet var vi begge litt rådeløse, men dette hentet seg raskt opp. Vi føler vi har fått gjort mye mer enn vi i utgangspunktet trodde vi skulle klare, og er meget fornøyd med sluttresultatet.

Da vi utviklet løsningen vår for å kunne tildele members tasks og omvendt, for så å filtrere etter både task og member, bestemte vi oss for å samle disse verdiene i en egen tabell, noe som er en litt annen løsning en den vi ble introdusert for i bonusforelesningen. Vi kom opp med løsningen før implementasjon av filtrering ble vist i bonusforelesning, så vi bestemte oss for å gå for den løsningen vi hadde laget helt selv, da det er denne vi er mest stolte av.

Vi er også fornøyde med konsistent bruk av begreper, navngivning av klasser, løsningen vår av å splitte opp controllerne etter POST og GET da Map.of ikke lot oss ha mer enn 10 kontrollere.

Vi har implementert URLDecoding i koden vår, men som nevnt over vil man kunne støte på et problem om man kjører jar-filen på en windowsmaskin uten å spesifisere UTF-8 som default.

**SJEKKLISTE**
- [ ] Oppgaven skal leveres i Wiseflow som en ZIP-fil og link til Github Classroom.

**Prosjektet må følge god programmeringsskikk. Dette er viktige krav og feil på et enkelt punkt kan gi en hel karakter i trekk.**
- [ ] Koden skal ha god testdekning.

**Vedlegg: Sjekkliste for innlevering**
- [ ] Dere har lastet opp en ZIP-fil med navn basert på navnet på deres Github repository
- [ ] Koden inneholder et godt sett med tester
