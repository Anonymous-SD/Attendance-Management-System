/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attenmansys;

/**
 *
 * @author Anonymous
 */
import attenmansys.Serv.ServerRMIInf;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.UIManager;

import de.javasoft.plaf.synthetica.SyntheticaPlainLookAndFeel;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class Login extends JFrame implements ActionListener {

    String type;
    Registry re;
    JLabel unl, pwdl, log, typ;
    JTextField untf;
    JPasswordField pwdtf;
    JButton subb, frgt;
    JSeparator jstl, jstr, jsb;
    JDesktopPane dp;
    ForgotPasswordForm fpf;
    JComboBox tp;

    public Login() throws Exception {

        //Font
        Font lab = new Font("Times New Roman", Font.BOLD, 20);
        Font tf = new Font("Times New Roman", Font.PLAIN, 18);

        //Frame
        setTitle("Attendance Management Login");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, screenSize.width, screenSize.height);
        setVisible(true);
        setLayout(null);
        Container con = this.getContentPane();
        setExtendedState(this.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension((int) (screenSize.width / 1.2), (int) (screenSize.height / 1.5)));

        //Components
        jstl = new JSeparator(SwingConstants.HORIZONTAL);
        jstr = new JSeparator(SwingConstants.HORIZONTAL);
        jsb = new JSeparator(SwingConstants.HORIZONTAL);

        String[] typedata = {"admin", "teacher", "student", "staff"};
        log = new JLabel("LOGIN");
        unl = new JLabel("User ID:");
        pwdl = new JLabel("Password:");
        untf = new JTextField();
        pwdtf = new JPasswordField();
        subb = new JButton("Login");
        frgt = new JButton("Forgot Password?");
        typ = new JLabel("Login type:");
        tp = new JComboBox(typedata);

        frgt.setToolTipText("Click if you have forgot your password");

        jstl.setBounds((int) (screenSize.width / (2.7)), (int) (screenSize.height / 6.3), 150, 100);
        jstl.setBackground(Color.red);
        log.setBounds((int) (screenSize.width / (2.1)), (int) (screenSize.height / 4.3), 150, 35);
        log.setFont(lab);
        jstr.setBounds((int) (screenSize.width / (1.85)), (int) (screenSize.height / 6.3), 150, 100);
        jstr.setBackground(Color.red);
        unl.setBounds((int) (screenSize.width / (2.42)), screenSize.height / 3, 200, 30);
        unl.setFont(lab);
        untf.setBounds((int) (screenSize.width / (2)), screenSize.height / 3, 200, 30);
        untf.setFont(tf);
        untf.setBorder(BorderFactory.createLoweredBevelBorder());
        pwdl.setBounds((int) (screenSize.width / (2.42)), (int) (screenSize.height / 2.4), 200, 30);
        pwdl.setFont(lab);
        pwdtf.setBounds((int) (screenSize.width / (2)), (int) (screenSize.height / 2.4), 200, 30);
        pwdtf.setFont(tf);
        pwdtf.setBorder(BorderFactory.createLoweredBevelBorder());

        typ.setBounds((int) (screenSize.width / (2.42)), (int) (screenSize.height / 2), 200, 30);
        typ.setFont(lab);
        tp.setBounds((int) (screenSize.width / (2)), (int) (screenSize.height / 2), 200, 30);
        tp.setFont(tf);

        subb.setBounds((int) (screenSize.width / 2.44), (int) (screenSize.height / 1.75), 100, 35);
        subb.setFont(lab);
        frgt.setBounds((int) (screenSize.width / 2), (int) (screenSize.height / 1.75), 200, 35);
        frgt.setFont(lab);
        jsb.setBounds((int) (screenSize.width / (2.7)), (int) (screenSize.height / 1.7), 500, 100);
        jsb.setBackground(Color.red);
        pwdtf.setDocument(new JTextFieldLimit(15));
        untf.setDocument(new JTextFieldLimit(15));
        //add Components
       
        con.add(jstl);
        con.add(log);
        con.add(unl);
        con.add(untf);
        con.add(pwdl);
        con.add(pwdtf);
        con.add(subb);
        con.add(frgt);
        con.add(jstr);
        con.add(jsb);
        con.add(typ);
        con.add(tp);

        //listener 
        subb.addActionListener(this);
        frgt.addActionListener(this);

        this.getRootPane().addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                jstl.setLocation((int) (con.getWidth() / (2.7)), (int) (con.getHeight() / 6.3));
                jstl.setSize((int) (con.getWidth() / 10), 100);
                log.setLocation((int) (con.getWidth() / ((2.05))), (int) (con.getHeight() / 4.3));
                jsb.setLocation((int) (con.getWidth() / (2.7)), (int) (con.getHeight() / 1.7));
                jsb.setSize((int) (con.getWidth() / 3.5), 100);
                jstr.setLocation((int) (con.getWidth() / (1.85)), (int) (con.getHeight() / 6.3));
                jstr.setSize((int) (con.getWidth() / 10), 100);
                unl.setLocation((int) (con.getWidth() / (2.42)), con.getHeight() / 3);
                untf.setLocation((int) (con.getWidth() / (2)), con.getHeight() / 3);
                pwdl.setLocation((int) (con.getWidth() / (2.42)), (int) (con.getHeight() / 2.4));
                pwdtf.setLocation((int) (con.getWidth() / (2)), (int) (con.getHeight() / 2.4));

                typ.setLocation((int) (con.getWidth() / (2.42)), (int) (con.getHeight() / 2));
                tp.setLocation((int) (con.getWidth() / (2)), (int) (con.getHeight() / 2));

                subb.setLocation((int) (con.getWidth() / 2.44), (int) (con.getHeight() / 1.75));
                frgt.setLocation((int) (con.getWidth() / 2), (int) (con.getHeight() / 1.75));
            }
        });

        //refresh
        SwingUtilities.updateComponentTreeUI(this);
    }

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(new SyntheticaPlainLookAndFeel());
        new Login();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == subb) {
            if (untf.getText().isEmpty() && pwdtf.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this.getRootPane(), "Please enter user id and password");
            } else if (untf.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this.getRootPane(), "Please enter user id");
            } else if (pwdtf.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this.getRootPane(), "Please enter password");
            } else {

                type = tp.getSelectedItem().toString();

                try {
                    re = LocateRegistry.getRegistry(9111);
                    ServerRMIInf rm;

                    rm = (ServerRMIInf) re.lookup("loginveri");
                    ArrayList<String> tdata = new ArrayList<String>();
                    tdata = rm.updverify((String) untf.getText().trim(), (String) pwdtf.getText().trim(), type);

                    if (!"invalid".equals(tdata.get(0))) {
                        JOptionPane.showMessageDialog(this, "Welcome, " + tdata.get(2));
                    }
                    switch (tdata.get(0)) {
                        case "admin":
                            AdminHome hm = new AdminHome(tdata);
                            // JOptionPane.showMessageDialog(this.getRootPane(),"welcome admin");
                            this.dispose();
                            break;
                        case "teacher":
                            TeacherHome th = new TeacherHome(tdata);
                            //  JOptionPane.showMessageDialog(this.getRootPane(),"welcome teacher");
                            this.dispose();
                            break;
                        case "staff":
                            StaffHome stfh = new StaffHome(tdata);
                            // JOptionPane.showMessageDialog(this.getRootPane(),"welcome staff");
                            this.dispose();
                            break;
                        case "student":
                            StudentHome stnh = new StudentHome(tdata);
                            //  JOptionPane.showMessageDialog(this.getRootPane(),"welcome student");
                            this.dispose();
                            break;
                        case "invalid":
                            JOptionPane.showMessageDialog(this.getRootPane(), "Please enter valid credentials.");
                            break;
                        default:
                            break;
                    }
                } catch (NotBoundException | AccessException ex) {

                } catch (RemoteException ex) {

                } catch (Exception ex) {

                }
            }
        } else if (e.getSource() == frgt) {

            new ForgotPasswordForm();
        }
    }
}

class JTextFieldLimit extends PlainDocument {

    private int limit;

    JTextFieldLimit(int limit) {
        super();
        this.limit = limit;
    }

    JTextFieldLimit(int limit, boolean upper) {
        super();
        this.limit = limit;
    }

    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        if (str == null) {
            return;
        }

        if ((getLength() + str.length()) <= limit) {
            super.insertString(offset, str, attr);
        }
    }
}
