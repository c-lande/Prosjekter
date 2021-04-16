package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspBoolean extends AspAtom{
  boolean b;

  AspBoolean(int n){
    super(n);
  }

  static AspBoolean parse(Scanner s){

    enterParser("boolean");
    AspBoolean ab = new AspBoolean(s.curLineNum());

    if(s.curToken().kind == trueToken){
    ab.b = true;
    skip(s, trueToken);
    leaveParser("boolean");
    return ab;

  }else if(s.curToken().kind == falseToken){
    ab.b = false;
    skip(s, falseToken);
    leaveParser("boolean");
    return ab;
    }
    return null;
  }

  @Override
  public void prettyPrint() {
    if(b){
      prettyWrite("true");
    }else{
      prettyWrite("false");
    }
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    return new RuntimeBoolValue(b);
  }
}
