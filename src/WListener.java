import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import javax.swing.JFrame;

public class WListener extends WindowAdapter implements WindowFocusListener {
	JFrame jfm;
	Dimension wh = Toolkit.getDefaultToolkit().getScreenSize();

	public WListener(JFrame jfm) {
		this.jfm = jfm;
	}

	public void windowGainedFocus(WindowEvent we) {
		change(we);
	}

	public void windowLostFocus(WindowEvent we) {
		change(we);
	}

	public void windowDeactivated(WindowEvent we) {
		change(we);
	}

	public void windowActivated(WindowEvent we) {
		change(we);
	}

	public void windowDeiconified(WindowEvent we) {
		change(we);
	}

	public void windowIconified(WindowEvent we) {
		change(we);
	}

	public void windowClosed(WindowEvent we) {
	}

	public void windowClosing(WindowEvent we) {
	}

	public void windowOpened(WindowEvent we) {
		change(we);
	}

	public void change(WindowEvent we) {
		try {
			this.jfm.setExtendedState(6);
			this.jfm.setSize((int) this.wh.getWidth(),
					(int) this.wh.getHeight());
			this.jfm.setFocusable(true);
			this.jfm.setAlwaysOnTop(true);
			we.getWindow().toFront();
			we.getWindow().requestFocus();
			we.getWindow().setAlwaysOnTop(false);
			we.getWindow().setAlwaysOnTop(true);
		} catch (Exception e) {
		}
	}
}