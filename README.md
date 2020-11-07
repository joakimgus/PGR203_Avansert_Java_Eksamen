### JAVA EKSAMEN

![Java CI with Maven](https://github.com/kristiania/pgr203eksamen-tinaeile/workflows/Java%20CI%20with%20Maven/badge.svg)

![ClassDiagram](https://github.com/kristiania/pgr203eksamen-tinaeile/blob/master/docs/javaClassDiagram.png?raw=true)

**SJEKKLISTE**
- [ ] Oppgaven skal leveres i Wiseflow som en ZIP-fil og link til Github Classroom.
- [ ] Innleveringen kan deles med en annen gruppe for gjensidig tilbakemelding. Tilbakemelding skal gis i form av Github issues.
- [ ] README.md på Github skal linke til Github Actions som skal kjøre enhetstester uten feil. README-filen skal også inneholde link til gitt tilbakemelding til annet team, et UML-diagram samt beskrivelse av hva kandiditene ønskes skal vurderes i evalueringen av innleveringen.

**Funksjonaliteten skal inkludere:**
- [x] Opprett prosjektmedlem med navn og email -> Liste prosjektmedlemmer.
- [x] Opprett ny prosjektoppgave med navn og status Tildel oppgave til prosjektmedlemmer.
- [x] Liste opp prosjektoppgaver, inkludert status og tildelte prosjektmedlemmer.
- [x] Endre oppgavestatus.
- [ ] Filtrere oppgaver per prosjektmedlem og status.

**Prosjektet må følge god programmeringsskikk. Dette er viktige krav og feil på et enkelt punkt kan gi en hel karakter i trekk.**
- [ ] Koden skal følge standard Java-konvensjoner med hensyn til store og små bokstaver og indentering Koden skal utvikles på Git, med Maven og kjøre tester på Github Actions.
- [ ] Koden skal ha god testdekning.
- [x] Det skal ikke forekomme SQL Injection feil.
- [x] Databasepassord skal være konfigurert i en fil som ikke sjekkes inn i git.
- [ ] README-fil må dokumentere hvordan man bygger, konfigurerer og kjører løsningen.
- [ ] README-fil må dokumentere designet på løsningen.
- [ ] README-fil må beskrive erfaringene med arbeidet og løsningen.
- [x] Maven skal bygge en executable jar som inneholder HTML-koden og som referer til pgr203.properties i current working directory.
- [x] God abstraksjon for eksempel DAO eller Controller-klasser gir bonuspoeng. Konsistent bruk av begreper og kode vektlegges. Brukervennlighet er ikke et vurderingskriterie for karakteren.

**Vedlegg: Sjekkliste for innlevering**
- [ ] Dere har lastet opp en ZIP-fil med navn basert på navnet på deres Github repository
- [ ] Koden er sjekket inn på github.com/kristiania-repository
- [x] Dere har committed kode med begge prosjektdeltagernes GitHub konto (alternativt: README beskriver arbeidsform)

**README.md**
- [ ] README.md inneholder en korrekt link til Github Actions
- [ ] README.md beskriver prosjektets funksjonalitet, hvordan man bygger det og hvordan man kjører det
- [ ] README.md beskriver eventuell ekstra leveranse utover minimum
- [ ] README.md inneholder et diagram som viser datamodellen
- [ ] Dere har gitt minst 2 positive og 2 korrektive GitHub issues til en annen gruppe og inkluderer link til disse fra README.md

**Koden**
- [ ] mvn package bygger en executable jar-fil
- [ ] Koden inneholder et godt sett med tester
- [ ] java -jar target/...jar (etter mvn package ) lar bruker legge til og liste ut data fra databasen via webgrensesnitt
- [x] Programmet leser dataSource.url , dataSource.username og dataSource.password fra pgr203.properties for å connecte til databasen
- [x] Programmet bruker Flywaydb for å sette opp databaseskjema
- [x] Server skriver nyttige loggmeldinger, inkludert informasjon om hvilken URL den kjører på ved oppstart

**Funksjonalitet**
- [x] Programmet kan liste prosjektdeltagere fra databasen
- [x] Programmet lar bruker opprette nye prosjektdeltagere i databasen
- [x] Programmet kan opprette og liste prosjektoppgaver fra databasen
- [x] Programmet lar bruker tildele prosjektdeltagere til oppgaver
- [x] Flere prosjektdeltagere kan tildeles til samme oppgave
- [x] Programmet lar bruker endre status på en oppgave

**Vedlegg: Mulighet for ekstrapoeng**
- [x] Håndtering av request target "/"
- [x] Avansert datamodell (mer enn 3 tabeller)
- [ ] UML diagram som dokumenterer datamodell og/eller arkitektur (presentert i README.md)
- [ ] Rammeverk rundt Http-håndtering (en god HttpMessage class med HttpRequest og HttpResponse subtyper) som gjenspeiler RFC7230
- [x] God bruk av DAO-pattern
- [x] God bruk av Controller-pattern
- [x] Korrekt håndtering av norske tegn i HTTP
- [ ] Link til video med god demonstrasjon av ping-pong programmering
- [ ] Automatisk rapportering av testdekning i Github Actions
- [ ] Annet. ???- [x] Lar samme oppgave tildeles flere medlemmer, og liste oppgave per member.
