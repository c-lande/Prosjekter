package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public abstract class AspPrimarySuffix extends AspSyntax {

  AspPrimarySuffix(int n){
    super(n);
  }

  static AspPrimarySuffix parse(Scanner s){
    enterParser("primary suffix");

    AspPrimarySuffix ps = null;
    TokenKind tk = s.curToken().kind;

    if (tk == TokenKind.leftBracketToken) {
      ps = AspSubscription.parse(s);
    }
    else if(tk == TokenKind.leftParToken){
      ps = AspArgument.parse(s);
    }

    leaveParser("primary suffix");
    return ps;
  }

  abstract void prettyPrint();
  abstract RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue;
}
