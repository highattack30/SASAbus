package it.sasabz.android.sasabus.classes;

import it.sasabz.android.sasabus.SASAbus;

import java.util.Vector;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class FavoritenList {
	
	/**                                                                                                                                                                                                          
	 * This function returns a vector of all the objects momentanly avaiable in the database                                                                                                                     
	 * @return a vector of objects if all goes right, alternativ it returns a MyError                                                                                                                              
	 */
	public static  Vector <Favorit>getList()
	{
		SQLiteDatabase sqlite = new FavoritenDB(SASAbus.getContext()).getReadableDatabase();
		Cursor cursor = sqlite.rawQuery("select * from " + FavoritenDB.FAVORITEN_TABLE_NAME + " order by id DESC", null);
		Vector <Favorit> list = null;
		if(cursor.moveToFirst())
		{
			list = new Vector<Favorit>();
			do {
				Favorit element = new Favorit(cursor.getInt(cursor.getColumnIndex("id")),
						cursor.getInt(cursor.getColumnIndex("linea")), 
						cursor.getString(cursor.getColumnIndex("partenza_de")),
						cursor.getString(cursor.getColumnIndex("destinazione_de")));
				list.add(element);
			} while(cursor.moveToNext());
		}
		cursor.close();
		sqlite.close();
		return list;
	}
	
}