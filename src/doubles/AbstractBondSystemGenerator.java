package doubles;

import java.util.ArrayList;
import java.util.List;

import org.openscience.cdk.interfaces.IAtomContainer;

import filter.ChemicalFilter;

public abstract class AbstractBondSystemGenerator {
    
    private List<ChemicalFilter<IAtomContainer>> filters;
    
    public abstract boolean hasNext();
    
    public abstract IAtomContainer generateNext();

    public boolean accept(IAtomContainer atomContainer) {
        for (ChemicalFilter filter : filters) {
            if (filter.accept(atomContainer)) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }
    
    public List<IAtomContainer> generate() {
        List<IAtomContainer> generatedResults = new ArrayList<IAtomContainer>();
        while (hasNext()) {
            IAtomContainer next = generateNext();
            if (accept(next)) {
                generatedResults.add(next);
            }
        }
        return generatedResults;
    }

}
