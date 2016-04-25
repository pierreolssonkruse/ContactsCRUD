package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import Controller.ContactsDAO;
import Model.Bean;

public class View extends JFrame {

	private static final long serialVersionUID = 7178004463445380077L;
	ContactsDAO contactsDAO;
	private JPanel pForm, pTable;
	private JLabel lName, lBdate, lPhone, lEmail, lSearch;
	private JTextField tfName, tfBdate, tfPhone, tfEmail, tfSearch;
	private JButton bCreate, bDelete, bRead, bUpdate, bSearch;
	private JScrollPane spTable;
	private JTable tTable;

	public View() {
		contactsDAO = new ContactsDAO();
		setTitle("Contacts Application");
		setSize(1000, 600);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		createForm();
		//pack();
		refreshTable();
	}

	public void createForm() {
		pForm = new JPanel();
		pForm.setBackground(Color.WHITE);
		pForm.setLayout(new GridLayout(5, 4, 11, 6));

		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

		pForm.setLayout(new java.awt.GridLayout(5, 4, 11, 6));

		lName = new JLabel("Name: ");
		lName.setHorizontalAlignment(SwingConstants.RIGHT);
		tfName = new JTextField();
		pForm.add(lName);
		pForm.add(tfName);

		bCreate = new JButton("Create");
		bCreate.setBackground(Color.WHITE);
		bCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				create();
			}
		});
		pForm.add(bCreate);

		lBdate = new JLabel("Birth Date: ");
		lBdate.setHorizontalAlignment(SwingConstants.RIGHT);
		tfBdate = new JTextField();
		pForm.add(lBdate);
		pForm.add(tfBdate);

		bRead = new JButton("Read");
		bRead.setBackground(Color.WHITE);
		bRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				read();
			}
		});
		pForm.add(bRead);

		lPhone = new JLabel("Phone Nr: ");
		lPhone.setHorizontalAlignment(SwingConstants.RIGHT);
		tfPhone = new JTextField();
		pForm.add(lPhone);
		pForm.add(tfPhone);

		bUpdate = new JButton("Update");
		bUpdate.setBackground(Color.WHITE);
		bUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				update();
			}
		});
		pForm.add(bUpdate);

		lEmail = new JLabel("Email: ");
		lEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		tfEmail = new JTextField();
		pForm.add(lEmail);
		pForm.add(tfEmail);

		bDelete = new JButton("Delete");
		bDelete.setBackground(Color.WHITE);
		bDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				delete();
			}
		});
		pForm.add(bDelete);
		
		lSearch = new JLabel("Search: ");
		lSearch.setHorizontalAlignment(SwingConstants.RIGHT);
		tfSearch = new JTextField();
		pForm.add(lSearch);
		pForm.add(tfSearch);

		bSearch = new JButton("Search");
		bSearch.setBackground(Color.WHITE);
		bSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				search();
			}
		});
		pForm.add(bSearch);

		getContentPane().add(pForm);

		pTable = new JPanel();
		tTable = new JTable();
		pTable.setLayout(new BorderLayout());
		tTable.setModel(new DefaultTableModel(new Object[5][5], new String[] {
				"Id", "Name", "Birth Date", "Phone Number", "Email" }));
		spTable = new JScrollPane();
		spTable.setViewportView(tTable);
		//spTable.setOpaque(false);
		spTable.getViewport().setBackground(Color.WHITE);
		pTable.add(spTable, BorderLayout.CENTER);
		tTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				read();
			}
		});

		getContentPane().add(pTable);
	}

	public void cleanFields() {
		tfName.setText("");
		tfBdate.setText("");
		tfPhone.setText("");
		tfEmail.setText("");
	}

	public boolean isValidData() {
		if (tfName.getText().isEmpty() || tfBdate.getText().isEmpty()
				|| tfPhone.getText().isEmpty() || tfEmail.getText().isEmpty())
			return false;
		else
			return true;
	}

	public void refreshTable() {
		DefaultTableModel tableModel = (DefaultTableModel) tTable.getModel();
		tableModel.setNumRows(0);
		
		List<Bean> beans = contactsDAO.readAll();

		for (int i = 0; i < beans.size(); i++) {
			Bean user = beans.get(i);

			tableModel.addRow(new Object[] { 1 });

			tTable.setValueAt(user.getId(), i, 0);
			tTable.setValueAt(user.getName(), i, 1);
			tTable.setValueAt(user.getBdate(), i, 2);
			tTable.setValueAt(user.getPhone(), i, 3);
			tTable.setValueAt(user.getEmail(), i, 4);

		}
		
	}
	
	public void resetTable()
	{
		//spTable.removeAll();
		spTable.setViewportView(tTable);
		spTable.revalidate();
		spTable.repaint();
	}

	public int getSelectedId() {
		int rowSelection = tTable.getSelectedRow();
		if (rowSelection >= 0) {
			int idSelection = (int) tTable.getValueAt(rowSelection, 0);
			return idSelection;
		} else {
			System.err.println("There is no row selected");
			return -1;
		}
	}

	public void create() {
		Bean bean = new Bean(tfName.getText(), tfBdate.getText(),
				tfPhone.getText(), tfEmail.getText());

		if (isValidData()) {
			contactsDAO.create(bean);
			refreshTable();
			JOptionPane.showMessageDialog(null, "User " + tfName.getText()
					+ " successfully inserted");
			cleanFields();
		} else {
			JOptionPane
			.showMessageDialog(null,
					"User can not be inserted. All fields must be filled in before inserting.");
		}

	}

	public void read() {
		if (getSelectedId() >= 0) {
			Bean bean = contactsDAO.read(getSelectedId());
			cleanFields();
			tfName.setText(bean.getName());
			tfBdate.setText(bean.getBdate());
			tfPhone.setText(bean.getPhone());
			tfEmail.setText(bean.getEmail());
			update();
		}
		resetTable();
	}

	public void update() {
		if (getSelectedId() >= 0) {
			Bean bean = new Bean(getSelectedId(), tfName.getText(),
					tfBdate.getText(), tfPhone.getText(), tfEmail.getText());
			contactsDAO.update(bean);
			refreshTable();
		}

	}
	
	public void searchTable(ArrayList<Object[]> searchList)
	{
		Object[][] searchTable = new Object[searchList.size()][5];
		System.out.println(searchList.size());
		for(int i = 0; i < searchList.size(); i++)
		{
			searchTable[i] = searchList.get(i); 
		}
		spTable.setViewportView(new JTable(searchTable, new String[] {
				"Id", "Name", "Birth Date", "Phone Number", "Email" }));
		spTable.revalidate();
	}

	public void delete() {
		DefaultTableModel tableModel = (DefaultTableModel) tTable.getModel();
		int rowSelection = tTable.getSelectedRow();
		if (rowSelection >= 0) {
			System.out.println("User id=" + getSelectedId()
					+ " removed.");
			contactsDAO.delete(getSelectedId());
			tableModel.removeRow(rowSelection);
			refreshTable();
			cleanFields();
		} else {
			JOptionPane.showMessageDialog(null,
					"No user selected for removal.");
		}
	}
	
	public void search(){
		searchTable(contactsDAO.search(tfSearch.getText()));
	}
		
}