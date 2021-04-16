package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


class AspFloat extends AspAtom{
  double floatL;

  AspFloat(int n){
    super(n);
  }

  static AspFloat parse(Scanner s){
    enterParser("float");
    AspFloat af = new AspFloat(s.curLineNum());
    af.floatL = s.curToken().floatLit;
    skip(s, floatToken);
    leaveParser("float");
    return af;
  }
  @Override
  public void prettyPrint() {
    prettyWrite(""+floatL);
  }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
      return new RuntimeFloatValue(floatL);
  }
}
