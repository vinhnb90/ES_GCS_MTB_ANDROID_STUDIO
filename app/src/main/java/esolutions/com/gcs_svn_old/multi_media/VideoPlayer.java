package esolutions.com.gcs_svn_old.multi_media;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


public class VideoPlayer implements Runnable {
	/**
	 * String section
	 */
	private boolean IS_ALIVE = true;
	private final int FPS = 24;
	private long LAST_FRAME_TIME;
	/**
	 * Data section
	 */
	private ArrayList<IVideoSink> mAlVideoSinks;
	/**
	 * Others section
	 */
	private BufferedInputStream mBufferedInputStream;




	public VideoPlayer(String filename) {
		mAlVideoSinks = new ArrayList<IVideoSink>();

		try {
			mBufferedInputStream = new BufferedInputStream(new FileInputStream(filename));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	public void addVideoSink(IVideoSink videoSink) {
		synchronized(mAlVideoSinks) {
			mAlVideoSinks.add(videoSink);
		}
	}

	public void removeVideoSink(IVideoSink videoSink) {
		synchronized(mAlVideoSinks) {
			if(mAlVideoSinks.contains(videoSink))
				mAlVideoSinks.remove(videoSink);
		}
	}

	@Override
	public void run() {
		int count = 0;

		while(IS_ALIVE) {
			if(LAST_FRAME_TIME == 0) {
				LAST_FRAME_TIME = System.currentTimeMillis();
			}

			try {
				long currentTime = System.currentTimeMillis();
				if(currentTime - LAST_FRAME_TIME < 1000 / FPS) {
					Thread.sleep(1000 / FPS - (currentTime - LAST_FRAME_TIME));
				}
				LAST_FRAME_TIME = System.currentTimeMillis();

				int b0 = mBufferedInputStream.read();
				if(b0 == -1) break;
				int b1 = mBufferedInputStream.read();
				int b2 = mBufferedInputStream.read();
				int b3 = mBufferedInputStream.read();

				count = b0 + (b1 << 8) + (b2 << 16) + (b3 << 24);
				byte[] buffer = new byte[count];
//				int readCount = mBufferedInputStream.read(buffer, 0, count);
				for(IVideoSink videoSink : mAlVideoSinks) {
					videoSink.onFrame(buffer, null);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		try {
			mBufferedInputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for(IVideoSink videoSink : mAlVideoSinks) {
			videoSink.onVideoEnd();
		}

	}

	public void stop() {
		IS_ALIVE = false;
	}

}
