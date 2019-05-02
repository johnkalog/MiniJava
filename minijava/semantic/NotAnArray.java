package semantic;

public class NotAnArray extends Exception{
  private String Identifier, MethodName, ClassName, Type;

  public NotAnArray(String Identifier,String Type,String MethodName,String ClassName){
    this.Identifier = Identifier;
    this.Type = Type;
    this.MethodName = MethodName;
    this.ClassName = ClassName;
  }

  public String getMessage() {
    return "Identifier: "+this.Identifier+" is: "+this.Type+" instead of: ArrayType in  method: "+this.MethodName+" in class: "+this.ClassName;
  }
}
