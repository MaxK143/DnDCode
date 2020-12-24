
public class DiceStatsMultiThreading {

	public static void main(String[] args) {
		try {
			
			int StartTime = (int) System.currentTimeMillis() ;
			
			DiceStatsThread Tree = new DiceStatsThread( 1000 , 3 , 5 ) ;
			
			while( Tree.getRunning() ) { Thread.sleep( 10 ); }
			
			double[][] Method = DiceSet.ToPercent( Tree.getStats() ) ;

			double[] AverageStats = Method[0] ;
			
			double[] AverageOccurances = Method[1] ;
			
			double[] Occurances = Method[2] ;
			
			double[] OneOccurances = Method[3] ;
			
			System.out.print( "Run Time: " + ( System.currentTimeMillis() - StartTime ) + " MilliSeconds" ) ;
			
			System.out.print( "\nAverageStats: " ) ;
			for( double i : AverageStats ) {
				System.out.print( i + ", " ) ;
			}

			System.out.print( "\nAverageOccurances: " ) ;
			for( double i : AverageOccurances ) {
				System.out.print( i + ", " ) ;
			}

			System.out.print( "\nOccurances: " ) ;
			for( double i : Occurances ) {
				System.out.print( i + ", " ) ;
			}

			System.out.print( "\nOneOccurances: " ) ;
			for( double i : OneOccurances ) {
				System.out.print( i + ", " ) ;
			}
			
		}catch( Throwable ex ) {
			
			ex.printStackTrace() ;
			
			System.exit(1) ;
			
		}finally {
			
			System.out.print( "\n\n\nCode Done" ) ;
			
			System.runFinalization() ;
			
			System.exit(0) ;
			
		}
	}
}
