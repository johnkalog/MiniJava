//
// Generated by JTB 1.3.2 DIT@UoA patched
//

package visitor;
import syntaxtree.*;
import java.util.*;

/**
 * All GJ visitors with no argument must implement this interface.
 */

public interface GJNoArguVisitor<R> {

   //
   // GJ Auto class visitors with no argument
   //

   public R visit(NodeList n) ;
   public R visit(NodeListOptional n) ;
   public R visit(NodeOptional n) ;
   public R visit(NodeSequence n) ;
   public R visit(NodeToken n) ;

   //
   // User-generated visitor methods below
   //

   /**
    * f0 -> <NUMBER>
    * f1 -> [ TernTail() ]
    */
   public R visit(Tern n);

   /**
    * f0 -> "?"
    * f1 -> Tern()
    * f2 -> ":"
    * f3 -> Tern()
    */
   public R visit(TernTail n);

}
