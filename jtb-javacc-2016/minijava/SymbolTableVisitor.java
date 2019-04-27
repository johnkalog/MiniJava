import syntaxtree.*;
import visitor.GJDepthFirst;
import java.util.*;

public class SymbolTableVisitor extends GJDepthFirst<Map<String, String>, Map<String, String>>{

  private Map<String, String> ClassExtend;
  private Map<ArrayList <String>, String> ClassFields;
  private Map<ArrayList <String>, String> FunctionFields;

  public SymbolTableVisitor(){
    ClassExtend = new HashMap<String, String>();
    ClassFields = new HashMap<ArrayList <String>, String>();
    FunctionFields = new HashMap<ArrayList <String>, String>();

  }

  /**
   * f0 -> MainClass()
   * f1 -> ( TypeDeclaration() )*
   * f2 -> <EOF>
   */
  public Map<String, String> visit(Goal n, Map<String, String> argu) {
     Map<String, String> _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
     n.f2.accept(this, argu);
     System.out.println(Collections.singletonList(ClassExtend));
     return _ret;
  }

  /**
   * f0 -> "class"
   * f1 -> Identifier()
   * f2 -> "{"
   * f3 -> "public"
   * f4 -> "static"
   * f5 -> "void"
   * f6 -> "main"
   * f7 -> "("
   * f8 -> "String"
   * f9 -> "["
   * f10 -> "]"
   * f11 -> Identifier()
   * f12 -> ")"
   * f13 -> "{"
   * f14 -> ( VarDeclaration() )*
   * f15 -> ( Statement() )*
   * f16 -> "}"
   * f17 -> "}"
   */
  public Map<String, String> visit(MainClass n, Map<String, String> argu) {
     Map<String, String> _ret=null;
     n.f0.accept(this, argu);
     String ClassName = n.f1.accept(this, argu).keySet().toArray()[0].toString();
     // n.f1.accept(this, argu);
     n.f2.accept(this, argu);
     n.f3.accept(this, argu);
     n.f4.accept(this, argu);
     n.f5.accept(this, argu);
     n.f6.accept(this, argu);
     n.f7.accept(this, argu);
     n.f8.accept(this, argu);
     n.f9.accept(this, argu);
     n.f10.accept(this, argu);
     n.f11.accept(this, argu);
     n.f12.accept(this, argu);
     n.f13.accept(this, argu);
     Map <String, String> IdentifierType = new HashMap<String, String>();
     if ( n.f14.present() ){
       n.f14.accept(this, IdentifierType);
       IdentifierType.forEach((key, value) -> {
         ArrayList<String> IdentifierClass = new ArrayList<String>();
         IdentifierClass.add(key);
         IdentifierClass.add(ClassName);
         ClassFields.put(IdentifierClass,value);
       });
       System.out.println(ClassFields);
     }
     else{
       System.out.println("noo");
     }
     // n.f14.accept(this, argu);
     n.f15.accept(this, argu);
     n.f16.accept(this, argu);
     n.f17.accept(this, argu);
     return _ret;
  }

  /**
   * f0 -> ClassDeclaration()
   *       | ClassExtendsDeclaration()
   */
  public Map<String, String> visit(TypeDeclaration n, Map<String, String> argu) {
     return n.f0.accept(this, argu);
  }

  /**
   * f0 -> "class"
   * f1 -> Identifier()
   * f2 -> "{"
   * f3 -> ( VarDeclaration() )*
   * f4 -> ( MethodDeclaration() )*
   * f5 -> "}"
   */
  public Map<String, String> visit(ClassDeclaration n, Map<String, String> argu) {
    String ClassName = n.f1.accept(this, argu).keySet().toArray()[0].toString();
    ClassExtend.put(ClassName,null);
     Map<String, String> _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
     n.f2.accept(this, argu);
     Map <String, String> IdentifierType = new HashMap<String, String>();
     // n.f3.accept(this, IdentifierType);
     if ( n.f3.present() ){
       n.f3.accept(this, IdentifierType);
       IdentifierType.forEach((key, value) -> {
         ArrayList<String> IdentifierClass = new ArrayList<String>();
         IdentifierClass.add(key);
         IdentifierClass.add(ClassName);
         ClassFields.put(IdentifierClass,value);
       });
       System.out.println(ClassFields);
     }
     else{
       System.out.println("noo");
     }
     Map <String, String> ClassForMethod = new HashMap<String, String>();
     if ( n.f4.present() ){
       ClassForMethod.put(ClassName,null);
       n.f4.accept(this, ClassForMethod);
     }
     else{
       System.out.println("noo1");
     }
     n.f5.accept(this, argu);
     return _ret;
  }

  /**
   * f0 -> "class"
   * f1 -> Identifier()
   * f2 -> "extends"
   * f3 -> Identifier()
   * f4 -> "{"
   * f5 -> ( VarDeclaration() )*
   * f6 -> ( MethodDeclaration() )*
   * f7 -> "}"
   */
  public Map<String, String> visit(ClassExtendsDeclaration n, Map<String, String> argu) {
    String ClassName = n.f1.accept(this, argu).keySet().toArray()[0].toString();
    String ClassParent = n.f3.accept(this, argu).keySet().toArray()[0].toString();
    ClassExtend.put(ClassName,ClassParent);
     Map<String, String> _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
     n.f2.accept(this, argu);
     n.f3.accept(this, argu);
     n.f4.accept(this, argu);
     Map <String, String> IdentifierType = new HashMap<String, String>();
     if ( n.f5.present() ){
       n.f5.accept(this, IdentifierType);
       IdentifierType.forEach((key, value) -> {
         ArrayList<String> IdentifierClass = new ArrayList<String>();
         IdentifierClass.add(key);
         IdentifierClass.add(ClassName);
         ClassFields.put(IdentifierClass,value);
       });
       System.out.println(ClassFields);
     }
     else{
       System.out.println("noo");
     }
     // n.f5.accept(this, argu);
     Map <String, String> ClassForMethod = new HashMap<String, String>();
     if ( n.f6.present() ){
       ClassForMethod.put(ClassName,null);
       n.f6.accept(this, ClassForMethod);
     }
     else{
       System.out.println("noo1");
     }
     // n.f6.accept(this, argu);
     n.f7.accept(this, argu);
     return _ret;
  }

  /**
   * f0 -> Type()
   * f1 -> Identifier()
   * f2 -> ";"
   */
  public Map<String, String> visit(VarDeclaration n, Map<String, String> argu) {
    String Type = n.f0.accept(this, argu).keySet().toArray()[0].toString();
    String Identifier =n.f1.accept(this, argu).keySet().toArray()[0].toString();
    argu.put(Identifier,Type);
     Map<String, String> _ret=null;
     n.f2.accept(this, argu);
     return _ret;
  }

  /**
   * f0 -> "public"
   * f1 -> Type()
   * f2 -> Identifier()
   * f3 -> "("
   * f4 -> ( FormalParameterList() )?
   * f5 -> ")"
   * f6 -> "{"
   * f7 -> ( VarDeclaration() )*
   * f8 -> ( Statement() )*
   * f9 -> "return"
   * f10 -> Expression()
   * f11 -> ";"
   * f12 -> "}"
   */
  public Map<String, String> visit(MethodDeclaration n, Map<String, String> argu) {
     Map<String, String> _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
     String MethodName = n.f2.accept(this, argu).keySet().toArray()[0].toString();
     n.f3.accept(this, argu);
     n.f4.accept(this, argu);
     n.f5.accept(this, argu);
     n.f6.accept(this, argu);
     Map <String, String> IdentifierType = new HashMap<String, String>();
     if ( n.f7.present() ){
       n.f7.accept(this, IdentifierType);
       String ClassName = argu.keySet().toArray()[0].toString();
       IdentifierType.forEach((key, value) -> {
         ArrayList<String> IdentifierFunctionClass = new ArrayList<String>();
         IdentifierFunctionClass.add(key);
         IdentifierFunctionClass.add(MethodName);
         IdentifierFunctionClass.add(ClassName);
         FunctionFields.put(IdentifierFunctionClass,value);
       });
       System.out.println(FunctionFields);
     }
     else{
       System.out.println("noo1");
     }
     // n.f7.accept(this, argu);
     n.f8.accept(this, argu);
     n.f9.accept(this, argu);
     n.f10.accept(this, argu);
     n.f11.accept(this, argu);
     n.f12.accept(this, argu);
     return _ret;
  }

  /**
   * f0 -> FormalParameter()
   * f1 -> FormalParameterTail()
   */
  public Map<String, String> visit(FormalParameterList n, Map<String, String> argu) {
     Map<String, String> _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
     return _ret;
  }

  /**
   * f0 -> Type()
   * f1 -> Identifier()
   */
  public Map<String, String> visit(FormalParameter n, Map<String, String> argu) {
     Map<String, String> _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
     return _ret;
  }

  /**
   * f0 -> ( FormalParameterTerm() )*
   */
  public Map<String, String> visit(FormalParameterTail n, Map<String, String> argu) {
     return n.f0.accept(this, argu);
  }

  /**
   * f0 -> ","
   * f1 -> FormalParameter()
   */
  public Map<String, String> visit(FormalParameterTerm n, Map<String, String> argu) {
     Map<String, String> _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
     return _ret;
  }

  /**
   * f0 -> ArrayType()
   *       | BooleanType()
   *       | IntegerType()
   *       | Identifier()
   */
  public Map<String, String> visit(Type n, Map<String, String> argu) {
     return n.f0.accept(this, argu);
  }

  /**
   * f0 -> "int"
   * f1 -> "["
   * f2 -> "]"
   */
  public Map<String, String> visit(ArrayType n, Map<String, String> argu) {
     Map<String, String> _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
     n.f2.accept(this, argu);
     Map<String, String> ArrayType = new HashMap<String, String>();
     ArrayType.put("ArrayType",null);
     return ArrayType;
  }

  /**
   * f0 -> "boolean"
   */
  public Map<String, String> visit(BooleanType n, Map<String, String> argu) {
     n.f0.accept(this, argu);
     Map<String, String> BooleanType = new HashMap<String, String>();
     BooleanType.put("BooleanType",null);
     return BooleanType;
  }

  /**
   * f0 -> "int"
   */
  public Map<String, String> visit(IntegerType n, Map<String, String> argu) {
     n.f0.accept(this, argu);
     Map<String, String> IntegerType = new HashMap<String, String>();
     IntegerType.put("IntegerType",null);
     return IntegerType;
  }

  /**
   * f0 -> Block()
   *       | AssignmentStatement()
   *       | ArrayAssignmentStatement()
   *       | IfStatement()
   *       | WhileStatement()
   *       | PrintStatement()
   */
  public Map<String, String> visit(Statement n, Map<String, String> argu) {
     return n.f0.accept(this, argu);
  }

  /**
   * f0 -> "{"
   * f1 -> ( Statement() )*
   * f2 -> "}"
   */
  public Map<String, String> visit(Block n, Map<String, String> argu) {
     Map<String, String> _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
     n.f2.accept(this, argu);
     return _ret;
  }

  /**
   * f0 -> Identifier()
   * f1 -> "="
   * f2 -> Expression()
   * f3 -> ";"
   */
  public Map<String, String> visit(AssignmentStatement n, Map<String, String> argu) {
     Map<String, String> _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
     n.f2.accept(this, argu);
     n.f3.accept(this, argu);
     return _ret;
  }

  /**
   * f0 -> Identifier()
   * f1 -> "["
   * f2 -> Expression()
   * f3 -> "]"
   * f4 -> "="
   * f5 -> Expression()
   * f6 -> ";"
   */
  public Map<String, String> visit(ArrayAssignmentStatement n, Map<String, String> argu) {
     Map<String, String> _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
     n.f2.accept(this, argu);
     n.f3.accept(this, argu);
     n.f4.accept(this, argu);
     n.f5.accept(this, argu);
     n.f6.accept(this, argu);
     return _ret;
  }

  /**
   * f0 -> "if"
   * f1 -> "("
   * f2 -> Expression()
   * f3 -> ")"
   * f4 -> Statement()
   * f5 -> "else"
   * f6 -> Statement()
   */
  public Map<String, String> visit(IfStatement n, Map<String, String> argu) {
     Map<String, String> _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
     n.f2.accept(this, argu);
     n.f3.accept(this, argu);
     n.f4.accept(this, argu);
     n.f5.accept(this, argu);
     n.f6.accept(this, argu);
     return _ret;
  }

  /**
   * f0 -> "while"
   * f1 -> "("
   * f2 -> Expression()
   * f3 -> ")"
   * f4 -> Statement()
   */
  public Map<String, String> visit(WhileStatement n, Map<String, String> argu) {
     Map<String, String> _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
     n.f2.accept(this, argu);
     n.f3.accept(this, argu);
     n.f4.accept(this, argu);
     return _ret;
  }

  /**
   * f0 -> "System.out.println"
   * f1 -> "("
   * f2 -> Expression()
   * f3 -> ")"
   * f4 -> ";"
   */
  public Map<String, String> visit(PrintStatement n, Map<String, String> argu) {
     Map<String, String> _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
     n.f2.accept(this, argu);
     n.f3.accept(this, argu);
     n.f4.accept(this, argu);
     return _ret;
  }

  /**
   * f0 -> AndExpression()
   *       | CompareExpression()
   *       | PlusExpression()
   *       | MinusExpression()
   *       | TimesExpression()
   *       | ArrayLookup()
   *       | ArrayLength()
   *       | MessageSend()
   *       | Clause()
   */
  public Map<String, String> visit(Expression n, Map<String, String> argu) {
     return n.f0.accept(this, argu);
  }

  /**
   * f0 -> Clause()
   * f1 -> "&&"
   * f2 -> Clause()
   */
  public Map<String, String> visit(AndExpression n, Map<String, String> argu) {
     Map<String, String> _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
     n.f2.accept(this, argu);
     return _ret;
  }

  /**
   * f0 -> PrimaryExpression()
   * f1 -> "<"
   * f2 -> PrimaryExpression()
   */
  public Map<String, String> visit(CompareExpression n, Map<String, String> argu) {
     Map<String, String> _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
     n.f2.accept(this, argu);
     return _ret;
  }

  /**
   * f0 -> PrimaryExpression()
   * f1 -> "+"
   * f2 -> PrimaryExpression()
   */
  public Map<String, String> visit(PlusExpression n, Map<String, String> argu) {
     Map<String, String> _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
     n.f2.accept(this, argu);
     return _ret;
  }

  /**
   * f0 -> PrimaryExpression()
   * f1 -> "-"
   * f2 -> PrimaryExpression()
   */
  public Map<String, String> visit(MinusExpression n, Map<String, String> argu) {
     Map<String, String> _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
     n.f2.accept(this, argu);
     return _ret;
  }

  /**
   * f0 -> PrimaryExpression()
   * f1 -> "*"
   * f2 -> PrimaryExpression()
   */
  public Map<String, String> visit(TimesExpression n, Map<String, String> argu) {
     Map<String, String> _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
     n.f2.accept(this, argu);
     return _ret;
  }

  /**
   * f0 -> PrimaryExpression()
   * f1 -> "["
   * f2 -> PrimaryExpression()
   * f3 -> "]"
   */
  public Map<String, String> visit(ArrayLookup n, Map<String, String> argu) {
     Map<String, String> _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
     n.f2.accept(this, argu);
     n.f3.accept(this, argu);
     return _ret;
  }

  /**
   * f0 -> PrimaryExpression()
   * f1 -> "."
   * f2 -> "length"
   */
  public Map<String, String> visit(ArrayLength n, Map<String, String> argu) {
     Map<String, String> _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
     n.f2.accept(this, argu);
     return _ret;
  }

  /**
   * f0 -> PrimaryExpression()
   * f1 -> "."
   * f2 -> Identifier()
   * f3 -> "("
   * f4 -> ( ExpressionList() )?
   * f5 -> ")"
   */
  public Map<String, String> visit(MessageSend n, Map<String, String> argu) {
     Map<String, String> _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
     n.f2.accept(this, argu);
     n.f3.accept(this, argu);
     n.f4.accept(this, argu);
     n.f5.accept(this, argu);
     return _ret;
  }

  /**
   * f0 -> Expression()
   * f1 -> ExpressionTail()
   */
  public Map<String, String> visit(ExpressionList n, Map<String, String> argu) {
     Map<String, String> _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
     return _ret;
  }

  /**
   * f0 -> ( ExpressionTerm() )*
   */
  public Map<String, String> visit(ExpressionTail n, Map<String, String> argu) {
     return n.f0.accept(this, argu);
  }

  /**
   * f0 -> ","
   * f1 -> Expression()
   */
  public Map<String, String> visit(ExpressionTerm n, Map<String, String> argu) {
     Map<String, String> _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
     return _ret;
  }

  /**
   * f0 -> NotExpression()
   *       | PrimaryExpression()
   */
  public Map<String, String> visit(Clause n, Map<String, String> argu) {
     return n.f0.accept(this, argu);
  }

  /**
   * f0 -> IntegerLiteral()
   *       | TrueLiteral()
   *       | FalseLiteral()
   *       | Identifier()
   *       | ThisExpression()
   *       | ArrayAllocationExpression()
   *       | AllocationExpression()
   *       | BracketExpression()
   */
  public Map<String, String> visit(PrimaryExpression n, Map<String, String> argu) {
     return n.f0.accept(this, argu);
  }

  /**
   * f0 -> <INTEGER_LITERAL>
   */
  public Map<String, String> visit(IntegerLiteral n, Map<String, String> argu) {
     return n.f0.accept(this, argu);
  }

  /**
   * f0 -> "true"
   */
  public Map<String, String> visit(TrueLiteral n, Map<String, String> argu) {
     return n.f0.accept(this, argu);
  }

  /**
   * f0 -> "false"
   */
  public Map<String, String> visit(FalseLiteral n, Map<String, String> argu) {
     return n.f0.accept(this, argu);
  }

  /**
   * f0 -> <IDENTIFIER>
   */
  public Map<String, String> visit(Identifier n, Map<String, String> argu) {
     //return n.f0.accept(this, argu);
     Map<String, String> Identifier = new HashMap<String, String>();
     Identifier.put(n.f0.toString(),null);
     return Identifier;
  }

  /**
   * f0 -> "this"
   */
  public Map<String, String> visit(ThisExpression n, Map<String, String> argu) {
     return n.f0.accept(this, argu);
  }

  /**
   * f0 -> "new"
   * f1 -> "int"
   * f2 -> "["
   * f3 -> Expression()
   * f4 -> "]"
   */
  public Map<String, String> visit(ArrayAllocationExpression n, Map<String, String> argu) {
     Map<String, String> _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
     n.f2.accept(this, argu);
     n.f3.accept(this, argu);
     n.f4.accept(this, argu);
     return _ret;
  }

  /**
   * f0 -> "new"
   * f1 -> Identifier()
   * f2 -> "("
   * f3 -> ")"
   */
  public Map<String, String> visit(AllocationExpression n, Map<String, String> argu) {
     Map<String, String> _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
     n.f2.accept(this, argu);
     n.f3.accept(this, argu);
     return _ret;
  }

  /**
   * f0 -> "!"
   * f1 -> Clause()
   */
  public Map<String, String> visit(NotExpression n, Map<String, String> argu) {
     Map<String, String> _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
     return _ret;
  }

  /**
   * f0 -> "("
   * f1 -> Expression()
   * f2 -> ")"
   */
  public Map<String, String> visit(BracketExpression n, Map<String, String> argu) {
     Map<String, String> _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
     n.f2.accept(this, argu);
     return _ret;
  }

}
