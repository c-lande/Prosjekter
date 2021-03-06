package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeFloatValue extends RuntimeValue {
  double floatValue;

  public RuntimeFloatValue(double v) {
    floatValue = v;
  }
  @Override
  protected String typeName(){
    return "float";
  }
  @Override
  public String toString(){
    return "" +floatValue;
  }

  @Override
  public String getStringValue(String what, AspSyntax where) {
    return ""+floatValue;
  }


  @Override
  public double getFloatValue(String what, AspSyntax where) {
    return floatValue;
  }

  @Override
  public boolean getBoolValue(String what, AspSyntax where){
    if(floatValue == 0.0){
      return false;
    }else{
      return true;
    }
  }

  @Override
  public RuntimeValue evalNot(AspSyntax where) {
    return new RuntimeBoolValue(false);
  }

  @Override
  public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where){
    if(v instanceof RuntimeFloatValue){
      return new RuntimeFloatValue(floatValue + v.getFloatValue("+", where));
    }else if(v instanceof RuntimeIntValue){
      return new RuntimeFloatValue(floatValue +  v.getFloatValue("+", where));
    }else{
      runtimeError("Type error: (+)" + typeName(), where);
    }
    return null;
  }

  @Override
  public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where){
    if (v instanceof RuntimeIntValue) {
       return new RuntimeFloatValue(floatValue / v.getIntValue("/", where));
     }
     else if(v instanceof RuntimeFloatValue){
       return new RuntimeFloatValue(floatValue /  v.getFloatValue("/", where));
     }else{
      runtimeError("Type error: (/)" + typeName(), where);
    }
    return null;
  }

  @Override
  public RuntimeValue evalLess(RuntimeValue v, AspSyntax where){
    if(v instanceof RuntimeFloatValue){
      return new RuntimeBoolValue(floatValue < v.getFloatValue("<", where));
    }
    else if(v instanceof RuntimeIntValue){
      return new RuntimeBoolValue(floatValue < v.getIntValue("<", where));
    }else{
      runtimeError("Type error: (<)" + typeName(), where);
    }
    return null;
  }

  @Override
  public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where){
    if(v instanceof RuntimeFloatValue){
      return new RuntimeBoolValue(floatValue == v.getFloatValue("==", where));
    }
    else if(v instanceof RuntimeIntValue){
      return new RuntimeBoolValue(floatValue == v.getIntValue("==", where));
    }
    else if(v instanceof RuntimeNoneValue){
      return new RuntimeBoolValue(false);
    }else{
      runtimeError("Type error: (==)", where);
    }
    return null;
  }

  @Override
  public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where){
    if(v instanceof RuntimeFloatValue){
      return new RuntimeBoolValue(floatValue > v.getFloatValue(">", where));
    }
    else if(v instanceof RuntimeIntValue){
      return new RuntimeBoolValue(floatValue > v.getIntValue(">", where));
    }else{
      runtimeError("Type error: (>)" + typeName(), where);
    }
    return null;
  }

  @Override
  public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where){
    if(v instanceof RuntimeFloatValue){
      return new RuntimeBoolValue(floatValue >= v.getFloatValue(">=", where));
    }
    else if(v instanceof RuntimeIntValue){
      return new RuntimeBoolValue(floatValue >= v.getIntValue(">=", where));
    }else{
      runtimeError("Type error: (>=)" + typeName(), where);
    }
    return null;
  }

  @Override
  public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where){
    if (v instanceof RuntimeIntValue) {
       return new RuntimeFloatValue(floatValue / v.getIntValue("//", where));
     }
     else if(v instanceof RuntimeFloatValue){
       return new RuntimeFloatValue(floatValue / v.getFloatValue("//", where));
     }else{
      runtimeError("Type error: (//)" + typeName(), where);
    }
    return null;
  }

  @Override
  public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where){
    if(v instanceof RuntimeFloatValue){
      return new RuntimeBoolValue(floatValue <= v.getFloatValue("<=", where));
    }
    else if(v instanceof RuntimeIntValue){
      return new RuntimeBoolValue(floatValue <= v.getIntValue("<=", where));
    }else{
      runtimeError("Type error: (<=)" + typeName(), where);
    }
    return null;
  }

  @Override
  public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where){
    if (v instanceof RuntimeIntValue) {
     long i = v.getIntValue("%", where);
     return new RuntimeFloatValue(floatValue - i * Math.floor(floatValue/i));
   }
   else if(v instanceof RuntimeFloatValue){
     double f = v.getFloatValue("%", where);
     return new RuntimeFloatValue(floatValue - f * Math.floor(floatValue/f));
   }else{
      runtimeError("Type error: (%)" + typeName(), where);
    }
    return null;
  }

  @Override
  public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where){
    if (v instanceof RuntimeIntValue) {
      return new RuntimeFloatValue(floatValue * v.getIntValue("*", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeFloatValue(floatValue * v.getFloatValue("*", where));
    }else{
      runtimeError("Type error: (*)" + typeName(), where);
    }
    return null;
  }

  @Override
  public RuntimeValue evalNegate(AspSyntax where){
    return new RuntimeFloatValue(-1 * floatValue);
  }

  @Override
  public RuntimeValue evalPositive(AspSyntax where){
    return new RuntimeFloatValue(floatValue);
  }

  @Override
  public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where){
    if (v instanceof RuntimeIntValue) {
       return new RuntimeBoolValue(floatValue != v.getIntValue("!=", where));
     }
     else if(v instanceof RuntimeFloatValue){
       return new RuntimeBoolValue(floatValue != v.getFloatValue("!=", where));
     }else{
      runtimeError("Type error: (!=)", where);
    }
    return null;
  }

  @Override
  public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where){
    if (v instanceof RuntimeIntValue) {
     return new RuntimeFloatValue(floatValue - v.getIntValue("-", where));
   }
   else if(v instanceof RuntimeFloatValue){
     return new RuntimeFloatValue(floatValue -  v.getFloatValue("-", where));
   }else{
      runtimeError("Type error: (-)" + typeName(), where);
    }
    return null;
  }
}
