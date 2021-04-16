package no.uio.ifi.asp.runtime;

import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;


public class RuntimeListValue extends RuntimeValue{

  ArrayList<RuntimeValue> list = new ArrayList<>();

  public RuntimeListValue (ArrayList<RuntimeValue> listparam){
    list = listparam;
  }

  public ArrayList<RuntimeValue> getList(String what, AspSyntax Where){
    return getList();
  }
  public ArrayList<RuntimeValue> getList(){
    return list;
  }

  @Override
  protected String typeName() {
    return "list";
  }

  @Override
  public RuntimeValue evalLen(AspSyntax where){
    return new RuntimeIntValue(list.size());
  }

  public String toString() {
      String listS= "";
      int counter = 0;
      for(RuntimeValue rv : list){
        if(list.size()-1 == counter){
          listS += rv.toString();
          break;
        }
        listS += rv.toString() + ", ";
        counter++;
      }
      if(list.size() == 0){
        listS = "[" + listS + "]";
      }
      listS = "[" + listS + "]";
      return listS;
    }


  @Override
  public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
    RuntimeValue rt = null;

    if(v instanceof RuntimeIntValue){
      long vint = v.getIntValue("Subscription", where);
      int vlong = (int)vint;

      if(vlong > list.size()-1){
        Main.error("Prover aa faa element " + vlong + ". Array str: "  + list.size());
      }else{
        rt = list.get(vlong);
      }
    }else{
      runtimeError("Type error for Subscription.", where);
    }
    return rt;
  }

  public ArrayList<RuntimeValue> getListValue(String what, AspSyntax where) {
  if(list.isEmpty()){
    return null;
  }else{
    return list;
  }
}

  @Override
	public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where){
		if (v instanceof RuntimeIntValue) {
      ArrayList<RuntimeValue> tList = new ArrayList<>();
			int counter = 0;
			long times = v.getIntValue("*", where);
			while (counter < times) {
				tList.addAll(list);
				counter++;
			}
			return new RuntimeListValue(tList);
		}
      runtimeError("Type error for '*'!", where);
    	return null;
	}

  @Override
  public RuntimeValue evalNot(AspSyntax where) {
    return new RuntimeBoolValue(false);
  }


  @Override
  public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where){
    if(v instanceof RuntimeNoneValue){
      return new RuntimeBoolValue(false);
    }
    runtimeError("Error for '==' ", where);
    return null;
  }

  public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where){
    if(v instanceof RuntimeNoneValue){
      return new RuntimeBoolValue(true);
    }
    runtimeError("Error for '!=' ", where);
    return null;
  }

  @Override
  public boolean getBoolValue(String what, AspSyntax where){
    if(list.isEmpty()){
      return false;
    }else {
      return true;
    }
  }

  @Override
  public void evalAssignElem(RuntimeValue v, RuntimeValue v2, AspSyntax where){
    if(v instanceof RuntimeIntValue){
      long midint = v.getIntValue("assign list", where);
      int pos = (int)midint;
      list.set(pos, v2);
    }else{
      runtimeError("Type error for Assign Element: ikke en integer verdi!", where);
    }
  }
}
