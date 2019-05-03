package semantic;

public class DifferentPrototype extends Exception{
  private String Method, MethodName, ClassName;

  public DifferentPrototype(String Method,String MethodName,String ClassName){
    this.Method = Method;
    this.MethodName = MethodName;
    this.ClassName = ClassName;
  }

  public String getMessage() {
    return "Method: "+this.Method+" hasn't the same prototype as used in method call in method: "+this.MethodName+
           " in class: "+this.ClassName;
  }
}
