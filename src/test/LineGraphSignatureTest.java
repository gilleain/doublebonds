package test;

import org.junit.Test;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.nonotify.NoNotificationChemObjectBuilder;
import org.openscience.cdk.templates.MoleculeFactory;

import signature.SymmetryClass;

import doubles.LineGraph;
import doubles.LineGraphSignature;

public class LineGraphSignatureTest {
    
    private IChemObjectBuilder builder = 
        NoNotificationChemObjectBuilder.getInstance();
    
    public IMolecule makePlainNapthalene() {
        IMolecule napthalene = builder.newInstance(IMolecule.class);
        for (int i = 0; i < 10; i++) {
            napthalene.addAtom(builder.newInstance(IAtom.class, "C"));
        }
        napthalene.addBond(0, 1, IBond.Order.SINGLE);
        napthalene.addBond(0, 5, IBond.Order.SINGLE);
        napthalene.addBond(1, 2, IBond.Order.SINGLE);
        napthalene.addBond(1, 6, IBond.Order.SINGLE);
        napthalene.addBond(2, 3, IBond.Order.SINGLE);
        napthalene.addBond(2, 9, IBond.Order.SINGLE);
        napthalene.addBond(3, 4, IBond.Order.SINGLE);
        napthalene.addBond(4, 5, IBond.Order.SINGLE);
        napthalene.addBond(6, 7, IBond.Order.SINGLE);
        napthalene.addBond(7, 8, IBond.Order.SINGLE);
        napthalene.addBond(8, 9, IBond.Order.SINGLE);
        return napthalene;
    }
    
    public IMolecule makeNapthaleneWithSomeDoubleBonds() {
        IMolecule napthalene = makePlainNapthalene();
        napthalene.getBond(0).setOrder(IBond.Order.DOUBLE);
        napthalene.getBond(4).setOrder(IBond.Order.DOUBLE);
        napthalene.getBond(7).setOrder(IBond.Order.DOUBLE);
        napthalene.getBond(8).setOrder(IBond.Order.DOUBLE);
        napthalene.getBond(10).setOrder(IBond.Order.DOUBLE);
        return napthalene;
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
        IMolecule napthalene = makePlainNapthalene();
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
        IMolecule napthalene = makeNapthaleneWithSomeDoubleBonds();
        LineGraph lineGraph = new LineGraph(napthalene, false);
        System.out.println(lineGraph);
        LineGraphSignature signature = new LineGraphSignature(lineGraph);
        System.out.println(signature);
        for (SymmetryClass symmetryClass : signature.getSymmetryClasses()) {
            System.out.println(symmetryClass);
        }
    }

}
