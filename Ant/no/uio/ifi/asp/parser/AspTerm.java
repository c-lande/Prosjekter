package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspTerm extends AspSyntax{

  ArrayList<AspFactor> fList = new ArrayList<>();
  ArrayList<AspTermOpr> TermOprList = new ArrayList<>();

    AspTerm(int n){
      super(n);
    }

  static AspTerm parse(Scanner s) {
    enterParser("AspTerm");
    AspTerm t = new AspTerm(s.curLineNum());

    while(true){
      t.fList.add(AspFactor.parse(s));
      if(s.isTermOpr()){
        t.TermOprList.add(AspTermOpr.parse(s));
      }else{
        break;
      }

    }
    leaveParser("AspTerm");
    return t;
  }

  @Override
  public void prettyPrint() {
    int count = 0;

    for (AspFactor af : fList ) {
      af.prettyPrint();

      if(count < TermOprList.size()){
        if(TermOprList.get(count) != null){
          AspTermOpr to = TermOprList.get(count);
          to.prettyPrint();

        }
      }
      count++;

    }
  }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
      RuntimeValue rt = fList.get(0).eval(curScope);

		  for (int i = 1; i < fList.size(); i++){
        TokenKind tk = TermOprList.get(i-1).tk;
			  if(tk == TokenKind.plusToken){
          rt = rt.evalAdd(fList.get(i).eval(curScope), this);
			  }
			  else if (tk == TokenKind.minusToken){
          rt = rt.evalSubtract(fList.get(i).eval(curScope), this);

			  }else{
          Main.panic("Illegal term: " + tk);
			}
		}
		return rt;
  }
}
