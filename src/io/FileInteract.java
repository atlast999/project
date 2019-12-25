package io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import model.Account;
import model.Class;



public class FileInteract {
	public static boolean saveSerialFile(ArrayList<Class> listClass, String path) {
		try {
			FileOutputStream fos = new FileOutputStream(path);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(listClass);
			
			oos.close();
			fos.close();
			return true;
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}
	
	public static ArrayList<Class> readSerialFile(String path){
		ArrayList<Class> listClass = new ArrayList<Class>();
		try {
			FileInputStream fis = new FileInputStream(path);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Object data = ois.readObject();
			if (data == null) return null;
			listClass = (ArrayList<Class>) data;
			ois.close();
			fis.close();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return listClass;
	}
	
	public static boolean saveSerialFileAcc(ArrayList<Account> listAcc, String path) {
		try {
			FileOutputStream fos = new FileOutputStream(path);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(listAcc);
			
			oos.close();
			fos.close();
			return true;
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}
	
	public static ArrayList<Account> readSerialFileAcc(String path){
		ArrayList<Account> listAcc = new ArrayList<Account>();
		try {
			FileInputStream fis = new FileInputStream(path);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Object data = ois.readObject();
			if (data == null) return null;
			listAcc = (ArrayList<Account>) data;
			ois.close();
			fis.close();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return listAcc;
	}
}
