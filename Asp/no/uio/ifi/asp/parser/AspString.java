package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspString extends AspAtom{
  String stringLiteral;

  AspString(int n){
    super(n);
  }

  static AspString parse(Scanner s){
    enterParser("string");
    AspString as = new AspString(s.curLineNum());
    as.stringLiteral = s.curToken().stringLit;
    skip(s, stringToken);
    leaveParser("string");
    return as;
  }
  @Override
  public void prettyPrint() {
    prettyWrite('"'+stringLiteral+'"');
  }


  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    return new RuntimeStringValue(stringLiteral);
  }
}
