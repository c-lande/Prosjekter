public class Spesialist extends Lege implements Godkjenningsfritak{
  public String navn;
  public int kontrollID;

  public Spesialist(String navn, int kontrollID){
    super(navn);
    this.kontrollID = kontrollID;
  }
  public Resept skrivResept(Legemiddel legemiddel, Pasient pasient, int reit)throws UlovligUtskrift{
    if(legemiddel instanceof PreparatA){
      narko++;
    }
    BlaaResepter resept = new BlaaResepter(legemiddel, this ,pasient, reit);
    pasient.addResept(resept);
    return resept;
  }

  public int hentKontrollID(){
    return kontrollID;
  }
}
