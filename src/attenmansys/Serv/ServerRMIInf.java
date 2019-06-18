/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attenmansys.Serv;

/**
 *
 * @author Anonymous
 */
import java.io.File;
import java.rmi.Remote;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JTable;

/**
 *
 * @author Anonymous
 */
public interface ServerRMIInf extends Remote {

    public String Pwdverify(String Emid,String type) throws Exception;

    public ArrayList updverify(String us, String pd, String tp) throws Exception;

    public ArrayList ShowDept(String depts) throws Exception;

    public ArrayList ShowClass(String ClassReq) throws Exception;

    public ArrayList ShowCourses(String Class, String CL) throws Exception;

    public String AddDept(String addDept) throws Exception;

    public String RemoveDept(String RemDept) throws Exception;

    public String EditDept(String old, String NewVal) throws Exception;

    public String AddClass(String NewClass) throws Exception;

    public String AddCourse(String ClsVal, String CourseVal) throws Exception;

    public String EditClass(String ClsOld, String ClassEdt) throws Exception;

    public String RemoveClass(String RemCls) throws Exception;

 

    public String RemoveCourse(String cls,String CourseVal) throws Exception;

    public String AddSingTeacher(String[] teadata) throws Exception;

    public String AddSingStudent(String[] stuData) throws Exception;

    public String AddSingStaff(String[] staffData) throws Exception;

    public String MultiTeaData(String[][] tableData, String dep) throws Exception;

    public String MultiStuData(String[][] tableData, String cls, String course) throws Exception;

    public String EditMember(String mem, String IDTyp, String iD, String Val, String newVal,String cls) throws Exception;

    public String RemoveMember(String MEM, String CLASS, String IDTYP, String ID) throws Exception;

    public String[][] CheckAtten(Date selectedDate, Date selectedDate1, String ab, String hd, String coun) throws Exception;

    public ArrayList<ArrayList<String>> GetTeachData() throws Exception;

    public String GetTime() throws Exception;

    public String GetDate() throws Exception;

    public String TeaAttenToDatabase(String[][] tableData, String dt) throws Exception;

    public ArrayList<ArrayList<String>> getStuData(String cls, String cour) throws Exception;

    public String StuAttenToDatabase(String[][] tableData, String text, String Tusrid,String pass) throws Exception;

    public String[][] CheckStuAttend(Date selectedDate, Date selectedDate1, String toString, String TeaCour, String TeCourData, String ab, String hd, String trim) throws Exception;

    public String[][] CheckTeaOwnAtten(Date selectedDate, Date selectedDate1,String UserId) throws Exception;
    public String[][] CheckStuOwnAtten(Date selectedDate, Date selectedDate1,String UserId) throws Exception;

    public String ChangeAdm(String[] data) throws Exception;

    public String ChgAdmDet(String selec, String newselec, String passs, String userid)throws Exception;

    public String SendingUserCred(String mem, String ran, String passs, String get)throws Exception;

    public String GenTeaAtt(Date datebf,Date dateaf,String dep)throws Exception;

    public String[][] getTeaRepData() throws Exception;

    public byte[] getTeaRepFile(String mn, String yr, String dp) throws Exception;

    public String DelTeaRep(String mn, String yr, String dp) throws Exception;

    public String GenStuRep(Date selectedDate, Date selectedDate1, String Sccls,String tid)throws Exception;

    public String GenStuDefRep(Date selectedDate, Date selectedDate1, String Sccls, String get,String minper)throws Exception;

    public String[][] getTeaOwnRep(String get) throws Exception;

    public String[][] getAdmStuDefData() throws Exception;

    public byte[] getStuDefFile(String teaid, String mn, String yr, String cls)throws Exception;

    public String[][] getAdmRepData()throws Exception;

    public byte[] getAdmStuRepFile(String teaid, String mn, String yr, String cls)throws Exception;

    public String[][] getDefStuRepDataTe(String tid) throws Exception;

    public byte[] getGenDeRepTe(String Teaid, String mn, String yr, String cls)throws Exception;

    public String delGenStuRepTe(String Teaid, String mn, String yr, String cls)throws Exception;

    public String[][] getStuAttRepDataTe(String get)throws Exception;

    public byte[] getStuAttRepFileTe(String Teaid, String mn, String yr, String cls)throws Exception;

    public String delStuAttRepFileTe(String Teaid, String mn, String yr, String cls)throws Exception;

    public String[][] getStuAttRepDataSt(String usid,String cls)throws Exception;

    public String[][] getStuDefRepDataSt(String usid, String cls)throws Exception;

    public String[][] getMemData(String mem,String depcls,String cou)throws Exception;
}
