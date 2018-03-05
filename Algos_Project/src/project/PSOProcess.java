
package project;

import java.applet.Applet;
import java.awt.Color;
import static java.awt.Color.BLACK;
import static java.awt.Color.BLUE;
import static java.awt.Color.GREEN;
import static java.awt.Color.PINK;
import static java.awt.Color.RED;
import static java.awt.Color.WHITE;
import static java.awt.Color.YELLOW;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

/**
 *
 * @author ayush
 */
public class PSOProcess extends Applet implements PSOConstants,Runnable {
	private Vector<Particle> swarm = new Vector<Particle>();
	private double[] pBest = new double[SWARM_SIZE];
	private Vector<Location> pBestLocation = new Vector<Location>();
	private double gBest;
	private Location gBestLocation;
	private double[] fitnessValueList = new double[SWARM_SIZE];
        private Vector<Location> gBestLocationList = new Vector<Location>();
        private ArrayList<Integer> obstaclex = new ArrayList<Integer>();
        private ArrayList<Integer> obstacley = new ArrayList<Integer>();
        private int obsx[]={600,500,100,700,250,800,900,1000,1100,400,650,900,250,450,750,950};
        private int obsy[] = {400,400,50,100,100,200,250,300,350,200,300,500,500,600,700,550};
        private boolean pathFlag = false;
    Color[] colors = new Color[]{BLUE};

    static int width, height; // variables for applet dimensions


        Random random = new Random(); // random number generator
	Random generator = new Random();
        
        public void init()
        {
            obstaclex.addAll(Arrays.asList(125,700,250,800,900,1000,1100,400,650,900,250,450,750,950,600,500));
            obstacley.addAll(Arrays.asList(50,100,100,200,250,300,350,200,300,500,500,600,700,550,400,400));
             initializeSwarm();
	     updateFitnessList();
             
             width = 900;
             height = 900;
        }
        
        public void start() {

        for (Particle p: swarm) {

            Thread t;
            t = new Thread(this);
            t.start();

        } // end for

    }
	
	public void run() {
		
		
		for(int i=0; i<SWARM_SIZE; i++) {
			pBest[i] = fitnessValueList[i];
			pBestLocation.add(swarm.get(i).getLocation());
		}
		
		int t = 0;
		double w;
		double err = 9999;
		
		while(t < MAX_ITERATION && err > 0) {
			// step 1 - update pBest
			for(int i=0; i<SWARM_SIZE; i++) {
				if(fitnessValueList[i] < pBest[i]) {
					pBest[i] = fitnessValueList[i];
					pBestLocation.set(i, swarm.get(i).getLocation());
				}
			}
				
			// step 2 - update gBest
			int bestParticleIndex = PSOUtility.getMinPos(fitnessValueList);
			if(t == 0 || fitnessValueList[bestParticleIndex] < gBest) {
				gBest = fitnessValueList[bestParticleIndex];
				gBestLocation = swarm.get(bestParticleIndex).getLocation();
                                gBestLocationList.add(gBestLocation);
			}
			
			w = W_UPPERBOUND - (((double) t) / MAX_ITERATION) * (W_UPPERBOUND - W_LOWERBOUND);
			
			for(int i=0; i<SWARM_SIZE; i++) {
				double r1 = generator.nextDouble();
				double r2 = generator.nextDouble();
				
				Particle p = swarm.get(i);
				
				// step 3 - update velocity
				double[] newVel = new double[PROBLEM_DIMENSION];
				newVel[0] = (w * p.getVelocity().getPos()[0]) + 
							(r1 * C1) * (pBestLocation.get(i).getLoc()[0] - p.getLocation().getLoc()[0]) +
							(r2 * C2) * (gBestLocation.getLoc()[0] - p.getLocation().getLoc()[0]);
        //Minimum Acceleration
                                if(newVel[0]>5.0)
                                {
                                    newVel[0]=5.0;
                                }
				newVel[1] = (w * p.getVelocity().getPos()[1]) + 
							(r1 * C1) * (pBestLocation.get(i).getLoc()[1] - p.getLocation().getLoc()[1]) +
      							(r2 * C2) * (gBestLocation.getLoc()[1] - p.getLocation().getLoc()[1]);
        //Minimum Acceleration 
				if(newVel[1]>5.0)
                                {
                                    newVel[1]=5.0;
                                }
                                Velocity vel = new Velocity(newVel);
				p.setVelocity(vel);
				
				// step 4 - update location
				double[] newLoc = new double[PROBLEM_DIMENSION];
				newLoc[0] = p.getLocation().getLoc()[0] + newVel[0];
                                
				newLoc[1] = p.getLocation().getLoc()[1] + newVel[1];
    //Obstacle Avoidance
                                if(((obstaclex.contains((Integer)(int)newLoc[0]))||(obstaclex.contains((Integer)(int)newLoc[0]+5)))&&((obstacley.contains((Integer)(int)newLoc[1]))||(obstacley.contains((Integer)(int)newLoc[1]))))
                                {
                                    newLoc[0] = newLoc[0]-10.0;
                                    newLoc[1] = newLoc[1]-10.0;
                                    
                                }
				Location loc = new Location(newLoc);
				p.setLocation(loc);
			}
			
			err = ProblemSet.evaluate(gBestLocation) - 0; // minimizing the functions means it's getting closer to 0
			
                        repaint(); 

                
                        try {
//                            
//                            Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
//                            Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);
//            for(Thread thread : threadArray)
//            {

//               
//
//            }
                    Thread.sleep(900);         
                             
                        } catch (InterruptedException e) {
                            return;
                        }
			
			System.out.println("ITERATION " + t + ": ");
			System.out.println("     Best X: " + gBestLocation.getLoc()[0]);
			System.out.println("     Best Y: " + gBestLocation.getLoc()[1]);
			System.out.println("     Value: " + ProblemSet.evaluate(gBestLocation));
			
			t++;
			updateFitnessList();
		}
		pathFlag = true;
                repaint();
		System.out.println("\nSolution found at iteration " + (t - 1) + ", the solutions is:");
		System.out.println("     Best X: " + gBestLocation.getLoc()[0]);
		System.out.println("     Best Y: " + gBestLocation.getLoc()[1]);
	}
        
        public void paint(Graphics g)
        {
            resize(2048, 1024);
            setBackground(BLACK);
            super.paint(g);
            int gbestx =0;
            int gbesty = 0;
            try{
            if(gBestLocation.getLoc()[0]!=0.0)
             gbestx=(int)gBestLocation.getLoc()[0];
            if(gBestLocation.getLoc()[1]!=0.0)
            gbesty =(int)gBestLocation.getLoc()[1];
            }
            catch(Exception e)
            {
                System.out.println();
            }
            
// Painting Global Best Optimized Path  

            if(pathFlag)
            {    
            Iterator<Location> it = gBestLocationList.iterator();
            while(it.hasNext())
            {
                Location l =it.next();
                int x = (int)l.getLoc()[0];
                int y = (int)l.getLoc()[1];
                g.setColor(WHITE);
                g.fillRect(x,y,2,2);
            }
            }
            
            g.setColor(YELLOW);
            g.fillOval(gbestx, gbesty, 10, 10);
            
            
            g.setColor(GREEN);
            g.fillRect(1200, 400, 40, 20);
            g.setColor(RED);
            
     //Obstacles in Path    
            for(int i=0;i<obstaclex.size();i++)
            {
                g.fillOval(obstaclex.get(i),obstacley.get(i),5,5);   
            }

            int i=0;
        for (Particle p: swarm) {
            // set current color            
            // draw filled oval using current x and y coordinates and diameter
            double loc[] = p.getLocation().getLoc();
            Long lx = Math.round(loc[0]);
            int x= Integer.valueOf(lx.intValue());
            Long ly = Math.round(loc[1]);
            int y= Integer.valueOf(ly.intValue());
            g.setColor(WHITE);
            int pbestx=(int)pBestLocation.get(i).getLoc()[0];
            int pbesty=(int)pBestLocation.get(i).getLoc()[1];
            // g.drawLine(pbestx, pbesty, gbestx, gbesty);
            g.setColor(RED);
            //  g.drawLine(x,y,pbestx, pbesty);
            g.setColor(PINK);
            g.drawLine(x,y,gbestx, gbesty);
            g.setColor(BLUE);
            g.fillOval(x,y, 5, 5);
            i++;

        }
        }
	
	public void initializeSwarm() {
		Particle p;
		for(int i=0; i<SWARM_SIZE; i++) {
			p = new Particle();
			
			
			double[] loc = new double[PROBLEM_DIMENSION];
			loc[0] = ProblemSet.LOC_X_LOW + generator.nextDouble() * (ProblemSet.LOC_X_HIGH - ProblemSet.LOC_X_LOW);
			loc[1] = ProblemSet.LOC_Y_LOW + generator.nextDouble() * (ProblemSet.LOC_Y_HIGH - ProblemSet.LOC_Y_LOW);
			Location location = new Location(loc);
			
			
			double[] vel = new double[PROBLEM_DIMENSION];
			vel[0] = ProblemSet.VEL_LOW + generator.nextDouble() * (ProblemSet.VEL_HIGH - ProblemSet.VEL_LOW);
			vel[1] = ProblemSet.VEL_LOW + generator.nextDouble() * (ProblemSet.VEL_HIGH - ProblemSet.VEL_LOW);
			Velocity velocity = new Velocity(vel);
			
			p.setLocation(location);
			p.setVelocity(velocity);
			swarm.add(p);
		}
	}
	
	public void updateFitnessList() {
		for(int i=0; i<SWARM_SIZE; i++) {
			fitnessValueList[i] = swarm.get(i).getFitnessValue();
		}
	}
}
