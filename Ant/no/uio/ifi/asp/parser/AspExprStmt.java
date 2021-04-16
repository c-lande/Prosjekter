package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspExprStmt extends AspSmallStmt {
    AspExpr ex;

  AspExprStmt(int n) {
	   super(n);
    }

  public static AspExprStmt parse(Scanner s) {
	   enterParser("expr stmt");
	   AspExprStmt ae =  new AspExprStmt(s.curLineNum());

     ae.ex = AspExpr.parse(s);
	   leaveParser("expr stmt");
	   return ae;
  }

  @Override
  public void prettyPrint() {
    ex.prettyPrint();
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    RuntimeValue rt = ex.eval(curScope);
    trace(rt.showInfo());
    return rt;
  }
}
