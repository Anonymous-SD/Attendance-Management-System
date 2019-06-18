/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attenmansys;


import attenmansys.Serv.ServerRMIInf;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


class ForgotPasswordForm extends JFrame implements ActionListener

{       
     String[] typedata = {"admin", "teacher", "student", "staff"};
      JComboBox tp;
    JLabel emlab;
    JTextField emtf;
   JButton emsub;
  public ForgotPasswordForm()
    {
         //Font
        Font lab = new Font("Times New Roman", Font.BOLD, 20);
        Font tf = new Font("Times New Roman", Font.PLAIN, 18);
         //Frame
         setSize(1200,600);
        setTitle("Forgot Password");
        setVisible(true);
         setLayout(null);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);    
        
            
          Dimension SS=Toolkit.getDefaultToolkit().getScreenSize();
      tp = new JComboBox(typedata);
        emlab=new JLabel("Enter User id:");
        emlab.setFont(lab);
        emlab.setBounds(SS.width/8,SS.height/7,250,35);
   
        emtf=new JTextField();
        emtf.setBounds(SS.width/5,SS.height/7,250,35);
        emtf.setFont(tf);
        
        emsub=new JButton("Submit");
        emsub.setFont(lab);
        emsub.setBounds(SS.width/6,SS.height/5,150,35);
        emsub.addActionListener(this);
        tp.setBounds((int)(SS.width/2.9),SS.height/7,150,35);
        add(emlab);
        add(emtf);
        add(emsub);
        add(tp);
        emtf.setDocument(new JTextFieldLimit(15));
        SwingUtilities.updateComponentTreeUI(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        
        String emm=emtf.getText().trim();
        if(emtf.getText().trim().isEmpty())
        {
        JOptionPane.showMessageDialog(this, "User Id field must not be empty.");
        }
        else
        {if(e.getSource()==emsub)
        {
            Registry re;
            try {
                re = LocateRegistry.getRegistry(9111);
                ServerRMIInf rm=(ServerRMIInf)re.lookup("empass");
                JOptionPane.showMessageDialog(this.getRootPane(),rm.Pwdverify(emm,tp.getSelectedItem().toString()));
            } catch (RemoteException|NotBoundException ex) 
            {  ex.printStackTrace();
            } catch (Exception ex) {
               ex.printStackTrace();
            }
            
        }}
    }
    
}
