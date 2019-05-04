package semantic;

public class NotAnInt extends Exception{
  private String Type, MethodName, ClassName;

  public NotAnInt(String Type,String MethodName,String ClassName){
    this.Type = Type;
    this.MethodName = MethodName;
    this.ClassName = ClassName;
  }

  public String getMessage() {
    return "Array index is: "+this.Type+" instead of IntegerType at ArrayAssignmentStatement in method: "+this.MethodName+
           " in class: "+this.ClassName;
  }
}
