package no.uio.ifi.asp.runtime;

import java.util.ArrayList;
import java.util.Scanner;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeLibrary extends RuntimeScope {
  private Scanner s = new Scanner(System.in);

  public RuntimeLibrary() {

  // len
  assign("len", new RuntimeFunc("len") {
  @Override
  public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
      checkNumParams(actualParams, 1, "len", where);
      return actualParams.get(0).evalLen(where);
    }
  });

    // Exit
    assign("exit", new RuntimeFunc("exit"){
    @Override
    public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where){
      checkNumParams(actualParams, 1, "exit", where);
      System.out.println("Avslutter programmet...");
      System.exit(1);
      return new RuntimeNoneValue();
    }
  });

  //float
  assign("float", new RuntimeFunc("float"){
    @Override
    public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
      checkNumParams(actualParams, 1, "float", where);
      return new RuntimeFloatValue(actualParams.get(0).getFloatValue("float", where));
    }
  });

  //int
  assign("int", new RuntimeFunc("int"){
    @Override
    public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
      checkNumParams(actualParams, 1, "int", where);
      return new RuntimeIntValue(actualParams.get(0).getIntValue("int", where));
    }
  });

  //print
  assign("print", new RuntimeFunc("print"){
    @Override
    public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
      for (RuntimeValue rt : actualParams ) {
        System.out.println(rt.toString() + " ");
      }
      return new RuntimeNoneValue();
    }
  });


  //range
  assign("range", new RuntimeFunc("range"){
    @Override
    public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
      checkNumParams(actualParams, 2, "range", where);
      ArrayList<RuntimeValue> rtList = new ArrayList<>();

      long v1 = actualParams.get(0).getIntValue("int", where);
      long v2 = actualParams.get(1).getIntValue("int", where);
      if(v1 < v2){
        for (long i = v1 ; i < v2 ; i++ ) {
          rtList.add(new RuntimeIntValue(i));
        }
        return new RuntimeListValue(rtList);

      }else if(v1 == v2){
        rtList.add(new RuntimeIntValue(v1));
        return new RuntimeListValue(rtList);

      }
      return null;
    }
  });

  //input
  assign("input", new RuntimeFunc("input"){
    @Override
    public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
      checkNumParams(actualParams, 1, "input", where);
      System.out.print(actualParams.get(0).getStringValue("input", where));
      return new RuntimeStringValue(s.next());
    }
  });

  //str
  assign("str", new RuntimeFunc("str"){
    @Override
    public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
      checkNumParams(actualParams, 1, "str", where);
      String str = actualParams.get(0).toString();
      return new RuntimeStringValue(str);
    }
  });
  }

  private void checkNumParams(ArrayList<RuntimeValue> actArgs, int nCorrect, String id, AspSyntax where) {
    if (actArgs.size() != nCorrect)
      RuntimeValue.runtimeError("Feil antall parametere: "+id+"!",where);
    }
}
