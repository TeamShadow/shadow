package shadow.parser;

import java.util.ArrayList;
import java.util.List;

import shadow.parser.javacc.ShadowParser;

public class DimensionNode extends SimpleNode {


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
