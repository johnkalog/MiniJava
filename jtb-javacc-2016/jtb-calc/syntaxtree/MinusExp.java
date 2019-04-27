//
// Generated by JTB 1.3.2 DIT@UoA patched
//

package syntaxtree;

/**
 * Grammar production:
 * f0 -> "-"
 * f1 -> Term()
 * f2 -> [ Exp2() ]
 */
public class MinusExp implements Node {
   public NodeToken f0;
   public Term f1;
   public NodeOptional f2;

   public MinusExp(NodeToken n0, Term n1, NodeOptional n2) {
      f0 = n0;
      f1 = n1;
      f2 = n2;
   }

   public MinusExp(Term n0, NodeOptional n1) {
      f0 = new NodeToken("-");
      f1 = n0;
      f2 = n1;
   }

   public void accept(visitor.Visitor v) {
      v.visit(this);
   }
   public <R,A> R accept(visitor.GJVisitor<R,A> v, A argu) {
      return v.visit(this,argu);
   }
   public <R> R accept(visitor.GJNoArguVisitor<R> v) {
      return v.visit(this);
   }
   public <A> void accept(visitor.GJVoidVisitor<A> v, A argu) {
      v.visit(this,argu);
   }
}
