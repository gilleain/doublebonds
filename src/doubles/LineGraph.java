package doubles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;

public class LineGraph {
    
    private class Node {
        
        public IBond.Order order;
        
        public IAtom atomA;
        
        public IAtom atomB;
        
        public Node(IBond.Order order, IAtom atomA, IAtom atomB) {
            this.order = order;
            this.atomA = atomA;
            this.atomB = atomB;
        }
        
        public String toString() {
            return "" + this.order;
        }
        
    }
    
    private List<Node> nodes;
    
    private List<List<Integer>> connectionTable;
    
    public LineGraph(IAtomContainer atomContainer, boolean eraseBondOrders) {
        nodes = new ArrayList<Node>();
        Map<IBond, Integer> nodeMap = new HashMap<IBond, Integer>();
        for (IBond bond : atomContainer.bonds()) {
            IAtom atomA = bond.getAtom(0);
            IAtom atomB = bond.getAtom(1);
            IBond.Order order = 
                eraseBondOrders? IBond.Order.SINGLE : bond.getOrder();
            Node node = new Node(order, atomA, atomB); 
            nodeMap.put(bond, nodes.size());
            nodes.add(node);
        }
        
        connectionTable = new ArrayList<List<Integer>>();
        for (IBond bond : atomContainer.bonds()) {
            int nodeIndex = nodeMap.get(bond);
            connectionTable.add(getConnected(bond, nodeIndex));
        }
    }
    
    private List<Integer> getConnected(IBond bond, int nodeIndex) {
        Node node = nodes.get(nodeIndex);
        List<Integer> connected = new ArrayList<Integer>();
        int otherIndex = 0;
        for (Node otherNode : nodes) {
            if (node != otherNode) {
                if (node.atomA == otherNode.atomA 
                        || node.atomA == otherNode.atomB
                        || node.atomB == otherNode.atomA
                        || node.atomB == otherNode.atomB) {
                    connected.add(otherIndex);
                }
            }
            otherIndex++;
        }
        return connected;
    }
    
    public int getNodeCount() {
        return nodes.size();
    }
    
    public String getNodeSymbol(int nodeIndex) {
        Node node = nodes.get(nodeIndex);
        switch (node.order) {
            case SINGLE : return "-";
            case DOUBLE : return "=";
            case TRIPLE : return "#";
            default : return "";
        }
    }
    
    public int[] getConnected(int nodeIndex) {
        List<Integer> connectedList = connectionTable.get(nodeIndex);
        int[] connectedArray = new int[connectedList.size()];
        int index = 0;
        for (Integer connected : connectedList) {
            connectedArray[index] = connected;
            index++;
        }
        return connectedArray;
     }
    
    public String toString() {
       return connectionTable.toString();
    }

}
