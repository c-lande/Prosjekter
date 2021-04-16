public class PreparatA extends Legemiddel{
  int styrke;
  public PreparatA(String navn, double pris, double virkestoff, int styrke){
    super(navn, pris, virkestoff);
    this.styrke = styrke;
  }
  /*Utvider klassen legemiddel, sender med navn, pris og virkestoff som super til legemiddel. Har i tillegg styrke. */
  public int hentNarkotiskStyrke(){
    return styrke;
  }

  @Override
  public String toString(){
    return super.toString() + "\n" +"Narkotisk styrke: "+ hentNarkotiskStyrke();
  }
  /*toString metode som returnerer nyttig info om legemiddelet. */

}
