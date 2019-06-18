
import java.util.ArrayList;

 class Multi3 implements Runnable { 
     Thread  t1;
   static final String[] val=new String[4];
    
    public  Multi3()
    {   
      val[0]="1"; 
       val[1]="2"; 
        val[2]="3"; 
         val[3]="4"; 
   t1=new Thread(this,"one");
         t1.start();
         
     System.out.println("working");    
       
     
     }
    public void data(String[] abc){   
    
         for(int i=0;i<abc.length;i++)
        {
          System.out.println(abc[i]);}
    }
 
    
public static void main(String args[]){  
 new Multi3();


}

    @Override
    public void run() {
     System.out.println(t1.getName());
          synchronized(val){
                  data(val); }
           
    }
 }  
