import java.util.Arrays;

public class DiceTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		int SampleSize = 1000000 ;
		
		long StartTime = System.currentTimeMillis() ;
		
		System.out.println( "Expected Approximate Runtime: " + (int)( ( (double) SampleSize / 10000) * 6 ) + " MilliSeconds" ) ;
		
		int index = 0 ;
		
		double average = 0 ;
		
		int[] Stats =  new int[6] ;
		
		double[] AverageStats = new double[6] ;
		
		int[] Occurances = new int[16] ;
		
		int[] AverageOccurances = new int[16] ;
		
		int[] OneOccurances = new int[16] ;
		
		int[] temp = OneOccurances ;
		
		for( double i : AverageStats ) {
			i = 0 ;
		}
		
		for( int i : AverageOccurances ) {
			i = 0 ;
		}
		
		for( int i : Occurances ) {
			i = 0 ;
		}
		
		for( int i : OneOccurances ) {
			i = 0 ;
		}
		
		while( index < SampleSize ) {
			
			Stats = DiceSet.RollSet() ;
			
			Arrays.sort( Stats ) ;
			
			temp = OneOccurances.clone() ;
			
			average = 0 ;
			
			for( int i = 0 ; i < Stats.length ; i++ ) {
				
				average += Stats[i] ;
				
				AverageStats[i] += ( (double) Stats[i] ) / SampleSize ;
				
				Occurances[ Stats[i] - 3 ]++ ;
				
				if( temp[ Stats[i] - 3 ] == OneOccurances[ Stats[i] - 3 ] ) {
					OneOccurances[ Stats[i] - 3 ]++ ;
				}
			}
			
			AverageOccurances[ (int)( ( average / 6 ) + 0.5 ) - 3 ]++ ;
			
			index++ ;
		}
		
		System.out.println( "Sample Size: " + SampleSize ) ;
		
		System.out.print( "Run Time: " + ( System.currentTimeMillis() - StartTime ) + " MilliSeconds" ) ;

		System.out.print( "\nAverageStats: " ) ;
		for( double i : AverageStats ) {
			System.out.print( i + ", " );
		}

		System.out.print( "\nAverageOccurances: " ) ;
		for( int i : AverageOccurances ) {
			System.out.print( i + ", " );
		}

		System.out.print( "\nOccurances: " ) ;
		for( int i : Occurances ) {
			System.out.print( i + ", " );
		}

		System.out.print( "\nOneOccurances: " ) ;
		for( int i : OneOccurances ) {
			System.out.print( i + ", " );
		}
		
		System.out.print( "\n\n\nCode Done" ) ;
		
		System.exit(0) ;
		
	}

}