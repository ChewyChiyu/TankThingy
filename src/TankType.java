
public enum TankType {
	SIMPLE(50,30,5);
	
	int w,h,speed;
	private TankType(int w, int h, int speed){
		this.w = w;
		this.h = h;
		this.speed = speed;
	}
}
