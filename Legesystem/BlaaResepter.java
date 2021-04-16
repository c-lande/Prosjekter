class BlaaResepter extends Resept{
  public BlaaResepter(Legemiddel legemiddel, Lege utskrivendeLege, Pasient pasient, int reit){
    super(legemiddel, utskrivendeLege, pasient, reit);
  }

  @Override
  public double prisAaBetale(){
    return legemiddel.hentPris()*0.25;
  }

  @Override
  public String farge(){
    return "Blaa";
  }
  /*Abstract metoden fra resept.*/
  @Override
  public String toString(){
    return super.toString() +"\n" + "Farge: " + farge() +"\n" +"Pris aa betale: " +prisAaBetale() + " kr";
  }
  /*toString metode som returnerer nyttig info om resepten. */



}
