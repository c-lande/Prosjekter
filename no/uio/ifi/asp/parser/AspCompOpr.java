package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspCompOpr extends AspSyntax{
  TokenKind kind;
  String token;

  AspCompOpr(int n) {
	   super(n);
  }

  static AspCompOpr parse(Scanner s){
    enterParser("comparison operator");

    AspCompOpr co = new AspCompOpr(s.curLineNum());
    co.kind = s.curToken().kind;


    if(s.curToken().kind == lessToken){
      skip(s, lessToken);
      co.token = "<";
      leaveParser("comparison operator");
      return co;
    }
    else if(s.curToken().kind == doubleEqualToken){
      skip(s, doubleEqualToken);
      co.token = "==";
      leaveParser("comparison operator");
      return co;
    }else if(s.curToken().kind == greaterEqualToken){
      skip(s, greaterEqualToken);
      co.token = ">=";
      leaveParser("comparison operator");
      return co;
    }
    else if(s.curToken().kind == greaterToken){
      skip(s, greaterToken);
      co.token = ">";
      leaveParser("comparison operator");
      return co;
    }else if(s.curToken().kind == notEqualToken){
      skip(s, notEqualToken);
      co.token = "!=";
      leaveParser("comparison operator");
      return co;
    }
    else if(s.curToken().kind == lessEqualToken){
      skip(s, lessEqualToken);
      co.token = "<=";
      leaveParser("comparison operator");
      return co;
    }
    return null;
  }

  @Override
  public void prettyPrint() {
    prettyWrite(token);
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
   return null;
  }
}
