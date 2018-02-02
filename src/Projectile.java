import java.awt.Graphics;

public class Projectile extends GameObject{
	
	ProjectileType type;
	double angle; 
	
	public Projectile(int x, int y, double angle, ProjectileType p){
		affectedByGravity = true;
		type = p;
		super.x = x;
		super.y = y;
		this.angle = angle;
		hitboxes.add(new Hitbox(x,y,type.w,type.h,0,ObjectType.PROJECTILE_SIMPLE));
	}


	@Override
	void draw(Graphics g) {
		// TODO Auto-generated method stub
		g.drawOval(x, y, type.w, type.h);
	}


	@Override
	void updateHitbox() {
		hitboxes.get(0).setLocation(x, y); //only one hitbox so yea
	}


	@Override
	void contact(Hitbox h1, Hitbox h2) { //h1 is self so not needed
		// TODO Auto-generated method stub
		if(h2.o.equals(ObjectType.FACET)){ //impacted facet
			setForTermination = true; //impacted bullet
		}
	}
}
