package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspReturnStmt extends AspSmallStmt {

  AspExpr ex;

  AspReturnStmt(int n) {
	   super(n);
    }

  public static AspReturnStmt parse(Scanner s) {
    enterParser("return stmt");
    AspReturnStmt rs = new AspReturnStmt(s.curLineNum());
    skip(s, returnToken);
    rs.ex = AspExpr.parse(s);
    leaveParser("return stmt");
    return rs;
  }

  @Override
  public void prettyPrint() {
    prettyWrite("return ");
    ex.prettyPrint();
  }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
      RuntimeValue rt = ex.eval(curScope);
      trace("return " + rt.showInfo());
      throw new RuntimeReturnValue(rt, lineNum);
  }
}
