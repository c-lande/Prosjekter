package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


public class AspSmallStmtList extends AspStmt {

  ArrayList<AspSmallStmt> stmtList = new ArrayList<>();
  Boolean semicolon = false;
  static int cSemicolon = 0;

  AspSmallStmtList(int n) {
	   super(n);
    }

  static AspSmallStmtList parse(Scanner s) {
    enterParser("small stmt list");
    AspSmallStmtList smallStmtList = new AspSmallStmtList(s.curLineNum());

	   while(s.curToken().kind != newLineToken){
       smallStmtList.stmtList.add(AspSmallStmt.parse(s));
		   if(s.curToken().kind == semicolonToken){
         skip(s, semicolonToken);
         cSemicolon++;
		   }
	   }
     skip(s, newLineToken);
     leaveParser("small stmt list");
     return smallStmtList;
  }

  @Override
    public void prettyPrint(){
      for (int i = 0; i < stmtList.size(); i++) {
      	 stmtList.get(i).prettyPrint();
  		     if(i == stmtList.size()-1){
             if(stmtList.size() == cSemicolon){
                prettyWrite("; ");
                  }
  		            }else{
  			            prettyWrite("; ");
  		            }
              }
      prettyWriteLn();
    }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    RuntimeValue rt = null;
    for (AspSmallStmt ss : stmtList) {
       rt = ss.eval(curScope);
    }
    return rt;
  }
}
