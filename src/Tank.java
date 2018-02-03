import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;

public class Tank extends GameObject{

	TankType t;


	Terrain terrain;
	double angle = 0;
	

	public Tank(int x, int y, Terrain terrain, TankType t){
		affectedByGravity = true;
		canMove = true;
		super.x = x;
		super.y = y;
		this.t = t;
		this.terrain = terrain;
		//applying hitbox
		hitboxes.add(new Hitbox(super.x + t.w/2,super.y+t.h-terrain.spacer,terrain.spacer,terrain.spacer,0, ObjectType.TANK_SIMPLE)); //movement hitbox detection to terrain
	}

	@Override
	void contact(GameObject o, GameObject o2, Hitbox h1, Hitbox h2) {
		// TODO Auto-generated method stub
		if(o2.setForTermination) { return; }
		if(h1.indexInArray==0){ // movement hitbox
			//finding if move tank is possible
			if(Math.abs(h2.y-h1.y) < t.h * 1.5){ //possible move as terrain is lower than tank height + Buffer
				//moving y position to facet in terrain
				y = h2.y - terrain.spacer;
				
				
				
			}else{
				//impossible , cannot move tank
				canMove = false;
				if(dx > 0){
					x -= terrain.spacer;
				}else{
					x += terrain.spacer;
				}
				
			}
			
		}
	}

	@Override
	void draw(Graphics g) {
		// TODO Auto-generated method stub
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform origin = g2d.getTransform(); //flipping tank to angle
		g2d.translate(x + t.w /2, y + t.h /2); //translating to center
		g2d.rotate(angle);
		g2d.setColor(Color.BLACK);
		g2d.fillRect(-t.w/2, -t.h, t.w, t.h); 
		g2d.setColor(Color.WHITE);
		for(int i = 0; i < hitboxes.size(); i++){
			Hitbox r1 = hitboxes.get(i);
			g2d.drawRect(0,0, r1.width, r1.height);
		}
		g2d.setTransform(origin); //reseting transform
	}

	@Override
	void updateHitbox() {
		// TODO Auto-generated method stub
		
		Hitbox movementBox = hitboxes.get(0);
		//move hitbox to right bounds if dx > 0 and vice versa
		if(dx > 0){
			movementBox.setLocation(super.x + t.w - terrain.spacer,super.y+t.h);
		}else if( dx < 0){
			movementBox.setLocation(super.x,super.y+ t.h);
		}else{
			movementBox.setLocation(super.x + t.w/2,super.y+ t.h);
		}

	}

}
