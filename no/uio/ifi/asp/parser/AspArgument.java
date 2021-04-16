package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspArgument extends AspPrimarySuffix{

  ArrayList<AspExpr> argList = new ArrayList<>();

  AspArgument(int n){
    super(n);
  }

  static AspArgument parse(Scanner s){
    enterParser("argument");
    AspArgument a = new AspArgument(s.curLineNum());
    skip(s, leftParToken);
    while(s.curToken().kind != rightParToken){
        a.argList.add(AspExpr.parse(s));
        if(s.curToken().kind == commaToken) {
          skip(s, commaToken);
        }
    }
    skip(s, rightParToken);
    leaveParser("argument");
    return a;
  }

  @Override
  public void prettyPrint() {
    prettyWrite("(");
    int count = 0;
    if(!argList.isEmpty()){
    for (AspExpr expr : argList ) {
      if(count > 0){
        prettyWrite(", ");
      }
      expr.prettyPrint();
      count++;
    }
  }
    prettyWrite(")");
  }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
      ArrayList<RuntimeValue> aList = new ArrayList<>();

      for (AspExpr expr : argList) {
        aList.add(expr.eval(curScope));
      }
      return new RuntimeListValue(aList);
  }
}
