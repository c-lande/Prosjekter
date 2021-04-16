import java.util.Iterator;
import java.util.NoSuchElementException;

class Lenkeliste<T> implements Liste<T> {

  class LenkelisteIterator implements Iterator<T>{
    Node temp = start;
    @Override
    public boolean hasNext(){
      /*Lenkeliste ikke tom, start.neste != null*/
        return temp != null;
    }
    @Override
    public T next(){
      if (start == null) {
                throw new NoSuchElementException("next");
            }
            Node tmp = temp;
            temp = temp.neste;
            return tmp.data;
        }
    }

  class Node{
    Node neste = null;
    T data;

    Node(T x){data = x;}
  }

  /*Oppretter klassen Node, som har en neste peker og T data.*/
  Node start = null;

  public Iterator<T> iterator(){
       return new LenkelisteIterator();
   }






  public int stoerrelse() {
    Node mid = start;
    int antall = 0;

    while (mid != null){
      antall++;
      mid = mid.neste;
    }
    return antall;
  }
  /*Går gjennom listen, finner størrelse.*/

  public void leggTil(T x) {
    if (start == null){
      start = new Node(x);
      return;
    }
    Node p = start;
    while (p.neste != null){
      p = p.neste;
    }
    p.neste = new Node(x);
  }
  /*Legger til element på slutten av listen*/

  public T fjern() throws UgyldigListeIndeks{
    if(start ==null){
      throw new UgyldigListeIndeks(-1);
    }

    Node p = start;
    T data = start.data;
    start = start.neste;
    return data;
  }
  /*Fjerner et element.*/

  public void sett(int pos, T x) throws UgyldigListeIndeks{
    if(pos < 0){
      throw new UgyldigListeIndeks(pos);
    }
    if(pos > stoerrelse()-1){
      throw new UgyldigListeIndeks(pos);
    }

    Node p = start;

    for(int i = 0; i<pos; i++){
      p = p.neste;
    }
    p.data = x;
  }
  /*For løkken går til element pos, skriver over dens data.*/

  public void leggTil(int pos, T x){

    if(pos == stoerrelse()){
      if(start ==null){
        start = new Node(x);
        return;
      }
      Node p = start;
      while (p.neste != null){
        p = p.neste;
      }
      p.neste = new Node(x);
      return;
      }

    if(pos < 0){
      throw new UgyldigListeIndeks(pos);
    }

    if(stoerrelse() != 0){
      if(pos > stoerrelse()-1 ){
        throw new UgyldigListeIndeks(pos);
      }
    }
      if(stoerrelse() == 0){
        if(pos > stoerrelse()){
          throw new UgyldigListeIndeks(pos);
        }
        start = new Node(x);

    }else if(pos == 0){
      Node node = new Node(x);
      node.neste = start;
      start = node;
    }else{
      Node p = start;

      for(int i = 0; i<pos-1; i++){
        p = p.neste;
      }
      Node nesteTneste = p.neste;
      p.neste = new Node(x);
      p.neste.neste = nesteTneste;
    }
  }
  /*Legger til et element, og forskyver de etter. Gjør dette ved å finne frem til
  et element i listen før gitt posisjon. Lager en node nesteTneste som er Noden som må forskyves.
  Oppretter deretter en ny node med x data, som blir pekt på av p. Deretter setter jeg p til
  å peke på neste.neste, og dermed forskyves alle med 1.*/

  public T fjern(int pos){
    if(pos < 0){
      throw new UgyldigListeIndeks(pos);
    }
    if(pos > stoerrelse()-1){
      throw new UgyldigListeIndeks(pos);
    }
    if(stoerrelse() == 1){
      T mdata = start.data;
      start = start.neste;
      return mdata;
    }else{
      Node p = start;
      for(int i = 0; i< pos-1; i++){
        p = p.neste;
      }
      Node n = p.neste;
      p.neste = n.neste;
      return n.data;
    }
  }
  /*Finner frem til posisjonen før den som skal fjernes, setter p til p.neste. Lager en node n
  og setter p sin neste til å være n sin neste. Hopper dermed over node n, som er noden med
  posisjonen som skal fjernes.*/

  public T hent(int pos) throws UgyldigListeIndeks{
    if(pos < 0){
      throw new UgyldigListeIndeks(pos);
    }
    if (pos > stoerrelse()-1){
      throw new UgyldigListeIndeks(pos);
    }
    Node p = start;

    for(int i = 0; i < pos; i++){
      p = p.neste;
    }
    return p.data;
  }
  /*Finner frem til posisjonen pos, og returnerer dataen.*/
}
