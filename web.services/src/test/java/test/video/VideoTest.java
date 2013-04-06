package test.video;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import util.videos.VideosUtil;

public class VideoTest {
	private static class Duration {
		int hours, minutes;
		double seconds;

		public Duration(int hours, int minutes, double seconds) {
			while (seconds >= 60) {
				seconds -= 60;
				minutes += 1;
			}

			while (minutes >= 60) {
				minutes -= 60;
				hours += 1;
			}

			this.hours = hours;
			this.minutes = minutes;
			this.seconds = seconds;
		}

		public Duration add(Duration duration) {
			double seconds = duration.seconds + this.seconds;
			int minutes = duration.minutes + this.minutes;
			int hours = duration.hours + this.hours;

			while (seconds >= 60) {
				seconds -= 60;
				minutes += 1;
			}

			while (minutes >= 60) {
				minutes -= 60;
				hours += 1;
			}

			return new Duration(hours, minutes, seconds);
		}

		@Override
		public String toString() {
			return hours + ":" + minutes + ":" + seconds;
		}

		public static boolean same(Duration duration1, Duration duration2) {
			return duration1.hours == duration2.hours
					&& duration1.minutes == duration2.minutes
					&& Math.abs(duration1.seconds - duration2.seconds) < 0.1;
		}

	}

	class StreamGobbler extends Thread {
		InputStream is;
		Duration dur;

		StreamGobbler(InputStream is) {
			this.is = is;
		}

		public void run() {
			try {
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				String line = null;
				while ((line = br.readLine()) != null)
					if (line.contains("Duration")) {
						String[] tokens = line.split(",")[0].split(":");
						int hours = Integer.parseInt(tokens[1].trim());
						int minutes = Integer.parseInt(tokens[2].trim());
						double seconds = Double.parseDouble(tokens[3].trim());
						dur = new Duration(hours, minutes, seconds);
					}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	@Test
	public void testAppend() throws IOException, InterruptedException,
			ExecutionException {
		String[] videos = { "src/test/resources/testVideo1.mp4",
				"src/test/resources/testVideo2.mp4" };
		Duration vid1 = getDuration(videos[0]);
		Duration vid2 = getDuration(videos[1]);
		VideosUtil.append(videos, "src/test/resources/output.mp4");
		System.out.println(vid1.add(vid2));
		Duration output = getDuration("src/test/resources/output.mp4");
		System.out.println(output);
		Assert.assertTrue(Duration.same(
				getDuration("src/test/resources/output.mp4"), vid1.add(vid2)));
	}
	
	@Before
	public void deleteOutput() {
		File f = new File("src/test/resources/output.mp4");
		f.delete();
	}

	protected Duration getDuration(String file) throws IOException,
			InterruptedException {
		Runtime rt = Runtime.getRuntime();
		Process proc = rt.exec("src/test/resources/ffmpeg -i " + file);
		StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream());
		StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream());
		errorGobbler.start();
		outputGobbler.start();
		proc.waitFor();
		Duration dur = errorGobbler.dur;
		return dur;
	}
}