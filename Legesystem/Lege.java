public class Lege implements Comparable<Lege>{
  public String navn;
  Lenkeliste<Resept> utskrevedeResepter;
  int narko = 0;

  public Lege(String navn){
    this.navn = navn;
    utskrevedeResepter = new Lenkeliste<Resept>();
  }

  public String hentNavn(){
    return navn;
  }
  /*Abstract metoden fra resept.*/

  @Override
  public String toString(){
    return "Lege navn: " + navn;
  }
  /*toString metode som returnerer navnet til legen. */

  public int hentNarko(){
    return narko;
  }

  public Resept skrivResept(Legemiddel legemiddel, Pasient pasient, int reit)throws UlovligUtskrift{
    Resept resept = new BlaaResepter(legemiddel, this ,pasient, reit);
    if(legemiddel instanceof PreparatA){
      throw new UlovligUtskrift(this, legemiddel);
      }
      pasient.addResept(resept);
      utskrevedeResepter.leggTil(resept);
      return resept;
    }
  /*metoden skriv resept. Tar inn legemiddel, pasientID og reit. Hvis denne legen er spesialist,
  returneres resepten jeg oppretter øverst i metoden.
  Hvis ikke legen er spesialist, sjekker jeg om legemiddelet er av typen Preparat A.
  Hvis det er det, blir man sendt til UlovligUtskrift, med denne legen og legemiddelet som parameter.
  Hvis det ikke er av typen A, returneres resepten.*/

  public int compareTo (Lege annen) {
    return this.navn.compareTo(annen.hentNavn());
  }

}
