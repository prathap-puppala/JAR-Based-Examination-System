import java.io.*;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

class examhere extends JFrame implements ActionListener {
	public static int ques_count = 30;
	int no_sec = 1;
	public static String q_options = "444444444444444444444444444444";//No of options per questions
	Container f = getContentPane();
	JButton[][] jb = new JButton[ques_count][9];
	JTextField[] inputs = new JTextField[ques_count];
	char[] ans = new char[ques_count];
	JButton next = new JButton("NEXT");
	JButton prev = new JButton("PREV");
	JButton submit = new JButton("SUBMIT");
	JButton submit2 = new JButton("SUBMIT2");
	JLabel jl;
	StringBuffer sb = new StringBuffer();
	String options2;
	String userId = new String();
	JPanel exam = new JPanel();
	JLabel ans_qus_count = new JLabel("Answered: 0/" + ques_count);
	public static int flag = 0;
	int sec_index = 0;
	int answered_count = 0;
	JLabel sec_dsp = new JLabel("sec number: " + this.sec_index);
	int[] sec_qno = { 0,ques_count };
	JLabel[] studentinfo = new JLabel[3];
	Dimension wh = Toolkit.getDefaultToolkit().getScreenSize();
	Font small;
	Font medium;
	Font big;
	Font large;
	Font small_b;
	Font medium_b;
	Font big_b;
	Font large_b;
	JPanel confirm_p = new JPanel();
	JButton yesb;
	JButton nob;
	JScrollPane jsp = new JScrollPane(this.exam, 22, 30);

	public void confirmDialog() {
		this.confirm_p.setBounds((int) this.wh.getWidth() - 475,
				(int) this.wh.getHeight() - 250, 400, 100);
		this.confirm_p.setBackground(new Color(240, 240, 240));
		this.confirm_p.setLayout(null);
		String confirmation = "Sure you want to Submit?";
		this.jl = new JLabel(confirmation);
		this.jl.setBounds(20, 10, 350, 50);
		this.confirm_p.add(this.jl);
		this.yesb = new JButton("Yes");
		this.yesb.addActionListener(this);
		this.nob = new JButton("No");
		this.nob.addActionListener(this);
		this.yesb.setBounds(20, 65, 60, 25);
		this.nob.setBounds(85, 65, 60, 25);
		this.confirm_p.add(this.yesb);
		this.confirm_p.add(this.nob);
		this.confirm_p.setBorder(BorderFactory.createLineBorder(new Color(100,
				100, 100)));
		this.f.add(this.confirm_p);
	}

	public examhere(String idno) {
		confirmDialog();
		this.confirm_p.setVisible(false);
		try {
			InputStream font_in = Quiz.class
					.getResourceAsStream("source/segoeui.ttf");
			Font font = Font.createFont(0, font_in);
			this.small = font.deriveFont(0, 9.0F);
			this.medium = font.deriveFont(0, 12.0F);
			this.big = font.deriveFont(0, 14.0F);
			this.large = font.deriveFont(0, 18.0F);

			this.small_b = font.deriveFont(1, 9.0F);
			this.medium_b = font.deriveFont(1, 12.0F);
			this.big_b = font.deriveFont(1, 14.0F);
			this.large_b = font.deriveFont(1, 18.0F);
		} catch (Exception fe) {
			JOptionPane.showMessageDialog(this, "Font ERROR:" + fe);
		}
		setLayout(null);
		setBounds(0, 0, (int) this.wh.getWidth(), (int) this.wh.getHeight());

		this.exam.setBackground(Color.WHITE);
		this.exam.setLayout(new BoxLayout(this.exam, 3));

		Image lpath = Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("source/rgu.png"));

		JLabel rgu_logo = new JLabel(new ImageIcon(lpath));
		rgu_logo.setBounds((int) this.wh.getWidth() / 2 - 18, 20, 36, 36);
		this.f.add(rgu_logo);

		this.userId = idno;
		this.studentinfo[0] = new JLabel(this.userId);
		this.studentinfo[1] = new JLabel("RGUKT", 0);
		String date = String.format("%1$s %2$tB %2$te, %2$tY", new Object[] {
				"On : ", new Date() });
		this.studentinfo[2] = new JLabel(date);

		JLabel copy_rght = new JLabel(
				"<html><span style='color:#3675A9;'> <b>&copy;SmartQuint</b><span></html>");
		copy_rght.setBounds((int) this.wh.getWidth() / 2 - 100,
				(int) this.wh.getHeight() - 50, 240, 25);
		copy_rght.setFont(this.medium);
		this.f.add(copy_rght);

		this.studentinfo[0].setBounds(50, 25, 550, 30);
		this.studentinfo[1].setBounds((int) this.wh.getWidth() / 2 - 250, 50,
				500, 30);
		this.studentinfo[2].setBounds(50, 60, 250, 30);
		this.studentinfo[0].setFont(this.large_b);
		this.studentinfo[1].setFont(this.large_b);
		this.studentinfo[2].setFont(this.medium);

		this.f.add(this.studentinfo[0]);
		this.f.add(this.studentinfo[1]);
		this.f.add(this.studentinfo[2]);

		this.next.setBounds(160, (int) this.wh.getHeight() - 80, 100, 25);
		this.prev.setBounds(50, (int) this.wh.getHeight() - 80, 100, 25);

		this.submit.setBounds((int) this.wh.getWidth() - 150,
				(int) this.wh.getHeight() - 80, 100, 25);
		this.submit.addActionListener(this);
		this.submit.setBackground(new Color(45, 55, 155));
		this.submit.setForeground(Color.WHITE);
		this.submit.setCursor(new Cursor(12));
		this.next.addActionListener(this);
		this.next.setBackground(new Color(45, 55, 155));
		this.next.setForeground(Color.WHITE);
		this.next.setCursor(new Cursor(12));
		this.prev.addActionListener(this);
		this.prev.setBackground(new Color(45, 55, 155));
		this.prev.setForeground(Color.WHITE);
		this.prev.setCursor(new Cursor(12));
		this.submit2.addActionListener(this);
		// this.f.add(this.next);
		// this.f.add(this.prev);
		this.f.add(this.submit);

		nextSection();

		this.jsp.getVerticalScrollBar().setUnitIncrement(32);

		this.jsp.setBounds(50, 100, (int) this.wh.getWidth() - 100,
				(int) this.wh.getHeight() - 200);
		this.f.add(this.jsp);

		setDefaultCloseOperation(3);
		setResizable(false);
		setAlwaysOnTop(true);
		setUndecorated(true);

		setVisible(true);

		this.sec_dsp.setBounds(300, (int) this.wh.getHeight() - 80, 180, 25);
		this.sec_dsp.setFont(this.big_b);
		// this.f.add(this.sec_dsp);

		this.ans_qus_count.setBounds((int) this.wh.getWidth() - 450,
				(int) this.wh.getHeight() - 80, 180, 25);
		this.ans_qus_count.setFont(this.big_b);
		this.f.add(this.ans_qus_count);

		JLabel timer = new JLabel("00:00");
		timer.setFont(this.large_b);
		timer.setBounds((int) this.wh.getWidth() - 100, 50, 100, 25);

		this.f.add(timer);
		new clock(timer, this.submit2);
	}

	public void prevSection() {
		if (this.sec_index > 1) {
			this.sec_index -= 1;

			int qfrom = this.sec_index - 1;
			int qto = this.sec_index;
			if (qfrom >= 0) {
				displaySec(qfrom, qto);
			}
			this.next.setEnabled(true);
			if (qfrom == 0) {
				this.prev.setEnabled(false);
			}
		}
	}

	public void nextSection() {
		if (this.sec_index < this.no_sec) {
			int qfrom = this.sec_index;
			int qto = this.sec_index + 1;
			this.sec_index += 1;
			displaySec(qfrom, qto);
			this.prev.setEnabled(true);
			if (qto == this.no_sec) {
				this.next.setEnabled(false);
			}
		}
	}

	public void displaySec(int from, int to) {
		this.jsp.getVerticalScrollBar().setValue(0);
		this.sec_dsp.setText("Section: " + this.sec_index + "/" + this.no_sec);
		this.sb.delete(0, this.sb.length());
		for (int k = 0; k < ques_count; k++) {
			if (this.ans[k] == 0) {
				this.sb.append("*");
			} else {
				this.sb.append(this.ans[k]);
			}
		}
		String temp_options = this.sb.toString();

		String options = "ABCDEFGHI";
		this.exam.removeAll();
		JPanel[] jimp = new JPanel[ques_count];
		JPanel[] jps = new JPanel[ques_count];
		for (int i = this.sec_qno[from]; (i < ques_count)
				&& (i < this.sec_qno[to]); i++) {
			String s = "images/" + (i + 1) + ".PNG";

			Image path = Toolkit.getDefaultToolkit().getImage(
					getClass().getResource(s));

			JLabel l = new JLabel(new ImageIcon(path));
			jimp[i] = new JPanel();
			jimp[i].setLayout(new FlowLayout(0));
			jimp[i].add(l);
			this.exam.add(jimp[i]);

			jps[i] = new JPanel();
			jps[i].setLayout(new FlowLayout(0, 25, 20));
			for (int j = 0; j < Integer.parseInt(q_options.charAt(i) + ""); j++) {
				char op = options.charAt(j);
				this.jb[i][j] = new JButton(" " + op + " ");
				this.jb[i][j].setSize(100, 35);
				this.jb[i][j].setActionCommand(Integer.toString(i) + "#"
						+ Integer.toString(j));
				this.jb[i][j].addActionListener(this);
				if (temp_options.charAt(i) == op) {
					this.jb[i][j].setBackground(new Color(140, 165, 225));
				}
				jps[i].add(this.jb[i][j]);
			}
			jps[i].setSize(400, 75);

			jimp[i].setBackground(Color.WHITE);
			jps[i].setBackground(Color.WHITE);
			this.exam.add(jps[i]);
			this.exam.updateUI();
		}
	}

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		this.sb.delete(0, this.sb.length());
		if (command.equals("SUBMIT")) {
			String updated_count = "<html>You answered:" + this.answered_count
					+ "<br>Sure you want to confirm?";
			this.jl.setText(updated_count);
			this.confirm_p.setVisible(false);
			this.confirm_p.setVisible(true);
		} else if (command.equals("Yes")) {
			for (int k = 0; k < ques_count; k++) {
				if (this.ans[k] == 0) {
					this.sb.append("*");
				} else {
					this.sb.append(this.ans[k]);
				}
			}
			flag = 1;

			this.options2 = this.sb.toString();
			try {
				evaluateAns(this.sb.toString(), this.userId);

				setVisible(false);
			} catch (Exception e1) {
				System.out.println("ERROR :" + e1);
				e1.printStackTrace();
				JOptionPane.showMessageDialog(this, "Error :" + e1);
			}
		} else if (command.equals("No")) {
			this.confirm_p.setVisible(false);
		} else if (command.equals("SUBMIT2")) {
			this.sb.delete(0, this.sb.length());
			for (int k = 0; k < ques_count; k++) {
				if (this.ans[k] == 0) {
					this.sb.append("*");
				} else {
					this.sb.append(this.ans[k]);
				}
			}
			try {
				evaluateAns(this.sb.toString(), this.userId);
				setVisible(false);
			} catch (Exception e2) {
				System.out.println("ERROR" + e2);
			}
		} else if (command.equals("NEXT")) {
			nextSection();
		} else if (command.equals("PREV")) {
			prevSection();
		} else {
			String[] a = command.split("#");
			int qin = Integer.parseInt(a[0]);
			int opin = Integer.parseInt(a[1]);

			Color c = this.jb[qin][opin].getBackground();
			if (this.ans[qin] == 0) {
				this.jb[qin][opin].setBackground(new Color(140, 165, 225));
				this.ans_qus_count.setText("Answered: " + ++this.answered_count
						+ "/" + ques_count);
			} else {
				switch (this.ans[qin]) {
				case 'A':
					this.jb[qin][0].setBackground(c);
					break;
				case 'B':
					this.jb[qin][1].setBackground(c);
					break;
				case 'C':
					this.jb[qin][2].setBackground(c);
					break;
				case 'D':
					this.jb[qin][3].setBackground(c);
					break;
				case 'E':
					this.jb[qin][4].setBackground(c);
					break;
				case 'F':
					this.jb[qin][5].setBackground(c);
					break;
				case 'G':
					this.jb[qin][6].setBackground(c);
					break;
				case 'H':
					this.jb[qin][7].setBackground(c);
					break;
				case 'I':
					this.jb[qin][8].setBackground(c);
					break;
				default:
					JOptionPane.showMessageDialog(this,
							Character.valueOf(this.ans[qin]));
				}
				this.jb[qin][opin].setBackground(new Color(140, 165, 225));
			}
			this.ans[qin] = this.jb[qin][opin].getText().charAt(1);
		}
	}

	private void evaluateAns(String user_options, String user_id)
			throws Exception {
		flag = 1;
		String org_ans = "";

		String user_ans = user_options;
		int sec_id = 0;
		int score = 0;
		int skip = 0;
		int attempted = 0;
		int total_s = 0;

		StringBuffer sec_marks = new StringBuffer();
		StringBuffer all_sec_score = new StringBuffer();
		while (sec_id < this.no_sec) {
			score = 0;
			skip = 0;
			attempted = 0;
			int lastQus = this.sec_qno[(sec_id + 1)];
			for (int i = this.sec_qno[sec_id]; i < lastQus; i++) {
				if ((user_ans.length() > i) && (org_ans.length() > i)) {
					if (user_ans.charAt(i) != '*') {
						if (org_ans.charAt(i) == user_ans.charAt(i)) {
							score++;
							total_s++;
						}
						attempted++;
					} else {
						skip++;
					}
				}
			}
			all_sec_score.append(" " + score);

			int qcount = lastQus - this.sec_qno[sec_id];

			sec_marks.append("@Score:" + score + "/" + qcount + " (Answered:"
					+ attempted + ")");

			sec_id++;
		}
		all_sec_score.append("@");

		sec_marks.append("@");
		String temp_marks = sec_marks.toString();
		sec_marks.delete(0, sec_marks.length());
		sec_marks.append("@" + total_s);
		sec_marks.append(temp_marks);

		setVisible(false);
		scoreCard sc = new scoreCard(this.userId, sec_marks.toString(),
				total_s, skip, user_ans, all_sec_score.toString());
	}
}