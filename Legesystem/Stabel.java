class Stabel<T> extends Lenkeliste<T> {

  public void leggPaa(T x){
    leggTil(stoerrelse(),x);
  }

  public T taAv(){
    T node = fjern(stoerrelse()-1);
    return node;
  }
  /*Den første metoden legger til et element på slutten av listen, den andre fjerner det siste.*/
}
