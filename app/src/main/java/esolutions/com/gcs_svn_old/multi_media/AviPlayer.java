package esolutions.com.gcs_svn_old.multi_media;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class AviPlayer implements Runnable {
	/**
	 * String section
	 */
	private int FRAME_RATE;
//	private int HEIGHT;
	private boolean ISLALIVE = true;
//	private int TOTAL_FRAMES;
//	private int WIDTH;
	private long LAST_FRAME_TIME;
	/**
	 * Data section
	 */
	private BufferedInputStream mBufferedInputStream;
	private ArrayList<IVideoSink> mAlVideoSinks;
	private ArrayList<IAudioSink> mAlAudioSinks;

	public AviPlayer(String filename) {
		mAlVideoSinks = new ArrayList<IVideoSink>();
		mAlAudioSinks = new ArrayList<IAudioSink>();

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

	public void addAudioSink(IAudioSink audioSink) {
		synchronized(mAlAudioSinks) {
			mAlAudioSinks.add(audioSink);
		}
	}


	@Override
	public void run() {
//		int count = 0;

		if(parseAviHeader()) {
			while(ISLALIVE) {
				try {
					int frameType = readFrameType();
					if(frameType == -1) {
						// end of stream
						break;
					}
					byte[] buffer = readFrame();

					if(frameType == 1) {
						// video
						if(LAST_FRAME_TIME == 0) {
							LAST_FRAME_TIME = System.currentTimeMillis();
						}
						long currentTime = System.currentTimeMillis();
						if(currentTime - LAST_FRAME_TIME < 1000000 / FRAME_RATE) {
							Thread.sleep(1000000 / FRAME_RATE - (currentTime - LAST_FRAME_TIME));
						}
						LAST_FRAME_TIME = System.currentTimeMillis();

						for(IVideoSink videoSink : mAlVideoSinks) {
							videoSink.onFrame(buffer, null);
						}
					} else if(frameType == 2) {
						for(IAudioSink audioSink : mAlAudioSinks) {
							audioSink.onPCM(buffer);
						}						
					}


				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

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

	private int readFrameType() {
		int peek = 0;
		int type = 0;
		try {
			peek = mBufferedInputStream.read();
			if(peek == -1) return -1;
			peek = mBufferedInputStream.read();
			mBufferedInputStream.skip(2);
			if(peek == 49) {
				// beginning of 01wb - audio
				type = 2;
			} else if(peek == 48) {
				// beginning of 00db - video
				type = 1;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return -1;
		}

		return type;
	}

	private byte[] readFrame() {
		byte[] buffer = null;

		try {
			int size = readInt();
			buffer = new byte[size];
			mBufferedInputStream.read(buffer, 0, size);
		} catch (IOException e) {
			return null;
		}

		return buffer;
	}



	private boolean parseAviHeader() {
		// todo: add more checks for robustness
		try {
			mBufferedInputStream.skip(0x84);
			FRAME_RATE = readInt();
			mBufferedInputStream.skip(4);
//			TOTAL_FRAMES = readInt();
			mBufferedInputStream.skip(32);
//			WIDTH = readInt();
//			HEIGHT = readInt();
			mBufferedInputStream.skip(28);
			// junk
			mBufferedInputStream.skip(4);
			int junkSize = readInt();
			mBufferedInputStream.skip(junkSize);
			// list
			mBufferedInputStream.skip(76);
			// strf
			mBufferedInputStream.skip(4);
			int srtfSize = readInt();
			mBufferedInputStream.skip(srtfSize);
			// junk 2
			mBufferedInputStream.skip(4);
			int junk2Size = readInt();
			mBufferedInputStream.skip(junk2Size);
			// junk 3
			mBufferedInputStream.skip(4);
			int junk3Size = readInt();
			mBufferedInputStream.skip(junk3Size);
			// list
			mBufferedInputStream.skip(12);
			// now it should be either audio or video data
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}

		return true;
	}

	private int readInt() throws IOException {
		try {
			int	b0 = mBufferedInputStream.read();
			int b1 = mBufferedInputStream.read();
			int b2 = mBufferedInputStream.read();
			int b3 = mBufferedInputStream.read();
			return b0 + (b1 << 8) + (b2 << 16) + (b3 << 24);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw(e);
		}
	}

	public void stop() {
		ISLALIVE = false;
	}
}
