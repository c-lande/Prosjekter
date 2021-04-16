package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspProgram extends AspSyntax {
     ArrayList<AspStmt> stmts = new ArrayList<>();

    AspProgram(int n) {
	super(n);
    }


    public static AspProgram parse(Scanner s) {
	     enterParser("program");
	     AspProgram ap = new AspProgram(s.curLineNum());
	     while (s.curToken().kind != eofToken) {
          //System.out.println(s.curToken().kind);
	        ap.stmts.add(AspStmt.parse(s));
	      }
        skip(s, eofToken);
	      leaveParser("program");
	      return ap;
      }


    @Override
    public void prettyPrint() {
      for(AspStmt s : stmts){
        s.prettyPrint();
        Main.log.finish();
      }
      prettyWrite(TokenKind.eofToken.toString());
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
           for (AspStmt as: stmts) {
               try {
                   as.eval(curScope);
               } catch (RuntimeReturnValue rrv) {
                   RuntimeValue.runtimeError("Runtime returnvalue error!", rrv.lineNum);
               }
           }
           return null;
    }
}
