package esolutions.com.gcs_svn_old.multi_media;

public interface IVideoSink {
	void onFrame(byte[] frame, byte[] pcm);
	void onInitError(String errorMessage);
	void onVideoEnd();
}