# O projektu #

viz [Project Home](http://code.google.com/p/sop-xml/)

Pro inspiraci [XMLUnit](http://xmlunit.sourceforge.net/) a [XOM](http://www.xom.nu/) - řeší podobný problém v rámci jedné svojí funkcionality.

# Analýza a návrh řešení #

3 funkce porovnávání:

  * porovnání namespace
  * porovnání prázdných elementů
  * porovnání atributů pro jednotlivé elementy

Funkce jsou na sobě závislé (hlavně porovnání namespace na zbylých porovnáních). V hlavním programu bude probíhat pouze načítání souborů, obsluha příkazové řádky a volání jednotlivých funkcí.

## Rozdělení projektu a práce ##

### Namespace ###

Podúlohy:

  * aliasy namespace
  * formy namespace
    * prázdný namespace
    * prvky v namespace

Implementačně složitější část z důvodu samotné problematiky "přibližného" porovnání namespace.

Řeší:
  * [Martin Putniorz](http://code.google.com/p/sop-xml/wiki/MartinPutniorz)
  * [Sandra Dedíková](http://code.google.com/p/sop-xml/wiki/SandraDedikova)

### Porovnání elementů a atributů ###

Implementačně jednodušší, budou využívány jak při porovnání XML, tak v porovnávání namespace.

Řeší:
  * [Mojmír Kubištel](http://code.google.com/p/sop-xml/wiki/MojmirKubistel)
  * [Jan Sláma](http://code.google.com/p/sop-xml/wiki/JanSlama) - případná výpomoc