package semantic;

public class InvalidReturnType extends Exception{
  private String Type, ReturnType, MethodName, ClassName;

  public InvalidReturnType(String Type,String ReturnType,String MethodName,String ClassName){
    this.Type = Type;
    this.ReturnType = ReturnType;
    this.MethodName = MethodName;
    this.ClassName = ClassName;
  }

  public String getMessage() {
    return "return type is "+this.Type+" but function: "+this.MethodName+" returns "+this.ReturnType+" in class: "+
           this.ClassName;
  }
}
