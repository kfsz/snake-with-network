package game;

public class score {
	
private int score=0;
private double multiplier=1;
private double penScore=0;

	score(){};
	
	public void incScore(int a){score+=(int)(multiplier*a);}
	public void incMulti(double a){multiplier+=a;}
	public void penScore(double a){penScore+=a;
		if(penScore>1){incScore(-1); penScore=0;}}
	
	public int retScore(){return score;}
}
