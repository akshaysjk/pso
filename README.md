# pso
A Simulated Model Multi Objective Optimal Trajectory Planning of Space Robot Using Particle Swarm Optimization
Space robots are playing significant roles in the maintenance and repair of space station and satellites and other future space services. To obtain the high efficiency, safety motion trajectory of space robot, the motion trajectory should be optimized in advance. For space robot system, optimizing the motion path becomes an important problem to minimize multiple objectives simultaneously, such as minimizing the disturbances or avoidance of the obstacle, minimizing the acceleration such that robots move with a velocity constraint and minimizing the traveling time, and so on. Therefore, all these optimal objectives are considered together to build a multi-objective function and the results depend on the associated weighting factors. Here, I have considered two objective function first minimization disturbance of space robot to the space base and minimization traveling time of space robot.
Multi objective trajectory optimization problem can be defined as 
        				   MinF(x) = ω1f1(x) + ω2f2(x)
Where f1 and f2 are the two objective functions considered and w1 and w2 are the weights associated with them
The main objective function is minimization of the distance between the robot and the destination space station. The minimal distance is calculated and given by the formula.
Distance D(x1,y1,x2,y2) =√(2&〖(x2-x1)〗^2+〖(y2-y1)〗^2 )
 

Secondly, the aim of time-optimal trajectory planning is the determination of the maximum velocity profile along a given path that complies with all given dynamic and kinematic robotic constraints. The motion of space robot can be denoted as a position vector, l, which starts from the starting point, p0, to the end point, pf, v is the velocity at that interval
  T= ∫_to^tf▒1/v ds,s Є[p0,pf]                                                           ….2

Particle velocity is calculated regarding the following formula
Across x and y direction.
Where w is calculated based on the number of iterations.  r1,c1,r2,c2 are the coffcients associated with the problem. 
newVel[0] = (w * p.getVelocity().getPos()[0]) + (r1 * C1) * (pBestLocation.get(i).getLoc()[0] - p.getLocation().getLoc()[0]) + (r2 * C2) * (gBestLocation.getLoc()[0] - p.getLocation().getLoc()[0])

newVel[1] = (w * p.getVelocity().getPos()[1]) + (r1 * C1) * (pBestLocation.get(i).getLoc()[1] - p.getLocation().getLoc()[1]) +(r2 * C2) * (gBestLocation.getLoc()[1] - p.getLocation().getLoc()[1])
I have considered the motion velocity of robot in the (x, y) plane Here I have restricted the limit of the motion of the particle to have a maximum velocity in a direction to be set to 5.0
Regarding the path of the robot across space station to avoid the obstacles coming in the way is given the highest possible factor over all the other factors. Here the emotion of the particle is tested, that in near future its path is going to match the obstacle based on this result necessary actions are performed to avoid the collision with the obstacle
Based on this objective function and using the particle swarm optimization technique generated the possible optimal trajectory path of the space robot, taking particle iterations of motion of robots or drones in the field and by using the particle local optimal and global optimal solution generated the optimized trajectory path.
Based on the analysis of pso over a different range of size of the swarm particles considering all the conditions such as minimum acceleration by imposing velocity limit, then obstacle avoidance, the optimized result with quick trajectory path comes out to be for about 200-300 iterations with the swarm size of about ranging from 30-50 particles.

Implemented in Java using Applets for Animations

