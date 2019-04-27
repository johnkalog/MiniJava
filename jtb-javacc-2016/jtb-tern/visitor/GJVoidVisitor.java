//
// Generated by JTB 1.3.2 DIT@UoA patched
//

package visitor;
import syntaxtree.*;
import java.util.*;

/**
 * All GJ void visitors must implement this interface.
 */

public interface GJVoidVisitor<A> {

   //
   // GJ void Auto class visitors
   //

   public void visit(NodeList n, A argu);
   public void visit(NodeListOptional n, A argu);
   public void visit(NodeOptional n, A argu);
   public void visit(NodeSequence n, A argu);
   public void visit(NodeToken n, A argu);

   //
   // User-generated visitor methods below
   //

   /**
    * f0 -> <NUMBER>
    * f1 -> [ TernTail() ]
    */
   public void visit(Tern n, A argu);

   /**
    * f0 -> "?"
    * f1 -> Tern()
    * f2 -> ":"
    * f3 -> Tern()
    */
   public void visit(TernTail n, A argu);

}
