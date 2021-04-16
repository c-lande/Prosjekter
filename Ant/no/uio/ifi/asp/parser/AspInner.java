package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspInner extends AspAtom{
  AspExpr aspEx;

  AspInner(int n){
    super(n);
  }

  static AspInner parse(Scanner s){
    enterParser("AspInner expression");
    skip(s, leftParToken);
    AspInner inn = new AspInner(s.curLineNum());
    inn.aspEx = AspExpr.parse(s);
    skip(s, rightParToken);
    leaveParser("AspInner expression");
    return inn;
  }

  @Override
  public void prettyPrint() {
    prettyWrite("(");
    aspEx.prettyPrint();
    prettyWrite(")");
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
   return aspEx.eval(curScope);
  }
}
