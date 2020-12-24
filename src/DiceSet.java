import java.io.UncheckedIOException;
import java.util.Arrays;

public class DiceSet {
	
	private final static int DiceSides = 6 ;
	
	private final static int DiceCount = 4 ; //Removes Lowest
	
	private final static int RollCount = 7 ; //Removes Lowest
	
	
	public static double[][] RoundStats( double[][] arr , double round ){
		
		for( int ind = 1 ; ind < arr.length ; ind++ ) {
			for( int i = 0 ; i < arr[ind].length ; i++ ) {
				arr[ind][i] = ( (int) (( arr[ind][i] * round ) + 0.5 ) ) / round ;
			}
		}
		
		return arr ;
		
	}
	
	
	public static double[][] ToPercent( double[][] arr ){
		
		double tot = 0 ;//total amount of stat arrays processed
		
		for( int ind = 0 ; ind < arr[1].length ; ind++ ) {
			tot += arr[1][ind] ;
		}

		for( int i = 6 ; i < arr[0].length ; i++ ) {
			arr[0][i] = -1 ;
		}

		for( int ind = 1 ; ind < arr.length ; ind++ ) {
			for( int i = 0 ; i < arr[0].length ; i++ ) {
				arr[ind][i] = ( arr[ind][i] / tot ) * 100 ;
			}
		}
		
		
		
		return arr ;
	}
	
	
	
	public static double[][] RunStats( int SizeOne , int SizeTwo ) {
		
		double[][] Temp = new double[4][16] ;
		
		double[][] Ret = new double[4][16] ;
		
		for( int ind = 0 ; ind < 4 ; ind++ ) {
			for( int i = 0 ; i < 16 ; i++ ) {
				Ret[ind][i] = 0 ;
			}
		}
		
		int index = 0 ;
		
		if( SizeTwo > 0 ) {
			while( index < SizeOne ) {
				
				Temp = RunStats( SizeOne , SizeTwo - 1 ) ; // This part to start new thread for fork and joining
				
				for( int ind = 0 ; ind < 4 ; ind++ ) {
					for( int i = 0 ; i < 16 ; i++ ) {
						Ret[ind][i] += Temp[ind][i] / SizeOne ;
					}
				}
				
				index++ ;
			}
			
			return Ret ;
			
		} else if( SizeTwo == 0 ) {
			return DiceSet.GetStats( SizeOne ) ;
		} else {
			throw new UncheckedIOException(null) ;
		}
	}
	
	@SuppressWarnings("unused")
	public static double[][] GetStats( int SampleSize ){
		
		double[][] Ret = new double[4][16] ;
		
		for( double[] x : Ret ) {
			for( double y : x ) {
				y = 0 ;
			}
		}

		int index = 0 ;
		
		double average = 0 ;
		
		int[] Stats =  new int[6] ;
		
		double[] AverageStats = new double[6] ;
		
		double[] AverageOccurances = new double[16] ;
		
		double[] Occurances = new double[16] ;
		
		double[] OneOccurances = new double[16] ;
		
		double[] temp = OneOccurances ;
		
		for( double i : AverageStats ) {
			i = 0 ;
		}
		
		for( double i : AverageOccurances ) {
			i = 0 ;
		}
		
		for( double i : Occurances ) {
			i = 0 ;
		}
		
		for( double i : OneOccurances ) {
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
		
		for( int i = 0 ; i < AverageStats.length ; i++ ) {
			Ret[0][i] = AverageStats[i] ;
		}
		
		for( int i = 0 ; i < AverageOccurances.length ; i++ ) {
			Ret[1][i] = AverageOccurances[i] ;
		}
		
		for( int i = 0 ; i < Occurances.length ; i++ ) {
			Ret[2][i] = Occurances[i] ;
		}
		
		for( int i = 0 ; i < OneOccurances.length ; i++ ) {
			Ret[3][i] = OneOccurances[i] ;
		}
		
		return Ret ;
		
	}
	
	public static int[] RollSet() {
		
		int[] Set = new int[RollCount-1] ;

		int min = RollDice() ;
		
		int temp = 0 ;
		
		for( int i = 0 ; i < RollCount-1 ; i++ ) {
			temp = RollDice() ;
			if( temp < min ) {
				Set[i] = min ;
				min = temp ;
			} else {
				Set[i] = temp ;
			}
		}
		
		return Set ;
		
	}
	
	private static int RollDice() {
		
		int DiceTotal = 0 ;
		
		int min = DiceSides + 1 ;
		
		int temp = 0 ;
		
		for( int i = 0 ; i < DiceCount ; i++ ) {
			temp = (int)( Math.random() * ( DiceSides ) ) + 1 ;
			DiceTotal += temp ;
			if( temp < min ) {
				min = temp ;
			}
		}
		
		return DiceTotal - min ;
		
	}
	
	

}
