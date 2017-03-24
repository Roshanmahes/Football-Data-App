![pset6screenshot](https://cloud.githubusercontent.com/assets/25647170/24294860/93b0c1d6-1098-11e7-8463-b616d240bf11.png)

Nu heb ik een 8 voor mijn codekwaliteit van Better Code Hub. Dit was eerst een 6. Ik heb geprobeerd zo veel mogelijk problemen te verhelpen. Dit zijn de grootste aanpassingen die ik heb gedaan:

- Write Short Units of Code
Voorheen had ik zeer lange functies in mijn Activity classes (bij OnCreate). Ook al had ik veel witregels toegevoegd, dit was erg onoverzichtelijk, besefte ik nadat ik BetterCodeHub had gebruikt. Nu heb ik dat veranderd door steeds kleinere functies te maken en die aan te roepen in mijn OnCreate. Nu is het een stuk overzichtelijker.

- Write Code Once
Ik had eerst drie RequestHelpers, aangezien ik drie keer data vanuit mijn API laad. Nu heb ik dit veranderd in een enkele HttpRequestHelper class, die drie keer benaderd wordt. Hij wordt nu één keer benaderd, doordat ik nu mijn link al in mijn AsyncTask class maak (bij DoInBackground), en vervolgens de HttpRequestHelper oproep met de link. De params hoef ik nu dus ook niet meer aan mijn HttpRequestHelper mee te geven.

- Write Simple Units of Code
Eerst had ik af en toe comments staan, waarin ik de naam van een functie roep, waardoor het er erg ingewikkeld uit zag. Dit was bij nader inzien niet nodig, mijn comments zijn nu korter en strakker.
