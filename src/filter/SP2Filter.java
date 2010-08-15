package filter;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.atomtype.CDKAtomTypeMatcher;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomType;
import org.openscience.cdk.interfaces.IAtomType.Hybridization;
import org.openscience.cdk.tools.manipulator.AtomTypeManipulator;

import doubles.Util;

public class SP2Filter implements ChemicalFilter<IAtomContainer> {
    
    public boolean accept(IAtomContainer atomContainer) {
        CDKAtomTypeMatcher matcher = 
            CDKAtomTypeMatcher.getInstance(atomContainer.getBuilder());
        for (IAtom atom : atomContainer.atoms()) {
            IAtomType matchedType;
            try {
                matchedType = matcher.findMatchingAtomType(atomContainer, atom);
            } catch (CDKException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return false;
            }
            if (matchedType == null) {
                System.out.print("unmatched ");
                Util.print(atomContainer);
                return false;
            } else {
                AtomTypeManipulator.configure(atom, matchedType);
            }
        }
        // TODO : only check ring atoms and ring adjacent atoms
        int i = 0;
        for (IAtom atom : atomContainer.atoms()) {
            Hybridization h = atom.getHybridization();
            System.out.println(h);
            if (h == CDKConstants.UNSET || 
                    !(h == Hybridization.SP2 || h == Hybridization.PLANAR3)) {
                System.out.print("unhybridized " + i + " " + h + " ");
                Util.print(atomContainer);
                return false;
            }
            i++;
        }
        return true;
    }

}
