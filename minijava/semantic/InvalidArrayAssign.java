package semantic;

public class InvalidArrayAssign extends Exception{
  private String Identifier, TypeExpression, MethodName, ClassName;

  public InvalidArrayAssign(String Identifier,String TypeExpression,String MethodName,String ClassName){
    this.Identifier = Identifier;
    this.TypeExpression = TypeExpression;
    this.MethodName = MethodName;
    this.ClassName = ClassName;
  }

  public String getMessage() {
    return this.TypeExpression+" can't be assigned to field of array: "+this.Identifier+" in method: "+this.MethodName+" in  class: "+this.ClassName;
  }
}
