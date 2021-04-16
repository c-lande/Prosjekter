abstract class Legemiddel{
  public String navn;
  private int id = 0;
  private static int tell = 0;
  public double pris;
  public double virkestoff;

  public Legemiddel(String navn, double pris, double virkestoff){
    this.navn = navn;
    this.pris = pris;
    this.virkestoff = virkestoff;
    id = tell++;
  }
  /*Parameter med navn, pris, virkestoff og en ID som øker med 1 per nye legemiddel. */

  @Override
  public String toString(){
    return navn +"\n"+ "Pris: " + pris +" kr" +"\n"+ "Virkestoff: " + virkestoff + "\nLegemiddelID: "+ id;
  }
  /*toString metode som returnerer nyttig info om legemiddelet. */

  public int hentId(){
    return id;
  }
  public String hentNavn(){
    return navn;
  }
  public double hentPris(){
    return pris;
  }
  public double hentVirkestoff(){
    return virkestoff;
  }
  public double settNyPris(double nyPris){
    return pris = nyPris;
  }
  /*Ulike metoder som returnerer informasjon om legemiddelet.*/

}
