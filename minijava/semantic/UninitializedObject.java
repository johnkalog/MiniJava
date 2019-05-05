package semantic;

public class UninitializedObject extends Exception{
  private String Identifier, MethodName, ClassName;

  public UninitializedObject(String Identifier,String MethodName,String ClassName){
    this.Identifier = Identifier;
    this.MethodName = MethodName;
    this.ClassName = ClassName;
  }

  public String getMessage() {
    return "Object type Identifier: "+this.Identifier+" hasn't been initialized for method call in method: "+
           this.MethodName+" in class: "+this.ClassName;
  }
}
