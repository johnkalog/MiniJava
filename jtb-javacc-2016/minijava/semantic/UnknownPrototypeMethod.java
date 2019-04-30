package semantic;

public class UnknownPrototypeMethod extends Exception{
  private String MethodName, ClassName, Type;
  private int index;

  public UnknownPrototypeMethod(String MethodName,String ClassName,String Type,int index){
    this.MethodName = MethodName;
    this.ClassName = ClassName;
    this.Type = Type;
    this.index = index;
  }

  public String getMessage() {
    if ( this.index==0 ){
      return "Unknown class type: "+this.Type+" for return parameter in method: "+this.MethodName+" in class: "+this.ClassName;
    }
    else{
      return "Unknown class type: "+this.Type+" for: "+String.valueOf(this.index)+" parameter in method: "+this.MethodName+" in class: "+this.ClassName;
    }
  }
}
