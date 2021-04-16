class SortertLenkeliste<T extends Comparable<T>> extends Lenkeliste<T>{


  @Override
  public void leggTil(T x){

    if (start == null){
      start = new Node(x);
      return;
    }

    Node p = start;
    for(int i = 0; i <= stoerrelse(); i++){

      if(x.compareTo(p.data)<0){
          super.leggTil(i, x);
          return;
        }
        if(p.neste == null){
          super.leggTil(stoerrelse(),x);
          return;
        }
        p = p.neste;
      }
  }

  /*Legger til elementer sortert fra minst til størst. */

  @Override
  public T fjern(){
    if(start ==null){
      throw new UgyldigListeIndeks(-1);
    }
    T node = fjern(stoerrelse()-1);
    return node;
  }
  /*Er listen tom, slår UgyldigListeIndeks til. Ellers fjerner den det siste elementet.*/

  @Override
  public void sett(int pos, T x) throws UnsupportedOperationException{
    throw new UnsupportedOperationException();
  }

  @Override
  public void leggTil(int pos, T x)throws UnsupportedOperationException{
    throw new UnsupportedOperationException();
  }
  /*Overskriver sett(int pos, T x) og leggTil(int pos, T x), begrenser muligheten for å sette
  inn elementer på en vilkårlig posisjon. */
}
