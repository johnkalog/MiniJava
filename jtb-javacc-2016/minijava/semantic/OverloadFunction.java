package semantic;

public class OverloadFunction extends Exception{
  private String MethodName, ClassName;

  public OverloadFunction(String MethodName,String ClassName){
    this.MethodName = MethodName;
    this.ClassName = ClassName;
  }

  public String getMessage() {
    return "Method "+this.MethodName+" can't be overloaded in class "+this.ClassName;
  }
}
