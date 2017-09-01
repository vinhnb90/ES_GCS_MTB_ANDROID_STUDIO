package esolutions.com.gcs_svn_old.esgcs.printer;

import android.os.Handler;

import com.printer.ZQPrinter;

public class My_ZQPrinter {
	public String BLUETOOTH_ADDRESS = "00:12:F3:19:49:CD";// Citizen
	// "00:1F:B7:02:77:D6"; // AB-320
	// "00:12:F3:19:49:CD"; // APEX2
	private ZQPrinter printer = null; // máy in bluetooth

	public My_ZQPrinter() {
		ConnectPrinter();
	}

	public int ConnectPrinter() {
		try {
			if (printer == null) {
				printer = new ZQPrinter();

				final Handler handler = new Handler();
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						if (printer != null) {
							printer.Connect(BLUETOOTH_ADDRESS);
						}
					}
				}, 400);
			}
			return 0; // success
		} catch (Exception e) {
			return 1; // exception
		}
	}

	public void RefreshPrinter() {
		DisconnectPrinter();
		ConnectPrinter();
	}

	public void DisconnectPrinter() {
		try {
			if (printer != null) {
				printer.Disconnect();
				printer = null;
			}
		} catch (Exception ex) {
			
		}
	}

	public int DoPrint(String content) {
		try {
			ConnectPrinter();
			int returevlaue = printer.PrintText(content,
					ZQPrinter.ALIGNMENT_LEFT, ZQPrinter.FT_BOLD,
					ZQPrinter.TS_0WIDTH | ZQPrinter.TS_0HEIGHT);
			if (returevlaue == ZQPrinter.AB_SUCCESS) {
				returevlaue = printer.LineFeed(3);
				return 0; // success
			} else {
				DisconnectPrinter();
				return 2; // connect error
			}
		} catch (Exception e) {
			DisconnectPrinter();
			return 1; // exception

		}
	}
}
