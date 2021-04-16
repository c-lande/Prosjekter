package no.uio.ifi.asp.runtime;

import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeStringValue extends RuntimeValue{
  String strValue;

  public RuntimeStringValue(String v){
    strValue = v;
  }

  @Override
  protected String typeName(){
    return "String";
  }
  @Override
  public String toString(){
    return strValue;
  }

  @Override
  public String getStringValue(String what, AspSyntax where) {
    return strValue;
  }

  @Override
  public boolean getBoolValue(String what, AspSyntax where){
    if(strValue == ""){
      return false;
    }else{
      return true;
    }
  }

  @Override
  public double getFloatValue(String what, AspSyntax where){
    return Double.parseDouble(strValue);
  }

  @Override
  public long getIntValue(String what, AspSyntax where){
    return Integer.parseInt(strValue);
  }
  @Override
  public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
    if(v instanceof RuntimeStringValue){
      return new RuntimeBoolValue(strValue.equals(v.getStringValue("==", where)));
    }else{
      runtimeError("Type error: (==)" + typeName(), where);
    }
    return null;
  }

  @Override
  public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
    if(v instanceof RuntimeIntValue) {
      long vl = v.getIntValue("Subscription", where);
      int vi = (int)vl;
      return new RuntimeStringValue(Character.toString(strValue.charAt(vi)));
    } else {
      runtimeError("Type error for Subscription.", where);
    }
    return null;
  }

  @Override
  public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
    if(v instanceof RuntimeStringValue){
      return new RuntimeStringValue(strValue + v.getStringValue("+", where));

    }else{
      runtimeError("Type error: (+)" + typeName(), where) ;
    }
    return null;
  }

  @Override
  public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
    if(v instanceof RuntimeIntValue){
      String newStr = "";
      for(int i = 0; i < v.getIntValue("*", where); i++){
        newStr += strValue;
      }

      return new RuntimeStringValue(newStr);
    }else{
      runtimeError("Type error (*)" + typeName(), where);
    }
    return null;
  }

  @Override
  public RuntimeValue evalLen(AspSyntax where) {
    return new RuntimeIntValue(strValue.length());

  }
  @Override
  public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
    if(v instanceof RuntimeStringValue){
      return new RuntimeBoolValue(strValue.length() < v.getStringValue("<", where).length());
    }else{
      runtimeError("Type error: (<)" + typeName(), where);
    }
    return null;
  }

  @Override
  public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
    if(v instanceof RuntimeStringValue){
      return new RuntimeBoolValue(!strValue.equals(v.getStringValue("!=",where)));
    }else{
      runtimeError("Type error: (!=)" + typeName(), where);
    }
    return null;
  }
  @Override
  public RuntimeValue evalNot(AspSyntax where) {
    if(strValue == ""){
      return new RuntimeBoolValue(true);
    }else{
      return new RuntimeBoolValue(false);
    }
  }
  @Override
	public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where){
    if(v instanceof RuntimeStringValue){
      String str2 = v.getStringValue("<=", where);
      return new RuntimeBoolValue(strValue.length() <= str2.length());
    }
    runtimeError("'<=' udefinert for "+typeName()+"!", where);
		return null;
  }
  @Override
  public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where){
    if(v instanceof RuntimeStringValue){
      String str2 = v.getStringValue(">=", where);
      return new RuntimeBoolValue(strValue.length() >= str2.length());
    }
    runtimeError("'>=' udefinert for "+typeName()+"!", where);
    return null;
  }
  @Override
  public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where){
    if (v instanceof RuntimeStringValue) {
      String strValue2 = v.getStringValue(">", where);
      return new RuntimeBoolValue(strValue.length() > strValue2.length());
    }
    runtimeError("'>' udefinert"+typeName()+"!", where);
    return null;
  }
}
