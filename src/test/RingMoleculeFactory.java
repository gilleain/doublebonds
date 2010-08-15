package test;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.nonotify.NoNotificationChemObjectBuilder;

public class RingMoleculeFactory {
    
    private static IChemObjectBuilder builder = 
        NoNotificationChemObjectBuilder.getInstance();
    
    public static IMolecule makePlainNapthalene() {
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
    
    public static IMolecule makeNapthaleneWithSomeDoubleBonds() {
        IMolecule napthalene = makePlainNapthalene();
        napthalene.getBond(0).setOrder(IBond.Order.DOUBLE);
        napthalene.getBond(4).setOrder(IBond.Order.DOUBLE);
        napthalene.getBond(7).setOrder(IBond.Order.DOUBLE);
        napthalene.getBond(8).setOrder(IBond.Order.DOUBLE);
        napthalene.getBond(10).setOrder(IBond.Order.DOUBLE);
        return napthalene;
    }
    
    public static IMolecule makeFusedThreeFourFiveRingSystem() {
        IMolecule fused = builder.newInstance(IMolecule.class);
        for (int i = 0; i < 8; i++) {
            fused.addAtom(builder.newInstance(IAtom.class, "C"));
        }
        fused.addBond(0, 1, IBond.Order.SINGLE);
        fused.addBond(0, 4, IBond.Order.SINGLE);
        fused.addBond(1, 2, IBond.Order.SINGLE);
        fused.addBond(1, 7, IBond.Order.SINGLE);
        fused.addBond(2, 3, IBond.Order.SINGLE);
        fused.addBond(2, 7, IBond.Order.SINGLE);
        fused.addBond(3, 4, IBond.Order.SINGLE);
        fused.addBond(3, 6, IBond.Order.SINGLE);
        fused.addBond(4, 5, IBond.Order.SINGLE);
        fused.addBond(5, 6, IBond.Order.SINGLE);
        return fused;
    }

}
