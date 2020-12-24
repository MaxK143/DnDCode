import java.util.ArrayList;

public class DiceStatsThread implements Runnable {

	private Thread thread ;
	private boolean running = false ;
	
	private int SampleSizeOne = 0 ;
	private int SampleSizeTwo = 0 ;  // Overall Sample Size is ( One ^ Two ) * Three
	private int SampleSizeThree = 0 ; // Thread Count
	
	private boolean isThreading ;
	
	private ArrayList<DiceStatsThread> Threads = new ArrayList<DiceStatsThread>() ;
	
	private double[][] Stats = null ;
	
	public DiceStatsThread( int SampleSizeOne , int SampleSizeTwo , int SampleSizeThree ) {
		this.SampleSizeOne = SampleSizeOne ;
		this.SampleSizeTwo = SampleSizeTwo ;
		this.SampleSizeThree = SampleSizeThree ;
		this.isThreading = true ;
		this.start() ;
	}
	
	public DiceStatsThread( int SampleSizeOne , int SampleSizeTwo , int SampleSizeThree , boolean isThreading ) {
		this.SampleSizeOne = SampleSizeOne ;
		this.SampleSizeTwo = SampleSizeTwo ;
		this.SampleSizeThree = SampleSizeThree ;
		this.isThreading = isThreading ;
		this.start() ;
	}

	public void run() {
		
		if( isThreading ) {
			
			int index = 0 ;
			while ( index < SampleSizeThree ) {
				
				Threads.add( new DiceStatsThread( SampleSizeOne , SampleSizeTwo, 0 , false) ) ;
				
				index++ ;
			}
			
			boolean flag = true ;
			
			while( flag ) {
				flag = false ;
				for( DiceStatsThread i : Threads ) {
					if( i.getRunning() ) {
						try { Thread.sleep( 10 ) ; } catch (InterruptedException e) { e.printStackTrace(); }
						flag = true ;
					}
				}
			}
			
			double[][][] Jesus = new double[ SampleSizeThree ][4][16] ;
			
			for( int ind = 0 ; ind < SampleSizeThree ; ind++ ) {
				Jesus[ind] = Threads.get(ind).getStats() ;
			}
			
			double[][] Ret = new double[4][16] ;
			
			for( int index1 = 0 ; index1 < SampleSizeThree ; index1++ ) {
				for( int index2 = 0 ; index2 < 4 ; index2++ ) {
					for( int index3 = 0 ; index3 < 16 ; index3++ ) {
						Ret[index2][index3] += Jesus[index1][index2][index3] / SampleSizeThree ;
					}
				}
			}
			
			Stats = Ret ;
			
		}else{
			Stats = DiceSet.RunStats( SampleSizeOne , SampleSizeTwo ) ;
		}
		
		this.stop();
	}
	
	public boolean getRunning() {
		return running ;
	}
	
	public double[][] getStats() {
		if( Stats == null || running ) {
			throw new NullPointerException() ;
		}
		return Stats ;
	}
	
	public void start(){
		if(running) { return; }
		running = true ;
		thread = new Thread(this) ;
		thread.start() ;
	}
	
	public void stop(){
		if(!running) return ;
			running = false ;
		try{ thread.join() ;
		} catch(InterruptedException e){ e.printStackTrace() ; }
	}

}
