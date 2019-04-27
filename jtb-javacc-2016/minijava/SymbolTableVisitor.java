import syntaxtree.*;
import visitor.GJDepthFirst;
import java.util.*;

public class SymbolTableVisitor extends GJDepthFirst<Map<String, String>, String>{

  private Map<String, String> ClassExtend;
  private Map<Set <String>, String> ClassFields;

  public SymbolTableVisitor(){
    ClassExtend = new HashMap<String, String>();
    ClassFields = new HashMap<Set <String>, String>();
  }

  /**
   * f0 -> MainClass()
   * f1 -> ( TypeDeclaration() )*
   * f2 -> <EOF>
   */
  public Map<String, String> visit(Goal n, String argu) {
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
  public Map<String, String> visit(MainClass n, String argu) {
     Map<String, String> _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
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
     n.f14.accept(this, argu);
     n.f15.accept(this, argu);
     n.f16.accept(this, argu);
     n.f17.accept(this, argu);
     return _ret;
  }

  /**
   * f0 -> ClassDeclaration()
   *       | ClassExtendsDeclaration()
   */
  public Map<String, String> visit(TypeDeclaration n, String argu) {
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
  public Map<String, String> visit(ClassDeclaration n, String argu) {
    String ClassName = n.f1.accept(this, argu).keySet().toArray()[0].toString();
    ClassExtend.put(ClassName,null);
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
   * f0 -> "class"
   * f1 -> Identifier()
   * f2 -> "extends"
   * f3 -> Identifier()
   * f4 -> "{"
   * f5 -> ( VarDeclaration() )*
   * f6 -> ( MethodDeclaration() )*
   * f7 -> "}"
   */
  public Map<String, String> visit(ClassExtendsDeclaration n, String argu) {
    String ClassName = n.f1.accept(this, argu).keySet().toArray()[0].toString();
    String ClassParent = n.f3.accept(this, argu).keySet().toArray()[0].toString();
    ClassExtend.put(ClassName,ClassParent);
     Map<String, String> _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
     n.f2.accept(this, argu);
     n.f3.accept(this, argu);
     n.f4.accept(this, argu);
     n.f5.accept(this, argu);
     n.f6.accept(this, argu);
     n.f7.accept(this, argu);
     return _ret;
  }

  /**
   * f0 -> Type()
   * f1 -> Identifier()
   * f2 -> ";"
   */
  public Map<String, String> visit(VarDeclaration n, String argu) {
    // Map <String, String> TypeIdentifier = new HashMap<String, String>();
    // String Type = n.f0.accept(this, argu);
    // String Identifier =n.f1.accept(this, argu);
    // TypeIdentifier.put(Type,Identifier);
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
  public Map<String, String> visit(MethodDeclaration n, String argu) {
     Map<String, String> _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
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
     return _ret;
  }

  /**
   * f0 -> FormalParameter()
   * f1 -> FormalParameterTail()
   */
  public Map<String, String> visit(FormalParameterList n, String argu) {
     Map<String, String> _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
     return _ret;
  }

  /**
   * f0 -> Type()
   * f1 -> Identifier()
   */
  public Map<String, String> visit(FormalParameter n, String argu) {
     Map<String, String> _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
     return _ret;
  }

  /**
   * f0 -> ( FormalParameterTerm() )*
   */
  public Map<String, String> visit(FormalParameterTail n, String argu) {
     return n.f0.accept(this, argu);
  }

  /**
   * f0 -> ","
   * f1 -> FormalParameter()
   */
  public Map<String, String> visit(FormalParameterTerm n, String argu) {
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
  public Map<String, String> visit(Type n, String argu) {
     return n.f0.accept(this, argu);
  }

  /**
   * f0 -> "int"
   * f1 -> "["
   * f2 -> "]"
   */
  public Map<String, String> visit(ArrayType n, String argu) {
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
  public Map<String, String> visit(BooleanType n, String argu) {
     n.f0.accept(this, argu);
     Map<String, String> BooleanType = new HashMap<String, String>();
     BooleanType.put("BooleanType",null);
     return BooleanType;
  }

  /**
   * f0 -> "int"
   */
  public Map<String, String> visit(IntegerType n, String argu) {
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
  public Map<String, String> visit(Statement n, String argu) {
     return n.f0.accept(this, argu);
  }

  /**
   * f0 -> "{"
   * f1 -> ( Statement() )*
   * f2 -> "}"
   */
  public Map<String, String> visit(Block n, String argu) {
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
  public Map<String, String> visit(AssignmentStatement n, String argu) {
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
  public Map<String, String> visit(ArrayAssignmentStatement n, String argu) {
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
  public Map<String, String> visit(IfStatement n, String argu) {
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
  public Map<String, String> visit(WhileStatement n, String argu) {
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
  public Map<String, String> visit(PrintStatement n, String argu) {
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
  public Map<String, String> visit(Expression n, String argu) {
     return n.f0.accept(this, argu);
  }

  /**
   * f0 -> Clause()
   * f1 -> "&&"
   * f2 -> Clause()
   */
  public Map<String, String> visit(AndExpression n, String argu) {
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
  public Map<String, String> visit(CompareExpression n, String argu) {
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
  public Map<String, String> visit(PlusExpression n, String argu) {
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
  public Map<String, String> visit(MinusExpression n, String argu) {
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
  public Map<String, String> visit(TimesExpression n, String argu) {
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
  public Map<String, String> visit(ArrayLookup n, String argu) {
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
  public Map<String, String> visit(ArrayLength n, String argu) {
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
  public Map<String, String> visit(MessageSend n, String argu) {
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
  public Map<String, String> visit(ExpressionList n, String argu) {
     Map<String, String> _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
     return _ret;
  }

  /**
   * f0 -> ( ExpressionTerm() )*
   */
  public Map<String, String> visit(ExpressionTail n, String argu) {
     return n.f0.accept(this, argu);
  }

  /**
   * f0 -> ","
   * f1 -> Expression()
   */
  public Map<String, String> visit(ExpressionTerm n, String argu) {
     Map<String, String> _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
     return _ret;
  }

  /**
   * f0 -> NotExpression()
   *       | PrimaryExpression()
   */
  public Map<String, String> visit(Clause n, String argu) {
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
  public Map<String, String> visit(PrimaryExpression n, String argu) {
     return n.f0.accept(this, argu);
  }

  /**
   * f0 -> <INTEGER_LITERAL>
   */
  public Map<String, String> visit(IntegerLiteral n, String argu) {
     return n.f0.accept(this, argu);
  }

  /**
   * f0 -> "true"
   */
  public Map<String, String> visit(TrueLiteral n, String argu) {
     return n.f0.accept(this, argu);
  }

  /**
   * f0 -> "false"
   */
  public Map<String, String> visit(FalseLiteral n, String argu) {
     return n.f0.accept(this, argu);
  }

  /**
   * f0 -> <IDENTIFIER>
   */
  public Map<String, String> visit(Identifier n, String argu) {
     //return n.f0.accept(this, argu);
     Map<String, String> Identifier = new HashMap<String, String>();
     Identifier.put(n.f0.toString(),null);
     return Identifier;
  }

  /**
   * f0 -> "this"
   */
  public Map<String, String> visit(ThisExpression n, String argu) {
     return n.f0.accept(this, argu);
  }

  /**
   * f0 -> "new"
   * f1 -> "int"
   * f2 -> "["
   * f3 -> Expression()
   * f4 -> "]"
   */
  public Map<String, String> visit(ArrayAllocationExpression n, String argu) {
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
  public Map<String, String> visit(AllocationExpression n, String argu) {
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
  public Map<String, String> visit(NotExpression n, String argu) {
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
  public Map<String, String> visit(BracketExpression n, String argu) {
     Map<String, String> _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
     n.f2.accept(this, argu);
     return _ret;
  }

}
