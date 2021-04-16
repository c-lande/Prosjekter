package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.HashMap;

public class AspDictDisplay extends AspAtom{
  ArrayList<AspExpr> exprList = new ArrayList<>();
  ArrayList<AspString> strs = new ArrayList<>();

  AspDictDisplay(int n){
    super(n);
  }

  public static AspDictDisplay parse(Scanner s) {
  		enterParser("dict display");

  		AspDictDisplay dd = new AspDictDisplay(s.curLineNum());

  		skip(s, TokenKind.leftBraceToken);

  		while(s.curToken().kind != TokenKind.rightBraceToken){
  			dd.strs.add(AspString.parse(s));
  			skip(s, TokenKind.colonToken);
  			dd.exprList.add(AspExpr.parse(s));

  			if(s.curToken().kind != TokenKind.commaToken){
          break;
  			}else{
          skip(s, TokenKind.commaToken);
  			}
  		}

  		skip(s, TokenKind.rightBraceToken);
  		leaveParser("dict display");
  		return dd;
  	}

  @Override
  public void prettyPrint() {
    prettyWrite("{");
    int counter = 0;
    for (AspString as : strs ) {
      as.prettyPrint();
      prettyWrite(":");
      exprList.get(counter).prettyPrint();
      counter++;
    }
    prettyWrite("}");
  }


  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    HashMap<String, RuntimeValue> hMap = new HashMap<>();

    if(!strs.isEmpty()){
      for (int i = 0; i < strs.size() ; i++ ) {
        hMap.put(strs.get(i).eval(curScope).toString(), exprList.get(i).eval(curScope));
      }
    }
    return new RuntimeDictValue(hMap);
  }
}
