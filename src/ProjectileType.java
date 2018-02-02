
public enum ProjectileType {
	SIMPLE(10,10,10);
	
	int w,h,power;
	private ProjectileType(int w, int h, int power){
		this.w = w;
		this.h = h;
		this.power = power;
	}
}
