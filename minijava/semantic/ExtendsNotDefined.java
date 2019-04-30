package semantic;

public class ExtendsNotDefined extends Exception{
  private String ClassName,ExtendsName;

  public ExtendsNotDefined(String ClassName,String ExtendsName){
    this.ClassName = ClassName;
    this.ExtendsName = ExtendsName;
  }

  public String getMessage() {
    return "Class: "+this.ClassName+" extends: "+this.ExtendsName+",class: "+ExtendsName+" not defined yet";
  }
}
