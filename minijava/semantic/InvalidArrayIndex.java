package semantic;

public class InvalidArrayIndex extends Exception{
  private String ArrayName, Identifier, Type, MethodName, ClassName;

  public InvalidArrayIndex(String ArrayName,String Identifier,String Type,String MethodName,String ClassName){
    this.ArrayName = ArrayName;
    this.Identifier = Identifier;
    this.Type = Type;
    this.MethodName = MethodName;
    this.ClassName = ClassName;
  }

  public String getMessage() {
    return "Invalid index at lookup expression: "+this.ArrayName+"["+this.Identifier+"].It's type is: "+this.Type+
            " instead of IntegerType in method: "+this.MethodName+" in class: "+this.ClassName;
  }
}
