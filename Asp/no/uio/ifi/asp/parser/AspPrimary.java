package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspPrimary extends AspSyntax{

  ArrayList<AspPrimarySuffix> pSuffixes = new ArrayList<>();
	AspAtom aa = null;

	AspPrimary(int n) {
		super(n);
	}

	public static AspPrimary parse(Scanner s) {
		enterParser("primary");

		AspPrimary ap = new AspPrimary(s.curLineNum());
		ap.aa = AspAtom.parse(s);

		while(s.curToken().kind == TokenKind.leftParToken || s.curToken().kind == TokenKind.leftBracketToken){
			ap.pSuffixes.add(AspPrimarySuffix.parse(s));
		}
		leaveParser("primary");
		return ap;
  }

  @Override
  public void prettyPrint() {
       aa.prettyPrint();
       for(AspPrimarySuffix ps : pSuffixes){
         ps.prettyPrint();
       }
     }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    ArrayList<RuntimeValue> exprInArgs = new ArrayList<>();
    RuntimeValue rt = aa.eval(curScope);
    if(!pSuffixes.isEmpty()){
      for (AspPrimarySuffix aps : pSuffixes ) {
        if(aps instanceof AspArgument){
          exprInArgs = aps.eval(curScope).getListValue("primaries", this);
          trace("Kaller paa " + rt + " med parametere " + exprInArgs);
          rt = rt.evalFuncCall(exprInArgs, this);
        }else if(aps instanceof AspSubscription){
          rt = rt.evalSubscription(aps.eval(curScope), this);
        }
      }
    }
    return rt;
  }
}
