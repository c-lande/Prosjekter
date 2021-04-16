package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


class AspNone extends AspAtom{
  TokenKind kind;

  AspNone(int n){
    super(n);
  }

  static AspNone parse(Scanner s){
    enterParser("none");
    AspNone an = new AspNone(s.curLineNum());
    an.kind = s.curToken().kind;
    skip(s, noneToken);
    leaveParser("none");
    return an;

  }
  @Override
  public void prettyPrint() {
    prettyWrite("none");
  }


  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    return new RuntimeNoneValue();
  }
}
