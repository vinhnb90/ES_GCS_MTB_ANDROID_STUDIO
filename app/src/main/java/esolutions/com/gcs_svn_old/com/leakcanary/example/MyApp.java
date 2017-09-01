package esolutions.com.gcs_svn_old.com.leakcanary.example;

import java.util.Set;

import esolutions.com.gcs_svn_old.com.example.sony.cameraremote.ServerDevice;
import esolutions.com.gcs_svn_old.com.example.sony.cameraremote.SimpleRemoteApi;
//import com.squareup.leakcanary.LeakCanary;
//import com.squareup.leakcanary.watcher.RefWatcher;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {
	
//	public static RefWatcher getRefWatcher(Context context) {
//		MyApp application = (MyApp) context.getApplicationContext();
//		return application.refWatcher;
//	}

//	private RefWatcher refWatcher;
	
    public void onCreate() {
          super.onCreate();
//          refWatcher = LeakCanary.install(this);
    }

//    private Activity mCurrentActivity = null;
//    public Activity getCurrentActivity(){
//          return mCurrentActivity;
//    }
//    public void setCurrentActivity(Activity mCurrentActivity){
//          this.mCurrentActivity = mCurrentActivity;
//    }
    
    private ServerDevice mTargetDevice;

    private SimpleRemoteApi mRemoteApi;

    private Set<String> mSupportedApiSet;

    /**
     * Sets a target ServerDevice object.
     * 
     * @param device
     */
    public void setTargetServerDevice(ServerDevice device) {
        mTargetDevice = device;
    }

    /**
     * Returns a target ServerDevice object.
     * 
     * @return return ServiceDevice
     */
    public ServerDevice getTargetServerDevice() {
        return mTargetDevice;
    }

    /**
     * Sets a SimpleRemoteApi object to transmit to Activity.
     * 
     * @param remoteApi
     */
    public void setRemoteApi(SimpleRemoteApi remoteApi) {
        mRemoteApi = remoteApi;
    }

    /**
     * Returns a SimpleRemoteApi object.
     * 
     * @return return SimpleRemoteApi
     */
    public SimpleRemoteApi getRemoteApi() {
        return mRemoteApi;
    }

    /**
     * Sets a List of supported APIs.
     * 
     * @param apiList
     */
    public void setSupportedApiList(Set<String> apiList) {
        mSupportedApiSet = apiList;
    }

    /**
     * Returns a list of supported APIs.
     * 
     * @return Returns a list of supported APIs.
     */
    public Set<String> getSupportedApiList() {
        return mSupportedApiSet;
    }
    
}
