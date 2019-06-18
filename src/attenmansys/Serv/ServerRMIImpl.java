/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attenmansys.Serv;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Anonymous
 */
public class ServerRMIImpl extends UnicastRemoteObject implements ServerRMIInf,Runnable {

    Connection con;
    Thread t1,t2;
    private static String depdat = "";
    private static String sturepclas="";
    private static String stureptean="";
   private static String sturepteadep="";
    private static String[] sturepthrdat=null;
    private static String studefrepclas="";
    private static String studefreptean="";
   private static String studefrepteadep="";
    private static String[] studefrepthrdat=null;

    public ServerRMIImpl() throws Exception {
        super();
    }
    String em, status;
    final String username = "amspassrecovery@gmail.com";
    final String password = "ams@passrec";
    String loginstatus;

    @Override
    public String Pwdverify(String usid, String type) {
        String pwd = "", emal = "";
        int err = 0;
        try {
            System.out.println(usid + type);
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");

            Statement st = con.createStatement();
            Statement st1 = con.createStatement();
            ResultSet rs = null, rs1 = null;
            switch (type) {
                case "staff":
                    //   Statement st=con.createStatement();
                    rs = st.executeQuery("select passwd from login where user_id='" + usid + "'");
                    rs1 = st1.executeQuery("select e_mail from staff where user_id='" + usid + "'");
                    break;
                case "admin":
                    rs = st.executeQuery("select passwd from login where user_id='" + usid + "' and user_t='" + type + "'");
                    rs1 = st1.executeQuery("select e_mail from admin where user_id='" + usid + "'");
                    break;
                case "teacher":
                    rs = st.executeQuery("select passwd from login where user_id='" + usid + "' and user_t='" + type + "'");
                    rs1 = st1.executeQuery("select e_mail from teacher where user_id='" + usid + "'");
                    break;
                case "student":
                    rs = st.executeQuery("select passwd from login where user_id='" + usid + "' and user_t='" + type + "'");
                    rs1 = st1.executeQuery("select e_mail from student where user_id='" + usid + "'");
                    break;
                default:
                    status = "Error occured..";
                    err = 1;
                    break;
            }

            if (rs.next() && rs1.next()) {
                pwd = rs.getString(1);
                emal = rs1.getString(1);
                System.out.println(pwd + " " + emal);
            } else {
                status = "No such User Id.";
                err = 1;
            }
            if (err != 1) {
                Properties props = new Properties();
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.port", "587");

                Session session = Session.getInstance(props,
                        new javax.mail.Authenticator() {

                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("amspassrecovery@gmail.com"));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(emal));
                message.setSubject("User Credentials");
                message.setText("your credentials for logging into attendance managent system are:" + "\n\n User Id: " + usid
                        + "\nPassword: " + pwd);

                Transport.send(message);

                System.out.println("Done");
                status = "Password sent successfully to the email provided.";
            }
        } catch (MessagingException e) {
            status = "error occured.";
            err = 1;
            throw new RuntimeException(e);
        } finally {
            return status;
        }
    }

    @Override
    public ArrayList updverify(String us, String pd, String tp) {
        ArrayList<String> teadata = new ArrayList<String>();
        try {

            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");
            System.out.println("Verifying user id and password...");
            String sql = "select * from login where user_id='" + us + "' and passwd='" + pd + "' and user_t='" + tp + "'";
            try (Statement st = con.createStatement()) {
                ResultSet rs = st.executeQuery(sql);
                if (rs.next()) {
                    System.out.println("verified successfully...");

                    Statement st1 = con.createStatement();
                    switch (tp) {
                        case "teacher": {
                            ResultSet Rs1 = st1.executeQuery("select * from teacher where user_id='" + us + "'");
                            if (Rs1.next()) {

                                teadata.add(tp);
                                teadata.add(Rs1.getString(1));
                                teadata.add(Rs1.getString(2));
                                teadata.add(Rs1.getString(3));
                                teadata.add(Rs1.getString(4));
                                teadata.add(Rs1.getString(5));
                                teadata.add(Rs1.getString(6));
                                teadata.add(String.valueOf(Rs1.getLong(7)));
                            }
                            break;
                        }
                        case "student": {
                            ResultSet Rs1 = st1.executeQuery("select * from student where user_id='" + us + "'");
                            if (Rs1.next()) {

                                teadata.add(tp);
                                teadata.add(Rs1.getString(1));
                                teadata.add(Rs1.getString(2));
                                teadata.add(Rs1.getString(3));
                                teadata.add(Rs1.getString(4));
                                teadata.add(Rs1.getString(5));

                                teadata.add(String.valueOf(Rs1.getInt(6)));
                                teadata.add(Rs1.getString(7));
                            }
                            break;
                        }
                        case "staff": {
                            ResultSet Rs1 = st1.executeQuery("select * from staff where user_id='" + us + "'");
                            if (Rs1.next()) {

                                teadata.add(tp);
                                teadata.add(Rs1.getString(1));
                                teadata.add(Rs1.getString(2));
                                teadata.add(Rs1.getString(3));
                                teadata.add(Rs1.getString(4));
                                teadata.add(String.valueOf(Rs1.getInt(5)));
                                teadata.add(Rs1.getString(6));

                            }
                            break;
                        }
                        case "admin": {
                            ResultSet Rs1 = st1.executeQuery("select * from admin where user_id='" + us + "'");
                            if (Rs1.next()) {

                                teadata.add(tp);
                                teadata.add(Rs1.getString(1));
                                teadata.add(Rs1.getString(2));
                                teadata.add(Rs1.getString(3));
                                teadata.add(Rs1.getString(4));
                                teadata.add(String.valueOf(Rs1.getLong(5)));
                            }
                            break;
                        }
                        default:
                            break;
                    }

                } else {
                    loginstatus = "invalid";

                    System.out.println("invalid credentials...");
                    teadata.add(loginstatus);
                }
                con.close();
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServerRMIImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ServerRMIImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return teadata;
        }
    }
//Show Dept

    @Override
    public ArrayList ShowDept(String depts) {
        ArrayList<String> ar = new ArrayList<String>();
        try {

            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");
            String sql = "select * from departments";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                rs.previous();

                while (rs.next()) {
                    ar.add(rs.getString(2));

                }
                System.out.println("done");
                st.close();
                con.close();
            } else {
                return (null);
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("exception 1");
        } catch (SQLException ex) {
            System.out.println("exception 2");

        }
        return ar;
    }

    //Show Class
    @Override
    public ArrayList ShowClass(String cls) throws Exception {
        ArrayList<String> ar = new ArrayList<String>();
        try {

            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");
            String sql = "select * from classes";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                rs.previous();

                while (rs.next()) {
                    ar.add(rs.getString(2));

                }
                System.out.println("done");
                st.close();
                con.close();

            } else {
                return (null);
            }

        } catch (ClassNotFoundException ex) {
            System.out.println("exception 1");
        } catch (SQLException ex) {
            System.out.println("exception 2");

        }
        return ar;
    }

    //Show Courses
    @Override
    public ArrayList ShowCourses(String ClassOf, String CL) throws Exception {

        ArrayList<String> ar = new ArrayList<String>();

        try {
            if (CL.equals("CourseList")) {
                Class.forName("com.mysql.jdbc.Driver");
                System.out.println("Connecting to a selected database...");
                con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
                System.out.println("Connected database successfully...");
                String sql = "select CourseName from classcourse where ClassName='" + ClassOf + "'";
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql);
                String re;

                while (rs.next()) {
                    re = rs.getString(1);
                    if (re.contains("-")) {
                        String[] spt = re.split("-");
                        for (int co = 0; co < spt.length; co++) {
                            ar.add(spt[co]);
                        }
                    } else {
                        ar.add(re);
                    }

                }

            } else {
                Class.forName("com.mysql.jdbc.Driver");
                System.out.println("Connecting to a selected database...");
                con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
                System.out.println("Connected database successfully...");
                String sql = "select CourseName from classcourse where ClassName='" + ClassOf + "'";
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql);

                while (rs.next()) {
                    ar.add(rs.getString(1));

                }
                System.out.println("done");
                st.close();
                con.close();
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("exception 1");
        } catch (SQLException ex) {
            System.out.println("exception 2");

        }
        return ar;
    }

    //Add Dept
    @Override
    public String AddDept(String addDept) throws Exception {
        String stat = "null";
        try {

            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");
            PreparedStatement ps = con.prepareStatement("insert into departments(Names) values(?)");
            ps.setString(1, addDept);
            ps.executeUpdate();
            stat = "Successful";
        } catch (ClassNotFoundException ex) {
            System.out.println("exception 1");
            stat = "Unsuccessful";
        } catch (SQLException ex) {
            System.out.println("exception 2");
            stat = "Unsuccessful";
        } finally {
            return stat;
        }
    }

    //Remove Dept
    @Override
    public String RemoveDept(String RemDept) throws Exception {
        String stat = "null";
        try {

            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");
            PreparedStatement ps = con.prepareStatement("delete from departments where Names=?");
            ps.setString(1, RemDept);
            ps.executeUpdate();

            stat = "Department removed Successfully";
        } catch (ClassNotFoundException ex) {
            System.out.println("exception 1");
            stat = "Unsuccessful, Please try again.";
        } catch (SQLException ex) {
            System.out.println("exception 2");
            stat = "Unsuccessful, Please try again.";
        } finally {
            return stat;
        }
    }

    //Edit Dept
    @Override
    public String EditDept(String old, String NewVal) throws Exception {
        String stat = "null";
        try {

            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");
            Statement st = con.createStatement();
            st.executeUpdate("update departments set Names='" + NewVal + "' where Names='" + old + "'");

            String sql1 = "update teacher set dept_n='" + NewVal + "' where dept_n='" + old + "'";
            Statement st1 = con.createStatement();
            st1.executeUpdate(sql1);

            String sql2 = "update teacherattendance set dept='" + NewVal + "' where dept='" + old + "'";
            Statement st2 = con.createStatement();
            st2.executeUpdate(sql2);

            String sql3 = "update teacherreport set dept='" + NewVal + "' where dept='" + old + "'";
            Statement st3 = con.createStatement();
            st3.executeUpdate(sql3);

            stat = "Department edited Successfully";
        } catch (ClassNotFoundException ex) {
            System.out.println("exception 1");
            stat = "Unsuccessful, Please try again.";
        } catch (SQLException ex) {
            System.out.println("exception 2");
            stat = "Unsuccessful, Please try again.";
        } finally {
            return stat;
        }
    }

    //Add Class
    @Override
    public String AddClass(String NewClass) throws Exception {
        String stat = "null";
        try {

            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");
            System.out.println("Creating table in given database...");
            Statement stmt = con.createStatement();
            PreparedStatement ps = con.prepareStatement("insert into classes(Names) values(?)");
            ps.setString(1, NewClass);
            ps.executeUpdate();

            System.out.println("Created table in given database...");

            stat = "Class  added Successfully.";
        } catch (ClassNotFoundException ex) {
            System.out.println("exception 1");
            stat = "Unsuccessful,please try again..";
        } catch (SQLException ex) {
            System.out.println("exception 2");
            stat = "Unsuccessful,please try again..";
        } finally {
            return stat;
        }

    }

    //EDIT class
    @Override
    public String EditClass(String ClsOld, String ClassEdt) throws Exception {
        String stat = "null";
        try {

            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");
            PreparedStatement ps = con.prepareStatement("update classes set Names=? where Names=?");
            ps.setString(1, ClassEdt);
            ps.setString(2, ClsOld);
            ps.executeUpdate();
            System.out.println(1);
            String sql = "update classcourse set ClassName='" + ClassEdt + "' where ClassName='" + ClsOld + "'";
            Statement st = con.createStatement();
            st.executeUpdate(sql);
            System.out.println(2);
            String sql1 = "update defaultersreport set class='" + ClassEdt + "' where class='" + ClsOld + "'";
            Statement st1 = con.createStatement();
            st1.executeUpdate(sql1);
            System.out.println(3);
            String sql2 = "update lecturedata set class='" + ClassEdt + "' where class='" + ClsOld + "'";
            Statement st2 = con.createStatement();
            st2.executeUpdate(sql2);
            System.out.println(4);
            String sql3 = "update student set class='" + ClassEdt + "' where class='" + ClsOld + "'";
            Statement st3 = con.createStatement();
            st3.executeUpdate(sql3);
            System.out.println(5);
            String sql4 = "update studentattendance set class='" + ClassEdt + "' where class='" + ClsOld + "'";
            Statement st4 = con.createStatement();
            st4.executeUpdate(sql4);
            System.out.println(6);
            String sql5 = "update studentreport set class='" + ClassEdt + "' where class='" + ClsOld + "'";
            Statement st5 = con.createStatement();
            st5.executeUpdate(sql5);
            System.out.println(7);

            System.out.println("done");
            st.close();
            con.close();

            stat = "Class edited Successfully";
        } catch (ClassNotFoundException ex) {
            System.out.println("exception 1");
            stat = "Unsuccessful";
        } catch (SQLException ex) {
            System.out.println("exception 2");
            stat = "Unsuccessful";
        } finally {
            return stat;
        }
    }

//REMOVE Class    
    @Override
    public String RemoveClass(String RemCls) throws Exception {
        String stat = "null";
        try {

            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");
            PreparedStatement ps = con.prepareStatement("delete from classes where Names=?");
            ps.setString(1, RemCls);
            ps.executeUpdate();
            String Sql = "delete from classcourse where ClassName='" + RemCls + "'";
            Statement st = con.createStatement();
            st.executeUpdate(Sql);

            System.out.println("done");
            ps.close();
            con.close();

            stat = "Successful";
        } catch (ClassNotFoundException ex) {
            System.out.println("exception 1");
            stat = "Unsuccessful";
        } catch (SQLException ex) {
            System.out.println("exception 2");
            stat = "Unsuccessful";
        } finally {
            return stat;
        }
    }
//Add Course

    @Override
    public String AddCourse(String ClsVal, String CourseVal) throws Exception {
        String stat = "null";
        try {

            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");
            PreparedStatement ps = con.prepareStatement("insert into classcourse(ClassName,CourseName)values(?,?)");
            ps.setString(1, ClsVal);
            ps.setString(2, CourseVal);
            ps.executeUpdate();
            stat = "Course added Successfully.";
        } catch (ClassNotFoundException ex) {
            System.out.println("exception 1");
            stat = "Unsuccessful";
        } catch (SQLException ex) {
            System.out.println("exception 2");
            stat = "Unsuccessful";
        } finally {
            return stat;
        }
    }

    @Override
    public String RemoveCourse(String cls, String CourseVal) throws Exception {
        String stat = "null";
        try {

            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");
            PreparedStatement ps = con.prepareStatement("delete from classcourse where CourseName=? and ClassName=?");
            ps.setString(1, CourseVal);
            ps.setString(2, cls);
            ps.executeUpdate();

            System.out.println("done");
            ps.close();
            con.close();

            stat = "Successful";
        } catch (ClassNotFoundException ex) {
            System.out.println("exception 1");
            stat = "Unsuccessful";
        } catch (SQLException ex) {
            System.out.println("exception 2");
            stat = "Unsuccessful";
        } finally {
            return stat;
        }
    }

    @Override
    public String AddSingTeacher(String[] newtb) throws Exception {
        String stat = null;
        try {

            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");

            PreparedStatement ps = con.prepareStatement("insert into teacher(user_id,first_n,last_n,dept_n,teach_id,e_mail,mob_no)values(?,?,?,?,?,?,?)");

            String uuid = UUID.randomUUID().toString();
            String[] ddt = uuid.split("-");

            String usn = "";
            for (int i = 0; i < ddt.length; i++) {
                usn = usn + ddt[i];
            }
            System.out.println(4);
            String Usern = newtb[0].substring(0, 2) + newtb[2].substring(0, 2) + newtb[3].substring(0, 2) + usn;

            Usern = Usern.substring(0, 14);

            ps.setString(1, Usern);
            ps.setString(2, newtb[0]);
            ps.setString(3, newtb[1]);
            ps.setString(4, newtb[2]);
            ps.setString(5, newtb[3]);
            ps.setString(6, newtb[4]);
            ps.setLong(7, Long.valueOf(newtb[5]));
            ps.executeUpdate();

            PreparedStatement ps1 = con.prepareStatement("insert into login values(?,?,?)");

            String pass = UUID.randomUUID().toString();
            String[] ddt1 = uuid.split("-");

            String pswd = "";
            for (int i = 0; i < ddt1.length; i++) {
                pswd = pswd + ddt1[i];
            }

            pswd = pswd.substring(0, 10);

            ps1.setString(1, Usern);
            ps1.setString(2, pswd);
            ps1.setString(3, "teacher");
            ps1.executeUpdate();

            stat = "Successful";

        } catch (ClassNotFoundException ex) {
            System.out.println("Class Exception");
            stat = "Unsuccessful";
        } catch (SQLException ex) {
            System.out.println("SQL Exception");
            stat = "Unsuccessful";
        } finally {
            return stat;
        }
    }

    @Override
    public String AddSingStudent(String[] stuData) throws Exception {
        String stat = null;
        try {

            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");

            PreparedStatement ps = con.prepareStatement("insert into student(user_id,first_n,last_n,class,courses,roll_no,e_mail)values(?,?,?,?,?,?,?)");

            String uuid = UUID.randomUUID().toString();
            String[] ddt = uuid.split("-");

            String usn = "";
            for (int i = 0; i < ddt.length; i++) {
                usn = usn + ddt[i];
            }
            System.out.println(4);
            String Usern = stuData[0].substring(0, 2) + stuData[2].substring(0, 2) + stuData[3].substring(0, 2) + usn;

            Usern = Usern.substring(0, 14);

            ps.setString(1, Usern);
            ps.setString(2, stuData[0]);
            ps.setString(3, stuData[1]);
            ps.setString(4, stuData[2]);
            ps.setString(5, stuData[3]);

            ps.setInt(6, Integer.parseInt(stuData[4]));
            ps.setString(7, stuData[5]);
            ps.executeUpdate();

            PreparedStatement ps1 = con.prepareStatement("insert into login values(?,?,?)");

            String pass = UUID.randomUUID().toString();
            String[] ddt1 = uuid.split("-");

            String pswd = "";
            for (int i = 0; i < ddt1.length; i++) {
                pswd = pswd + ddt1[i];
            }

            pswd = pswd.substring(0, 10);

            ps1.setString(1, Usern);
            ps1.setString(2, pswd);
            ps1.setString(3, "student");
            ps1.executeUpdate();

            stat = "Successful";

        } catch (ClassNotFoundException ex) {
            System.out.println("Class Exception");
            stat = "Unsuccessful";
        } catch (SQLException ex) {
            System.out.println("SQL Exception");
            stat = "Unsuccessful";
        } finally {
            return stat;
        }
    }

    @Override
    public String AddSingStaff(String[] staffData) throws Exception {
        String stat = null;
        try {

            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from staff");

            if (rs.next()) {
                st.executeUpdate("delete from staff");
                st.executeUpdate("delete from login where user_t='staff'");
            }

            PreparedStatement ps = con.prepareStatement("insert into staff(user_id,first_n,last_n,e_mail,Mob_no,Stf_id)values(?,?,?,?,?,?)");

            String uuid = UUID.randomUUID().toString();
            String[] ddt = uuid.split("-");

            String usn = "";
            for (int i = 0; i < ddt.length; i++) {
                usn = usn + ddt[i];
            }
            System.out.println(4);
            String Usern = staffData[0].substring(0, 2) + staffData[3].substring(0, 2) + staffData[4].substring(0, 2) + usn;

            Usern = Usern.substring(0, 14);

            ps.setString(1, Usern);

            ps.setString(2, staffData[0]);
            ps.setString(3, staffData[1]);
            ps.setString(4, staffData[2]);
            ps.setLong(5, Long.valueOf(staffData[3]));
            ps.setString(6, staffData[4]);

            ps.executeUpdate();

            PreparedStatement ps1 = con.prepareStatement("insert into login values(?,?,?)");

            String pass = UUID.randomUUID().toString();
            String[] ddt1 = uuid.split("-");

            String pswd = "";
            for (int i = 0; i < ddt1.length; i++) {
                pswd = pswd + ddt1[i];
            }

            pswd = pswd.substring(0, 10);

            ps1.setString(1, Usern);
            ps1.setString(2, pswd);
            ps1.setString(3, "staff");
            ps1.executeUpdate();

            stat = "Successful";

        } catch (ClassNotFoundException ex) {
            System.out.println("Class Exception");
            stat = "Unsuccessful";
        } catch (SQLException ex) {
            System.out.println("SQL Exception");
            stat = "Unsuccessful";
        } finally {
            return stat;
        }
    }

    @Override
    public String MultiTeaData(String[][] tableData, String dep) throws Exception {
        String stat = "undefined";
        try {

            String[][] data = (String[][]) tableData;
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");
            System.out.println(1);
            PreparedStatement ps = con.prepareStatement("insert into teacher(user_id,first_n,last_n,dept_n,teach_id,e_mail,mob_no) values(?,?,?,?,?,?,?)");

            int rows = tableData.length;
            int columns = tableData[0].length;
            System.out.println(2);
            for (int i = 0; i < rows; i++) {
                System.out.println(3);
                String uuid = UUID.randomUUID().toString();
                String[] ddt = uuid.split("-");

                String usn = "";
                for (int iao = 0; iao < ddt.length; iao++) {
                    usn = usn + ddt[iao];
                }
                System.out.println(4);
                String Usern = data[i][0].substring(0, 2) + data[i][2].substring(0, 2) + data[i][3].substring(0, 2) + usn;

                Usern = Usern.substring(0, 14);

                ps.setString(1, Usern);
                System.out.println(5);
                ps.setString(2, data[i][0]);
                ps.setString(3, data[i][1]);
                ps.setString(4, dep);
                ps.setString(5, data[i][2]);
                ps.setString(6, data[i][3]);
                ps.setLong(7, Long.valueOf(data[i][4]));

                ps.addBatch();
                System.out.println(6);
                PreparedStatement ps1 = con.prepareStatement("insert into login values(?,?,?)");

                String pass = UUID.randomUUID().toString();
                String[] ddt1 = uuid.split("-");

                String pswd = "";
                for (int io = 0; io < ddt1.length; io++) {
                    pswd = pswd + ddt1[io];
                }

                pswd = pswd.substring(0, 10);

                ps1.setString(1, Usern);
                ps1.setString(2, pswd);
                ps1.setString(3, "teacher");
                ps1.executeUpdate();

            }

            ps.executeBatch();

            stat = "Successful";

        } catch (ClassNotFoundException ex) {
            System.out.println("Class Exception");
            stat = "Unsuccessful";
        } catch (SQLException ex) {
            System.out.println("SQL Exception");
            stat = "Unsuccessful";
        } finally {
            return stat;
        }
    }

    @Override

    public String MultiStuData(String[][] tableData, String cls, String course) throws Exception {
        String stat = "undefined";
        try {

            String[][] data = (String[][]) tableData;
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");

            PreparedStatement ps = con.prepareStatement("insert into student(user_id,first_n,last_n,class,courses,roll_no,e_mail) values(?,?,?,?,?,?,?)");

            int rows = tableData.length;
            int columns = tableData[0].length;

            for (int i = 0; i < rows; i++) {
                String uuid = UUID.randomUUID().toString();
                String[] ddt = uuid.split("-");

                String usn = "";
                for (int io = 0; io < ddt.length; io++) {
                    usn = usn + ddt[io];
                }
                System.out.println(4);
                String Usern = data[i][0].substring(0, 2) + cls.substring(0, 2) + course.substring(0, 2) + usn;

                Usern = Usern.substring(0, 14);

                ps.setString(1, Usern);

                ps.setString(2, data[i][0]);
                ps.setString(3, data[i][1]);
                ps.setString(4, cls);
                ps.setString(5, course);
                ps.setInt(6, Integer.parseInt(data[i][2]));
                ps.setString(7, data[i][3]);

                ps.addBatch();
                PreparedStatement ps1 = con.prepareStatement("insert into login values(?,?,?)");

                String pass = UUID.randomUUID().toString();
                String[] ddt1 = uuid.split("-");

                String pswd = "";
                for (int io = 0; io < ddt1.length; io++) {
                    pswd = pswd + ddt1[io];
                }

                pswd = pswd.substring(0, 10);

                ps1.setString(1, Usern);
                ps1.setString(2, pswd);
                ps1.setString(3, "student");
                ps1.executeUpdate();

            }

            ps.executeBatch();

            stat = "Successful";

        } catch (ClassNotFoundException ex) {
            System.out.println("Class Exception");
            stat = "Unsuccessful";
        } catch (SQLException ex) {
            System.out.println("SQL Exception");
            stat = "Unsuccessful";
        } finally {
            return stat;
        }
    }

    @Override
    public String EditMember(String mem, String IDTyp, String iD, String Val, String newVal, String cls) throws Exception {
        String idtyp = IDTyp, id, val = "", val2 = "", newval = "", stat = "", Idtypfinal = "";
        id = iD;
        newval = newVal;
        String sql = "";
        try {
            System.out.println(IDTyp);
            Connection con;
            Statement st = null;
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");
            st = con.createStatement();
            if (mem.equals("a")) {

                if (idtyp.equals("User Id")) {
                    Idtypfinal = "user_id";
                } else if (idtyp.equals("Teacher Id")) {
                    Idtypfinal = "teach_id";
                }
                switch (Val) {
                    case "First Name":
                        val = "first_n";
                        val2 = "f_name";

                        break;
                    case "Last Name":
                        val = "last_n";
                        val2 = "l_name";
                        break;
                    case "Mobile":
                        val = "mob_no";
                        break;
                    case "Email":
                        val = "e_mail";
                        break;
                    default:
                        break;
                }

                sql = "update teacher set " + val + "='" + newval + "' where " + Idtypfinal + "='" + id + "'";
                if (!val2.equals("")) {
                    String nsql = "update teacherattendance set " + val2 + "='" + newval + "' where " + Idtypfinal + "='" + id + "'";
                    Statement teaattst = con.createStatement();
                    teaattst.executeUpdate(nsql);

                }

            } else if (mem.equals("b")) {

                switch (Val) {
                    case "First Name":
                        val = "f_name";
                        break;
                    case "Last Name":
                        val = "l_name";
                        break;
                    case "Roll no":
                        val = "roll_no";
                        break;
                    case "Email":
                        val = "e_mail";
                        break;
                    default:
                        break;
                }
                if (idtyp.equals("User Id")) {
                    Idtypfinal = "user_id";
                    sql = "update student set " + val + "='" + newval + "' where " + Idtypfinal + "='" + id + "'";
                    String nsql = "update studentattendance set " + val + "='" + newval + "' where " + Idtypfinal + "='" + id + "'";
                    Statement teaattst = con.createStatement();
                    teaattst.executeUpdate(nsql);
                } else if (idtyp.equals("Roll no")) {
                    Idtypfinal = "roll_no";
                    sql = "update student set " + val + "='" + newval + "' where " + Idtypfinal + "='" + id + "' and class='" + cls + "'";
                    String nsql = "update studentattendance set " + val + "='" + newval + "' where " + Idtypfinal + "='" + id + "' and class='" + cls + "'";
                    Statement teaattst = con.createStatement();
                    teaattst.executeUpdate(nsql);
                }

            } else if (mem.equals("c")) {

                if (idtyp.equals("User Id")) {
                    Idtypfinal = "user_id";
                } else if (idtyp.equals("Staff Id")) {
                    Idtypfinal = "Stf_id";
                }

                switch (Val) {
                    case "First Name":
                        val = "first_n";
                        break;
                    case "Last Name":
                        val = "last_n";
                        break;
                    case "Email":
                        val = "e_mail";
                        break;
                    case "Mobile":
                        val = "Mob_no";
                        break;
                    default:
                        break;
                }
                sql = "update staff set " + val + "='" + newval + "' where " + Idtypfinal + "='" + id + "'";

            }
            System.out.println(val);
            System.out.println(newval);
            System.out.println(Idtypfinal);
            System.out.println(id);
            System.out.println(sql);

            int rwcoun = st.executeUpdate(sql);
            System.out.println(rwcoun);
            if (rwcoun > 0) {
                stat = "Edited Successfully.";
            } else if (rwcoun == 0) {
                stat = "no data found.";
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("Class Exception");
            stat = "Unsuccessful";
        } catch (SQLException ex) {
            System.out.println("SQL Exception");
            stat = "Unsuccessful";
        } finally {
            return stat;
        }

    }

    @Override
    public String RemoveMember(String MEM, String CLASS, String IDTYP, String ID) throws Exception {
        String stat = "undefined", mem, cls, idtyp = null, id;
        int from, to;
        cls = CLASS;
        Statement st = null;
        Connection con;
        String sql = null;
        id = ID;
        try {
            System.out.println(cls + " " + MEM + " " + CLASS);
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");

            if (MEM.equals("a")) {
                System.out.println(cls + " " + MEM + " " + CLASS);
                if (IDTYP.equals("User Id")) {

                    sql = "delete from teacher where user_id='" + id + "'";

                    Statement st1 = con.createStatement();
                    int coun = st1.executeUpdate("delete from login where user_id='" + id + "' and user_t='teacher'");
                    if (coun == 0) {
                        stat = "no data found.";
                    } else if (coun > 0) {
                        stat = "Successful";
                    }

                } else if (IDTYP.equals("Teacher Id")) {
                    System.out.println("Inside teacher id");
                    Statement st1 = con.createStatement();
                    ResultSet rsrolldata = st1.executeQuery("select user_id from teacher where teach_id='" + id + "'");
                    while (rsrolldata.next()) {
                        String usr = rsrolldata.getString(1);
                        Statement st2 = con.createStatement();
                        int coun = st2.executeUpdate("delete from login where user_id='" + usr + "' and user_t='teacher'");
                        if (coun == 0) {
                            stat = "no data found.";
                        } else if (coun > 0) {
                            stat = "Successful";
                        }
                    }

                    sql = "delete from teacher where teach_id='" + id + "'";

                }

            } else if (MEM.equals("b")) {

                if (IDTYP.equals("User Id")) {

                    sql = "delete from student where user_id='" + id + "' and class='" + cls + "'";
                    Statement st1 = con.createStatement();
                    int coun = st1.executeUpdate("delete from login where user_id='" + id + "'  and user_t='student'");
                    if (coun == 0) {
                        stat = "no data found.";
                    } else if (coun > 0) {
                        stat = "Successful";
                    }

                } else if (IDTYP.equals("Roll no")) {
                    int nid = Integer.parseInt(id);
                    System.out.println(nid);
                    Statement st1 = con.createStatement();
                    ResultSet rsrolldata = st1.executeQuery("select user_id from student where roll_no=" + nid + " and class='" + cls + "'");
                    while (rsrolldata.next()) {
                        String usr = rsrolldata.getString(1);
                        Statement st2 = con.createStatement();
                        int coun = st2.executeUpdate("delete from login where user_id='" + usr + "'  and user_t='student'");
                        if (coun == 0) {
                            stat = "no data found.";
                        } else if (coun > 0) {
                            stat = "Successful";
                        }
                    }

                    sql = "delete from student where roll_no=" + nid + " and class='" + cls + "'";

                }

            } else if (MEM.equals("bm")) {
                System.out.println(cls + " " + IDTYP + " " + ID);
                from = Integer.parseInt(IDTYP);

                to = Integer.parseInt(ID);

                Statement st1 = con.createStatement();

                ResultSet rsrolldata = st1.executeQuery("select user_id from student where class='" + cls + "' and roll_no between " + from + " and " + to);
                while (rsrolldata.next()) {
                    String usr = rsrolldata.getString(1);
                    Statement st2 = con.createStatement();
                    int coun = st2.executeUpdate("delete from login where user_id='" + usr + "'  and user_t='student'");
                    if (coun == 0) {
                        stat = "no data found.";
                    } else if (coun > 0) {
                        stat = "Successful";
                    }
                }

                sql = "delete from student where class='" + cls + "' and roll_no between " + from + " and " + to;

            }

            st = con.createStatement();

            int count = st.executeUpdate(sql);
            if (count == 0) {
                stat = "no data found.";
            } else if (count > 0) {
                stat = "Successful";
            }

        } catch (ClassNotFoundException ex) {
            System.out.println("Class Exception");
            stat = "Unsuccessful";
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("SQL Exception");
            stat = "Unsuccessful";
        } finally {
            return stat;
        }

    }
//checking attendance

    @Override
    public String[][] CheckAtten(Date selectedDate, Date selectedDate1, String ab, String hd, String coun) {
        System.out.println(hd);
        String[][] stat = null;
        String ui, fn, ln, tid, dn;
        int dys;
        float perce;
        int sum;
        String[] lis = null;
        Statement st1;
        int ct = 0;
        int i = 0, cou = 0;
        int rowct = 0;
        Date difft = null;
        Time dif11 = null;
        String data[][] = null;
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");

        if (hd.equals("hours")) {
            cou = Integer.parseInt(coun) * 3600;
            int hours = (int) (cou / 3600);
            int minutes = (int) ((cou % 3600) / 60);
            int seconds = (int) (cou % 60);

            String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);

            try {
                difft = df.parse(timeString);
            } catch (ParseException ex) {

            }
            dif11 = new Time(difft.getTime());

        }
        if (hd.equals("days")) {
            cou = Integer.parseInt(coun);

        }

        SimpleDateFormat dfr;
        try {
            dfr = new SimpleDateFormat("yyyy/MM/dd");
            String d1 = dfr.format(selectedDate);
            Date parsed1 = dfr.parse(d1);

            java.sql.Date pardt1 = new java.sql.Date(parsed1.getTime());

            String d2 = dfr.format(selectedDate1);

            Date parsed2 = dfr.parse(d2);
            float dtepercentnum = 0;
            java.sql.Date pardt2 = new java.sql.Date(parsed2.getTime());
            System.out.println(pardt1);
            System.out.println(pardt2);

            //  select user_id,count(date), sum(time_to_sec(timediffs)) from teacherattendance where date BETWEEN '2017-02-12' and '2017-02-12' GROUP by user_id
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");

            Statement datecnt = con.createStatement();
            ResultSet dtcntrs = datecnt.executeQuery("select count(DISTINCT date) from teacherattendance");
            int Datecnt = 0;
            if (dtcntrs.next()) {
                Datecnt = dtcntrs.getInt(1);
                System.out.println(Datecnt);
                dtepercentnum = cou * Datecnt / 100;
            }

            Statement st = con.createStatement();

            ResultSet rsa = st.executeQuery("select user_id,count(date), sum(time_to_sec(timediffs)) from teacherattendance where date BETWEEN '" + pardt1 + "' and '" + pardt2 + "' and cast(timediffs as time)>'00:00:00' GROUP by user_id");

            if (rsa.next()) {
                rsa.last();
                ct = rsa.getRow();
                System.out.println("total rows " + ct);

                data = new String[ct][9];
                rsa.first();
                rsa.previous();

                while (rsa.next()) {

                    ui = rsa.getString(1);
                    dys = rsa.getInt(2);
                    sum = rsa.getInt(3);
                    st1 = con.createStatement();
                    ResultSet rs1 = st1.executeQuery("select first_n,last_n,teach_id,dept_n from teacher where user_id='" + ui + "'");

                    while (rs1.next()) {
                        fn = rs1.getString(1);
                        ln = rs1.getString(2);
                        tid = rs1.getString(3);
                        dn = rs1.getString(4);

                        lis = new String[9];
                        lis[0] = ui;

                        lis[1] = fn;
                        lis[2] = ln;
                        lis[3] = tid;
                        lis[4] = dn;
                        lis[5] = String.valueOf(dys);
                        perce=((float)dys/(float)(Datecnt))*100;
                        System.out.println(perce+" perce");
                        lis[6] = String.valueOf(Datecnt);
                        lis[7]=String.valueOf(perce+" %");
                        System.out.println("values added to list");
                        System.out.println(dys);

                        int hors = (int) (sum / 3600);
                        int mintes = (int) ((sum % 3600) / 60);
                        int secnds = (int) (sum % 60);
                        System.out.println(hors + " " + mintes + " " + secnds);
                        String timeString1 = String.format("%02d:%02d:%02d", hors, mintes, secnds);
                        lis[8] = timeString1;
                        System.out.println(timeString1);
                        Date dts1 = df.parse(timeString1);
                        Time dif1 = new Time(dts1.getTime());
                        System.out.println(dif1);
                        if (ab.equals("Above")) {
                            if (hd.equals("hours")) {

                                if (dif1.after(dif11)) {

                                    for (int j = 0; j < lis.length; j++) {
                                        data[i][j] = lis[j];

                                    }
                                }

                            } else if (hd.equals("days")) {
                                if (dys > dtepercentnum) {
                                    for (int j = 0; j < lis.length; j++) {
                                        data[i][j] = lis[j];
                                    }
                                }

                            }

                        } else if (ab.equals("Below")) {
                            System.out.println("inside beloww");
                            if (hd.equals("hours")) {
                                System.out.println("inside hours");

                                if (dif1.before(dif11)) {

                                    for (int j = 0; j < lis.length; j++) {
                                        data[i][j] = lis[j];
                                        System.out.println("added " + data[i][j]);
                                    }
                                }
                            } else if (hd.equals("days")) {
                                System.out.println("inside days under below");
                                if (dys <= dtepercentnum) {

                                    for (int j = 0; j < lis.length; j++) {
                                        data[i][j] = lis[j];
                                    }
                                }
                            }

                        }

                    }
                    System.out.println(data[i][1]);
                    if (data[i][1] != null) {

                        i++;
                        System.out.println("inside i++");
                        System.out.println(data[i][1]);

                    } else {
                        i = i;
                        System.out.println("inside null");

                        System.out.println(data[i][1]);
                    }
                }

                if (data[0][1] != null) {
                    System.out.println("added successfully");
                } else {
                    return(null);
                }
            } else {
                return null;
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("Class Exception");
            stat[0][0] = "Unsuccessful";
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("SQL Exception");
            stat[0][0] = "Unsuccessful";
        } catch (ParseException ex) {

        } finally {
            return data;
        }
    }

    @Override
    public ArrayList<ArrayList<String>> GetTeachData() {
        ArrayList<ArrayList<String>> ab = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");
            Statement st = con.createStatement();

            Statement allst = con.createStatement();
            ResultSet alltea = allst.executeQuery("select * from teacher");

            alltea.last();
            int lasrw = alltea.getRow();
            System.out.println(lasrw);
            alltea.first();

            ab = new ArrayList<ArrayList<String>>();

            for (int firct = 0; firct < lasrw; firct++) {
                ab.add(new ArrayList<String>());
                ab.get(firct).add(alltea.getString(1));
                ab.get(firct).add(alltea.getString(2));
                ab.get(firct).add(alltea.getString(3));
                ab.get(firct).add(alltea.getString(4));
                ab.get(firct).add(alltea.getString(5));

                alltea.next();
            }
            System.out.println("Successfully added into list.");

        } catch (ClassNotFoundException ex) {
            System.out.println("exception 1");
        } catch (SQLException ex) {
            System.out.println("exception 2");

        }
        return ab;

    }

    @Override
    public String GetTime() throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        String dte = df.format(new Date());
        return dte;
    }

    @Override
    public String GetDate() throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String dte = df.format(new Date());
        return dte;
    }

    @Override
    public String TeaAttenToDatabase(String[][] tableData, String dt) throws Exception {
        String stat = "undefined";
        String tids = null;
        String tidar[];

        try {

            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");

            String[][] data = (String[][]) tableData;

            String[] daate = dt.split(" ");
            String daate1 = daate[1].trim();
            System.out.println(daate1);
            Date parsed = df.parse(daate1);

            java.sql.Date d1 = new java.sql.Date(parsed.getTime());
            System.out.println(d1);

            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");

            PreparedStatement ps = con.prepareStatement("insert into teacherattendance(user_id,teach_id,dept,f_name,l_name,in_time,out_time,date,timediffs)values(?,?,?,?,?,?,?,?,?)");

            int rows = tableData.length;

            System.out.println(rows);

            for (int i = 0; i < rows; i++) {

                ps.setString(1, data[i][1]);
                ps.setString(2, data[i][5]);
                ps.setString(3, data[i][4]);
                ps.setString(4, data[i][2]);
                ps.setString(5, data[i][3]);

                SimpleDateFormat dft1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String indtm = daate1 + " " + data[i][6];
                Date par1 = dft1.parse(indtm);
                Timestamp dt1 = new Timestamp(par1.getTime());

                ps.setTimestamp(6, dt1);

                String otdtm = daate1 + " " + data[i][7];
                Date par2 = dft1.parse(otdtm);
                Timestamp dt2 = new Timestamp(par2.getTime());

                ps.setTimestamp(7, dt2);
                ps.setDate(8, d1);

                String indif = new String(indtm);
                indif = indif.replace("/", "-");
                String otdif = new String(otdtm);
                otdif = otdif.replace("/", "-");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime dateTime1 = LocalDateTime.parse(indif, formatter);
                LocalDateTime dateTime2 = LocalDateTime.parse(otdif, formatter);
                System.out.println(dateTime1);
                System.out.println(dateTime2);
                long diffInSeconds = java.time.Duration.between(dateTime1, dateTime2).getSeconds();
                int hours = (int) (diffInSeconds / 3600);
                int minutes = (int) ((diffInSeconds % 3600) / 60);
                int seconds = (int) (diffInSeconds % 60);
                String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                System.out.println(timeString);
                Date difft = dft1.parse(daate1 + " " + timeString);
                Timestamp dif1 = new Timestamp(difft.getTime());

                ps.setTimestamp(9, dif1);

                ps.addBatch();

            }

            ps.executeBatch();

            stat = "Successful";

        } catch (ClassNotFoundException ex) {
            System.out.println("Class Exception");
            ex.printStackTrace();
            stat = "Unsuccessful";
        } catch (SQLException ex) {
            System.out.println("SQL Exception");
            ex.printStackTrace();
            stat = "Unsuccessful";
        } finally {
            return stat;
        }
    }

    @Override
    public ArrayList<ArrayList<String>> getStuData(String cls, String cour) throws Exception {
        ArrayList<ArrayList<String>> ar = null;

        //String[] THead = {"User Id", "First Name", "Last Name","Class","Course", "Roll no.", "in time", "out time"};
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");
            Statement st = con.createStatement();

            Statement allst = con.createStatement();
            ResultSet allstu = allst.executeQuery("select * from student where class='" + cls + "'");
            if(allstu.next())
            {
            allstu.last();
            int lasrw = allstu.getRow();
            System.out.println(lasrw);
            allstu.first();

            ar = new ArrayList<ArrayList<String>>();
            boolean a = false;
            for (int firct = 0; firct < lasrw; firct++) {
                String[] splitcour = allstu.getString(5).split("-");
                for (int courct = 0; courct < splitcour.length; courct++) {
                    if (splitcour[courct].equals(cour)) {
                        a = true;
                        System.out.println("if " + splitcour[courct]);
                        break;
                    } else {
                        a = false;
                        System.out.println("else " + splitcour[courct]);

                    }

                }
                System.out.println(a);
                if (a == true) {
                    System.out.println("inside else with " + allstu.getString(5));
                    int alsiz = ar.size();

                    ar.add(new ArrayList<String>());
                    ar.get(alsiz).add(allstu.getString(1));
                    ar.get(alsiz).add(allstu.getString(2));
                    ar.get(alsiz).add(allstu.getString(3));
                    ar.get(alsiz).add(allstu.getString(4));
                    ar.get(alsiz).add(allstu.getString(5));
                    ar.get(alsiz).add(String.valueOf(allstu.getInt(6)));

                }
                allstu.next();
                a = false;
            }
            System.out.println("Successfully added into list.");
            }
            else{return null;}
        } catch (ClassNotFoundException ex) {
            System.out.println("exception 1");
        } catch (SQLException ex) {
            System.out.println("exception 2");

        }
        return ar;

    }

    @Override
    public String StuAttenToDatabase(String[][] tableData, String TfDate, String Tusrid,String pass) throws Exception {
        String stat = "undefined";
        String tids = null;
        String tidar[];

        try {   
               Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");
            
            Statement stver=con.createStatement();
            ResultSet rstver=stver.executeQuery("select * from login where user_id='"+Tusrid+"' and passwd='"+pass+"' and user_t='teacher'");
            if(rstver.next())
            {
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");

            String[][] data = (String[][]) tableData;

            String[] daate = TfDate.split(" ");
            String daate1 = daate[1].trim();
            System.out.println(daate1);
            Date parsed = df.parse(daate1);

            java.sql.Date d1 = new java.sql.Date(parsed.getTime());
            System.out.println(d1);

         
            int TableLecId;
            Statement checlecid = con.createStatement();
            System.out.println(data[0][4] + data[0][5] + d1);
            ResultSet Rslecid = checlecid.executeQuery("select * from lecturedata where class='" + data[0][4] + "' and course='" + data[0][5] + "'  and date='" + d1 + "' order by lec_id asc");
            if (Rslecid.next()) {
                Rslecid.previous();
                Rslecid.last();
                System.out.println("inside tablelecid if");
                TableLecId = Rslecid.getInt(1);
                TableLecId = TableLecId + 1;
            } else {
                TableLecId = 1;
            }

            PreparedStatement newLec = con.prepareStatement("insert into lecturedata values(?,?,?,?,?)");
            newLec.setInt(1, TableLecId);
            newLec.setString(2, Tusrid);
            newLec.setString(3, data[0][4]);
            newLec.setString(4, data[0][5]);
            newLec.setDate(5, d1);
            newLec.executeUpdate();

            Statement st = con.createStatement();
            tidar = new String[tableData.length];
            System.out.println(tableData.length);

            PreparedStatement ps = con.prepareStatement("insert into studentattendance(lec_id,user_id,first_n,last_n,roll_no,class,course,teach_usrid,in_time,out_time,date,timediffs)values(?,?,?,?,?,?,?,?,?,?,?,?)");

            int rows = tableData.length;

            System.out.println(rows);

            for (int i = 0; i < rows; i++) {

                ps.setInt(1, TableLecId);
                ps.setString(2, data[i][1]);
                ps.setString(3, data[i][2]);
                ps.setString(4, data[i][3]);
                ps.setInt(5, Integer.parseInt(data[i][6]));
                ps.setString(6, data[i][4]);
                ps.setString(7, data[i][5]);
                ps.setString(8, Tusrid);

                SimpleDateFormat dft1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String indtm = daate1 + " " + data[i][7];
                Date par1 = dft1.parse(indtm);
                Timestamp dt1 = new Timestamp(par1.getTime());

                ps.setTimestamp(9, dt1);

                String otdtm = daate1 + " " + data[i][8];
                Date par2 = dft1.parse(otdtm);
                Timestamp dt2 = new Timestamp(par2.getTime());

                ps.setTimestamp(10, dt2);
                ps.setDate(11, d1);

                String indif = new String(indtm);
                indif = indif.replace("/", "-");
                String otdif = new String(otdtm);
                otdif = otdif.replace("/", "-");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime dateTime1 = LocalDateTime.parse(indif, formatter);
                LocalDateTime dateTime2 = LocalDateTime.parse(otdif, formatter);
                System.out.println(dateTime1);
                System.out.println(dateTime2);
                long diffInSeconds = java.time.Duration.between(dateTime1, dateTime2).getSeconds();
                int hours = (int) (diffInSeconds / 3600);
                int minutes = (int) ((diffInSeconds % 3600) / 60);
                int seconds = (int) (diffInSeconds % 60);
                String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                System.out.println(timeString);
                Date difft = dft1.parse(daate1 + " " + timeString);
                Timestamp dif1 = new Timestamp(difft.getTime());

                ps.setTimestamp(12, dif1);

                ps.addBatch();

            }

            ps.executeBatch();

            stat = "Successful";
            }
            else
            {
            stat="Invalid password.";
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("Class Exception");
            ex.printStackTrace();
            stat = "Unsuccessful";
        } catch (SQLException ex) {
            System.out.println("SQL Exception");
            ex.printStackTrace();
            stat = "Unsuccessful";
        } finally {
            return stat;
        }
    }

    @Override
    public String[][] CheckStuAttend(Date selectedDate, Date selectedDate1, String cls, String TeaCour, String TeCourData, String ab, String hd, String hrdys) throws Exception {
        String[][] stat = null;
        String ui, fn, ln, roll_no, dn;
        float lecs;
        int sum;
        String[] lis = null;
        Statement st1;
        int ct = 0;
        int i = 0;
        float cou = 0;
        float perct;
        int rowct = 0;
        Date difft = null;
        Time dif11 = null;
        String data[][] = null;
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");

        if (hd.equals("hours")) {
            cou = Integer.parseInt(hrdys) * 3600;
            int hours = (int) (cou / 3600);
            int minutes = (int) ((cou % 3600) / 60);
            int seconds = (int) (cou % 60);

            String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);

            try {
                difft = df.parse(timeString);
            } catch (ParseException ex) {

            }
            dif11 = new Time(difft.getTime());

        }
        if (hd.equals("lectures")) {
            cou = Integer.parseInt(hrdys);

        }

        SimpleDateFormat dfr;
        try {
            dfr = new SimpleDateFormat("yyyy/MM/dd");
            String d1 = dfr.format(selectedDate);
            Date parsed1 = dfr.parse(d1);

            java.sql.Date pardt1 = new java.sql.Date(parsed1.getTime());

            String d2 = dfr.format(selectedDate1);

            Date parsed2 = dfr.parse(d2);
            float lecpercentnum = 0;
            java.sql.Date pardt2 = new java.sql.Date(parsed2.getTime());
            System.out.println(pardt1);
            System.out.println(pardt2);

            //  select user_id,count(date), sum(time_to_sec(timediffs)) from teacherattendance where date BETWEEN '2017-02-12' and '2017-02-12' GROUP by user_id
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");

            Statement totallec = con.createStatement();
            ResultSet rstotallec = null;
            int teacourcount = 0;
            if (TeaCour.equals("Course")) {
                rstotallec = totallec.executeQuery("select count(course) from lecturedata where date between '" + pardt1 + "' and '" + pardt2 + "' and class='" + cls + "' and course='" + TeCourData + "'");

            } else if (TeaCour.equals("Teacher")) {
                rstotallec = totallec.executeQuery("select count(user_id) from lecturedata where date between '" + pardt1 + "' and '" + pardt2 + "' and class='" + cls + "' and user_id='" + TeCourData + "'");
            }
            if (rstotallec.next()) {
                teacourcount = rstotallec.getInt(1);
                lecpercentnum = cou * teacourcount / 100;
                System.out.println("lecpercentnum " + lecpercentnum);
            }

            Statement st = con.createStatement();
            if (TeaCour.equals("Course")) {
                ResultSet rsa = st.executeQuery("select user_id,count(lec_id), sum(time_to_sec(timediffs)) from studentattendance where class='" + cls + "' and course='" + TeCourData + "' and date BETWEEN '" + pardt1 + "' and '" + pardt2 + "' and cast(timediffs as time)>'00:00:00' GROUP by user_id");

                if (rsa.next()) {
                    rsa.last();
                    ct = rsa.getRow();
                    System.out.println("total rows " + ct);

                    data = new String[ct][10];
                    rsa.first();
                    rsa.previous();

                    while (rsa.next()) {

                        ui = rsa.getString(1);
                        lecs = rsa.getInt(2);

                        sum = rsa.getInt(3);
                        st1 = con.createStatement();
                        ResultSet rs1 = st1.executeQuery("select first_n,last_n,roll_no,courses from student where user_id='" + ui + "'");

                        while (rs1.next()) {
                            fn = rs1.getString(1);
                            ln = rs1.getString(2);
                            roll_no = rs1.getString(3);

                            lis = new String[10];
                            lis[0] = ui;

                            lis[1] = fn;
                            lis[2] = ln;
                            lis[3] = roll_no;
                            lis[4] = cls;
                            System.out.println("rs1 gtstr4 "+rs1.getString(4));
                            lis[5] = rs1.getString(4);
                            lis[6] = String.valueOf((int) lecs);
                            lis[7] = String.valueOf(teacourcount);
                               lis[8]=String.valueOf(((float)lecs/(float)teacourcount)*100);
                            System.out.println("values added to list");
                            System.out.println(lecs);

                            int hors = (int) (sum / 3600);
                            int mintes = (int) ((sum % 3600) / 60);
                            int secnds = (int) (sum % 60);
                            System.out.println(hors + " " + mintes + " " + secnds);
                            String timeString1 = String.format("%02d:%02d:%02d", hors, mintes, secnds);
                            lis[9] = timeString1;
                            System.out.println(timeString1);
                            Date dts1 = df.parse(timeString1);
                            Time dif1 = new Time(dts1.getTime());
                            System.out.println(dif1);
                            if (ab.equals("Above")) {
                                if (hd.equals("hours")) {

                                    if (dif1.after(dif11)) {

                                        for (int j = 0; j < lis.length; j++) {
                                            data[i][j] = lis[j];

                                        }
                                    }

                                } else if (hd.equals("lectures")) {
                                    if (lecs > lecpercentnum) {
                                        for (int j = 0; j < lis.length; j++) {
                                            data[i][j] = lis[j];
                                        }
                                    }

                                }

                            } else if (ab.equals("Below")) {
                                System.out.println("inside beloww");
                                if (hd.equals("hours")) {
                                    System.out.println("inside hours");

                                    if (dif1.before(dif11)) {

                                        for (int j = 0; j < lis.length; j++) {
                                            data[i][j] = lis[j];
                                            System.out.println("added " + data[i][j]);
                                        }
                                    }
                                } else if (hd.equals("lectures")) {
                                    System.out.println("inside days under below");
                                    if (lecs < lecpercentnum) {

                                        for (int j = 0; j < lis.length; j++) {
                                            data[i][j] = lis[j];
                                        }
                                    }
                                }

                            }

                        }
                        if (data[i][1] == null) {
                            System.out.println(data[i][1]);
                            i = i;
                            System.out.println("inside null");
                        } else {
                            i++;
                            System.out.println("inside i++");
                            System.out.println(data[i][1]);
                        }
                    }
                }
            } else if (TeaCour.equals("Teacher")) {
                ResultSet rsa = st.executeQuery("select user_id,count(lec_id), sum(time_to_sec(timediffs)) from studentattendance where class='" + cls + "' and teach_usrid='" + TeCourData + "' and date BETWEEN '" + pardt1 + "' and '" + pardt2 + "' and cast(timediffs as time)>'00:00:00' GROUP by user_id");

                if (rsa.next()) {
                    rsa.last();
                    ct = rsa.getRow();
                    System.out.println("total rows " + ct);

                    data = new String[ct][10];
                    rsa.first();
                    rsa.previous();

                    while (rsa.next()) {

                        ui = rsa.getString(1);
                        lecs = rsa.getInt(2);
                        sum = rsa.getInt(3);
                        st1 = con.createStatement();
                        ResultSet rs1 = st1.executeQuery("select first_n,last_n,roll_no,courses from student where user_id='" + ui + "'");

                        while (rs1.next()) {
                            fn = rs1.getString(1);
                            ln = rs1.getString(2);
                            roll_no = rs1.getString(3);

                            lis = new String[10];
                            lis[0] = ui;

                            lis[1] = fn;
                            lis[2] = ln;
                            lis[3] = roll_no;
                            lis[4] = cls;
                            lis[5] =  rs1.getString(4);
                            lis[6] = String.valueOf((int) lecs);
                            lis[7] = String.valueOf(teacourcount);
                           
                            lis[8]=String.valueOf(((float)lecs/(float)teacourcount)*100);
                            System.out.println("lecs " + lecs);
                            System.out.println("values added to list");
                            System.out.println(lecs);

                            int hors = (int) (sum / 3600);
                            int mintes = (int) ((sum % 3600) / 60);
                            int secnds = (int) (sum % 60);
                            System.out.println(hors + " " + mintes + " " + secnds);
                            String timeString1 = String.format("%02d:%02d:%02d", hors, mintes, secnds);
                            lis[9] = timeString1;
                            System.out.println(timeString1);
                            Date dts1 = df.parse(timeString1);
                            Time dif1 = new Time(dts1.getTime());
                            System.out.println(dif1);
                            if (ab.equals("Above")) {
                                if (hd.equals("hours")) {

                                    if (dif1.after(dif11)) {

                                        for (int j = 0; j < lis.length; j++) {
                                            data[i][j] = lis[j];

                                        }
                                    }

                                } else if (hd.equals("lectures")) {
                                    if (lecs > lecpercentnum) {
                                        for (int j = 0; j < lis.length; j++) {
                                            data[i][j] = lis[j];
                                        }
                                    }

                                }

                            } else if (ab.equals("Below")) {
                                System.out.println("inside beloww");
                                if (hd.equals("hours")) {
                                    System.out.println("inside hours");

                                    if (dif1.before(dif11)) {

                                        for (int j = 0; j < lis.length; j++) {
                                            data[i][j] = lis[j];
                                            System.out.println("added " + data[i][j]);
                                        }
                                    }
                                } else if (hd.equals("lectures")) {
                                    System.out.println("inside days under below");
                                    if (lecs < lecpercentnum) {

                                        for (int j = 0; j < lis.length; j++) {
                                            data[i][j] = lis[j];
                                        }
                                    }
                                }

                            }

                        }
                        if (data[i][1] == null) {
                            System.out.println(data[i][1]);
                            i = i;
                            System.out.println("inside null");
                        } else {
                            i++;
                            System.out.println("inside i++");
                            System.out.println(data[i][1]);
                        }
                    }
                }

            }
            System.out.println("added successfully");
        } catch (ClassNotFoundException ex) {
            System.out.println("Class Exception");
            stat[0][0] = "Unsuccessful";
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("SQL Exception");
            stat[0][0] = "Unsuccessful";
        } catch (ParseException ex) {

        } finally {
            return data;
        }
    }

    @Override
    public String[][] CheckTeaOwnAtten(Date selectedDate, Date selectedDate1, String UserId) throws Exception {
        String[][] teaown = null;
        SimpleDateFormat dfr;
        try {
            dfr = new SimpleDateFormat("yyyy/MM/dd");
            String d1 = dfr.format(selectedDate);
            Date parsed1 = dfr.parse(d1);

            java.sql.Date pardt1 = new java.sql.Date(parsed1.getTime());

            String d2 = dfr.format(selectedDate1);

            Date parsed2 = dfr.parse(d2);
            float lecpercentnum = 0;
            java.sql.Date pardt2 = new java.sql.Date(parsed2.getTime());
            System.out.println(pardt1);
            System.out.println(pardt2);

            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");

            Statement teaAtt = con.createStatement();
            ResultSet rsteaAtt = teaAtt.executeQuery("select f_name,l_name,dept,date,time_to_sec(in_time),time_to_sec(out_time),time_to_sec(timediffs) from teacherattendance where date between '" + pardt1 + "' and '" + pardt2 + "' and user_id='" + UserId + "'");
            if (rsteaAtt.next()) {
                rsteaAtt.last();
                int rwct = rsteaAtt.getRow();
                teaown = new String[rwct][6];
                rsteaAtt.first();

                for (int i = 0; i < rwct; i++) {
                    teaown[i][0] = UserId;

                    teaown[i][1] = rsteaAtt.getString(1);
                    teaown[i][2] = rsteaAtt.getString(2);
                    teaown[i][3] = rsteaAtt.getString(3);

                    Date dt = rsteaAtt.getDate(4);
                    teaown[i][4] = dt.toString();
                    int intm = rsteaAtt.getInt(5);
                    int outm = rsteaAtt.getInt(6);
                    int diftm = rsteaAtt.getInt(7);

                    int hors = (int) (intm / 3600);
                    int mintes = (int) ((intm % 3600) / 60);
                    int secnds = (int) (intm % 60);
                    System.out.println(hors + " " + mintes + " " + secnds);
                    String timeString1 = String.format("%02d:%02d:%02d", hors, mintes, secnds);

                    teaown[i][5] = timeString1;
                    hors = (int) (outm / 3600);
                    mintes = (int) ((outm % 3600) / 60);
                    secnds = (int) (outm % 60);
                    System.out.println(hors + " " + mintes + " " + secnds);
                    String timeString2 = String.format("%02d:%02d:%02d", hors, mintes, secnds);

                    hors = (int) (diftm / 3600);
                    mintes = (int) ((diftm % 3600) / 60);
                    secnds = (int) (diftm % 60);
                    System.out.println(hors + " " + mintes + " " + secnds);
                    String timeString3 = String.format("%02d:%02d:%02d", hors, mintes, secnds);

                    if (timeString3.equals("00:00:00")) {
                        teaown[i][5] = "absent";
                    } else {
                        teaown[i][5] = "present";
                    }

                    rsteaAtt.next();

                }

            }

        } catch (Exception e) {
        } finally {

            return teaown;
        }

    }

    @Override
    public String[][] CheckStuOwnAtten(Date selectedDate, Date selectedDate1, String UserId) throws Exception {
        String[][] stuown = null;
        SimpleDateFormat dfr;
        try {
            dfr = new SimpleDateFormat("yyyy/MM/dd");
            String d1 = dfr.format(selectedDate);
            Date parsed1 = dfr.parse(d1);

            java.sql.Date pardt1 = new java.sql.Date(parsed1.getTime());

            String d2 = dfr.format(selectedDate1);

            Date parsed2 = dfr.parse(d2);
            float lecpercentnum = 0;
            java.sql.Date pardt2 = new java.sql.Date(parsed2.getTime());
            System.out.println(pardt1);
            System.out.println(pardt2);

            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");

            Statement stuAtt = con.createStatement();
            ResultSet rsstuAtt = stuAtt.executeQuery("select first_n,last_n,teach_usrid,date,time_to_sec(in_time),time_to_sec(out_time),time_to_sec(timediffs) from studentattendance where date between '" + pardt1 + "' and '" + pardt2 + "' and user_id='" + UserId + "'");
            if (rsstuAtt.next()) {
                rsstuAtt.last();
                int rwct = rsstuAtt.getRow();
                stuown = new String[rwct][7];
                rsstuAtt.first();

                for (int i = 0; i < rwct; i++) {
                    stuown[i][0] = UserId;
                    stuown[i][1] = rsstuAtt.getString(1);
                    stuown[i][2] = rsstuAtt.getString(2);
                    String tusrid = rsstuAtt.getString(3);
                    System.out.println(stuown[i][0] + "" + stuown[i][1] + stuown[i][2] + tusrid);
                    Statement tedet = con.createStatement();
                    ResultSet rstedet = tedet.executeQuery("select first_n,dept_n from teacher where user_id='" + tusrid + "'");
                    rstedet.next();
                    stuown[i][3] = rstedet.getString(2);
                    stuown[i][4] = rstedet.getString(1);

                    Date dt = rsstuAtt.getDate(4);
                    stuown[i][5] = dt.toString();
                    int intm = rsstuAtt.getInt(5);
                    int outm = rsstuAtt.getInt(6);
                    int diftm = rsstuAtt.getInt(7);

                    int hors = (int) (intm / 3600);
                    int mintes = (int) ((intm % 3600) / 60);
                    int secnds = (int) (intm % 60);
                    System.out.println(hors + " " + mintes + " " + secnds);
                    String timeString1 = String.format("%02d:%02d:%02d", hors, mintes, secnds);

                    stuown[i][6] = timeString1;
                    hors = (int) (outm / 3600);
                    mintes = (int) ((outm % 3600) / 60);
                    secnds = (int) (outm % 60);
                    System.out.println(hors + " " + mintes + " " + secnds);
                    String timeString2 = String.format("%02d:%02d:%02d", hors, mintes, secnds);

                    hors = (int) (diftm / 3600);
                    mintes = (int) ((diftm % 3600) / 60);
                    secnds = (int) (diftm % 60);
                    System.out.println(hors + " " + mintes + " " + secnds);
                    String timeString3 = String.format("%02d:%02d:%02d", hors, mintes, secnds);

                    if (timeString3.equals("00:00:00")) {
                        stuown[i][6] = "absent";
                    } else {
                        stuown[i][6] = "present";
                    }

                    rsstuAtt.next();

                }

            }

        } finally {

            return stuown;
        }

    }

    @Override
    public String ChangeAdm(String[] data) throws Exception {
        String status = "undefined error";
        String[] addata = data;
        try {
            System.out.println(addata[5] + " " + addata[6]);
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");

            Statement stuAtt = con.createStatement();
            ResultSet rsstuAtt = stuAtt.executeQuery("Select * from login where user_t='admin' and user_id='" + addata[6] + "' and passwd='" + addata[5] + "'");
            if (rsstuAtt.next()) {
                System.out.println(1);
                PreparedStatement ps = con.prepareStatement("delete from admin");
                ps.executeUpdate();
                System.out.println(2);
                PreparedStatement ps1 = con.prepareStatement("insert into admin values(?,?,?,?,?)");
                System.out.println(3);
                String uuid = UUID.randomUUID().toString();
                String[] ddt = uuid.split("-");

                String usn = "";
                for (int i = 0; i < ddt.length; i++) {
                    usn = usn + ddt[i];
                }
                System.out.println(4);
                String Usern = addata[0].substring(0, 1) + addata[1].substring(0, 1) + addata[3].substring(0, 1) + usn;

                Usern = Usern.substring(0, 14);

                ps1.setString(1, Usern);
                ps1.setString(2, addata[0]);
                ps1.setString(3, addata[1]);
                ps1.setString(4, addata[2]);
                ps1.setLong(5, Long.valueOf(addata[3].trim()));

                ps1.executeUpdate();

                System.out.println(5);
                PreparedStatement ps3 = con.prepareStatement("update login set user_id=?,passwd=? where user_t='admin'");
                ps3.setString(1, Usern);
                ps3.setString(2, addata[4]);
                System.out.println(6);
                ps3.executeUpdate();

                Properties props = new Properties();
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.port", "587");

                Session session = Session.getInstance(props,
                        new javax.mail.Authenticator() {

                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("amspassrecovery@gmail.com"));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(addata[2]));
                message.setSubject("Credentials request");
                message.setText("\n\nYour credentials for logging into Attendance Management System are: "
                        + "\n\n User Id: " + Usern
                        + "\nPassword: " + addata[4]);

                Transport.send(message);

                status = "done, user id and password has been sent to provided email id.";
            } else {
                status = "wrong credential";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    @Override
    public String ChgAdmDet(String selec, String newselec, String passs, String userid) throws Exception {
        String status = "undefined";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");

            Statement admchgst = con.createStatement();
            ResultSet rsadmchgst = admchgst.executeQuery("Select * from login where user_t='admin' and user_id='" + userid + "' and passwd='" + passs + "'");
            if (rsadmchgst.next()) {
                if (selec.equals("Email")) {
                    PreparedStatement psasel = con.prepareStatement("update admin set e_mail=? where user_id=?");
                    psasel.setString(1, newselec);
                    psasel.setString(2, userid);
                    psasel.executeUpdate();
                } else if (selec.equals("Mobile")) {
                    PreparedStatement psasel = con.prepareStatement("update admin set Mob_no=? where user_id=?");
                    psasel.setLong(1, Long.valueOf(newselec));
                    psasel.setString(2, userid);
                    psasel.executeUpdate();
                } else if (selec.equals("Password")) {
                    PreparedStatement psasel = con.prepareStatement("update login set passwd=? where user_id=? and user_t=?");
                    psasel.setString(1, newselec);
                    psasel.setString(2, userid);
                    psasel.setString(3, "admin");
                    psasel.executeUpdate();
                }
                status = "Changes made successfully";
            } else {
                status = "Wrong Credential.";
            }
        } catch (Exception e) {
        } finally {
            return status;
        }
    }

    @Override
    public String SendingUserCred(String mem, String ran, String passs, String get) throws Exception {
        String stat = "undefined";
        System.out.println(mem + " " + ran + " " + passs + " " + get);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");

            Statement admchgst = con.createStatement();
            ResultSet rsadmchgst = admchgst.executeQuery("Select * from login where user_t='admin' and user_id='" + get + "' and passwd='" + passs + "'");
            if (rsadmchgst.next()) {
                if (mem.equals("teacher")) {
                    System.out.println("inside teachers");
                    Statement stfs = con.createStatement();
                    ResultSet rstfs = stfs.executeQuery("select user_id,e_mail from teacher where dept_n='" + ran + "'");
                    while (rstfs.next()) {
                        System.out.println("inside t-while");
                        String userid = rstfs.getString(1);
                        String email = rstfs.getString(2);
                        System.out.println(userid);
                        Statement stfs1 = con.createStatement();
                        ResultSet rstfs1 = stfs1.executeQuery("select passwd from login where user_t='teacher' and user_id='" + userid + "'");
                        rstfs1.next();
                        String pwd = rstfs1.getString(1);
                        System.out.println("inside t-while1");
                        Properties props = new Properties();
                        props.put("mail.smtp.starttls.enable", "true");
                        props.put("mail.smtp.auth", "true");
                        props.put("mail.smtp.host", "smtp.gmail.com");
                        props.put("mail.smtp.port", "587");

                        Session session = Session.getInstance(props,
                                new javax.mail.Authenticator() {

                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(username, password);
                            }
                        });

                        Message message = new MimeMessage(session);
                        message.setFrom(new InternetAddress("amspassrecovery@gmail.com"));
                        message.setRecipients(Message.RecipientType.TO,
                                InternetAddress.parse(email));
                        message.setSubject("Credentials request");
                        message.setText("\n\nYour credentials for logging into Attendance Management System are: "
                                + "\n\n User Id: " + userid
                                + "\nPassword: " + pwd);
                        System.out.println("inside t-while2");
                        Transport.send(message);

                        stat = "done, user id and password has been sent to provided email id.";
                        System.out.println("inside t-while3");
                    }

                } else if (mem.equals("student")) {

                    Statement stfs = con.createStatement();
                    ResultSet rstfs = stfs.executeQuery("select user_id,e_mail from student where class='" + ran + "'");
                    while (rstfs.next()) {
                        String userid = rstfs.getString(1);
                        String email = rstfs.getString(2);

                        Statement stfs1 = con.createStatement();
                        ResultSet rstfs1 = stfs1.executeQuery("select passwd from login where user_t='student' and user_id='" + userid + "'");
                        rstfs1.next();
                        String pwd = rstfs1.getString(1);

                        Properties props = new Properties();
                        props.put("mail.smtp.starttls.enable", "true");
                        props.put("mail.smtp.auth", "true");
                        props.put("mail.smtp.host", "smtp.gmail.com");
                        props.put("mail.smtp.port", "587");

                        Session session = Session.getInstance(props,
                                new javax.mail.Authenticator() {

                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(username, password);
                            }
                        });

                        Message message = new MimeMessage(session);
                        message.setFrom(new InternetAddress("amspassrecovery@gmail.com"));
                        message.setRecipients(Message.RecipientType.TO,
                                InternetAddress.parse(email));
                        message.setSubject("Credentials request");
                        message.setText("\n\nYour credentials for logging into Attendance Management System are: "
                                + "\n\n User Id: " + userid
                                + "\nPassword: " + pwd);

                        Transport.send(message);

                        stat = "done, user id and password has been sent to provided email id.";

                    }

                } else if (mem.equals("staff")) {

                    Statement stfs = con.createStatement();
                    ResultSet rstfs = stfs.executeQuery("select user_id,e_mail from staff");
                    while (rstfs.next()) {
                        String userid = rstfs.getString(1);
                        String email = rstfs.getString(2);

                        Statement stfs1 = con.createStatement();
                        ResultSet rstfs1 = stfs1.executeQuery("select passwd from login where user_t='staff' and user_id='" + userid + "'");
                        rstfs1.next();
                        String pwd = rstfs1.getString(1);

                        Properties props = new Properties();
                        props.put("mail.smtp.starttls.enable", "true");
                        props.put("mail.smtp.auth", "true");
                        props.put("mail.smtp.host", "smtp.gmail.com");
                        props.put("mail.smtp.port", "587");

                        Session session = Session.getInstance(props,
                                new javax.mail.Authenticator() {

                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(username, password);
                            }
                        });

                        Message message = new MimeMessage(session);
                        message.setFrom(new InternetAddress("amspassrecovery@gmail.com"));
                        message.setRecipients(Message.RecipientType.TO,
                                InternetAddress.parse(email));
                        message.setSubject("Credentials request");
                        message.setText("\n\nYour credentials for logging into Attendance Management System are: "
                                + "\n\n User Id: " + userid
                                + "\nPassword: " + pwd);

                        Transport.send(message);

                        stat = "done, user id and password has been sent to provided email id.";

                    }
                }

            } else {
                stat = "Invalid Credential";
            }
        } catch (Exception e) {
        } finally {
            return stat;
        }
    }

    @Override
    public String GenTeaAtt(Date datebf, Date dateaf, String dep) throws Exception {
        String stat = "";
        int totct = 0, atnct = 0;
        try {
            String a = null;

            SimpleDateFormat dfr = new SimpleDateFormat("yyyy/MM/dd");
            String d1 = dfr.format(datebf);
            Date parsed1 = dfr.parse(d1);

            java.sql.Date pardt1 = new java.sql.Date(parsed1.getTime());

            String d2 = dfr.format(dateaf);

            Date parsed2 = dfr.parse(d2);

            Calendar cal = Calendar.getInstance();
            cal.setTime(parsed1);
            String mon1 = null, mon2 = null;
            int month1 = cal.get(Calendar.MONTH);
            cal.setTime(parsed2);
            int month2 = cal.get(Calendar.MONTH);
            int year = cal.get(Calendar.YEAR);
            switch (month1) {
                case 0:
                    mon1 = "January";
                    break;
                case 1:
                    mon1 = "February";
                    break;
                case 2:
                    mon1 = "March";
                    break;
                case 3:
                    mon1 = "April";
                    break;
                case 4:
                    mon1 = "May";
                    break;
                case 5:
                    mon1 = "June";
                    break;
                case 6:
                    mon1 = "July";
                    break;
                case 7:
                    mon1 = "August";
                    break;
                case 8:
                    mon1 = "September";
                    break;
                case 9:
                    mon1 = "October";
                    break;
                case 10:
                    mon1 = "November";
                    break;
                case 11:
                    mon1 = "December";
                    break;
                default:
                    break;
            }
            switch (month2) {
                case 0:
                    mon2 = "January";
                    break;
                case 1:
                    mon2 = "February";
                    break;
                case 2:
                    mon2 = "March";
                    break;
                case 3:
                    mon2 = "April";
                    break;
                case 4:
                    mon2 = "May";
                    break;
                case 5:
                    mon2 = "June";
                    break;
                case 6:
                    mon2 = "July";
                    break;
                case 7:
                    mon2 = "August";
                    break;
                case 8:
                    mon2 = "September";
                    break;
                case 9:
                    mon2 = "October";
                    break;
                case 10:
                    mon2 = "November";
                    break;
                case 11:
                    mon2 = "December";
                    break;
                default:
                    break;
            }

            java.sql.Date pardt2 = new java.sql.Date(parsed2.getTime());
            DefaultTableModel dtm = new DefaultTableModel();
            String[] Head = {"Teacher Id", "fn", "ln", "dte", "intm", "outm", "hrs"};
            dtm.setColumnIdentifiers(Head);
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");

            Statement tearepst = con.createStatement();
            ResultSet rstearepst = tearepst.executeQuery("SELECT teach_id,f_name,l_name,date,time_to_sec(in_time),time_to_sec(out_time),time_to_sec(timediffs) FROM teacherattendance where date BETWEEN '" + pardt1 + "' and '" + pardt2 + "' and dept='" + dep + "' ORDER BY teach_id,date");
            if (rstearepst.next()) {

                rstearepst.last();
                int rsct = rstearepst.getRow();

                rstearepst.first();
                String tid, fn, ln;
                Date dt;
                int intm, outm, tmdf;
                for (int i = 0; i < rsct; i++) {
                    tid = rstearepst.getString(1);
                    fn = rstearepst.getString(2);
                    ln = rstearepst.getString(3);
                    dt = rstearepst.getDate(4);
                    intm = rstearepst.getInt(5);
                    outm = rstearepst.getInt(6);
                    tmdf = rstearepst.getInt(7);

                    int hors = (int) (intm / 3600);
                    int mintes = (int) ((intm % 3600) / 60);
                    int secnds = (int) (intm % 60);
                    System.out.println(hors + " " + mintes + " " + secnds);
                    String timeString1 = String.format("%02d:%02d:%02d", hors, mintes, secnds);

                    hors = (int) (outm / 3600);
                    mintes = (int) ((outm % 3600) / 60);
                    secnds = (int) (outm % 60);
                    System.out.println(hors + " " + mintes + " " + secnds);
                    String timeString2 = String.format("%02d:%02d:%02d", hors, mintes, secnds);

                    hors = (int) (tmdf / 3600);
                    mintes = (int) ((tmdf % 3600) / 60);
                    secnds = (int) (tmdf % 60);
                    System.out.println(hors + " " + mintes + " " + secnds);
                    String timeString3 = String.format("%02d:%02d:%02d", hors, mintes, secnds);
                    System.out.println(timeString3);
                    if (timeString3.equals("00:00:00")) {
                        timeString1 = "absent";
                        timeString2 = "absent";
                        timeString3 = "absent";
                    }

                    if (i == 0) {
                        System.out.println("inside i=0");
                        dtm.addRow(new Object[]{" ", " ", " ", " ", " ", " ", " "});
                        dtm.addRow(new Object[]{tid, fn, ln, dt, timeString1, timeString2, timeString3});
                        a = tid;
                        if (!timeString3.equals("absent")) {
                            atnct++;
                        }
                        totct++;
                    } else {

                        if (tid.equals(a)) {
                            System.out.println("inside equals " + tid + " " + a);
                            dtm.addRow(new Object[]{" ", " ", " ", dt, timeString1, timeString2, timeString3});
                            if (!timeString3.equals("absent")) {
                                atnct++;
                            }
                            totct++;

                        } else if (!tid.equals(a)) {

                            dtm.addRow(new Object[]{"Total ", String.valueOf(atnct), "out of", String.valueOf(totct), "attended. ", " ", " "});

                            System.out.println("inside not equals " + tid + " " + a);
                            dtm.addRow(new Object[]{" ", " ", " ", " ", " ", " ", " "});
                            dtm.addRow(new Object[]{tid, fn, ln, dt, timeString1, timeString2, timeString3});
                            a = tid;
                            if (timeString3.equals("absent")) {
                                atnct = 0;
                            } else {
                                atnct = 1;
                            }
                            totct = 1;

                        }

                    }
                    int f = rsct - 1;
                    if (f == i) {

                        dtm.addRow(new Object[]{"Total ", String.valueOf(atnct), "out of", String.valueOf(totct), "attended. ", " ", " "});

                    }

                    rstearepst.next();
                }

                rstearepst.close();
                tearepst.close();

                JRTableModelDataSource dataSource = new JRTableModelDataSource(dtm);

                String reportSource = "C:\\Users\\Anonymous\\Documents\\NetBeansProjects\\AttenManSys\\src\\Resource\\TeacherAtnRep.jrxml";
                Map<String, Object> params = new HashMap<String, Object>();
                String monthstr;
                if (mon2.equals(mon1)) {
                    monthstr = mon1;
                } else {
                    monthstr = mon1 + " " + mon2;
                }
                {

                }

                params.put("mon", "month: " + monthstr);
                params.put("dept", "Department: " + dep);

                JasperReport jasperReport = JasperCompileManager.compileReport(reportSource);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
               // JasperViewer.viewReport(jasperPrint, false);
                //   String path=System.getProperty("user.dir");

                File f = new File("C:\\AMSRF\\TeacherRep\\" + dep + monthstr + year + ".pdf");
                if (f.exists() && !f.isDirectory()) {
                    stat = "previous report has been overwritten";
                    Statement tabledat = con.createStatement();
                    tabledat.executeUpdate("delete from teacherreport where month='" + monthstr + "' and year=" + year + " and dept='" + dep + "'");
                }
                JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\AMSRF\\TeacherRep\\" + dep + monthstr + year + ".pdf");
                stat = stat + ", successful \n select view report to view the report.";

                PreparedStatement reptodb = con.prepareStatement("insert into teacherreport values(?,?,?,?,?,?)");
                reptodb.setString(1, f.getAbsolutePath());
                reptodb.setString(2, monthstr);
                reptodb.setInt(3, year);
                reptodb.setString(4, dep);
                reptodb.setDate(5, pardt1);
                reptodb.setDate(6, pardt2);

                reptodb.executeUpdate();
                t1=new Thread(this,"admin");
                depdat=dep;
                t1.start();
            }else
            {stat="no records found for the given date range.";}
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return stat;
        }
    }

    @Override
    public String[][] getTeaRepData() throws Exception {
        String[][] data = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");
            Statement repdat = con.createStatement();
            ResultSet rsrepdat = repdat.executeQuery("select month,year,dept,from_dt,to_dt from teacherreport");
            if (rsrepdat.next()) {
                rsrepdat.last();
                int ct = rsrepdat.getRow();
                rsrepdat.first();
                data = new String[ct][5];
                for (int cts = 0; cts < ct; cts++) {
                    String mn = rsrepdat.getString(1);
                    int yr = rsrepdat.getInt(2);
                    String dep = rsrepdat.getString(3);
                    data[cts][0] = mn;
                    data[cts][1] = String.valueOf(yr);
                    data[cts][2] = dep;
                    data[cts][3] = String.valueOf(rsrepdat.getDate(4));
                    data[cts][4] = String.valueOf(rsrepdat.getDate(5));
                    rsrepdat.next();
                }
            } else {
              return null;
            }

        } catch (Exception e) {

        } finally {
            return data;
        }
    }

    @Override
    public byte[] getTeaRepFile(String mn, String yr, String dp) throws Exception {
        File f = null;
        String path;
        byte buffer[] = null;
        try {

            int tr = Integer.parseInt(yr);
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");
            Statement repdat = con.createStatement();
            ResultSet rsrepdat = repdat.executeQuery("select path from teacherreport where month='" + mn + "' and year=" + tr + " and dept='" + dp + "'");
            if (rsrepdat.next()) {
                path = rsrepdat.getString(1);
                f = new File(path);
                System.out.println(f.getAbsolutePath());
                buffer = new byte[(int) f.length()];
                BufferedInputStream inputFileStream = new BufferedInputStream(new FileInputStream(path));
                inputFileStream.read(buffer, 0, buffer.length);
                inputFileStream.close();

            }

        } catch (Exception ex) {
            return null;
        }

        return buffer;
    }

    @Override
    public String DelTeaRep(String mn, String yr, String dp) throws Exception {
        File f = null;
        String path = null, stat = " ";

        try {

            int tr = Integer.parseInt(yr);
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");
            Statement repdat = con.createStatement();
            ResultSet rsrepdat = repdat.executeQuery("select path from teacherreport where month='" + mn + "' and year=" + tr + " and dept='" + dp + "'");
            if (rsrepdat.next()) {
                path = rsrepdat.getString(1);
                f = new File(path);
            }
            if (f.delete()) {
                Statement strm = con.createStatement();
                strm.executeUpdate("delete from teacherreport where month='" + mn + "' and year=" + tr + " and dept='" + dp + "'");

                stat = "File deleted Successfully";

            } else {
                stat = "could not delete file";
            }
        } catch (Exception ex) {
        } finally {
            return stat;
        }
    }

    @Override
    public String GenStuRep(Date datebf, Date dateaf, String Sccls, String tid) {
        String stat = "";
        sturepclas=Sccls;
        int totct = 0, atnct = 0;
        try {
            int a = 0;
            
            SimpleDateFormat dfr = new SimpleDateFormat("yyyy/MM/dd");
            String d1 = dfr.format(datebf);
            Date parsed1 = dfr.parse(d1);

            java.sql.Date pardt1 = new java.sql.Date(parsed1.getTime());

            String d2 = dfr.format(dateaf);

            Date parsed2 = dfr.parse(d2);

            Calendar cal = Calendar.getInstance();
            cal.setTime(parsed1);
            String mon1 = null, mon2 = null;
            int month1 = cal.get(Calendar.MONTH);
            cal.setTime(parsed2);
            int month2 = cal.get(Calendar.MONTH);
            int year = cal.get(Calendar.YEAR);
            switch (month1) {
                case 0:
                    mon1 = "January";
                    break;
                case 1:
                    mon1 = "February";
                    break;
                case 2:
                    mon1 = "March";
                    break;
                case 3:
                    mon1 = "April";
                    break;
                case 4:
                    mon1 = "May";
                    break;
                case 5:
                    mon1 = "June";
                    break;
                case 6:
                    mon1 = "July";
                    break;
                case 7:
                    mon1 = "August";
                    break;
                case 8:
                    mon1 = "September";
                    break;
                case 9:
                    mon1 = "October";
                    break;
                case 10:
                    mon1 = "November";
                    break;
                case 11:
                    mon1 = "December";
                    break;
                default:
                    break;
            }
            switch (month2) {
                case 0:
                    mon2 = "January";
                    break;
                case 1:
                    mon2 = "February";
                    break;
                case 2:
                    mon2 = "March";
                    break;
                case 3:
                    mon2 = "April";
                    break;
                case 4:
                    mon2 = "May";
                    break;
                case 5:
                    mon2 = "June";
                    break;
                case 6:
                    mon2 = "July";
                    break;
                case 7:
                    mon2 = "August";
                    break;
                case 8:
                    mon2 = "September";
                    break;
                case 9:
                    mon2 = "October";
                    break;
                case 10:
                    mon2 = "November";
                    break;
                case 11:
                    mon2 = "December";
                    break;
                default:
                    break;
            }

            java.sql.Date pardt2 = new java.sql.Date(parsed2.getTime());
            DefaultTableModel dtm = new DefaultTableModel();
            String[] Head = {"rol", "fn", "ln", "cou", "dte", "intm", "outm", "tottm"};
            dtm.setColumnIdentifiers(Head);
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");

            Statement stuGenrep = con.createStatement();
            ResultSet rstuGenrep = stuGenrep.executeQuery("select roll_no,first_n,last_n,course,date,time_to_sec(in_time),time_to_sec(out_time),time_to_sec(timediffs),user_id from studentattendance where date between '" + pardt1 + "' and '" + pardt2 + "' and teach_usrid='" + tid + "' and class='" + Sccls + "' order by course,roll_no,date");

            if (rstuGenrep.next()) {

                rstuGenrep.last();
                int rsct = rstuGenrep.getRow();
                sturepthrdat=new String[rsct];
                rstuGenrep.first();
                String fn, ln, cour;
                int roll;
                Date dt;
                int intm, outm, tmdf;
                for (int i = 0; i < rsct; i++) {
                    roll = rstuGenrep.getInt(1);
                    fn = rstuGenrep.getString(2);
                    ln = rstuGenrep.getString(3);
                    cour = rstuGenrep.getString(4);
                    dt = rstuGenrep.getDate(5);
                    intm = rstuGenrep.getInt(6);
                    outm = rstuGenrep.getInt(7);
                    tmdf = rstuGenrep.getInt(8);
                   sturepthrdat[i]=rstuGenrep.getString(9);
                    int hors = (int) (intm / 3600);
                    int mintes = (int) ((intm % 3600) / 60);
                    int secnds = (int) (intm % 60);
                    System.out.println(hors + " " + mintes + " " + secnds);
                    String timeString1 = String.format("%02d:%02d:%02d", hors, mintes, secnds);

                    hors = (int) (outm / 3600);
                    mintes = (int) ((outm % 3600) / 60);
                    secnds = (int) (outm % 60);
                    System.out.println(hors + " " + mintes + " " + secnds);
                    String timeString2 = String.format("%02d:%02d:%02d", hors, mintes, secnds);

                    hors = (int) (tmdf / 3600);
                    mintes = (int) ((tmdf % 3600) / 60);
                    secnds = (int) (tmdf % 60);
                    System.out.println(hors + " " + mintes + " " + secnds);
                    String timeString3 = String.format("%02d:%02d:%02d", hors, mintes, secnds);
                    System.out.println(timeString3);
                    if (timeString3.equals("00:00:00")) {
                        timeString1 = "absent";
                        timeString2 = "absent";
                        timeString3 = "absent";
                    }

                    if (i == 0) {
                        System.out.println("inside i=0");
                        dtm.addRow(new Object[]{" ", " ", " ", " ", " ", " ", " ", " "});
                        dtm.addRow(new Object[]{roll, fn, ln, cour, dt, timeString1, timeString2, timeString3});
                        a = roll;
                        if (!timeString3.equals("absent")) {
                            atnct++;
                        }
                        totct++;
                    } else {

                        if (roll == a) {
                            System.out.println("inside equals " + tid + " " + a);
                            dtm.addRow(new Object[]{" ", " ", " ", cour, dt, timeString1, timeString2, timeString3});
                            if (!timeString3.equals("absent")) {
                                atnct++;
                            }
                            totct++;

                        } else if (roll != a) {

                            dtm.addRow(new Object[]{"", "Total lectures ", String.valueOf(atnct), "out of", String.valueOf(totct), "attended. ", " ", " "});

                            System.out.println("inside not equals " + roll + " " + a);
                            dtm.addRow(new Object[]{" ", " ", " ", " ", " ", " ", " ", " "});
                            dtm.addRow(new Object[]{roll, fn, ln, cour, dt, timeString1, timeString2, timeString3});
                            a = roll;
                            if (timeString3.equals("absent")) {
                                atnct = 0;
                            } else {
                                atnct = 1;
                            }
                            totct = 1;

                        }

                    }
                    int f = rsct - 1;
                    if (f == i) {

                        dtm.addRow(new Object[]{"", "Total lectures ", String.valueOf(atnct), "out of", String.valueOf(totct), "attended. ", " ", " "});

                    }

                    rstuGenrep.next();
                }

                rstuGenrep.close();
                stuGenrep.close();

                for (int cos = 0; cos < dtm.getRowCount(); cos++) {
                    System.out.println(dtm.getValueAt(cos, 0) + " " + dtm.getValueAt(cos, 1) + " " + dtm.getValueAt(cos, 2) + " " + dtm.getValueAt(cos, 3) + " " + dtm.getValueAt(cos, 4) + " " + dtm.getValueAt(cos, 5) + dtm.getValueAt(cos, 6) + " " + dtm.getValueAt(cos, 7));
                }

                JRTableModelDataSource dataSource = new JRTableModelDataSource(dtm);
                String tfn = null, tln = null, tdep = null, teaid = null;
                Statement sttea = con.createStatement();
                ResultSet rsstea = sttea.executeQuery("select first_n,last_n,dept_n,teach_id from teacher where user_id='" + tid + "'");
                if (rsstea.next()) {
                  
                    tfn = rsstea.getString(1);
                      stureptean=tfn;
                    tln = rsstea.getString(2);
                    tdep = rsstea.getString(3);
                    sturepteadep=tdep;
                    teaid = rsstea.getString(4);
                    
                }
                String reportSource = "C:\\Users\\Anonymous\\Documents\\NetBeansProjects\\AttenManSys\\src\\Resource\\StudentAtRep.jrxml";
                Map<String, Object> params = new HashMap<String, Object>();
                String monthstr;

                if (mon2.equals(mon1)) {
                    monthstr = mon1;
                } else {
                    monthstr = mon1 + " " + mon2;
                }
                params.put("class", "Class: " + Sccls);
                params.put("mon", "month: " + monthstr);
                params.put("tname", "Teacher Name: " + tfn + " " + tln);
                params.put("dept", "Department: " + tdep);

                JasperReport jasperReport = JasperCompileManager.compileReport(reportSource);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
                JasperViewer.viewReport(jasperPrint, false);
                //   String path=System.getProperty("user.dir");

                File f = new File("C:\\AMSRF\\StudentRep\\" + tfn + tdep + teaid + Sccls + monthstr + year + ".pdf");
                if (f.exists() && !f.isDirectory()) {
                    stat = "previous report has been overwritten ,";
                    Statement tabledat = con.createStatement();
                    System.out.println(f.getAbsolutePath());
                    tabledat.executeUpdate("delete from defaultersreport where month='" + monthstr + "' and teach_id='" + teaid + "' and class='" + Sccls + "' and year=" + year);

                }

                JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\AMSRF\\StudentRep\\" + tfn + tdep + teaid + Sccls + monthstr + year + ".pdf");
                stat = stat + " successful \n select view report to view the report.";

                PreparedStatement reptodb = con.prepareStatement("insert into studentreport values(?,?,?,?,?,?,?)");
                reptodb.setString(1, teaid);
                reptodb.setString(2, monthstr);
                reptodb.setInt(3, year);
                reptodb.setString(4, f.getAbsolutePath());
                reptodb.setString(5, Sccls);
                reptodb.setDate(6, pardt1);
                reptodb.setDate(7, pardt2);

                reptodb.executeUpdate();
                  t1=new Thread(this,"teacherAttrep");
               
                t1.start();

            }else
            {
            stat="No record found for tht selected dates/class.";
            }

        } catch (Exception ex) {
        } finally {
            return stat;
        }
    }

    @Override
    public String GenStuDefRep(Date datebf, Date dateaf, String Sccls, String get, String minper) {
        String stat = "";
        try {
            System.out.println(1);
            int pereq = Integer.parseInt(minper);
            SimpleDateFormat dfr = new SimpleDateFormat("yyyy/MM/dd");
            String d1 = dfr.format(datebf);
            Date parsed1 = dfr.parse(d1);

            java.sql.Date pardt1 = new java.sql.Date(parsed1.getTime());

            String d2 = dfr.format(dateaf);

            Date parsed2 = dfr.parse(d2);

            Calendar cal = Calendar.getInstance();
            cal.setTime(parsed1);
            String mon1 = null, mon2 = null;
            int month1 = cal.get(Calendar.MONTH);
            cal.setTime(parsed2);
            int month2 = cal.get(Calendar.MONTH);
            int year = cal.get(Calendar.YEAR);
            switch (month1) {
                case 0:
                    mon1 = "January";
                    break;
                case 1:
                    mon1 = "February";
                    break;
                case 2:
                    mon1 = "March";
                    break;
                case 3:
                    mon1 = "April";
                    break;
                case 4:
                    mon1 = "May";
                    break;
                case 5:
                    mon1 = "June";
                    break;
                case 6:
                    mon1 = "July";
                    break;
                case 7:
                    mon1 = "August";
                    break;
                case 8:
                    mon1 = "September";
                    break;
                case 9:
                    mon1 = "October";
                    break;
                case 10:
                    mon1 = "November";
                    break;
                case 11:
                    mon1 = "December";
                    break;
                default:
                    break;
            }
            switch (month2) {
                case 0:
                    mon2 = "January";
                    break;
                case 1:
                    mon2 = "February";
                    break;
                case 2:
                    mon2 = "March";
                    break;
                case 3:
                    mon2 = "April";
                    break;
                case 4:
                    mon2 = "May";
                    break;
                case 5:
                    mon2 = "June";
                    break;
                case 6:
                    mon2 = "July";
                    break;
                case 7:
                    mon2 = "August";
                    break;
                case 8:
                    mon2 = "September";
                    break;
                case 9:
                    mon2 = "October";
                    break;
                case 10:
                    mon2 = "November";
                    break;
                case 11:
                    mon2 = "December";
                    break;
                default:
                    break;
            }
            System.out.println(2);
            java.sql.Date pardt2 = new java.sql.Date(parsed2.getTime());
            DefaultTableModel dtm = new DefaultTableModel();
            String[] Head = {"rol", "fn", "ln", "atnl", "totl", "atnp"};
            dtm.setColumnIdentifiers(Head);
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");

            Statement stattentot = con.createStatement();
            String[][] data;    //select user_id,sum(case when cast(timediffs as time)>'00:00:00' then 1 else 0 end) from studentattendance where date BETWEEN '2017-02-02' and '2017-02-23' and class='TYBSc' and teach_usrid='SuCSsj3daad677' group by user_id
            ResultSet rsattentot = stattentot.executeQuery("select user_id,sum(case when cast(timediffs as time)>'00:00:00' then 1 else 0 end) from studentattendance where date BETWEEN '" + pardt1 + "' and '" + pardt2 + "' and class='" + Sccls + "' and teach_usrid='" + get + "' GROUP by user_id");
            if (rsattentot.next()) {
                String uid, fname = null, lname = null;
                int attendedct, totattenct = 0, roll = 0;
                int percent;

                rsattentot.last();
                int count = rsattentot.getRow();
     studefrepthrdat=new String[count];
                rsattentot.first();
                for (int ict = 0; ict < count; ict++) {
                    uid = rsattentot.getString(1);
                    studefrepthrdat[ict]=uid;
                    attendedct = rsattentot.getInt(2);
                    Statement stttot = con.createStatement();
                    ResultSet rstttot = stttot.executeQuery("select count(in_time) from studentattendance where date BETWEEN '" + pardt1 + "' and '" + pardt2 + "' and class='" + Sccls + "' and user_id='" + uid + "' and teach_usrid='" + get + "'");
                    //select count(in_time) from studentattendance where date BETWEEN '2017-02-02' and '2017-02-22' and class='TYBSC' and user_id='ab1' and teach_usrid='abc'
                    if (rstttot.next()) {
                        totattenct = rstttot.getInt(1);
                    }
                    System.out.println(3);
                    Statement stdet = con.createStatement();
                    ResultSet stdetrs = stdet.executeQuery("select first_n,last_n,roll_no from student where user_id='" + uid + "'");
                    if (stdetrs.next()) {
                        fname = stdetrs.getString(1);
                        lname = stdetrs.getString(2);
                        roll = stdetrs.getInt(3);
                    }
                    System.out.println(4);
                    percent = (attendedct * 100) / totattenct;
                    System.out.println("attended " + attendedct);
                    System.out.println("total lec " + totattenct);
                    if (percent < pereq) {
                        System.out.println("in " + roll + " " + fname + " " + lname + " " + attendedct + " " + totattenct + " " + percent);
                        dtm.addRow(new Object[]{String.valueOf(roll), fname, lname, String.valueOf(attendedct), String.valueOf(totattenct), String.valueOf(percent)});
                        System.out.println("dtmvalue" + dtm.getValueAt(0, 0));
                        System.out.println(5);
                        rsattentot.next();
                    } else {
                        System.out.println("out  " + roll + " " + fname + " " + lname + " " + attendedct + " " + totattenct + " " + percent);
                        System.out.println(6);
                        rsattentot.next();

                    }
                }

            
            System.out.println(7);

            JRTableModelDataSource dataSource = new JRTableModelDataSource(dtm);
            String tfn = null, tln = null, tdep = null, teaid = null;
            Statement sttea = con.createStatement();
            ResultSet rsstea = sttea.executeQuery("select first_n,last_n,dept_n,teach_id from teacher where user_id='" + get + "'");
            if (rsstea.next()) {
                System.out.println(11);
                tfn = rsstea.getString(1);
                tln = rsstea.getString(2);
                tdep = rsstea.getString(3);
               studefreptean=tfn;
    studefrepteadep=tdep;
                teaid = rsstea.getString(4);
            }
            String reportSource = "C:\\Users\\Anonymous\\Documents\\NetBeansProjects\\AttenManSys\\src\\Resource\\StudentDefRep.jrxml";
            Map<String, Object> params = new HashMap<String, Object>();
            String monthstr;
            System.out.println(12);
            if (mon2.equals(mon1)) {
                monthstr = mon1;
            } else {
                monthstr = mon1 + " " + mon2;
            }
            System.out.println(monthstr + " " + Sccls + " " + tfn + " " + tln + " " + tdep);
            params.put("mon", "month: " + monthstr);
            params.put("class", "Class: " + Sccls);
            System.out.println(13);
            params.put("tname", "Teacher Name: " + tfn + " " + tln);
            params.put("dep", "Department: " + tdep);
            params.put("minp", "Minimum Percentage: " + String.valueOf(minper));
            JasperReport jasperReport = JasperCompileManager.compileReport(reportSource);
            System.out.println(14);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
            System.out.println(15);
            JasperViewer.viewReport(jasperPrint, false);
            //   String path=System.getProperty("user.dir");
            System.out.println(16);
            File f = new File("C:\\AMSRF\\StudentDefReport\\" + tfn + tdep + teaid + Sccls + monthstr + year + ".pdf");
            if (f.exists() && !f.isDirectory()) {
                stat = "previous report has been overwritten ,";
                Statement tabledat = con.createStatement();
                System.out.println(f.getAbsolutePath());
                tabledat.executeUpdate("delete from defaultersreport where month='" + monthstr + "' and teach_id='" + teaid + "' and class='" + Sccls + "' and year=" + year);

            }

            JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\AMSRF\\StudentDefReport\\" + tfn + tdep + teaid + Sccls + monthstr + year + ".pdf");
            stat = stat + " successful \n select view report to view the report.";

            PreparedStatement reptodb = con.prepareStatement("insert into defaultersreport values(?,?,?,?,?,?,?)");
            reptodb.setString(1, teaid);
            reptodb.setString(2, monthstr);
            reptodb.setInt(3, year);
            reptodb.setString(4, f.getAbsolutePath());
            reptodb.setString(5, Sccls);
            reptodb.setDate(6, pardt1);
            reptodb.setDate(7, pardt2);
            reptodb.executeUpdate();
            
          
            studefrepclas=Sccls;
   
               t1=new Thread(this,"teacherDefAttrep");
               
                t1.start();
}else
            {
            stat="no record found for selected dates/Class.";
            }
        } catch (SQLException ex) {
           stat="Server error";
        } finally {
            return stat;
        }
    }

    @Override
    public String[][] getTeaOwnRep(String get) throws Exception {
        String[][] data = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");
            Statement repdat = con.createStatement();
            ResultSet rsrepdat = repdat.executeQuery("select month,year,dept,from_dt,to_dt from teacherreport where dept='" + get + "'");
            if (rsrepdat.next()) {
                rsrepdat.last();
                int ct = rsrepdat.getRow();
                rsrepdat.first();
                data = new String[ct][5];
                for (int cts = 0; cts < ct; cts++) {
                    String mn = rsrepdat.getString(1);
                    int yr = rsrepdat.getInt(2);
                    String dep = rsrepdat.getString(3);
                    data[cts][0] = mn;
                    data[cts][1] = String.valueOf(yr);
                    data[cts][2] = dep;
                    data[cts][3] = String.valueOf(rsrepdat.getDate(4));
                    data[cts][4] = String.valueOf(rsrepdat.getDate(5));

                    rsrepdat.next();
                }
            } else {
                return (null);
            }

        } catch (Exception e) {

        } finally {
            return data;
        }
    }

    @Override
    public String[][] getAdmStuDefData() throws Exception {
        String[][] data = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");
            Statement repdat = con.createStatement();
            ResultSet rsrepdat = repdat.executeQuery("select teach_id,month,year,class,from_dt,to_dt from defaultersreport");
            String fn = null;

            if (rsrepdat.next()) {
                rsrepdat.last();
                int ct = rsrepdat.getRow();
                rsrepdat.first();
                data = new String[ct][7];
                for (int cts = 0; cts < ct; cts++) {
                    String tid = rsrepdat.getString(1);
                    String mn = rsrepdat.getString(2);
                    int yr = rsrepdat.getInt(3);
                    String cls = rsrepdat.getString(4);

                    Statement pstid = con.createStatement();
                    ResultSet rspstid = pstid.executeQuery("select first_n from teacher where teach_id='" + tid + "'");
                    if (rspstid.next()) {
                        fn = rspstid.getString(1);
                        data[cts][0] = tid;
                        data[cts][1] = fn;
                        data[cts][2] = mn;
                        data[cts][3] = String.valueOf(yr);
                        data[cts][4] = cls;
                        data[cts][5] = String.valueOf(rsrepdat.getDate(5));
                        data[cts][6] = String.valueOf(rsrepdat.getDate(6));

                    }
                    System.out.println(cls);
                    System.out.println(fn);
                    System.out.println(tid);
                    System.out.println(mn);
                    rsrepdat.next();
                }

            } else {
                data[0][0] = "no data";
            }

        } catch (Exception e) {

        } finally {
            return data;
        }
    }

    @Override
    public byte[] getStuDefFile(String teaid, String mn, String yr, String cls) throws Exception {
        File f = null;
        String path;
        byte buffer[] = null;
        try {

            int tr = Integer.parseInt(yr);
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");
            Statement repdat = con.createStatement();
            ResultSet rsrepdat = repdat.executeQuery("select path from defaultersreport where teach_id='" + teaid + "' and month='" + mn + "' and year=" + tr + " and class='" + cls + "'");
            if (rsrepdat.next()) {
                path = rsrepdat.getString(1);
                f = new File(path);
                System.out.println(f.getAbsolutePath());
                buffer = new byte[(int) f.length()];
                BufferedInputStream inputFileStream = new BufferedInputStream(new FileInputStream(path));
                inputFileStream.read(buffer, 0, buffer.length);
                inputFileStream.close();

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return buffer;
    }

    @Override
    public String[][] getAdmRepData() throws Exception {
        String[][] data = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");
            Statement repdat = con.createStatement();
            ResultSet rsrepdat = repdat.executeQuery("select teach_id,month,year,class,from_dt,to_dt from studentreport");
            String fn = null;

            if (rsrepdat.next()) {
                rsrepdat.last();
                int ct = rsrepdat.getRow();
                rsrepdat.first();
                data = new String[ct][7];
                for (int cts = 0; cts < ct; cts++) {
                    String tid = rsrepdat.getString(1);
                    String mn = rsrepdat.getString(2);
                    int yr = rsrepdat.getInt(3);
                    String cls = rsrepdat.getString(4);

                    Statement pstid = con.createStatement();
                    ResultSet rspstid = pstid.executeQuery("select first_n from teacher where teach_id='" + tid + "'");
                    if (rspstid.next()) {
                        fn = rspstid.getString(1);
                        data[cts][0] = tid;
                        data[cts][1] = fn;
                        data[cts][2] = mn;
                        data[cts][3] = String.valueOf(yr);
                        data[cts][4] = cls;
                        data[cts][5] = String.valueOf(rsrepdat.getDate(5));
                        data[cts][6] = String.valueOf(rsrepdat.getDate(6));
                    }
                    System.out.println(cls);
                    System.out.println(fn);
                    System.out.println(tid);
                    System.out.println(mn);

                    rsrepdat.next();

                }

            } else {
                data[0][0] = "no data";
            }

        } catch (Exception e) {

        } finally {
            return data;
        }
    }

    @Override
    public byte[] getAdmStuRepFile(String teaid, String mn, String yr, String cls) throws Exception {
        File f = null;
        String path;
        byte buffer[] = null;
        try {

            int tr = Integer.parseInt(yr);
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");
            Statement repdat = con.createStatement();
            ResultSet rsrepdat = repdat.executeQuery("select path from studentreport where teach_id='" + teaid + "' and month='" + mn + "' and year=" + tr + " and class='" + cls + "'");
            if (rsrepdat.next()) {
                path = rsrepdat.getString(1);
                f = new File(path);
                System.out.println(f.getAbsolutePath());
                buffer = new byte[(int) f.length()];
                BufferedInputStream inputFileStream = new BufferedInputStream(new FileInputStream(path));
                inputFileStream.read(buffer, 0, buffer.length);
                inputFileStream.close();

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return buffer;
    }

    @Override
    public String[][] getDefStuRepDataTe(String tid) throws Exception {
        String[][] data = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");
            Statement repdat = con.createStatement();
            ResultSet rsrepdat = repdat.executeQuery("select teach_id,month,year,class,from_dt,to_dt from defaultersreport");
            String fn = null;

            if (rsrepdat.next()) {
                rsrepdat.last();
                int ct = rsrepdat.getRow();
                rsrepdat.first();
                data = new String[ct][7];
                for (int cts = 0; cts < ct; cts++) {
                    String tids = rsrepdat.getString(1);
                    String mn = rsrepdat.getString(2);
                    int yr = rsrepdat.getInt(3);
                    String cls = rsrepdat.getString(4);

                    Statement pstid = con.createStatement();
                    ResultSet rspstid = pstid.executeQuery("select first_n from teacher where teach_id='" + tid + "'");
                    if (rspstid.next()) {
                        fn = rspstid.getString(1);
                        data[cts][0] = tid;
                        data[cts][1] = fn;
                        data[cts][2] = mn;
                        data[cts][3] = String.valueOf(yr);
                        data[cts][4] = cls;
                        data[cts][5] = String.valueOf(rsrepdat.getDate(5));
                        data[cts][6] = String.valueOf(rsrepdat.getDate(6));

                    }
                    System.out.println(cls);
                    System.out.println(fn);
                    System.out.println(tid);
                    System.out.println(mn);
                    rsrepdat.next();
                }

            } else {
                data[0][0] = "no data";
            }

        } catch (Exception e) {

        } finally {
            return data;
        }
    }

    @Override
    public byte[] getGenDeRepTe(String Teaid, String mn, String yr, String cls) {
        File f = null;
        String path;
        byte buffer[] = null;
        try {

            int tr = Integer.parseInt(yr);
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");
            Statement repdat = con.createStatement();
            ResultSet rsrepdat = repdat.executeQuery("select path from defaultersreport where teach_id='" + Teaid + "' and month='" + mn + "' and year=" + tr + " and class='" + cls + "'");
            if (rsrepdat.next()) {
                path = rsrepdat.getString(1);
                f = new File(path);
                System.out.println(f.getAbsolutePath());
                buffer = new byte[(int) f.length()];
                BufferedInputStream inputFileStream = new BufferedInputStream(new FileInputStream(path));
                inputFileStream.read(buffer, 0, buffer.length);
                inputFileStream.close();

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return buffer;
    }

    @Override
    public String delGenStuRepTe(String Teaid, String mn, String yr, String cls) {
        File f = null;
        String path = null, stat = " ";

        try {

            int tr = Integer.parseInt(yr);
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");
            Statement repdat = con.createStatement();
            ResultSet rsrepdat = repdat.executeQuery("select path from defaultersreport where teach_id='" + Teaid + "' and month='" + mn + "' and year=" + tr + " and class='" + cls + "'");
            if (rsrepdat.next()) {
                path = rsrepdat.getString(1);
                f = new File(path);
            }
            if (f.delete()) {
                Statement strm = con.createStatement();
                strm.executeUpdate("delete from defaultersreport where month='" + mn + "' and year=" + tr + " and class='" + cls + "'");

                stat = "File deleted Successfully";

            } else {
                stat = "could not delete file";
            }
        } catch (Exception ex) {
            stat = "no file found";
        } finally {
            return stat;
        }
    }

    @Override
    public String[][] getStuAttRepDataTe(String get) throws Exception {
        String[][] data = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");
            Statement repdat = con.createStatement();
            ResultSet rsrepdat = repdat.executeQuery("select teach_id,month,year,class,from_dt,to_dt from studentreport");
            String fn = null;

            if (rsrepdat.next()) {
                rsrepdat.last();
                int ct = rsrepdat.getRow();
                rsrepdat.first();
                data = new String[ct][7];
                for (int cts = 0; cts < ct; cts++) {
                    String tids = rsrepdat.getString(1);
                    String mn = rsrepdat.getString(2);
                    int yr = rsrepdat.getInt(3);
                    String cls = rsrepdat.getString(4);

                    Statement pstid = con.createStatement();
                    ResultSet rspstid = pstid.executeQuery("select first_n from teacher where teach_id='" + get + "'");
                    if (rspstid.next()) {
                        fn = rspstid.getString(1);
                        data[cts][0] = get;
                        data[cts][1] = fn;
                        data[cts][2] = mn;
                        data[cts][3] = String.valueOf(yr);
                        data[cts][4] = cls;
                        data[cts][5] = String.valueOf(rsrepdat.getDate(5));
                        data[cts][6] = String.valueOf(rsrepdat.getDate(6));
                    }
                    System.out.println(cls);
                    System.out.println(fn);
                    System.out.println(get);
                    System.out.println(mn);
                    rsrepdat.next();
                }

            } else {
                data[0][0] = "no data";
            }

        } catch (Exception e) {

        } finally {
            return data;
        }
    }

    @Override
    public byte[] getStuAttRepFileTe(String Teaid, String mn, String yr, String cls) throws Exception {
        File f = null;
        String path;
        byte buffer[] = null;
        try {

            int tr = Integer.parseInt(yr);
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");
            Statement repdat = con.createStatement();
            ResultSet rsrepdat = repdat.executeQuery("select path from studentreport where teach_id='" + Teaid + "' and month='" + mn + "' and year=" + tr + " and class='" + cls + "'");
            if (rsrepdat.next()) {
                path = rsrepdat.getString(1);
                f = new File(path);
                System.out.println(f.getAbsolutePath());
                buffer = new byte[(int) f.length()];
                BufferedInputStream inputFileStream = new BufferedInputStream(new FileInputStream(path));
                inputFileStream.read(buffer, 0, buffer.length);
                inputFileStream.close();

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return buffer;
    }

    @Override
    public String delStuAttRepFileTe(String Teaid, String mn, String yr, String cls) throws Exception {
        File f = null;
        String path = null, stat = " ";

        try {

            int tr = Integer.parseInt(yr);
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");
            Statement repdat = con.createStatement();
            ResultSet rsrepdat = repdat.executeQuery("select path from studentreport where teach_id='" + Teaid + "' and month='" + mn + "' and year=" + tr + " and class='" + cls + "'");
            if (rsrepdat.next()) {
                path = rsrepdat.getString(1);
                f = new File(path);
            }
            if (f.delete()) {
                Statement strm = con.createStatement();
                strm.executeUpdate("delete from studentreport where month='" + mn + "' and year=" + tr + " and class='" + cls + "'");

                stat = "File deleted Successfully";

            } else {
                stat = "could not delete file";
            }
        } catch (Exception ex) {
            stat = "no file found";
        } finally {
            return stat;
        }
    }

    @Override
    public String[][] getStuAttRepDataSt(String usid, String cls) throws Exception {
        String data[][] = null;

        try {
            DefaultTableModel dtm = new DefaultTableModel(0, 0);
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");
            Statement strepdat = con.createStatement();
            ResultSet rstrepdat = strepdat.executeQuery("select teach_id,month,year,from_dt,to_dt from studentreport where class='" + cls + "'");
            {
                if (rstrepdat.next()) {
                    rstrepdat.last();
                    int count = rstrepdat.getRow();

                    data = new String[count][7];
                    for (int ct = 0; ct < count; ct++) {
                        String tusrid = null, tfname = null;
                        String tid = rstrepdat.getString(1);
                        String month = rstrepdat.getString(2);
                        int yr = rstrepdat.getInt(3);

                        java.sql.Date dt1 = rstrepdat.getDate(4);
                        java.sql.Date dt2 = rstrepdat.getDate(5);

                        Statement getid = con.createStatement();
                        ResultSet rsgetid = getid.executeQuery("select user_id,first_n from teacher where teach_id='" + tid + "'");
                        if (rsgetid.next()) {
                            tusrid = rsgetid.getString(1);
                            tfname = rsgetid.getString(2);
                        }

                        Statement stsattpr = con.createStatement();

                        ResultSet rstsattpr = stsattpr.executeQuery("select * from studentattendance where date between '" + dt1 + "' and '" + dt2 + "' and user_id='" + usid + "' and class='" + cls + "' and teach_usrid='" + tusrid + "'");
                        if (rstsattpr.next()) {
                            data[ct][0] = tid;
                            data[ct][1] = tfname;
                            data[ct][2] = month;
                            data[ct][3] = String.valueOf(yr);
                            data[ct][4] = cls;
                            data[ct][5] = String.valueOf(dt1);
                            data[ct][6] = String.valueOf(dt2);

                            rstrepdat.next();
                        } else {
                            rstrepdat.next();
                        }

                    }

                } else {
                    return (null);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return data;

        }
    }

    @Override
    public String[][] getStuDefRepDataSt(String usid, String cls) throws Exception {
        String data[][] = null;

        try {
            DefaultTableModel dtm = new DefaultTableModel(0, 0);
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");
            Statement strepdat = con.createStatement();
            ResultSet rstrepdat = strepdat.executeQuery("select teach_id,month,year,from_dt,to_dt from defaultersreport where class='" + cls + "'");
            {
                if (rstrepdat.next()) {
                    rstrepdat.last();
                    int count = rstrepdat.getRow();

                    data = new String[count][7];
                    for (int ct = 0; ct < count; ct++) {
                        String tusrid = null, tfname = null;
                        String tid = rstrepdat.getString(1);
                        String month = rstrepdat.getString(2);
                        int yr = rstrepdat.getInt(3);

                        java.sql.Date dt1 = rstrepdat.getDate(4);
                        java.sql.Date dt2 = rstrepdat.getDate(5);

                        Statement getid = con.createStatement();
                        ResultSet rsgetid = getid.executeQuery("select user_id,first_n from teacher where teach_id='" + tid + "'");
                        if (rsgetid.next()) {
                            tusrid = rsgetid.getString(1);
                            tfname = rsgetid.getString(2);
                        }

                        Statement stsattpr = con.createStatement();

                        ResultSet rstsattpr = stsattpr.executeQuery("select * from studentattendance where date between '" + dt1 + "' and '" + dt2 + "' and user_id='" + usid + "' and class='" + cls + "' and teach_usrid='" + tusrid + "'");
                        if (rstsattpr.next()) {
                            data[ct][0] = tid;
                            data[ct][1] = tfname;
                            data[ct][2] = month;
                            data[ct][3] = String.valueOf(yr);
                            data[ct][4] = cls;
                            data[ct][5] = String.valueOf(dt1);
                            data[ct][6] = String.valueOf(dt2);

                            rstrepdat.next();
                        } else {
                            rstrepdat.next();
                        }

                    }

                } else {
                    return (null);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return data;

        }
    }

    @Override
    public String[][] getMemData(String mem, String depcls, String cou) throws Exception {
        String[][] data = null;
        try {
            switch (mem) {
                case "Teacher": {
                    Class.forName("com.mysql.jdbc.Driver");
                    System.out.println("Connecting to a selected database...");
                    con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
                    System.out.println("Connected database successfully...");
                    Statement strepdat = con.createStatement();
                    ResultSet rstrepdat = strepdat.executeQuery("select * from teacher where dept_n='" + depcls + "'");
                    if (rstrepdat.next()) {
                        rstrepdat.last();
                        int ct = rstrepdat.getRow();
                        rstrepdat.first();
                        data = new String[ct][7];
                        for (int count = 0; count < ct; count++) {
                            data[count][0] = rstrepdat.getString(1);
                            data[count][1] = rstrepdat.getString(2);
                            data[count][2] = rstrepdat.getString(3);
                            data[count][3] = rstrepdat.getString(4);
                            data[count][4] = rstrepdat.getString(5);
                            data[count][5] = rstrepdat.getString(6);
                            data[count][6] = String.valueOf(rstrepdat.getLong(7));
                            rstrepdat.next();

                        }

                    }
                    break;
                }
                case "Student": {
                    System.out.println("Connecting to a selected database...");
                    con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
                    System.out.println("Connected database successfully...");
                    Statement strepdat = con.createStatement();
                    ResultSet rstrepdat = strepdat.executeQuery("select * from student where class='" + depcls + "' and courses='" + cou + "'");
                    if (rstrepdat.next()) {
                        rstrepdat.last();
                        int ct = rstrepdat.getRow();
                        rstrepdat.first();
                        data = new String[ct][7];
                        for (int count = 0; count < ct; count++) {
                            data[count][0] = rstrepdat.getString(1);
                            data[count][1] = rstrepdat.getString(2);
                            data[count][2] = rstrepdat.getString(3);
                            data[count][3] = rstrepdat.getString(4);
                            data[count][4] = rstrepdat.getString(5);
                            data[count][5] = String.valueOf(rstrepdat.getInt(6));
                            data[count][6] = rstrepdat.getString(7);
                            rstrepdat.next();

                        }
                    }
                    break;
                }
                case "Staff": {
                    System.out.println("Connecting to a selected database...");
                    con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
                    System.out.println("Connected database successfully...");
                    Statement strepdat = con.createStatement();
                    ResultSet rstrepdat = strepdat.executeQuery("select * from staff");
                    if (rstrepdat.next()) {
                        rstrepdat.last();

                        data = new String[1][6];

                        data[0][0] = rstrepdat.getString(1);
                        data[0][1] = rstrepdat.getString(2);
                        data[0][2] = rstrepdat.getString(3);
                        data[0][3] = rstrepdat.getString(4);
                        data[0][4] = String.valueOf(rstrepdat.getLong(5));
                        data[0][5] = rstrepdat.getString(6);

                        rstrepdat.next();

                    }
                    break;
                }
                default:
                    break;
            }

        } catch (Exception ex) {
        } finally {
            return data;
        }

    }

    @Override
    public void run() {
        
    
        
       if(t1.getName().equals("admin"))
       {
      synchronized(depdat){ GenTeaRepThr(depdat);
       }}else if(t1.getName().equals("teacherAttrep"))
       {
       synchronized(sturepthrdat){ GenStuAttRepThr(sturepclas,stureptean,sturepteadep,sturepthrdat);}
       }
       else if(t1.getName().equals("teacherDefAttrep"))
       {
       synchronized(studefrepthrdat){ GenStuDefAttRepThr(studefrepclas,studefreptean,studefrepteadep,studefrepthrdat); }
       } //}
    }

    private void GenTeaRepThr(String dept) {
        int coun;
        String[] ar = null;
       try{
        Class.forName("com.mysql.jdbc.Driver");
                    System.out.println("Connecting to a selected database...");
                    con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
                    System.out.println("Connected database successfully...");
                    System.out.println("thread");
                    System.out.println(dept);
                    Statement stdat = con.createStatement();
                    ResultSet rstdat = stdat.executeQuery("select e_mail from teacher where dept_n='" + dept + "'");
                    if (rstdat.next()) {
                        rstdat.last();
                        System.out.println("inside if");
                    coun=rstdat.getRow();
                    ar=new String[coun];
                    rstdat.first();
                      System.out.println("count "+coun);
                    for(int ct=0;ct<coun;ct++)
                    {
                        System.out.println(rstdat.getString(1));
                    ar[ct]=rstdat.getString(1);
                    rstdat.next();
                    }
                System.out.println("got ar value "+ar[1]);
                       final String username = "amspassrecovery@gmail.com";
        final String password = "ams@passrec";

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
          });

        try {
           
             for(int i=0;i<ar.length;i++)
            {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("amspassrecovery@gmail.com"));
           
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(ar[i]));
            message.setSubject("Report notification.");
            message.setText("Teacher report has been generated. \n Click view reports after logging into Attendance Management System.");

            Transport.send(message);

            System.out.println("Done");
            }
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    
                    }
                    else{System.out.println("Inside else");}
       }
       catch(Exception ex)
       {
       }
       }

    private void GenStuAttRepThr(String sturepclas, String stureptean, String sturepteadep, String[] sturepthrdat) {
        try{
             Class.forName("com.mysql.jdbc.Driver");
                    System.out.println("Connecting to a selected database...");
                    con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
                    System.out.println("Connected database successfully...");
                     Statement stdat = con.createStatement();
                    ResultSet rstdat = stdat.executeQuery("select e_mail from admin");
                    if (rstdat.next()) {
                    String eml=rstdat.getString(1);
                       final String username = "amspassrecovery@gmail.com";
        final String password = "ams@passrec";

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
          });
                     try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("amspassrecovery@gmail.com"));
           
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(eml));
            message.setSubject("Report notification.");
            message.setText("Student attendance report has been generated."
                    + "\n by "+stureptean
                    + "\n Department "+sturepteadep
                    + "\n for class "+sturepclas
                    + " \n Click view student attendance reports after logging into Attendance Management System.");

            Transport.send(message);

            System.out.println("Done");
       
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
                    }
                    int cout; 
                    String[] unique = Arrays.stream(sturepthrdat).distinct().toArray(String[]::new);
                    String[] stueml = new String[unique.length];
                   
                    System.out.println(unique.length);
                     System.out.println(stueml.length);
                     Statement stdat1 = con.createStatement();
                     for(int io=0;io<unique.length;io++)
                     {
                    ResultSet rstdat1 = stdat1.executeQuery("select e_mail from student where user_id='"+unique[io]+"'");
                     if(rstdat1.next())
                     {
                    stueml[io]=rstdat1.getString(1);
                     }
                    }
                    
                      final String username = "amspassrecovery@gmail.com";
        final String password = "ams@passrec";

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
          });
                     try {
                          for(int its=0;its<stueml.length;its++)
            {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("amspassrecovery@gmail.com"));
           
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse( stueml[its]));
            message.setSubject("Report notification.");
            message.setText("Student attendance report has been generated."
                    + "\n by "+stureptean
                    + "\n Department "+sturepteadep
                    + "\n for class "+sturepclas
                    + " \n Click view  attendance reports after logging into Attendance Management System.");

            Transport.send(message);

            System.out.println("Done");
            }
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

                    
                        
        
        
        }catch(Exception ex){}
    }

    private void GenStuDefAttRepThr(String sturepclas, String stureptean, String sturepteadep, String[] sturepthrdat) {
        try{
             Class.forName("com.mysql.jdbc.Driver");
                    System.out.println("Connecting to a selected database...");
                    con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
                    System.out.println("Connected database successfully...");
                     Statement stdat = con.createStatement();
                    ResultSet rstdat = stdat.executeQuery("select e_mail from admin");
                    if (rstdat.next()) {
                    String eml=rstdat.getString(1);
                       final String username = "amspassrecovery@gmail.com";
        final String password = "ams@passrec";

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
          });
                     try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("amspassrecovery@gmail.com"));
           
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(eml));
            message.setSubject("Report notification.");
            message.setText("Student defaulters report has been generated."
                    + "\n by "+stureptean
                    + "\n Department "+sturepteadep
                    + "\n for class "+sturepclas
                    + " \n Click view student defaulters reports after logging into Attendance Management System.");

            Transport.send(message);

            System.out.println("Done");
       
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
                    }
                    int cout; 
                    String[] unique = Arrays.stream(sturepthrdat).distinct().toArray(String[]::new);
                    String[] stueml = new String[unique.length];
                   
                    System.out.println(unique.length);
                     System.out.println(stueml.length);
                     Statement stdat1 = con.createStatement();
                     for(int io=0;io<unique.length;io++)
                     {
                    ResultSet rstdat1 = stdat1.executeQuery("select e_mail from student where user_id='"+unique[io]+"'");
                     if(rstdat1.next())
                     {
                    stueml[io]=rstdat1.getString(1);
                     }
                    }
                    
                      final String username = "amspassrecovery@gmail.com";
        final String password = "ams@passrec";

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
          });
                     try {
                          for(int its=0;its<stueml.length;its++)
            {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("amspassrecovery@gmail.com"));
           
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse( stueml[its]));
            message.setSubject("Report notification.");
            message.setText("Student defaulters' report has been generated."
                    + "\n by "+stureptean
                    + "\n Department "+sturepteadep
                    + "\n for class "+sturepclas
                    + " \n Click view  Defaulters reports after logging into Attendance Management System.");

            Transport.send(message);

            System.out.println("Done");
            }
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

                    
                        
        
        
        }catch(Exception ex){}  
    }
    }

