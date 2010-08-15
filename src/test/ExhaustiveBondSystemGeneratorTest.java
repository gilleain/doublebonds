package test;

import org.junit.Test;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.templates.MoleculeFactory;

import doubles.ExhaustiveBondSystemGenerator;
import doubles.Util;
import filter.SP2Filter;

public class ExhaustiveBondSystemGeneratorTest {
    
    @Test
    public void benzoquinone() throws CDKException {
        IMolecule quinone = RingMoleculeFactory.makeQuinoneWithoutRingbonds();
        ExhaustiveBondSystemGenerator generator = 
            new ExhaustiveBondSystemGenerator();
        generator.addFilter(new SP2Filter());
        generator.generate(quinone);
        for (IAtomContainer possible : generator.generate()) {
            Util.printDoubles(possible);
        }
    }
    
    @Test
    public void benzene() throws CDKException {
        IMolecule benzene = MoleculeFactory.makeCyclohexane();
        ExhaustiveBondSystemGenerator generator = 
            new ExhaustiveBondSystemGenerator();
        generator.generate(benzene);
        int numberOfSolutions = 0;
        while (generator.hasNext()) {
            IAtomContainer possible = generator.generateNext();
            Util.printDoubles(possible);
            numberOfSolutions++;
        }
        System.out.println(numberOfSolutions + " solutions");
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
            Util.printDoubles(possible);
            numberOfSolutions++;
        }
        System.out.println(numberOfSolutions + " solutions");
    }

}
