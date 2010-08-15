package filter;

import org.openscience.cdk.interfaces.IChemObject;

/**
 * A filter accepts or rejects a chemical object.
 * 
 * @author maclean
 *
 */
public interface ChemicalFilter<T extends IChemObject> {

    /**
     * Test a chemical object to see if it is valid.
     * 
     * @param chemobject
     * @return
     */
    public boolean accept(T chemobject);
}
