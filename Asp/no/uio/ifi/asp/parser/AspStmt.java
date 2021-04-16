package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public abstract class AspStmt extends AspSyntax{

    AspStmt(int n){
      super(n);
    }

  static AspStmt parse(Scanner s) {
    enterParser("stmt");
    AspStmt stmt;

    if(s.curToken().kind == forToken || s.curToken().kind == whileToken
    || s.curToken().kind == ifToken || s.curToken().kind == defToken){
      stmt = AspCmpndStmt.parse(s);
      leaveParser("stmt");
      return stmt;
    }else{
      stmt = AspSmallStmtList.parse(s);
      leaveParser("stmt");
      return stmt;
    }
  }
  abstract void prettyPrint();
  abstract RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue;
}
