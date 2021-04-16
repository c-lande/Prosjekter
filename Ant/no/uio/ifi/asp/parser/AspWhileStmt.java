package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


class AspWhileStmt extends AspCmpndStmt{
  AspExpr ae;
  AspSuite as;

  AspWhileStmt(int n){
    super(n);
  }

  static AspWhileStmt parse(Scanner s){
    AspWhileStmt ws = new AspWhileStmt(s.curLineNum());

    enterParser("while statement");
    skip(s, whileToken);
    ws.ae = AspExpr.parse(s);
    skip(s, colonToken);
    ws.as = AspSuite.parse(s);
    leaveParser("while statement");

    return ws;
  }
  @Override
  public void prettyPrint() {
    prettyWrite("while ");
    ae.prettyPrint();
    prettyWrite(": ");
    as.prettyPrint();
  }


  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    while (true) {
      RuntimeValue rt = ae.eval(curScope);
      if (!rt.getBoolValue("while",this)){
        trace("while True: ...");
        break;
      }
      trace("while False:");
      rt = as.eval(curScope);
      }
      return null;
  }
}
