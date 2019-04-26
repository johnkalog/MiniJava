import syntaxtree.*;
import visitor.GJDepthFirst;

public class SymbolTableVisitor extends GJDepthFirst<Integer, Integer>{

  /**
   * f0 -> MainClass()
   * f1 -> ( TypeDeclaration() )*
   * f2 -> <EOF>
   */
  public Integer visit(Goal n, Integer argu) {
     Integer _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
     n.f2.accept(this, argu);
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
  public Integer visit(MainClass n, Integer argu) {
     Integer _ret=null;
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
  public Integer visit(TypeDeclaration n, Integer argu) {
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
  public Integer visit(ClassDeclaration n, Integer argu) {
     Integer _ret=null;
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
  public Integer visit(ClassExtendsDeclaration n, Integer argu) {
     Integer _ret=null;
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
  public Integer visit(VarDeclaration n, Integer argu) {
     Integer _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
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
  public Integer visit(MethodDeclaration n, Integer argu) {
     Integer _ret=null;
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
  public Integer visit(FormalParameterList n, Integer argu) {
     Integer _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
     return _ret;
  }

  /**
   * f0 -> Type()
   * f1 -> Identifier()
   */
  public Integer visit(FormalParameter n, Integer argu) {
     Integer _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
     return _ret;
  }

  /**
   * f0 -> ( FormalParameterTerm() )*
   */
  public Integer visit(FormalParameterTail n, Integer argu) {
     return n.f0.accept(this, argu);
  }

  /**
   * f0 -> ","
   * f1 -> FormalParameter()
   */
  public Integer visit(FormalParameterTerm n, Integer argu) {
     Integer _ret=null;
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
  public Integer visit(Type n, Integer argu) {
     return n.f0.accept(this, argu);
  }

  /**
   * f0 -> "Integer"
   * f1 -> "["
   * f2 -> "]"
   */
  public Integer visit(ArrayType n, Integer argu) {
     Integer _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
     n.f2.accept(this, argu);
     return _ret;
  }

  /**
   * f0 -> "boolean"
   */
  public Integer visit(BooleanType n, Integer argu) {
     return n.f0.accept(this, argu);
  }

  /**
   * f0 -> "Integer"
   */
  public Integer visit(IntegerType n, Integer argu) {
     return n.f0.accept(this, argu);
  }

  /**
   * f0 -> Block()
   *       | AssignmentStatement()
   *       | ArrayAssignmentStatement()
   *       | IfStatement()
   *       | WhileStatement()
   *       | PrintStatement()
   */
  public Integer visit(Statement n, Integer argu) {
     return n.f0.accept(this, argu);
  }

  /**
   * f0 -> "{"
   * f1 -> ( Statement() )*
   * f2 -> "}"
   */
  public Integer visit(Block n, Integer argu) {
     Integer _ret=null;
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
  public Integer visit(AssignmentStatement n, Integer argu) {
     Integer _ret=null;
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
  public Integer visit(ArrayAssignmentStatement n, Integer argu) {
     Integer _ret=null;
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
  public Integer visit(IfStatement n, Integer argu) {
     Integer _ret=null;
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
  public Integer visit(WhileStatement n, Integer argu) {
     Integer _ret=null;
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
  public Integer visit(PrintStatement n, Integer argu) {
     Integer _ret=null;
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
  public Integer visit(Expression n, Integer argu) {
     return n.f0.accept(this, argu);
  }

  /**
   * f0 -> Clause()
   * f1 -> "&&"
   * f2 -> Clause()
   */
  public Integer visit(AndExpression n, Integer argu) {
     Integer _ret=null;
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
  public Integer visit(CompareExpression n, Integer argu) {
     Integer _ret=null;
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
  public Integer visit(PlusExpression n, Integer argu) {
     Integer _ret=null;
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
  public Integer visit(MinusExpression n, Integer argu) {
     Integer _ret=null;
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
  public Integer visit(TimesExpression n, Integer argu) {
     Integer _ret=null;
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
  public Integer visit(ArrayLookup n, Integer argu) {
     Integer _ret=null;
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
  public Integer visit(ArrayLength n, Integer argu) {
     Integer _ret=null;
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
  public Integer visit(MessageSend n, Integer argu) {
     Integer _ret=null;
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
  public Integer visit(ExpressionList n, Integer argu) {
     Integer _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
     return _ret;
  }

  /**
   * f0 -> ( ExpressionTerm() )*
   */
  public Integer visit(ExpressionTail n, Integer argu) {
     return n.f0.accept(this, argu);
  }

  /**
   * f0 -> ","
   * f1 -> Expression()
   */
  public Integer visit(ExpressionTerm n, Integer argu) {
     Integer _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
     return _ret;
  }

  /**
   * f0 -> NotExpression()
   *       | PrimaryExpression()
   */
  public Integer visit(Clause n, Integer argu) {
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
  public Integer visit(PrimaryExpression n, Integer argu) {
     return n.f0.accept(this, argu);
  }

  /**
   * f0 -> <INTEGER_LITERAL>
   */
  public Integer visit(IntegerLiteral n, Integer argu) {
     return n.f0.accept(this, argu);
  }

  /**
   * f0 -> "true"
   */
  public Integer visit(TrueLiteral n, Integer argu) {
     return n.f0.accept(this, argu);
  }

  /**
   * f0 -> "false"
   */
  public Integer visit(FalseLiteral n, Integer argu) {
     return n.f0.accept(this, argu);
  }

  /**
   * f0 -> <IDENTIFIER>
   */
  public Integer visit(Identifier n, Integer argu) {
     return n.f0.accept(this, argu);
  }

  /**
   * f0 -> "this"
   */
  public Integer visit(ThisExpression n, Integer argu) {
     return n.f0.accept(this, argu);
  }

  /**
   * f0 -> "new"
   * f1 -> "Integer"
   * f2 -> "["
   * f3 -> Expression()
   * f4 -> "]"
   */
  public Integer visit(ArrayAllocationExpression n, Integer argu) {
     Integer _ret=null;
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
  public Integer visit(AllocationExpression n, Integer argu) {
     Integer _ret=null;
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
  public Integer visit(NotExpression n, Integer argu) {
     Integer _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
     return _ret;
  }

  /**
   * f0 -> "("
   * f1 -> Expression()
   * f2 -> ")"
   */
  public Integer visit(BracketExpression n, Integer argu) {
     Integer _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
     n.f2.accept(this, argu);
     return _ret;
  }

}
