package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspFactorOpr extends AspSyntax {
  TokenKind kind;
  String token;

  AspFactorOpr(int n){
    super(n);
  }

static AspFactorOpr parse(Scanner s) {
  enterParser("factor opr");
  AspFactorOpr fp = new AspFactorOpr(s.curLineNum());
  fp.kind = s.curToken().kind;

  if(s.curToken().kind == astToken){
    skip(s, astToken);
    fp.token = "*";
    leaveParser("factor opr");
    return fp;
  }else if(s.curToken().kind == percentToken){
    skip(s, percentToken);
    fp.token = "%";
    leaveParser("factor opr");
    return fp;
  }else if(s.curToken().kind == doubleSlashToken){
    skip(s, doubleSlashToken);
    fp.token = "//";
    leaveParser("factor opr");
    return fp;
  }
  else if(s.curToken().kind == slashToken){
    skip(s, slashToken);
    fp.token = "/";
    leaveParser("factor opr");
    return fp;
  }

  return null;
  }

  @Override
  public void prettyPrint() {
      prettyWrite(" "+token+ " ");
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    return null;
  }
}
