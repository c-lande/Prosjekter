package no.uio.ifi.asp.runtime;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;
import no.uio.ifi.asp.parser.*;
import java.util.ArrayList;
import no.uio.ifi.asp.runtime.*;

public class RuntimeFunc extends RuntimeValue {
    public ArrayList<RuntimeValue> formalParameters = new ArrayList<>();
    AspFuncDef af;
    RuntimeScope funcScope;
    String funcName;

    public RuntimeFunc(AspFuncDef fd, RuntimeScope s, String name){
      af = fd;
      funcScope = s;
      funcName = name;
    }
    public RuntimeFunc(String name){
      funcName = name;
    }

    @Override
    protected String typeName() {
      return "Func";
    }
    @Override
    public String toString(){
      return funcName;
    }

    public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> parameters, AspSyntax where) {
      // (a) Sjekk at antallet aktuelle parametre er det samme som antallet formelle parametre.
      if(parameters == null){
        parameters = new ArrayList<RuntimeValue>();
      }
      int counterFormal = formalParameters.size();
      int counterActual = parameters.size();
      if(counterFormal != counterActual) runtimeError("func error ", where);

      // (b) Opprett et nytt RuntimeScope-objekt. Dette skopets outer skal være det skopet der funksjonen ble deklarert.
      RuntimeScope scope = new RuntimeScope(funcScope);

      // (c) Gå gjennom parameterlisten og tilordne alle de aktuelle parameterverdiene til de formelle parametrene.
      int counter = 0;
      for (RuntimeValue rt : parameters ) {
  			String fp = formalParameters.get(counter).getStringValue("function call", where);
  			scope.assign(fp, rt);
        counter ++;
      }

      // (d) Kall funksjonens runFunction (med det nye skopet som parameter) slik at den kan utføre innmaten av funksjonen.
      try{
        af.runFunction(scope);
      }catch(RuntimeReturnValue rrv){
        return rrv.value;
      }
      return new RuntimeNoneValue();
    }
  }
