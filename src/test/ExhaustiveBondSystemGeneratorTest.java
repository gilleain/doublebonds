package test;

import org.junit.Test;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IMolecule;

import doubles.ExhaustiveBondSystemGenerator;

public class ExhaustiveBondSystemGeneratorTest {
    
    public void printDoubles(IAtomContainer atomContainer) {
        System.out.print("|");
        for (int i = 0; i < atomContainer.getBondCount(); i++) {
            IBond bond = atomContainer.getBond(i);
            if (bond.getOrder() == IBond.Order.DOUBLE) {
                System.out.print(i + "|");
            }
        }
        System.out.println();
    }
    
    @Test
    public void napthalene() throws CDKException {
        IMolecule napthalene = RingMoleculeFactory.makePlainNapthalene();
        ExhaustiveBondSystemGenerator generator = 
            new ExhaustiveBondSystemGenerator();
        generator.generate(napthalene);
        int numberOfSolutions = 0;
        while (generator.hasNext()) {
            IAtomContainer possible = generator.generateNext();
            printDoubles(possible);
            numberOfSolutions++;
        }
        System.out.println(numberOfSolutions + " solutions");
    }

}
