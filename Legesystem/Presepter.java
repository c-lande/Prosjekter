public class Presepter extends HviteResepter{

  public Presepter(Legemiddel lmiddel, Lege lege, Pasient pasient,int reit){
    super(lmiddel, lege, pasient, 3);
  }

  @Override
  public double prisAaBetale(){
    double prisen = legemiddel.hentPris();
    double nyPris = prisen-108;

    if(nyPris >=0){
      return nyPris;
    } else{
      return 0;
    }
  }
  /*Trekker 108 kroner av prisen. hvis den nye prisen er over 0, blir nypris den nye prisen. Hvis den er mindre enn 0, returneres 0. */
  @Override
  public String toString(){
    return "Presept:"+"\n"+ super.toString()+"\n" +"Pris aa betale " +prisAaBetale() + " kr";
  }
  /*Returnerer nyttig info om presepten*/
}
