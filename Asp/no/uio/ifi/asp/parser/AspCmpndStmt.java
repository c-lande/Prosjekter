package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public abstract class AspCmpndStmt extends AspStmt{

  AspCmpndStmt(int i){
    super(i);
  }

  static AspCmpndStmt parse(Scanner s){

    enterParser("compound stmt");
    AspCmpndStmt cs = null;

    if(s.curToken().kind == whileToken){
      cs = AspWhileStmt.parse(s);
      leaveParser("compound stmt");
      return cs;
    }else if(s.curToken().kind == ifToken){
      cs = AspIfStmt.parse(s);
      leaveParser("compound stmt");
      return cs;
    }else if(s.curToken().kind == forToken){
      cs = AspForStmt.parse(s);
      leaveParser("compound stmt");
      return cs;
    }else if(s.curToken().kind == defToken){
      cs = AspFuncDef.parse(s);
      leaveParser("compound stmt");
      return cs;
    }
    return null;
  }
  abstract void prettyPrint();
  abstract RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue;
}
