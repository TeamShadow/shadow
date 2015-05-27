package shadow.parser.javacc;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import shadow.tac.nodes.TACOperand;

public class DimensionNode extends SimpleNode {	
    private List<Integer> arrayDimensions = new ArrayList<Integer>();
    private int currentDimensions = 1;	
	
	public DimensionNode(int id) {
    	super(id);
    }
    
    public DimensionNode(ShadowParser sp, int id) {
    	super(sp, id);
    }
    
    public List<Integer> getArrayDimensions() {
  	  return arrayDimensions;
    }

    public void incrementDimensions() {
  	  currentDimensions++;  
    }
    
    public void pushDimensions() {
  	  arrayDimensions.add(currentDimensions);
  	  currentDimensions = 1;
    }
    
    public int getTotalDimensions() {
    	int total = 0;
    	for( int value : arrayDimensions )
    		total += value;
    	return total;
    }
}
