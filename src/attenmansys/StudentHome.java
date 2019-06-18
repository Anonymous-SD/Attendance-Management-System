/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attenmansys;

import attenmansys.Serv.ServerRMIInf;
import de.javasoft.plaf.synthetica.SyntheticaBlueLightLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaPlainLookAndFeel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
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
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

/**
 *
 * @author Hydramist
 */
public class StudentHome extends JFrame implements MenuListener {

    JMenuBar mb1;
    JMenu logot;

    Dimension screenSize;
    JSplitPane sp;
    JTabbedPane tp;
    private BufferedImage image;
    private int w, h;
    JPanel leftPanel, rightPanel;
    ArrayList<String> sdata;
    JButton ManStu, ManTch;
    int wid, hei;
    Font lab = new Font("Times New Roman", Font.BOLD, 20);
    Font labs = new Font("Times New Roman", Font.PLAIN, 20);
    Font labb = new Font("Times New Roman", Font.BOLD, 25);
    Font tit = new Font("Times New Roman", Font.BOLD, 22);

    public StudentHome(ArrayList Studata) {
        //frame

        mb1 = new JMenuBar();
        setJMenuBar(mb1);
        logot = new JMenu("Logout");
        mb1.add(logot);
        logot.addMenuListener(this);
        sdata = Studata;
        setTitle("Student Home");
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, screenSize.width, screenSize.height);
        setVisible(true);

        //panel
        leftPanel = new JPanel();
        leftPanel.setOpaque(true);
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5));
        leftPanel.setLayout(new BorderLayout(5, 5));

        leftPanel.setBackground(new Color(174, 226, 249));
        
        rightPanel = new JPanel();
        rightPanel.setBackground(new Color(174, 226, 249));
        rightPanel.setLayout(null);

        //tabbed
        tp = new JTabbedPane();
        tp.setFont(lab);
        tp.addTab("Attendance", new SAtten());

      
 JScrollPane s = new JScrollPane(tp, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            add(s);

        //   Container con=this.getContentPane();
        setExtendedState(this.MAXIMIZED_BOTH);
        // setMinimumSize(new Dimension((int) (screenSize.width / 1.5), (int) (screenSize.height / 1.5)));
        //Components
        setSize((int) (screenSize.width / 1.5), (int) (screenSize.height / 1.5));
        sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, s, rightPanel);

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
        new StudentHome(ar);

    }

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void menuCanceled(MenuEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
    private class SAtten extends JPanel implements ActionListener {

        JButton vsa, vsr, vsd;
        JPanel centerPanel;

        public SAtten() {

            centerPanel = new JPanel();
            centerPanel.setOpaque(true);
            centerPanel.setBackground(Color.darkGray);
            GridLayout gl = new GridLayout(5, 1);
            centerPanel.setLayout(gl);
            gl.setHgap(50);
            gl.setVgap(50);

            centerPanel.setBorder(
                    BorderFactory.createTitledBorder("Check your Attendance"));
            centerPanel.setPreferredSize(new Dimension(350, 550));

            vsa = new JButton("View Attendance");
            vsr = new JButton("View Report");
            vsd = new JButton("View Defaulters");

            vsa.setFont(lab);
            vsr.setFont(lab);
            vsd.setFont(lab);

            vsa.setSize(200, 40);
            vsr.setSize(200, 40);
            vsd.setSize(200, 40);
            centerPanel.add(vsa);
            centerPanel.add(vsr);
            centerPanel.add(vsd);

           
            add(centerPanel, BorderLayout.CENTER);
            SwingUtilities.updateComponentTreeUI(this);

            vsa.addActionListener(this);
            vsr.addActionListener(this);
            vsd.addActionListener(this);

            addComponentListener(new ComponentAdapter() {
                public void componentResized(ComponentEvent e) {

                }
            });
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == vsa) {
                sp.setRightComponent(new ViewStuAttend("C:\\AMSRF\\other\\222.jpg"));
            }
            if (e.getSource() == vsr) {
                sp.setRightComponent(new ViewStuAttRepStu("C:\\AMSRF\\other\\222.jpg"));
            }
            if (e.getSource() == vsd) {
             sp.setRightComponent(new ViewStuDefRepStu("C:\\AMSRF\\other\\222.jpg"));
            }
        }

    }

  

    class ViewStuAttend extends JPanel implements ActionListener {

        JLabel MHead, FrmDte, ToDte;
        JScrollPane scp;
        JButton Submit;
        JComboBox Attyp, Ccls, Ccour;
        JTextField TotTf;
        JTable MarTD;

        DefaultTableModel dtm;
        DateFormat df;
        JDatePanelImpl datePanel, datePanel1;
        UtilDateModel model, model1;
        Properties p;
        JDatePickerImpl datepicker, datepicker1;
        String[] THead = {"No.", "User Id", "First Name", "Last Name", "Teacher Dept.", "Teacher Name", "date", "Attendance"};

        public ViewStuAttend(String pico) {

            setLayout(null);
            MHead = new JLabel("View Attendance");
            MHead.setFont(new Font("Old English Text MT", Font.BOLD, 35));
            add(MHead);
            MHead.setBounds((int) (wid / 5), hei / 8, 600, 30);
            MarTD = new JTable();
            dtm = new DefaultTableModel(0, 0);
            dtm.setColumnIdentifiers(THead);
            MarTD.setModel(dtm);
            MarTD.setEnabled(false);

            TotTf = new JTextField();
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

            FrmDte.setBounds((int) (wid / 10), (int) (hei / 4), 150, 40);
            datepicker.setBounds((int) (wid / 6), (int) (hei / 4), 200, 30);
            ToDte.setBounds((int) (wid / 2.9), (int) (hei / 4), 150, 40);
            datepicker1.setBounds((int) (wid / 2.5), (int) (hei / 4), 200, 30);
            Submit.setBounds((int) (wid / 5), (int) (hei / 3), 150, 40);
            Submit.setFont(lab);

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
           
           

            scp.setSize(new Dimension(780, 250));
            scp.setLocation(wid / 28, (int) (hei / 2.2));

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
            if (e.getSource() == Submit) {int errcount=0;
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
                            ServerRMIInf rm = (ServerRMIInf) re.lookup("CheckStuOwnAtten");

                            String[][] rest = rm.CheckStuOwnAtten(selectedDate, selectedDate1, sdata.get(1).toString());
                            int act;
                            for (int restct = 0; restct < rest.length; restct++) {
                                act = restct + 1;
                                dtm.addRow(new Object[]{String.valueOf(act), rest[restct][0], rest[restct][1], rest[restct][2], rest[restct][3], rest[restct][4], rest[restct][5], rest[restct][6]});
                            }

                        } catch (RemoteException | NotBoundException ex) {
                            ex.printStackTrace();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }}}}
                }

            }
        }

    }
     class ViewStuAttRepStu extends JPanel implements ActionListener,ListSelectionListener{    
         String mn, yr, dp;
        JLabel MHead, rwsel;
        JTable MarTD;
        DefaultTableModel dtm;
        JScrollPane scp;
        JButton Save;
        byte[] bt;
        String Teaid,cls;
        JFileChooser fc;

        public ViewStuAttRepStu(String pico) {
                 setLayout(null);

            MHead = new JLabel("View Attendance Report");

            MHead.setFont(new Font("Old English Text MT", Font.BOLD, 35));
            add(MHead);
            MHead.setBounds((int) (wid / 6), hei / 7, 700, 30);
            
            
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
           dtm.setColumnIdentifiers(new Object[]{"Teacher Id","Teacher Name","month", "year", "class","from_dte","to_dte"});

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
                ServerRMIInf rm = (ServerRMIInf) re.lookup("getStuAttRepDataSt");

                String[][] rest = rm.getStuAttRepDataSt(sdata.get(1),sdata.get(4));

                if (rest == null) {
                    JOptionPane.showMessageDialog(this, "No Report Found.");
                } else {
                    // System.out.println(rest[0][0]);
                    for (int ct = 0; ct < rest.length; ct++) {
                        dtm.addRow(new Object[]{rest[ct][0], rest[ct][1], rest[ct][2], rest[ct][3], rest[ct][4],rest[ct][5],rest[ct][6]});
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

                    sp.setDividerLocation(wid / 3);
                }
            });
            
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
          if (e.getSource() == Save) {
              if(mn!=null)
              {
                Registry re;
                try {

                    re = LocateRegistry.getRegistry(9111);
                    ServerRMIInf rm = (ServerRMIInf) re.lookup("getStuAttRepFileTe");

                    bt = rm.getStuAttRepFileTe(Teaid,mn, yr, cls);
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
            }else{JOptionPane.showMessageDialog(this,"No record selected.");}   }
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
              Teaid = MarTD.getValueAt(MarTD.getSelectedRow(), 0).toString();
            mn = MarTD.getValueAt(MarTD.getSelectedRow(), 2).toString();
            yr = MarTD.getValueAt(MarTD.getSelectedRow(), 3).toString();
            cls = MarTD.getValueAt(MarTD.getSelectedRow(), 4).toString();
        }
    }

    class ViewStuDefRepStu extends JPanel implements ListSelectionListener,ActionListener{
            
            String mn, yr, dp;
        JLabel MHead, rwsel;
        JTable MarTD;
        DefaultTableModel dtm;
        JScrollPane scp;
        JButton Save;
        byte[] bt;
        String Teaid,cls;
        JFileChooser fc;

        public ViewStuDefRepStu(String pico) {
               setLayout(null);

            MHead = new JLabel("View Defaulters' Report");

            MHead.setFont(new Font("Old English Text MT", Font.BOLD, 35));
            add(MHead);
            MHead.setBounds((int) (wid / 6), hei / 7, 700, 30);
            
            
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
           dtm.setColumnIdentifiers(new Object[]{"Teacher Id","Teacher Name","month", "year", "class","from_dte","to_dte"});

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
                ServerRMIInf rm = (ServerRMIInf) re.lookup("getStuDefRepDataSt");

                String[][] rest = rm.getStuDefRepDataSt(sdata.get(1),sdata.get(4));

                if (rest == null) {
                    JOptionPane.showMessageDialog(this, "No Report Found.");
                } else {
                    // System.out.println(rest[0][0]);
                    for (int ct = 0; ct < rest.length; ct++) {
                        dtm.addRow(new Object[]{rest[ct][0], rest[ct][1], rest[ct][2], rest[ct][3], rest[ct][4],rest[ct][5],rest[ct][6]});
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
            
            
            
            
            
            
            addComponentListener(new ComponentAdapter() {
                public void componentResized(ComponentEvent e) {

                    sp.setDividerLocation(wid / 3);
                }
            });
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
        public void valueChanged(ListSelectionEvent e) {
              Teaid = MarTD.getValueAt(MarTD.getSelectedRow(), 0).toString();
            mn = MarTD.getValueAt(MarTD.getSelectedRow(), 2).toString();
            yr = MarTD.getValueAt(MarTD.getSelectedRow(), 3).toString();
            cls = MarTD.getValueAt(MarTD.getSelectedRow(), 4).toString();
              }

        @Override
        public void actionPerformed(ActionEvent e) {
             if (e.getSource() == Save) {
                 if(mn!=null)
                 {
                Registry re;
                try {

                    re = LocateRegistry.getRegistry(9111);
                    ServerRMIInf rm = (ServerRMIInf) re.lookup("getGenDeRepTe");

                    bt = rm.getGenDeRepTe(Teaid,mn, yr, cls);
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
            } else{JOptionPane.showMessageDialog(this,"no record selected.");}}
         }
    }

}
