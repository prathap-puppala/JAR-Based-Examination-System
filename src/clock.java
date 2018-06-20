import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;

class clock implements Runnable {
	JLabel counter;
	JButton submit2;
	int quiz_time = 45;
	int sec = 60;
	int temp_qtime = this.quiz_time;
	int flag;
	int tred = (int) (this.quiz_time * 0.1D);

	public clock(JLabel clock_label, JButton submit) {
		this.flag = examhere.flag;
		this.counter = clock_label;
		this.submit2 = submit;
		new Thread(this).start();
	}

	public void run() {
		for (;;) {
			int tsec = this.sec % 60;
			String sec00;

			if (tsec < 10) {
				sec00 = "0" + tsec;
			} else {
				sec00 = "" + tsec;
			}
			String hour00;

			if (this.quiz_time < 10) {
				hour00 = "0" + this.quiz_time;
			} else {
				hour00 = "" + this.quiz_time;
			}
			this.counter.setText(hour00 + ":" + sec00);

			int ttemp = Integer.parseInt(hour00);
			if (ttemp <= this.tred) {
				this.counter.setForeground(Color.RED);
			} else {
				this.counter.setForeground(Color.BLACK);
			}
			if (this.sec == 60) {
				this.quiz_time -= 1;
			}
			this.sec -= 1;
			try {
				Thread.sleep(1000L);
				if (this.sec == 0) {
					if ((this.quiz_time == 0) && (examhere.flag == 0)) {
						this.submit2.doClick();
					}
					this.sec = 60;
				}
			} catch (InterruptedException ie) {
				System.out.println("Clock Interrupted");
			}
		}
	}
}