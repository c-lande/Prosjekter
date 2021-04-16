package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

abstract class AspSmallStmt extends AspSyntax{

  AspSmallStmt(int n){
    super(n);
  }

  static AspSmallStmt parse(Scanner s){
    enterParser("Small statement");

    if(s.curToken().kind == nameToken){
      if(s.anyEqualToken()){
        AspSmallStmt ss = AspAssignment.parse(s);
        leaveParser("small statement");
        return ss;

      }else{
        AspSmallStmt ss = AspExprStmt.parse(s);
        leaveParser("small statement");
        return ss;
      }
  }
  else if(s.curToken().kind == passToken){
    AspSmallStmt ss = AspPassStmt.parse(s);
    leaveParser("small statement");
    return ss;

    }
    else if(s.curToken().kind == returnToken){
      AspSmallStmt ss = AspReturnStmt.parse(s);
      leaveParser("small statement");
      return ss;
  }
  return null;
}
  abstract void prettyPrint();
	abstract RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue;
}
