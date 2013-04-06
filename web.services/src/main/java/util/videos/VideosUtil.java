package util.videos;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import util.config.Config;

/**
 * Performs operations with video files using FFMpeg
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 23/7/2012
 * @version 1.8
 */
public class VideosUtil {

	/**
	 * Consumes the stream created by the video operations
	 * 
	 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
	 *         García</a>
	 * @since 23/7/2012
	 * @version 1.0
	 */
	private static class StreamConsumer extends Thread {

		private InputStream is;

		/**
		 * Creates a thread that consumes an InputStream
		 * 
		 * @param is
		 *            InputStream to be consumed
		 */
		public StreamConsumer(InputStream is) {
			this.is = is;
		}

		/**
		 * Reads the stream until the end, consuming it
		 */
		@Override
		public void run() {
			try {
				while (is.read() != -1)
					;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Appends video files together
	 * 
	 * @param videos
	 *            The path to the videos to be appended
	 * @param output
	 *            The output filename
	 * @throws RuntimeException
	 *             If the videos cannot be appended
	 * @throws IllegalArgumentException
	 *             If there are less than two videos to be appended
	 */
	public static void append(String[] videos, String output) {
		if (videos.length < 2) {
			throw new IllegalArgumentException(
					"Se necesitan al menos dos vídeos");
		}
		String ffmpegPath = Config.getProperty("ffmpeg");
		String concatFiles = "concat:";
		String command = "";
		try {
			for (int i = 0; i < videos.length; i++) {
				command = ffmpegPath + "ffmpeg -i " + videos[i]
						+ " -f mpegts -c copy -bsf:v h264_mp4toannexb "
						+ ffmpegPath + "temp" + i + ".mpeg.ts";
				Process convert = Runtime.getRuntime().exec(command);
				if (i > 0) {
					concatFiles += "|";
				}
				concatFiles += ffmpegPath + "temp" + i + ".mpeg.ts";
				convert.waitFor();
			}
			command = ffmpegPath + "ffmpeg -isync -y -i \"" + concatFiles
					+ "\" -f matroska -c copy " + output;
			Runtime.getRuntime().exec(command).waitFor();
			deleteTempFiles(videos.length);
		} catch (IOException e) {
			throw new RuntimeException("Error uniendo vídeos", e);
		} catch (InterruptedException e) {
			throw new RuntimeException("Error uniendo vídeos", e);
		}
	}

	/**
	 * Converts a video file in terms of resolution, bitrate and format
	 * 
	 * @param input
	 *            The path to the video to be resized
	 * @param output
	 *            The output filename
	 * @throws RuntimeException
	 *             If the video cannot be resized
	 */
	public static void resize(String input, String output) {
		try {
			String command = Config.getProperty("ffmpeg") + "ffmpeg -i "
					+ input + " -s " + Config.getProperty("width") + "x"
					+ Config.getProperty("height") + " -b:v "
					+ Config.getProperty("videoBitrate") + "k -ab "
					+ Config.getProperty("audioBitrate") + "k -ac "
					+ Config.getProperty("audioChannels") + " -y " + output;
			Process convert = Runtime.getRuntime().exec(command);
			InputStream inputStream = convert.getInputStream();
			InputStream errorStream = convert.getErrorStream();
			Thread inputConsumer = new StreamConsumer(inputStream);
			Thread errorConsumer = new StreamConsumer(errorStream);
			inputConsumer.start();
			errorConsumer.start();
			convert.waitFor();
		} catch (IOException e) {
			RuntimeException re = new RuntimeException(
					"Error transformando v�deos");
			re.setStackTrace(e.getStackTrace());
			throw re;
		} catch (InterruptedException e) {
			RuntimeException re = new RuntimeException(
					"Error transformando v�deos");
			re.setStackTrace(e.getStackTrace());
			throw re;
		}
	}

	/**
	 * Reencodes a video file to mp4
	 * 
	 * @param input
	 *            The path to the video to be reencoded
	 * @param output
	 *            The output filename
	 * @throws RuntimeException
	 *             If the video cannot be resized
	 */
	public static void reencode(String input, String output) {
		try {
			String command = Config.getProperty("ffmpeg") + "ffmpeg -i "
					+ input + " -s " + Config.getProperty("width") + "x"
					+ Config.getProperty("height") + " -b:v "
					+ Config.getProperty("videoBitrate") + "k -ab "
					+ Config.getProperty("audioBitrate") + "k -ac "
					+ Config.getProperty("audioChannels") + " -y " + output;
			Process convert = Runtime.getRuntime().exec(command + ".mp4");
			InputStream inputStream = convert.getInputStream();
			InputStream errorStream = convert.getErrorStream();
			Thread inputConsumer = new StreamConsumer(inputStream);
			Thread errorConsumer = new StreamConsumer(errorStream);
			inputConsumer.start();
			errorConsumer.start();
			convert.waitFor();
		} catch (IOException e) {
			RuntimeException re = new RuntimeException(
					"Error transformando vídeos");
			re.setStackTrace(e.getStackTrace());
			throw re;
		} catch (InterruptedException e) {
			RuntimeException re = new RuntimeException(
					"Error transformando vídeos");
			re.setStackTrace(e.getStackTrace());
			throw re;
		}
	}

	/**
	 * Takes a screenshot of a video file
	 * 
	 * @param input
	 *            The video file to take the screenshot from it
	 * @param output
	 *            The output filename
	 * @throws RuntimeException
	 *             If the screenshot cannot be taken
	 */
	public static void takeScreenshot(String input, String output) {
		try {
			String command = Config.getProperty("ffmpeg") + "ffmpeg -i "
					+ input + " -s " + Config.getProperty("width") + "x"
					+ Config.getProperty("height") + " -ss 1 -t 1 -f mjpeg "
					+ output;
			Process takeScreenshot = Runtime.getRuntime().exec(command);
			InputStream inputStream = takeScreenshot.getInputStream();
			InputStream errorStream = takeScreenshot.getErrorStream();
			Thread inputConsumer = new StreamConsumer(inputStream);
			Thread errorConsumer = new StreamConsumer(errorStream);
			inputConsumer.start();
			errorConsumer.start();
			takeScreenshot.waitFor();
		} catch (IOException e) {
			RuntimeException re = new RuntimeException(
					"Error capturando un fotograma del v�deo");
			re.setStackTrace(e.getStackTrace());
			throw re;
		} catch (InterruptedException e) {
			RuntimeException re = new RuntimeException(
					"Error capturando un fotograma del v�deo");
			re.setStackTrace(e.getStackTrace());
			throw re;
		}
	}

	/**
	 * Deletes temporary files created while appending some videos together
	 * 
	 * @param length
	 *            How many files had been created
	 */
	private static void deleteTempFiles(int length) {
		for (int i = 0; i < length; i++) {
			File f = new File(Config.getProperty("ffmpeg") + "temp" + i
					+ ".mpeg.ts");
			f.delete();
		}
	}

}
