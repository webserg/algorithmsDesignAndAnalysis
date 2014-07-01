import junit.framework.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by webserg on 17.06.2014.
 */
public class MinWeightedSumOfComplTimes {

    private static Logger log = Logger.getLogger(FilesHelper.class.getName());

    static {
        log.setLevel(Level.SEVERE);
    }

    @Test
    public void test1() throws Exception {
        MinWeightedSumOfComplTimes system = new MinWeightedSumOfComplTimes();
        List<Job> jobs = new ArrayList<>();
        CountScoreStategy scoreStategy = new CountScoreStrategyDifference();
        jobs.add(new Job(3,5,scoreStategy));
        jobs.add(new Job(1,2,scoreStategy));
        Assert.assertEquals(system.runSum(jobs),23);
    }

    @Test
    public void test2() throws Exception {
        MinWeightedSumOfComplTimes system = new MinWeightedSumOfComplTimes();
        List<Job> jobs = new ArrayList<>();
        CountScoreStategy scoreStategy = new CountScoreStrategyRatio();
        jobs.add(new Job(3,5,scoreStategy));
        jobs.add(new Job(1,2,scoreStategy));
        Assert.assertEquals(system.runSum(jobs),22);
    }


    @Test
    public void test3() throws Exception {
        MinWeightedSumOfComplTimes system = new MinWeightedSumOfComplTimes();
        List<Job> jobs = Arrays.asList(system.readJobsFromFile("resource/jobsTest1.txt",new CountScoreStrategyDifference()));
        Assert.assertEquals(21701,system.runSum(jobs));
    }

    @Test
    public void test4() throws Exception {
        MinWeightedSumOfComplTimes system = new MinWeightedSumOfComplTimes();
        List<Job> jobs = Arrays.asList(system.readJobsFromFile("resource/jobsTest1.txt",new CountScoreStrategyRatio()));
        Assert.assertEquals(21025,system.runSum(jobs));
    }

    @Test
    public void test() throws Exception {
        MinWeightedSumOfComplTimes system = new MinWeightedSumOfComplTimes();
        List<Job> jobs = Arrays.asList(system.readJobsFromFile("resource/jobs.txt",new CountScoreStrategyDifference()));
        System.out.printf("%d",system.runSum(jobs));
    }

    @Test
    public void testRation() throws Exception {
        MinWeightedSumOfComplTimes system = new MinWeightedSumOfComplTimes();
        List<Job> jobs = Arrays.asList(system.readJobsFromFile("resource/jobs.txt",new CountScoreStrategyRatio()));
        System.out.printf("%d",system.runSum(jobs));
    }

    public long runSum(List<Job> jobs) {
        int N = jobs.size();
        PriorityQueue<Job> heap = new PriorityQueue<>(N, new JobComparator());
        heap.addAll(jobs);
        long sum = 0;
        long complitionTime = 0;
        while (!heap.isEmpty()) {
            Job cur = heap.poll();
            complitionTime += cur.getLength();
            sum += complitionTime * cur.getWeight();
        }
        return sum;
    }

    interface CountScoreStategy{
        Double countScore(long w,long l);
    }

    class CountScoreStrategyDifference implements CountScoreStategy {
        @Override
        public Double countScore(long w,long l) {
            return (double)w - l;
        }
    }

    class CountScoreStrategyRatio implements CountScoreStategy {
        @Override
        public Double countScore(long w,long l) {
            return (double)w/l;
        }
    }


    class Job {
        private final long weight;
        private final long length;
        private final Double score;

        Job(int weight, int length, CountScoreStategy countScoreStategy) {
            this.weight = weight;
            this.length = length;
            this.score = countScoreStategy.countScore(this.weight,this.length);
        }


        public long getWeight() {
            return weight;
        }

        public long getLength() {
            return length;
        }

        public Double getScore() {
            return score;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Job job = (Job) o;

            if (length != job.length) return false;
            if (weight != job.weight) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = (int) (weight ^ (weight >>> 32));
            result = 31 * result + (int) (length ^ (length >>> 32));
            return result;
        }
    }

    class JobComparator implements Comparator<Job> {
        @Override
        public int compare(Job o1, Job o2) {
            return o1.getScore() > o2.getScore() ? -1 : o1.getScore() < o2.getScore() ? 1 : o1.getWeight() > o2.getWeight() ? -1 : 1;
        }
    }

    Job[] readJobsFromFile(String filePath,CountScoreStategy stategy) {
        Charset charset = Charset.forName("UTF-8");
        Path path = Paths.get(filePath);

        MinWeightedSumOfComplTimes.Job[] jobs = null;
        try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
            String line = reader.readLine();
            int N = Integer.parseInt(line);
            jobs = new MinWeightedSumOfComplTimes.Job[N];
            int idx = 0;
            while ((line = reader.readLine()) != null) {
                String[] strNumber = line.split("\\s+");
                int weight = Integer.parseInt(strNumber[0]);
                int length = Integer.parseInt(strNumber[1]);
                jobs[idx++] = new MinWeightedSumOfComplTimes.Job(weight, length,stategy);
            }
        } catch (IOException x) {
            log.severe("IOException:" + x.getMessage());
        }
        return jobs;
    }
}

