/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attenmansys;

import attenmansys.Serv.ServerRMIInf;
import de.javasoft.plaf.synthetica.SyntheticaPlainLookAndFeel;
import java.awt.Color;
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
import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class StaffHome extends JFrame {
JButton MarSubSubmit;
    JPanel rightPanel;
    JPanel leftPanel;
    Dimension screenSize;
    JLabel wc;
    String AttenText;
    JSplitPane sp;
    JTabbedPane tp;
Timer time;
Document doc;
    int wid, hei;
    private BufferedImage image;
ArrayList<String> StfData;
    JButton ManStu, ManTch;

    Font lab = new Font("Times New Roman", Font.BOLD, 20);
    Font labs = new Font("Times New Roman", Font.PLAIN, 20);
    Font labb = new Font("Times New Roman", Font.BOLD, 25);
    Font tit = new Font("Times New Roman", Font.BOLD, 22);

    public StaffHome(ArrayList Stfdata) {
        this.StfData=Stfdata;
//frame
        setTitle("Staff Home");
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
        tp.addTab("Attendance", new StaffHome.Atten());

       

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
    public void dispos()
    {
    this.dispose();
    }
    public static void main(String[] abc) {
        try {

            UIManager.setLookAndFeel(new SyntheticaPlainLookAndFeel());
        } catch (ParseException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        ArrayList ar = null;
        new StaffHome(ar);

    }

    class Atten extends JPanel implements ActionListener {

        JButton MTA;

        public Atten() {

            setLayout(null);

            MTA = new JButton("Mark Attendance");
            MTA.setFont(lab);

            MTA.setBounds(wid / 9, hei / 6, 200, 40);
            MTA.addActionListener(this);
            add(MTA);

            addComponentListener(new ComponentAdapter() {
                public void componentResized(ComponentEvent e) {

                    MTA.setLocation(wid / 9, hei / 6);
                    sp.setDividerLocation(wid / 3);
                }
            });
            SwingUtilities.updateComponentTreeUI(this);

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == MTA) {
                sp.setRightComponent(new MarkTeaAtten("C:\\AMSRF\\other\\222.jpg"));
            }
        }
    }

  

    class MarkTeaAtten extends JPanel implements ActionListener {

        JButton gtd;
        private int w, h;
        JLabel MHead, ScndId;
        JTable MarTD;
        JScrollPane scp;
        JTextField tf1, tfDt;
        JButton ToDb;
        SimpleDateFormat df;
        String[] THead = {"No.", "User Id", "First Name", "Last Name", "Department", "Id", "in time", "out time"};
        String[][] dat;
        DefaultTableModel dtm;
        int cn = 1;

        public MarkTeaAtten(String pico) {
            setLayout(null);
            MHead = new JLabel("Mark Teacher Attendance");

            MHead.setFont(new Font("Old English Text MT", Font.BOLD, 35));
            add(MHead);
            MHead.setBounds((int) (wid / 5.5), hei / 7, 700, 30);

            tfDt = new JTextField();
            gtd = new JButton("Display Teacher Data");

            ScndId = new JLabel("User Id:");
            ScndId.setFont(lab);
            ScndId.setBounds((int) (wid / 5), (int) (hei / 1.5), 200, 30);
            add(ScndId);
            MarTD = new JTable();
            dtm = new DefaultTableModel(0, 0);
            dtm.setColumnIdentifiers(THead);
            MarTD.setModel(dtm);
            tf1 = new JTextField();
            tf1.setBounds((int) (wid / 3.9), (int) (hei / 1.5), 200, 30);
            add(tf1);
            tf1.setFont(lab);
            MarTD.setEnabled(false);
            tf1.setDocument(new JTextFieldLimit(20)); 
    tf1.getDocument().addDocumentListener(new TimeDocListenerStf());
      
            MarSubSubmit = new JButton("Submit");
            MarSubSubmit.setFont(lab);
            MarSubSubmit.setBounds((int) (wid / 5), (int) (hei / 1.3), 180, 30);
MarSubSubmit.setVisible(false);
            ToDb = new JButton("Save List");
            ToDb.setBounds((int) (wid / 2.9), (int) (hei / 1.3), 180, 30);
            ToDb.setFont(lab);
            ToDb.addActionListener(this);
            add(ToDb);
            
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
           MarTD.getColumnModel().getColumn(1).setMinWidth(0);
            MarTD.getColumnModel().getColumn(1).setMaxWidth(0);
           

            scp.setSize(new Dimension(780, 250));
            scp.setLocation(wid / 28, (int) (hei / 2.2));
            

            scp = new JScrollPane(MarTD);
            scp.setSize(new Dimension(600, 250));
            scp.setLocation(wid / 9, hei / 4);
            add(scp);
            add(MarSubSubmit);
            SwingUtilities.updateComponentTreeUI(this);
            MarSubSubmit.addActionListener(this);

            Registry re = null;
            try {
                re = LocateRegistry.getRegistry(9111);

                ServerRMIInf rm = (ServerRMIInf) re.lookup("GetDate");

                tfDt.setText("Date: " + rm.GetDate());

            }  catch (Exception ex) {
    JOptionPane.showMessageDialog(this.getRootPane(),"Please check your connection and try again.");
                
            }

            tfDt.setFont(lab);
            tfDt.setEnabled(false);
            tfDt.setBounds(wid / 9, (int) (hei / 5), 180, 35);
            add(tfDt);

            gtd.setFont(lab);
            gtd.setBounds(wid / 3, (int) (hei / 5), 250, 35);
            add(gtd);

            gtd.addActionListener(this);
            //  MarTD.setBounds((int) (wid / 5), hei / 2, 600, 30);
            //   Submit = new JButton("Submit");
            // add(Submit);
            //   Submit.addActionListener(this);
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

            if (e.getSource() == gtd) {
                 if(dtm.getRowCount()!=0)
                  {
                  JOptionPane.showMessageDialog(this, "Teacher data is already loaded.");
                  }
                  else{
                Registry rea;
                try {
                    rea = LocateRegistry.getRegistry(9111);
                    ServerRMIInf rms = (ServerRMIInf) rea.lookup("GetTeachData");
                    ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
                    res = rms.GetTeachData();
                    int rasct = res.size();
                    System.out.println(rasct);
                    for (int rmsct = 0; rmsct < rasct; rmsct++) {
                        int no = rmsct + 1;
                        dtm.addRow(new Object[]{String.valueOf(no), res.get(rmsct).get(0), res.get(rmsct).get(1), res.get(rmsct).get(2), res.get(rmsct).get(3), res.get(rmsct).get(4), "-", "-"});
                        System.out.println(dtm.getValueAt(0, 0));
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(StaffHome.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NotBoundException ex) {
                    Logger.getLogger(StaffHome.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(StaffHome.class.getName()).log(Level.SEVERE, null, ex);
                }
                 }
            }
            if (e.getSource() == MarSubSubmit) {
                int ct = dtm.getRowCount();
                for (int i = 0; i < ct; i++) {
                    if (tf1.getText().trim().equals(dtm.getValueAt(i, 1))) {

                        if (dtm.getValueAt(i, 6).equals("-")) {
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
                            dtm.setValueAt(tm, i, 6);
                        } else if (dtm.getValueAt(i, 7).equals("-")) {
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
                           
                        } else {
                            JOptionPane.showMessageDialog(this, "Attendance already marked");
                        }
                    }
                   
                }
            }
tf1.setText("");
            if (e.getSource() == ToDb) {
                     if (dtm.getRowCount() > 0) {
                DefaultTableModel dtm = (DefaultTableModel) MarTD.getModel();
                int nRow = dtm.getRowCount(), nCol = dtm.getColumnCount();
                String[][] tableData = new String[nRow][nCol];
                for (int i = 0; i < nRow; i++) {
                    for (int j = 0; j < nCol; j++) {

                        tableData[i][j] = (String) dtm.getValueAt(i, j);

                    }
                     if (tableData[i][6].equals("-")) {
                            tableData[i][6] = "00:00:00";
                            tableData[i][7] = "00:00:00";
                        } else if ((!tableData[i][6].equals("-")) && (tableData[i][7].equals("-"))) {
                            tableData[i][6] = "00:00:00";
                            tableData[i][7] = "00:00:00";

                        }
                }

                String Stat;
                Registry re;
                try {
                    re = LocateRegistry.getRegistry(9111);
                    ServerRMIInf rm = (ServerRMIInf) re.lookup("TeaAttenToDatabase");
                    Stat = rm.TeaAttenToDatabase(tableData, tfDt.getText());
                    System.out.println(Stat);
                    if (Stat.equals("Successful")) {
                        JOptionPane.showMessageDialog(this, "Teacher successfully added.");
                    } else if (Stat.equals("Unuccessful")) {
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
                     else{JOptionPane.showMessageDialog(this,"No available records to submit.");}
            }
        }
        
    } class TimeDocListenerStf implements DocumentListener {

        public TimeDocListenerStf() {
            time=new Timer(400,new ActionListener(){
                @Override       public void actionPerformed(ActionEvent e) {
                    
                    
                    if(doc!=null)
            {
            try{AttenText=doc.getText(0, doc.getLength());
         
               System.out.println(AttenText);
               
               MarSubSubmit.doClick();
              
            }           catch (BadLocationException ex) {
                     
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
         set(e);    }

        @Override
        public void changedUpdate(DocumentEvent e) {
        }  
            private void set(DocumentEvent e)
            {
               if(time.isRunning())
               {
               time.restart();
               }
                else
               {
               doc=e.getDocument();
               time.start();
               }
                
            }

       
      
    }

}
