package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;

import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspForStmt extends AspCmpndStmt{
  AspName an;
  AspExpr ae;
  AspSuite as;

  AspForStmt(int n){
    super(n);
  }

  static AspForStmt parse(Scanner s){
    enterParser("for stmt");
    AspForStmt fs = new AspForStmt(s.curLineNum());
    skip(s, forToken);
    fs.an = AspName.parse(s);
    skip(s, inToken);
    fs.ae = AspExpr.parse(s);
    skip(s, colonToken);
    fs.as = AspSuite.parse(s);
    leaveParser("for stmt");
    return fs;
  }
  @Override
  public void prettyPrint() {
    prettyWrite("for ");
    an.prettyPrint();
    prettyWrite(" in ");
    ae.prettyPrint();
    prettyWrite(": ");
    as.prettyPrint();
  }


  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
      RuntimeValue rt = ae.eval(curScope);

      if(rt instanceof RuntimeListValue){
          ArrayList<RuntimeValue> exprList = rt.getListValue("for", this);
          for (RuntimeValue expr : exprList) {
            curScope.assign(an.name, expr);
            rt = as.eval(curScope);
          }
      }else{
        RuntimeValue.runtimeError("Expr i for loop er ikke en liste.", this);
      }

      return rt;
  }
}
