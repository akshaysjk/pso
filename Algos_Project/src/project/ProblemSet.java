
package project;

/**
 *
 * @author ayush
 */
public class ProblemSet {
	public static final double LOC_X_LOW = 1;
	public static final double LOC_X_HIGH = 4;
	public static final double LOC_Y_LOW = 1;
	public static final double LOC_Y_HIGH = 3;
	public static final double VEL_LOW = 0;
	public static final double VEL_HIGH = 2;
	
	 
	                                                  
	
	public static double evaluate(Location location) {
		double result = 0;
		double x1 = location.getLoc()[0]; // the "x" part of the location
		double y1 = location.getLoc()[1]; // the "y" part of the location
                
                double x2 = 1200; // the "x" part of the distanation
		double y2 = 400; // the "y" part of the distanation
                 
                double distance = Math.sqrt(Math.abs(Math.pow(x2-x1,2)+Math.pow(y2-y1,2)));
		

		
		return distance;
	}
}
