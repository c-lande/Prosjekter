package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspIfStmt extends AspCmpndStmt{

  ArrayList<AspExpr> exprList = new ArrayList<>();
  ArrayList<AspSuite> suiteList = new ArrayList<>();

  AspIfStmt(int n){
    super(n);
  };

  public static AspIfStmt parse(Scanner s){
    enterParser("if statement");
    AspIfStmt is = new AspIfStmt(s.curLineNum());
    skip(s, ifToken);
    is.exprList.add(AspExpr.parse(s));
    skip(s, colonToken);
    is.suiteList.add(AspSuite.parse(s));

    while(s.curToken().kind == elifToken){
      skip(s, elifToken);
      is.exprList.add(AspExpr.parse(s));
      skip(s, colonToken);
      is.suiteList.add(AspSuite.parse(s));
    }

    if(s.curToken().kind == elseToken){
      skip(s, elseToken);
      skip(s, colonToken);
      is.suiteList.add(AspSuite.parse(s));
    }
    leaveParser("if statement");
    return is;
    }

    @Override
    public void prettyPrint() {
      prettyWrite("if ");
      exprList.get(0).prettyPrint();
      prettyWrite(": ");
      suiteList.get(0).prettyPrint();

      if(exprList.size() > 1){
        for (int i = 0; i < exprList.size() ; i++ ) {
          prettyWrite("elif ");
          exprList.get(i).prettyPrint();
          prettyWrite(": ");
          suiteList.get(i).prettyPrint();
        }
      }
      if(suiteList.size() > exprList.size()){
        prettyWrite("else ");
        prettyWrite(": ");
        suiteList.get(suiteList.size()-1).prettyPrint();
      }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
      RuntimeValue rt = null;
      for(int i = 0; i < exprList.size(); i++){
        rt = exprList.get(i).eval(curScope);
        if(rt.getBoolValue("if test", this)){
          trace("If true alt #1");
          AspSuite as = suiteList.get(i);
          return as.eval(curScope);
          }
        }
        if(suiteList.size() > exprList.size()){
          trace("else: ...");
          return suiteList.get(suiteList.size()-1).eval(curScope);
      }
      return rt;
    }
}
