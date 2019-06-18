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
import java.awt.Component;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
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
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class TeacherHome extends JFrame implements MenuListener {

    JButton MarSubSubmit;
    JMenuBar mb1;
    JMenu logot;
    String AttenText;
    private BufferedImage image;
    private int w, h;
    ArrayList<String> ALTdata;
    JPanel leftPanel, rightPanel;
    Dimension screenSize;
    JSplitPane sp;
    JTabbedPane tp;
    JButton ManStu, ManTch;
    int wid, hei;
    Font lab = new Font("Times New Roman", Font.BOLD, 20);
    Font labs = new Font("Times New Roman", Font.PLAIN, 20);
    Font labb = new Font("Times New Roman", Font.BOLD, 25);
    Font tit = new Font("Times New Roman", Font.BOLD, 22);
    Timer time;
    Document doc;

    public TeacherHome(ArrayList dat) {
        //frame
        mb1 = new JMenuBar();
        setJMenuBar(mb1);
        logot = new JMenu("Logout");
        mb1.add(logot);
        logot.addMenuListener(this);
        ALTdata = dat;

        setTitle("Teacher Home");
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, screenSize.width, screenSize.height);
        setVisible(true);

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
        tp.addTab("Attendance", new Teach());
        tp.addTab("Manage Attendance", new Attend());

        //   Container con=this.getContentPane();
        setExtendedState(this.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension((int) (screenSize.width / 1.5), (int) (screenSize.height / 1.5)));
        setSize((int) (screenSize.width / 1.5), (int) (screenSize.height / 1.5));
        //Components
        sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        sp.setLeftComponent(tp);
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
        TeacherHome teacherHome = new TeacherHome(ar);
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

    private class Teach extends JPanel implements ActionListener {

        JButton editT;

        public Teach() {

            setLayout(null);
            editT = new JButton("Mark Attendance");

            editT.setFont(lab);

            editT.setBounds(wid / 9, hei / 6, 200, 40);

            add(editT);
            editT.addActionListener(this);
            SwingUtilities.updateComponentTreeUI(this);

            addComponentListener(new ComponentAdapter() {
                public void componentResized(ComponentEvent e) {

                    editT.setLocation(wid / 9, hei / 6);
                    sp.setDividerLocation(wid / 3);
                }
            });

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == editT) {
                sp.setRightComponent(new MarkStuAtten("C:\\AMSRF\\other\\222.jpg"));

            }
        }

    }

    private class Attend extends JPanel implements ActionListener {

        JButton VTAtt, SAttR, VTR, VSAtt, VGenSAttR, GSDef, VDef;
        JLabel Teach, Stu;

        public Attend() {

            setLayout(null);
            Teach = new JLabel("<html><body><u>Teacher</u></body></html>");
            Teach.setBounds(wid / 9, hei / 9, 200, 50);
            Teach.setFont(labb);

            Stu = new JLabel("<html><body><u>Student</u></body></html>");
            Stu.setBounds(wid / 9, hei / 3, 200, 50);
            Stu.setFont(labb);

            //Teach
            VTAtt = new JButton("View Attendance");
            VTR = new JButton("View Reports");
            VTAtt.setFont(lab);
            VTR.setFont(lab);

            //Stu
            VSAtt = new JButton("Check Attendance");
            SAttR = new JButton("Generate Report");
            VGenSAttR = new JButton("View Reports");
            GSDef = new JButton("Generate Defaulters");
            VDef = new JButton("View Defaulters");

            VSAtt.setFont(lab);
            SAttR.setFont(lab);
            VGenSAttR.setFont(lab);
            GSDef.setFont(lab);
            VDef.setFont(lab);

            VTAtt.setBounds(wid / 9, hei / 6, 200, 40);
            VTR.setBounds(wid / 9, hei / 5, 200, 40);

            VSAtt.setBounds(wid / 9, (int) (hei / 2.5), 250, 40);
            SAttR.setBounds(wid / 9, (int) (hei / 2.1), 250, 40);
            VGenSAttR.setBounds(wid / 9, (int) (hei / 1.8), 250, 40);
            GSDef.setBounds(wid / 9, (int) (hei / 1.57), 250, 40);
            VDef.setBounds(wid / 9, (int) (hei / 1.4), 250, 40);

            add(Teach);
            add(VTAtt);
            add(VTR);

            add(Stu);
            add(VSAtt);
            add(SAttR);
            add(VGenSAttR);
            add(GSDef);
            add(VDef);

            VTAtt.addActionListener(this);
            VTR.addActionListener(this);
            VSAtt.addActionListener(this);
            SAttR.addActionListener(this);
            VGenSAttR.addActionListener(this);
            GSDef.addActionListener(this);
            VDef.addActionListener(this);
            SwingUtilities.updateComponentTreeUI(this);

            addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    Teach.setLocation(wid / 9, hei / 9);

                    VTAtt.setLocation(wid / 9, hei / 6);
                    VTR.setLocation(wid / 9, hei / 4);

                    VSAtt.setLocation(wid / 9, (int) (hei / 2.5));
                    SAttR.setLocation(wid / 9, (int) (hei / 2.1));
                    VGenSAttR.setLocation(wid / 9, (int) (hei / 1.8));
                    GSDef.setLocation(wid / 9, (int) (hei / 1.57));
                    VDef.setLocation(wid / 9, (int) (hei / 1.4));

                    Teach.setLocation(wid / 9, hei / 9);
                    Stu.setLocation(wid / 9, hei / 3);
                    sp.setDividerLocation(wid / 3);
                }
            });

        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == VTAtt) {
                sp.setRightComponent(new ViewTeaAtten("C:\\AMSRF\\other\\222.jpg"));
            }
            if (e.getSource() == VSAtt) {
                sp.setRightComponent(new ViewStuAtten("C:\\AMSRF\\other\\222.jpg"));
            }
            if (e.getSource() == VTR) {
                sp.setRightComponent(new ViewTeacherReport("C:\\AMSRF\\other\\222.jpg"));
            }
            if (e.getSource() == SAttR) {
                sp.setRightComponent(new GenStuAttRep("C:\\AMSRF\\other\\222.jpg"));

            }
            if (e.getSource() == VGenSAttR) {
                sp.setRightComponent(new ViewStuAttRep("C:\\AMSRF\\other\\222.jpg"));

            }
            if (e.getSource() == GSDef) {
                sp.setRightComponent(new GenStuDef("C:\\AMSRF\\other\\222.jpg"));

            }
            if (e.getSource() == VDef) {
                sp.setRightComponent(new ViewStuDef("C:\\AMSRF\\other\\222.jpg"));
            }
        }

    }

    class MarkStuAtten extends JPanel implements ActionListener, PopupMenuListener {

        int i = 1;

        JScrollPane scp;
        JLabel MHead, Lclas, Lcour;
        JComboBox Ccls, Ccour;
        JButton SaveToDb;
        JTextField usTf, tfDt;
        JTable MarTD;
        JButton gsd;
        int cn = 1;
        DefaultTableModel dtm;
        String[] THead = {"No.", "User Id", "First Name", "Last Name", "Class", "Course", "Roll no.", "in time", "out time"};

        public MarkStuAtten(String pik) {
            setLayout(null);

            MHead = new JLabel("Mark Student Attendance");

            MHead.setFont(new Font("Old English Text MT", Font.BOLD, 35));
            add(MHead);
            MHead.setBounds((int) (wid / 5.5), hei / 7, 700, 30);

            Lclas = new JLabel("Class:");
            Lcour = new JLabel("Course:");

            Ccls = new JComboBox();
            Ccour = new JComboBox();

            MarTD = new JTable();
            dtm = new DefaultTableModel(0, 0);
            dtm.setColumnIdentifiers(THead);
            MarTD.setModel(dtm);
            MarTD.setEnabled(false);

            tfDt = new JTextField();
            gsd = new JButton("Get Student data");
            Registry re = null;
            scp = new JScrollPane(MarTD, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            MarTD.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            TableColumn column = MarTD.getColumnModel().getColumn(0);
            column.setPreferredWidth(130);
            column = MarTD.getColumnModel().getColumn(1);
            column.setPreferredWidth(130);
            column = MarTD.getColumnModel().getColumn(2);
            column.setPreferredWidth(130);
            column = MarTD.getColumnModel().getColumn(3);
            column.setPreferredWidth(130);
            column = MarTD.getColumnModel().getColumn(4);
            column.setPreferredWidth(130);
            column = MarTD.getColumnModel().getColumn(5);
            column.setPreferredWidth(130);
            column = MarTD.getColumnModel().getColumn(6);
            column.setPreferredWidth(130);
            column = MarTD.getColumnModel().getColumn(7);
            column.setPreferredWidth(130);
            column = MarTD.getColumnModel().getColumn(8);
            column.setPreferredWidth(130);
            MarTD.getColumnModel().getColumn(1).setMinWidth(0);
            MarTD.getColumnModel().getColumn(1).setMaxWidth(0);
            scp.setSize(new Dimension(780, 250));
            scp.setLocation(wid / 28, (int) (hei / 2.2));

            try {
                re = LocateRegistry.getRegistry(9111);

                ServerRMIInf rm = (ServerRMIInf) re.lookup("GetDate");

                tfDt.setText("Date: " + rm.GetDate());

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this.getRootPane(), "Please check your connection and try again.");
                
            }
            tfDt.setFont(lab);
            tfDt.setEnabled(false);

            MarSubSubmit = new JButton("Submit");
            MarSubSubmit.setFont(lab);
            SaveToDb = new JButton("Save List");

            usTf = new JTextField();
            usTf.setDocument(new JTextFieldLimit(20)); 
           scp = new JScrollPane(MarTD);

            scp.setSize(new Dimension(650, 250));
            scp.setLocation(wid / 9, (int) (hei / 2.5));

            add(scp);
            add(tfDt);
            SwingUtilities.updateComponentTreeUI(this);

            Lclas.setFont(lab);
            Lcour.setFont(lab);
            SaveToDb.setFont(lab);
            gsd.setFont(lab);

            tfDt.setBounds(wid / 9, (int) (hei / 3), 180, 35);
            gsd.setBounds(wid / 3, (int) (hei / 3), 250, 35);
            Lclas.setBounds(wid / 10, hei / 4, 150, 35);
            Ccls.setBounds(wid / 6, hei / 4, 200, 35);
            Lcour.setBounds(wid / 3, hei / 4, 150, 35);
            Ccour.setBounds((int) (wid / 2.5), hei / 4, 200, 35);
            MarSubSubmit.setBounds((int) (wid / 6), (int) (hei / 1.2), 200, 35);
            usTf.setBounds((int) (wid / 6), (int) (hei / 1.3), 200, 35);
            SaveToDb.setBounds((int) (wid / 3), (int) (hei / 1.2), 200, 35);
            add(Lclas);
            add(Lcour);
            add(Ccls);
            add(Ccour);
            add(MarSubSubmit);
            add(usTf);
            add(SaveToDb);
            add(gsd);
            Ccls.addPopupMenuListener(this);
            Ccour.addPopupMenuListener(this);
            MarSubSubmit.addActionListener(this);
            MarSubSubmit.setVisible(false);
            SaveToDb.addActionListener(this);
            gsd.addActionListener(this);
            usTf.getDocument().addDocumentListener(new TimeDocListener());

            try {
                image = ImageIO.read(new File(pik));
                w = image.getWidth();
                h = image.getHeight();

            } catch (IOException ioe) {
                System.out.println("Could not read in the pic");
                //System.exit(0);
            }
            addComponentListener(new ComponentAdapter() {
                public void componentResized(ComponentEvent e) {
                    MHead.setLocation((int) (wid / 5.5), hei / 7);
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
            if (e.getSource() == gsd) {
                int errcount = 0;
                if (Ccls.getSelectedItem() == null) {
                    errcount = 1;
                    JOptionPane.showMessageDialog(this, "Please select class.");
                }
                if (errcount != 1 && Ccour.getSelectedItem() == null) {
                    errcount = 1;
                    JOptionPane.showMessageDialog(this, "Please select course.");
                }

                if (dtm.getRowCount() != 0) {
                    JOptionPane.showMessageDialog(this, "Student data is already loaded.");
                } else {
                    if (errcount != 1) {
                        Registry re;
                        try {

                            re = LocateRegistry.getRegistry(9111);
                            ServerRMIInf rm = (ServerRMIInf) re.lookup("getStuData");
                            ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
                            res = rm.getStuData(Ccls.getSelectedItem().toString(), Ccour.getSelectedItem().toString());
                            if (res.size() != 0) {
                                int rasct = res.size();
                                System.out.println(rasct);
                                for (int rmsct = 0; rmsct < rasct; rmsct++) {
                                    int no = rmsct + 1;
                                    dtm.addRow(new Object[]{String.valueOf(no), res.get(rmsct).get(0), res.get(rmsct).get(1), res.get(rmsct).get(2), res.get(rmsct).get(3), Ccour.getSelectedItem().toString(), res.get(rmsct).get(5), "-", "-"});
                                    System.out.println(dtm.getValueAt(0, 0));
                                }
                            } else {
                                JOptionPane.showMessageDialog(this.getRootPane(), "No record found.");
                            }
                        } catch (RemoteException | NotBoundException ex) {
                            ex.printStackTrace();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }

            if (e.getSource() == MarSubSubmit) {

                int ct = dtm.getRowCount();
                for (int i = 0; i < ct; i++) {
                    if (usTf.getText().trim().equals(dtm.getValueAt(i, 1))) {

                        if (dtm.getValueAt(i, 7).equals("-")) {
                            Registry re = null;
                            String tm = null;

                            try {
                                re = LocateRegistry.getRegistry(9111);

                                ServerRMIInf rm = (ServerRMIInf) re.lookup("GetTime");

                                tm = rm.GetTime();

                            } catch (RemoteException ex) {

                            } catch (NotBoundException ex) {

                            } catch (Exception ex) {

                            }
                            dtm.setValueAt(tm, i, 7);
                        } else if (dtm.getValueAt(i, 8).equals("-")) {
                            Registry re = null;
                            String tm = null;

                            try {
                                re = LocateRegistry.getRegistry(9111);

                                ServerRMIInf rm = (ServerRMIInf) re.lookup("GetTime");

                                tm = rm.GetTime();

                            } catch (RemoteException ex) {

                            } catch (NotBoundException ex) {

                            } catch (Exception ex) {

                            }
                            dtm.setValueAt(tm, i, 8);

                        } else {
                            JOptionPane.showMessageDialog(this, "Attendance already marked");
                        }
                    }

                }

            }
            usTf.setText("");

            if (e.getSource() == SaveToDb) {
                if (dtm.getRowCount() > 0) {
                    String pass = JOptionPane.showInputDialog(this.getRootPane(), "Enter your password.");

                    dtm = (DefaultTableModel) MarTD.getModel();
                    int nRow = dtm.getRowCount(), nCol = dtm.getColumnCount();
                    String[][] tableData = new String[nRow][nCol];
                    for (int i = 0; i < nRow; i++) {
                        for (int j = 0; j < nCol; j++) {

                            tableData[i][j] = (String) dtm.getValueAt(i, j);

                        }
                        if (tableData[i][7].equals("-")) {
                            tableData[i][7] = "00:00:00";
                            tableData[i][8] = "00:00:00";
                        } else if ((!tableData[i][7].equals("-")) && (tableData[i][8].equals("-"))) {
                            tableData[i][7] = "00:00:00";
                            tableData[i][8] = "00:00:00";

                        }
                    }

                    String Stat;
                    Registry re;
                    try {

                        re = LocateRegistry.getRegistry(9111);
                        ServerRMIInf rm = (ServerRMIInf) re.lookup("StuAttenToDatabase");
                        Stat = rm.StuAttenToDatabase(tableData, tfDt.getText(), ALTdata.get(1), pass);
                        System.out.println(Stat);

                        JOptionPane.showMessageDialog(this, Stat);

                    } catch (RemoteException ex) {
                        Logger.getLogger(AdminHome.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (NotBoundException ex) {
                        Logger.getLogger(AdminHome.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(AdminHome.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    JOptionPane.showMessageDialog(this, "No records found to be submitted.");
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

                    DefaultListModel listModel = new DefaultListModel();
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
                if (Ccls.getSelectedItem() != null) {
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
                } else {
                    JOptionPane.showMessageDialog(this, "Select Class First.");
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

    public class ViewTeaAtten extends JPanel implements ActionListener {

        JLabel MHead, FrmDte, ToDte;
        JScrollPane scp;
        JButton Submit;
        JComboBox Attyp, Ccls, Ccour;
        
        JTable MarTD;

        DefaultTableModel dtm;
        DateFormat df;
        JDatePanelImpl datePanel, datePanel1;
        UtilDateModel model, model1;
        Properties p;
        JDatePickerImpl datepicker, datepicker1;
        String[] THead = {"No.", "User Id", "First Name", "Last Name", "Dept.", "date", "Attendance"};

        public ViewTeaAtten(String pico) {
            setLayout(null);
            MHead = new JLabel("View Attendance");

            MHead.setFont(new Font("Old English Text MT", Font.BOLD, 35));
            add(MHead);
            MHead.setBounds((int) (wid / 4.5), hei / 7, 700, 30);

            MarTD = new JTable();
            dtm = new DefaultTableModel(0, 0);
            dtm.setColumnIdentifiers(THead);
            MarTD.setModel(dtm);
            MarTD.setEnabled(false);

           
            
            Submit = new JButton("Submit");

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

            scp = new JScrollPane(MarTD, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            MarTD.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            TableColumn column = MarTD.getColumnModel().getColumn(0);
            column.setPreferredWidth(130);
            column = MarTD.getColumnModel().getColumn(1);
            column.setPreferredWidth(130);
            column = MarTD.getColumnModel().getColumn(2);
            column.setPreferredWidth(130);
            column = MarTD.getColumnModel().getColumn(3);
            column.setPreferredWidth(130);
            column = MarTD.getColumnModel().getColumn(4);
            column.setPreferredWidth(130);
            column = MarTD.getColumnModel().getColumn(5);
            column.setPreferredWidth(130);
            scp = new JScrollPane(MarTD, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            MarTD.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

            column = MarTD.getColumnModel().getColumn(6);
            column.setPreferredWidth(130);

            FrmDte.setBounds((int) (wid / 10), (int) (hei / 4), 150, 40);
            datepicker.setBounds((int) (wid / 6), (int) (hei / 4), 200, 30);
            ToDte.setBounds((int) (wid / 2.9), (int) (hei / 4), 150, 40);
            datepicker1.setBounds((int) (wid / 2.5), (int) (hei / 4), 200, 30);
            Submit.setBounds((int) (wid / 5), (int) (hei / 3), 150, 40);
            Submit.setFont(lab);

            scp = new JScrollPane(MarTD);

            scp.setSize(new Dimension(700, 250));
            scp.setLocation(wid / 12, (int) (hei / 2.2));

            add(scp);
            add(Submit);

            Submit.addActionListener(this);

            add(FrmDte);
            add(ToDte);

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
                    MHead.setLocation((int) (wid / 4.5), hei / 7);
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
            if (e.getSource() == Submit) {
                int errcount=0;
                if (dtm.getRowCount() > 0) {
                    for (int i = dtm.getRowCount() - 1; i > -1; i--) {
                        dtm.removeRow(i);
                    }
                }
                Date selectedDate = (Date) datepicker.getModel().getValue();
                Date selectedDate1 = (Date) datepicker1.getModel().getValue();
                if (selectedDate == null || selectedDate1 == null) {
                    JOptionPane.showMessageDialog(this, "Please select date/s.");
                } else {
                    String ab = null, hd = null;
                    df = new SimpleDateFormat("yyyy/MM/dd");

                    if (selectedDate1.before(selectedDate)) {
                        JOptionPane.showMessageDialog(this, "From date must be older than to date.");
                    }  else {
                             Date currdt = null;
                    try {
                        Registry re = LocateRegistry.getRegistry(9111);

                        ServerRMIInf rm = (ServerRMIInf) re.lookup("GetDate");

                        currdt = new SimpleDateFormat("yy/MM/dd").parse(rm.GetDate());

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Please check your connection.");
                    }
                    if (currdt != null) {
                        if (selectedDate.after(currdt) || selectedDate1.after(currdt)) {
                            JOptionPane.showMessageDialog(this, "dates must be older than current date.");
                        
                        } else {

                            if (errcount != 1) {
                        Registry re;
                        try {

                            re = LocateRegistry.getRegistry(9111);
                            ServerRMIInf rm = (ServerRMIInf) re.lookup("CheckTeaOwnAtten");

                            String[][] rest = rm.CheckTeaOwnAtten(selectedDate, selectedDate1, ALTdata.get(1));
                            int act;
                            for (int restct = 0; restct < rest.length; restct++) {
                                act = restct + 1;
                                dtm.addRow(new Object[]{String.valueOf(act), rest[restct][0], rest[restct][1], rest[restct][2], rest[restct][3], rest[restct][4], rest[restct][5]});
                            }

                        } catch (RemoteException | NotBoundException ex) {
                            ex.printStackTrace();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                    }}}
            }
        }

    }

    class ViewStuAtten extends JPanel implements ActionListener, PopupMenuListener {

        JLabel MHead, FrmDte, ToDte, AtTyp, Enhrdt;
        DateFormat df;  
        int lecper = 0;
        JDatePanelImpl datePanel, datePanel1;
        UtilDateModel model, model1;
        Properties p;
        JDatePickerImpl datepicker, datepicker1;
        JTable tbs;

        String[] StuTable = {"User Id", "First Name", "Last Name", "Roll no.", "Class", "Course", "lecs. Attended", "Out of (lecs)", "lecs(%)", "Hours"};
        String[] tp = {"Above", "Below"};
        JComboBox Attyp, Ccls, Ccour;
        JLabel LCls, LCour, LTeaId, SrchTp;
        JRadioButton Hrs, lec, RTea, RCour;
        ButtonGroup hd, tc;
        JButton sub;
        JTextField hrdys ;

        public ViewStuAtten(String pico) {
            setLayout(null);

            MHead = new JLabel("View Student Attendance");

            MHead.setFont(new Font("Old English Text MT", Font.BOLD, 35));
            add(MHead);
            MHead.setBounds((int) (wid / 7), hei / 7, 700, 30);

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

            Ccls = new JComboBox();

            LCls = new JLabel("Class:");
            LCls.setFont(lab);

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
            hrdys.setDocument(new JTextFieldLimit(4)); 
            sub = new JButton("Submit");
            sub.setFont(lab);
            lec.addActionListener(this);
            FrmDte.setBounds((int) (wid / 10), (int) (hei / 4), 150, 40);
            datepicker.setBounds((int) (wid / 6), (int) (hei / 4), 200, 30);
            ToDte.setBounds((int) (wid / 2.9), (int) (hei / 4), 150, 40);
            datepicker1.setBounds((int) (wid / 2.5), (int) (hei / 4), 200, 30);
            LCls.setBounds((int) (wid / 10), (int) (hei / 3), 200, 35);
            Ccls.setBounds((int) (wid / 6), (int) (hei / 3), 200, 35);

            SrchTp.setBounds((int) (wid / 10), (int) (hei / 2.5), 200, 40);
            Hrs.setBounds((int) (wid / 4), (int) (hei / 2.5), 100, 40);
            lec.setBounds((int) (wid / 3), (int) (hei / 2.5), 140, 40);

            AtTyp.setBounds((int) (wid / 10), (int) (hei / 2), 200, 40);
            Attyp.setBounds((int) (wid / 4), (int) (hei / 2), 150, 30);

            Enhrdt.setBounds((int) (wid / 8.2), (int) (hei / 1.7), 250, 40);
            hrdys.setBounds((int) (wid / 3), (int) (hei / 1.7), 150, 30);
            //    sub.setBounds((int) (wid / 5), (int) (hei / 1.5), 150, 30);
            sub.setBounds((int) (wid / 5), (int) (hei / 1.5), 150, 30);

            add(FrmDte);
            add(datepicker);
            add(ToDte);
            add(datepicker1);
            add(Ccls);

            add(LCls);
            add(AtTyp);
            add(Attyp);

            add(Hrs);
            add(lec);
            add(SrchTp);
            add(Enhrdt);
            add(hrdys);
            add(sub);

            Ccls.addPopupMenuListener(this);
            sub.addActionListener(this);
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

                    sp.setDividerLocation(wid / 3);
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
            if (e.getSource() == sub) {

                int errcount = 0;
                if (Ccls.getSelectedItem() == null) {
                    errcount = 1;
                    JOptionPane.showMessageDialog(this, "Please select a class.");
                }
                if (errcount != 1 && !Hrs.isSelected() && !lec.isSelected()) {
                    errcount = 1;
                    JOptionPane.showMessageDialog(this, "select for hours/lectures");
                }
                if (errcount != 1 && hrdys.getText().trim().length() == 0) {
                    JOptionPane.showMessageDialog(this, "Please enter hours/lectures(%).");
                    errcount = 1;
                } else if (errcount != 1) {
                    try {
                        hrdnum = Integer.parseInt(hrdys.getText().trim());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Enter lectures(%)/hours as a numeric integer value.");
                    }

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
                                if (errcount != 1 && lec.isSelected()) {
                                    hd = "lectures";
                                    if ((hrdnum - 100) > 0 || hrdnum < 0) {
                                        JOptionPane.showMessageDialog(this, "lec % must be between 0 to 100%");
                                        errcount = 1;
                                    }
                                }

                                if (errcount != 1) {
                                    Registry re;
                                    try {

                                        re = LocateRegistry.getRegistry(9111);
                                        ServerRMIInf rm = (ServerRMIInf) re.lookup("CheckStuAtten");
                                        ArrayList<String> ar = new ArrayList<String>();

                                        String[][] rest = rm.CheckStuAttend(selectedDate, selectedDate1, Ccls.getSelectedItem().toString(), "Teacher", ALTdata.get(1), Attyp.getSelectedItem().toString(), hd, hrdys.getText().trim());
                                        // System.out.println(rest[0][0]);

                                        DefaultTableModel modello = new DefaultTableModel(rest, StuTable);
                                        tbs = new JTable(modello);

                                        if (rest != null && rest[0][1] != null) {
                                            CheckStuAtten csa = new CheckStuAtten(tbs);
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
        }

        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

        }

        @Override
        public void popupMenuCanceled(PopupMenuEvent e) {
            Ccls.hidePopup();
        }
    }

    class CheckStuAtten extends JFrame implements ActionListener {

        JTable newtb;
        JButton sub;

        CheckStuAtten(JTable tbd) {
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

        }

    }

    class GenStuAttRep extends JPanel implements ActionListener, PopupMenuListener {

        JLabel MHead, FrmDte, ToDte, ClsL;
        UtilDateModel model, model1;
        JComboBox SCCls;
        JButton Sub;
        JDatePanelImpl datePanel, datePanel1;
        JDatePickerImpl datepicker, datepicker1;
        Properties p;

        public GenStuAttRep(String pico) {
            setLayout(null);

            MHead = new JLabel("Generate Student Attendance Report.");

            MHead.setFont(new Font("Old English Text MT", Font.BOLD, 35));
            add(MHead);
            MHead.setBounds((int) (wid / 8), hei / 7, 700, 30);

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

            ClsL = new JLabel("Select Class:");
            ClsL.setFont(lab);

            SCCls = new JComboBox();
            SCCls.setFont(lab);

            Sub = new JButton("Generate");
            Sub.setFont(lab);
            FrmDte.setBounds((int) (wid / 10), (int) (hei / 4), 150, 40);
            datepicker.setBounds((int) (wid / 6), (int) (hei / 4), 200, 30);
            ToDte.setBounds((int) (wid / 2.9), (int) (hei / 4), 150, 40);
            datepicker1.setBounds((int) (wid / 2.5), (int) (hei / 4), 200, 30);
            ClsL.setBounds((int) (wid / 8), (int) (hei / 2.8), 200, 40);
            SCCls.setBounds((int) (wid / 3.7), (int) (hei / 2.8), 180, 35);
            Sub.setBounds((int) (wid / 5), (int) (hei / 1.5), 200, 35);

            add(FrmDte);
            add(ToDte);
            add(ClsL);
            add(SCCls);
            add(Sub);

            Sub.addActionListener(this);
            SCCls.addPopupMenuListener(this);

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

                    sp.setDividerLocation(wid / 3);
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
            String Sccls = null;
            if (e.getSource() == Sub) {
                int errcount = 0;
                if (SCCls.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(this, "Please select Class.");
                    errcount = 1;
                } else {
                    Sccls = SCCls.getSelectedItem().toString();
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

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Please check your connection.");
                    }
                    if (currdt != null) {
                        if (selectedDate.after(currdt) || selectedDate1.after(currdt)) {
                            JOptionPane.showMessageDialog(this, "dates must be older than current date.");
                        } else {

                            if (errcount != 1) {
                                Registry re;
                                try {

                                    re = LocateRegistry.getRegistry(9111);
                                    ServerRMIInf rm = (ServerRMIInf) re.lookup("GenStuRep");
                                    String res;
                                    res = rm.GenStuRep(selectedDate, selectedDate1, Sccls, ALTdata.get(1));
                                    JOptionPane.showMessageDialog(this, res);

                                } catch (RemoteException ex) {

                                } catch (NotBoundException ex) {

                                } catch (Exception ex) {

                                }
                            }
                        }
                    }
                }
            }
        }

        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            if (e.getSource() == SCCls) {

                SCCls.removeAllItems();
                Registry re;
                try {
                    re = LocateRegistry.getRegistry(9111);
                    ServerRMIInf rm = (ServerRMIInf) re.lookup("ShowClasses");
                    ArrayList<String> res = new ArrayList<String>();
                    res = rm.ShowClass("ShwClass");

                    for (int i = 0; i < res.size(); i++) {
                        SCCls.addItem(res.get(i));
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
            SCCls.hidePopup();
        }
    }

    class ViewStuAttRep extends JPanel implements ActionListener, ListSelectionListener {

        String mn, yr, dp;
        JLabel MHead, rwsel;
        JTable MarTD;
        DefaultTableModel dtm;
        JScrollPane scp;
        JButton Save, delete;
        byte[] bt;
        String Teaid, cls;
        JFileChooser fc;

        public ViewStuAttRep(String pico) {
            setLayout(null);

            MHead = new JLabel("View Student Attendance Report");

            MHead.setFont(new Font("Old English Text MT", Font.BOLD, 35));
            add(MHead);
            MHead.setBounds((int) (wid / 7), hei / 7, 700, 30);

            fc = new JFileChooser();

            fc.addActionListener(this);
            setLayout(null);

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
           dtm.setColumnIdentifiers(new Object[]{"Id", "First name", "month", "year", "class", "from_dte", "to_dte"});

            MarTD.setModel(dtm);

            scp = new JScrollPane(MarTD, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            MarTD.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            TableColumn column = MarTD.getColumnModel().getColumn(0);
            column.setPreferredWidth(150);
            column = MarTD.getColumnModel().getColumn(1);
            column.setPreferredWidth(150);
            column = MarTD.getColumnModel().getColumn(2);
            column.setPreferredWidth(150);
            column = MarTD.getColumnModel().getColumn(3);
            column.setPreferredWidth(150);
            column = MarTD.getColumnModel().getColumn(4);
            column.setPreferredWidth(150);
            column = MarTD.getColumnModel().getColumn(5);
            column.setPreferredWidth(150);
            column = MarTD.getColumnModel().getColumn(6);
            column.setPreferredWidth(150);
            scp.setSize(new Dimension(550, 250));
            scp.setLocation(wid / 9, (int) (hei / 3));

            Registry re;
            try {

                re = LocateRegistry.getRegistry(9111);
                ServerRMIInf rm = (ServerRMIInf) re.lookup("getStuAttRepDataTe");

                String[][] rest = rm.getStuAttRepDataTe(ALTdata.get(5));

                if (rest == null) {
                    JOptionPane.showMessageDialog(this, "No Report Found.");
                } else {
                    // System.out.println(rest[0][0]);
                    for (int ct = 0; ct < rest.length; ct++) {
                        dtm.addRow(new Object[]{rest[ct][0], rest[ct][1], rest[ct][2], rest[ct][3], rest[ct][4], rest[ct][5], rest[ct][6]});
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Please check your connection.");
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

                    sp.setDividerLocation(wid / 3);
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
                        ServerRMIInf rm = (ServerRMIInf) re.lookup("getStuAttRepFileTe");

                        bt = rm.getStuAttRepFileTe(Teaid, mn, yr, cls);
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
                    JOptionPane.showMessageDialog(this, "no record selected.");
                }
            } else if (e.getSource() == delete) {
                if (mn != null) {
                    Registry re;
                    try {

                        re = LocateRegistry.getRegistry(9111);
                        ServerRMIInf rm = (ServerRMIInf) re.lookup("delStuAttRepFileTe");

                        String res = rm.delStuAttRepFileTe(Teaid, mn, yr, cls);

                        JOptionPane.showMessageDialog(this, res);
                        sp.setRightComponent(new ViewStuDef("C:\\AMSRF\\other\\222.jpg"));

                    } catch (RemoteException ex) {
                        Logger.getLogger(AdminHome.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (NotBoundException ex) {
                        Logger.getLogger(AdminHome.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(AdminHome.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No record selected for eletion.");
                }

            }
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {

            Teaid = MarTD.getValueAt(MarTD.getSelectedRow(), 0).toString();

            mn = MarTD.getValueAt(MarTD.getSelectedRow(), 2).toString();
            yr = MarTD.getValueAt(MarTD.getSelectedRow(), 3).toString();
            cls = MarTD.getValueAt(MarTD.getSelectedRow(), 4).toString();

        }
    }

    class GenStuDef extends JPanel implements ActionListener, PopupMenuListener {

        JLabel MHead, FrmDte, ToDte, ClsL, minpercent;
        UtilDateModel model, model1;
        JComboBox SCCls;
        JTextField minperTF;
        int minp;
        JButton Sub;
        JDatePanelImpl datePanel, datePanel1;
        JDatePickerImpl datepicker, datepicker1;
        Properties p;

        public GenStuDef(String pico) {
            setLayout(null);

            MHead = new JLabel("Generate Student Defaulters'");

            MHead.setFont(new Font("Old English Text MT", Font.BOLD, 35));
            add(MHead);
            MHead.setBounds((int) (wid / 7), hei / 7, 700, 30);

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

            ClsL = new JLabel("Select Class:");
            ClsL.setFont(lab);

            SCCls = new JComboBox();
            SCCls.setFont(lab);

            Sub = new JButton("Generate");
            Sub.setFont(lab);

            minpercent = new JLabel("Minimum Attendance(%):");
            minpercent.setFont(lab);

            minperTF = new JTextField();
            minperTF.setDocument(new JTextFieldLimit(3)); 
            FrmDte.setBounds((int) (wid / 10), (int) (hei / 4), 150, 40);
            datepicker.setBounds((int) (wid / 6), (int) (hei / 4), 200, 30);
            ToDte.setBounds((int) (wid / 2.9), (int) (hei / 4), 150, 40);
            datepicker1.setBounds((int) (wid / 2.5), (int) (hei / 4), 200, 30);
            ClsL.setBounds((int) (wid / 8), (int) (hei / 2.8), 200, 40);
            SCCls.setBounds((int) (wid / 3.7), (int) (hei / 2.8), 180, 35);
            Sub.setBounds((int) (wid / 5), (int) (hei / 1.5), 200, 35);
            minpercent.setBounds((int) (wid / 8), (int) (hei / 2.3), 300, 40);
            minperTF.setBounds((int) (wid / 3), (int) (hei / 2.3), 200, 40);

            add(minpercent);
            add(minperTF);
            add(FrmDte);
            add(ToDte);
            add(ClsL);
            add(SCCls);
            add(Sub);

            Sub.addActionListener(this);
            SCCls.addPopupMenuListener(this);

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

                    sp.setDividerLocation(wid / 3);
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
            String Sccls = null;
            if (e.getSource() == Sub) {
                int errcount = 0;
                if (SCCls.getSelectedItem() == null || minperTF.getText() == "") {
                    JOptionPane.showMessageDialog(this, "Please select Class.");
                    errcount = 1;
                } else {
                    Sccls = SCCls.getSelectedItem().toString();
                }

                if (errcount != 1 && minperTF.getText().trim().length() == 0) {
                    JOptionPane.showMessageDialog(this, "please enter minimum percentage required.");
                    errcount = 1;
                } else if (errcount != 1) {
                    try {
                        minp = Integer.parseInt(minperTF.getText().trim());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Value of minimum percentage must be an integer value.");
                        errcount = 1;
                    }
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

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Please check your connection.");
                    }
                    if (currdt != null) {
                        if (selectedDate.after(currdt) || selectedDate1.after(currdt)) {
                            JOptionPane.showMessageDialog(this, "dates must be older than current date.");
                        } else {
                            if (errcount != 1 && (minp - 100) > 0 || minp < 0) {
                                JOptionPane.showMessageDialog(this, "minimum Attendance % value must be between 0 to 100%");
                                errcount = 1;
                            }

                            if (errcount != 1) {

                                Registry re;
                                try {

                                    re = LocateRegistry.getRegistry(9111);
                                    ServerRMIInf rm = (ServerRMIInf) re.lookup("GenStuDefRep");
                                    String res;
                                    res = rm.GenStuDefRep(selectedDate, selectedDate1, Sccls, ALTdata.get(1), minperTF.getText().trim());
                                    JOptionPane.showMessageDialog(this, res);

                                } catch (RemoteException ex) {

                                } catch (NotBoundException ex) {

                                } catch (Exception ex) {
                                }
                            }
                        }

                    }
                }
            }
        }

        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            if (e.getSource() == SCCls) {

                SCCls.removeAllItems();
                Registry re;
                try {
                    re = LocateRegistry.getRegistry(9111);
                    ServerRMIInf rm = (ServerRMIInf) re.lookup("ShowClasses");
                    ArrayList<String> res = new ArrayList<String>();
                    res = rm.ShowClass("ShwClass");

                    for (int i = 0; i < res.size(); i++) {
                        SCCls.addItem(res.get(i));
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
            SCCls.hidePopup();
        }
    }

    class ViewStuDef extends JPanel implements ActionListener, ListSelectionListener {

        String mn, yr, dp;
        JLabel MHead, rwsel;
        JTable MarTD;
        DefaultTableModel dtm;
        JScrollPane scp;
        JButton Save, delete;
        byte[] bt;
        String Teaid, cls;
        JFileChooser fc;

        public ViewStuDef(String pico) {
            setLayout(null);

            MHead = new JLabel("View Student Defaulters' Report");

            MHead.setFont(new Font("Old English Text MT", Font.BOLD, 35));
            add(MHead);
            MHead.setBounds((int) (wid / 7), hei / 7, 700, 30);

            fc = new JFileChooser();

            fc.addActionListener(this);
            setLayout(null);

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
           dtm.setColumnIdentifiers(new Object[]{"Id", "First name", "month", "year", "class", "from(date)", "to(date)"});

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
            column.setPreferredWidth(150);
            column = MarTD.getColumnModel().getColumn(4);
            column.setPreferredWidth(150);
            column = MarTD.getColumnModel().getColumn(5);
            column.setPreferredWidth(150);
            column = MarTD.getColumnModel().getColumn(6);
            column.setPreferredWidth(150);
            scp.setSize(new Dimension(550, 250));
            scp.setLocation(wid / 9, (int) (hei / 3));

            Registry re;
            try {

                re = LocateRegistry.getRegistry(9111);
                ServerRMIInf rm = (ServerRMIInf) re.lookup("getDefStuRepDataTe");

                String[][] rest = rm.getDefStuRepDataTe(ALTdata.get(5));

                if (rest == null) {
                    JOptionPane.showMessageDialog(this, "No Report Found.");
                } else {
                    // System.out.println(rest[0][0]);
                    for (int ct = 0; ct < rest.length; ct++) {
                        dtm.addRow(new Object[]{rest[ct][0], rest[ct][1], rest[ct][2], rest[ct][3], rest[ct][4], rest[ct][5], rest[ct][6]});
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Please check your connection..");
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

                    sp.setDividerLocation(wid / 3);
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
                        ServerRMIInf rm = (ServerRMIInf) re.lookup("getGenDeRepTe");

                        bt = rm.getGenDeRepTe(Teaid, mn, yr, cls);
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
                    JOptionPane.showMessageDialog(this, "no record selected.");
                }
            } else if (e.getSource() == delete) {
                if (mn != null) {
                    Registry re;
                    try {

                        re = LocateRegistry.getRegistry(9111);
                        ServerRMIInf rm = (ServerRMIInf) re.lookup("delGenStuRepTe");

                        String res = rm.delGenStuRepTe(Teaid, mn, yr, cls);

                        JOptionPane.showMessageDialog(this, res);
                        sp.setRightComponent(new ViewStuDef("C:\\AMSRF\\other\\222.jpg"));

                    } catch (RemoteException ex) {
                        Logger.getLogger(AdminHome.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (NotBoundException ex) {
                        Logger.getLogger(AdminHome.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(AdminHome.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "no record selected.");
                }
            }

        }

        @Override
        public void valueChanged(ListSelectionEvent e) {

            Teaid = MarTD.getValueAt(MarTD.getSelectedRow(), 0).toString();

            mn = MarTD.getValueAt(MarTD.getSelectedRow(), 2).toString();
            yr = MarTD.getValueAt(MarTD.getSelectedRow(), 3).toString();
            cls = MarTD.getValueAt(MarTD.getSelectedRow(), 4).toString();

        }
    }

    class ViewTeacherReport extends JPanel implements ActionListener, ListSelectionListener {

        String mn, yr, dp;
        JLabel MHead, rwsel;
        JTable MarTD;
        DefaultTableModel dtm;
        JScrollPane scp;
        JButton Save;
        byte[] bt;
        JFileChooser fc;

        public ViewTeacherReport(String pico) {
            setLayout(null);

            fc = new JFileChooser();

            fc.addActionListener(this);
            setLayout(null);
            MHead = new JLabel("View Teacher Attendance Report");
            MHead.setFont(new Font("Old English Text MT", Font.BOLD, 35));
            add(MHead);
            MHead.setBounds((int) (wid / 9), hei / 8, 600, 30);

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
           dtm.setColumnIdentifiers(new Object[]{"month", "year", "department", "from(date)", "to(date)"});

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
                ServerRMIInf rm = (ServerRMIInf) re.lookup("getTeaOwnRep");
                System.out.println(ALTdata.get(4));
                String[][] rest = rm.getTeaOwnRep(ALTdata.get(4));

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
            Save.setBounds(wid / 5, (int) (hei / 1.3), 250, 35);

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

                    sp.setDividerLocation(wid / 3);
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
                int errcount = 0;
                if (mn == null) {
                    JOptionPane.showMessageDialog(this, "No records selected.");
                    errcount = 1;
                }

                if (errcount != 1) {
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

    class TimeDocListener implements DocumentListener {

        public TimeDocListener() {
            time = new Timer(400, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    if (doc != null) {
                        try {
                            AttenText = doc.getText(0, doc.getLength());

                            System.out.println(AttenText);

                            MarSubSubmit.doClick();
                        } catch (BadLocationException ex) {

                        }

                    }
                }

            });
            time.setRepeats(false);

        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            set(e);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            set(e);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
        }

        private void set(DocumentEvent e) {
            if (time.isRunning()) {
                time.restart();
            } else {
                doc = e.getDocument();
                time.start();
            }

        }

    }

}
