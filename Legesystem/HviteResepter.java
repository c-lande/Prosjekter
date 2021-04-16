public class HviteResepter extends Resept{
  public int id = 0;
  public HviteResepter(Legemiddel lmiddel, Lege lege,Pasient pasient ,int reit){
    super(lmiddel, lege, pasient, reit);
    id +=1;
  }
  /*Sender parameter til resept, øker id med 1 per nye objekt slik at de får ulik id.*/
  @Override
  public String farge(){
    return "Hvit";
  }
  @Override
  public double prisAaBetale(){
    return legemiddel.hentPris();
  }
  /*Metode for pris og farge.*/
  @Override
  public String toString(){
    return super.toString();
  }
  /*Skriver ut nyttig info om HviteResepter.*/
}
