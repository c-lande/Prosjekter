package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


class AspFactorPrefix extends AspSyntax {
  TokenKind kind;
  String token;

  AspFactorPrefix(int n){
    super(n);
  }

  static AspFactorPrefix parse(Scanner s) {
    enterParser("factor prefix");
    AspFactorPrefix fp = new AspFactorPrefix(s.curLineNum());
    fp.kind = s.curToken().kind;
    if(s.curToken().kind == plusToken){
      skip(s, plusToken);
      fp.token = "+";
      leaveParser("factor prefix");
      return fp;
    }
    else if(s.curToken().kind == minusToken){
      skip(s, minusToken);
      fp.token = "-";
      leaveParser("factor prefix");
      return fp;
    }

    return null;
  }
  @Override
  public void prettyPrint() {
    prettyWrite(" " + token + " ");
  }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
	     return null;
  }
}
