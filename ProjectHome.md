# Projekt sop-xml #

Studentský projekt do předmětu PB138 jehož cílem je vyvinutí Java knihovny pro sémanticky orientované porovnávání XML souborů

## Rozdělení projektu a práce ##

viz wiki stránka [AboutSopXml](http://code.google.com/p/sop-xml/wiki/AboutSopXml)

## Původní zadání projektu ##

Výstupem programu je buďto tvrzení, že xml soubory jsou podobné, anebo rozdílné spolu s výpisem rozdílů.

To co se považuje za podobná XML je vaším úkolem definovat. Podobnost xml je zcela jistě relace ekvivalence (xml soubor je sám sobě podobný, jeli podobný , B a B, C musí být i A, C). O některých XML souborech lze s jistotou říci že jsou si podobné, například:
  * Sobory A, B které se liší v aliasech namespaců
  * Sobory A, B kde v souboru A je element prázdný s ukončovacím tagem a v B je element prázdný nicméně bez ukončovacího tagu (sestává se pouze z jednoho tagu)
  * Soubory ve které jsou stejné, ale v některých mají jiné pořadí atributy.

Na druhou stranu existuje mnoho případů, kdy není zcela jasné jestli jsou dva XML soubory v relaci podobnosti, například:
  * Různé pořadí elementů
  * Bílé znaky navíc v obsahu elementu

Pro tyto případy musí mít knihovna nastavení, kde lze dodefinovat jestli uživatel považuje takové dokumenty za podobné.
Součástí řešení musí být jasná dokumentace relace "podobná xml". Je vhodné aby si nejdříve řešitelé prošly existující metody a nástroje na porovnávání XML a v z nich si vypsali, co daný nástroj považuje za podobnost.

V rámci implementace využijte pouze JDK.

Smysluplné využití unit testů bude bráno jako velké plus

## Tým vývojářů/studentů ##
  * [Martin Putniorz](http://code.google.com/p/sop-xml/wiki/MartinPutniorz)  - programátor, team leader
  * [Mojmír Kubištel](http://code.google.com/p/sop-xml/wiki/MojmirKubistel)  - programátor
  * [Sandra Dedíková](http://code.google.com/p/sop-xml/wiki/SandraDedikova)  - programátor
  * [Jan Sláma](http://code.google.com/p/sop-xml/wiki/JanSlama)  - wiki administrátor, programátor

## Dohlížející ##
  * Mgr. Filip Nguyen
  * doc. RNDr. Tomáš Pitner, Ph.D.
  * Mgr. Luděk Bártek, Ph.D.