package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspAssignment extends AspSmallStmt{
  ArrayList<AspSubscription> subscriptions = new ArrayList<>();
  AspName n;
  AspExpr ex;

  AspAssignment(int n){
    super(n);
  }

  static AspAssignment parse(Scanner s){
    enterParser("assignment");
    AspAssignment a = new AspAssignment(s.curLineNum());
    a.n = AspName.parse(s);
    while(s.curToken().kind != equalToken){
      a.subscriptions.add(AspSubscription.parse(s));
    }
    skip(s, equalToken);
    a.ex = AspExpr.parse(s);
    leaveParser("assignment");
    return a;
  }

  @Override
  public void prettyPrint() {
    n.prettyPrint();
    for (AspSubscription s : subscriptions ) {
      s.prettyPrint();
    }
    prettyWrite(" = ");
    ex.prettyPrint();
  }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
      RuntimeValue rt = ex.eval(curScope);
      RuntimeValue rt2 = null;

      if(!subscriptions.isEmpty()){
        rt2 = n.eval(curScope);
        for(int i = 0; i<subscriptions.size()-1; i++){
          rt2 = rt2.evalSubscription(subscriptions.get(i).eval(curScope), this);
        }
        RuntimeValue lastPos = subscriptions.get(subscriptions.size()-1).eval(curScope);
        rt2.evalAssignElem(lastPos, rt, this);
        trace(n.name +"[" + lastPos + "] = " + rt);
        return rt;
      }
      else{
        curScope.assign(n.name, rt);
        trace(n.name + " = " + rt.toString());
        return rt;

    }
  }
}
