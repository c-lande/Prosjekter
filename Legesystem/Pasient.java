class Pasient{
  private String navn;
  private String fodselsnr;
  static int teller = 0;
  private int id;
  Stabel<Resept> rListe=new Stabel<Resept>();

  public Pasient(String navn, String fodselsnr){
    this.navn = navn;
    this.fodselsnr = fodselsnr;
    id = teller;
    teller ++;


  }

  public void addResept(Resept resept){
    rListe.leggPaa(resept);
  }
  public Stabel<Resept> hentRes(){
    return rListe;
  }


  public int hentId(){
    return id;
  }
  public String hentNavn(){
    return navn;
  }


  public String toString(){
    return "\n"+ navn + "\n" + "Fodselsnummer: " + fodselsnr + "\n" + "Id: " + id;
  }
}
