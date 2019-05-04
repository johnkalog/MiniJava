package semantic;

public class InvalidAssign extends Exception{
  private String Type, TypeExpression, MethodName, ClassName;

  public InvalidAssign(String Type,String TypeExpression,String MethodName,String ClassName){
    this.Type = Type;
    this.TypeExpression = TypeExpression;
    this.MethodName = MethodName;
    this.ClassName = ClassName;
  }

  public String getMessage() {
    return this.TypeExpression+" can't be assigned to "+this.Type+" in method: "+this.MethodName+" in  class: "+this.ClassName;
  }
}
