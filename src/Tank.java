import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;

public class Tank extends GameObject{

	TankType t;


	Terrain terrain;
	double angle = 0;
	Point p1 = new Point();
	Point p2 = new Point();

	public Tank(int x, int y, Terrain terrain, TankType t){
		affectedByGravity = true;
		canMove = true;
		super.x = x;
		super.y = y;
		this.t = t;
		this.terrain = terrain;
		//applying hitbox
		hitboxes.add(new Hitbox(x,y,t.w,t.h,0,ObjectType.TANK_SIMPLE)); // hitbox for combat detection
	}

	@Override
	void contact(Hitbox h1, Hitbox h2) {
		// TODO Auto-generated method stub
		//finding angle of tank in relation to terrain
		int heightPushUp = 0;
		int xPosBuffer = 0;
		final int Buffer = 5;
		for(int i = 0; i < terrain.hitboxes.size(); i ++){ 
			if(i==h2.indexInArray){
				p2.setLocation(terrain.hitboxes.get(i).x,terrain.hitboxes.get(i).y); //getting new location of p2
				heightPushUp = terrain.hitboxes.get(i).y - t.h + Buffer; //distance from tank to terrain
				xPosBuffer = terrain.hitboxes.get(i).x;
			} 
		}
		double slope = -( p2.getY() - p1.getY() ) /  ( p2.getX() - p1.getX() );
		System.out.println(Math.abs(p2.getY()-p1.getY()));
		if(Math.abs(p2.getY()-p1.getY()) < ( t.h * 1.5 ) || Math.toDegrees(slope) < 60){ // jump isnt so big / realistic angle
			if(Math.abs(angle - Math.toDegrees(slope)) > 25){ //decrease sensi and realistic slope
				p1.setLocation(p2.getX(), p2.getY()); //pushing position of previous search down to p1
				System.out.println(Math.abs(angle - Math.toDegrees(slope)));
				angle = -Math.toDegrees(slope);
				y = heightPushUp;
			}
		}else{
			//slope is impossible
			canMove = false; //locking movement until initial release
			if(dx > 0){
				x = xPosBuffer -= t.w;
			}else{
				x = xPosBuffer;
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
		g2d.fillRect(-t.w/2, -t.h/2, t.w, t.h); 
		g2d.setColor(Color.WHITE);
		for(int i = 0; i < hitboxes.size(); i++){
			Hitbox r1 = hitboxes.get(i);
			g2d.drawRect(-r1.width/2, -r1.height/2, r1.width, r1.height);
		}
		g2d.setTransform(origin); //reseting transform
	}

	@Override
	void updateHitbox() {
		// TODO Auto-generated method stub
		hitboxes.get(0).setLocation(x, y); //full body combat detection

	}

}
