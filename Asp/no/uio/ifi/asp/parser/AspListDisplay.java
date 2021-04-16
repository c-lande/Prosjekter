package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspListDisplay extends AspAtom{
  ArrayList<AspExpr> exprList = new ArrayList<>();

  AspListDisplay(int n){
    super(n);
  }

  static AspListDisplay parse(Scanner s){
    enterParser("list display");
    skip(s, leftBracketToken);
    AspListDisplay AspListDisplay = new AspListDisplay(s.curLineNum());
    if(s.curToken().kind != rightBracketToken){
      while(true){
        AspListDisplay.exprList.add(AspExpr.parse(s));
        if(s.curToken().kind != commaToken){
           break;
         }
      skip(s, commaToken);
      }
    }
    skip(s, rightBracketToken);
    leaveParser("list display");
    return AspListDisplay;
  }

  @Override
  public void prettyPrint() {
    prettyWrite("[");
    int count = 0;
    for (AspExpr ae : exprList ) {
      if(count > 0){
        prettyWrite(", ");
      }
      ae.prettyPrint();
      count++;
    }
    prettyWrite("]");
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
  	ArrayList<RuntimeValue> rtList = new ArrayList<>();

  	for (AspExpr ae : exprList) {
  		rtList.add(ae.eval(curScope));
  	}
  	return new RuntimeListValue(rtList);
  }
}
