public class Militaerresepter extends HviteResepter{
  Legemiddel lmiddel;
  public Militaerresepter(Legemiddel lmiddel, Lege lege, Pasient pasient,int reit){
    super(lmiddel, lege, pasient, reit);
  }
  @Override
  public double prisAaBetale(){
    return 0;
  }
  /*Returnerer 0 siden militaerresepter er gratis. */
  @Override
  public String toString(){
    return "Militaerresept:"+"\n"+super.toString()+"\n" +"Pris aa betale " +prisAaBetale() + " kr";
  }
/*returnerer nyttig info om militaerresepter*/

}
