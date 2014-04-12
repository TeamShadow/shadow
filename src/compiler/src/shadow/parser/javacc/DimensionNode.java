package shadow.parser.javacc;

import java.util.ArrayList;
import java.util.List;

public class DimensionNode extends SignatureNode {

	
	public DimensionNode(int id) {
    	super(id);
    }
    
    public DimensionNode(ShadowParser sp, int id) {
    	super(sp, id);
    }
	
    private List<Integer> arrayDimensions = new ArrayList<Integer>();
    private int currentDimensions = 1;
    
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
}
