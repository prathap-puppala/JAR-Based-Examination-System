import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Label;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

class Login extends JFrame implements ActionListener, KeyListener {
	TextField idno_val = new TextField();
	TextField pwd_val = new TextField();
	String univ_id = null;
	JLabel footer = new JLabel();
	JLabel errorMsg;
	JButton sign_in;
	public static Font pFont = null;
	public static Font bFont = null;
	public static Font titleFont = null;
	Font small;
	Font medium;
	Font big;
	Font large;
	Font small_b;
	Font medium_b;
	Font big_b;
	Font large_b;

	public Login(){
		try {
			
			InputStream font_in = Quiz.class.getResourceAsStream("source/segoeui.ttf");
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
		Dimension wh = Toolkit.getDefaultToolkit().getScreenSize();
		setLayout(null);
		setBounds((int) wh.getWidth() / 2 - 200,
				(int) wh.getHeight() / 2 - 100, 400, 200);
		setDefaultCloseOperation(3);
		setResizable(false);
		setAlwaysOnTop(true);
		setUndecorated(true);

		Container f = getContentPane();
		f.setLayout(null);

		Image iconpath = Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("source/icon.png"));
		JLabel img = new JLabel(new ImageIcon(iconpath));
		img.setBounds(130, 29, 24, 24);
		f.add(img);

		JLabel title = new JLabel("User Login");
		title.setForeground(new Color(10, 110, 110));
		title.setBounds(160, 25, 200, 30);
		title.setFont(this.large_b);
		f.add(title);

		Label idno = new Label("University ID", 2);
		idno.setForeground(new Color(10, 110, 110));
		idno.setBounds(50, 65, 100, 33);
		this.idno_val.setBounds(160, 70, 175, 22);
		idno.setFont(this.medium_b);
		this.idno_val.setFont(this.medium_b);
		f.add(idno);
		f.add(this.idno_val);

		Label pwd = new Label("Password", 2);
		pwd.setForeground(new Color(10, 110, 110));
		pwd.setBounds(50, 100, 100, 33);
		pwd.setFont(this.medium_b);
		this.pwd_val.setEchoChar('*');
		this.pwd_val.setFont(this.medium_b);
		this.pwd_val.setBounds(160, 105, 175, 22);
		f.add(pwd);
		f.add(this.pwd_val);

		this.errorMsg = new JLabel("", 0);
		this.errorMsg.setForeground(Color.RED);
		this.errorMsg.setBounds(120, 5, 160, 15);
		this.errorMsg.setFont(this.medium);
		f.add(this.errorMsg);

		this.footer = new JLabel("<html>&copy;IIIT-NUZIVID",
				2);
		this.footer.setBounds(130, 175, 220, 25);
		this.footer.setForeground(new Color(150, 75, 75));
		this.footer.setFont(new Font("Segoe UI", 1, 9));
		f.add(this.footer);

		this.sign_in = new JButton("LOGIN");
		this.sign_in.setBounds(160, 140, 100, 22);
		this.sign_in.setBackground(new Color(137, 173, 90));

		this.sign_in.addActionListener(this);
		this.idno_val.addKeyListener(this);
		this.pwd_val.addKeyListener(this);
		add(this.sign_in);
		setVisible(true);
	}

	public void keyTyped(KeyEvent ke) {
		if (ke.getKeyChar() == '\n') {
			this.sign_in.doClick();
		}
	}

	public void keyPressed(KeyEvent ke) {
	}

	public void keyReleased(KeyEvent ke) {
	}

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("LOGIN")) {
			try {
				InputStream f = Quiz.class
						.getResourceAsStream("source/password.txt");

				InputStreamReader input_file = new InputStreamReader(f);
				BufferedReader reader_file = new BufferedReader(input_file);
				String get_user_name;
				while ((get_user_name = reader_file.readLine()) != null) {
					String pwds = get_user_name;

					String uid = this.idno_val.getText().toUpperCase();
					String pwd = this.pwd_val.getText();
					if ((pwds.indexOf(uid + "-" + pwd) >= 0)
							&& (!uid.equals("")) && (!pwd.equals(""))
							&& (uid.length()>4) && (pwd.length() >= 1)) {
						Settings exam=new Settings();
						String dsktpPath = System.getProperty("user.home");
						File Che=new File(dsktpPath +"/Desktop/" + uid+"_"+exam.subject+".txt");
						
						String tmpPath = System.getProperty("java.io.tmpdir");
						/*
						File Che1=new File(tmpPath + "/"+ exam.examPwd+"/"+uid
								+ "_"+exam.subject+".txt");
							*/
					if(Che.exists()){

						this.errorMsg.setText("Exam Already Finished!");
						this.errorMsg.setForeground(Color.RED);
		
					}
					else{
						setVisible(false);
						for (;;) {
							String exam_pwd = JOptionPane.showInputDialog(null,
									"Enter Exam Password:");
							Settings exa=new Settings();
							if (exam_pwd.equals(exa.examPwd)) {
								examhere eh = new examhere(
										this.idno_val.getText().toUpperCase());
								WListener w = new WListener(eh);
								eh.addWindowListener(w);
								eh.addWindowFocusListener(w);
								break;
							}
						}

						this.errorMsg.setText("Invalid User Credentials.!");
						this.errorMsg.setForeground(Color.RED);

					}
				}
				}
			} catch (Exception fe) {
			}
		}
	}
}