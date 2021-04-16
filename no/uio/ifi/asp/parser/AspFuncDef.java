package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspFuncDef extends AspCmpndStmt{

  ArrayList<AspName> nameList = new ArrayList<>();
  AspName n;
  AspSuite suite;


  AspFuncDef(int n){
    super(n);
  }

  static AspFuncDef parse(Scanner s){
    enterParser("function def");
    AspFuncDef fd = new AspFuncDef(s.curLineNum());
    skip(s, defToken);
    fd.n = AspName.parse(s);
    skip(s, leftParToken);
    while(s.curToken().kind != rightParToken){
      fd.nameList.add(AspName.parse(s));
      if(s.curToken().kind != commaToken){
        break;
      }
      skip(s, commaToken);
    }
    skip(s,rightParToken);
    skip(s, colonToken);

    fd.suite = AspSuite.parse(s);
    leaveParser("Function def");
    return fd;
    }

    @Override
    public void prettyPrint() {
      prettyWrite("def ");
      n.prettyPrint();
      prettyWrite("(");

      int count = 0;
      for (AspName an : nameList ) {
        an.prettyPrint();
        count++;
        if(count == nameList.size()){
          break;
        }
        prettyWrite(", ");
      }
      prettyWrite(")");
      prettyWrite(":");
      suite.prettyPrint();

    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
      RuntimeFunc rtf = new RuntimeFunc(this, curScope, n.name);
      trace("def " + n.name);


      for (AspName an : nameList) {
        RuntimeValue rt = new RuntimeStringValue(an.name);
        rtf.formalParameters.add(rt);
      }
      curScope.assign(n.name, rtf);
      return rtf;
    }

    public void runFunction(RuntimeScope curScope) throws RuntimeReturnValue {
      suite.eval(curScope);
    }
  }
