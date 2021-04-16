package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


class AspComparison extends AspSyntax{

  ArrayList<AspTerm> tList = new ArrayList<>();
  ArrayList<AspCompOpr> compOprList = new ArrayList<>();

  AspComparison(int n) {
	   super(n);
  }

  static AspComparison parse(Scanner s){
      enterParser("comparison");
      AspComparison c = new AspComparison(s.curLineNum());

      while(true){
        c.tList.add(AspTerm.parse(s));
        if(s.isCompOpr()){
          c.compOprList.add(AspCompOpr.parse(s));
        }else{
          break;
        }
      }
      leaveParser("comparison");
      return c;
    }

  @Override
  void prettyPrint(){
    int count = 0;

    if(compOprList.size() == 0){
      tList.get(0).prettyPrint();
    }else{
      for (AspTerm at : tList ) {
        at.prettyPrint();
        if(count < compOprList.size()){
          if(compOprList.get(count) != null){
            AspCompOpr co = compOprList.get(count);
            co.prettyPrint();
          }
        }
        count++;
        }
      }
    }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    RuntimeValue rt = tList.get(0).eval(curScope);
    for (int i = 1; i < tList.size() ; i++ ) {
      rt = tList.get(i-1).eval(curScope);
      TokenKind tk = compOprList.get(i-1).kind;

      if(tk == TokenKind.lessToken){
        rt = rt.evalLess(tList.get(i).eval(curScope), this);
      }
      else if(tk == TokenKind.greaterToken){
        rt = rt.evalGreater(tList.get(i).eval(curScope), this);
      }
      else if(tk == TokenKind.notEqualToken){
        rt = rt.evalNotEqual(tList.get(i).eval(curScope), this);
      }
      else if(tk == TokenKind.greaterEqualToken){
        rt = rt.evalGreaterEqual(tList.get(i).eval(curScope), this);
      }else if(tk == TokenKind.lessEqualToken){
        rt = rt.evalLessEqual(tList.get(i).eval(curScope), this);
      }
      else if(tk == TokenKind.doubleEqualToken){
        rt = rt.evalEqual(tList.get(i).eval(curScope), this);
      }
      else{
        Main.panic("Finner ikke comp opr: " + tk);
      }
      if(!rt.getBoolValue("comparison", this)){
        return rt;
      }
    }
    return rt;
  }
}
