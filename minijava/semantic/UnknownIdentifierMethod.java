package semantic;

public class UnknownIdentifierMethod extends Exception{
  private String Identifier, MethodName, ClassName, Type;

  public UnknownIdentifierMethod(String Identifier,String MethodName,String ClassName,String Type){
    this.Identifier = Identifier;
    this.MethodName = MethodName;
    this.ClassName = ClassName;
    this.Type = Type;
  }

  public String getMessage() {
    return "Unknown class type: "+this.Type+" for field: "+this.Identifier+" in method: "+this.MethodName+" in class: "+this.ClassName;
  }
}
