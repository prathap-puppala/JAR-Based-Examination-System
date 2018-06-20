
import java.util.*;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Label;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Date;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class scoreCard extends JFrame implements ActionListener, Runnable {
	TextField idno_val = new TextField();
	TextField pwd_val = new TextField();
	String univ_id = null;
	String total_score;
	private static String request = "";
	int score;
	int skip;
	String optns;
	String user_id;
	String all_sec_score = "";
	Font small;
	Font medium;
	Font large;
	Font enlarged;
	Font medium_b;
	Font large_b;
	String decrypt = "";
	String decryptscore;

	public scoreCard(String userId, String sec_score, int score, int skip,
			String optns, String all_sec_score) {
		this.all_sec_score = all_sec_score;
		this.total_score = sec_score;
		this.score = score;
		this.skip = skip;
		this.optns = optns;
		this.user_id = userId;
		Thread thrd = new Thread(this);
		thrd.start();

	}

	public void run() {
		scrCard();
	}

	public void scrCard() {
		try {
			InputStream font_in = Quiz.class
					.getResourceAsStream("source/segoeui.ttf");
			Font font = Font.createFont(0, font_in);
			this.small = font.deriveFont(0, 9.0F);
			this.medium = font.deriveFont(0, 12.0F);
			this.large = font.deriveFont(0, 18.0F);

			this.enlarged = font.deriveFont(1, 50.0F);
			this.medium_b = font.deriveFont(1, 12.0F);
			this.large_b = font.deriveFont(1, 18.0F);
		} catch (Exception fe) {
			JOptionPane.showMessageDialog(this, "Font ERROR:" + fe);
		}
		Dimension wh = Toolkit.getDefaultToolkit().getScreenSize();
		setLayout(null);
		setBounds((int) wh.getWidth() / 2 - 275,
				(int) wh.getHeight() / 2 - 220, 550, 470);
		setDefaultCloseOperation(3);
		setResizable(false);
		setAlwaysOnTop(false);

		Container f = getContentPane();

		f.setBackground(new Color(250, 250, 250));
		JPanel profile = new JPanel();
		profile.setLayout(null);
		profile.setBounds(0, 0, 200, 450);
		profile.setBackground(new Color(245, 245, 245));
		profile.setBorder(BorderFactory.createLineBorder(new Color(230, 230,
				230), 15));
		f.add(profile);

		Label jl = new Label("Exam Status", 1);
		jl.setBounds(25, 20, 150, 25);
		jl.setFont(this.large_b);
		profile.add(jl);

		JLabel sid = new JLabel(this.user_id, 0);
		sid.setBounds(50, 150, 100, 50);
		sid.setFont(this.large_b);
		profile.add(sid);

		JLabel smarks = new JLabel("", 0);
		smarks.setBounds(25, 280, 150, 45);
		smarks.setFont(this.enlarged);
		profile.add(smarks);

		JLabel yours = new JLabel("", 0);
		yours.setBounds(25, 220, 150, 25);
		yours.setFont(this.medium);
		profile.add(yours);
		JLabel outof = new JLabel("", 0);
		outof.setBounds(15, 240, 160, 40);
		outof.setFont(this.medium);
		profile.add(outof);

		Image iconpath = Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("source/profile.png"));
		JLabel p_icon = new JLabel(new ImageIcon(iconpath));
		p_icon.setBounds(50, 60, 100, 93);
		profile.add(p_icon);

		JPanel result_panel = new JPanel();
		result_panel.setLayout(new BoxLayout(result_panel, 3));
		JScrollPane jsp = new JScrollPane(result_panel, 20, 30);
		jsp.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230),
				1));
		jsp.setBounds(200, 0, 345, 445);
		f.add(jsp);

		StringTokenizer stk = new StringTokenizer(this.total_score, "@");

		JLabel footer = new JLabel("RGUKT-NUZVID", 0);
		footer.setBounds(50, 350, 100, 25);
		footer.setFont(this.medium);
		profile.add(footer);

		JButton ok = new JButton("<html><b style='color:white;'>OK");
		ok.setCursor(new Cursor(12));
		ok.setActionCommand("OK");
		ok.setBounds(15, 410, 170, 25);
		ok.setBackground(new Color(140, 165, 155));
		ok.addActionListener(this);
		profile.add(ok);
		String dsktpPath = System.getProperty("user.home");
		String tmpPath = System.getProperty("java.io.tmpdir");
		File Che1=null;
		File save_marks = null;
		FileOutputStream fos = null;
		FileOutputStream fos1 = null;
		Settings exam=new Settings();
		try {
			save_marks = new File(dsktpPath + "/Desktop/" + this.user_id
					+"_"+exam.subject+".txt");
			/*
			Che1=new File(dsktpPath + "/.pra/"+ exam.examPwd+"/"+this.user_id
					+ "_"+exam.subject+".txt");
			*/
			fos = new FileOutputStream(save_marks);
			/*fos1 = new FileOutputStream(Che1);*/
		} catch (FileNotFoundException fe) {
		}
		StringBuffer marks_data = new StringBuffer();
		int i = 1;
		int connection = 0;
		int h = 0;
		while (stk.hasMoreTokens()) {
			if (i == 1) {
				String su = stk.nextToken();
				setVisible(true);
				decryptscore = su;
				yours.setText("Encrypting Answer Script...");
				outof.setText("Creating Answer Script...");
				smarks.setText("Done");
				Date date = new Date();
			    String stuid=new String(); //Encrypted student ID
			   stuid="";
			   
			   if(this.user_id.charAt(0)=='N'){stuid+="9";}
			   else if(this.user_id.charAt(0)=='R'){stuid+="8";}
			   else if(this.user_id.charAt(0)=='B'){stuid+="7";}
			   else if(this.user_id.charAt(0)=='S'){stuid+="6";}
			   
			   for(i=1;i<this.user_id.length();i++){
			    	String cha=new String();
			    	cha="";
			    	cha+=this.user_id.charAt(i);
			    	int num=Integer.parseInt(cha);
			    	num=9-num;
			    	stuid+=Integer.toString(num);
			    }
			    
				marks_data.append(stuid+ this.user_id+"\nTIME STAMP:"
						+ date.toString() + "\n");
			}
			Random r = new Random();
			int unans = 0;
			String user_ans=new String();
			user_ans="";   //Encrypted user string
			for (i = 0; i < optns.length(); i++) {
				char ch = optns.charAt(i);
				if(ch=='*'){user_ans+='0';}
				else if(ch=='A'){user_ans+='9';}
				else if(ch=='B'){user_ans+='8';}
				else if(ch=='C'){user_ans+='7';}
				else if(ch=='D'){user_ans+='6';}
				else if(ch=='E'){user_ans+='5';}
				else if(ch=='F'){user_ans+='4';}
				else if(ch=='G'){user_ans+='3';}
				else if(ch=='H'){user_ans+='2';}
			}
			decrypt = decrypt + decryptscore;
			int ans = (optns.length() - unans);
			decrypt = decrypt + Integer.toString(ans);
			String scr = stk.nextToken();

			Label pwd = new Label("Please Upload the generated .txt file at http://10.11.3.55/exam");
			// marks_data.append(pwd.getText() + "\n");
			marks_data.append(user_ans + "\n");
			pwd.setFont(this.medium);

			pwd.setSize(new Dimension(345, 40));
			if (i % 2 == 1) {
				pwd.setBackground(new Color(230, 230, 230));
			} else {
				pwd.setBackground(new Color(240, 240, 240));
			}
			result_panel.add(pwd);

			i++;
		}
		try {
			if (save_marks.exists()) {
				fos.write(marks_data.toString().getBytes());
				fos.close();
			}
		} catch (Exception fe) {
			JOptionPane.showMessageDialog(this, "ERROR:Results Saving Error");
		}
		// for (;;)
		// {
		// connection = sendTestDetails(this.score, this.skip, this.optns,
		// this.user_id);
		// if (connection != 0) {
		// break;
		// }
		// Object[] op = { "Retry", "Cancel" };
		// int yes_no = JOptionPane.showOptionDialog(this,
		// "Connection Failed...\nPlease Check your Connection & Try Again!",
		// "Network Connection Error", 2, 2, null, op, op[0]);
		// if (yes_no == 1) {
		// System.exit(0);
		// }
		// }
		// setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("OK")) {
			try {
				// if (Desktop.isDesktopSupported()) {
				// Desktop.getDesktop().browse(new
				// URI("http://10.11.4.99/submit2.php?id=" + this.user_id +
				// "&submit=Submit"));
				// }
			} catch (Exception dsktp) {
			}
			System.exit(0);
		}
	}

	public int sendTestDetails(int score, int skip, String optns, String user_id) {
		String id = user_id;
		String sec_score = this.all_sec_score;
		String marks = score + "";
		String unAns = skip + "";
		String options = optns;

		String urlParameters = "ID=" + id + "&sec_score=" + sec_score
				+ "&Marks=" + marks + "&options=" + options + "&unAns=" + unAns;

		request = "http://10.11.4.99/submit.php";
		try {
			URL url = new URL(request);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();

			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			connection.setRequestProperty("charset", "utf-8");
			connection.setRequestProperty("Content-Length",
					"" + Integer.toString(urlParameters.getBytes().length));
			connection.setUseCaches(false);
			DataOutputStream wr = new DataOutputStream(
					connection.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
			Scanner sc = new Scanner(connection.getInputStream());
			while (sc.hasNextLine()) {
				JOptionPane.showMessageDialog(null, sc.nextLine());
			}
			sc.close();
			connection.disconnect();

			return 1;
		} catch (Exception te) {
			System.out.println("ERROR:" + te);
		}
		return 0;
	}
}
