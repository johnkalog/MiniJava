package semantic;

public class OverridePrototype extends Exception{
  private String MethodName, ClassName, ClassParent;

  public OverridePrototype(String MethodName,String ClassName,String ClassParent){
    this.MethodName = MethodName;
    this.ClassName = ClassName;
    this.ClassParent = ClassParent;
  }

  public String getMessage() {
    return "Method "+this.MethodName+" at class "+this.ClassName+" has different prototype from it's parent class "+this.ClassParent;
  }
}
