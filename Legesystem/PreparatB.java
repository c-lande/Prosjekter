public class PreparatB extends Legemiddel{
  int styrke;
  public PreparatB(String navn, double pris, double virkestoff, int styrke){
    super(navn, pris, virkestoff);
    this.styrke = styrke;
  }
  /*Utvider legemiddel, sender med navn, pris og virkestoff til legemiddel som super, har i tillegg styrke. */ 
  public int hentVanedannendeStyrke(){
    return styrke;
  }
  @Override
  public String toString(){
    return super.toString() + "\n" +"Vanedannende styrke: "+ hentVanedannendeStyrke();
  }
  /*toString metode som returnerer nyttig info om legemiddelet. */
}
