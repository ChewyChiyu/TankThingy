import java.awt.Color;
import java.awt.Graphics;

public class Projectile extends GameObject{
	
	ProjectileType type;
	
	public Projectile(int x, int y, int dx, int dy, ProjectileType p){
		affectedByGravity = true;
		canMove = true;
		type = p;
		super.x = x;
		super.y = y;
		hitboxes.add(new Hitbox(x,y,type.w,type.h,0,ObjectType.PROJECTILE_SIMPLE));
		
		this.dx = dx;
		this.dy = dy;
	}


	@Override
	void draw(Graphics g) {
		// TODO Auto-generated method stub
		if(!setForTermination){
		g.setColor(Color.BLACK);
		g.fillOval(x, y, type.w, type.h);
		}
	}


	@Override
	void updateHitbox() {
		hitboxes.get(0).setLocation(x, y); //only one hitbox so yea
	}


	@Override
	void contact(GameObject o, GameObject o2,Hitbox h1, Hitbox h2) { //h1 is self so not needed
		// TODO Auto-generated method stub
		if(o2.setForTermination) { return; }
		if(h2.o.equals(ObjectType.FACET)){ //impacted facet
			terminate(); //impacted bullet
		}
	}
}
