package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspSuite extends AspSyntax{

  ArrayList<AspStmt> stmtList = new ArrayList<>();
  AspSmallStmtList asl = null;

    AspSuite(int n){
      super(n);
    }

  static AspSuite parse(Scanner s) {
    enterParser("AspSuite");
    AspSuite as = new AspSuite(s.curLineNum());

    if(s.curToken().kind == newLineToken){
      skip(s, newLineToken);
      skip(s, indentToken);
      while(s.curToken().kind != dedentToken){
        as.stmtList.add(AspStmt.parse(s));
      }
      skip(s, dedentToken);
    }else{
      as.asl = AspSmallStmtList.parse(s);
    }
    leaveParser("AspSuite");
    return as;
    }

    @Override
    public void prettyPrint() {
      if(asl == null){
        prettyWriteLn();
        prettyIndent();
        for (AspStmt as : stmtList ) {
          as.prettyPrint();
        }
        prettyDedent();
      }else{
        asl.prettyPrint();
      }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
      RuntimeValue rt = null;
      if(asl == null){
        for (AspStmt as : stmtList ) {
          rt = as.eval(curScope);
        }
        return rt;
      }else{
        rt = asl.eval(curScope);
      }
      return rt;

    }
  }
