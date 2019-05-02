package semantic;

public class DoesNotExistMethodInClass extends Exception{
  private String Method,IfInClass, MethodName, ClassName;

  public DoesNotExistMethodInClass(String Method,String IfInClass,String MethodName,String ClassName){
    this.Method = Method;
    this.IfInClass = IfInClass;
    this.MethodName = MethodName;
    this.ClassName = ClassName;
  }

  public String getMessage() {
    return "Method: "+this.Method+" can't be supported(declared at class or parent's class) from class: "+
            this.IfInClass+" at method call in method: "+this.MethodName+" in class: "+this.ClassName;
  }
}
