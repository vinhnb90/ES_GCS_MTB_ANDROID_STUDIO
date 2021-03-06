package esolutions.com.gcs_svn_old.multi_media;

import java.util.ArrayList;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class PCMPlayer implements Runnable {
	/**
	 * String section
	 */
	private boolean IS_ALIVE = true;
	/**
	 * Data section
	 */
	private ArrayList<byte[]> mAlBuffers = new ArrayList<byte[]>();
	/**
	 * Other section
	 */
	private AudioTrack mAudioTrack;
	
	public PCMPlayer() {

	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		int bufSize = AudioTrack.getMinBufferSize(8000, 
				AudioFormat.CHANNEL_CONFIGURATION_MONO, 
				AudioFormat.ENCODING_PCM_16BIT);
		mAudioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 
				8000, 
				AudioFormat.CHANNEL_CONFIGURATION_MONO, 
				AudioFormat.ENCODING_PCM_16BIT, 
				bufSize, 
				AudioTrack.MODE_STREAM);
		mAudioTrack.play();	
		
		while(IS_ALIVE) {
			byte[] buffer = null;
			boolean dataFlag = true;
			
			while(dataFlag) {
				synchronized(mAlBuffers) {
					if(mAlBuffers.size() > 0) {
						buffer = mAlBuffers.remove(0);
					}
					else {
						dataFlag = false;
						break;
					}
				}
				
				mAudioTrack.write(buffer, 0, buffer.length);
			}
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		mAudioTrack.stop();
		mAudioTrack.release();
	}
	
	public void writePCM(byte[] pcm) {
		synchronized(mAlBuffers) {
			byte[] buffer = new byte[pcm.length];
			System.arraycopy(pcm, 0, buffer, 0, buffer.length);
			mAlBuffers.add(buffer);
		}
	}
	
	public void stop() {
		IS_ALIVE = false;
	}

}
