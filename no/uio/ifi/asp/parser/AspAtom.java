package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public abstract class AspAtom extends AspSyntax{

  AspAtom(int i){
    super(i);
  }

  static AspAtom parse(Scanner s){
    enterParser("atom");
    AspAtom aa = null;

    if(s.curToken().kind == nameToken){
      aa = AspName.parse(s);
    }else if(s.curToken().kind == integerToken){
      aa = AspInteger.parse(s);
    }else if(s.curToken().kind == floatToken){
      aa = AspFloat.parse(s);
    }else if(s.curToken().kind == stringToken){
      aa = AspString.parse(s);
    }else if(s.curToken().kind == trueToken){
      aa = AspBoolean.parse(s);
    }else if(s.curToken().kind == falseToken){
      aa = AspBoolean.parse(s);
    }else if(s.curToken().kind == noneToken){
      aa = AspNone.parse(s);
    }else if(s.curToken().kind == leftBraceToken ){
      aa = AspDictDisplay.parse(s);
    }else if(s.curToken().kind == leftBracketToken){
      aa = AspListDisplay.parse(s);
    }else if(s.curToken().kind == leftParToken){
      aa = AspInner.parse(s);
    }else{
			parserError("Forventet ATOM, men fant: " + s.curToken().kind.toString(), s.curLineNum());
		}
    leaveParser("atom");
    return aa;
  }

  abstract void prettyPrint();
  abstract RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue;
}
