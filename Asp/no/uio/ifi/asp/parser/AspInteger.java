package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspInteger extends AspAtom{
  long aspInteger;

  AspInteger(int n){
    super(n);
  }

  static AspInteger parse(Scanner s){
    enterParser("integer");
    AspInteger ai = new AspInteger(s.curLineNum());
    ai.aspInteger = s.curToken().integerLit;
    skip(s, integerToken);
    leaveParser("integer");
    return ai;
  }

  @Override
  public void prettyPrint() {
    prettyWrite(Long.toString(aspInteger));
  }


  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
      return new RuntimeIntValue(aspInteger);
  }
}
