class TestPreparat{
  public static void main(String[] args) {
    PreparatA a = new PreparatA("aPrep", 200, 1, 4);
    PreparatB b = new PreparatB("bPrep", 143, 4, 5);
    PreparatC c = new PreparatC("cPrep", 15, 1);

    System.out.println(a.hentId());
    System.out.println(a.hentNavn());
    System.out.println(a.hentPris());
    System.out.println(a.hentVirkestoff());
    System.out.println(a.settNyPris(5));
    System.out.println(a.hentNarkotiskStyrke());

    System.out.println("\n");

    System.out.println(b.hentId());
    System.out.println(b.hentNavn());
    System.out.println(b.hentPris());
    System.out.println(b.hentVirkestoff());
    System.out.println(b.settNyPris(5));
    System.out.println(b.hentVanedannendeStyrke());

    System.out.println("\n");

    System.out.println(c.hentId());
    System.out.println(c.hentNavn());
    System.out.println(c.hentPris());
    System.out.println(c.hentVirkestoff());
    System.out.println(c.settNyPris(5));


  }
}
