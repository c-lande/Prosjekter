package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class RuntimeDictValue extends RuntimeValue{
  Map<String, RuntimeValue> dictionary = new HashMap<>();

  public RuntimeDictValue(HashMap<String, RuntimeValue> v) {
		dictionary = v;
	}

  @Override
   protected String typeName() {
     return "dict";
   }

   @Override
     public String showInfo(){
         return toString();
     }

  @Override
	public String toString() {
		String strSet = "";
    int counter = 0;

    strSet = strSet + "{";
    for (Map.Entry<String, RuntimeValue> entry : dictionary.entrySet()) {
        String keyword = entry.getKey();
        RuntimeValue val = entry.getValue();
        if(counter < dictionary.size()-1){
            strSet = strSet + keyword + val.showInfo() + ", ";
        }else{
            strSet = strSet + keyword + val.showInfo();
        }
        counter++;
    }
    strSet = strSet + '}';
    return strSet;
	}

   @Override
   public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where){
     if(v instanceof RuntimeStringValue){
       String keyword = v.getStringValue("evalSubscription", where);
       if(dictionary.get(keyword) == null){
         runtimeError("Keyword " + keyword + "ikke i ", where);
       }else{
         return dictionary.get(keyword);
       }
     }else{
       runtimeError("Type error for dict", where);
     }
     return null;
   }

   @Override
   public RuntimeValue evalNot(AspSyntax where) {
     return new RuntimeBoolValue(false);
   }

   @Override
   public boolean getBoolValue(String what, AspSyntax where){
     if(dictionary.isEmpty()){
       return false;
     }
     return true;
   }

   @Override
   public void evalAssignElem(RuntimeValue v, RuntimeValue v2, AspSyntax where){
     String keyword = v.getStringValue("assign dict", where);
     dictionary.put(keyword, v2);
   }
}
