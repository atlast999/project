package userInterface;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import io.FileInteract;
import model.Class;
import model.Student;
import model.Subject;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.table.TableModel;
import java.awt.Window.Type;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JCheckBox;

public class ui extends JFrame {

	private JPanel contentPane;
	private DefaultTableModel dtmStudList, dtmScoreList;
	
	private DefaultMutableTreeNode root;
	private JTree treeClassList;
	private JTextField textFieldMidScore;
	private JTextField textFieldFinalScore;
	private JTextField textFieldNameStud;
	private JTextField textFieldStudID;
	private JTextField textFieldBirthYear;
	private JTextField textFieldSex;
	private JTextField textFieldCity;
	private JTable tableStuList;
	private JTextField textFieldNameSubj;
	private ArrayList<Class> listClass = FileInteract.readSerialFile("data.txt");
	private JTable tableScoreList;
	private int accType;
	private JComboBox cbxSubName;

	public void setAccType(int accType) {
		this.accType = accType;
	}



	public ui() {
		setTitle("Quản lý sinh viên");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 1160, 650);
		
		setLocationRelativeTo(null);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				JFrame frame = new JFrame();
				int choice = JOptionPane.showConfirmDialog(frame, "Lưu tất cả cập nhật");
				frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                if (choice == JOptionPane.NO_OPTION) System.exit(0);
                if (choice == JOptionPane.OK_OPTION) {
                	boolean kt = FileInteract.saveSerialFile(listClass, "data.txt");
                	if (!kt) JOptionPane.showMessageDialog(null, "Lưu thất bại");
                	else System.exit(0);
                }
			}
		});
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JMenuBar menuBar = new JMenuBar();
		contentPane.add(menuBar, BorderLayout.NORTH);
		
		JMenu menu = new JMenu("file");
		menuBar.add(menu);
		
		JMenuItem menuItem_1 = new JMenuItem("save");
		menu.add(menuItem_1);
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!checkAccType()) return;
				boolean kt = FileInteract.saveSerialFile(listClass, "data.txt");
            	if (!kt) JOptionPane.showMessageDialog(null, "Lưu thất bại");
            	else JOptionPane.showMessageDialog(null, "Đã lưu các cập nhật");
			}
		});
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.95);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		JPanel panelClassStudScore = new JPanel();
		splitPane.setLeftComponent(panelClassStudScore);
		panelClassStudScore.setLayout(new BorderLayout(0, 0));
		
		JPanel panelClassListAndFunc = new JPanel();
		panelClassStudScore.add(panelClassListAndFunc, BorderLayout.WEST);
		panelClassListAndFunc.setLayout(new BorderLayout(0, 0));
		
		JPanel panelClassList = new JPanel();
		panelClassListAndFunc.add(panelClassList, BorderLayout.CENTER);
		panelClassList.setLayout(new BorderLayout(0, 0));
		
		
		/////////////////////////////////////////////////////////
		root = new DefaultMutableTreeNode("Danh sách lớp");
		treeClassList = new JTree(root);
		treeClassList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				showTableStudList();
			}
		});
		panelClassList.add(treeClassList);
		/////////////////////////////////////////////////////////
		
		JPanel panelClassFunc = new JPanel();
		panelClassListAndFunc.add(panelClassFunc, BorderLayout.SOUTH);
		
		///////////////////////////////////////////////
		JButton btnAddClass = new JButton("Thêm lớp");
		btnAddClass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!checkAccType()) return;
				processAddClass();
			}
		});
		panelClassFunc.add(btnAddClass);
		///////////////////////////////////////////////
		
		///////////////////////////////////////////////
		JButton btnDelClass = new JButton("Xóa lớp");
		btnDelClass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!checkAccType()) return;
				processDelClass();
			}
		});
		panelClassFunc.add(btnDelClass);
		////////////////////////////////////////////////
		
		JSplitPane splitPaneStudAndScore = new JSplitPane();
		splitPaneStudAndScore.setResizeWeight(0.795);
		panelClassStudScore.add(splitPaneStudAndScore, BorderLayout.CENTER);
		
		JPanel panelStudListAndFunc = new JPanel();
		splitPaneStudAndScore.setLeftComponent(panelStudListAndFunc);
		panelStudListAndFunc.setLayout(new BorderLayout(0, 0));
		
		JLabel lblStudInfor = new JLabel("Thông tin sinh viên");
		lblStudInfor.setHorizontalAlignment(SwingConstants.CENTER);
		panelStudListAndFunc.add(lblStudInfor, BorderLayout.NORTH);
		
		JScrollPane scrollPaneStudList = new JScrollPane();
		panelStudListAndFunc.add(scrollPaneStudList, BorderLayout.CENTER);	
		
		/////////////////////////////////////////////////
		dtmStudList = new DefaultTableModel();
		dtmStudList.addColumn("Họ và tên");
		dtmStudList.addColumn("MSSV");
		dtmStudList.addColumn("Năm sinh");
		dtmStudList.addColumn("Giới tính");
		dtmStudList.addColumn("Quê quán");
		dtmStudList.addColumn("Điểm trung bình");
		
		tableStuList = new JTable(dtmStudList);
		tableStuList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				showStudInfor();
				showTableScoreList();
			}
		});
		scrollPaneStudList.setViewportView(tableStuList);
		/////////////////////////////////////////////////
		JPanel panelStudFunc = new JPanel();
		panelStudListAndFunc.add(panelStudFunc, BorderLayout.SOUTH);
		
		/////////////////////////////////////////////////////
		JButton btnDelStud = new JButton("Xóa sinh viên");
		btnDelStud.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!checkAccType()) return;
				processDelStud();
			}
		});		
		panelStudFunc.add(btnDelStud);
		//////////////////////////////////////////////////////////////*/
		
		JButton btnSortStud = new JButton("Sắp xếp theo điểm");
		btnSortStud.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				processSortStud();
			}
		});
		panelStudFunc.add(btnSortStud);
		
		JButton btnFindStud = new JButton("Tìm kiếm");
		btnFindStud.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				processFindStud();
			}
		});
		panelStudFunc.add(btnFindStud);
		///////////////////////////////////////////////////////////////////
		
		JPanel panelScoreListAndFunc = new JPanel();
		splitPaneStudAndScore.setRightComponent(panelScoreListAndFunc);
		panelScoreListAndFunc.setLayout(new BorderLayout(0, 0));
		
		JLabel lblScoreInfor = new JLabel("Bảng điểm");
		lblScoreInfor.setHorizontalAlignment(SwingConstants.CENTER);
		panelScoreListAndFunc.add(lblScoreInfor, BorderLayout.NORTH);
		
		JScrollPane scrollPaneScoreList = new JScrollPane();
		panelScoreListAndFunc.add(scrollPaneScoreList, BorderLayout.CENTER);
		
		/////////////////////////////////////////////////////////////
		dtmScoreList = new DefaultTableModel();
		dtmScoreList.addColumn("Môn học");
		dtmScoreList.addColumn("Điểm QT");
		dtmScoreList.addColumn("Điểm KT");
		dtmScoreList.addColumn("Trạng thái");
		
		tableScoreList = new JTable(dtmScoreList);
		tableScoreList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				showScoreInfor();
			}
		});
		scrollPaneScoreList.setViewportView(tableScoreList);
		/////////////////////////////////////////////////////////////
		
		JPanel panelScoreFunc = new JPanel();
		panelScoreListAndFunc.add(panelScoreFunc, BorderLayout.SOUTH);
		
		////////////////////////////////////////////////////////////////
		JButton btnDelSubj = new JButton("Xóa môn học");
		btnDelSubj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!checkAccType()) return;
				processDelSubject();
			}
		});
		panelScoreFunc.add(btnDelSubj);
		/////////////////////////////////////////////////////////////////
		
		JSplitPane splitPaneUpdateStudAndScore = new JSplitPane();
		splitPaneUpdateStudAndScore.setResizeWeight(0.795);
		splitPane.setRightComponent(splitPaneUpdateStudAndScore);
		
		JPanel panelUpdStudInforAndFunc = new JPanel();
		splitPaneUpdateStudAndScore.setLeftComponent(panelUpdStudInforAndFunc);
		panelUpdStudInforAndFunc.setLayout(new BorderLayout(0, 0));
		
		JPanel panelUpdStudInfor = new JPanel();
		panelUpdStudInforAndFunc.add(panelUpdStudInfor, BorderLayout.CENTER);
		panelUpdStudInfor.setLayout(new GridLayout(2, 3, 0, 0));
		
		JPanel panelNameStud = new JPanel();
		panelUpdStudInfor.add(panelNameStud);
		
		JLabel lblNameStud = new JLabel("Họ tên");
		lblNameStud.setHorizontalAlignment(SwingConstants.CENTER);
		panelNameStud.add(lblNameStud);
		
		textFieldNameStud = new JTextField();
		panelNameStud.add(textFieldNameStud);
		textFieldNameStud.setColumns(15);
		
		JPanel panelBirthYear = new JPanel();
		panelUpdStudInfor.add(panelBirthYear);
		
		JLabel lblBirthYear = new JLabel("Năm sinh");
		panelBirthYear.add(lblBirthYear);
		
		textFieldBirthYear = new JTextField();
		panelBirthYear.add(textFieldBirthYear);
		textFieldBirthYear.setColumns(10);
		
		JPanel panelCity = new JPanel();
		panelUpdStudInfor.add(panelCity);
		
		JLabel lblCity = new JLabel("Quê quán");
		panelCity.add(lblCity);
		
		textFieldCity = new JTextField();
		panelCity.add(textFieldCity);
		textFieldCity.setColumns(10);
		
		JPanel panelStudID = new JPanel();
		panelUpdStudInfor.add(panelStudID);
		
		JLabel lblStudID = new JLabel("MSSV");
		panelStudID.add(lblStudID);
		
		textFieldStudID = new JTextField();
		panelStudID.add(textFieldStudID);
		textFieldStudID.setColumns(10);
		
		JPanel panelSex = new JPanel();
		panelUpdStudInfor.add(panelSex);
		
		JLabel lblSex = new JLabel("Giới tính");
		panelSex.add(lblSex);
		
		textFieldSex = new JTextField();
		panelSex.add(textFieldSex);
		textFieldSex.setColumns(10);
		
		JPanel panelUpdStudFunc = new JPanel();
		panelUpdStudInforAndFunc.add(panelUpdStudFunc, BorderLayout.SOUTH);
		
		//////////////////////////////////////////////
		JButton btnAddStud = new JButton("Thêm sinh viên");
		btnAddStud.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!checkAccType()) return;
				processAddStudent();
			}
		});
		panelUpdStudFunc.add(btnAddStud);
		
		JButton btnUpdateInfor = new JButton("Cập nhật thông tin");
		btnUpdateInfor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!checkAccType()) return;
				processUpdateInfor();
			}
		});
		panelUpdStudFunc.add(btnUpdateInfor);
		
		JButton btnClearData = new JButton("Clear");
		btnClearData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				processClearData();
			}
		});
		panelUpdStudFunc.add(btnClearData);
		///////////////////////////////////////////////
		
		JPanel panelUpdScoreInforAndFunc = new JPanel();
		splitPaneUpdateStudAndScore.setRightComponent(panelUpdScoreInforAndFunc);
		panelUpdScoreInforAndFunc.setLayout(new BorderLayout(0, 0));
		
		JPanel panelUpdScoreInfor = new JPanel();
		panelUpdScoreInforAndFunc.add(panelUpdScoreInfor, BorderLayout.CENTER);
		panelUpdScoreInfor.setLayout(new BoxLayout(panelUpdScoreInfor, BoxLayout.Y_AXIS));
		
		JPanel panelNameSubj = new JPanel();
		panelUpdScoreInfor.add(panelNameSubj);
		panelNameSubj.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNameSubj = new JLabel("Tên môn học");
		lblNameSubj.setHorizontalAlignment(SwingConstants.LEFT);
		panelNameSubj.add(lblNameSubj);
		
		Vector<String> subList = new Vector<String>();
		subList.add("Tin học đại cương");
		subList.add("Toán rời rạc");
		subList.add("Phương pháp tính");
		subList.add("Xác suất thống kê");
		subList.add("Giải tích");
		subList.add("Đại số");
		subList.add("Vật lý đại cương");
		subList.add("Nhập môn CNTT và TT");
		subList.add("Kiến trúc máy tính");
		subList.add("Kỹ thuật lập trình	");
		subList.add("Hệ điều hành");
		subList.add("Mạng máy tính");
		subList.add("Cơ sở dữ liệu");
		subList.add("Hướng đối tượng");
		subList.add("Thuật toán ứng dụng");
		subList.add("Lập trình mạng");
		cbxSubName = new JComboBox(subList);
		cbxSubName.setEditable(true);
		panelNameSubj.add(cbxSubName);
		
		JPanel panelMidScore = new JPanel();
		panelUpdScoreInfor.add(panelMidScore);
		
		JLabel lblMidScore = new JLabel("Điểm quá trình");
		panelMidScore.add(lblMidScore);
		
		textFieldMidScore = new JTextField();
		panelMidScore.add(textFieldMidScore);
		textFieldMidScore.setColumns(10);
		
		JPanel panelFinalScore = new JPanel();
		panelUpdScoreInfor.add(panelFinalScore);
		
		JLabel lblFinalScore = new JLabel("Điểm kết thúc");
		panelFinalScore.add(lblFinalScore);
		
		textFieldFinalScore = new JTextField();
		panelFinalScore.add(textFieldFinalScore);
		textFieldFinalScore.setColumns(10);
		
		JPanel panelUpdScoreFunc = new JPanel();
		panelUpdScoreInforAndFunc.add(panelUpdScoreFunc, BorderLayout.SOUTH);
		/////////////////////////////////////////////////////////
		
		JButton btnUpdScore = new JButton("Cập nhật điểm");
		btnUpdScore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!checkAccType()) return;
				processUpdateScore();
			}
		});
		panelUpdScoreFunc.add(btnUpdScore);
		
		/////////////////////////////////////////////////////////
		showTree();
	}
	
	protected boolean checkAccType()
	{
		if(accType == 3) 
		{
			JOptionPane.showMessageDialog(null, "Không có quyền thực thi");
			return false;
		}
		return true;
	}

	protected void processFindStud() {
		JFrame frame = new JFrame();
		String key = JOptionPane.showInputDialog(frame, "Nhập tên hoặc MSSV: ");
		frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treeClassList.getLastSelectedPathComponent(); //lay lop tren dang chon tren cay
		if (selectedNode == null || selectedNode.getLevel() == 0) {
			JOptionPane.showMessageDialog(null, "Hãy chọn lớp");
			return;
		}
		dtmStudList.setRowCount(0);
		Class clss = (Class) selectedNode.getUserObject();
		for (Student stud : clss.getListStudent()) {
			if (stud.getIdStu().equals(key) || stud.getNameStu().indexOf(key) != -1) {
				Vector<Object> vec = new Vector<Object>();
				vec.add(stud.getNameStu());
				vec.add(stud.getIdStu());
				vec.add(stud.getYearBirth());
				vec.add(stud.getSex());
				vec.add(stud.getCity());
				String avScore = String.format("%.2f", stud.getAverageScore());
				vec.add(avScore);
				dtmStudList.addRow(vec);
			}
		}
	}


	protected void processClearData() {
		textFieldNameStud.setText("");
		textFieldStudID.setText("");
		textFieldBirthYear.setText("");
		textFieldSex.setText("");
		textFieldCity.setText("");
	}
	//sap xep sinh vien theo diem trung binh
	protected void processSortStud() {
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treeClassList.getLastSelectedPathComponent(); //lay lop tren dang chon tren cay
		if (selectedNode == null || selectedNode.getLevel() == 0) {
			JOptionPane.showMessageDialog(null, "Hãy chọn lớp");
			return;
		}
		Class clss = (Class) selectedNode.getUserObject();
		clss.sortStudByScore();
		showTableStudList();
	}
	
	protected void processDelSubject() {	
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treeClassList.getLastSelectedPathComponent();
		if (selectedNode == null) return;
		int rowStud = tableStuList.getSelectedRow();	//get index of selected student
		if (rowStud == -1) return;
		int rowScore = tableScoreList.getSelectedRow();	//get index of selected subject
		if (rowScore == -1) return;
		
		Class clss = (Class) selectedNode.getUserObject();
			Student stud = clss.getListStudent().get(rowStud);
			stud.delSubject(rowScore);
		showTableScoreList(); 
	}

	protected void processUpdateScore() {
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treeClassList.getLastSelectedPathComponent();
		if (selectedNode == null) {
			JOptionPane.showMessageDialog(null, "Chưa chọn lớp!");
			return;
		}
		int row = tableStuList.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(null, "Chọn sinh viên cần cập nhật điểm");
			return;
		}	
		Class clss = (Class) selectedNode.getUserObject();
			Student stud = clss.getListStudent().get(row);
			Subject sub = new Subject();
			//valueless text field
			String subName = (String)cbxSubName.getSelectedItem();
			String midScore = textFieldMidScore.getText();
			String finalScore = textFieldFinalScore.getText();
			if(subName.equals("") || midScore.equals("") || finalScore.equals("") ) {
				JOptionPane.showMessageDialog(null, "Nhập dữ liệu không hợp lệ");
				return;
			}
			sub.setSubName(subName);
			sub.setMidScore(Double.parseDouble(midScore));
			sub.setFinalScore(Double.parseDouble(finalScore));
			//if the subject already existed
			int index = 0;
			boolean done = false;
			for(Subject subIte : stud.getListSubject()) {
				if (subIte.getSubName().equals(sub.getSubName())) {
					index = stud.getListSubject().indexOf(subIte);
					stud.updateSubject(index, sub);
					done = true;
					break;
				}
			}
			//else
			if(!done) {
				index = stud.getListSubject().size();
				stud.updateSubject(index, sub);
			}
		showTableScoreList();
		showTableStudList();
		tableStuList.setRowSelectionInterval(row, row);
		tableScoreList.setRowSelectionInterval(index, index);
	}
	
	protected void processUpdateInfor() {  //student's info
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treeClassList.getLastSelectedPathComponent();
		if (selectedNode == null) return;
		int row = tableStuList.getSelectedRow();
		if (row == -1) return;
		
		Class clss = (Class) selectedNode.getUserObject();
			String IDstud = tableStuList.getValueAt(row, 1).toString(); //get id of selected student
			for(Student stud : clss.getListStudent())
			{
				if (stud.getIdStu().equals(IDstud))
				{
					stud.setNameStu(textFieldNameStud.getText());
					stud.setIdStu(textFieldStudID.getText());
					stud.setYearBirth(textFieldBirthYear.getText());
					stud.setSex(textFieldSex.getText());
					stud.setCity(textFieldCity.getText());
					row = clss.getListStudent().indexOf(stud);
					clss.updateStudent(row, stud);
					break;
				}
			}
		showTableStudList();
		tableStuList.setRowSelectionInterval(row, row);
	}
	
	protected void processDelStud() {
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treeClassList.getLastSelectedPathComponent();
		if (selectedNode == null) return;
		int row = tableStuList.getSelectedRow();
		if (row == -1) return;
		
		Class clss = (Class) selectedNode.getUserObject();
		String IDstud = tableStuList.getValueAt(row, 1).toString();
		for(Student stud : clss.getListStudent())
		{
			if (stud.getIdStu().equals(IDstud))
			{
				row = clss.getListStudent().indexOf(stud);
				break;
			}
		}
			clss.delStudent(row);
		showTableStudList();
	}

	protected void processAddStudent() {
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treeClassList.getLastSelectedPathComponent();
		if (selectedNode == null || selectedNode.getLevel() == 0) {
			JOptionPane.showMessageDialog(null, "Hãy chọn lớp");
			return;
		}
		Class clss = (Class) selectedNode.getUserObject();
				Student stud = new Student();
				stud.setNameStu(textFieldNameStud.getText());
				stud.setIdStu(textFieldStudID.getText());
				stud.setYearBirth(textFieldBirthYear.getText());
				stud.setSex(textFieldSex.getText());
				stud.setCity(textFieldCity.getText());
				if (stud.getIdStu().equals("") || stud.getNameStu().equals(""))
				{
					JOptionPane.showMessageDialog(null, "MSSV hoặc họ tên không để trống");
				}
				for(Student studcheck : clss.getListStudent()) {
					if (stud.getIdStu().equals(studcheck.getIdStu())) {
						JOptionPane.showMessageDialog(null, "Mã sinh viên đã tồn tài");
						return;
					}
				}
				clss.addStudent(stud);
		showTableStudList();
	}

	protected void processDelClass() {
		//get selected class on the class list
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treeClassList.getLastSelectedPathComponent();
		if (selectedNode == null) return; //no class is selected
		Class clss = (Class) selectedNode.getUserObject();
				listClass.remove(clss);
		showTree();
	}

	protected void processAddClass() {
		JFrame frame = new JFrame();	
		String nameClass = JOptionPane.showInputDialog(frame, "Nhập tên lớp: ");
		frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		if (nameClass.length() <= 0) return;
		
		Class clss = new Class(nameClass);
			listClass.add(clss);
		
		showTree();	
	}
	
	
	protected void showScoreInfor() {// to update
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treeClassList.getLastSelectedPathComponent();
		if (selectedNode == null) return;
		
		int rowStud = tableStuList.getSelectedRow();
		if (rowStud == -1) return;
		int rowScore = tableScoreList.getSelectedRow();
		if (rowScore == -1) return;
		
		Class clss = (Class) selectedNode.getUserObject();
				Subject sub = clss.getListStudent().get(rowStud).getListSubject().get(rowScore);
				cbxSubName.setSelectedItem(sub.getSubName());
				//textFieldNameSubj.setText(sub.getSubName());
				textFieldMidScore.setText(Double.toString(sub.getMidScore()));
				textFieldFinalScore.setText(Double.toString(sub.getFinalScore()));

	}
		
	protected void showStudInfor() {
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treeClassList.getLastSelectedPathComponent();
		if (selectedNode == null) return;
		
		int row = tableStuList.getSelectedRow();
		if (row == -1) return;
		
		Class clss = (Class) selectedNode.getUserObject();
				//Student stud = clss.getListStudent().get(tableStuList.convertRowIndexToModel(row));
				String studID = tableStuList.getValueAt(row, 1).toString();
				for(Student stud : clss.getListStudent())
				{
					if (stud.getIdStu().equals(studID))
					{
						textFieldNameStud.setText(stud.getNameStu());
						textFieldStudID.setText(stud.getIdStu());
						textFieldBirthYear.setText(stud.getYearBirth());
						textFieldSex.setText(stud.getSex());
						textFieldCity.setText(stud.getCity());
						break;
					}
				}
				
	}

	protected void showTableScoreList() {
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treeClassList.getLastSelectedPathComponent();
		if (selectedNode.getLevel()==0) {
			JOptionPane.showMessageDialog(null, "Hãy chọn lớp");
			return;
		}
		int row = tableStuList.getSelectedRow();
		if (row == -1) return;
		dtmScoreList.setRowCount(0); //clear the old table
		Class clss = (Class) selectedNode.getUserObject();
		String studID = tableStuList.getValueAt(row, 1).toString();
		for(Student stud : clss.getListStudent())
		{
			if (stud.getIdStu().equals(studID))
			{
				for(Subject sub : stud.getListSubject()) {
					Vector<Object> vec = new Vector<Object>();
					vec.add(sub.getSubName());
					vec.add(sub.getMidScore());
					vec.add(sub.getFinalScore());
					vec.add(sub.getStatus());
					dtmScoreList.addRow(vec);
				}
				break;
			}
		}
	}	
	
	protected void showTableStudList() {
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treeClassList.getLastSelectedPathComponent();
		if (selectedNode.getLevel() == 0) return;
		dtmStudList.setRowCount(0);
		Class clss = (Class) selectedNode.getUserObject();
				for(Student stud : clss.getListStudent()) {
					Vector<Object> vec = new Vector<Object>();
					vec.add(stud.getNameStu());
					vec.add(stud.getIdStu());
					vec.add(stud.getYearBirth());
					vec.add(stud.getSex());
					vec.add(stud.getCity());
					String avScore = String.format("%.2f", stud.getAverageScore());
					vec.add(avScore);
					dtmStudList.addRow(vec);
				}

	}	
	
	protected void showTree() {// list of classes
		root.removeAllChildren();
		for(Class clss : listClass) {
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(clss);
			root.add(node);
		}
		treeClassList.updateUI();
		treeClassList.expandRow(0);
	}
	
}
