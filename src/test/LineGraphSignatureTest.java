package test;

import org.junit.Test;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.signature.MoleculeSignature;
import org.openscience.cdk.templates.MoleculeFactory;

import signature.SymmetryClass;

import doubles.LineGraph;
import doubles.LineGraphSignature;

public class LineGraphSignatureTest {
    
    @Test
    public void fusedTest() {
        IMolecule fused = RingMoleculeFactory.makeFusedThreeFourFiveRingSystem();
        MoleculeSignature molSig = new MoleculeSignature(fused);
        for (SymmetryClass symClass : molSig.getSymmetryClasses()) {
            System.out.println(symClass);
        }
        LineGraph lineGraph = new LineGraph(fused, false);
        LineGraphSignature signature = new LineGraphSignature(lineGraph);
        System.out.println(signature);
        for (SymmetryClass symmetryClass : signature.getSymmetryClasses()) {
            System.out.println(symmetryClass);
        }
    }
    
    @Test
    public void benzeneSignatureWithBondOrdersErased() {
        IMolecule benzene = MoleculeFactory.makeBenzene();
        LineGraph lineGraph = new LineGraph(benzene, true);
        LineGraphSignature signature = new LineGraphSignature(lineGraph);
        System.out.println(signature);
    }
    
    @Test
    public void benzeneSignatureWithBondOrdersPreserved() {
        IMolecule benzene = MoleculeFactory.makeBenzene();
        LineGraph lineGraph = new LineGraph(benzene, false);
        LineGraphSignature signature = new LineGraphSignature(lineGraph);
        System.out.println(signature);
    }
    
    @Test
    public void plainNapthaleneSymmetryClasses() {
        IMolecule napthalene = RingMoleculeFactory.makePlainNapthalene();
        LineGraph lineGraph = new LineGraph(napthalene, true);
        System.out.println(lineGraph);
        LineGraphSignature signature = new LineGraphSignature(lineGraph);
        System.out.println(signature);
        for (SymmetryClass symmetryClass : signature.getSymmetryClasses()) {
            System.out.println(symmetryClass);
        }
    }
    
    @Test
    public void doubleBondedNapthaleneSymmetryClasses() {
        IMolecule napthalene = 
            RingMoleculeFactory.makeNapthaleneWithSomeDoubleBonds();
        LineGraph lineGraph = new LineGraph(napthalene, false);
        System.out.println(lineGraph);
        LineGraphSignature signature = new LineGraphSignature(lineGraph);
        System.out.println(signature);
        for (SymmetryClass symmetryClass : signature.getSymmetryClasses()) {
            System.out.println(symmetryClass);
        }
    }

}
