package semantic;

public class ExtendsItsSelf extends Exception{
  private String ClassName;

  public ExtendsItsSelf(String ClassName){
    this.ClassName = ClassName;
  }

  public String getMessage() {
    return "Class "+this.ClassName+" extends it's self";
  }
}
