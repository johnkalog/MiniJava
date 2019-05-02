package semantic;

public class NotAnArray extends Exception{
  private String Operation, Identifier, MethodName, ClassName, Type;

  public NotAnArray(String Operation,String Identifier,String Type,String MethodName,String ClassName){
    this.Operation = Operation;
    this.Identifier = Identifier;
    this.Type = Type;
    this.MethodName = MethodName;
    this.ClassName = ClassName;
  }

  public String getMessage() {
    return "Identifier: "+this.Identifier+" is: "+this.Type+" instead of: ArrayType in "+this.Operation+" in  method: "+this.MethodName+" in class: "+this.ClassName;
  }
}
