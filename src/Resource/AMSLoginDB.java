/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Resource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException; 
import java.util.UUID;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
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
public class AMSLoginDB {

   Connection con;
   Date d=new Date();
    boolean sa;
    public AMSLoginDB()  {
  /*  Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb","root","");
            System.out.println("Connected database successfully...");
            
            
            Statement teaAtt = con.createStatement();
            ResultSet rsteaAtt = teaAtt.executeQuery("select path from defaultersreport");
            
            while(rsteaAtt.next())
            {
            System.out.println(rsteaAtt.getString(1));
            
            }        
            java.sql.Date dt=new   java.sql.Date(2017,02,04);
        System.out.println(dt);
              System.out.println(String.valueOf(dt));
        
        /*    String[] Head={"Teacher Id","fn","ln","dte","intm","outm","hrs"};
        
        DefaultTableModel dtm=new DefaultTableModel();
        dtm.setColumnIdentifiers(Head);
        
        
        
        
        
        dtm.addRow(new Object[]{"B","C","D","E","F","10:10:10","H"});
        dtm.addRow(new Object[]{"B","C","D","E","F","G","H"});
         dtm.addRow(new Object[]{"B","C","D","E","F","G","H"}); dtm.addRow(new Object[]{"B","C","D","E","F","G","H"});
          dtm.addRow(new Object[]{"B","C","D","E","F","G","H"});
           dtm.addRow(new Object[]{"B","C","D","E","F","G","H"});
            dtm.addRow(new Object[]{"B","C","D","E","F","G","H"});
             dtm.addRow(new Object[]{"B","C","D","E","F","G","H"});
              dtm.addRow(new Object[]{"B","C","D","E","F","G","H"});
               dtm.addRow(new Object[]{"B","C","D","E","F","G","H"});
                dtm.addRow(new Object[]{"B","C","D","E","F","G","H"});
                 dtm.addRow(new Object[]{"B","C","D","E","F","G","H"});  dtm.addRow(new Object[]{"B","C","D","E","F","G","H"});
           dtm.addRow(new Object[]{"B","C","D","E","F","G","H"});
            dtm.addRow(new Object[]{"B","C","D","E","F","G","H"});
             dtm.addRow(new Object[]{"B","C","D","E","F","G","H"});
              dtm.addRow(new Object[]{"B","C","D","E","F","G","H"});
               dtm.addRow(new Object[]{"B","C","D","E","F","G","H"});
                dtm.addRow(new Object[]{"B","C","D","E","F","G","H"});
                 dtm.addRow(new Object[]{"B","C","D","E","F","G","H"});  dtm.addRow(new Object[]{"B","C","D","E","F","G","H"});
           dtm.addRow(new Object[]{"B","C","D","E","F","G","H"});
            dtm.addRow(new Object[]{"B","C","D","E","F","G","H"});
             dtm.addRow(new Object[]{"B","C","D","E","F","G","H"});
              dtm.addRow(new Object[]{"B","C","D","E","F","G","H"});
               dtm.addRow(new Object[]{"B","C","D","E","F","G","H"});
                dtm.addRow(new Object[]{"B","C","D","E","F","G","H"});
                 dtm.addRow(new Object[]{"B","C","D","E","F","G","H"});
                  dtm.addRow(new Object[]{"B","C","D","E","F","G","H"});
                   dtm.addRow(new Object[]{"B","C","D","E","F","G","H"}); dtm.addRow(new Object[]{"B","C","D","E","F","G","H"}); dtm.addRow(new Object[]{"B","C","D","E","F","G","H"});
                    dtm.addRow(new Object[]{"B","C","D","E","F","G","H"});
                   
                   
         
        JTable tab=new JTable(dtm);
        
    try {
        JRTableModelDataSource dataSource = new JRTableModelDataSource(
                tab.getModel());

        String reportSource = "C:\\Users\\Anonymous\\Documents\\NetBeansProjects\\AttenManSys\\src\\Resource\\TeacherAtnRep.jrxml";
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("mon","Month of Jan-Feb");
          

        JasperReport jasperReport = JasperCompileManager.compileReport(reportSource);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
        JasperViewer.viewReport(jasperPrint, false);
     //   String path=System.getProperty("user.dir");
JasperExportManager.exportReportToPdfFile(jasperPrint,"C:\\Users\\Anonymous\\Documents\\Report.pdf");
    } catch (Exception e) {
        e.printStackTrace();
    }
     */

//  String repPath="C:\\Users\\Anonymous\\Documents\\NetBeansProjects\\AttenManSys\\src\\Database\\report1.jrxml";
        ///try {
           //  Class.forName("com.mysql.jdbc.Driver");
            //System.out.println("Connecting to a selected database...");
            //con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb","root","");
            //System.out.println("Connected database successfully...");
         //   JasperReport jr=JasperCompileManager.compileReport(repPath);
            //JasperPrint jp=JasperFillManager.fillReport(repPath, null, con);
            //JasperViewer.viewReport(jp);
            
            /*      try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb","root","");
            System.out.println("Connected database successfully...");
            System.out.println("Inserting records into the table...");
            Statement st = con.createStatement();
            ResultSet rs= st.executeQuery("select * from teacherattendance where date between '2017-02-13'  and '2017-02-13'");
            Time t = null, t1;
            SimpleDateFormat    df = new SimpleDateFormat("HH:mm:ss");
            
            
            while (rs.next()) {
            System.out.println(df.format(rs.getTimestamp(6)));
            //    System.out.println(d);
            //  System.out.println(s1);
            // select count(date), sum(time_to_sec(timediffs)) from teacherattendance where date BETWEEN '2017-02-12' and '2017-02-12' GROUP by user_id
            
            
            
            }
            con.close();
            st.close();
            } catch (ClassNotFoundException ex) {
            
            System.out.println("Class not found");
            ex.printStackTrace();
            } catch (SQLException ex) {
            System.out.println("SQL exception");
            ex.printStackTrace();
            }
            
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb","root","");
            System.out.println("Connected database successfully...");
            
            
            Statement teaAtt = con.createStatement();
            ResultSet rsteaAtt = teaAtt.executeQuery("select f_name,l_name,dept,date,time_to_sec(in_time),time_to_sec(out_time),time_to_sec(timediffs) from teacherattendance where user_id='abc'");
            
            while(rsteaAtt.next())
            {
            
            
            
            
            Date dt=rsteaAtt.getDate(4);
            int intm=  rsteaAtt.getInt(5);
            int outm = rsteaAtt.getInt(6);
            int diftm=  rsteaAtt.getInt(7);
            
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
            
            hors = (int) (diftm / 3600);
            mintes = (int) ((diftm % 3600) / 60);
            secnds = (int) (diftm % 60);
            System.out.println(hors + " " + mintes + " " + secnds);
            String timeString3 = String.format("%02d:%02d:%02d", hors, mintes, secnds);
            
            
            System.out.println(dt.toString());
            System.out.println(timeString1);
            System.out.println(timeString2);
            System.out.println(timeString3);
            
            
            /*
            
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            con = DriverManager.getConnection("jdbc:mysql://localhost/amsdb", "root", "");
            System.out.println("Connected database successfully...");
            
            
            
            ArrayList<ArrayList<String>> ab;
            Statement allst=con.createStatement();
            ResultSet alltea=allst.executeQuery("select * from teacher");
            
            alltea.last();
            int lasrw=alltea.getRow() ;
            System.out.println(lasrw);
            alltea.first();
            
            ab=new ArrayList<ArrayList<String>>();
            
            for(int firct = 0;firct<lasrw;firct++)
            {   ab.add(new ArrayList<String>());
            ab.get(firct).add(alltea.getString(1));
            ab.get(firct).add(alltea.getString(2));
            ab.get(firct).add(alltea.getString(3));
            ab.get(firct).add(alltea.getString(4));
            ab.get(firct).add(alltea.getString(5));
            
            
            alltea.next();
            }
            System.out.println(ab.get(2).get(3));
            
            
            ArrayList<ArrayList<String>> ar1=new ArrayList<ArrayList<String>>();
            
            ar1.add(new ArrayList<String>());
            ar1.get(0).add("VAL2");
            
            for(String val:ar1.get(0))
            {
            if(ab.get(0).contains(val))
            {
            System.out.println("yes");
            }
            }
            
            /*        PreparedStatement ps=con.prepareStatement("insert into datab values(?,?,?)");
            
            SimpleDateFormat    df = new SimpleDateFormat("yyyy/MM/dd");
            String ds=df.format(d);
            System.out.println(ds);
            SimpleDateFormat    dt = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat    df1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date parsed=df.parse(ds);
            java.sql.Date d1=new java.sql.Date(parsed.getTime());
            
            ps.setDate(1,d1);
            
            String dt1="2:12:12";
            Date part=dt.parse(dt1);
            System.out.println(part.getTime());
            java.sql.Time dtq=new java.sql.Time(part.getTime());
            System.out.println(dtq);
            ps.setTime(2,dtq);
            
            
            String dt2=ds+" "+"12:12:12";
            Date part1=df1.parse(dt2);
            java.sql.Timestamp dtq1=new java.sql.Timestamp(part1.getTime());
            
            
            ps.setTimestamp(3,dtq1);
            ps.executeUpdate();
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime1= LocalDateTime.parse("2014-11-25 9:45:34", formatter);
            LocalDateTime dateTime2= LocalDateTime.parse("2014-11-25 6:43:23", formatter);
            
            long diffInMilli = java.time.Duration.between(dateTime1, dateTime2).toMillis();
            long diffInSeconds = java.time.Duration.between(dateTime2, dateTime1).getSeconds();
            long diffInMinutes = java.time.Duration.between(dateTime1, dateTime2).toMinutes();
            
            int hours = (int) (diffInSeconds / 3600);
            int minutes = (int) ((diffInSeconds % 3600) / 60);
            int seconds = (int) (diffInSeconds % 60);
            
            String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            
            
            System.out.println(timeString);*/
      
            
           }

    public static void main(String[] abc)  {
     int a=5;
     System.out.println((float)a);
      
    }}
   /*     String a="9383736636";
        
       System.out.println();
        
String uuid = UUID.randomUUID().toString();
String[] ddt=uuid.split("-");

String usn = "";
for(int i=0;i<ddt.length;i++)
{
    usn=usn+ddt[i];
}

System.out.println("uuid = " + usn);*/


