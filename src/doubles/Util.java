package doubles;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;

import test.AtomContainerPrinter;

public class Util {
    
    static AtomContainerPrinter printer = new AtomContainerPrinter();
    
    public static void printDoubles(IAtomContainer atomContainer) {
        System.out.print("|");
        for (int i = 0; i < atomContainer.getBondCount(); i++) {
            IBond bond = atomContainer.getBond(i);
            if (bond.getOrder() == IBond.Order.DOUBLE) {
                System.out.print(i + "|");
            }
        }
        System.out.println();
    }
    
    public static void print(IAtomContainer atomContainer) {
        System.out.println(printer.toString(atomContainer));
    }

}
