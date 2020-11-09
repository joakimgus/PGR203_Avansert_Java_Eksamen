![Java CI with Maven](https://github.com/kristiania/pgr203eksamen-tinaeile/workflows/Java%20CI%20with%20Maven/badge.svg)
## PGR203 - Eksamen : Avansert Java

#### Beskrivelse av prosjektets funksjonalitet :
Vi startet med å opprette repository fra innlevering 3 da vi allerede hadde implementert databaseaksess, og funksjon for å kunne legge til members og tasks.

###### Bygg og test executable jar-fil :
1. Kjør mvn package i terminal. (Eventuelt Maven -> lifecycle -> package, for å bygge en jar fil.)
2. Kjør java -jar target/http-client-1.0-SNAPSHOT.jar i terminal for å starte programmet.

###### Funksjonalitet :
1. Etter programmet har startet via jar-filen som beskrevet over, vil løsningen kunne aksesseres i browser via localhost:8080/index.html.

2. Inne på siden vil man få opp en listemeny bestående av 4 hoveddeler (_members, tasks, status, og assign_):

**Members**:
- List members : *Listevisning av alle eksisterende members i databasen.*
- List members with tasks : *Listevisning av alle eksisterende members som er tildelt en eller flere tasks, og hvilken status hver task er tildelt. Denne visningen er sortert etter Members.*
- Add new member : *Side for å kunne legge til flere members i databasen med både navn og e-post.*

**Tasks**:
- List members : *Listevisning av alle eksisterende tasks i databasen.*
- List tasks with members: *Listevisning av alle eksisterende tasks tildelt members og status. Denne visningen er sortert etter Tasks.*
- Add new task : *Side for å kunne legge til flere tasks i databasen med både tittel og beskrivelse.*

**Status**:
- List status : *Listevisning av alle eksisterende statuses i databasen.*
- List statuses with tasks : *Listevisning sortert etter status, med alle tasks og members under hver status.*
- Add new status : *Side for å legge til ny status i databasen.*

**Assign**:
- Assign task to member : *Side for å tildele en task/member til member/task.*
- Assign status to task : *Side for å tildele status/task til task/status.*

- [ ] README-fil må dokumentere hvordan man bygger, konfigurerer og kjører løsningen.
- [ ] README-fil må dokumentere designet på løsningen.
- [ ] README-fil må beskrive erfaringene med arbeidet og løsningen.

- [x] Opprett prosjektmedlem med navn og email -> Liste prosjektmedlemmer.
- [x] Opprett ny prosjektoppgave med navn og status Tildel oppgave til prosjektmedlemmer.
- [x] Liste opp prosjektoppgaver, inkludert status og tildelte prosjektmedlemmer.
- [x] Endre oppgavestatus.
- [x] Filtrere oppgaver per prosjektmedlem og status.
- [x] Programmet kan liste prosjektdeltagere fra databasen
- [x] Programmet lar bruker opprette nye prosjektdeltagere i databasen
- [x] Programmet kan opprette og liste prosjektoppgaver fra databasen
- [x] Programmet lar bruker tildele prosjektdeltagere til oppgaver
- [x] Flere prosjektdeltagere kan tildeles til samme oppgave
- [x] Programmet lar bruker endre status på en oppgave
***

###### Ekstra funksjonalitet :
***
- [x] Håndtering av request target "/"
- [x] Avansert datamodell (mer enn 3 tabeller)
- [x] UML diagram som dokumenterer datamodell og/eller arkitektur (presentert i README.md)
- [ ] Rammeverk rundt Http-håndtering (en god HttpMessage class med HttpRequest og HttpResponse subtyper) som gjenspeiler RFC7230
- [x] God bruk av DAO-pattern
- [x] God bruk av Controller-pattern
- [x] Korrekt håndtering av norske tegn i HTTP
- [ ] Link til video med god demonstrasjon av ping-pong programmering
- [ ] Automatisk rapportering av testdekning i Github Actions
- [ ] Annet. ???- [x] Lar samme oppgave tildeles flere medlemmer, og liste oppgave per member.
***

###### Java UML Class Diagram :
![ClassDiagram](https://github.com/kristiania/pgr203eksamen-tinaeile/blob/master/docs/javaClassDiagram.png?raw=true)
***

###### Database UML Diagram :
![DatabaseDiagram](https://github.com/kristiania/pgr203eksamen-tinaeile/blob/master/docs/databaseDiagram.png?raw=true)
***

**SJEKKLISTE**
- [ ] Oppgaven skal leveres i Wiseflow som en ZIP-fil og link til Github Classroom.
- [ ] README.md på Github skal linke til Github Actions som skal kjøre enhetstester uten feil. README-filen skal også inneholde link til gitt tilbakemelding til annet team, et UML-diagram samt beskrivelse av hva kandiditene ønskes skal vurderes i evalueringen av innleveringen.

**Prosjektet må følge god programmeringsskikk. Dette er viktige krav og feil på et enkelt punkt kan gi en hel karakter i trekk.**
- [x] Koden skal følge standard Java-konvensjoner med hensyn til store og små bokstaver og indentering Koden skal utvikles på Git, med Maven og kjøre tester på Github Actions.
- [ ] Koden skal ha god testdekning.
- [x] Det skal ikke forekomme SQL Injection feil.
- [x] Databasepassord skal være konfigurert i en fil som ikke sjekkes inn i git.
- [x] Maven skal bygge en executable jar som inneholder HTML-koden og som referer til pgr203.properties i current working directory.
- [x] God abstraksjon for eksempel DAO eller Controller-klasser gir bonuspoeng. Konsistent bruk av begreper og kode vektlegges. Brukervennlighet er ikke et vurderingskriterie for karakteren.

**Vedlegg: Sjekkliste for innlevering**
- [ ] Dere har lastet opp en ZIP-fil med navn basert på navnet på deres Github repository
- [x] Koden er sjekket inn på github.com/kristiania-repository
- [x] Dere har committed kode med begge prosjektdeltagernes GitHub konto (alternativt: README beskriver arbeidsform)
- [ ] mvn package bygger en executable jar-fil
- [ ] Koden inneholder et godt sett med tester
- [ ] java -jar target/...jar (etter mvn package ) lar bruker legge til og liste ut data fra databasen via webgrensesnitt
- [x] Programmet leser dataSource.url , dataSource.username og dataSource.password fra pgr203.properties for å connecte til databasen
- [x] Programmet bruker Flywaydb for å sette opp databaseskjema
- [x] Server skriver nyttige loggmeldinger, inkludert informasjon om hvilken URL den kjører på ved oppstart

**README.md**
- [x] README.md inneholder en korrekt link til Github Actions
- [ ] README.md beskriver prosjektets funksjonalitet, hvordan man bygger det og hvordan man kjører det
- [ ] README.md beskriver eventuell ekstra leveranse utover minimum
- [x] README.md inneholder et diagram som viser datamodellen
- [x] Dere har gitt minst 2 positive og 2 korrektive GitHub issues til en annen gruppe og inkluderer link til disse fra README.md
