package semantic;

public class UnknownObjectName extends Exception{
  private String Type, MethodName, ClassName;

  public UnknownObjectName(String Type,String MethodName,String ClassName){
    this.Type = Type;
    this.MethodName = MethodName;
    this.ClassName = ClassName;
  }

  public String getMessage() {
    return "Unknown Object Type: "+this.Type+" for method call in method: "+this.MethodName+" in class: "+this.ClassName;
  }
}
