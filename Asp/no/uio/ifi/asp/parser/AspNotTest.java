package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


class AspNotTest extends AspSyntax {

  AspComparison c;
  boolean not = false;

  AspNotTest(int n){
    super(n);
  }

static AspNotTest parse(Scanner s) {
  enterParser("Not test");
  AspNotTest nt = new AspNotTest(s.curLineNum());
  if(s.curToken().kind == notToken){
    nt.not = true;
    skip(s, notToken);
    }

    nt.c = AspComparison.parse(s);
    leaveParser("Not test");
    return nt;
  }

  @Override
  public void prettyPrint() {
    if(not){
      prettyWrite(" not ");
    }
    c.prettyPrint();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
      RuntimeValue rt = c.eval(curScope);
      if(not){
          rt = rt.evalNot(this);
      }
      return rt;
      }
  }
