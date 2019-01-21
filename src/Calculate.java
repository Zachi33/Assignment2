import edu.cmu.ri.createlab.terk.robot.finch.Finch;

public class Calculate {

	public static Point A, B, C, CP; 
	
	public static float angleIt=0.7f;//in degrees
	public static float dx=1.7f;//in millimeters
	public static double betweenWheels=90;
	public static double toPEN =111;
	
	
	public static double przekatna=119.774789f;
	public static double arc=67.6684d;
	public static double arc2=67.6684d;
	public static Point newC;
	public static Finch myf=null;
	public static Point vectorf=new Point(0,0);
	public static Point vectorb=new Point(0,0);
	public static int state=0;
	public static Point[] map;
	
	
	public static void main(String args[]) {
		
		myf=new Finch();
		A=new Point(-betweenWheels/2,toPEN);
		B=new Point(betweenWheels/2,toPEN);
		C=new Point(0,0);
		przekatna = Math.sqrt(Math.pow(betweenWheels/2, 2)+Math.pow(toPEN, 2));//the same length
		arc=getAngle(A,C)-90;
		arc2=getAngle(B,C)-90;
		
		map=new Point[8];
		map[0]=new Point(0,-200);
		map[1]=new Point(200,-200);
		map[2]=new Point(200,-400);
		map[3]=new Point(400,-400);
		map[4]=new Point(200,-400);
		map[5]=new Point(200,-200);
		map[6]=new Point(0,-200);
		map[7]=new Point(0,-0);
		executer(map);
		

	}
	static void executer(Point[] map) {
	
		LineMenager mgr = new LineMenager(map);
		
		while(!mgr.finish) {
			
			
			Point next=mgr.getNext(C,dx);
			
			
		int choice= choose(possibilities(A,B,C,getAngle(A,B)),next);
		update(choice);
		
		int t= 25;
		switch(choice) {
		case(1):{
			myf.setWheelVelocities(0, -60,t);
			//myf.setWheelVelocities(0, 0,10);
			break;}
		case(2):{
			myf.setWheelVelocities(0, 60,t);
			//myf.setWheelVelocities(0, 0,10);
			break;}
		case(3):{
			myf.setWheelVelocities(60, 0,t);
			//myf.setWheelVelocities(0, 0,10);
			break;}
		case(4):{
			myf.setWheelVelocities(-60, 0,t);
			//myf.setWheelVelocities(0, 0,10);
			break;}
		case(5):{
			myf.setWheelVelocities(60, 60,t);
			//myf.setWheelVelocities(0, 0,10);
			break;}
		case(6):{
			myf.setWheelVelocities(-60, -57,t);
			//myf.setWheelVelocities(0, 0,10);
		break;}
	}
	}
	}
	
	static Point[] possibilities (Point A, Point B, Point C, double angle) {
		Point[] x = new Point[6];
		//pivot A, clockwise
		x[0]=new Point(A.x+Math.cos(Math.toRadians(angle-90+arc+angleIt))*przekatna,A.y+Math.sin(Math.toRadians(angle+90+arc+angleIt))*przekatna);
		
		//pivot A, counterclockwise
		x[1]=new Point(A.x+Math.cos(Math.toRadians(angle-90+arc-angleIt))*przekatna,A.y+Math.sin(Math.toRadians(angle+90+arc-angleIt))*przekatna);
		
		//pivot B, clockwise
		x[2]=new Point(B.x+Math.cos(Math.toRadians(angle-90+arc2+angleIt))*przekatna,B.y+Math.sin(Math.toRadians(angle+90+arc2+angleIt))*przekatna);
				
		//pivot B, counterclockwise
		x[3]=new Point(B.x+Math.cos(Math.toRadians(angle-90+arc2-angleIt))*przekatna,B.y+Math.sin(Math.toRadians(angle+90+arc2-angleIt))*przekatna);
		
		//forward
		vectorf=new Point(Math.cos(Math.toRadians(180-angle))*dx,Math.sin(Math.toRadians(180-angle))*dx);
//		System.out.println(vectorf.x);
//		System.out.println(vectorf.y);
		
		x[4]=new Point(C.x+vectorf.x,C.y+vectorf.y);
		
		//backward
		vectorb=new Point(-vectorf.x,-vectorf.y);
		x[5]=new Point(C.x+vectorb.x,C.y+vectorb.y);
		
		return x;
	}
	
	static Point getNew(Point curr) {
		Point w=new Point(0,0);
		switch(state) {
		case(0):{
			w=new Point(curr.x+dx,0);
			if(curr.x>50)state=1;
			break;}
		case(1):{
			w=new Point(50-curr.y/5,curr.y-dx);
			if(curr.y<-50)state=2;
			break;}
		case(2):{
			w=new Point(curr.x+dx,-50);
			if(curr.x>100)state=3;
			break;}
		case(3):{
			w=new Point(100,curr.y-dx);
			if(curr.y<-100)state=4;
			break;}
		case(4):{
			w=new Point(curr.x+dx,-100);
			if(curr.x>150)state=5;
			break;}
		}
		
		
		
		return w;
	}
	
	static void update(int choice) {
		//pivot A
		if(choice==1||choice==2) {
			C=newC;
			if(choice==1) {
				
				B.x=A.x+Math.cos(Math.toRadians(getAngle(A,B)-90+angleIt))*90;
				B.y=A.y+Math.sin(Math.toRadians(getAngle(A,B)+90+angleIt))*90;
			}
			else {
				B.x=A.x+Math.cos(Math.toRadians(getAngle(A,B)-90-angleIt))*90;
				B.y=A.y+Math.sin(Math.toRadians(getAngle(A,B)+90-angleIt))*90;
				
			}
		}
		//pivot B
		else if(choice==3||choice==4){
			C=newC;
			if(choice==3) {
				A.x=B.x-Math.cos(Math.toRadians(getAngle(A,B)-90+angleIt))*90;
				A.y=B.y-Math.sin(Math.toRadians(getAngle(A,B)+90+angleIt))*90;
				
			}
			else {
				A.x=B.x-Math.cos(Math.toRadians(getAngle(A,B)-90-angleIt))*90;
				A.y=B.y-Math.sin(Math.toRadians(getAngle(A,B)+90-angleIt))*90;
			}
		}
		else {
			C=newC;
			if(choice==5) {
				A.x+=vectorf.x;
				A.y+=vectorf.y;
				B.x+=vectorf.x;
				B.y+=vectorf.y;
				
			}
			else {
				A.x+=vectorb.x;
				A.y+=vectorb.y;
				B.x+=vectorb.x;
				B.y+=vectorb.y;
			}
		}
	}
	
	static int choose(Point[] possible, Point CP) {
		double d1=dist(possible[0],CP);
		double d2=dist(possible[1],CP);
		double d3=dist(possible[2],CP);
		double d4=dist(possible[3],CP);
		double d5=dist(possible[4],CP);
		double d6=dist(possible[5],CP);

		
		int out=-1;
		
		if(d4<=d1 && d4<=d2&& d4<=d3&& d4<=d5&& d4<=d6) {out=4;newC=possible[3];}
		else if(d3<=d1&&d3<=d2&&d3<=d4&&d3<=d5&& d3<=d6) {out=3;newC=possible[2];}
		else if(d2<=d1&&d2<=d4&&d2<=d3&&d2<=d5&& d2<=d6) {out=2;newC=possible[1];}
		else if(d1<=d4&&d1<=d2&&d1<=d3&&d1<=d5&& d1<=d6) {out=1;newC=possible[0];}
		else if(d5<=d1&&d5<=d4&&d5<=d3&&d5<=d2&& d5<=d6) {out=5;newC=possible[4];}
		else if(d6<=d4&&d6<=d2&&d6<=d3&&d6<=d5&& d6<=d1) {out=6;newC=possible[5];}
		
		
		return out;
	}
	
	static double dist(Point A, Point B) {
		
		return Math.sqrt(Math.pow(A.x-B.x, 2)+Math.pow(A.y-B.y, 2));
	}
	
	static double getAngle(Point A, Point B) {
		double rad= Math.atan2((B.x-A.x), (B.y-A.y));
		return ((rad/(Math.PI*2))*360);
		
	}
	
}

class Point{
	public Point(double x, double y) {
		this.x=x;
		this.y=y;
	}
	public double x=0;
	public double y=0;
}

class LineMenager{
	
	public boolean finish=false;
	private Line[] lines;
	int linia=0;
	public double olddist=1000000000;
	
	public LineMenager(Point[] map) {
		this.lines=createLines(map);
		
		
	}
	private Line[] createLines(Point[] map) {
		
		Line[] out = new Line[map.length];
		
		for(int i =0; i<map.length;i++)
		{
			if(i==0) 
			out[0]=new Line(new Point(0,0), map[0]);
			else 
				out[i]=new Line(map[i-1], map[i]);
			
		}

		
		return out;
	}
	public Point getNext(Point curr, double dx) 
	{
 
		
		Point near=nearest(lines[linia],curr);
		Point w= translated(near,dx,lines[linia]);
		double distance = dist(curr,lines[linia].B);
		if(distance<dx) {linia++;}
		if(linia==lines.length)finish=true;
	
//		System.out.println("***********************************");
//		System.out.println("Linia nr "+linia+": (a: "+lines[linia].a+", b:"+lines[linia].b+")");
//		System.out.println("Cel tej linii: (x:"+lines[linia].B.x+", y:"+lines[linia].B.y+")");
//		System.out.println("Nearest: (x:"+near.x+", y:"+near.y+")");
//		System.out.println("Translated: (x:"+w.x+", y:"+w.y+")");
//		System.out.println("Curr: (x:"+curr.x+", y:"+curr.y+")");
//		System.out.println("Distance od curr do celu:"+distance);
//		System.out.println("***********************************");
//		System.out.println("");
		
		return w;
	}
	private Point nearest(Line line, Point current) {
		
		Line perpendicular = new Line(line, current);
		
		return common(perpendicular, line);
		
		}
	private Point translated(Point old, double dx, Line curr) {
		
		//System.out.println(Math.cos(Math.atan(curr.a)));
		
		double x1 =old.x + dx*Math.cos(Math.atan(curr.a));
		double x2 =old.x - dx*Math.cos(Math.atan(curr.a));
		
		if(dist(curr.B,new Point(x1,curr.getY(x1)))>=dist(curr.B,new Point(x2,curr.getY(x2))))
		return new Point(x2,curr.getY(x2));
		else
			return new Point(x1,curr.getY(x1));
	}
    public Point common(Line A, Line B) {
		
		double x = (B.b-A.b)/(A.a-B.a);
		
		return new Point(x,x*A.a+A.b);
	}
    static double dist(Point A, Point B) {
	
	return Math.sqrt(Math.pow(A.x-B.x, 2)+Math.pow(A.y-B.y, 2));
}
}
class Line{
	public double a=0;
	public double b=0;
	public boolean direction=false;
	public Point B=null;
	
	public Line(Line perp, Point through) {
		this.a=-(1/perp.a);
		this.b=through.y-(this.a*through.x);	
	}
	public Line(Point A, Point B) {
		if(A.x==B.x)B.x+=0.001d;
		if(A.y==B.y)B.y+=0.001d;
		this.B=B;
		this.a=(A.y-B.y)/(A.x-B.x);
		this.b=A.y-(a*A.x);	
		if(B.x>A.x)direction=true;
	}
	public double getY(double x) {
		
		return this.a*x+this.b;
	}
	
	
	
}

