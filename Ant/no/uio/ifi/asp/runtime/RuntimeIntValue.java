package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeIntValue extends RuntimeValue {
  long intValue;

  public RuntimeIntValue(long v) {
    intValue = v;
  }
  @Override
  protected String typeName(){
    return "int";
  }
  @Override
  public String toString(){
    return "" +intValue;
  }

  @Override
  public String getStringValue(String what, AspSyntax where) {
    return Long.toString(intValue);
  }

  @Override
  public long getIntValue(String what, AspSyntax where) {
    return intValue;
  }

  @Override
  public double getFloatValue(String what, AspSyntax where) {
    return (double)intValue;
  }

  @Override
  public boolean getBoolValue(String what, AspSyntax where){
    if(intValue == 0){
      return false;
    }else{
      return true;
    }
  }

  @Override
  public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where){
    if(v instanceof RuntimeIntValue){
      return new RuntimeIntValue(intValue + v.getIntValue("+", where));
    }else if(v instanceof RuntimeFloatValue){
      return new RuntimeFloatValue(intValue + v.getFloatValue("+", where));
    }else{
      runtimeError("Type error: (+)" + typeName(), where);
    }
    return null;
  }

  @Override
  public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where){
    if(v instanceof RuntimeIntValue){
      return new RuntimeIntValue(intValue / v.getIntValue("/", where));
    } else if(v instanceof RuntimeFloatValue){
      return new RuntimeFloatValue(intValue / v.getFloatValue("/", where));
    }else{
      runtimeError("Type error: (/)" + typeName(), where);
    }
    return null;
  }

  @Override
  public RuntimeValue evalLess(RuntimeValue v, AspSyntax where){
    if(v instanceof RuntimeIntValue){
      return new RuntimeBoolValue(intValue < v.getIntValue("<", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeBoolValue(intValue < v.getFloatValue("<", where));
    }else{
      runtimeError("Type error: (<)" + typeName(), where);
    }
    return null;
  }

  @Override
  public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where){
    if(v instanceof RuntimeIntValue){
      return new RuntimeBoolValue(intValue == v.getIntValue("==", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeBoolValue(intValue == v.getFloatValue("==", where));
    }else if(v instanceof RuntimeNoneValue){
      return new RuntimeBoolValue(false);
    }else{
      runtimeError("Type error: (==)", where);
    }
    return null;
  }

  @Override
  public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where){
    if(v instanceof RuntimeIntValue){
      return new RuntimeBoolValue(intValue >= v.getIntValue(">=", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeBoolValue(intValue >= v.getFloatValue(">=", where));
    }else{
      runtimeError("Type error: (>=)" + typeName(), where);
    }
    return null;
  }

  @Override
  public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where){
    if(v instanceof RuntimeIntValue){
      return new RuntimeIntValue(Math.floorMod(intValue, v.getIntValue("%", where)));
    }
    else if(v instanceof RuntimeFloatValue){
      double floatVal = v.getFloatValue("%", where);
      return new RuntimeFloatValue(intValue-floatVal*Math.floor(intValue/floatVal));
    }else{
      runtimeError("Type error: (%)" + typeName(), where);
    }
    return null;
  }

  @Override
  public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where){
    if(v instanceof RuntimeIntValue){
      return new RuntimeIntValue(Math.floorDiv(intValue, v.getIntValue("//", where)));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeFloatValue(Math.floor(intValue / v.getFloatValue("//", where)));
    }else{
      runtimeError("Type error: (//)" + typeName(), where);
    }
    return null;
  }

  @Override
  public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where){
    if(v instanceof RuntimeIntValue){
      return new RuntimeBoolValue(intValue <= v.getIntValue("<=", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeBoolValue(intValue <= v.getFloatValue("<=", where));
    }else{
      runtimeError("Type error: (<=)" + typeName(), where);
    }
    return null;
  }


  @Override
  public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where){
    if(v instanceof RuntimeIntValue){
      return new RuntimeIntValue(intValue * v.getIntValue("*", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeFloatValue(intValue * v.getFloatValue("*", where));
    }else{
      runtimeError("Type error: (*)" + typeName(), where);
    }
    return null;
  }

  @Override
  public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where){
    if(v instanceof RuntimeIntValue){
      return new RuntimeBoolValue(intValue > v.getIntValue(">", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeBoolValue(intValue > v.getFloatValue(">", where));
    }else{
      runtimeError("Type error: (>)" + typeName(), where);
    }
    return null;
  }

  @Override
  public RuntimeValue evalNegate(AspSyntax where){
    return new RuntimeIntValue(-1 * intValue);
  }

  @Override
  public RuntimeValue evalNot(AspSyntax where) {
    return new RuntimeBoolValue(false);
  }

  @Override
  public RuntimeValue evalPositive(AspSyntax where){
    return new RuntimeIntValue(intValue);
  }

  @Override
  public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where){
    if(v instanceof RuntimeIntValue){
      return new RuntimeBoolValue(intValue != v.getIntValue("!=", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeBoolValue(intValue != v.getFloatValue("!=", where));
    }else{
      runtimeError("Type error: (!=)" + typeName(), where);
    }
    return null;
  }



  @Override
  public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where){
    if(v instanceof RuntimeIntValue){
      return new RuntimeIntValue(intValue - v.getIntValue("-", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeFloatValue(intValue - v.getFloatValue("-", where));
    }else{
      runtimeError("Type error: (-)" + typeName(), where);
    }
    return null;
  }
}
