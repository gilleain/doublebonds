package filter;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.atomtype.CDKAtomTypeMatcher;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomType;
import org.openscience.cdk.interfaces.IAtomType.Hybridization;

public class SP2Filter implements ChemicalFilter<IAtomContainer> {
    
    public boolean accept(IAtomContainer atomContainer) {
        CDKAtomTypeMatcher matcher = 
            CDKAtomTypeMatcher.getInstance(atomContainer.getBuilder());
        for (IAtom atom : atomContainer.atoms()) {
            IAtomType matched;
            try {
                matched = matcher.findMatchingAtomType(atomContainer, atom);
            } catch (CDKException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return false;
            }
            if (matched == null) return false;
        }
        // TODO : only check ring atoms and ring adjacent atoms
        for (IAtom atom : atomContainer.atoms()) {
            Hybridization h = atom.getHybridization();
            if (h == CDKConstants.UNSET || 
                    !(h == Hybridization.SP2 || h == Hybridization.PLANAR3)) {
                return false;
            }
        }
        return true;
    }

}
