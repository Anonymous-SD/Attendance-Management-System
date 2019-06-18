/*

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attenmansys;

import attenmansys.Serv.ServerRMIInf;
import de.javasoft.plaf.synthetica.SyntheticaPlainLookAndFeel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Anonymous
 */
public class AdminHome extends JFrame implements ActionListener, MenuListener {

    JMenuBar mb1;
    JMenu logot, ShwMemDat;
    private int w, h;
    JPanel rightPanel;
    JPanel leftPanel;
    Dimension screenSize;
    JLabel wc;
    JSplitPane sp;
    JTabbedPane tp;
    JScrollPane sr;
    JButton ManStu, ManTch, ManStaff;
    int wid, hei;
    EmailValidator emailValidator = new EmailValidator();
    private BufferedImage image;
    ArrayList<String> admdata = new ArrayList<String>();
    //  JSeparator ajs=new JSeparator(SwingConstants.VERTICAL);
    Font lab = new Font("Times New Roman", Font.BOLD, 20);
    Font labs = new Font("Times New Roman", Font.PLAIN, 20);
    Font labb = new Font("Times New Roman", Font.BOLD, 25);
    Font tit = new Font("Times New Roman", Font.BOLD, 22);

    public AdminHome(ArrayList tdata) {

        admdata = tdata;
        mb1 = new JMenuBar();
        setJMenuBar(mb1);
        logot = new JMenu("Logout");
        mb1.add(logot);
        logot.addMenuListener(this);
        //frame
        setTitle("Admin Home");
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, screenSize.width, screenSize.height);
        setVisible(true);
        // setLayout(null);

        //panel
        leftPanel = new JPanel();
        leftPanel.setBackground(new Color(174, 226, 249));
        leftPanel.setLayout(null);

        rightPanel = new JPanel();
        rightPanel.setBackground(new Color(174, 226, 249));
        rightPanel.setLayout(null);

        //tabbed
        tp = new JTabbedPane();
        tp.setFont(lab);
        tp.addTab("Manage Members & Department", new Tab1());

        tp.addTab("Manage Admin & Member Credentials", new Tab4());
        tp.addTab("Manage Attendance", new Tab5());

        tp.setPreferredSize(new Dimension(350, 900));

        //
        //   Container con=this.getContentPane();
        setExtendedState(this.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension((int) (screenSize.width / 1.5), (int) (screenSize.height / 1.5)));
        setSize((int) (screenSize.width / 1.5), (int) (screenSize.height / 1.5));
        //Components
        JScrollPane s = new JScrollPane(tp, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        add(s);

        sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        sp.setLeftComponent(s);
        sp.setRightComponent(rightPanel);

        sp.setEnabled(false);

        sp.setOneTouchExpandable(true);

        sp.setDividerLocation(screenSize.width / 3);

        // ajs.setBackground(Color.red);
        // ajs.setBounds(screenSize.width/4,screenSize.height/15 ,100,(int)(screenSize.height/1.2));
        //add component
        //con.add(ajs);
        add(sp);

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                sp.setDividerLocation(getWidth() / 3);

                wid = getWidth();
                hei = getHeight();

            }
        });
        SwingUtilities.updateComponentTreeUI(this);

    }

    public static void main(String[] abc) {

        try {

            UIManager.setLookAndFeel(new SyntheticaPlainLookAndFeel());
        } catch (ParseException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        ArrayList ar = null;
        new AdminHome(ar);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void menuSelected(MenuEvent e) {
        if (e.getSource() == logot) {
            try {
                Login login = new Login();
                this.dispose();
            } catch (Exception ex) {

            }
        }
    }

    @Override
    public void menuDeselected(MenuEvent e) {

    }

    @Override
    public void menuCanceled(MenuEvent e) {
    }

    class ViewMem extends JPanel implements ActionListener, PopupMenuListener {

        JLabel MHead, LDep, SClass, SCourse, View;
        JRadioButton te, stn, sf;
        JComboBox SCClass, SCCourseComb, TDept;
        JTable maindt;
        JButton getdat, Save;
        DefaultTableModel dtm;
        ButtonGroup teabg;
        JScrollPane scp;

        String[] THead = {"user id", "first name", "last name", "department", "teach_id", "Email", "Mobile"};
        String[] StnHead = {"user id", "first name", "last name", "class", "course", "roll no", "Email"};
        String[] StfHead = {"user id", "first name", "last name", "Email", "Mobile", "staff id"};

        public ViewMem(String pico) {
            setLayout(null);
            MHead = new JLabel("View Members' Details");
            MHead.setFont(new Font("Old English Text MT", Font.BOLD, 35));
            add(MHead);
            MHead.setBounds((int) (wid / 6), hei / 12, 600, 30);
            te = new JRadioButton("Teacher");
            te.setFont(lab);
            stn = new JRadioButton("Student");
            stn.setFont(lab);
            sf = new JRadioButton("Staff");
            sf.setFont(lab);

            View = new JLabel("View(details):");
            View.setFont(tit);
            View.setBounds(wid / 10, hei / 6, 200, 40);
            add(View);

            teabg = new ButtonGroup();
            teabg.add(te);
            teabg.add(stn);
            teabg.add(sf);
            getdat = new JButton("Get Data");
            getdat.setFont(lab);
            getdat.setBounds(wid / 4, (int) (hei / 2.9), 200, 35);
            add(getdat);
            te.setBounds(wid / 4, hei / 6, 100, 40);
            stn.setBounds(wid / 3, hei / 6, 100, 40);
            sf.setBounds((int) (wid / 2.4), hei / 6, 100, 40);

            Save = new JButton("Save data");
            Save.setFont(lab);
            Save.setBounds(wid / 4, (int) (hei / 1.3), 200, 35);
            add(Save);

            add(te);
            add(stn);
            add(sf);
            maindt = new JTable();
            dtm = new DefaultTableModel(0, 0) {

                public boolean isCellEditable(int row, int column) {
                    return false;//This causes all cells to be not editable
                }
            ;

            };
           dtm.setColumnIdentifiers(THead);

            maindt.setModel(dtm);

            scp = new JScrollPane(maindt, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            maindt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            TableColumn column = maindt.getColumnModel().getColumn(0);
            column.setPreferredWidth(150);
            column = maindt.getColumnModel().getColumn(1);
            column.setPreferredWidth(150);
            column = maindt.getColumnModel().getColumn(2);
            column.setPreferredWidth(150);
            column = maindt.getColumnModel().getColumn(3);
            column.setPreferredWidth(150);
            column = maindt.getColumnModel().getColumn(4);
            column.setPreferredWidth(150);
            column = maindt.getColumnModel().getColumn(5);
            column.setPreferredWidth(150);
            column = maindt.getColumnModel().getColumn(6);
            column.setPreferredWidth(150);

            scp.setSize(new Dimension(700, 250));
            scp.setLocation(wid / 12, (int) (hei / 2.5));
            add(scp);

            LDep = new JLabel("Department:");
            LDep.setFont(lab);
            LDep.setBounds((int) (wid / 6), (int) (hei / 4), 200, 40);
            add(LDep);

            TDept = new JComboBox();
            TDept.setFont(lab);
            TDept.setBounds((int) (wid / 3.5), (int) (hei / 4), 200, 35);
            add(TDept);

            SClass = new JLabel("Class:");
            SClass.setFont(lab);
            SClass.setBounds((int) (wid / 10), (int) (hei / 4), 200, 35);
            add(SClass);
            SCourse = new JLabel("Courses:");
            SCourse.setFont(lab);
            SCourse.setBounds((int) (wid / 3), (int) (hei / 4), 200, 35);
            add(SCourse);
            SCClass = new JComboBox();
            SCClass.setFont(lab);

            SCClass.setBounds((int) (wid / 7), (int) (hei / 4), 200, 35);
            SCCourseComb = new JComboBox();
            SCCourseComb.setFont(lab);
            SCCourseComb.setBounds((int) (wid / 2.5), (int) (hei / 4), 200, 35);
            add(SCClass);
            add(SCCourseComb);
            SCClass.addPopupMenuListener(this);
            SCCourseComb.addPopupMenuListener(this);
            TDept.addPopupMenuListener(this);
            LDep.setVisible(true);
            SClass.setVisible(false);
            SCourse.setVisible(false);
            SCClass.setVisible(false);
            SCCourseComb.setVisible(false);
            TDept.setVisible(true);

            te.addActionListener(this);
            stn.addActionListener(this);
            sf.addActionListener(this);
            getdat.addActionListener(this);
            Save.addActionListener(this);
            te.setSelected(true);
            try {
                image = ImageIO.read(new File(pico));
                w = image.getWidth();
                h = image.getHeight();

            } catch (IOException ioe) {
                System.out.println("Could not read in the pic");
                //System.exit(0);
            }
            Save.setVisible(false);
        }

        public Dimension getPreferredSize() {
            return new Dimension(w * 2, h * 2);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (te.isSelected()) {
                if (dtm.getRowCount() > 0) {
                    for (int i = dtm.getRowCount() - 1; i > -1; i--) {
                        dtm.removeRow(i);
                    }
                }
                LDep.setVisible(true);
                SClass.setVisible(false);
                SCourse.setVisible(false);
                SCClass.setVisible(false);
                SCCourseComb.setVisible(false);
                TDept.setVisible(true);
                dtm.setColumnIdentifiers(THead);
                TableColumn column = maindt.getColumnModel().getColumn(0);
                column.setPreferredWidth(150);
                column = maindt.getColumnModel().getColumn(1);
                column.setPreferredWidth(150);
                column = maindt.getColumnModel().getColumn(2);
                column.setPreferredWidth(150);
                column = maindt.getColumnModel().getColumn(3);
                column.setPreferredWidth(150);
                column = maindt.getColumnModel().getColumn(4);
                column.setPreferredWidth(150);
                column = maindt.getColumnModel().getColumn(5);
                column.setPreferredWidth(150);
                column = maindt.getColumnModel().getColumn(6);
                column.setPreferredWidth(150);

                if (e.getSource() == getdat) {
                    int errcount = 0;
                    String deps = "";
                    if (TDept.getSelectedItem() != null) {
                        deps = TDept.getSelectedItem().toString();
                    } else {
                        JOptionPane.showMessageDialog(this, "please select department.");
                        errcount = 1;
                    }
                    if (errcount != 1) {
                        Registry re;
                        try {

                            re = LocateRegistry.getRegistry(9111);
                            ServerRMIInf rm = (ServerRMIInf) re.lookup("getMemData");

                            String[][] res = rm.getMemData("Teacher", deps, "none");

                            for (int i = 0; i < res.length; i++) {
                                dtm.addRow(new Object[]{res[i][0], res[i][1], res[i][2], res[i][3], res[i][4], res[i][5], res[i][6]});

                            }
                        } catch (RemoteException | NotBoundException ex) {
                            ex.printStackTrace();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                if (e.getSource() == Save) {

                }

            }
            if (stn.isSelected()) {
                if (dtm.getRowCount() > 0) {
                    for (int i = dtm.getRowCount() - 1; i > -1; i--) {
                        dtm.removeRow(i);
                    }
                }
                LDep.setVisible(false);
                SClass.setVisible(true);
                SCourse.setVisible(true);
                SCClass.setVisible(true);
                SCCourseComb.setVisible(true);
                TDept.setVisible(false);
                dtm.setColumnIdentifiers(StnHead);

                TableColumn column = maindt.getColumnModel().getColumn(0);
                column.setPreferredWidth(150);
                column = maindt.getColumnModel().getColumn(1);
                column.setPreferredWidth(150);
                column = maindt.getColumnModel().getColumn(2);
                column.setPreferredWidth(150);
                column = maindt.getColumnModel().getColumn(3);
                column.setPreferredWidth(150);
                column = maindt.getColumnModel().getColumn(4);
                column.setPreferredWidth(150);
                column = maindt.getColumnModel().getColumn(5);
                column.setPreferredWidth(150);
                column = maindt.getColumnModel().getColumn(6);
                column.setPreferredWidth(150);
                if (e.getSource() == getdat) {
                    int errcount = 0;

                    String cls = "", cour = "";
                    if (SCClass.getSelectedItem() != null) {
                        cls = SCClass.getSelectedItem().toString();
                    } else {
                        errcount = 1;
                        JOptionPane.showMessageDialog(this, "Please select class.");
                    }
                    if (SCCourseComb.getSelectedItem() != null) {
                        cour = SCCourseComb.getSelectedItem().toString();
                    } else if (errcount != 1) {
                        errcount = 1;
                        JOptionPane.showMessageDialog(this, "Please select course combination.");
                    }
                    if (errcount != 1) {
                        Registry re;
                        try {

                            re = LocateRegistry.getRegistry(9111);
                            ServerRMIInf rm = (ServerRMIInf) re.lookup("getMemData");

                            String[][] res = rm.getMemData("Student", cls, cour);

                            for (int i = 0; i < res.length; i++) {
                                dtm.addRow(new Object[]{res[i][0], res[i][1], res[i][2], res[i][3], res[i][4], res[i][5], res[i][6]});

                            }
                        } catch (RemoteException | NotBoundException ex) {
                            ex.printStackTrace();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                }
                if (e.getSource() == Save) {
                }

            }
            if (sf.isSelected()) {
                if (dtm.getRowCount() > 0) {
                    for (int i = dtm.getRowCount() - 1; i > -1; i--) {
                        dtm.removeRow(i);
                    }
                }
                LDep.setVisible(false);
                SClass.setVisible(false);
                SCourse.setVisible(false);
                SCClass.setVisible(false);
                SCCourseComb.setVisible(false);
                TDept.setVisible(false);
                dtm.setColumnIdentifiers(StfHead);
                TableColumn column = maindt.getColumnModel().getColumn(0);
                column.setPreferredWidth(150);
                column = maindt.getColumnModel().getColumn(1);
                column.setPreferredWidth(150);
                column = maindt.getColumnModel().getColumn(2);
                column.setPreferredWidth(150);
                column = maindt.getColumnModel().getColumn(3);
                column.setPreferredWidth(150);
                column = maindt.getColumnModel().getColumn(4);
                column.setPreferredWidth(150);
                column = maindt.getColumnModel().getColumn(5);
                column.setPreferredWidth(150);

                if (e.getSource() == getdat) {
                    Registry re;
                    try {

                        re = LocateRegistry.getRegistry(9111);
                        ServerRMIInf rm = (ServerRMIInf) re.lookup("getMemData");

                        String[][] res = rm.getMemData("Staff", "none", "none");

                        if (res != null) {
                            int i = 0;
                            dtm.addRow(new Object[]{res[i][0], res[i][1], res[i][2], res[i][3], res[i][4], res[i][5]});
                        } else {
                            JOptionPane.showMessageDialog(this, "no data found.");
                        }

                    } catch (RemoteException | NotBoundException ex) {
                        ex.printStackTrace();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                if (e.getSource() == Save) {
                }

            }
        }

        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            if (e.getSource() == SCCourseComb) {
                Registry re;
                try {
                    re = LocateRegistry.getRegistry(9111);
                    ServerRMIInf rm = (ServerRMIInf) re.lookup("ShowCourses");
                    ArrayList<String> res = new ArrayList<String>();
                    res = rm.ShowCourses(SCClass.getSelectedItem().toString(), "no");
                    String[] rest = new String[res.size()];
                    DefaultComboBoxModel listModel = new DefaultComboBoxModel();
                    for (int i = 0; i < res.size(); i++) {
                        listModel.addElement(res.get(i));

                    }
                    SCCourseComb.setModel(listModel);

                } catch (RemoteException | NotBoundException ex) {
                    ex.printStackTrace();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            } else if (e.getSource() == SCClass) {
                SCClass.removeAllItems();
                Registry re;
                try {

                    re = LocateRegistry.getRegistry(9111);
                    ServerRMIInf rm = (ServerRMIInf) re.lookup("ShowClasses");
                    ArrayList<String> res = new ArrayList<String>();
                    res = rm.ShowClass("class");
                    String[] rest = new String[res.size()];

                    for (int i = 0; i < res.size(); i++) {
                        SCClass.addItem(res.get(i));

                    }

                } catch (RemoteException | NotBoundException ex) {
                    ex.printStackTrace();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else if (e.getSource() == TDept) {
                TDept.removeAllItems();
                Registry re;
                try {
                    re = LocateRegistry.getRegistry(9111);
                    ServerRMIInf rm = (ServerRMIInf) re.lookup("ShowDept");
                    ArrayList<String> res = new ArrayList<String>();
                    res = rm.ShowDept("depts");

                    for (int i = 0; i < res.size(); i++) {
                        TDept.addItem(res.get(i));
                    }
                } catch (Exception ex) {

                }

            }
        }

        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
        }

        @Override
        public void popupMenuCanceled(PopupMenuEvent e) {
            SCCourseComb.hidePopup();
            SCClass.hidePopup();
            TDept.hidePopup();
        }

    }

    class Tab1 extends JPanel implements ActionListener {

        JButton add, Rem, edit, mandepcls, View;
        JLabel Teastustf, Stf, DeptCls;
        JPanel centerPanel, southPanel;

        public Tab1() {
            centerPanel = new JPanel();
            centerPanel.setOpaque(true);
            centerPanel.setBackground(Color.darkGray);
            GridLayout gl = new GridLayout(4, 1);
            centerPanel.setLayout(gl);
            gl.setHgap(50);
            gl.setVgap(50);

            centerPanel.setBorder(
                    BorderFactory.createTitledBorder("Student/Teachers/Staff"));
            centerPanel.setPreferredSize(new Dimension(320, 400));

            add = new JButton("Add");
            Rem = new JButton("Remove");
            edit = new JButton("Edit");
            View = new JButton("View details");

            mandepcls = new JButton("<html><body>Manage Department <br>/Class</body></html>");

            add.addActionListener(this);
            Rem.addActionListener(this);
            edit.addActionListener(this);
            View.addActionListener(this);
            mandepcls.addActionListener(this);

            add.setFont(lab);
            Rem.setFont(lab);
            edit.setFont(lab);
            View.setFont(lab);
            mandepcls.setFont(labb);

            add.setSize(200, 40);
            edit.setSize(200, 40);
            Rem.setSize(200, 40);

            mandepcls.setSize(350, 40);
            southPanel = new JPanel();
            southPanel.setOpaque(true);
            southPanel.setBackground(Color.darkGray);
            GridLayout gl1 = new GridLayout(2, 1);
            southPanel.setLayout(gl1);
            gl1.setHgap(50);
            gl1.setVgap(50);

            southPanel.setBorder(
                    BorderFactory.createTitledBorder("Department/Class"));
            southPanel.setPreferredSize(new Dimension(320, 220));

            centerPanel.add(add);
            centerPanel.add(Rem);
            centerPanel.add(edit);
            centerPanel.add(View);

            add(centerPanel, BorderLayout.NORTH);
            southPanel.add(mandepcls);
            add(southPanel, BorderLayout.SOUTH);
            SwingUtilities.updateComponentTreeUI(this);

            addComponentListener(new ComponentAdapter() {
                public void componentResized(ComponentEvent e) {

                    sp.setDividerLocation(wid / 3);
                }
            });

        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == add) {
                sp.setRightComponent(new AddTea("C:\\AMSRF\\other\\222.jpg"));

            }
            if (e.getSource() == edit) {
                sp.setRightComponent(new EditMem("C:\\AMSRF\\other\\222.jpg"));
            }
            if (e.getSource() == Rem) {
                sp.setRightComponent(new RemMem("C:\\AMSRF\\other\\222.jpg"));
            }
            if (e.getSource() == mandepcls) {
                sp.setRightComponent(new MDeptClss("C:\\AMSRF\\other\\222.jpg"));
            }
            if (e.getSource() == View) {
                sp.setRightComponent(new ViewMem("C:\\AMSRF\\other\\222.jpg"));
            }

        }

    }

    class Tab4 extends JPanel implements ActionListener {

        JPanel centerPanel, southPanel;
        JButton ChAdm, EditAdm;
        JButton SendUsrCred;

        public Tab4() {

            ChAdm = new JButton("Change Admin");
            EditAdm = new JButton("Edit Admin");
            SendUsrCred = new JButton("Send Credentials");
            SendUsrCred.setFont(lab);
            EditAdm.setFont(lab);
            ChAdm.setFont(lab);
            ChAdm.setSize(200, 40);
            EditAdm.setSize(200, 40);

            SendUsrCred.setSize(200, 40);
            centerPanel = new JPanel();
            centerPanel.setOpaque(true);
            centerPanel.setBackground(Color.darkGray);
            GridLayout gl = new GridLayout(3, 1);
            centerPanel.setLayout(gl);
            gl.setHgap(50);
            gl.setVgap(50);

            centerPanel.setBorder(
                    BorderFactory.createTitledBorder("Admin Settings"));
            centerPanel.setPreferredSize(new Dimension(320, 300));
            centerPanel.add(ChAdm);
            centerPanel.add(EditAdm);

            southPanel = new JPanel();
            southPanel.setOpaque(true);
            southPanel.setBackground(Color.darkGray);
            GridLayout gl1 = new GridLayout(2, 1);
            southPanel.setLayout(gl1);
            gl1.setHgap(50);
            gl1.setVgap(50);

            southPanel.setBorder(
                    BorderFactory.createTitledBorder("Member Credentials"));
            southPanel.setPreferredSize(new Dimension(320, 200));

            southPanel.add(SendUsrCred);

            add(centerPanel, BorderLayout.NORTH);
            add(southPanel, BorderLayout.SOUTH);
            SwingUtilities.updateComponentTreeUI(this);

            ChAdm.addActionListener(this);
            EditAdm.addActionListener(this);
            SendUsrCred.addActionListener(this);

            addComponentListener(new ComponentAdapter() {
                public void componentResized(ComponentEvent e) {

                }
            });
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == ChAdm) {
                sp.setRightComponent(new ChangeAdmin("C:\\AMSRF\\other\\222.jpg"));
            }
            if (e.getSource() == EditAdm) {
                sp.setRightComponent(new EditAdmin("C:\\AMSRF\\other\\222.jpg"));
            }
            if (e.getSource() == SendUsrCred) {
                sp.setRightComponent(new SendUsrCreden("C:\\AMSRF\\other\\222.jpg"));
            }

        }

    }

    class Tab5 extends JPanel implements ActionListener {

        JButton VTAtt, GenTAttR, VGenTAttR, VSAtt, VGenSAttR, VSDef;

        JPanel centerPanel, southPanel;

        public Tab5() {

            VTAtt = new JButton("Check Attendance");
            GenTAttR = new JButton("Generate Report");
            VGenTAttR = new JButton("View Reports");

            VSAtt = new JButton("Check Attendance");
            VGenSAttR = new JButton("View Reports");
            VSDef = new JButton("View Defaulters");

            VTAtt.setFont(lab);
            GenTAttR.setFont(lab);
            VGenTAttR.setFont(lab);

            VSAtt.setFont(lab);
            VGenSAttR.setFont(lab);
            VSDef.setFont(lab);

            VTAtt.setSize(200, 40);
            GenTAttR.setSize(200, 40);
            VGenTAttR.setSize(200, 40);

            VSAtt.setSize(200, 40);
            VGenSAttR.setSize(200, 40);
            VSDef.setSize(200, 40);

            centerPanel = new JPanel();
            centerPanel.setOpaque(true);
            centerPanel.setBackground(Color.darkGray);
            GridLayout gl = new GridLayout(4, 1);
            centerPanel.setLayout(gl);
            gl.setHgap(50);
            gl.setVgap(50);

            centerPanel.setBorder(
                    BorderFactory.createTitledBorder("Teacher Attendance"));
            centerPanel.setPreferredSize(new Dimension(320, 400));

            centerPanel.add(VTAtt);
            centerPanel.add(GenTAttR);
            centerPanel.add(VGenTAttR);

            southPanel = new JPanel();
            southPanel.setOpaque(true);
            southPanel.setBackground(Color.darkGray);
            GridLayout gl1 = new GridLayout(4, 1);
            southPanel.setLayout(gl1);
            gl1.setHgap(50);
            gl1.setVgap(50);

            southPanel.setBorder(
                    BorderFactory.createTitledBorder("Student Attendance"));
            southPanel.setPreferredSize(new Dimension(320, 400));

            southPanel.add(VSAtt);
            southPanel.add(VGenSAttR);
            southPanel.add(VSDef);

            add(centerPanel, BorderLayout.NORTH);
            add(southPanel, BorderLayout.SOUTH);

            VTAtt.addActionListener(this);
            GenTAttR.addActionListener(this);
            VGenTAttR.addActionListener(this);
            VSAtt.addActionListener(this);
            VGenSAttR.addActionListener(this);
            VSDef.addActionListener(this);

            SwingUtilities.updateComponentTreeUI(this);
            addComponentListener(new ComponentAdapter() {
                public void componentResized(ComponentEvent e) {

                }
            });

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == VTAtt) {
                sp.setRightComponent(new ViewTeaAtt("C:\\AMSRF\\other\\222.jpg"));
            }
            if (e.getSource() == GenTAttR) {
                sp.setRightComponent(new GenTeaAttRep("C:\\AMSRF\\other\\222.jpg"));
            }
            if (e.getSource() == VGenTAttR) {
                sp.setRightComponent(new ViewGenTeaAttRep("C:\\AMSRF\\other\\222.jpg"));
            }
            if (e.getSource() == VSAtt) {
                sp.setRightComponent(new ViewStuAtt("C:\\AMSRF\\other\\222.jpg"));
            }
            if (e.getSource() == VGenSAttR) {
                sp.setRightComponent(new ViewGenStuAttRep("C:\\AMSRF\\other\\222.jpg"));
            }
            if (e.getSource() == VSDef) {
                sp.setRightComponent(new ViewStuDefRep("C:\\AMSRF\\other\\222.jpg"));
            }
        }

    }

    class AddTea extends JPanel implements ActionListener, PopupMenuListener {

        TableInternalFrame tif;

        JButton Prev, ToDb, addl, addTPT;
        JLabel head, Add, instype, note;
        JRadioButton singT, multiT, te, stn, sf;
        ButtonGroup teabg, addw;
        int sfct = 1;
        JTable tb;

        //Sing 
        JLabel TFname, TLname, TDept, TEmail, TMob, TId, EnDet;
        JComboBox TCDept;
        JTextField TTFname, TTLname, TTEmail, TTMob, TTId;

        JComboBox SCClass, SCCourseComb;
        JLabel SClass, SCourse, SFname, SLname, SRollno, SEmail;
        JTextField SSFname, SSLname, SSRollno, SSEmail;
        JLabel StfFname, StfLname, StfEmail, StfMob, StfId;
        JTextField SStfFname, SStfLname, SStfEmail, SStfMob, SStfId;
        int i = 1;
        JFileChooser f1;
        FileInputStream fis = null;

        public AddTea(String picurl) {

            f1 = new JFileChooser();
            f1.addActionListener(this);

            setLayout(null);

            //singLE DATA
            TFname = new JLabel("First Name:");
            TFname.setFont(lab);
            TFname.setBounds((int) (wid / 10), (int) (hei / 2.2), 200, 40);
            add(TFname);

            TLname = new JLabel("Last Name:");
            TLname.setFont(lab);
            TLname.setBounds((int) (wid / 3), (int) (hei / 2.2), 200, 40);
            add(TLname);

            TId = new JLabel("Id:");
            TId.setFont(lab);
            TId.setBounds((int) (wid / 10), (int) (hei / 1.98), 200, 40);
            add(TId);
            TDept = new JLabel("Department:");
            TDept.setFont(lab);
            TDept.setBounds((int) (wid / 3), (int) (hei / 1.98), 200, 40);
            add(TDept);

            TMob = new JLabel("Mobile:");
            TMob.setFont(lab);
            TMob.setBounds((int) (wid / 10), (int) (hei / 1.78), 200, 40);
            add(TMob);
            TEmail = new JLabel("Email:");
            TEmail.setFont(lab);
            TEmail.setBounds((int) (wid / 3), (int) (hei / 1.78), 200, 40);
            add(TEmail);

            //TFs
            TTFname = new JTextField();
            TTFname.setFont(labs);
            TTFname.setDocument(new JTextFieldLimit(23));
            TTFname.setBounds((int) (wid / 5.3), (int) (hei / 2.18), 160, 30);
            add(TTFname);

            TTLname = new JTextField();
            TTLname.setFont(labs);
            TTLname.setDocument(new JTextFieldLimit(23));
            TTLname.setBounds((int) (wid / 2.3), (int) (hei / 2.2), 160, 30);
            add(TTLname);

            TTId = new JTextField();
            TTId.setFont(labs);
            TTId.setBounds((int) (wid / 5.3), (int) (hei / 1.96), 160, 30);
            TTId.setDocument(new JTextFieldLimit(15));
            add(TTId);

            TCDept = new JComboBox();
            TCDept.setFont(labs);
            TCDept.setBounds((int) (wid / 2.3), (int) (hei / 1.96), 160, 30);
            add(TCDept);

            TTMob = new JTextField();
            TTMob.setFont(labs);
            TTMob.setBounds((int) (wid / 5.3), (int) (hei / 1.76), 160, 30);
            TTMob.setDocument(new JTextFieldLimit(10));
            add(TTMob);
            TTEmail = new JTextField();
            TTEmail.setFont(labs);
            TTEmail.setBounds((int) (wid / 2.3), (int) (hei / 1.76), 160, 30);
            add(TTEmail);
            TTEmail.setDocument(new JTextFieldLimit(30));
            //SLS
            SFname = new JLabel("First Name:");
            SFname.setFont(lab);
            SFname.setBounds((int) (wid / 10), (int) (hei / 2.2), 200, 40);
            add(SFname);
            SLname = new JLabel("Last Name:");
            SLname.setFont(lab);
            SLname.setBounds((int) (wid / 3), (int) (hei / 2.2), 200, 40);
            add(SLname);

            SClass = new JLabel("Class:");
            SClass.setFont(lab);
            SClass.setBounds((int) (wid / 3), (int) (hei / 1.98), 200, 40);
            add(SClass);
            SCourse = new JLabel("Courses:");
            SCourse.setFont(lab);
            SCourse.setBounds((int) (wid / 10), (int) (hei / 1.78), 200, 40);
            add(SCourse);
            SRollno = new JLabel("Rollno.");
            SRollno.setFont(lab);
            SRollno.setBounds((int) (wid / 3), (int) (hei / 1.78), 200, 40);
            add(SRollno);
            SEmail = new JLabel("Email:");
            SEmail.setFont(lab);
            SEmail.setBounds((int) (wid / 10), (int) (hei / 1.98), 200, 40);
            add(SEmail);

            //STFs
            SSFname = new JTextField();
            SSFname.setFont(labs);
            SSFname.setDocument(new JTextFieldLimit(23));
            SSFname.setBounds((int) (wid / 5.3), (int) (hei / 2.18), 160, 30);
            add(SSFname);

            SSLname = new JTextField();
            SSLname.setFont(labs);
            SSLname.setBounds((int) (wid / 2.3), (int) (hei / 2.2), 160, 30);
            SSLname.setDocument(new JTextFieldLimit(23));
            add(SSLname);

            SCClass = new JComboBox();
            SCClass.setFont(labs);
            SCClass.setBounds((int) (wid / 2.3), (int) (hei / 1.96), 160, 30);

            add(SCClass);

            SCCourseComb = new JComboBox();
            SCCourseComb.setFont(labs);
            SCCourseComb.setBounds((int) (wid / 5.3), (int) (hei / 1.76), 160, 30);
            add(SCCourseComb);

            SSRollno = new JTextField();
            SSRollno.setFont(labs);
            SSRollno.setBounds((int) (wid / 2.3), (int) (hei / 1.76), 160, 30);
            SSRollno.setDocument(new JTextFieldLimit(4));
            add(SSRollno);

            SSEmail = new JTextField();
            SSEmail.setFont(labs);
            SSEmail.setBounds((int) (wid / 5.3), (int) (hei / 1.96), 160, 30);
            SSEmail.setDocument(new JTextFieldLimit(30));
            add(SSEmail);

            //Stfls
            StfFname = new JLabel("First Name:");
            StfFname.setFont(lab);
            StfFname.setBounds((int) (wid / 10), (int) (hei / 2.2), 200, 40);
            add(StfFname);

            StfLname = new JLabel("Last Name:");
            StfLname.setFont(lab);
            StfLname.setBounds((int) (wid / 3), (int) (hei / 2.2), 200, 40);
            add(StfLname);

            StfEmail = new JLabel("Email:");
            StfEmail.setFont(lab);
            StfEmail.setBounds((int) (wid / 10), (int) (hei / 1.98), 200, 40);
            add(StfEmail);

            StfMob = new JLabel("Mobile:");
            StfMob.setFont(lab);
            StfMob.setBounds((int) (wid / 3), (int) (hei / 1.98), 200, 40);
            add(StfMob);

            StfId = new JLabel("Staff Id:");
            StfId.setFont(lab);
            StfId.setBounds((int) (wid / 10), (int) (hei / 1.78), 200, 40);
            add(StfId);

            //StfTFs
            SStfFname = new JTextField();
            SStfFname.setFont(labs);
            SStfFname.setBounds((int) (wid / 5.3), (int) (hei / 2.18), 160, 30);
            SStfFname.setDocument(new JTextFieldLimit(23));
            add(SStfFname);

            SStfLname = new JTextField();
            SStfLname.setFont(labs);
            SStfLname.setBounds((int) (wid / 2.3), (int) (hei / 2.2), 160, 30);
            SStfLname.setDocument(new JTextFieldLimit(23));
            add(SStfLname);

            SStfEmail = new JTextField();
            SStfEmail.setFont(labs);
            SStfEmail.setBounds((int) (wid / 5.3), (int) (hei / 1.96), 160, 30);
            SStfEmail.setDocument(new JTextFieldLimit(30));
            add(SStfEmail);

            SStfMob = new JTextField();
            SStfMob.setFont(labs);
            SStfMob.setBounds((int) (wid / 2.3), (int) (hei / 1.96), 160, 30);
            SStfMob.setDocument(new JTextFieldLimit(10));
            add(SStfMob);

            SStfId = new JTextField();
            SStfId.setFont(labs);
            SStfId.setBounds((int) (wid / 5.3), (int) (hei / 1.76), 160, 30);
            SStfId.setDocument(new JTextFieldLimit(15));
            add(SStfId);

            //heading
            head = new JLabel("Add Members");
            //add
            addl = new JButton("Add");
            Add = new JLabel("Add:");
            te = new JRadioButton("Teacher");
            stn = new JRadioButton("Student");
            sf = new JRadioButton("Staff");
            addTPT = new JButton("Add to preview Table");
            addw = new ButtonGroup();
            addw.add(te);
            addw.add(stn);
            addw.add(sf);

            Prev = new JButton("Preview");
            ToDb = new JButton("Add to Database");

            //type
            instype = new JLabel("Insertion Type:");
            singT = new JRadioButton("Single");
            EnDet = new JLabel("Enter Details:");

            multiT = new JRadioButton("Multiple(list)");
            teabg = new ButtonGroup();

            teabg.add(singT);
            teabg.add(multiT);

            //addT.addActionListener(this);
            head.setFont(new Font("Old English Text MT", Font.BOLD, 35));
            addTPT.setFont(lab);
            singT.setFont(lab);
            Prev.setFont(lab);
            addl.setFont(lab);
            te.setFont(lab);
            stn.setFont(lab);
            sf.setFont(lab);

            ToDb.setFont(lab);
            multiT.setFont(lab);
            instype.setFont(tit);
            Add.setFont(tit);
            EnDet.setFont(tit);

            head.setBounds((int) (wid / 4.1), hei / 8, 400, 40);
            singT.setBounds((int) (wid / 4), hei / 3, 100, 40);
            multiT.setBounds((int) (wid / 3), hei / 3, 150, 40);
            instype.setBounds((int) (wid / 10), hei / 3, 200, 40);
            EnDet.setBounds((int) (wid / 10), (int) (hei / 2.5), 200, 40);

            Add.setBounds(wid / 10, hei / 4, 200, 40);
            te.setBounds(wid / 4, hei / 4, 100, 40);
            stn.setBounds(wid / 3, hei / 4, 100, 40);
            sf.setBounds((int) (wid / 2.4), hei / 4, 100, 40);

            addTPT.setBounds((int) (wid / 9.2), (int) (hei / 1.43), 250, 40);
            Prev.setBounds((int) (wid / 3), (int) (hei / 1.43), 150, 40);
            ToDb.setBounds((int) (wid / 5), (int) (hei / 1.27), 200, 40);

            //add.setBounds();
            add(te);
            add(stn);
            add(sf);
            add(Add);

            add(Prev);

            add(ToDb);
            add(addTPT);
            add(head);
            add(singT);
            add(multiT);
            add(instype);
            add(EnDet);

            //events
            note = new JLabel();

            note.setForeground(Color.red);
            note.setBounds(wid / 10, (int) (hei / 2.3), 600, 100);
            note.setVisible(false);
            add(note);

            addTPT.addActionListener(this);
            Prev.addActionListener(this);
            ToDb.addActionListener(this);
            singT.addActionListener(this);
            multiT.addActionListener(this);
            te.addActionListener(this);
            stn.addActionListener(this);
            TCDept.addPopupMenuListener(this);
            sf.addActionListener(this);
            SCClass.addPopupMenuListener(this);
            SCCourseComb.addPopupMenuListener(this);

//select
            note.setVisible(false);
            SSFname.setVisible(false);
            SSLname.setVisible(false);

            SCClass.setVisible(false);
            SSRollno.setVisible(false);
            SCCourseComb.setVisible(false);
            SSEmail.setVisible(false);

            SClass.setVisible(false);
            SCourse.setVisible(false);
            SFname.setVisible(false);
            SLname.setVisible(false);
            SRollno.setVisible(false);

            SEmail.setVisible(false);

            StfFname.setVisible(false);
            StfLname.setVisible(false);
            StfEmail.setVisible(false);
            StfMob.setVisible(false);
            StfId.setVisible(false);

            SStfFname.setVisible(false);
            SStfLname.setVisible(false);
            SStfEmail.setVisible(false);
            SStfMob.setVisible(false);
            SStfId.setVisible(false);

            singT.setSelected(true);
            te.setSelected(true);
            addTPT.setVisible(false);
            Prev.setVisible(false);

            try {
                image = ImageIO.read(new File(picurl));
                w = image.getWidth();
                h = image.getHeight();

            } catch (IOException ioe) {
                System.out.println("Could not read in the pic");
                //System.exit(0);
            }

            SwingUtilities.updateComponentTreeUI(this);

            addComponentListener(new ComponentAdapter() {
                public void componentResized(ComponentEvent e) {

                    TFname.setLocation((int) (wid / 10), (int) (hei / 2.2));
                    TLname.setLocation((int) (wid / 3), (int) (hei / 2.2));
                    TId.setLocation((int) (wid / 10), (int) (hei / 1.98));
                    TDept.setLocation((int) (wid / 3), (int) (hei / 1.98));
                    TMob.setLocation((int) (wid / 10), (int) (hei / 1.78));
                    TEmail.setLocation((int) (wid / 3), (int) (hei / 1.78));

                    TTFname.setLocation((int) (wid / 5.3), (int) (hei / 2.18));
                    TTLname.setLocation((int) (wid / 2.3), (int) (hei / 2.2));
                    TTId.setLocation((int) (wid / 5.3), (int) (hei / 1.96));
                    TCDept.setLocation((int) (wid / 2.3), (int) (hei / 1.96));
                    TTMob.setLocation((int) (wid / 5.3), (int) (hei / 1.76));
                    TTEmail.setLocation((int) (wid / 2.3), (int) (hei / 1.76));

                    SFname.setLocation((int) (wid / 10), (int) (hei / 2.2));
                    SLname.setLocation((int) (wid / 3), (int) (hei / 2.2));
                    SEmail.setLocation((int) (wid / 10), (int) (hei / 1.98));
                    SClass.setLocation((int) (wid / 3), (int) (hei / 1.98));
                    SCourse.setLocation((int) (wid / 10), (int) (hei / 1.78));
                    SRollno.setLocation((int) (wid / 3), (int) (hei / 1.78));

                    SSFname.setLocation((int) (wid / 5.3), (int) (hei / 2.18));
                    SSLname.setLocation((int) (wid / 2.3), (int) (hei / 2.2));
                    SSEmail.setLocation((int) (wid / 5.3), (int) (hei / 1.96));
                    SCClass.setLocation((int) (wid / 2.3), (int) (hei / 1.96));
                    SCCourseComb.setLocation((int) (wid / 5.3), (int) (hei / 1.76));
                    SSRollno.setLocation((int) (wid / 2.3), (int) (hei / 1.76));

                    StfFname.setLocation((int) (wid / 10), (int) (hei / 2.2));
                    StfLname.setLocation((int) (wid / 3), (int) (hei / 2.2));
                    StfEmail.setLocation((int) (wid / 10), (int) (hei / 1.98));
                    StfMob.setLocation((int) (wid / 3), (int) (hei / 1.98));
                    StfId.setLocation((int) (wid / 10), (int) (hei / 1.78));

                    SStfFname.setLocation((int) (wid / 5.3), (int) (hei / 2.18));
                    SStfLname.setLocation((int) (wid / 2.3), (int) (hei / 2.2));
                    SStfEmail.setLocation((int) (wid / 5.3), (int) (hei / 1.96));
                    SStfMob.setLocation((int) (wid / 2.3), (int) (hei / 1.96));
                    SStfId.setLocation((int) (wid / 5.3), (int) (hei / 1.76));

                    head.setLocation((int) (wid / 4.2), hei / 8);
                    singT.setLocation((int) (wid / 4), hei / 3);
                    multiT.setLocation((int) (wid / 3), hei / 3);
                    instype.setLocation((int) (wid / 10), hei / 3);
                    Add.setLocation(wid / 10, hei / 4);
                    te.setLocation(wid / 4, hei / 4);
                    EnDet.setLocation((int) (wid / 10), (int) (hei / 2.5));
                    stn.setLocation(wid / 3, hei / 4);
                    sf.setLocation((int) (wid / 2.4), hei / 4);
                    note.setLocation(wid / 10, (int) (hei / 2.3));
                    addTPT.setLocation((int) (wid / 9.2), (int) (hei / 1.43));
                    Prev.setLocation((int) (wid / 3), (int) (hei / 1.43));
                    ToDb.setLocation((int) (wid / 5), (int) (hei / 1.27));

                    if (multiT.isSelected()) {

                        TCDept.setLocation(wid / 5, (int) (hei / 1.68));
                        TDept.setLocation(wid / 10, (int) (hei / 1.7));
                        SClass.setLocation(wid / 10, (int) (hei / 1.7));
                        SCClass.setLocation(wid / 6, (int) (hei / 1.68));
                        SCourse.setLocation(wid / 3, (int) (hei / 1.7));
                        SCCourseComb.setLocation((int) (wid / 2.5), (int) (hei / 1.68));
                    }

                }
            });

        }

        public Dimension getPreferredSize() {
            return new Dimension(w * 2, h * 2);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if (multiT.isSelected()) {
                ToDb.setVisible(false);
                addTPT.setVisible(true);
                Prev.setVisible(true);
                if (i == 1) {

                    JOptionPane.showMessageDialog(rootPane, "Needs Excel file to import multiple data");
                    i++;
                }

            }
            if (singT.isSelected() || multiT.isSelected()) {
                EnDet.setVisible(false);
                note.setVisible(false);
                SCClass.setVisible(false);
                SCCourseComb.setVisible(false);
                SSFname.setVisible(false);
                SSLname.setVisible(false);
                SSRollno.setVisible(false);

                SSEmail.setVisible(false);

                SClass.setVisible(false);
                SCourse.setVisible(false);
                SFname.setVisible(false);
                SLname.setVisible(false);
                SRollno.setVisible(false);

                SEmail.setVisible(false);

                StfFname.setVisible(false);
                StfLname.setVisible(false);
                StfEmail.setVisible(false);
                StfMob.setVisible(false);
                StfId.setVisible(false);

                SStfFname.setVisible(false);
                SStfLname.setVisible(false);
                SStfEmail.setVisible(false);
                SStfMob.setVisible(false);
                SStfId.setVisible(false);

                TFname.setVisible(false);
                TLname.setVisible(false);
                TDept.setVisible(false);
                TEmail.setVisible(false);
                TMob.setVisible(false);

                TId.setVisible(false);

                TTFname.setVisible(false);
                TTLname.setVisible(false);
                TCDept.setVisible(false);
                TTEmail.setVisible(false);
                TTMob.setVisible(false);

                TTId.setVisible(false);

            }

            if (singT.isSelected() && te.isSelected()) {
                ToDb.setVisible(true);
                addTPT.setVisible(false);
                Prev.setVisible(false);
                TDept.setLocation((int) (wid / 3), (int) (hei / 1.98));
                TCDept.setLocation((int) (wid / 2.3), (int) (hei / 1.96));
                EnDet.setVisible(true);
                TTFname.setVisible(true);
                TTLname.setVisible(true);
                TCDept.setVisible(true);
                TTEmail.setVisible(true);
                TTMob.setVisible(true);

                TTId.setVisible(true);

                TFname.setVisible(true);
                TLname.setVisible(true);
                TDept.setVisible(true);
                TEmail.setVisible(true);
                TMob.setVisible(true);

                TId.setVisible(true);

            }
            if (singT.isSelected() && stn.isSelected()) {
                ToDb.setVisible(true);
                addTPT.setVisible(false);
                Prev.setVisible(false);
                SClass.setLocation((int) (wid / 3), (int) (hei / 1.98));
                SCourse.setLocation((int) (wid / 10), (int) (hei / 1.78));
                SCClass.setLocation((int) (wid / 2.3), (int) (hei / 1.96));
                SCCourseComb.setLocation((int) (wid / 5.3), (int) (hei / 1.76));

                EnDet.setVisible(true);
                SCClass.setVisible(true);
                SCCourseComb.setVisible(true);
                SSFname.setVisible(true);
                SSLname.setVisible(true);
                SSRollno.setVisible(true);

                SSEmail.setVisible(true);

                SClass.setVisible(true);
                SCourse.setVisible(true);
                SFname.setVisible(true);
                SLname.setVisible(true);
                SRollno.setVisible(true);

                SEmail.setVisible(true);

            }
            if (singT.isSelected() && sf.isSelected()) {
                ToDb.setVisible(true);
                addTPT.setVisible(false);
                Prev.setVisible(false);
                EnDet.setVisible(true);
                StfFname.setVisible(true);
                StfLname.setVisible(true);
                StfEmail.setVisible(true);
                StfMob.setVisible(true);
                StfId.setVisible(true);

                SStfFname.setVisible(true);
                SStfLname.setVisible(true);
                SStfEmail.setVisible(true);
                SStfMob.setVisible(true);
                SStfId.setVisible(true);

            }

            if (multiT.isSelected() && te.isSelected()) {
                TCDept.setVisible(true);
                TDept.setVisible(true);
                TCDept.setLocation(wid / 5, (int) (hei / 1.68));
                TDept.setLocation(wid / 10, (int) (hei / 1.7));
                note.setText("<html><body><font size=5>Note: To add multiple Teachers, an excel sheet "
                        + "is to be selected <br> and format of data in excel must be"
                        + " 'first name,last name,teacher id,email id,mobile'.</font></body></html>");

                note.setVisible(true);
            }
            if (multiT.isSelected() && stn.isSelected()) {

                SClass.setVisible(true);
                SCClass.setVisible(true);
                SCourse.setVisible(true);
                SCCourseComb.setVisible(true);

                SClass.setLocation(wid / 10, (int) (hei / 1.7));
                SCClass.setLocation(wid / 6, (int) (hei / 1.68));

                SCourse.setLocation(wid / 3, (int) (hei / 1.7));
                SCCourseComb.setLocation((int) (wid / 2.5), (int) (hei / 1.68));

                note.setText("<html><body><font size=5>Note: To add multiple students, an excel sheet "
                        + "is to be selected <br> and format of data in excel must be"
                        + " 'first name,last name,<br> roll no,email'.</font></body></html>");
                note.setVisible(true);

            }
            if (multiT.isSelected() && sf.isSelected()) {
                note.setVisible(false);
                addTPT.setVisible(false);
                Prev.setVisible(false);
                singT.setSelected(true);
                JOptionPane.showMessageDialog(this, "Only one staff is allowed to be appointed.");
                EnDet.setVisible(true);
                StfFname.setVisible(true);
                StfLname.setVisible(true);
                StfEmail.setVisible(true);
                StfMob.setVisible(true);
                StfId.setVisible(true);

                SStfFname.setVisible(true);
                SStfLname.setVisible(true);
                SStfEmail.setVisible(true);
                SStfMob.setVisible(true);
                SStfId.setVisible(true);

            }

//Adding single teacher data
            if (te.isSelected()) {

                if (singT.isSelected()) {

                    if (e.getSource() == ToDb) {
                        int errcount = 0;
                        String TFN, TLN, TID, TDEPT = null, TMOB, TEMAIL;

                        TFN = TTFname.getText().trim();
                        TLN = TTLname.getText().trim();
                        TID = TTId.getText().trim();
                        if (TFN.matches(".*\\d+.*") || TLN.matches(".*\\d+.*")) {
                            JOptionPane.showMessageDialog(this, "Invalid name.");
                            errcount = 1;
                        }
                        if (TCDept.getSelectedItem() != null) {
                            TDEPT = TCDept.getSelectedItem().toString().trim();

                        } else if (errcount != 1) {
                            errcount = 1;
                            JOptionPane.showMessageDialog(this, "Please select Department.");
                        }
                        TMOB = TTMob.getText().trim();
                        TEMAIL = TTEmail.getText().trim();
                        String[] TData = {TFN, TLN, TDEPT, TID, TEMAIL, TMOB};

                        if (errcount != 1 && TFN.trim().isEmpty() || TLN.trim().isEmpty() || TID.trim().isEmpty() || TDEPT.trim().isEmpty() || TMOB.trim().isEmpty() || TEMAIL.trim().isEmpty()) {
                            JOptionPane.showMessageDialog(this, "All fields are mandatory.");
                            errcount = 1;
                        }

                        long mobno;
                        if (errcount != 1) {
                            try {
                                mobno = Long.parseLong(TMOB);
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(this, "Invalid mobile number.");
                                errcount = 1;
                            }
                        }

                        if (errcount != 1 && !emailValidator.validate(TEMAIL.trim())) {
                            JOptionPane.showMessageDialog(this, "Invalid Email.");
                            errcount = 1;
                        }

                        if (errcount != 1) {
                            Registry re;
                            try {
                                re = LocateRegistry.getRegistry(9111);
                                ServerRMIInf rm = (ServerRMIInf) re.lookup("AddSingTea");

                                String res = rm.AddSingTeacher(TData);
                                if (res.equals("Successful")) {
                                    JOptionPane.showMessageDialog(this, "Teacher added Successfully.");

                                    TTFname.setText("");
                                    TTLname.setText("");
                                    TTId.setText("");
                                    TTMob.setText("");
                                    TTEmail.setText("");

                                } else if (res.equals("Unsuccessful")) {
                                    JOptionPane.showMessageDialog(this, "Error Occured.");
                                }

                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(this, "Unable to connect to server, Please check yur connection.");
                            }

                        }
                    }
                }

                if (multiT.isSelected()) {
                    String departmt = null;

                    String abc = "none";
                    if (e.getSource() == addTPT) {
                        int errcount = 0;
                        System.out.println("before " + TCDept.getSelectedItem());
                        if (TCDept.getSelectedItem() == null) {
                            errcount = 1;
                            JOptionPane.showMessageDialog(this, "Please select Department.");
                            System.out.println("after " + TCDept.getSelectedItem());
                        } else {
                            departmt = TCDept.getSelectedItem().toString();
                            String[][] value = null;
                            int[][] nums = null;
                            f1.showOpenDialog(addTPT);
                            File f = new File(f1.getSelectedFile().toString());

                            try {
                                fis = new FileInputStream(f);

                                XSSFWorkbook workbook = new XSSFWorkbook(fis);
                                XSSFSheet sh1 = workbook.getSheetAt(0);
                                XSSFRow row;
                                XSSFCell cell;

                                int sheetCn = workbook.getNumberOfSheets();

                                for (int cn = 0; cn < sheetCn; cn++) {

                                    // get 0th sheet data
                                    XSSFSheet sheet = workbook.getSheetAt(cn);

                                    // get number of rows from sheet
                                    int rows = sheet.getPhysicalNumberOfRows();

                                    // get number of cell from row
                                    int cells = sheet.getRow(cn).getPhysicalNumberOfCells();

                                    value = new String[rows][cells];

                                    for (int r = 0; r < rows; r++) {
                                        row = sheet.getRow(r);
// bring row
                                        if (row != null) {
                                            for (int c = 0; c < cells; c++) {
                                                cell = row.getCell(c);
                                                nums = new int[rows][cells];

                                                if (cell != null) {

                                                    switch (cell.getCellType()) {

                                                        case XSSFCell.CELL_TYPE_FORMULA:
                                                            value[r][c] = cell.getCellFormula();
                                                            break;
                                                        case XSSFCell.CELL_TYPE_NUMERIC:

                                                            long l;
                                                            l = (new Double(cell.getNumericCellValue())).longValue();
                                                            System.out.println("sdfs " + l);
                                                            value[r][c] = ""
                                                                    + l;
                                                            break;

                                                        case XSSFCell.CELL_TYPE_STRING:
                                                            value[r][c] = ""
                                                                    + cell.getStringCellValue();
                                                            break;

                                                        case XSSFCell.CELL_TYPE_BLANK:
                                                            value[r][c] = "[BLANK]";

                                                            break;

                                                        case XSSFCell.CELL_TYPE_ERROR:
                                                            value[r][c] = "" + cell.getErrorCellValue();
                                                            break;
                                                        default:
                                                    }
                                                    System.out.print(value[r][c]);

                                                } else {

                                                    System.out.print("[null]\t");

                                                }
                                            } // for(c)
                                            System.out.print("\n");
                                        }
                                    } // for(r)
                                }

                            } catch (FileNotFoundException ex) {

                            } catch (IOException ex) {
                            }

                            String[] THead = {"First Name", "Last Name", "Teacher Id", "Email", "Mobile"};

                            int Ccount = value[0].length;
                            System.out.println(Ccount);
                            if (Ccount < 5 || Ccount > 5) {
                                JOptionPane.showMessageDialog(this, "check the columns in the excel file and select with exact number of columns");

                            } else {

                                DefaultTableModel model = new DefaultTableModel(value, THead);
                                tb = new JTable(model);

                                tif = new TableInternalFrame(tb, departmt, "inv", "inv", "teacher");

                            }

                        }
                    }
                    if (e.getSource() == Prev) {
                        if (tb != null) {
                            tif.setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(this, "select an excel file first by clicking add to preview button");

                        }
                    }
                }
            }

//Single Student Data
            if (stn.isSelected()) {
                if (singT.isSelected()) {

                    if (e.getSource() == ToDb) {
                        int errcount = 0;
                        String SFN, SLN, SCLASS = null, SCOURSE = null, SROLLNO, SEMAIL;
                        SFN = SSFname.getText().trim();
                        SLN = SSLname.getText().trim();
                        if (SFN.matches(".*\\d+.*") || SLN.matches(".*\\d+.*")) {
                            JOptionPane.showMessageDialog(this, "Invalid name.");
                            errcount = 1;
                        }
                        if (SCClass.getSelectedItem() != null) {
                            SCLASS = SCClass.getSelectedItem().toString().trim();

                        } else if (errcount != 1) {
                            errcount = 1;
                            JOptionPane.showMessageDialog(this, "Please select class.");
                        }
                        if (errcount != 1 && SCCourseComb.getSelectedItem() != null) {
                            SCOURSE = SCCourseComb.getSelectedItem().toString().trim();

                        } else if (errcount != 1) {
                            errcount = 1;
                            JOptionPane.showMessageDialog(this, "Please select Course.");
                        }

                        SROLLNO = SSRollno.getText().trim();
                        SEMAIL = SSEmail.getText().trim();
                        if (errcount != 1 && SFN.trim().isEmpty() || SLN.trim().isEmpty() || SROLLNO.trim().isEmpty() || SEMAIL.trim().isEmpty()) {
                            JOptionPane.showMessageDialog(this, "All fields are mandatory.");
                            errcount = 1;
                        }

                        int rollno;
                        if (errcount != 1) {
                            try {
                                rollno = Integer.parseInt(SROLLNO);
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(this, "Invalid Roll number.");
                                errcount = 1;
                            }
                        }

                        if (errcount != 1 && !emailValidator.validate(SEMAIL.trim())) {
                            JOptionPane.showMessageDialog(this, "Invalid Email.");
                            errcount = 1;
                        }

                        String[] stuData = {SFN, SLN, SCLASS, SCOURSE, SROLLNO, SEMAIL};
                        if (errcount != 1) {
                            Registry re;
                            try {
                                re = LocateRegistry.getRegistry(9111);
                                ServerRMIInf rm = (ServerRMIInf) re.lookup("AddSingStu");

                                String res = rm.AddSingStudent(stuData);
                                if (res.equals("Successful")) {
                                    JOptionPane.showMessageDialog(this, "Student added Successfully.");
                                    SSFname.setText("");
                                    SSLname.setText("");
                                    SSRollno.setText("");
                                    SSEmail.setText("");
                                } else if (res.equals("Unsuccessful")) {
                                    JOptionPane.showMessageDialog(this, "Error Occured.");
                                }

                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(this, "Please Check your connection");

                            }
                        }
                    }

                }
                if (multiT.isSelected()) {

                    if (e.getSource() == addTPT) {
                        int errcount = 0;
                        if (SCClass.getSelectedItem() == null) {
                            errcount = 1;
                            JOptionPane.showMessageDialog(this, "Please select class.");
                        }
                        if (errcount != 1 && SCCourseComb.getSelectedItem() == null) {
                            errcount = 1;
                            JOptionPane.showMessageDialog(this, "Please select Course.");

                        }

                        if (errcount != 1) {
                            String cls = SCClass.getSelectedItem().toString();
                            String cour = SCCourseComb.getSelectedItem().toString();
                            String abc = "none";
                            String[][] value = null;
                            int[][] nums = null;
                            f1.showOpenDialog(addTPT);
                            File f = new File(f1.getSelectedFile().toString());

                            try {
                                fis = new FileInputStream(f);

                                XSSFWorkbook workbook = new XSSFWorkbook(fis);
                                XSSFSheet sh1 = workbook.getSheetAt(0);
                                XSSFRow row;
                                XSSFCell cell;

                                int sheetCn = workbook.getNumberOfSheets();

                                for (int cn = 0; cn < sheetCn; cn++) {

                                    // get 0th sheet data
                                    XSSFSheet sheet = workbook.getSheetAt(cn);

                                    // get number of rows from sheet
                                    int rows = sheet.getPhysicalNumberOfRows();

                                    // get number of cell from row
                                    int cells = sheet.getRow(cn).getPhysicalNumberOfCells();

                                    value = new String[rows][cells];

                                    for (int r = 0; r < rows; r++) {
                                        row = sheet.getRow(r); // bring row
                                        if (row != null) {
                                            for (int c = 0; c < cells; c++) {
                                                cell = row.getCell(c);
                                                nums = new int[rows][cells];

                                                if (cell != null) {

                                                    switch (cell.getCellType()) {

                                                        case XSSFCell.CELL_TYPE_FORMULA:
                                                            value[r][c] = cell.getCellFormula();
                                                            break;
                                                        case XSSFCell.CELL_TYPE_NUMERIC:
                                                            long l;
                                                            l = (new Double(cell.getNumericCellValue())).longValue();
                                                            value[r][c] = ""
                                                                    + l;
                                                            break;

                                                        case XSSFCell.CELL_TYPE_STRING:
                                                            value[r][c] = ""
                                                                    + cell.getStringCellValue();
                                                            break;

                                                        case XSSFCell.CELL_TYPE_BLANK:
                                                            value[r][c] = "[BLANK]";
                                                            break;

                                                        case XSSFCell.CELL_TYPE_ERROR:
                                                            value[r][c] = "" + cell.getErrorCellValue();
                                                            break;
                                                        default:
                                                    }
                                                    System.out.print(value[r][c]);

                                                } else {
                                                    System.out.print("[null]\t");

                                                }
                                            } // for(c)
                                            System.out.print("\n");
                                        }
                                    } // for(r)
                                }

                            } catch (FileNotFoundException ex) {

                            } catch (IOException ex) {
                            }

                            int Ccount = value[0].length;
                            System.out.println(Ccount);
                            if (Ccount < 4 || Ccount > 4) {
                                JOptionPane.showMessageDialog(this, "check the columns in the excel file and select with exact number of columns");

                            } else {
                                String[] THead = {"First Name", "Last Name", "Roll no", "Email"};
                                DefaultTableModel model = new DefaultTableModel(value, THead);
                                tb = new JTable(model);

                                tif = new TableInternalFrame(tb, "inv", SCClass.getSelectedItem().toString().trim(), SCCourseComb.getSelectedItem().toString().trim(), "student");
                            }
                        }
                    }
                    if (e.getSource() == Prev) {
                        if (tb != null) {
                            tif.setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(this, "select an excel file first by clicking add to preview button");
                        }

                    }
                }
            }

            if (sf.isSelected()) {
                if (sfct == 1) {
                    JOptionPane.showMessageDialog(this, "If staff has already been added, the old data will be replaced with new data.");
                    sfct++;
                }
                if (singT.isSelected()) {
                    if (e.getSource() == ToDb) {
                        int errcount = 0;
                        String STFFNAME, STFLNAME, STFEMAIL, STFMOB, STFID;
                        STFFNAME = SStfFname.getText().trim();
                        STFLNAME = SStfLname.getText().trim();
                        if (STFFNAME.matches(".*\\d+.*") || STFLNAME.matches(".*\\d+.*")) {
                            JOptionPane.showMessageDialog(this, "Invalid name.");
                            errcount = 1;
                        }
                        STFEMAIL = SStfEmail.getText().trim();
                        STFMOB = SStfMob.getText().trim();
                        STFID = SStfId.getText().trim();
                        if (errcount != 1 && STFFNAME.trim().isEmpty() || STFID.trim().isEmpty() || STFLNAME.trim().isEmpty() || STFEMAIL.trim().isEmpty() || STFMOB.trim().isEmpty()) {
                            JOptionPane.showMessageDialog(this, "All fields are mandatory.");
                            errcount = 1;
                        }

                        long mob;
                        if (errcount != 1) {

                            try {
                                mob = Long.parseLong(STFMOB);
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(this, "Invalid mobile number.");
                                errcount = 1;
                            }
                        }
                        if (errcount != 1 && !emailValidator.validate(STFEMAIL.trim())) {
                            JOptionPane.showMessageDialog(this, "Invalid Email.");
                            errcount = 1;
                        }

                        String[] staffData = {STFFNAME, STFLNAME, STFEMAIL, STFMOB, STFID};
                        if (errcount != 1) {
                            Registry re;
                            try {
                                re = LocateRegistry.getRegistry(9111);
                                ServerRMIInf rm = (ServerRMIInf) re.lookup("AddSingStaff");

                                String res = rm.AddSingStaff(staffData);
                                if (res.equals("Successful")) {
                                    JOptionPane.showMessageDialog(this, "Staff added Successfully.");
                                    SStfLname.setText("");
                                    SStfFname.setText("");
                                    SStfMob.setText("");
                                    SStfId.setText("");
                                } else if (res.equals("Unsuccessful")) {
                                    JOptionPane.showMessageDialog(this, "Error Occured.");
                                }

                            } catch (RemoteException | NotBoundException ex) {
                                ex.printStackTrace();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }

                }

            }

        }
        int a = 1;

        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {

            if (e.getSource() == SCCourseComb) {
                SCCourseComb.removeAllItems();
                Registry re;
                try {

                    re = LocateRegistry.getRegistry(9111);
                    ServerRMIInf rm = (ServerRMIInf) re.lookup("ShowCourses");
                    ArrayList<String> res = new ArrayList<String>();
                    res = rm.ShowCourses(SCClass.getSelectedItem().toString().trim(), "no");
                    String[] rest = new String[res.size()];

                    for (int i = 0; i < res.size(); i++) {
                        SCCourseComb.addItem(res.get(i));

                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "check your connection.");
                    ex.printStackTrace();
                }

            }

            if (e.getSource() == SCClass) {
                SCClass.removeAllItems();
                Registry re;
                try {

                    re = LocateRegistry.getRegistry(9111);
                    ServerRMIInf rm = (ServerRMIInf) re.lookup("ShowClasses");
                    ArrayList<String> res = new ArrayList<String>();
                    res = rm.ShowClass("class");
                    String[] rest = new String[res.size()];

                    for (int i = 0; i < res.size(); i++) {
                        SCClass.addItem(res.get(i));

                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "check your connection.");
                    ex.printStackTrace();
                }

            }

            if (e.getSource() == TCDept) {
                TCDept.removeAllItems();
                Registry re;
                try {

                    re = LocateRegistry.getRegistry(9111);
                    ServerRMIInf rm = (ServerRMIInf) re.lookup("ShowDept");
                    ArrayList<String> res = new ArrayList<String>();
                    res = rm.ShowDept("depts");
                    String[] rest = new String[res.size()];

                    for (int i = 0; i < res.size(); i++) {
                        TCDept.addItem(res.get(i));

                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "check your connection.");
                    ex.printStackTrace();
                }

            }
        }

        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

        }

        @Override
        public void popupMenuCanceled(PopupMenuEvent e) {
            SCCourseComb.hidePopup();
        }
    }

//JTable InterNal Frame
    class TableInternalFrame extends JFrame implements ActionListener {

        JTable newtb;
        JButton sub;
        String dep;
        String typ;
        String cl;
        String cour;

        TableInternalFrame(JTable tbd, String dept, String cls, String course, String type) {
            typ = type;
            dep = dept;
            cl = cls;
            cour = course;
            newtb = tbd;
            sub = new JButton("add to database");
            setTitle("Data Table Preview");
            setSize(wid / 2, hei / 2);

            setLayout(new BorderLayout(3, 3));

            JScrollPane scp = new JScrollPane(newtb);
            scp.setSize(new Dimension(wid / 3, hei / 3));
            setMinimumSize(new Dimension(wid / 4, hei / 3));
            sub.setSize(300, 34);
            add(scp, BorderLayout.CENTER);
            add(sub, BorderLayout.SOUTH);

            sub.addActionListener(this);
            SwingUtilities.updateComponentTreeUI(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == sub) {
                int errcount = 0;
                if (typ.equals("teacher")) {
                    if (null != newtb.getCellEditor()) {

                        newtb.getCellEditor().stopCellEditing();
                    }

                    DefaultTableModel dtm = (DefaultTableModel) newtb.getModel();
                    int nRow = dtm.getRowCount(), nCol = dtm.getColumnCount();
                    String[][] tableData = new String[nRow][nCol];
                    for (int i = 0; i < nRow; i++) {

                        if (dtm.getValueAt(i, 0).toString().matches(".*\\d+.*") || dtm.getValueAt(i, 1).toString().matches(".*\\d+.*")) {
                            int rowcoun = i + 1;
                            JOptionPane.showMessageDialog(this, "Invalid name field found at row " + rowcoun);
                            errcount = 1;
                        }

                        if (errcount != 1 && dtm.getValueAt(i, 0).toString().trim().isEmpty() || dtm.getValueAt(i, 1).toString().trim().isEmpty() || dtm.getValueAt(i, 2).toString().trim().isEmpty() || dtm.getValueAt(i, 3).toString().trim().isEmpty() || dtm.getValueAt(i, 4).toString().trim().isEmpty()) {
                            int rowcoun = i + 1;
                            JOptionPane.showMessageDialog(this, "empty field found at row ." + rowcoun);
                            errcount = 1;
                        }

                        long roll;
                        if (errcount != 1) {

                            try {
                                roll = Long.parseLong(dtm.getValueAt(i, 4).toString().trim());
                            } catch (Exception ex) {
                                int rowcoun = i + 1;
                                JOptionPane.showMessageDialog(this, "Invalid mobile number field found at row." + rowcoun);
                                errcount = 1;
                            }
                        }
                        if (errcount != 1 && !emailValidator.validate(dtm.getValueAt(i, 3).toString())) {
                            int rowcoun = i + 1;
                            JOptionPane.showMessageDialog(this, "Invalid Email field found at row " + rowcoun);
                            errcount = 1;
                        }

                        for (int j = 0; j < nCol; j++) {

                            tableData[i][j] = (String) dtm.getValueAt(i, j);
                        }
                    }
                    if (errcount != 1) {
                        String Stat;
                        Registry re;
                        try {
                            re = LocateRegistry.getRegistry(9111);
                            ServerRMIInf rm = (ServerRMIInf) re.lookup("AddMultiTea");
                            Stat = rm.MultiTeaData(tableData, dep);
                            System.out.println(Stat);
                            if (Stat.equals("Successful")) {
                                JOptionPane.showMessageDialog(this, "Teacher data successfully added.");
                            } else if (Stat.equals("Unsuccessful")) {
                                JOptionPane.showMessageDialog(this, "Error Occured.");
                            }

                        } catch (RemoteException ex) {
                            Logger.getLogger(AdminHome.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (NotBoundException ex) {
                            Logger.getLogger(AdminHome.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (Exception ex) {
                            Logger.getLogger(AdminHome.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } else if (typ.contains("student")) {

                    if (null != newtb.getCellEditor()) {

                        newtb.getCellEditor().stopCellEditing();
                    }

                    DefaultTableModel dtm = (DefaultTableModel) newtb.getModel();
                    int nRow = dtm.getRowCount(), nCol = dtm.getColumnCount();
                    String[][] tableData = new String[nRow][nCol];
                    for (int i = 0; i < nRow; i++) {

                        if (dtm.getValueAt(i, 0).toString().matches(".*\\d+.*") || dtm.getValueAt(i, 1).toString().matches(".*\\d+.*")) {
                            int rowcoun = i + 1;
                            JOptionPane.showMessageDialog(this, "Invalid name field found at row " + rowcoun);
                            errcount = 1;
                        }

                        if (errcount != 1 && dtm.getValueAt(i, 0).toString().trim().isEmpty() || dtm.getValueAt(i, 1).toString().trim().isEmpty() || dtm.getValueAt(i, 2).toString().trim().isEmpty() || dtm.getValueAt(i, 3).toString().trim().isEmpty()) {
                            int rowcoun = i + 1;
                            JOptionPane.showMessageDialog(this, "empty field found at row ." + rowcoun);
                            errcount = 1;
                        }

                        long roll;
                        if (errcount != 1) {

                            try {
                                roll = Long.parseLong(dtm.getValueAt(i, 2).toString().trim());
                            } catch (Exception ex) {
                                int rowcoun = i + 1;
                                JOptionPane.showMessageDialog(this, "Invalid roll number field found at row." + rowcoun);
                                errcount = 1;
                            }
                        }
                        if (errcount != 1 && !emailValidator.validate(dtm.getValueAt(i, 3).toString())) {
                            int rowcoun = i + 1;
                            JOptionPane.showMessageDialog(this, "Invalid Email field found at row " + rowcoun);
                            errcount = 1;
                        }

                        for (int j = 0; j < nCol; j++) {
                            tableData[i][j] = (String) dtm.getValueAt(i, j);
                        }
                    }
                    if (errcount != 1) {
                        String Stat;
                        Registry re;
                        try {
                            re = LocateRegistry.getRegistry(9111);
                            ServerRMIInf rm = (ServerRMIInf) re.lookup("AddMultiStu");
                            Stat = rm.MultiStuData(tableData, cl, cour);
                            System.out.println(Stat);
                            if (Stat.equals("Successful")) {
                                JOptionPane.showMessageDialog(this, "Student data successfully added.");
                            } else if (Stat.equals("Unsuccessful")) {
                                JOptionPane.showMessageDialog(this, "Error Occured.");
                            }

                        } catch (RemoteException ex) {
                            Logger.getLogger(AdminHome.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (NotBoundException ex) {
                            Logger.getLogger(AdminHome.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (Exception ex) {
                            Logger.getLogger(AdminHome.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }

    }

    class MDeptClss extends JPanel implements ActionListener, PopupMenuListener, ItemListener {

        JButton ShwCls, ShwCourse;
        JList LCls, LCourse;
        JLabel MainHead, Manage, SelectAct, SelectToEdt;
        JRadioButton Dept, Class, AddD, RemD, EditD;
        JComboBox SelEdit, CourseCount;
        JList CourList, DeptList;
        ButtonGroup man, act, toEd;
        JButton ShowDept, AddDept, DelDept, addClass, DelClass, ShowCour, AddToDb;
        JTextField AddDTf1, AddTf2, AddTf3, AddTf4;

        public MDeptClss(String img) {
            setLayout(null);
            //Head
            MainHead = new JLabel("Manage Department and Class");
            MainHead.setFont(new Font("Old English Text MT", Font.BOLD, 35));

            //tit
            Manage = new JLabel("Manage:");
            Manage.setFont(tit);
            //typt
            Dept = new JRadioButton("Department");
            Class = new JRadioButton("Class");

            //Dept
            ShowDept = new JButton("Show Departments");
            DeptList = new JList();
            AddDept = new JButton("Add");
            AddDTf1 = new JTextField();
            AddTf2 = new JTextField();
            AddTf3 = new JTextField();
            AddTf4 = new JTextField();

            //action
            SelectAct = new JLabel("Select Action:");
            SelectAct.setFont(tit);
            AddD = new JRadioButton("Add");
            RemD = new JRadioButton("Delete");
            EditD = new JRadioButton("Edit");

            act = new ButtonGroup();
            act.add(AddD);
            act.add(RemD);
            act.add(EditD);

            man = new ButtonGroup();
            man.add(Dept);
            man.add(Class);

            //Class
            ShwCls = new JButton("Show Classes");
            ShwCourse = new JButton("Show Courses");
            LCls = new JList();
            LCourse = new JList();
            SelectToEdt = new JLabel("Select to Edit:");
            SelectToEdt.setFont(tit);
            String[] abc = {"Class", "Course Combo"};
            SelEdit = new JComboBox(abc);
            String[] Count = {"1", "2", "3", "4"};
            CourseCount = new JComboBox(Count);

            CourseCount.setVisible(false);
            AddDTf1.setVisible(false);
            AddTf3.setVisible(false);
            AddTf4.setVisible(false);

            //fonts
            Class.setFont(lab);
            Dept.setFont(lab);
            ShowDept.setFont(lab);
            DeptList.setFont(lab);
            AddD.setFont(lab);

            RemD.setFont(lab);
            EditD.setFont(lab);
            AddDept.setFont(lab);
            ShwCls.setFont(lab);
            ShwCourse.setFont(lab);
            SelEdit.setFont(lab);
            CourseCount.setFont(lab);

            //bounds
            MainHead.setBounds((int) (wid / 6), hei / 8, 700, 30);
            Manage.setBounds((int) (wid / 10), hei / 4, 200, 30);
            Dept.setBounds(wid / 4, hei / 4, 150, 40);
            Class.setBounds((int) (wid / 2.5), hei / 4, 100, 30);
            ShowDept.setBounds(wid / 8, (int) (hei / 2.5), 200, 30);
            DeptList.setBounds((int) (wid / 3.3), (int) (hei / 2.7), 200, 120);
            SelectAct.setBounds((int) (wid / 10), (int) (hei / 1.75), 200, 30);

            ShwCls.setBounds(wid / 17, (int) (hei / 2.5), 150, 30);
            LCls.setBounds((int) (wid / 5.5), (int) (hei / 2.7), 180, 120);

            ShwCourse.setBounds(wid / 3, (int) (hei / 2.5), 150, 30);
            LCourse.setBounds((int) (wid / 2), (int) (hei / 2.7), 180, 120);

            AddD.setBounds((int) (wid / 4.5), (int) (hei / 1.75), 100, 30);
            RemD.setBounds((int) (wid / 2.8), (int) (hei / 1.75), 100, 30);
            EditD.setBounds((int) (wid / 2), (int) (hei / 1.75), 100, 30);

            SelectToEdt.setBounds((int) (wid / 10), (int) (hei / 1.55), 200, 30);
            SelEdit.setBounds((int) (wid / 4), (int) (hei / 1.55), 150, 30);
            CourseCount.setBounds((int) (wid / 2.5), (int) (hei / 1.55), 70, 30);

            AddDept.setBounds((int) (wid / 5), (int) (hei / 1.25), 200, 35);
            AddDTf1.setBounds((int) (wid / 10), (int) (hei / 1.4), 130, 30);
            AddDTf1.setDocument(new JTextFieldLimit(25));
            AddTf2.setBounds((int) (wid / 4.5), (int) (hei / 1.4), 130, 30);
            AddTf2.setDocument(new JTextFieldLimit(25));
            AddTf3.setBounds((int) (wid / 3), (int) (hei / 1.4), 130, 30);
            AddTf3.setDocument(new JTextFieldLimit(25));
            AddTf4.setBounds((int) (wid / 2.2), (int) (hei / 1.4), 130, 30);
            AddTf4.setDocument(new JTextFieldLimit(25));
            add(MainHead);
            add(Class);
            add(Dept);
            add(Manage);
            add(SelectAct);
            add(AddD);
            add(RemD);
            add(EditD);
            add(DeptList);
            add(ShowDept);
            add(AddDept);
            add(AddDTf1);
            add(AddTf2);
            add(AddTf3);
            add(AddTf4);
            add(CourseCount);
            add(ShwCls);
            add(ShwCourse);
            add(LCls);
            add(LCourse);
            add(SelectToEdt);
            add(SelEdit);

            try {
                image = ImageIO.read(new File(img));
                w = image.getWidth();
                h = image.getHeight();

            } catch (IOException ioe) {
                System.out.println("Could not read in the pic");
                //System.exit(0);
            }
            addComponentListener(new ComponentAdapter() {
                public void componentResized(ComponentEvent e) {

                    MainHead.setLocation((int) (wid / 6), hei / 8);
                    Manage.setLocation((int) (wid / 10), hei / 4);
                    Dept.setLocation(wid / 4, hei / 4);
                    Class.setLocation((int) (wid / 2.5), hei / 4);
                    ShowDept.setLocation(wid / 8, (int) (hei / 2.5));
                    DeptList.setLocation((int) (wid / 3.3), (int) (hei / 2.7));
                    SelectAct.setLocation((int) (wid / 10), (int) (hei / 1.75));

                    AddD.setLocation((int) (wid / 4.5), (int) (hei / 1.75));
                    RemD.setLocation((int) (wid / 2.8), (int) (hei / 1.75));
                    EditD.setLocation((int) (wid / 2), (int) (hei / 1.75));

                    SelectToEdt.setLocation((int) (wid / 10), (int) (hei / 1.55));
                    SelEdit.setLocation((int) (wid / 4), (int) (hei / 1.55));

                    ShwCls.setLocation(wid / 17, (int) (hei / 2.5));
                    LCls.setLocation((int) (wid / 5.5), (int) (hei / 2.7));
                    ShwCourse.setLocation(wid / 3, (int) (hei / 2.5));
                    LCourse.setLocation((int) (wid / 2.2), (int) (hei / 2.7));

                }
            });

            //eventlisteners
            Dept.addActionListener(this);
            Class.addActionListener(this);
            AddD.addActionListener(this);
            RemD.addActionListener(this);
            EditD.addActionListener(this);
            SelEdit.addActionListener(this);
            SelEdit.addItemListener(this);
            CourseCount.addActionListener(this);
            ShowDept.addActionListener(this);
            ShwCls.addActionListener(this);
            ShwCourse.addActionListener(this);
            Dept.setSelected(true);
            AddD.setSelected(true);
            AddDept.addActionListener(this);

            ShwCls.setVisible(false);
            LCls.setVisible(false);
            ShwCourse.setVisible(false);
            LCourse.setVisible(false);
            SelectToEdt.setVisible(false);
            SelEdit.setVisible(false);

        }

        public Dimension getPreferredSize() {
            return new Dimension(w * 2, h * 2);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {

//Showing Department
            if (e.getSource() == ShowDept) {
                DeptList.removeAll();
                Registry re;
                try {
                    re = LocateRegistry.getRegistry(9111);
                    ServerRMIInf rm = (ServerRMIInf) re.lookup("ShowDept");
                    ArrayList<String> res = new ArrayList<String>();
                    res = rm.ShowDept("depts");
                    if (res != null) {
                        String[] rest = new String[res.size()];
                        DefaultListModel listModel = new DefaultListModel();
                        for (int i = 0; i < res.size(); i++) {
                            listModel.addElement(res.get(i));

                        }
                        DeptList.setModel(listModel);
                    } else {
                        JOptionPane.showMessageDialog(this, "No departments found.");
                    }
                } catch (RemoteException | NotBoundException ex) {
                    ex.printStackTrace();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

//Adding Dept
            if (e.getSource() == AddDept) {
                if (AddD.isSelected() && Dept.isSelected()) {
                    if (AddTf2.getText().length() != 0) {
                        String Stat;
                        Registry re;
                        try {
                            re = LocateRegistry.getRegistry(9111);
                            ServerRMIInf rm = (ServerRMIInf) re.lookup("AddDept");
                            Stat = rm.AddDept(AddTf2.getText().trim());
                            if (Stat.equals("Successful")) {
                                JOptionPane.showMessageDialog(this, "Department successfully added.");
                                AddTf2.setText("");
                            } else if (Stat.equals("Unsuccessful")) {
                                JOptionPane.showMessageDialog(this, "Error Occured.");
                            }
                            ShowDept.doClick();

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(this, "Please check your connection.");
                        }

                    } else {
                        JOptionPane.showMessageDialog(this, "Enter name of department to add");
                    }
                }

//Removing Dept
                if (RemD.isSelected() && Dept.isSelected()) {
                    if (DeptList.getSelectedValue() != null) {
                        String Stat;
                        Registry re;
                        try {
                            re = LocateRegistry.getRegistry(9111);
                            ServerRMIInf rm = (ServerRMIInf) re.lookup("RemoveDept");
                            Stat = rm.RemoveDept(DeptList.getSelectedValue().toString().trim());

                            JOptionPane.showMessageDialog(this, Stat);
                            ShowDept.doClick();

                        } catch (RemoteException | NotBoundException ex) {
                            ex.printStackTrace();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Please select department to remove.");
                    }
                }

//Editing Dept
                if (EditD.isSelected() && Dept.isSelected()) {
                    if (DeptList.getSelectedValue() != null) {
                        if (AddTf2.getText().length() != 0) {
                            String trgt = DeptList.getSelectedValue().toString().trim();
                            String Stat;
                            Registry re;
                            try {
                                re = LocateRegistry.getRegistry(9111);
                                ServerRMIInf rm = (ServerRMIInf) re.lookup("EditDept");
                                Stat = rm.EditDept(trgt, AddTf2.getText().trim());
                                JOptionPane.showMessageDialog(this, Stat);
                                ShowDept.doClick();
                                AddTf2.setText("");
                            } catch (RemoteException | NotBoundException ex) {
                                ex.printStackTrace();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Please enter new value for department");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Please select Department to edit.");

                    }
                }
            }
//Showing Class
            if (e.getSource() == ShwCls) {
                Registry re;
                try {
                    re = LocateRegistry.getRegistry(9111);
                    ServerRMIInf rm = (ServerRMIInf) re.lookup("ShowClasses");
                    ArrayList<String> res = new ArrayList<String>();
                    res = rm.ShowClass("ShwClass");
                    String[] rest = new String[res.size()];
                    if (!res.isEmpty()) {
                        DefaultListModel listModel = new DefaultListModel();
                        for (int i = 0; i < res.size(); i++) {
                            listModel.addElement(res.get(i));

                        }
                        LCls.setModel(listModel);

                    } else {
                        JOptionPane.showMessageDialog(this, "No classes found.");
                    }
                } catch (RemoteException | NotBoundException ex) {
                    ex.printStackTrace();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

//Showing Courses
            if (e.getSource() == ShwCourse && Class.isSelected()) {
                if (LCls.getSelectedValue() != null) {

                    Registry re;
                    try {
                        re = LocateRegistry.getRegistry(9111);
                        ServerRMIInf rm = (ServerRMIInf) re.lookup("ShowCourses");
                        ArrayList<String> res = new ArrayList<String>();
                        res = rm.ShowCourses(LCls.getSelectedValue().toString().trim(), "no");
                        String[] rest = new String[res.size()];

                        DefaultListModel listModel = new DefaultListModel();
                        for (int i = 0; i < res.size(); i++) {
                            listModel.addElement(res.get(i));
                        }
                        LCourse.setModel(listModel);
                        if (res.isEmpty()) {
                            JOptionPane.showMessageDialog(this, "No courses.");
                        }
                    } catch (RemoteException | NotBoundException ex) {
                        ex.printStackTrace();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Select class first.");
                }
            }

//Adding Class
            if (e.getSource() == AddDept) {
                if (Class.isSelected() && SelEdit.getSelectedItem().toString().equals("Class") && AddD.isSelected()) {
                    if (AddTf2.getText().length() != 0) {
                        String res;
                        Registry re;
                        try {
                            re = LocateRegistry.getRegistry(9111);
                            ServerRMIInf rm = (ServerRMIInf) re.lookup("AddClass");

                            res = rm.AddClass(AddTf2.getText().trim());
                            ShwCls.doClick();
                            JOptionPane.showMessageDialog(this, res);
                            AddTf2.setText("");
                        } catch (RemoteException | NotBoundException ex) {
                            ex.printStackTrace();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Enter name of class to add.");
                    }
                }

//Editing Class            
                if (Class.isSelected() && SelEdit.getSelectedItem().toString().equals("Class") && EditD.isSelected()) {

                    if (LCls.getSelectedValue().toString() != null) {
                        if (AddTf2.getText().length() != 0) {
                            String res;
                            Registry re;
                            try {
                                re = LocateRegistry.getRegistry(9111);
                                ServerRMIInf rm = (ServerRMIInf) re.lookup("EditClass");

                                res = rm.EditClass(LCls.getSelectedValue().toString().trim(), AddTf2.getText().trim());
                                ShwCls.doClick();
                                AddTf2.setText("");
                                JOptionPane.showMessageDialog(this, res);

                            } catch (RemoteException | NotBoundException ex) {
                                ex.printStackTrace();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Please enter new name for class.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Please select Class to edit.");
                    }
                }

//Removing Class
                if (Class.isSelected() && SelEdit.getSelectedItem().toString().equals("Class") && RemD.isSelected()) {
                    if (LCls.getSelectedValue().toString() != null) {
                        String res;
                        Registry re;
                        try {
                            re = LocateRegistry.getRegistry(9111);
                            ServerRMIInf rm = (ServerRMIInf) re.lookup("RemoveClass");
                            res = rm.RemoveClass(LCls.getSelectedValue().toString().trim());
                            ShwCls.doClick();
                            JOptionPane.showMessageDialog(this, "Class Removed Successfully.");

                        } catch (RemoteException | NotBoundException ex) {
                            ex.printStackTrace();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Please select class to remove.");
                    }
                }

//Adding Courses   
                if (SelEdit.getSelectedItem().toString() != null && SelEdit.getSelectedItem().toString().equals("Course Combo") && AddD.isSelected()) {
                    int errcount = 0;
                    if (LCls.getModel().getSize() != 0) {
                        if (LCls.getSelectedValue().toString() != null) {
                            String Comb = null, Cls;
                            if (CourseCount.getSelectedItem().toString().equals("1")) {
                                if (AddTf2.getText().length() != 0) {
                                    Comb = AddTf2.getText();
                                } else {
                                    JOptionPane.showMessageDialog(this, "Enter value for course");
                                    errcount = 1;
                                }
                            }
                            if (CourseCount.getSelectedItem().toString().equals("2")) {
                                if (AddTf2.getText().length() != 0 && AddDTf1.getText().length() != 0) {
                                    Comb = AddDTf1.getText() + "-" + AddTf2.getText();
                                } else {
                                    JOptionPane.showMessageDialog(this, "Enter values for course combination");
                                    errcount = 1;
                                }
                            }
                            if (CourseCount.getSelectedItem().toString().equals("3")) {
                                if (AddTf2.getText().length() != 0 && AddDTf1.getText().length() != 0 && AddTf3.getText().length() != 0) {
                                    Comb = AddDTf1.getText() + "-" + AddTf2.getText() + "-" + AddTf3.getText();
                                } else {
                                    JOptionPane.showMessageDialog(this, "Enter values for course combination");
                                    errcount = 1;
                                }
                            }
                            if (CourseCount.getSelectedItem().toString().equals("4")) {
                                if (AddTf2.getText().length() != 0 && AddDTf1.getText().length() != 0 && AddTf3.getText().length() != 0 && AddTf4.getText().length() != 0) {
                                    Comb = AddDTf1.getText() + "-" + AddTf2.getText() + "-" + AddTf3.getText() + "-" + AddTf4.getText();
                                } else {
                                    JOptionPane.showMessageDialog(this, "Enter values for course combination.");
                                    errcount = 1;
                                }
                            }
                            if (errcount != 1) {
                                Registry re;
                                try {
                                    re = LocateRegistry.getRegistry(9111);
                                    ServerRMIInf rm = (ServerRMIInf) re.lookup("AddCourse");

                                    String res = rm.AddCourse(LCls.getSelectedValue().toString(), Comb.trim());
                                    JOptionPane.showMessageDialog(this, res);
                                    ShwCourse.doClick();
                                    AddTf2.setText("");
                                    AddDTf1.setText("");
                                    AddTf3.setText("");
                                    AddTf4.setText("");
                                } catch (RemoteException | NotBoundException ex) {
                                    ex.printStackTrace();
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }

                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Please select Class in which the course to be added.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Please select Class in which the course to be added.");
                    }
                }

//Removing COurse
                if (Class.isSelected() && SelEdit.getSelectedItem().toString().equals("Course Combo") && RemD.isSelected()) {
                    String res;
                    Registry re;
                    if (LCls.getModel().getSize() != 0) {
                        if (LCourse.getSelectedValue() != null) {
                            try {
                                re = LocateRegistry.getRegistry(9111);
                                ServerRMIInf rm = (ServerRMIInf) re.lookup("RemoveCourse");
                                res = rm.RemoveCourse(LCls.getSelectedValue().toString().trim(), LCourse.getSelectedValue().toString().trim());
                                ShwCourse.doClick();
                                JOptionPane.showMessageDialog(this, res);

                            } catch (RemoteException | NotBoundException ex) {
                                ex.printStackTrace();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                        } else {
                            JOptionPane.showMessageDialog(this, "Please select course combination which is to be removed");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "list courses by selecting class and clicking show course button.");
                    }
                }
            }

            if (Dept.isSelected()) {
                ShwCls.setVisible(false);
                LCls.setVisible(false);
                ShwCourse.setVisible(false);
                LCourse.setVisible(false);
                ShowDept.setVisible(true);
                DeptList.setVisible(true);
                SelectToEdt.setVisible(false);
                SelEdit.setVisible(false);

            }

            if (Class.isSelected()) {
                ShowDept.setVisible(false);
                DeptList.setVisible(false);
                ShwCls.setVisible(true);
                LCls.setVisible(true);
                ShwCourse.setVisible(true);
                LCourse.setVisible(true);
                SelectToEdt.setVisible(true);
                SelEdit.setVisible(true);

            }

            if ((AddD.isSelected() || EditD.isSelected()) && Dept.isSelected()) {
                AddTf2.setEnabled(true);
                if (AddD.isSelected()) {
                    AddDept.setText("Add");
                } else if (EditD.isSelected()) {
                    AddDept.setText("Edit");
                }
            }
            if (RemD.isSelected() && Dept.isSelected()) {
                AddTf2.setEnabled(false);
                AddDept.setText("Remove");

            }
            if ((AddD.isSelected() || EditD.isSelected()) && Class.isSelected()) {
                AddTf2.setEnabled(true);
                AddDTf1.setEnabled(true);
                AddTf3.setEnabled(true);
                AddTf4.setEnabled(true);
                if (AddD.isSelected()) {
                    AddDept.setText("Add");
                } else if (EditD.isSelected()) {
                    AddDept.setText("Save");
                }
            } else if (RemD.isSelected() && Class.isSelected()) {
                AddTf2.setEnabled(false);
                AddDTf1.setEnabled(false);
                AddTf3.setEnabled(false);
                AddTf4.setEnabled(false);
                AddDept.setText("Remove");
            }

            if (SelEdit.getSelectedItem().toString().equals("Course Combo") && (AddD.isSelected())) {
                CourseCount.setVisible(true);

                if (CourseCount.getSelectedItem().toString().equals("1")) {

                    AddTf2.setVisible(true);
                    AddDTf1.setVisible(false);
                    AddTf3.setVisible(false);
                    AddTf4.setVisible(false);

                } else if (CourseCount.getSelectedItem().toString().equals("2")) {

                    AddDTf1.setVisible(true);
                    AddTf2.setVisible(true);
                    AddTf3.setVisible(false);
                    AddTf4.setVisible(false);
                } else if (CourseCount.getSelectedItem().toString().equals("3")) {
                    AddDTf1.setVisible(true);
                    AddTf2.setVisible(true);
                    AddTf3.setVisible(true);
                    AddTf4.setVisible(false);
                } else if (CourseCount.getSelectedItem().toString().equals("4")) {
                    AddDTf1.setVisible(true);
                    AddTf2.setVisible(true);
                    AddTf3.setVisible(true);
                    AddTf4.setVisible(true);
                } else {
                    CourseCount.setVisible(false);
                    AddDTf1.setVisible(false);
                    AddTf3.setVisible(false);
                    AddTf4.setVisible(false);

                }
            } else {
                CourseCount.setVisible(false);
            }

        }

        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {

        }

        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

        }

        @Override
        public void popupMenuCanceled(PopupMenuEvent e) {

        }

        @Override
        public void itemStateChanged(ItemEvent e) {
            if (SelEdit.getSelectedItem().toString().equals("Course Combo")) {
                EditD.setVisible(false);
            } else {
                EditD.setVisible(true);
            }
        }
    }
///EDIT MEMBERS

    class EditMem extends JPanel implements ActionListener, PopupMenuListener {

        JLabel MHead, SelToEdt, StfEdt, ScSel, EnId, NewVal, SelClass;
        JTextField EnIdTf, NewValTf;
        JRadioButton Teac, Stf, stu, EditStf, ChgStf;
        JComboBox ScTea, ScStu, ScStf, SCClass;
        ButtonGroup SelEdt, SelStf;
        JComboBox ScIdRoll;
        JButton Submit;
        String[] Tr = {"First Name", "Last Name", "Email", "Mobile"};
        String[] Su = {"First Name", "Last Name", "Roll no", "Email"};
        String[] Sf = {"First Name", "Last Name", "Email", "Mobile"};
        String[] TIdRl = {"User Id", "Teacher Id"};
        String[] StuIdRl = {"User Id", "Roll no"};
        String[] StfIdRl = {"User Id", "Staff Id"};

        public EditMem(String Pic) {

            setLayout(null);

            SelClass = new JLabel("Select Class:");
            SCClass = new JComboBox();

            SelClass.setFont(lab);
            SCClass.setFont(lab);
            add(SelClass);
            add(SCClass);
            SCClass.addPopupMenuListener(this);

            //Comb
            ScTea = new JComboBox(Tr);

            ScIdRoll = new JComboBox(TIdRl);
            //head
            MHead = new JLabel("Edit Members");
            MHead.setFont(new Font("Old English Text MT", Font.BOLD, 35));

            //select
            SelToEdt = new JLabel("Select to Edit:");
            StfEdt = new JLabel("Operation:");

            Teac = new JRadioButton("Teacher");
            Stf = new JRadioButton("Staff");
            stu = new JRadioButton("Student");

            SelEdt = new ButtonGroup();
            SelEdt.add(Teac);
            SelEdt.add(stu);
            SelEdt.add(Stf);

            //stf
            EditStf = new JRadioButton("Edit Staff");
            ChgStf = new JRadioButton("Change Staff");
            SelStf = new ButtonGroup();
            SelStf.add(EditStf);
            SelStf.add(ChgStf);

            //
            ScSel = new JLabel("Value of:");
            NewVal = new JLabel("New Value:");
            NewValTf = new JTextField();
            NewValTf.setDocument(new JTextFieldLimit(25));

            EnId = new JLabel("Enter Id:");
            Submit = new JButton("Save");
            EnIdTf = new JTextField();
            EnIdTf.setDocument(new JTextFieldLimit(18));
            //fonts
            SelToEdt.setFont(tit);
            StfEdt.setFont(tit);
            Teac.setFont(lab);
            Stf.setFont(lab);
            stu.setFont(lab);
            EditStf.setFont(lab);
            ChgStf.setFont(lab);
            ScSel.setFont(lab);
            ScTea.setFont(lab);
            NewVal.setFont(lab);
            ScIdRoll.setFont(lab);
            EnId.setFont(lab);
            Submit.setFont(lab);

            //Bounds
            MHead.setBounds((int) (wid / 4), hei / 8, 400, 30);
            SelToEdt.setBounds(wid / 10, hei / 4, 200, 40);
            Teac.setBounds(wid / 4, hei / 4, 100, 40);
            Stf.setBounds(wid / 3, hei / 4, 100, 40);
            stu.setBounds((int) (wid / 2.4), hei / 4, 100, 40);

            StfEdt.setBounds((int) (wid / 10), (int) (hei / 3), 200, 30);
            EditStf.setBounds((int) (wid / 4), (int) (hei / 3), 200, 30);
            ChgStf.setBounds((int) (wid / 2.5), (int) (hei / 3), 200, 30);

            SelClass.setBounds((int) (wid / 8), (int) (hei / 2.8), 200, 30);
            SCClass.setBounds((int) (wid / 4), (int) (hei / 2.8), 150, 30);

            EnId.setBounds((int) (wid / 8), (int) (hei / 2.3), 200, 30);
            EnIdTf.setBounds((int) (wid / 4), (int) (hei / 2.3), 150, 30);
            ScIdRoll.setBounds((int) (wid / 2.6), (int) (hei / 2.3), 150, 30);
            ScSel.setBounds((int) (wid / 8), (int) (hei / 1.9), 150, 30);
            ScTea.setBounds((int) (wid / 4), (int) (hei / 1.9), 150, 30);
            NewVal.setBounds((int) (wid / 8), (int) (hei / 1.7), 150, 30);
            NewValTf.setBounds((int) (wid / 4), (int) (hei / 1.7), 150, 30);
            Submit.setBounds((int) (wid / 5), (int) (hei / 1.4), 180, 40);
            //value

            //add
            add(MHead);
            add(SelToEdt);
            add(Teac);
            add(Stf);
            add(stu);
            add(StfEdt);
            add(EditStf);
            add(ChgStf);
            add(ScSel);
            add(ScTea);
            add(ScIdRoll);
            add(EnId);
            add(EnIdTf);
            add(NewValTf);
            add(NewVal);
            add(Submit);

            //Listeners
            Teac.addActionListener(this);
            Stf.addActionListener(this);
            stu.addActionListener(this);
            EditStf.addActionListener(this);
            ChgStf.addActionListener(this);

            ScIdRoll.addPopupMenuListener(this);
            ScTea.addPopupMenuListener(this);

            Teac.setSelected(true);
            EditStf.setVisible(false);
            ChgStf.setVisible(false);
            StfEdt.setVisible(false);
            SelClass.setVisible(false);
            SCClass.setVisible(false);
            Submit.addActionListener(this);

            try {
                image = ImageIO.read(new File(Pic));
                w = image.getWidth();
                h = image.getHeight();

            } catch (IOException ioe) {
                System.out.println("Could not read in the pic");
                //System.exit(0);
            }
            addComponentListener(new ComponentAdapter() {
                public void componentResized(ComponentEvent e) {

                    MHead.setLocation((int) (wid / 4), (int) (hei / 8));

                }
            });

        }

        public Dimension getPreferredSize() {
            return new Dimension(w * 2, h * 2);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (ScTea.getSelectedItem().toString().trim().equals("First Name")) {
                EnIdTf.setDocument(new JTextFieldLimit(25));
            }
            if (ScTea.getSelectedItem().toString().trim().equals("Last Name")) {
                EnIdTf.setDocument(new JTextFieldLimit(25));
            }
            if (ScTea.getSelectedItem().toString().trim().equals("Mobile")) {
                EnIdTf.setDocument(new JTextFieldLimit(10));
            }
            if (ScTea.getSelectedItem().toString().trim().equals("Email")) {
                EnIdTf.setDocument(new JTextFieldLimit(30));
            }
            if (ScTea.getSelectedItem().toString().trim().equals("Roll no")) {
                EnIdTf.setDocument(new JTextFieldLimit(4));
            }

            if (Teac.isSelected() || stu.isSelected()) {
                EditStf.setVisible(false);
                ChgStf.setVisible(false);
                StfEdt.setVisible(false);
                SelClass.setVisible(false);
                SCClass.setVisible(false);

                if (Teac.isSelected()) {

                    NewValTf.setDocument(new JTextFieldLimit(30));
                    if (e.getSource() == Submit) {
                        int errcount = 0;
                        String IDTyp = ScIdRoll.getSelectedItem().toString().trim();

                        String Val = ScTea.getSelectedItem().toString().trim();
                        String iD = EnIdTf.getText().trim();
                        String newVal = NewValTf.getText().trim();
                        System.out.println(Val.equals("First Name"));
                        if (Val.equals("First Name") || Val.equals("Last Name")) {
                            if (newVal.matches(".*\\d+.*") || newVal.matches(".*\\d+.*")) {
                                JOptionPane.showMessageDialog(this, "Invalid name.");
                                errcount = 1;
                            }
                        } else if (Val.equals("Email")) {

                            if (errcount != 1 && !emailValidator.validate(newVal.trim())) {
                                JOptionPane.showMessageDialog(this, "Invalid Email.");
                                errcount = 1;
                            }
                        } else if (Val.equals("Mobile")) {
                            long mobno;
                            if (errcount != 1) {
                                try {
                                    mobno = Long.parseLong(newVal);
                                } catch (Exception ex) {
                                    JOptionPane.showMessageDialog(this, "Invalid mobile number.");
                                    errcount = 1;
                                }
                            }
                        }

                        if (errcount != 1 && iD.isEmpty()) {
                            JOptionPane.showMessageDialog(this, "Please enter your user id.");
                            errcount = 1;
                        }
                        if (errcount != 1 && newVal.isEmpty()) {
                            JOptionPane.showMessageDialog(this, "Please enter new value.");
                            errcount = 1;
                        }

                        if (errcount != 1) {
                            String res;
                            Registry re;
                            try {
                                re = LocateRegistry.getRegistry(9111);
                                ServerRMIInf rm = (ServerRMIInf) re.lookup("EditMem");
                                res = rm.EditMember("a", IDTyp, iD, Val, newVal, "none");

                                JOptionPane.showMessageDialog(this, res);
                                EnIdTf.setText("");
                                NewValTf.setText("");
                                ScIdRoll.setSelectedIndex(0);
                                ScTea.setSelectedIndex(0);

                            } catch (RemoteException | NotBoundException ex) {
                                ex.printStackTrace();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                        }
                    }

                }
                if (stu.isSelected()) {
                    SelClass.setVisible(true);
                    SCClass.setVisible(true);

                    if (e.getSource() == Submit) {
                        int errcount = 0;
                        String IDTyp = ScIdRoll.getSelectedItem().toString().trim();
                        String cls = null;
                        String newVal = NewValTf.getText().trim();
                        if (SCClass.getSelectedItem() != null) {
                            cls = SCClass.getSelectedItem().toString();
                        } else {
                            JOptionPane.showMessageDialog(this, "please select class.");
                            errcount = 1;
                        }

                        if (errcount != 1 && IDTyp.equals("Roll no")) {
                            try {
                                int num = Integer.parseInt(IDTyp);
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(this, "invalid roll number.");
                                errcount = 1;
                            }
                        }
                        String Val = ScTea.getSelectedItem().toString().trim();

                        if (errcount != 1 && (Val.equals("First Name") || Val.equals("Last Name"))) {
                            if (newVal.matches(".*\\d+.*") || newVal.matches(".*\\d+.*")) {
                                JOptionPane.showMessageDialog(this, "Invalid name.");
                                errcount = 1;
                            }
                        }
                        if (Val.equals("Email")) {

                            if (errcount != 1 && !emailValidator.validate(newVal.trim())) {
                                JOptionPane.showMessageDialog(this, "Invalid Email.");
                                errcount = 1;
                            }
                        }
                        if (Val.equals("Mobile")) {
                            long mobno;
                            if (errcount != 1) {
                                try {
                                    mobno = Long.parseLong(newVal);
                                } catch (Exception ex) {
                                    JOptionPane.showMessageDialog(this, "Invalid mobile number.");
                                    errcount = 1;
                                }
                            }

                        }
                        String iD = EnIdTf.getText().trim();
                        if (errcount != 1 && iD.isEmpty()) {
                            JOptionPane.showMessageDialog(this, "Please enter user id/roll no.");
                            errcount = 1;

                        }

                        if (errcount != 1 && newVal.isEmpty()) {
                            JOptionPane.showMessageDialog(this, "Please enter new value.");
                            errcount = 1;

                        }
                        if (errcount != 1) {
                            String res;
                            Registry re;
                            try {
                                re = LocateRegistry.getRegistry(9111);
                                ServerRMIInf rm = (ServerRMIInf) re.lookup("EditMem");
                                res = rm.EditMember("b", IDTyp, iD, Val, newVal, cls);

                                JOptionPane.showMessageDialog(this, res);
                                EnIdTf.setText("");
                                NewValTf.setText("");
                                ScIdRoll.setSelectedIndex(0);
                                ScTea.setSelectedIndex(0);
                            } catch (RemoteException | NotBoundException ex) {
                                ex.printStackTrace();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            }

            if (Stf.isSelected()) {

                SelClass.setVisible(false);
                SCClass.setVisible(false);

                EditStf.setVisible(true);
                ChgStf.setVisible(true);
                StfEdt.setVisible(true);
                if (e.getSource() == Submit) {
                    if (!EditStf.isSelected() && !ChgStf.isSelected()) {
                        {
                            JOptionPane.showMessageDialog(this, "Select type of operation first.");
                        }
                    }
                }
                if (EditStf.isSelected()) {

                    if (e.getSource() == Submit) {
                        int errcount = 0;

                        String IDTyp = ScIdRoll.getSelectedItem().toString().trim();
                        String Val = ScTea.getSelectedItem().toString().trim();
                        System.out.println(IDTyp);
                        String iD = EnIdTf.getText().trim();
                        String newVal = NewValTf.getText().trim();
                        if (errcount != 1 && (Val.equals("First Name") || Val.equals("Last Name"))) {
                            if (newVal.matches(".*\\d+.*") || newVal.matches(".*\\d+.*")) {
                                JOptionPane.showMessageDialog(this, "Invalid name.");
                                errcount = 1;
                            }
                        } else if (Val.equals("Email")) {

                            if (errcount != 1 && !emailValidator.validate(newVal.trim())) {
                                JOptionPane.showMessageDialog(this, "Invalid Email.");
                                errcount = 1;
                            }
                        } else if (Val.equals("Mobile")) {
                            long mobno;
                            if (errcount != 1) {
                                try {
                                    mobno = Long.parseLong(newVal);
                                } catch (Exception ex) {
                                    JOptionPane.showMessageDialog(this, "Invalid mobile number.");
                                    errcount = 1;
                                }
                            }
                        }
                        if (errcount != 1 && iD.isEmpty()) {
                            JOptionPane.showMessageDialog(this, "Please enter your user id.");
                            errcount = 1;
                        }
                        if (errcount != 1 && newVal.isEmpty()) {
                            JOptionPane.showMessageDialog(this, "Please enter new value.");
                            errcount = 1;
                        }

                        String res;

                        Registry re;
                        if (errcount != 1 && iD.isEmpty()) {
                            JOptionPane.showMessageDialog(this, "Please enter user id/roll no.");
                            errcount = 1;

                        }

                        if (errcount != 1 && newVal.isEmpty()) {
                            JOptionPane.showMessageDialog(this, "Please enter new value.");
                            errcount = 1;

                        }
                        if (errcount != 1) {
                            try {
                                re = LocateRegistry.getRegistry(9111);
                                ServerRMIInf rm = (ServerRMIInf) re.lookup("EditMem");
                                res = rm.EditMember("c", IDTyp, iD, Val, newVal, "none");

                                JOptionPane.showMessageDialog(this, res);
                                EnIdTf.setText("");
                                NewValTf.setText("");
                                ScIdRoll.setSelectedIndex(0);
                                ScTea.setSelectedIndex(0);
                            } catch (RemoteException | NotBoundException ex) {
                                ex.printStackTrace();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
                if (ChgStf.isSelected()) {
                    JOptionPane.showMessageDialog(this, "Adding new Staff will automatically replace Staff details");
                    EditStf.setSelected(true);
                }
            }

        }

        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            SCClass.removeAllItems();
            if (stu.isSelected()) {
                EnId.setText("Enter Id/Roll no:");
                if (e.getSource() == ScIdRoll) {

                    DefaultComboBoxModel dcm1 = new DefaultComboBoxModel(StuIdRl);
                    ScIdRoll.setModel(dcm1);

                }
                if (e.getSource() == ScTea) {
                    DefaultComboBoxModel dcm = new DefaultComboBoxModel(Su);
                    ScTea.setModel(dcm);

                }
            }
            if (Stf.isSelected()) {
                if (e.getSource() == ScIdRoll) {

                    DefaultComboBoxModel dcm1 = new DefaultComboBoxModel(StfIdRl);
                    ScIdRoll.setModel(dcm1);

                }

                if (e.getSource() == ScTea) {

                    DefaultComboBoxModel dcm = new DefaultComboBoxModel(Sf);
                    ScTea.setModel(dcm);
                }
            }
            if (Teac.isSelected()) {
                EnId.setText("Enter Id:");
                if (e.getSource() == ScIdRoll) {
                    DefaultComboBoxModel dcm1 = new DefaultComboBoxModel(TIdRl);
                    ScIdRoll.setModel(dcm1);
                }
                if (e.getSource() == ScTea) {

                    DefaultComboBoxModel dcm = new DefaultComboBoxModel(Tr);
                    ScTea.setModel(dcm);
                }

            }
            if (e.getSource() == SCClass) {
                Registry re;
                try {
                    re = LocateRegistry.getRegistry(9111);
                    ServerRMIInf rm = (ServerRMIInf) re.lookup("ShowClasses");
                    ArrayList<String> res = new ArrayList<String>();
                    res = rm.ShowClass("ShwClass");
                    String[] rest = new String[res.size()];

                    for (int i = 0; i < res.size(); i++) {
                        SCClass.addItem(res.get(i));
                    }

                } catch (RemoteException | NotBoundException ex) {
                    ex.printStackTrace();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        }

        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

        }

        @Override
        public void popupMenuCanceled(PopupMenuEvent e) {

        }
    }
//REMOVE MEMBER

    class RemMem extends JPanel implements ActionListener, PopupMenuListener {

        JLabel MaHead, SelClass, SelToRem, Vals, Start, End, Id;
        JTextField IdTf, FrmTf, ToTf;
        JComboBox SelOpt, SCRClass;
        JButton Sub;

        JRadioButton Te, Stn, Sing, Mul;
        ButtonGroup Testn, Val;
        String[] t = {"User Id", "Teacher Id"};
        String[] s = {"User Id", "Roll no"};

        public RemMem(String Pic) {
            setLayout(null);

            SelClass = new JLabel("Select Class:");
            SCRClass = new JComboBox();

            SelClass.setFont(lab);
            SCRClass.setFont(lab);
            add(SelClass);
            add(SCRClass);

            MaHead = new JLabel("Remove Members");
            MaHead.setFont(new Font("Old English Text MT", Font.BOLD, 35));

            SelToRem = new JLabel("Select to Remove:");
            Vals = new JLabel("Deletion type:");
            Start = new JLabel("From (Roll no):");

            FrmTf = new JTextField();
            ToTf = new JTextField();
            End = new JLabel("To (Roll no):");
            Id = new JLabel("Id/Roll no:");
            IdTf = new JTextField();
            Sub = new JButton("Submit");
            SelOpt = new JComboBox();
            Testn = new ButtonGroup();
            Val = new ButtonGroup();
            Te = new JRadioButton("Teacher");
            Stn = new JRadioButton("Student");
            Testn.add(Te);
            Testn.add(Stn);
            Sing = new JRadioButton("Single");
            Mul = new JRadioButton("Multiple");
            Val.add(Sing);
            Val.add(Mul);

            SelToRem.setFont(tit);
            Vals.setFont(tit);
            Start.setFont(lab);
            Te.setFont(lab);
            Stn.setFont(lab);
            Sing.setFont(lab);
            Mul.setFont(lab);
            End.setFont(lab);
            Sub.setFont(lab);
            Id.setFont(lab);

            add(Te);
            add(Stn);
            add(Sing);
            add(Mul);
            SelOpt.setFont(lab);
            add(MaHead);
            add(SelToRem);
            add(Vals);
            add(Start);
            add(FrmTf);
            add(ToTf);
            add(End);
            add(Id);
            add(IdTf);
            add(Sub);
            add(SelOpt);

            Te.addActionListener(this);
            Stn.addActionListener(this);
            Sing.addActionListener(this);
            Mul.addActionListener(this);
            Sub.addActionListener(this);
            SelOpt.addPopupMenuListener(this);
            SCRClass.addPopupMenuListener(this);

            Te.setSelected(true);
            Sing.setSelected(true);
            Start.setVisible(false);
            End.setVisible(false);
            FrmTf.setVisible(false);
            ToTf.setVisible(false);
            SelClass.setVisible(false);
            SCRClass.setVisible(false);

            DefaultComboBoxModel dcm = new DefaultComboBoxModel(t);
            SelOpt.setModel(dcm);

            MaHead.setBounds((int) (wid / 4), hei / 8, 400, 30);
            SelToRem.setBounds(wid / 10, hei / 4, 200, 40);
            Te.setBounds(wid / 4, hei / 4, 100, 40);
            Stn.setBounds(wid / 3, hei / 4, 100, 40);
            Vals.setBounds((int) (wid / 10), (int) (hei / 3), 200, 30);
            Sing.setBounds((int) (wid / 4), (int) (hei / 3), 100, 30);
            Mul.setBounds((int) (wid / 3), (int) (hei / 3), 100, 30);

            SelClass.setBounds((int) (wid / 8), (int) (hei / 2.4), 200, 30);
            SCRClass.setBounds((int) (wid / 4), (int) (hei / 2.4), 150, 30);

            Start.setBounds((int) (wid / 10), (int) (hei / 2), 200, 30);
            End.setBounds((int) (wid / 3), (int) (hei / 2), 200, 30);
            FrmTf.setBounds((int) (wid / 4.8), (int) (hei / 2), 100, 30);
            ToTf.setBounds((int) (wid / 2.3), (int) (hei / 2), 100, 30);

            Id.setBounds((int) (wid / 8), (int) (hei / 2), 100, 30);
            IdTf.setBounds((int) (wid / 5), (int) (hei / 2), 150, 30);
            SelOpt.setBounds((int) (wid / 3), (int) (hei / 2), 150, 30);

            Sub.setBounds((int) (wid / 5), (int) (hei / 1.5), 180, 40);

            try {
                image = ImageIO.read(new File(Pic));
                w = image.getWidth();
                h = image.getHeight();

            } catch (IOException ioe) {
                System.out.println("Could not read in the pic");
                //System.exit(0);
            }
            addComponentListener(new ComponentAdapter() {
                public void componentResized(ComponentEvent e) {

                }
            });

        }

        public Dimension getPreferredSize() {
            return new Dimension(w * 2, h * 2);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (Te.isSelected() || Stn.isSelected()) {
                Start.setVisible(false);
                End.setVisible(false);
                FrmTf.setVisible(false);
                ToTf.setVisible(false);

                Id.setVisible(false);
                IdTf.setVisible(false);
                SelOpt.setVisible(false);
                SelClass.setVisible(false);
                SCRClass.setVisible(false);

                if (Te.isSelected()) {
                    if (Sing.isSelected()) {
                        SelOpt.setVisible(true);
                        Id.setVisible(true);
                        IdTf.setVisible(true);

                        if (e.getSource() == Sub) {
                            int errcount = 0;
                            String ID, IDTYP;
                            ID = IdTf.getText().trim();
                            IDTYP = SelOpt.getSelectedItem().toString().trim();
                            if (ID.isEmpty()) {
                                JOptionPane.showMessageDialog(this, "Please enter user Id/teacher Id");
                                errcount = 1;
                            }

                            if (errcount != 1) {
                                String res;
                                Registry re;
                                try {
                                    re = LocateRegistry.getRegistry(9111);
                                    ServerRMIInf rm = (ServerRMIInf) re.lookup("RemoveMem");
                                    res = rm.RemoveMember("a", null, IDTYP, ID);

                                    JOptionPane.showMessageDialog(this, res);
                                    IdTf.setText("");
                                } catch (RemoteException | NotBoundException ex) {
                                    ex.printStackTrace();
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }

                        }
                    }
                    if (Mul.isSelected()) {
                        JOptionPane.showMessageDialog(this, "Only one teacher could be removed.");
                        Sing.setSelected(true);
                        Id.setVisible(true);
                        IdTf.setVisible(true);
                        SelOpt.setVisible(true);

                    }
                }
                if (Stn.isSelected()) {
                    SelClass.setVisible(true);
                    SCRClass.setVisible(true);
                    if (Sing.isSelected()) {
                        Id.setVisible(true);
                        IdTf.setVisible(true);
                        SelOpt.setVisible(true);

                        if (e.getSource() == Sub) {
                            int errcount = 0;
                            String ID, IDTYP = SelOpt.getSelectedItem().toString().trim();
                            ID = IdTf.getText().trim();

                            String cls = "";
                            if (SCRClass.getSelectedItem() != null) {
                                cls = SCRClass.getSelectedItem().toString().trim();
                            } else {
                                JOptionPane.showMessageDialog(this, "please select class.");
                                errcount = 1;
                            }
                            if (errcount != 1 && ID.isEmpty()) {

                                JOptionPane.showMessageDialog(this, "please enter roll no./user Id.");
                                errcount = 1;

                            }
                            int roll;
                            if (errcount != 1 && IDTYP.equals("Roll no")) {
                                try {
                                    roll = Integer.parseInt(ID);
                                } catch (Exception ex) {
                                    JOptionPane.showMessageDialog(this, "Invalid roll no.");
                                    errcount = 1;
                                }

                            }

                            String res;

                            if (errcount != 1) {
                                Registry re;
                                try {
                                    re = LocateRegistry.getRegistry(9111);
                                    ServerRMIInf rm = (ServerRMIInf) re.lookup("RemoveMem");
                                    res = rm.RemoveMember("b", cls, IDTYP, ID);

                                    JOptionPane.showMessageDialog(this, res);
                                    IdTf.setText("");
                                } catch (RemoteException | NotBoundException ex) {
                                    ex.printStackTrace();
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                    }
                    if (Mul.isSelected()) {
                        Start.setVisible(true);
                        End.setVisible(true);
                        FrmTf.setVisible(true);
                        ToTf.setVisible(true);
                        {
                            if (e.getSource() == Sub) {
                                int errcount = 0;

                                String FROM = FrmTf.getText().trim(), TO = ToTf.getText().trim(), CLASS = null, res;

                                if (FROM.isEmpty() || TO.isEmpty()) {
                                    JOptionPane.showMessageDialog(this, "all fields are required.");
                                    errcount = 1;
                                }
                                int frm, to;
                                if (errcount != 1) {
                                    try {
                                        frm = Integer.parseInt(FROM);
                                        to = Integer.parseInt(TO);

                                    } catch (Exception ex) {
                                        JOptionPane.showMessageDialog(this, "invalid roll no/s");
                                        errcount = 1;
                                    }
                                }

                                if (SCRClass.getSelectedItem() != null) {

                                    CLASS = SCRClass.getSelectedItem().toString().trim();

                                } else if (errcount != 1) {
                                    JOptionPane.showMessageDialog(this, "Please select class.");
                                }

                                if (errcount != 1) {
                                    Registry re;
                                    try {
                                        re = LocateRegistry.getRegistry(9111);

                                        ServerRMIInf rm = (ServerRMIInf) re.lookup("RemoveMem");
                                        res = rm.RemoveMember("bm", CLASS, FROM, TO);

                                        JOptionPane.showMessageDialog(this, res);
                                        IdTf.setText("");
                                        FrmTf.setText("");
                                        ToTf.setText("");

                                    } catch (RemoteException | NotBoundException ex) {
                                        ex.printStackTrace();
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            }

                        }

                    }
                }
            }
        }

        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {

            if (e.getSource() == SCRClass) {
                SCRClass.removeAllItems();
                Registry re;
                try {
                    re = LocateRegistry.getRegistry(9111);
                    ServerRMIInf rm = (ServerRMIInf) re.lookup("ShowClasses");
                    ArrayList<String> res = new ArrayList<String>();
                    res = rm.ShowClass("ShwClass");
                    String[] rest = new String[res.size()];

                    for (int i = 0; i < res.size(); i++) {
                        SCRClass.addItem(res.get(i));
                    }

                } catch (RemoteException | NotBoundException ex) {
                    ex.printStackTrace();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (Te.isSelected()) {
                DefaultComboBoxModel dcm = new DefaultComboBoxModel(t);
                SelOpt.setModel(dcm);
            }
            if (Stn.isSelected()) {
                DefaultComboBoxModel dcm = new DefaultComboBoxModel(s);
                SelOpt.setModel(dcm);
            }

        }

        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

        }

        @Override
        public void popupMenuCanceled(PopupMenuEvent e) {
            SCRClass.hidePopup();
        }

    }

    //MANAGE ATTENDANCE
    //TEACHER ATTENDANCE
    class ViewTeaAtt extends JPanel implements ActionListener {

        JLabel MHead, UsrId, FrmDte, ToDte, AtTyp, TotHrs, Percent, Dept, SrchTp, Enhrdt;
        JButton sub;

        String[] TeachTable = {"User Id", "First Name", "Last Name", "Department", "Total Hours", "Attendance %"};
        String[] tp = {"Above", "Below"};
        JComboBox Attyp;
        JRadioButton Hrs, Days;
        ButtonGroup hd;
        JTextField hrdys;
        DateFormat df;
        JDatePanelImpl datePanel, datePanel1;
        UtilDateModel model, model1;
        Properties p;
        JDatePickerImpl datepicker, datepicker1;
        JTable tbs;
        int i = 0;

        public ViewTeaAtt(String pico) {
            setLayout(null);
            MHead = new JLabel("Check Teacher Attendance");
            MHead.setFont(new Font("Old English Text MT", Font.BOLD, 35));
            add(MHead);
            MHead.setBounds((int) (wid / 6), hei / 8, 600, 30);

            model = new UtilDateModel();
            p = new Properties();
            p.put("text.today", "Today");
            p.put("text.month", "Month");
            p.put("text.year", "Year");
            datePanel = new JDatePanelImpl(model, p);
            datepicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

            add(datepicker);

            model1 = new UtilDateModel();
            datePanel1 = new JDatePanelImpl(model1, p);
            datepicker1 = new JDatePickerImpl(datePanel1, new DateLabelFormatter());

            add(datepicker1);

            FrmDte = new JLabel("From:");
            FrmDte.setFont(tit);

            ToDte = new JLabel("To:");
            ToDte.setFont(tit);

            AtTyp = new JLabel("Attendance Type:");
            AtTyp.setFont(tit);

            Attyp = new JComboBox(tp);
            Attyp.setFont(lab);

            Hrs = new JRadioButton("Hours");
            Days = new JRadioButton("Days(%)");
            hd = new ButtonGroup();
            hd.add(Hrs);
            hd.add(Days);

            SrchTp = new JLabel("Search Type:");
            SrchTp.setFont(tit);

            Hrs.setFont(lab);
            Days.setFont(lab);

            Enhrdt = new JLabel("Enter days(%)/Hours:");
            Enhrdt.setFont(lab);

            hrdys = new JTextField();

            sub = new JButton("Submit");
            sub.setFont(lab);

            // TotHrs = new JLabel("Hours");
            //Percent = new JLabel("Percentage:");
            // Dept = new JLabel("Department:");
            FrmDte.setBounds((int) (wid / 10), (int) (hei / 4), 150, 40);
            datepicker.setBounds((int) (wid / 6), (int) (hei / 4), 200, 30);
            ToDte.setBounds((int) (wid / 2.9), (int) (hei / 4), 150, 40);
            datepicker1.setBounds((int) (wid / 2.5), (int) (hei / 4), 200, 30);

            SrchTp.setBounds((int) (wid / 10), (int) (hei / 2.8), 200, 40);
            Hrs.setBounds((int) (wid / 4), (int) (hei / 2.8), 100, 40);
            Days.setBounds((int) (wid / 3), (int) (hei / 2.8), 120, 40);

            AtTyp.setBounds((int) (wid / 10), (int) (hei / 2.2), 200, 40);
            Attyp.setBounds((int) (wid / 4), (int) (hei / 2.2), 150, 30);

            Enhrdt.setBounds((int) (wid / 8), (int) (hei / 1.8), 250, 40);
            hrdys.setBounds((int) (wid / 3), (int) (hei / 1.78), 150, 30);
            sub.setBounds((int) (wid / 5), (int) (hei / 1.5), 150, 30);
            hrdys.setDocument(new JTextFieldLimit(5));
            add(FrmDte);
            add(ToDte);
            add(AtTyp);
            add(Attyp);

            add(Hrs);
            add(Days);
            add(SrchTp);
            add(Enhrdt);
            add(hrdys);
            add(sub);
            // add(Dept);

            sub.addActionListener(this);
            Days.addActionListener(this);
            try {
                image = ImageIO.read(new File(pico));
                w = image.getWidth();
                h = image.getHeight();

            } catch (IOException ioe) {
                System.out.println("Could not read in the pic");
                //System.exit(0);
            }
            addComponentListener(new ComponentAdapter() {
                public void componentResized(ComponentEvent e) {

                }
            });
        }

        public Dimension getPreferredSize() {
            return new Dimension(w * 2, h * 2);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (Days.isSelected() && i == 0) {
                JOptionPane.showMessageDialog(this, "Enter Days as percentage.");
                i++;
            }
            int errcount = 0;
            if (e.getSource() == sub) {

                Date selectedDate = (Date) datepicker.getModel().getValue();
                Date selectedDate1 = (Date) datepicker1.getModel().getValue();
                if (selectedDate == null || selectedDate1 == null) {
                    JOptionPane.showMessageDialog(this, "Please select date/s.");
                } else {
                    String ab = null, hd = null;

                    if (selectedDate.after(selectedDate1)) {
                        JOptionPane.showMessageDialog(this, "From date must be older than to date.");
                    } else {
                        Date currdt = null;
                        try {
                            Registry re = LocateRegistry.getRegistry(9111);

                            ServerRMIInf rm = (ServerRMIInf) re.lookup("GetDate");

                            currdt = new SimpleDateFormat("yy/MM/dd").parse(rm.GetDate());

                        } catch (RemoteException ex) {

                        } catch (NotBoundException ex) {

                        } catch (Exception ex) {

                        }
                        if (currdt != null) {
                            if (selectedDate.after(currdt) || selectedDate1.after(currdt)) {
                                JOptionPane.showMessageDialog(this, "dates must be older than current date.");
                            } else {
                                if (Hrs.isSelected()) {
                                    int hr;
                                    try {
                                        hr = Integer.parseInt(hrdys.getText().trim());
                                    } catch (Exception ex) {
                                        JOptionPane.showMessageDialog(this, "hours must be a numeric value.");
                                        errcount = 1;
                                    }
                                    hd = "hours";
                                    if (Attyp.getSelectedItem().toString().trim().equals("Above")) {
                                        ab = "Above";

                                    } else if (Attyp.getSelectedItem().toString().trim().equals("Below")) {
                                        ab = "Below";
                                    }
                                }
                                if (Days.isSelected()) {
                                    int dy;
                                    try {
                                        dy = Integer.parseInt(hrdys.getText().trim());
                                    } catch (Exception ex) {
                                        JOptionPane.showMessageDialog(this, "Days must be a numeric value.");
                                        errcount = 1;
                                    }
                                    hd = "days";
                                    if (Attyp.getSelectedItem().toString().trim().equals("Above")) {
                                        ab = "Above";
                                    } else if (Attyp.getSelectedItem().toString().trim().equals("Below")) {
                                        ab = "Below";
                                    }

                                }
                                if (errcount != 1) {
                                    Registry re;
                                    try {

                                        re = LocateRegistry.getRegistry(9111);
                                        ServerRMIInf rm = (ServerRMIInf) re.lookup("CheckAtten");

                                        String[][] rest = rm.CheckAtten(selectedDate, selectedDate1, ab, hd, hrdys.getText().trim());

                                        String[] THead = {"User Id", "First Name", "Last Name", "Teacher Id", "Department", "Days", "out of(Days)", " Total % days", "Hours"};
                                        DefaultTableModel modello = new DefaultTableModel(rest, THead);
                                        tbs = new JTable(modello);
                                        if (rest != null && rest[0][1] != null) {
                                            new CheckAttenFr(tbs);
                                        } else {
                                            JOptionPane.showMessageDialog(this, "No record found.");

                                        }
                                    } catch (RemoteException | NotBoundException ex) {
                                        ex.printStackTrace();
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            }

                        } else {
                            JOptionPane.showMessageDialog(this, "Check your connection.");
                        }
                    }

                }

            }
        }
    }

    class CheckAttenFr extends JFrame implements ActionListener {

        JTable newtb;
        JButton sub;

        CheckAttenFr(JTable tbd) {
            setVisible(true);
            newtb = tbd;
            sub = new JButton("save");
            setTitle("Data Table Preview");
            setSize(wid / 2, hei / 2);
            newtb.setEnabled(false);
            setLayout(new BorderLayout(3, 3));

            JScrollPane scp = new JScrollPane(newtb);
            scp.setSize(new Dimension(wid / 2, hei / 2));
            setMinimumSize(new Dimension(wid / 4, hei / 3));
            sub.setSize(300, 34);
            add(scp, BorderLayout.CENTER);
            // add(sub, BorderLayout.SOUTH);

            sub.addActionListener(this);
            SwingUtilities.updateComponentTreeUI(this);
        }
//Generating attendance report

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    class GenTeaAttRep extends JPanel implements ActionListener, PopupMenuListener {

        JLabel MHead, FrmDte, ToDte, DepL;
        UtilDateModel model, model1;
        JComboBox SCDep;
        JButton Sub;
        JDatePanelImpl datePanel, datePanel1;
        JDatePickerImpl datepicker, datepicker1;
        Properties p;

        public GenTeaAttRep(String pico) {
            setLayout(null);
            MHead = new JLabel("Generate Teacher Attendance Report");
            MHead.setFont(new Font("Old English Text MT", Font.BOLD, 35));
            add(MHead);
            MHead.setBounds((int) (wid / 10), hei / 8, 600, 30);

            model = new UtilDateModel();
            p = new Properties();
            p.put("text.today", "Today");
            p.put("text.month", "Month");
            p.put("text.year", "Year");
            datePanel = new JDatePanelImpl(model, p);
            datepicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

            add(datepicker);

            model1 = new UtilDateModel();
            datePanel1 = new JDatePanelImpl(model1, p);
            datepicker1 = new JDatePickerImpl(datePanel1, new DateLabelFormatter());

            add(datepicker1);

            FrmDte = new JLabel("From:");
            FrmDte.setFont(tit);

            ToDte = new JLabel("To:");
            ToDte.setFont(tit);

            DepL = new JLabel("Select Department:");
            DepL.setFont(lab);

            SCDep = new JComboBox();
            SCDep.setFont(lab);

            Sub = new JButton("Generate");
            Sub.setFont(lab);
            //  ShwRep = new JButton("Show Report");
            //ShwRep.setFont(lab);
            //Save = new JButton("Save Report");
            //Save.setFont(lab);

            FrmDte.setBounds((int) (wid / 10), (int) (hei / 4), 150, 40);
            datepicker.setBounds((int) (wid / 6), (int) (hei / 4), 200, 30);
            ToDte.setBounds((int) (wid / 2.9), (int) (hei / 4), 150, 40);
            datepicker1.setBounds((int) (wid / 2.5), (int) (hei / 4), 200, 30);
            DepL.setBounds((int) (wid / 8), (int) (hei / 2.8), 200, 40);
            SCDep.setBounds((int) (wid / 3.7), (int) (hei / 2.8), 180, 35);
            Sub.setBounds((int) (wid / 5), (int) (hei / 1.5), 200, 35);
            //ShwRep.setBounds((int) (wid / 4), (int) (hei / 1.5), 150, 30);
            // Save.setBounds((int) (wid / 2.6), (int) (hei / 1.5), 150, 30);

            add(FrmDte);
            add(ToDte);
            add(DepL);
            add(SCDep);
            add(Sub);
            //   add(ShwRep);
            // add(Save);

            Sub.addActionListener(this);
            SCDep.addPopupMenuListener(this);

            try {
                image = ImageIO.read(new File(pico));
                w = image.getWidth();
                h = image.getHeight();

            } catch (IOException ioe) {
                System.out.println("Could not read in the pic");
                //System.exit(0);
            }
            addComponentListener(new ComponentAdapter() {
                public void componentResized(ComponentEvent e) {

                }
            });
        }

        public Dimension getPreferredSize() {
            return new Dimension(w * 2, h * 2);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == Sub) {
                if (SCDep.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(this, "Please select Department.");
                } else {
                    Date selectedDate = (Date) datepicker.getModel().getValue();
                    Date selectedDate1 = (Date) datepicker1.getModel().getValue();
                    if (selectedDate == null || selectedDate1 == null) {
                        JOptionPane.showMessageDialog(this, "Please select date/s.");
                    } else {
                        Date currdt = null;
                        try {
                            Registry re = LocateRegistry.getRegistry(9111);

                            ServerRMIInf rm = (ServerRMIInf) re.lookup("GetDate");

                            currdt = new SimpleDateFormat("yy/MM/dd").parse(rm.GetDate());

                        } catch (RemoteException ex) {

                        } catch (NotBoundException ex) {

                        } catch (Exception ex) {

                        }
                        if (currdt != null) {
                            if (selectedDate.after(currdt) || selectedDate1.after(currdt)) {
                                JOptionPane.showMessageDialog(this, "dates must be older than current date.");
                            } else {
                                String Scdep = SCDep.getSelectedItem().toString();

                                Registry re;
                                try {

                                    re = LocateRegistry.getRegistry(9111);
                                    ServerRMIInf rm = (ServerRMIInf) re.lookup("GenTeaRep");
                                    String res;
                                    res = rm.GenTeaAtt(selectedDate, selectedDate1, Scdep);
                                    JOptionPane.showMessageDialog(this, res);

                                } catch (RemoteException ex) {
                                    Logger.getLogger(AdminHome.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (NotBoundException ex) {
                                    Logger.getLogger(AdminHome.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (Exception ex) {
                                    Logger.getLogger(AdminHome.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    }
                }
            }
        }

        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            SCDep.removeAllItems();
            if (e.getSource() == SCDep) {
                Registry re;
                try {

                    re = LocateRegistry.getRegistry(9111);
                    ServerRMIInf rm = (ServerRMIInf) re.lookup("ShowDept");
                    ArrayList<String> res = new ArrayList<String>();
                    res = rm.ShowDept("depts");
                    String[] rest = new String[res.size()];

                    for (int i = 0; i < res.size(); i++) {
                        SCDep.addItem(res.get(i));

                    }

                } catch (RemoteException | NotBoundException ex) {
                    ex.printStackTrace();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

        }

        @Override
        public void popupMenuCanceled(PopupMenuEvent e) {
            if (e.getSource() == SCDep) {
                SCDep.hidePopup();
            }
        }
    }

    class ViewGenTeaAttRep extends JPanel implements ActionListener, ListSelectionListener {

        String mn, yr, dp;
        JLabel MHead, rwsel;
        JTable MarTD;
        DefaultTableModel dtm;
        JScrollPane scp;
        JButton Save, delete;
        byte[] bt;
        JFileChooser fc;

        public ViewGenTeaAttRep(String pico) {
            fc = new JFileChooser();

            fc.addActionListener(this);
            setLayout(null);
            MHead = new JLabel("View Teacher Attendance Report");
            MHead.setFont(new Font("Old English Text MT", Font.BOLD, 35));
            add(MHead);
            MHead.setBounds((int) (wid / 9), hei / 8, 600, 30);

            rwsel = new JLabel("Select row to save/delete report.");
            rwsel.setFont(tit);
            rwsel.setBounds((int) (wid / 9), hei / 4, 700, 25);
            add(rwsel);
            MarTD = new JTable();
            dtm = new DefaultTableModel(0, 0) {

                public boolean isCellEditable(int row, int column) {
                    return false;//This causes all cells to be not editable
                }
            ;

            };
           dtm.setColumnIdentifiers(new Object[]{"month", "year", "department", "From(date)", "To(date)"});

            MarTD.setModel(dtm);

            scp = new JScrollPane(MarTD, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            MarTD.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            TableColumn column = MarTD.getColumnModel().getColumn(0);
            column.setPreferredWidth(200);
            column = MarTD.getColumnModel().getColumn(1);
            column.setPreferredWidth(200);
            column = MarTD.getColumnModel().getColumn(2);
            column.setPreferredWidth(200);
            column = MarTD.getColumnModel().getColumn(3);
            column.setPreferredWidth(200);
            column = MarTD.getColumnModel().getColumn(4);
            column.setPreferredWidth(200);
            scp.setSize(new Dimension(550, 250));
            scp.setLocation(wid / 9, (int) (hei / 3));

            Registry re;
            try {

                re = LocateRegistry.getRegistry(9111);
                ServerRMIInf rm = (ServerRMIInf) re.lookup("getTeaRepData");

                String[][] rest = rm.getTeaRepData();

                if (rest == null) {
                    JOptionPane.showMessageDialog(this, "No Report Found.");
                } else {
                    // System.out.println(rest[0][0]);
                    for (int ct = 0; ct < rest.length; ct++) {
                        dtm.addRow(new Object[]{rest[ct][0], rest[ct][1], rest[ct][2], rest[ct][3], rest[ct][4]});
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Please check your connection and try again.");
            }

            Save = new JButton("Get & Save Report");
            Save.setFont(lab);
            Save.setBounds(wid / 6, (int) (hei / 1.3), 250, 35);
            delete = new JButton("Delete Report");
            delete.setBounds((int) (wid / 2.5), (int) (hei / 1.3), 200, 35);
            delete.setFont(lab);

            add(scp);
            add(Save);
            add(delete);

            Save.addActionListener(this);
            delete.addActionListener(this);
            MarTD.getSelectionModel().addListSelectionListener(this);
            try {
                image = ImageIO.read(new File(pico));
                w = image.getWidth();
                h = image.getHeight();

            } catch (IOException ioe) {
                System.out.println("Could not read in the pic");
                //System.exit(0);
            }
            addComponentListener(new ComponentAdapter() {
                public void componentResized(ComponentEvent e) {

                }
            });
        }

        public Dimension getPreferredSize() {
            return new Dimension(w * 2, h * 2);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == Save) {
                if (mn != null) {
                    Registry re;
                    try {

                        re = LocateRegistry.getRegistry(9111);
                        ServerRMIInf rm = (ServerRMIInf) re.lookup("getTeaRepFile");

                        bt = rm.getTeaRepFile(mn, yr, dp);
                        if (bt != null) {

                            fc.showSaveDialog(this);
                            String path = fc.getSelectedFile().getAbsolutePath();
                            FileOutputStream stream = new FileOutputStream(path + ".pdf");
                            try {
                                stream.write(bt);
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(this, "Error occured.");
                            } finally {
                                stream.close();
                                JOptionPane.showMessageDialog(this, "File saved successfully.");
                            }

                        } else {
                            JOptionPane.showMessageDialog(this, "error processing file");
                        }

                    } catch (RemoteException | NotBoundException ex) {
                        ex.printStackTrace();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Please select record first.");
                }
            } else if (e.getSource() == delete) {
                if (mn != null) {
                    Registry re;
                    try {

                        re = LocateRegistry.getRegistry(9111);
                        ServerRMIInf rm = (ServerRMIInf) re.lookup("DelTeaRep");

                        String res = rm.DelTeaRep(mn, yr, dp);

                        JOptionPane.showMessageDialog(this, res);
                        sp.setRightComponent(new ViewGenTeaAttRep("C:\\AMSRF\\other\\222.jpg"));

                    } catch (RemoteException ex) {
                        Logger.getLogger(AdminHome.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (NotBoundException ex) {
                        Logger.getLogger(AdminHome.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(AdminHome.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Please select record first.");
                }

            }
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            mn = MarTD.getValueAt(MarTD.getSelectedRow(), 0).toString();
            yr = MarTD.getValueAt(MarTD.getSelectedRow(), 1).toString();
            dp = MarTD.getValueAt(MarTD.getSelectedRow(), 2).toString();
        }
    }

    //STUDENT ATTENDANCE
    class ViewStuAtt extends JPanel implements ActionListener, PopupMenuListener {

        JLabel MHead, UsrId, FrmDte, ToDte, AtTyp, TotHrs, Percent, Dept, SrchTp, Enhrdt, RTyp;
        JButton sub;

        String[] StuTable = {"User Id", "First Name", "Last Name", "Roll no.", "Class", "Course", "lecs. Attended", "Out of (lecs)", "lecture(%)", "Hours"};
        String[] tp = {"Above", "Below"};
        JComboBox Attyp, Ccls, Ccour;
        JLabel LCls, LCour, LTeaId;
        JRadioButton Hrs, lec, RTea, RCour;
        ButtonGroup hd, tc;

        JTextField hrdys, TfTeaId;

        int lecper = 0;
        DateFormat df;
        JDatePanelImpl datePanel, datePanel1;
        UtilDateModel model, model1;
        Properties p;
        JDatePickerImpl datepicker, datepicker1;
        JTable tbs;

        public ViewStuAtt(String pico) {
            setLayout(null);
            MHead = new JLabel("Check Student Attendance");
            MHead.setFont(new Font("Old English Text MT", Font.BOLD, 35));
            add(MHead);
            MHead.setBounds((int) (wid / 6), hei / 8, 600, 30);

            model = new UtilDateModel();
            p = new Properties();
            p.put("text.today", "Today");
            p.put("text.month", "Month");
            p.put("text.year", "Year");
            datePanel = new JDatePanelImpl(model, p);
            datepicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

            add(datepicker);
            model1 = new UtilDateModel();
            datePanel1 = new JDatePanelImpl(model1, p);
            datepicker1 = new JDatePickerImpl(datePanel1, new DateLabelFormatter());

            add(datepicker1);

            FrmDte = new JLabel("From:");
            FrmDte.setFont(tit);

            ToDte = new JLabel("To:");
            ToDte.setFont(tit);

            AtTyp = new JLabel("Attendance Type:");
            AtTyp.setFont(tit);

            Attyp = new JComboBox(tp);
            Attyp.setFont(lab);

            Hrs = new JRadioButton("Hours");
            lec = new JRadioButton("Lectures(%)");
            hd = new ButtonGroup();
            hd.add(Hrs);
            hd.add(lec);

            SrchTp = new JLabel("Search Type:");
            SrchTp.setFont(tit);

            Hrs.setFont(lab);
            lec.setFont(lab);

            Enhrdt = new JLabel("Enter lectures(%)/Hours:");
            Enhrdt.setFont(lab);

            hrdys = new JTextField();

            sub = new JButton("Submit");
            sub.setFont(lab);

            Ccls = new JComboBox();
            Ccour = new JComboBox();
            LCls = new JLabel("Class:");
            LCour = new JLabel("Course:");
            LTeaId = new JLabel("Enter User Id:");
            TfTeaId = new JTextField();
            hrdys.setDocument(new JTextFieldLimit(5));
            TfTeaId.setDocument(new JTextFieldLimit(23));
            LCls.setFont(lab);
            LCour.setFont(lab);
            LTeaId.setFont(lab);

            RTyp = new JLabel("Teacher/Course:");
            RTea = new JRadioButton("Teacher");
            RCour = new JRadioButton("Course");
            tc = new ButtonGroup();
            tc.add(RTea);
            tc.add(RCour);

            RTyp.setFont(lab);
            RTea.setFont(lab);
            RCour.setFont(lab);

            add(RTyp);
            add(RTea);
            add(RCour);
            add(Ccls);
            add(Ccour);
            add(LCls);
            add(LTeaId);
            add(LCour);
            add(TfTeaId);

            LTeaId.setVisible(false);
            TfTeaId.setVisible(false);

            LCour.setVisible(false);
            Ccour.setVisible(false);

            Ccls.addPopupMenuListener(this);
            Ccour.addPopupMenuListener(this);
            // TotHrs = new JLabel("Hours");
            //Percent = new JLabel("Percentage:");
            // Dept = new JLabel("Department:");
            FrmDte.setBounds((int) (wid / 10), (int) (hei / 4), 150, 40);
            datepicker.setBounds((int) (wid / 6), (int) (hei / 4), 200, 30);
            ToDte.setBounds((int) (wid / 2.9), (int) (hei / 4), 150, 40);
            datepicker1.setBounds((int) (wid / 2.5), (int) (hei / 4), 200, 30);

            /*   LCls.setBounds();
            LCour.setBounds();
            LTeaId.setBounds();
            Ccls.setBounds();
            Ccour.setBounds();
            TfTeaId.setBounds(); */
            LCls.setBounds((int) (wid / 10), (int) (hei / 3), 200, 35);
            Ccls.setBounds((int) (wid / 6), (int) (hei / 3), 200, 35);

            RTyp.setBounds((int) (wid / 10), (int) (hei / 2.5), 200, 35);
            RTea.setBounds((int) (wid / 4.5), (int) (hei / 2.5), 100, 35);
            RCour.setBounds((int) (wid / 3), (int) (hei / 2.5), 100, 35);

            LTeaId.setBounds((int) (wid / 8.2), (int) (hei / 2.1), 200, 35);
            TfTeaId.setBounds((int) (wid / 4), (int) (hei / 2.1), 200, 35);

            LCour.setBounds((int) (wid / 8.2), (int) (hei / 2.1), 200, 35);
            Ccour.setBounds((int) (wid / 4), (int) (hei / 2.1), 200, 35);

            SrchTp.setBounds((int) (wid / 10), (int) (hei / 1.8), 200, 40);
            Hrs.setBounds((int) (wid / 4), (int) (hei / 1.8), 100, 40);
            lec.setBounds((int) (wid / 3), (int) (hei / 1.8), 140, 40);

            AtTyp.setBounds((int) (wid / 10), (int) (hei / 1.55), 200, 40);
            Attyp.setBounds((int) (wid / 4), (int) (hei / 1.54), 150, 30);

            Enhrdt.setBounds((int) (wid / 8.2), (int) (hei / 1.36), 250, 40);
            hrdys.setBounds((int) (wid / 3), (int) (hei / 1.35), 150, 30);
            //    sub.setBounds((int) (wid / 5), (int) (hei / 1.5), 150, 30);
            sub.setBounds((int) (wid / 5), (int) (hei / 1.2), 150, 30);
            add(FrmDte);
            add(ToDte);
            add(AtTyp);
            add(Attyp);

            add(Hrs);
            add(lec);
            add(SrchTp);
            add(Enhrdt);
            add(hrdys);
            add(sub);
            lec.addActionListener(this);
            sub.addActionListener(this);
            RTea.addActionListener(this);
            RCour.addActionListener(this);
            try {
                image = ImageIO.read(new File(pico));
                w = image.getWidth();
                h = image.getHeight();

            } catch (IOException ioe) {
                System.out.println("Could not read in the pic");
                //System.exit(0);
            }
            addComponentListener(new ComponentAdapter() {
                public void componentResized(ComponentEvent e) {

                }
            });
        }

        public Dimension getPreferredSize() {
            return new Dimension(w * 2, h * 2);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int hrdnum = 0;
            if (lec.isSelected() && lecper == 0) {
                JOptionPane.showMessageDialog(this, "Enter lectures as percentage.");
                lecper++;
            }
            String TeaCour = null, TeCourData = null;
            if (RTea.isSelected()) {

                LTeaId.setVisible(true);
                TfTeaId.setVisible(true);
                LCour.setVisible(false);
                Ccour.setVisible(false);

            }
            if (RCour.isSelected()) {

                LTeaId.setVisible(false);
                TfTeaId.setVisible(false);

                LCour.setVisible(true);
                Ccour.setVisible(true);

            }

            if (e.getSource() == sub) {

                int errcount = 0;
                if (Ccls.getSelectedItem() == null) {
                    errcount = 1;
                    JOptionPane.showMessageDialog(this, "Please select a class.");
                }
                if (errcount != 1 && !RTea.isSelected() && !RCour.isSelected()) {
                    errcount = 1;
                    JOptionPane.showMessageDialog(this, "select for teacher/course");
                }
                if (errcount != 1 && RCour.isSelected() && Ccour.getSelectedItem() == null) {
                    errcount = 1;
                    JOptionPane.showMessageDialog(this, "Please select course.");
                }
                if (errcount != 1 && RTea.isSelected() && TfTeaId.getText().length() == 0) {
                    errcount = 1;
                    JOptionPane.showMessageDialog(this, "Please enter Id.");
                }
                if (errcount != 1 && !Hrs.isSelected() && !lec.isSelected()) {
                    errcount = 1;
                    JOptionPane.showMessageDialog(this, "select for hours/lectures");
                }
                if (errcount != 1 && hrdys.getText().trim().length() == 0) {
                    JOptionPane.showMessageDialog(this, "Please enter hours/days(%).");
                    errcount = 1;
                } else if (errcount != 1) {
                    try {
                       hrdnum = Integer.parseInt(hrdys.getText().trim());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Enter lectures(%)/hours as a numeric integer value.");
                    }

                }

                if (RTea.isSelected()) {
                    TeaCour = "Teacher";

                    TeCourData = TfTeaId.getText();
                } else if (RCour.isSelected() && Ccour.getSelectedItem() != null) {
                    TeaCour = "Course";

                    TeCourData = Ccour.getSelectedItem().toString();
                }

                Date selectedDate = (Date) datepicker.getModel().getValue();
                Date selectedDate1 = (Date) datepicker1.getModel().getValue();
                if (selectedDate == null || selectedDate1 == null) {
                    JOptionPane.showMessageDialog(this, "Please select date/s.");
                } else {
                    Date currdt = null;
                    try {
                        Registry re = LocateRegistry.getRegistry(9111);

                        ServerRMIInf rm = (ServerRMIInf) re.lookup("GetDate");

                        currdt = new SimpleDateFormat("yy/MM/dd").parse(rm.GetDate());

                    } catch (RemoteException ex) {

                    } catch (NotBoundException ex) {

                    } catch (Exception ex) {

                    }
                    if (currdt != null) {
                        if (selectedDate.after(currdt) || selectedDate1.after(currdt)) {
                            JOptionPane.showMessageDialog(this, "dates must be older than current date.");
                        } else {

                            String ab = null, hd = null;
                            df = new SimpleDateFormat("yyyy/MM/dd");

                            if (selectedDate.after(selectedDate1)) {
                                JOptionPane.showMessageDialog(this, "From date must be older than to date.");
                            } else {

                                if (Hrs.isSelected()) {
                                    hd = "hours";

                                }
                                if (lec.isSelected()) {
                                    hd = "lectures";
                                      if((hrdnum-100)>0||hrdnum<0)
                            {
                                JOptionPane.showMessageDialog(this,"lec % must be between 0 to 100%");
                               errcount=1;
                            }

                                }
                                if (errcount != 1) {
                                    Registry re;
                                    try {

                                        re = LocateRegistry.getRegistry(9111);
                                        ServerRMIInf rm = (ServerRMIInf) re.lookup("CheckStuAtten");
                                        ArrayList<String> ar = new ArrayList<String>();

                                        String[][] rest = rm.CheckStuAttend(selectedDate, selectedDate1, Ccls.getSelectedItem().toString(), TeaCour, TeCourData, Attyp.getSelectedItem().toString(), hd, hrdys.getText().trim());
                                        // System.out.println(rest[0][0]);

                                        DefaultTableModel modello = new DefaultTableModel(rest, StuTable);
                                        tbs = new JTable(modello);
                                        if (rest != null && rest[0][1] != null) {
                                            new CheckStuAttenFr(tbs);
                                        } else {
                                            JOptionPane.showMessageDialog(this, "No record found.");

                                        }

                                    } catch (RemoteException | NotBoundException ex) {
                                        ex.printStackTrace();
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                }

                                /*      DefaultTableModel dtm = (DefaultTableModel) newtb.getModel();
                    int nRow = dtm.getRowCount(), nCol = dtm.getColumnCount();
                    String[][] tableData = new String[nRow][nCol];
                    for (int i = 0; i < nRow; i++) {
                        for (int j = 0; j < nCol; j++) {
                              tableData[i][j] = (String) dtm.getValueAt(i, j); */
                            }
                        }
                    } 
                }

            }

        }

        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            if (e.getSource() == Ccls) {
                Ccls.removeAllItems();
                Registry re;
                try {
                    re = LocateRegistry.getRegistry(9111);
                    ServerRMIInf rm = (ServerRMIInf) re.lookup("ShowClasses");
                    ArrayList<String> res = new ArrayList<String>();
                    res = rm.ShowClass("ShwClass");
                    String[] rest = new String[res.size()];

                    for (int i = 0; i < res.size(); i++) {
                        Ccls.addItem(res.get(i));
                    }

                } catch (RemoteException | NotBoundException ex) {
                    ex.printStackTrace();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
            if (e.getSource() == Ccour) {
                Ccour.removeAllItems();
                Registry re;
                try {
                    String CL = "CourseList";
                    re = LocateRegistry.getRegistry(9111);
                    ServerRMIInf rm = (ServerRMIInf) re.lookup("ShowCourses");
                    ArrayList<String> res = new ArrayList<String>();
                    res = rm.ShowCourses(Ccls.getSelectedItem().toString().trim(), CL);
                    Set uniqueValues = new HashSet(res);

                    for (Iterator it = uniqueValues.iterator(); it.hasNext();) {
                        Ccour.addItem(it.next());

                    }
                } catch (RemoteException | NotBoundException ex) {
                    ex.printStackTrace();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        }

        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

        }

        @Override
        public void popupMenuCanceled(PopupMenuEvent e) {
            Ccls.hidePopup();
            Ccour.hidePopup();
        }
    }

    class CheckStuAttenFr extends JFrame implements ActionListener {

        JTable newtb;
        JButton sub;

        CheckStuAttenFr(JTable tbd) {
            setVisible(true);
            newtb = tbd;
            sub = new JButton("save");
            setTitle("Data Table Preview");
            setSize(wid / 2, hei / 2);
            newtb.setEnabled(false);
            setLayout(new BorderLayout(3, 3));

            JScrollPane scp = new JScrollPane(newtb);
            scp.setSize(new Dimension(wid / 3, hei / 3));
            setMinimumSize(new Dimension(wid / 4, hei / 3));
            sub.setSize(300, 34);
            add(scp, BorderLayout.CENTER);
            // add(sub, BorderLayout.SOUTH);

            sub.addActionListener(this);
            SwingUtilities.updateComponentTreeUI(this);
        }
//Generating attendance report

        @Override
        public void actionPerformed(ActionEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    class ViewGenStuAttRep extends JPanel implements ActionListener, ListSelectionListener {

        String mn, yr, dp, teaid, name, cls;
        JLabel MHead, rwsel;
        JTable MarTD;
        DefaultTableModel dtm;
        JScrollPane scp;
        JButton Save;
        byte[] bt;
        JFileChooser fc;

        public ViewGenStuAttRep(String pico) {
            setLayout(null);
            MHead = new JLabel("View Student Attendance Report");
            MHead.setFont(new Font("Old English Text MT", Font.BOLD, 35));
            add(MHead);
            MHead.setBounds((int) (wid / 9), hei / 8, 600, 30);

            fc = new JFileChooser();

            fc.addActionListener(this);

            rwsel = new JLabel("Select row to save report.");
            rwsel.setFont(tit);
            rwsel.setBounds((int) (wid / 9), hei / 4, 700, 25);
            add(rwsel);
            MarTD = new JTable();
            dtm = new DefaultTableModel(0, 0) {

                public boolean isCellEditable(int row, int column) {
                    return false;//This causes all cells to be not editable
                }
            ;

            };
           dtm.setColumnIdentifiers(new Object[]{"Teacher Id", "Teacher Name", "Month", "Year", "Class", "from(date)", "to(date)"});

            MarTD.setModel(dtm);

            scp = new JScrollPane(MarTD, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            MarTD.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            TableColumn column = MarTD.getColumnModel().getColumn(0);
            column.setPreferredWidth(200);
            column = MarTD.getColumnModel().getColumn(1);
            column.setPreferredWidth(200);
            column = MarTD.getColumnModel().getColumn(2);
            column.setPreferredWidth(200);
            column = MarTD.getColumnModel().getColumn(3);
            column.setPreferredWidth(200);
            column = MarTD.getColumnModel().getColumn(4);
            column.setPreferredWidth(200);
            column = MarTD.getColumnModel().getColumn(5);
            column.setPreferredWidth(200);
            column = MarTD.getColumnModel().getColumn(6);
            column.setPreferredWidth(200);

            scp.setSize(new Dimension(550, 250));
            scp.setLocation(wid / 9, (int) (hei / 3));

            Registry re;
            try {

                re = LocateRegistry.getRegistry(9111);
                ServerRMIInf rm = (ServerRMIInf) re.lookup("getAdmRepData");

                String[][] rest = rm.getAdmRepData();

                if (rest == null) {
                    JOptionPane.showMessageDialog(this, "No Report Found.");
                } else {
                    // System.out.println(rest[0][0]);
                    for (int ct = 0; ct < rest.length; ct++) {
                        dtm.addRow(new Object[]{rest[ct][0], rest[ct][1], rest[ct][2], rest[ct][3], rest[ct][4], rest[ct][5], rest[ct][6]});
                    }
                }
            } catch (RemoteException | NotBoundException ex) {
                ex.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            Save = new JButton("Get & Save Report");
            Save.setFont(lab);
            Save.setBounds(wid / 6, (int) (hei / 1.3), 250, 35);

            add(scp);
            add(Save);

            Save.addActionListener(this);

            MarTD.getSelectionModel().addListSelectionListener(this);

            try {
                image = ImageIO.read(new File(pico));
                w = image.getWidth();
                h = image.getHeight();

            } catch (IOException ioe) {
                System.out.println("Could not read in the pic");
                //System.exit(0);
            }
            addComponentListener(new ComponentAdapter() {
                public void componentResized(ComponentEvent e) {

                }
            });
        }

        public Dimension getPreferredSize() {
            return new Dimension(w * 2, h * 2);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == Save) {
                if (mn != null) {
                    Registry re;
                    try {

                        re = LocateRegistry.getRegistry(9111);
                        ServerRMIInf rm = (ServerRMIInf) re.lookup("getAdmStuRepFile");

                        bt = rm.getAdmStuRepFile(teaid, mn, yr, cls);
                        if (bt != null) {

                            fc.showSaveDialog(this);
                            String path = fc.getSelectedFile().getAbsolutePath();
                            FileOutputStream stream = new FileOutputStream(path + ".pdf");
                            try {
                                stream.write(bt);
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(this, "Error occured.");
                            } finally {
                                stream.close();
                                JOptionPane.showMessageDialog(this, "File saved successfully.");
                            }

                        } else {
                            JOptionPane.showMessageDialog(this, "error processing file");
                        }

                    } catch (RemoteException | NotBoundException ex) {
                        ex.printStackTrace();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Please select record first.");
                }
            }
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            teaid = MarTD.getValueAt(MarTD.getSelectedRow(), 0).toString();
            name = mn = MarTD.getValueAt(MarTD.getSelectedRow(), 1).toString();
            mn = MarTD.getValueAt(MarTD.getSelectedRow(), 2).toString();
            yr = MarTD.getValueAt(MarTD.getSelectedRow(), 3).toString();
            cls = MarTD.getValueAt(MarTD.getSelectedRow(), 4).toString();
        }
    }

    class ViewStuDefRep extends JPanel implements ActionListener, ListSelectionListener {

        String mn, yr, dp, teaid, name, cls;
        JLabel MHead, rwsel;
        JTable MarTD;
        DefaultTableModel dtm;
        JScrollPane scp;
        JButton Save;
        byte[] bt;
        JFileChooser fc;

        public ViewStuDefRep(String pico) {
            setLayout(null);
            MHead = new JLabel("View Student Defaulters");
            MHead.setFont(new Font("Old English Text MT", Font.BOLD, 35));
            add(MHead);
            MHead.setBounds((int) (wid / 6), hei / 8, 600, 30);

            fc = new JFileChooser();

            fc.addActionListener(this);

            rwsel = new JLabel("Select row to save report.");
            rwsel.setFont(tit);
            rwsel.setBounds((int) (wid / 9), hei / 4, 700, 25);
            add(rwsel);
            MarTD = new JTable();
            dtm = new DefaultTableModel(0, 0) {

                public boolean isCellEditable(int row, int column) {
                    return false;//This causes all cells to be not editable
                }
            ;

            };
           dtm.setColumnIdentifiers(new Object[]{"Teacher Id", "Teacher Name", "Month", "Year", "Class", "from(date)", "to(date)"});

            MarTD.setModel(dtm);

            scp = new JScrollPane(MarTD, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            MarTD.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            TableColumn column = MarTD.getColumnModel().getColumn(0);
            column.setPreferredWidth(200);
            column = MarTD.getColumnModel().getColumn(1);
            column.setPreferredWidth(200);
            column = MarTD.getColumnModel().getColumn(2);
            column.setPreferredWidth(200);
            column = MarTD.getColumnModel().getColumn(3);
            column.setPreferredWidth(200);
            column = MarTD.getColumnModel().getColumn(4);
            column.setPreferredWidth(200);
            column = MarTD.getColumnModel().getColumn(5);
            column.setPreferredWidth(200);
            column = MarTD.getColumnModel().getColumn(6);
            column.setPreferredWidth(200);

            scp.setSize(new Dimension(550, 250));
            scp.setLocation(wid / 9, (int) (hei / 3));

            Registry re;
            try {

                re = LocateRegistry.getRegistry(9111);
                ServerRMIInf rm = (ServerRMIInf) re.lookup("getAdmStuDefData");

                String[][] rest = rm.getAdmStuDefData();

                if (rest == null) {
                    JOptionPane.showMessageDialog(this, "No Report Found.");
                } else {
                    // System.out.println(rest[0][0]);
                    for (int ct = 0; ct < rest.length; ct++) {
                        dtm.addRow(new Object[]{rest[ct][0], rest[ct][1], rest[ct][2], rest[ct][3], rest[ct][4], rest[ct][5], rest[ct][6]});
                    }
                }
            } catch (RemoteException | NotBoundException ex) {
                ex.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            Save = new JButton("Get & Save Report");
            Save.setFont(lab);
            Save.setBounds(wid / 6, (int) (hei / 1.3), 250, 35);

            add(scp);
            add(Save);

            Save.addActionListener(this);

            MarTD.getSelectionModel().addListSelectionListener(this);

            try {
                image = ImageIO.read(new File(pico));
                w = image.getWidth();
                h = image.getHeight();

            } catch (IOException ioe) {
                System.out.println("Could not read in the pic");
                //System.exit(0);
            }
            addComponentListener(new ComponentAdapter() {
                public void componentResized(ComponentEvent e) {

                }
            });
        }

        public Dimension getPreferredSize() {
            return new Dimension(w * 2, h * 2);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == Save) {
                if (mn != null) {
                    Registry re;
                    try {

                        re = LocateRegistry.getRegistry(9111);
                        ServerRMIInf rm = (ServerRMIInf) re.lookup("getAdmStuDefFile");

                        bt = rm.getStuDefFile(teaid, mn, yr, cls);
                        if (bt != null) {

                            fc.showSaveDialog(this);
                            String path = fc.getSelectedFile().getAbsolutePath();
                            FileOutputStream stream = new FileOutputStream(path + ".pdf");
                            try {
                                stream.write(bt);
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(this, "Error occured.");
                            } finally {
                                stream.close();
                                JOptionPane.showMessageDialog(this, "File saved successfully.");
                            }

                        } else {
                            JOptionPane.showMessageDialog(this, "error processing file");
                        }

                    } catch (RemoteException | NotBoundException ex) {
                        ex.printStackTrace();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Please select record first.");
                }
            }
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            teaid = MarTD.getValueAt(MarTD.getSelectedRow(), 0).toString();
            name = mn = MarTD.getValueAt(MarTD.getSelectedRow(), 1).toString();
            mn = MarTD.getValueAt(MarTD.getSelectedRow(), 2).toString();
            yr = MarTD.getValueAt(MarTD.getSelectedRow(), 3).toString();
            cls = MarTD.getValueAt(MarTD.getSelectedRow(), 4).toString();

        }
    }

    class ChangeAdmin extends JPanel implements ActionListener {

        JLabel MHead, enDet, passw, firstn, lastn, email, mob, Vpassw;
        JTextField Tfirstn, Tlastn, Temail, Tmob;
        JPasswordField Tpassw, VTpassw;
        JPanel centerPanel, middlePanel;
        JButton submit;

        public ChangeAdmin(String pico) {

            MHead = new JLabel("Change Admin");
            MHead.setFont(new Font("Old English Text MT", Font.BOLD, 35));
            MHead.setSize(200, 200);

            centerPanel = new JPanel();
            centerPanel.setOpaque(false);

            GridLayout gl = new GridLayout(1, 3);
            centerPanel.setLayout(gl);
            gl.setHgap(50);
            gl.setVgap(50);
            add(centerPanel, BorderLayout.NORTH);
            centerPanel.setBorder(
                    BorderFactory.createEmptyBorder());
            // centerPanel.setPreferredSize(new Dimension(900, 100));
            centerPanel.add(new JLabel());

            centerPanel.add(MHead);
            centerPanel.add(new JLabel());

            //middle
            enDet = new JLabel("Enter Details");
            enDet.setFont(tit);

            firstn = new JLabel("First Name:");
            firstn.setFont(lab);
            Tfirstn = new JTextField();
            Tfirstn.setDocument(new JTextFieldLimit(25));
            lastn = new JLabel("Last Name:");
            lastn.setFont(lab);
            Tlastn = new JTextField();
            Tlastn.setDocument(new JTextFieldLimit(25));
            email = new JLabel("Email:");
            email.setFont(lab);
            Temail = new JTextField();
            Temail.setDocument(new JTextFieldLimit(30));
            mob = new JLabel("Mobile:");
            mob.setFont(lab);
            Tmob = new JTextField();
            Tmob.setDocument(new JTextFieldLimit(10));
            passw = new JLabel("Password:");
            passw.setFont(lab);
            Tpassw = new JPasswordField();
            Tpassw.setDocument(new JTextFieldLimit(15));
            Vpassw = new JLabel("Confirm Password:");

            Vpassw.setFont(lab);
            VTpassw = new JPasswordField();
            VTpassw.setDocument(new JTextFieldLimit(15));
            submit = new JButton("Submit");
            submit.setFont(lab);

            middlePanel = new JPanel();
            middlePanel.setOpaque(false);

            GridLayout gl1 = new GridLayout(6, 4);
            middlePanel.setLayout(gl1);
            gl1.setHgap(30);
            gl1.setVgap(30);

            middlePanel.setBorder(
                    BorderFactory.createEmptyBorder());
            //      middlePanel.setPreferredSize(new Dimension(800, 350));

            middlePanel.add(new JLabel());
            middlePanel.add(new JLabel());
            middlePanel.add(new JLabel());
            middlePanel.add(new JLabel());

            middlePanel.add(enDet);
            middlePanel.add(new JLabel());
            middlePanel.add(new JLabel());
            middlePanel.add(new JLabel());
            middlePanel.add(firstn);
            middlePanel.add(Tfirstn);
            middlePanel.add(lastn);
            middlePanel.add(Tlastn);
            middlePanel.add(email);
            middlePanel.add(Temail);
            middlePanel.add(mob);
            middlePanel.add(Tmob);
            middlePanel.add(passw);
            middlePanel.add(Tpassw);
            middlePanel.add(Vpassw);
            middlePanel.add(VTpassw);
            middlePanel.add(new JLabel());
            middlePanel.add(submit);
            submit.addActionListener(this);
            add(middlePanel, BorderLayout.SOUTH);

            try {
                image = ImageIO.read(new File(pico));
                w = image.getWidth();
                h = image.getHeight();

            } catch (IOException ioe) {
                System.out.println("Could not read in the pic");
                //System.exit(0);
            }
        }

        public Dimension getPreferredSize() {
            return new Dimension(w * 2, h * 2);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int errcount = 0;

            String curpass, fn, ln, eml, mobl, pass;
            String[] data = new String[7];
            if (e.getSource() == submit) {

                if (Tfirstn.getText().trim().length() == 0 || Tlastn.getText().trim().length() == 0 || Temail.getText().trim().length() == 0 || Tmob.getText().trim().length() == 0 || Tpassw.getText().trim().length() == 0) {
                    JOptionPane.showMessageDialog(this, "All fields are mandatory.");
                    errcount = 1;
                } else {
                    if (Tpassw.getText().equals(VTpassw.getText())) {

                        fn = Tfirstn.getText().trim();
                        ln = Tlastn.getText().trim();

                        if (errcount != 1 && fn.matches(".*\\d+.*") || ln.matches(".*\\d+.*")) {
                            JOptionPane.showMessageDialog(this, "Invalid name.");
                            errcount = 1;
                        }

                        eml = Temail.getText().trim();
                        if (errcount != 1 && !emailValidator.validate(eml.trim())) {
                            JOptionPane.showMessageDialog(this, "Invalid Email.");
                            errcount = 1;
                        }
                        mobl = Tmob.getText().trim();
                        long mobno;
                        if (errcount != 1) {
                            try {
                                mobno = Long.parseLong(mobl);
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(this, "Invalid mobile number.");
                                errcount = 1;
                            }
                        }
                        pass = Tpassw.getText();

                        data[0] = fn;
                        data[1] = ln;
                        data[2] = eml;
                        data[3] = mobl;
                        data[4] = pass;

                        if (errcount != 1) {
                            curpass = JOptionPane.showInputDialog(this, "Enter current password.");
                            data[5] = curpass.trim();
                            data[6] = admdata.get(1);
                            Registry re;
                            try {
                                re = LocateRegistry.getRegistry(9111);
                                ServerRMIInf rm = (ServerRMIInf) re.lookup("ChangeAdmin");

                                String res = rm.ChangeAdm(data);
                                {
                                    JOptionPane.showMessageDialog(this, res);

                                }

                            } catch (RemoteException | NotBoundException ex) {
                                ex.printStackTrace();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }

                    } else {

                        JOptionPane.showMessageDialog(this, "New Password and Confirm Password Doesnt match.");
                    }
                }
            }
        }
    }

    class EditAdmin extends JPanel implements ActionListener {

        JLabel MHead, SelToChg, NewVal, pass;
        String[] admData = {"Email", "Mobile", "Password"};
        JComboBox Cdat;
        JTextField Data;
        JPasswordField Tpassw, VTpassw;
        JPanel centerPanel, middlePanel;
        JButton Submit;

        public EditAdmin(String pico) {

            MHead = new JLabel("Edit Admin");
            MHead.setFont(new Font("Old English Text MT", Font.BOLD, 35));
            MHead.setSize(200, 200);

            centerPanel = new JPanel();
            centerPanel.setOpaque(false);

            GridLayout gl = new GridLayout(1, 3);
            centerPanel.setLayout(gl);
            gl.setHgap(50);
            gl.setVgap(50);
            add(centerPanel, BorderLayout.NORTH);
            // centerPanel.setPreferredSize(new Dimension(900, 100));
            centerPanel.add(new JLabel());

            centerPanel.add(MHead);
            centerPanel.add(new JLabel());
            centerPanel.setBorder(BorderFactory.createEmptyBorder());
            SelToChg = new JLabel("Select to change:");
            SelToChg.setFont(tit);
            Cdat = new JComboBox(admData);
            Cdat.setFont(lab);

            NewVal = new JLabel("New Value:");
            NewVal.setFont(lab);
            Data = new JTextField();
            Data.setDocument(new JTextFieldLimit(30));
            pass = new JLabel("Enter current password:");
            pass.setFont(lab);
            Tpassw = new JPasswordField();
            Submit = new JButton("Submit");
            Submit.setFont(lab);
            middlePanel = new JPanel();
            middlePanel.setOpaque(false);

            GridLayout gl1 = new GridLayout(6, 4);
            middlePanel.setLayout(gl1);
            gl1.setHgap(30);
            gl1.setVgap(30);
            middlePanel.setBorder(
                    BorderFactory.createEmptyBorder());
            //      middlePanel.setPreferredSize(new Dimension(800, 350));
            middlePanel.add(new JLabel());
            middlePanel.add(new JLabel());
            middlePanel.add(new JLabel());
            middlePanel.add(new JLabel());
            middlePanel.add(new JLabel());
            middlePanel.add(SelToChg);

            middlePanel.add(Cdat);
            middlePanel.add(new JLabel());
            middlePanel.add(new JLabel());
            middlePanel.add(NewVal);
            middlePanel.add(Data);
            middlePanel.add(new JLabel());
            middlePanel.add(new JLabel());
            middlePanel.add(pass);
            middlePanel.add(Tpassw);
            middlePanel.add(new JLabel());
            middlePanel.add(new JLabel());
            middlePanel.add(new JLabel());
            middlePanel.add(new JLabel());
            middlePanel.add(new JLabel());
            middlePanel.add(new JLabel());
            middlePanel.add(Submit);

            add(middlePanel, BorderLayout.SOUTH);
            Submit.addActionListener(this);
            try {
                image = ImageIO.read(new File(pico));
                w = image.getWidth();
                h = image.getHeight();

            } catch (IOException ioe) {
                System.out.println("Could not read in the pic");
                //System.exit(0);
            }
        }

        public Dimension getPreferredSize() {
            return new Dimension(w * 2, h * 2);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int errcount = 0;
            if (Data.getText().toString().length() == 0 || Tpassw.getText().length() == 0) {
                errcount = 1;
                JOptionPane.showMessageDialog(this, "All fields are mandatory.");
            } else {
                if (e.getSource() == Submit) {
                    String selec, newselec, passs;
                    selec = Cdat.getSelectedItem().toString();
                    newselec = Data.getText().trim();
                    passs = Tpassw.getText().trim();
                    if (Cdat.getSelectedItem().toString().equals("Email")) {
                        if (errcount != 1 && !emailValidator.validate(newselec)) {
                            JOptionPane.showMessageDialog(this, "Invalid Email.");
                            errcount = 1;
                        }
                    }
                    if (Cdat.getSelectedItem().toString().equals("Mobile")) {
                        long mobno;
                        if (errcount != 1) {
                            try {
                                mobno = Long.parseLong(newselec);
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(this, "Invalid mobile number.");
                                errcount = 1;
                            }
                        }

                    }

                    if (errcount != 1) {

                        Registry re;
                        try {
                            re = LocateRegistry.getRegistry(9111);
                            ServerRMIInf rm = (ServerRMIInf) re.lookup("ChangeAdminDetails");

                            String res = rm.ChgAdmDet(selec, newselec, passs, admdata.get(1));
                            {
                                JOptionPane.showMessageDialog(this, res);

                            }

                        } catch (RemoteException | NotBoundException ex) {
                            ex.printStackTrace();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    class SendUsrCreden extends JPanel implements ActionListener, PopupMenuListener {

        JLabel MHead, Student, Teacher, select, selectcldep, Staff, Vpass;
        JRadioButton tea, stu, stf;
        ButtonGroup tsfbg;
        JComboBox Cdat;
        JTextField Data;
        JPasswordField Tpassw;
        JPanel centerPanel, middlePanel;
        JButton submit;

        public SendUsrCreden(String pico) {

            MHead = new JLabel("Sending User Credentials");
            MHead.setFont(new Font("Old English Text MT", Font.BOLD, 35));
            MHead.setSize(200, 200);

            centerPanel = new JPanel();
            centerPanel.setOpaque(false);

            GridLayout gl = new GridLayout(1, 3);
            centerPanel.setLayout(gl);
            gl.setHgap(50);
            gl.setVgap(50);
            add(centerPanel, BorderLayout.NORTH);
            centerPanel.setBorder(
                    BorderFactory.createEmptyBorder());;
            // centerPanel.setPreferredSize(new Dimension(900, 100));
            centerPanel.add(new JLabel());

            centerPanel.add(MHead);
            centerPanel.add(new JLabel());

            select = new JLabel("Select to Send:");
            select.setFont(tit);
            tea = new JRadioButton("Teacher");
            tea.setFont(lab);
            stu = new JRadioButton("Student");
            stu.setFont(lab);
            stf = new JRadioButton("Staff");
            stf.setFont(lab);
            tsfbg = new ButtonGroup();
            tsfbg.add(tea);
            tsfbg.add(stu);
            tsfbg.add(stf);
            selectcldep = new JLabel("Select Dept./Class:");
            selectcldep.setFont(lab);
            Cdat = new JComboBox();
            Cdat.setFont(lab);
            Vpass = new JLabel("Enter current password:");
            Vpass.setFont(lab);
            Tpassw = new JPasswordField();
            submit = new JButton("Send");
            submit.setFont(lab);
            middlePanel = new JPanel();
            middlePanel.setOpaque(false);

            GridLayout gl1 = new GridLayout(6, 4);
            middlePanel.setLayout(gl1);
            gl1.setHgap(5);
            gl1.setVgap(30);

            middlePanel.setBorder(
                    BorderFactory.createEmptyBorder());
            //      middlePanel.setPreferredSize(new Dimension(800, 350));
            middlePanel.add(new JLabel());
            middlePanel.add(new JLabel());
            middlePanel.add(new JLabel());
            middlePanel.add(new JLabel());
            middlePanel.add(select);
            middlePanel.add(tea);
            middlePanel.add(stu);
            middlePanel.add(stf);
            middlePanel.add(new JLabel());
            middlePanel.add(selectcldep);
            middlePanel.add(Cdat);
            middlePanel.add(new JLabel());
            middlePanel.add(new JLabel());
            middlePanel.add(Vpass);
            middlePanel.add(Tpassw);
            middlePanel.add(new JLabel());
            middlePanel.add(new JLabel());
            middlePanel.add(new JLabel());
            middlePanel.add(new JLabel());
            middlePanel.add(new JLabel());
            middlePanel.add(new JLabel());

            middlePanel.add(submit);

            add(middlePanel, BorderLayout.SOUTH);
            submit.addActionListener(this);
            Cdat.addPopupMenuListener(this);
            tea.addActionListener(this);
            stu.addActionListener(this);
            stf.addActionListener(this);
            try {
                image = ImageIO.read(new File(pico));
                w = image.getWidth();
                h = image.getHeight();

            } catch (IOException ioe) {
                System.out.println("Could not read in the pic");
                //System.exit(0);
            }
        }

        public Dimension getPreferredSize() {
            return new Dimension(w * 2, h * 2);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int errcount = 0;

            if (e.getSource() == submit) {
                if (Tpassw.getText().trim().length() == 0) {
                    errcount = 1;
                    JOptionPane.showMessageDialog(this, "Please enter password.");
                }
                String mem = null, ran = null, passs;
                if (Cdat.getSelectedItem() != null) {
                    ran = Cdat.getSelectedItem().toString();
                    JOptionPane.showMessageDialog(this, "Please select course");
                    errcount = 1;
                }
                if (tea.isSelected()) {
                    mem = "teacher";

                } else if (stu.isSelected()) {
                    mem = "student";

                } else if (stf.isSelected()) {
                    mem = "staff";
                    ran = "none";
                }

                passs = Tpassw.getText().trim();
                if (errcount != 1) {
                    Registry re;
                    try {
                        re = LocateRegistry.getRegistry(9111);
                        ServerRMIInf rm = (ServerRMIInf) re.lookup("SendingUserCred");

                        String res = rm.SendingUserCred(mem, ran, passs, admdata.get(1));
                        {
                            JOptionPane.showMessageDialog(this, res);

                        }

                    } catch (RemoteException | NotBoundException ex) {
                        ex.printStackTrace();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }

        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            if (e.getSource() == Cdat) {
                if (tea.isSelected()) {
                    try {
                        Cdat.removeAll();
                        Registry re;

                        re = LocateRegistry.getRegistry(9111);
                        ServerRMIInf rm = (ServerRMIInf) re.lookup("ShowDept");
                        ArrayList<String> res = new ArrayList<String>();
                        res = rm.ShowDept("depts");
                        String[] rest = new String[res.size()];
                        DefaultComboBoxModel listModel = new DefaultComboBoxModel();
                        for (int i = 0; i < res.size(); i++) {
                            listModel.addElement(res.get(i));

                        }
                        Cdat.setModel(listModel);
                    } catch (Exception ex) {

                    }
                } else if (stu.isSelected()) {
                    Cdat.removeAllItems();
                    Registry re;
                    try {

                        re = LocateRegistry.getRegistry(9111);
                        ServerRMIInf rm = (ServerRMIInf) re.lookup("ShowClasses");
                        ArrayList<String> res = new ArrayList<String>();
                        res = rm.ShowClass("class");
                        String[] rest = new String[res.size()];

                        for (int i = 0; i < res.size(); i++) {
                            Cdat.addItem(res.get(i));

                        }

                    } catch (RemoteException | NotBoundException ex) {
                        ex.printStackTrace();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

        }

        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
        }

        @Override
        public void popupMenuCanceled(PopupMenuEvent e) {
            Cdat.hidePopup();
        }

    }

    class EmailValidator {

        private Pattern pattern;
        private Matcher matcher;

        private static final String EMAIL_PATTERN
                = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        public EmailValidator() {
            pattern = Pattern.compile(EMAIL_PATTERN);
        }

        /**
         * Validate hex with regular expression
         *
         * @param hex hex for validation
         * @return true valid hex, false invalid hex
         */
        public boolean validate(final String hex) {

            matcher = pattern.matcher(hex);
            return matcher.matches();

        }
    }
}
