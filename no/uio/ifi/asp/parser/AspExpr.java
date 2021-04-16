package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspExpr extends AspSyntax {
    ArrayList<AspAndTest> andList = new ArrayList<>();

    AspExpr(int n) {
      super(n);
    }

  public static AspExpr parse(Scanner s) {
	   enterParser("expr");

    	AspExpr ae = new AspExpr(s.curLineNum());
      while(true){
        ae.andList.add(AspAndTest.parse(s));
        if(s.curToken().kind != TokenKind.orToken){
          break;
        }
        else {
          skip(s, TokenKind.orToken);
        }
      }

    	leaveParser("expr");
    	return ae;
    }


    @Override
    public void prettyPrint() {
    int counter = 0;

    for (AspAndTest at : andList) {
      if(counter > 0){
        prettyWrite(" or ");
      }
      at.prettyPrint();
      counter++;
      }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
      RuntimeValue rt = andList.get(0).eval(curScope);
      for(int i = 1; i < andList.size(); i++){
        if(rt.getBoolValue("expr", this)){
          return rt;
        }
        rt = andList.get(i).eval(curScope);
      }
      return rt;
    }
}
