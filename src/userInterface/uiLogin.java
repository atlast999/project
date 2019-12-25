package userInterface;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import io.FileInteract;
import model.Account;
import model.Class;
import model.Student;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class uiLogin extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldUsername;
	//private JTextField textFieldPassword;
	private JComboBox comboBoxType;
	private JPasswordField textFieldPassword;
	private ArrayList<Account> listAcc = FileInteract.readSerialFileAcc("account.txt");

	public uiLogin() {
		
		setTitle("Đăng nhập hệ thống");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 325, 217);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelAccInfor = new JPanel();
		contentPane.add(panelAccInfor, BorderLayout.CENTER);
		panelAccInfor.setLayout(new BoxLayout(panelAccInfor, BoxLayout.Y_AXIS));
		
		JPanel panelUsername = new JPanel();
		panelAccInfor.add(panelUsername);
		
		JLabel lblUsername = new JLabel("Tài khoản: ");
		panelUsername.add(lblUsername);
		
		textFieldUsername = new JTextField();
		panelUsername.add(textFieldUsername);
		textFieldUsername.setColumns(10);
		
		JPanel panelPassword = new JPanel();
		panelAccInfor.add(panelPassword);
		
		JLabel lblPassword = new JLabel("Mật khẩu: ");
		panelPassword.add(lblPassword);
		
		textFieldPassword = new JPasswordField();
		panelPassword.add(textFieldPassword);
		textFieldPassword.setColumns(10);
		
		JPanel panelType = new JPanel();
		panelAccInfor.add(panelType);
		
		JLabel lblType = new JLabel("Loại tài khoản: ");
		panelType.add(lblType);
		
		comboBoxType = new JComboBox();
			comboBoxType.addItem("Giảng viên");
			comboBoxType.addItem("Sinh viên");
		panelType.add(comboBoxType);
		
		JPanel panelLogin = new JPanel();
		contentPane.add(panelLogin, BorderLayout.SOUTH);
		
		JButton btnLogin = new JButton("Đăng nhập");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				processLogin();
			}
		});
		panelLogin.add(btnLogin);
	}

	protected void processLogin() {
		// TODO Auto-generated method stub
		String username, password;
		int typeAcc;
		username = textFieldUsername.getText();
		password = textFieldPassword.getText();
		typeAcc = comboBoxType.getSelectedIndex();
		typeAcc+=2;
		int checkLogin = -1;
		for(Account acc : listAcc)
		{
			if (acc.getUsername().equals(username) 
					&& acc.getPassword().equals(password)
					&& acc.getTypeAcc() == typeAcc)
			{
				checkLogin = typeAcc;
				break;
			}
		}
		if (checkLogin == -1) JOptionPane.showMessageDialog(null, "Đăng nhập thất bại!\n"
				+ "Hãy kiểm tra lại thông tin");
		else
		{
			this.setVisible(false);	//disable login screen
			ui frame = new ui();	//create user interface
			frame.setVisible(true);
			frame.setAccType(checkLogin); //store account's type
		}
	}

}
