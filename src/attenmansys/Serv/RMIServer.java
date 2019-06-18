/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attenmansys.Serv;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author Anonymous
 */
public class RMIServer {

    public static void main(String[] abc) {

        try {
            ServerRMIImpl sri = new ServerRMIImpl();
            Registry re = LocateRegistry.createRegistry(9111);
            //login
            re.rebind("empass", sri);

        re.rebind("loginveri", sri);

            //depts
            re.rebind("ShowDept", sri);
            re.rebind("ShowClasses", sri);
            re.rebind("ShowCourses", sri);

            re.rebind("AddDept", sri);
            re.rebind("RemoveDept", sri);
            re.rebind("EditDept", sri);
            re.rebind("AddClass", sri);
            re.rebind("EditClass", sri);
            re.rebind("RemoveClass", sri);
            re.rebind("AddCourse", sri);
            re.rebind("EditCourse", sri);
            re.rebind("RemoveCourse", sri);

            //adding members
            re.rebind("AddSingTea", sri);
            re.rebind("AddSingStu", sri);
            re.rebind("AddSingStaff", sri);
            re.rebind("AddMultiTea", sri);
            re.rebind("AddMultiStu", sri);

            //editing members
            re.rebind("EditMem", sri);

            //deleting members
            re.rebind("RemoveMem", sri);

            //checking attendance
            
            re.rebind("CheckAtten", sri);

            //getting  teach data
            re.rebind("GetTeachData", sri);
            //getting  stu data
            re.rebind("getStuData", sri);

            //getting date
            re.rebind("GetTime", sri);
            re.rebind("GetDate", sri);

            //adding teacher attendance to database
            re.rebind("TeaAttenToDatabase", sri);
            //adding student attendance to database
            re.rebind("StuAttenToDatabase", sri);

            //checking student attendance (admin)
            re.rebind("CheckStuAtten", sri);

            //checkin teacher own attend
            re.rebind("CheckTeaOwnAtten", sri);

            //checkin student own attend
            re.rebind("CheckStuOwnAtten", sri);

            //change admin
            re.rebind("ChangeAdmin", sri);
            //change admin settings 
            re.rebind("ChangeAdminDetails", sri);

            //GEn teacher Rep
            re.rebind("GenTeaRep", sri);

            //get teacher rep data
            re.rebind("getTeaRepData", sri);

            //getting report file
            re.rebind("getTeaRepFile", sri);

            //deleting rep
            re.rebind("DelTeaRep", sri);

            //generating student report
            re.rebind("GenStuRep", sri);

            //generating defaulters
            re.rebind("GenStuDefRep", sri);
            re.rebind("getTeaOwnRep", sri);

            //stu def by adm
            re.rebind("getAdmStuDefData", sri);
            
            //def file by adm
            re.rebind("getAdmStuDefFile", sri);
            
            //stu rep by adm
            re.rebind("getAdmRepData", sri);
            //stu rep file
            re.rebind("getAdmStuRepFile", sri);
            //view stu atten def rep tea
            re.rebind("getDefStuRepDataTe", sri);
            
            //stn atn rep te
            re.rebind("getGenDeRepTe", sri);
            re.rebind("delGenStuRepTe", sri);

             re.rebind("getStuAttRepDataTe", sri);
              re.rebind("getStuAttRepFileTe", sri);
               re.rebind("delStuAttRepFileTe", sri);
            
               
               //stn atn rep stn
               re.rebind("getStuAttRepDataSt", sri);
               
            // stn def rep stn
            
            re.rebind("getStuDefRepDataSt", sri);
               
            //get mem data
            re.rebind("getMemData", sri);
            
            //sending user credentials
            re.rebind("SendingUserCred", sri);
            System.out.println("Server Started Successfully");
        } catch (RemoteException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    

}
