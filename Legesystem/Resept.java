abstract class Resept{
  public Legemiddel legemiddel;
  public Lege utskrivendeLege;
  public Pasient pasient;
  public int reit;
  private static int tell = 0;
  private int ID;

  public Resept(Legemiddel legemiddel, Lege utskrivendeLege, Pasient pasient, int reit){
    this.legemiddel = legemiddel;
    this.utskrivendeLege = utskrivendeLege;
    this.pasient = pasient;
    this.reit = reit;
    ID = tell++;
  }

  public String toString(){
    return "ID: " + ID + "\n" +"Pasient: " + pasient +"\n"+ "Legemiddel: " + legemiddel + "\n"+"Reit: " + reit + "\n" +utskrivendeLege ;
  }
  /*toString metode som returnerer nyttig info om resepten.. */



  public int hentId(){
    return ID;
  }
  public String hentLegemiddel(){
    String navn = legemiddel.hentNavn();
    return navn;
  }
  public Lege hentLege(){
    return utskrivendeLege;
  }
  public Pasient hentPasient(){
    return pasient;
  }
  public int hentReit(){
    return reit;
  }
  public Legemiddel hentlMid(){
    return legemiddel;
  }


  public boolean bruk(){
    if(reit >0){
      reit --;
      return true;
    }else{
      return false;
    }
  }
  /*De første metodene returnerer nyttig informasjon om resepter. Den siste sjekker om resepten kan brukes flere ganger. */

  abstract public String farge();

  abstract public double prisAaBetale();
/*Abstract metoder for farge og pris å betale. Tar de i bruk i subklasser. */
}
