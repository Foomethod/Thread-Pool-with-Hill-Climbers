import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.Random;
public class MainThreadPool {

	public static void main(String args[])
	{
		double genNum, minimum = Double.MAX_VALUE;
		double[] storage = new double[200];
		Random rand = new Random();
		double[][] array = new double[200][100];
		HillClimbing[] task = new HillClimbing[200];
		ExecutorService exec = Executors.newCachedThreadPool();
		
		for(int r = 0; r < 200; r++)//doesn't require synchronization as all threads are working on a different row
		{		
			for(int c = 0; c < 100; c++)
			{
				genNum = ThreadLocalRandom.current().nextDouble(-5.12,5.12);
				array[r][c] = genNum;
				//System.out.println(genNum);
			}			
		}
		
		long starttime = System.currentTimeMillis();
		for(int r = 0; r < 200; r++)
		{
			task[r] = new HillClimbing(array,r,storage);
			exec.execute(task[r]);
		}

		exec.shutdown();
		while(!exec.isTerminated()) {}
		
		for(int a = 0; a < 200; a++)
		{
			System.out.printf("Minimum for row %d is : %.2f\n",a + 1,storage[a]);
			if(storage[a] < minimum)
			{
				minimum = storage[a];
			}
		}

		long endtime = System.currentTimeMillis();
		
		long totaltime = (endtime - starttime);
		System.out.printf("Cached thread pool...\nMinimum value for all 200 rows: %.2f"
				+ "\nTotal time taken: %dms", minimum,totaltime);
	}
}