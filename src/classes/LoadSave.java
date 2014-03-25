package classes;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class LoadSave {

	
	public static GamerInfo load(){
		
		GamerInfo gamerInfo = null;
	      try
	      {
	         FileInputStream fileIn = new FileInputStream("gameInfo.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         gamerInfo = (GamerInfo) in.readObject();
	         in.close();
	         fileIn.close();
	      }
	      catch(IOException i)
	      {
	    	  gamerInfo = new GamerInfo();//if files does not exist create new info
	         
	      }
	      catch(ClassNotFoundException c)
	      {
	         System.out.println("Class not found");
	         c.printStackTrace();
	         return null;
	      }
		return gamerInfo;
	}
	
	
	public static void save(GamerInfo gamerInfo){
	      
	      try
	      {
	         FileOutputStream fileOut = new FileOutputStream("gameInfo.ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(gamerInfo);
	         out.close();
	         fileOut.close();
	      }
	      catch(IOException i)
	      {
	          i.printStackTrace();
	      }
	      
	}
}
