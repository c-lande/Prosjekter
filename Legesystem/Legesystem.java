import java.util.*;
import java.io.*;
import java.util.Scanner;

public class Legesystem{
    // Opprett lister som lagrer objektene i legesystemet
    static SortertLenkeliste<Lege> LegerList = new SortertLenkeliste<>();
    static Lenkeliste<Resept> ReseptList = new Lenkeliste<>();
    static Lenkeliste<Pasient> PasienterList = new Lenkeliste<>();
    static Lenkeliste<Legemiddel> PreparatList = new Lenkeliste<>();


    public static void main(String[] args){
      File dir = new File("les.txt");
      lesFraFil(dir);
      Main();
    }

    private static void lesFraFil(File fil){
        Scanner scanner = null;
        try{
            scanner = new Scanner(fil);
        }catch(FileNotFoundException e){
            System.out.println("Fant ikke filen, starter opp som et tomt Legesystem");
            return;
        }
        String innlest = scanner.nextLine();
        while(scanner.hasNextLine()){

            String[] info = innlest.split(" ");

            // Legger til alle pasientene i filen
            if(info[1].compareTo("Pasienter") == 0){
                while(scanner.hasNextLine()) {
                    innlest = scanner.nextLine();
                    String[] pasienter = innlest.split(", ");

                    //Om vi er ferdig med å legge til pasienter, bryt whileløkken,
                    //slik at vi fortsetter til koden for å legge til legemiddler
                    if(innlest.charAt(0) == '#'){
                        break;
                    }

                    String pasientnavn = pasienter[0];
                    String fodselsnr = pasienter[1];

                    Pasient pasient = new Pasient(pasientnavn, fodselsnr);
                    PasienterList.leggTil(pasient);
                }
            }
            //Legger inn Legemidlene
            else if(info[1].compareTo("Legemidler") == 0){
                while(scanner.hasNextLine()){
                    innlest = scanner.nextLine();
                    //Om vi er ferdig med å legge til legemidler, bryt whileløkken,
                    //slik at vi fortsetter til koden for å legge til leger
                    if(innlest.charAt(0) == '#'){
                        break;
                    }
                    String[] legemiddel = innlest.split(", ");
                    if(legemiddel[1].compareTo("a") == 0){
                      String navn = legemiddel[0];
                      String type = legemiddel[1];
                      double pris = Integer.parseInt(legemiddel[2]);
                      double virkestoff = Integer.parseInt(legemiddel[3]);
                      int styrke = Integer.parseInt(legemiddel[4]);

                      PreparatA lmiddel = new PreparatA(navn, pris, virkestoff, styrke);
                      PreparatList.leggTil(lmiddel);


                    }
                    else if(legemiddel[1].compareTo("b") == 0){
                      String navn = legemiddel[0];
                      String type = legemiddel[1];
                      double pris = Integer.parseInt(legemiddel[2]);
                      double virkestoff = Integer.parseInt(legemiddel[3]);
                      int styrke = Integer.parseInt(legemiddel[4]);

                      PreparatB lmiddel = new PreparatB(navn, pris, virkestoff, styrke);
                      PreparatList.leggTil(lmiddel);

                    }else if (legemiddel[1].compareTo("c") == 0){
                      String navn = legemiddel[0];
                      String type = legemiddel[1];
                      double pris = Integer.parseInt(legemiddel[2]);
                      double virkestoff = Integer.parseInt(legemiddel[3]);

                      PreparatC lmiddel = new PreparatC(navn, pris, virkestoff);
                      PreparatList.leggTil(lmiddel);

                    }
                }
            }
            //Legger inn leger
            else if(info[1].compareTo("Leger") == 0){
                while(scanner.hasNextLine()){
                    innlest = scanner.nextLine();

                    if(innlest.charAt(0) == '#'){
                        break;
                    }
                    info = innlest.split(", ");
                    int kontrollid = Integer.parseInt(info[1]);
                    if(kontrollid == 0){
                      Lege lege = new Lege(info[0]);
                      LegerList.leggTil(lege);

                    }else{
                      Spesialist sLege = new Spesialist(info[0], kontrollid);
                      LegerList.leggTil(sLege);
                    }
                }
            }
            //Legger inn Resepter
            else if(info[1].compareTo("Resepter") == 0){
                while(scanner.hasNextLine()){
                    innlest = scanner.nextLine();
                    info = innlest.split(", ");


                    int lmiddelID = Integer.parseInt(info[0]);
                    String legenavn = info[1];
                    int pasient1 = Integer.parseInt(info[2]);
                    int reit = Integer.parseInt(info[3]);

                    Pasient pas = PasienterList.hent(pasient1);
                    Legemiddel lmid = PreparatList.hent(lmiddelID);

                    Lege midLege = null;
                    for (Lege lege :LegerList ) {
                      if(legenavn.equals(lege.hentNavn())){
                      midLege = lege;
                      break;
                      }
                    }
                    try{
                      Resept res = midLege.skrivResept(lmid, pas, reit);
                      ReseptList.leggTil(res);

                    }catch(UlovligUtskrift x){
                      System.out.println(x+"\n");
                    }
                }
            }
        }
    }

    public static void Main(){
      Scanner spm = new Scanner(System.in);
      System.out.println("Her har du en oversikt over legesystemet. Hva onsker du aa se mer paa?");
      System.out.println("Leger");
      System.out.println("Pasienter");
      System.out.println("Resepter");
      System.out.println("Legemidler");
      System.out.println("Reit");
      System.out.println("Leggtil");
      System.out.println("Statistikk");
      String spm1 = spm.nextLine();

      if(spm1.equals("pasienter")||spm1.equals("Pasienter")){
        visPasienter();
     }else if(spm1.equals("leger")||spm1.equals("Leger")){
       System.out.println("________________________");
       visLeger();
     }else if(spm1.equals("resepter")||spm1.equals("Resepter")){
       System.out.println("________________________");
       visResepter();
     } else if(spm1.equals("legemidler")||spm1.equals("Legemidler")){
       System.out.println("________________________");
       visLegemidler();
     }else if(spm1.equals("reit")||spm1.equals("Reit")){
       System.out.println("________________________");
       brukReit();
     }else if(spm1.equals("Leggtil")||spm1.equals("leggtil")){
       System.out.println("________________________");
       leggTilMeny();
     }else if(spm1.equals("Statistikk")||spm1.equals("statistikk")){
       System.out.println("________________________");
       statistikk();
     }
    }

    public static void visPasienter(){
      System.out.println("Her er alle pasientene: ");
      for (Pasient pas :PasienterList ) {
        System.out.println(pas);
      }
      Scanner spm = new Scanner(System.in);
      fortsett();
    }

    public static void visLeger(){
      System.out.println("Her er alle legene: ");
      for (Lege lege :LegerList ) {
        System.out.println(lege);
      }
      Scanner spm = new Scanner(System.in);
      fortsett();
    }

    public static void visResepter(){
      System.out.println("Her er alle reseptene: ");
      for (Resept res :ReseptList ) {
        System.out.println(res+"\n");
      }
      Scanner spm = new Scanner(System.in);
      fortsett();
    }

    public static void visLegemidler(){
      System.out.println("Her er alle legemidlene: ");
      for (Legemiddel lmiddel :PreparatList ) {
        System.out.println(lmiddel+"\n");
      }
      Scanner spm = new Scanner(System.in);
      fortsett();
    }

    public static void leggTilMeny(){
      System.out.println("Her er en oversikt over hva du kan legge til:");
      System.out.println("Lege\nPasient\nResept\nLegemiddel");

      Scanner spm = new Scanner(System.in);
      System.out.println("Hva vil du legge til?");
      String spm1 = spm.nextLine();
      if(spm1.equals("Lege")||spm1.equals("lege")){
        System.out.println("________________________");
        leggTilLege();
      }else if(spm1.equals("Pasient")||spm1.equals("pasient")){
        leggTilPasient();
      }else if(spm1.equals("Resept")||spm1.equals("resept")){
        skrivResept();
      }else if(spm1.equals("Legemiddel")||spm1.equals("legemiddel")){
        leggTilLegemiddel();
      }
    }

    public static void leggTilLege(){
      Scanner spm = new Scanner(System.in);
      System.out.println("Vil du legge til en lege eller spesialist?");
      String spm1 = spm.nextLine();

      if(spm1.equals("lege")){
        Scanner spm2 = new Scanner(System.in);
        System.out.println("Hva heter legen?");
        String spm3 = spm2.nextLine();
        Lege lege = new Lege(spm3);
        LegerList.leggTil(lege);
        System.out.println("________________________");
        System.out.println(lege.hentNavn() + " er naa lagt til!");
        fortsett();
      }else if(spm1.equals("spesialist")){
        Scanner spm4 = new Scanner(System.in);
        System.out.println("Hva heter spesialisten?");
        String spm5 = spm4.nextLine();
        Scanner spm6 = new Scanner(System.in);
        System.out.println("Hva er kontrollID-en til spesialisten?");
        int spm7 = Integer.parseInt(spm6.nextLine());

        Spesialist lege1 = new Spesialist(spm5, spm7);
        LegerList.leggTil(lege1);
        System.out.println("________________________");
        System.out.println(lege1.hentNavn() +" er naa lagt til!");
        fortsett();
      }
    }

    public static void leggTilPasient(){
      Scanner spm = new Scanner(System.in);
      System.out.println("Hva er navnet til pasienten?");
      String spm1 = spm.nextLine();

      Scanner spm2 = new Scanner(System.in);
      System.out.println("Hva er fodselsnummeret til pasienten?");
      String spm3 = spm2.nextLine();
      Pasient pas = new Pasient(spm1, spm3);
      PasienterList.leggTil(pas);
      System.out.println(pas.hentNavn() + " er naa lagt til!");
      fortsett();
      }

    public static void leggTilLegemiddel(){
      Scanner spm = new Scanner(System.in);
      System.out.println("Vil du legge til av typen A, B eller C?");
      String spm1 = spm.nextLine();

      System.out.println("________________________");
      if(spm1.equals("A")||spm1.equals("a")){
        Scanner navn = new Scanner(System.in);
        System.out.println("Navnet til legemiddelt:");
        String navnet = navn.nextLine();

        Scanner pris = new Scanner(System.in);
        System.out.println("Prisen til legemiddelet:");
        double prisen = Integer.parseInt(pris.nextLine());


        Scanner virkestoff = new Scanner(System.in);
        System.out.println("Virkestoffet til legemiddelet:");
        double vstoff = Integer.parseInt(virkestoff.nextLine());

        Scanner styrke = new Scanner(System.in);
        System.out.println("Narkotiskstyrke til legemiddelet:");
        int styrken = Integer.parseInt(virkestoff.nextLine());

        PreparatA aprep = new PreparatA(navnet, prisen, vstoff, styrken);
        PreparatList.leggTil(aprep);
        System.out.println("Du har naa lagt til " + navnet);
        fortsett();

      }else if(spm1.equals("B")||spm1.equals("b")){
        Scanner navn = new Scanner(System.in);
        System.out.println("Navnet til legemiddelt:");
        String navnet = navn.nextLine();

        Scanner pris = new Scanner(System.in);
        System.out.println("Prisen til legemiddelet:");
        double prisen = Integer.parseInt(pris.nextLine());


        Scanner virkestoff = new Scanner(System.in);
        System.out.println("Virkestoffet til legemiddelet:");
        double vstoff = Integer.parseInt(virkestoff.nextLine());

        Scanner styrke = new Scanner(System.in);
        System.out.println("Vanedannende styrke til legemiddelet:");
        int styrken = Integer.parseInt(virkestoff.nextLine());

        PreparatB bprep = new PreparatB(navnet, prisen, vstoff, styrken);
        PreparatList.leggTil(bprep);
        System.out.println("Du har naa lagt til " + navnet);
        fortsett();

        }else if(spm1.equals("C")||spm1.equals("c")) {

          Scanner navn = new Scanner(System.in);
          System.out.println("Navnet til legemiddelt:");
          String navnet = navn.nextLine();

          Scanner pris = new Scanner(System.in);
          System.out.println("Prisen til legemiddelet:");
          double prisen = Integer.parseInt(pris.nextLine());

          Scanner virkestoff = new Scanner(System.in);
          System.out.println("Virkestoffet til legemiddelet:");
          double vstoff = Integer.parseInt(virkestoff.nextLine());

          PreparatC cprep = new PreparatC(navnet, prisen, vstoff);
          PreparatList.leggTil(cprep);
          System.out.println("Du har naa lagt til " + navnet);
          fortsett();

      }
    }

    public static void skrivResept(){
      System.out.println("________________________");
      System.out.println("For aa skrive en resept, maa du forst skrive ID-en til et legemiddel.");
      Scanner spm = new Scanner(System.in);
      System.out.println("Hva er ID-en til legemiddelet ditt?");
      for (Legemiddel lmid : PreparatList ) {
        System.out.println(lmid.hentId() + " " + lmid.hentNavn());
      }
      String spm1 = spm.nextLine();
      int spm2 = Integer.parseInt(spm1);

      Legemiddel legemiddelet = PreparatList.hent(spm2);
      System.out.println(legemiddelet);

      System.out.println("________________________");
      System.out.println("\nHva er ID-en til pasienten din?");
      for (Pasient pas :PasienterList ) {
        System.out.println(pas.hentId() +" "+ pas.hentNavn());
      }
      String spm3 = spm.nextLine();
      int spm4 = Integer.parseInt(spm3);
      Pasient pasienten = PasienterList.hent(spm4);
      System.out.println(pasienten);

      System.out.println("________________________");
      System.out.println("Antall reit: ");
      String spm5 = spm.nextLine();
      int spm6 = Integer.parseInt(spm5);

      System.out.println("________________________");
      System.out.println("Legenavn: ");
      for (Lege lege :LegerList ) {
        System.out.println(lege.hentNavn());

      }
      String spm7 = spm.nextLine();
      System.out.println("________________________");

      Lege rLege = null;
      for (Lege lege :LegerList ) {
        if(lege.hentNavn().equals(spm7)){
          rLege = lege;
        }
      }

      try{
        Resept res = rLege.skrivResept(legemiddelet, pasienten, spm6);
        ReseptList.leggTil(res);
        System.out.println(res);

      }catch(UlovligUtskrift x){
        System.out.println(x+"\n");
      }

      System.out.println("________________________");
      System.out.println("Du har naa skrevet resepten!");
      fortsett();
    }

    public static void brukReit(){
      Scanner spm = new Scanner(System.in);
      System.out.println("Her er reseptene. Hvilken er ID-en til den du vil bruke?");
      for (Resept res :ReseptList ) {
        System.out.println(res.hentId() + " " + res.hentLegemiddel() + " " + "(reit: " + res.hentReit() + ")");
      }
      String spm1 = spm.nextLine();
      int spm2 = Integer.parseInt(spm1);

      for (Resept res :ReseptList ) {
        if(spm2 == res.hentId()){
          res.bruk();
        }
      }
      System.out.println("________________________");
      for (Resept res : ReseptList ) {
        System.out.println(res+"\n");
      }
      fortsett();
    }

    public static void fortsett(){
      Scanner spm = new Scanner(System.in);
      System.out.println("Vil du gaa tilbake eller avslutte?");
      String inp = spm.nextLine();
      if(inp.equals("avslutt")||inp.equals("Avslutt")){
        Avslutt();
      }else if(inp.equals("tilbake")||inp.equals("Tilbake")){
        System.out.println("________________________");
        Main();
      }else{
        System.out.println("________________________");
        System.out.println("Denne inputen er ikke gyldig! Prov igjen\n");
        Main();
      }
    }

    public static void statistikk(){

      int vanedannende = 0;
      int narkotisk = 0;

      for (Resept res : ReseptList ) {
        if(res.hentlMid() instanceof PreparatB){
          vanedannende +=1;
        }

      }
      System.out.println("Antall vanedannende resepter skrevet ut: " + vanedannende);

      for (Resept res :ReseptList ) {
        if(res.hentlMid() instanceof PreparatA){
          narkotisk+=1;
        }
      }
      System.out.println("Antall narkotiske resepter skrevet ut: " + narkotisk);
      System.out.println("\n");

      for (Lege lege :LegerList ) {
        if(lege.hentNarko() > 0){
          System.out.println(lege.hentNavn() +" har skrevet ut "+ lege.hentNarko() + " narkotiske resepter.");
        }
      }

      System.out.println("\n");

      for(int i = 0; i< PasienterList.stoerrelse(); i++){
        Stabel<Resept> pasResept = PasienterList.hent(i).hentRes();

        int teller = 0;
        for (Resept res :pasResept ) {
          if(res.hentlMid() instanceof PreparatA){
          teller++;
          }
        }if(teller > 0){
          System.out.println("Pasienten " + PasienterList.hent(i).hentNavn() + " har " + teller + " narkotiske resepter.");
        }
      }
      System.out.println("________________________");
      fortsett();
    }

    public static void Avslutt(){
      System.out.println("Du har naa avsluttet.");
    }

}
