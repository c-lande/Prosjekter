package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspFactor extends AspSyntax{

  ArrayList<AspFactorPrefix> fpList = new ArrayList<>();
  ArrayList<AspFactorOpr> foList = new ArrayList<>();
  ArrayList<AspPrimary> priList = new ArrayList<>();

  AspFactor(int n){
    super(n);
  }

  static AspFactor parse(Scanner s){
    enterParser("factor");
    AspFactor f = new AspFactor(s.curLineNum());
    while(true){
      if(s.isTermOpr()){
        f.fpList.add(AspFactorPrefix.parse(s));
      }else{
        f.fpList.add(null);
      }
      f.priList.add(AspPrimary.parse(s));

      if(s.isFactorOpr()){
        f.foList.add(AspFactorOpr.parse(s));
      }else{
        break;
      }
    }
    leaveParser("factor");
    return f;
  }

  @Override
  public void prettyPrint() {
    int count = 0;
    for (AspPrimary ap : priList ) {

      if(count < fpList.size()){
        if(fpList.get(count) != null){
          AspFactorPrefix fp = fpList.get(count);
          fp.prettyPrint();
        }
      }
      ap.prettyPrint();

      if(count < foList.size()){
        if(foList.get(count) != null){
          AspFactorOpr fo = foList.get(count);
          fo.prettyPrint();
        }
      }
      count++;
    }
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    RuntimeValue rt = null;
    if(fpList.get(0) != null){
      TokenKind tk = fpList.get(0).kind;
      if(tk == TokenKind.plusToken){
        rt = priList.get(0).eval(curScope).evalPositive(this);
      }else if(tk == TokenKind.minusToken){
        rt = priList.get(0).eval(curScope).evalNegate(this);
      }
      else{
        Main.panic("Feil tokenkind: " + tk);
      }
    }else{
      rt = priList.get(0).eval(curScope);
    }

    if(!foList.isEmpty()){
      for (int i = 1; i < priList.size() ; i++ ) {
      if(fpList.get(i) != null){
          TokenKind tk = fpList.get(i).kind;
          if(tk == TokenKind.plusToken){
            rt = priList.get(i).eval(curScope).evalPositive(this);
          }else if(tk == TokenKind.minusToken){
            rt = priList.get(i).eval(curScope).evalNegate(this);
          }
          else{
            Main.panic("Feil tokenkind: " + tk);
          }
        }

        TokenKind tk = foList.get(i-1).kind;
        if(tk == TokenKind.astToken){
          rt = rt.evalMultiply(priList.get(i).eval(curScope), this);
        }else if(tk == TokenKind.percentToken){
          rt = rt.evalModulo(priList.get(i).eval(curScope), this);
        }else if(tk == TokenKind.slashToken){
          rt = rt.evalDivide(priList.get(i).eval(curScope), this);
        }else if(tk == TokenKind.doubleSlashToken){
          rt = rt.evalIntDivide(priList.get(i).eval(curScope), this);
        }else{
          Main.panic("Feil tokenkind: " + tk);
        }
      }
    }
    return rt;
  }
}
