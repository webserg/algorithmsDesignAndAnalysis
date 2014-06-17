import java.io.BufferedReader;
import java.io.IOException;
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


    public static void main(String[] args) {
        MinWeightedSumOfComplTimes system = new MinWeightedSumOfComplTimes();
        List<Job> jobs = Arrays.asList(system.readJobsFromFile("resource/jobs.txt"));
        log.severe(system.runSum(jobs) + "");
    }

    public int runSum(List<Job> jobs) {
        int N = jobs.size();
        PriorityQueue<Job> heap = new PriorityQueue<>(N, new JobComparator());
        heap.addAll(jobs);
        int sum = 0;
        int complitionTime = 0;
        while (heap.isEmpty()) {
            Job cur = heap.poll();
            complitionTime += cur.getLength();
            sum += complitionTime * cur.getWeight();
        }
        return sum;
    }


    class Job {
        private final int weight;
        private final int length;
        private final int score;

        Job(int weight, int length) {
            this.weight = weight;
            this.length = length;
            this.score = countScore();
        }

        private int countScore() {
            return weight - length;
        }

        public int getWeight() {
            return weight;
        }

        public int getLength() {
            return length;
        }

        public int getScore() {
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
            int result = weight;
            result = 31 * result + length;
            return result;
        }
    }

    class JobComparator implements Comparator<Job> {
        @Override
        public int compare(Job o1, Job o2) {
            return o1.getScore() > o2.getScore() ? -1 : o1.getScore() < o2.getScore() ? 1 : o1.getWeight() > o2.getWeight() ? -1 : 1;
        }
    }

    Job[] readJobsFromFile(String filePath) {
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
                jobs[idx++] = new MinWeightedSumOfComplTimes.Job(weight, length);
            }
        } catch (IOException x) {
            log.severe("IOException:" + x.getMessage());
        }
        return jobs;
    }
}


