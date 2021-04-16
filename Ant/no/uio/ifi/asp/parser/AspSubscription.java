package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspSubscription extends AspPrimarySuffix {

    AspExprStmt es;

    AspSubscription(int n){
      super(n);
    }

    static AspSubscription parse(Scanner s){
      enterParser("Subscription");
      AspSubscription scp = new AspSubscription(s.curLineNum());
      skip(s, leftBracketToken);

      scp.es = AspExprStmt.parse(s);

      skip(s, rightBracketToken);
      leaveParser("Subscription");
      return scp;
    }

    @Override
    public void prettyPrint() {
      prettyWrite("[");
		  es.prettyPrint();
		  prettyWrite("]");
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
      return es.eval(curScope);
  }
}
