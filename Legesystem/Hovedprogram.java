public class Hovedprogram{
  public static void main(String[] args) {
    SortertLenkeliste<Lege> SortertLeger = new SortertLenkeliste<>();
    Pasient pasient1 = new Pasient ("Per", "130499");
    /*Legemidler*/
    PreparatA aPrep = new PreparatA("Predizol", 450, 75, 8);
    PreparatB bPrep = new PreparatB("Paralgin Forte", 65, 33, 5);
    PreparatC cPrep = new PreparatC("Ibux", 120, 3);

    /*Leger*/
    Lege lege1 = new Lege("Dr. Cox");
    Spesialist lege2 = new Spesialist("Dr. Wilson", 3);
    Lege lege3 = new Lege("Dr. Aristoteles");
    Lege lege4 = new Lege("Dr. Per Johan");


    /*Resepter*/
    Presepter resept1 = new Presepter(cPrep, lege2, pasient1 ,1);
    Militaerresepter resept2 = new Militaerresepter(aPrep, lege1, pasient1 ,3);
    BlaaResepter blaa = new BlaaResepter(aPrep, lege1, pasient1,1);
    HviteResepter hvit = new HviteResepter(bPrep, lege1, pasient1,2);

    System.out.println(aPrep.toString());
    System.out.println("\n");
    System.out.println(bPrep.toString());
    System.out.println("\n");
    System.out.println(cPrep.toString());

    System.out.println("\n");
    System.out.println(lege1.toString());
    System.out.println(lege2.toString());
    System.out.println("\n");

    System.out.println(resept1.toString());
    System.out.println("\n");
    System.out.println(resept2.toString());
    System.out.println("\n");
    System.out.println(blaa.toString());
    System.out.println("\n");
    System.out.println(hvit.toString());
    System.out.println("\n");

    try{
      System.out.println(lege1.skrivResept(cPrep, pasient1, 3) + "\n");

    }catch(UlovligUtskrift x){
      System.out.println(x+"\n");
    }

    try{
      System.out.println(lege2.skrivResept(aPrep, pasient1, 3)+"\n");

    }catch(UlovligUtskrift x){
      System.out.println(x+"\n");
    }

    pasient1.addResept(resept1);
    pasient1.addResept(resept2);

    Lenkeliste<Lege> legeList = new Lenkeliste<Lege>();

    legeList.leggTil(lege1);
    legeList.leggTil(lege4);
    legeList.leggTil(lege3);


    Lege forst = legeList.hent(0);
    int pos = 0;

    for (Lege lege : legeList ) {
      if(lege.compareTo(forst) < 0){
        forst = lege;
        System.out.println("\n"+forst+"\n");

      }
    }

    System.out.println("\n"+"\n");
    SortertLeger.leggTil(lege3);
    SortertLeger.leggTil(lege4);
    SortertLeger.leggTil(lege1);

    for (Lege lege : SortertLeger ) {
      System.out.println(lege);

    }
  }
}
