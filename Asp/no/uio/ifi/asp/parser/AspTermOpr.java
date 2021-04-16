package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspTermOpr extends AspTerm{
  String tokenS;
  TokenKind tk;

    AspTermOpr(int n){
      super(n);
    }

  static AspTermOpr parse(Scanner s) {
    enterParser("term operator");
    AspTermOpr to = new AspTermOpr(s.curLineNum());
    to.tk = s.curToken().kind;
    if(s.curToken().kind == plusToken){
      skip(s, plusToken);
      to.tokenS = "+";
      leaveParser("term operator");
      return to;
    }
    else if(s.curToken().kind == minusToken){
      skip(s, minusToken);
      to.tokenS = "-";
      leaveParser("term operator");
      return to;
    }
    return null;
  }

  @Override
  public void prettyPrint() {
    prettyWrite(tokenS);
  }


  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    return null;
  }
}
