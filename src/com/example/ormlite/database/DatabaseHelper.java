package com.example.ormlite.database;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.ormlite.R;
import com.example.ormlite.data.Data;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	private static final String TAG = "DatabaseHelper";
	
	private static final String DATABASE_NAME = "Android.db";
	
	private static final int DATABASE_VERSION = 1;
	
	private Dao<Data, Integer> dao = null;
	private RuntimeExceptionDao<Data, Integer> runtimeDao = null;
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
		// TODO Auto-generated method stub
		try {
			Log.i(TAG, "On Create");
			TableUtils.createTable(arg1, Data.class);
			Log.i(TAG, "DataBase Created");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i(TAG, "Can't create Database");
		}
		
		RuntimeExceptionDao<Data, Integer> dao = getDataDao();
		long millis = System.currentTimeMillis();
		// create some entries in the onCreate
		Data simple = new Data(millis);
		dao.create(simple);
		simple = new Data(millis + 1);
		dao.create(simple);
		Log.i(DatabaseHelper.class.getName(), "created new entries in onCreate: " + millis);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}
	
	public Dao<Data, Integer> getDao() throws SQLException
	{
		if(dao == null)
		{
			dao = getDao(Data.class);
		}
		return dao;
	}
	
	public RuntimeExceptionDao<Data, Integer> getDataDao()
	{
		if(runtimeDao == null)
		{
			runtimeDao = getRuntimeExceptionDao(Data.class);
		}
		return runtimeDao;
	}
	
	@Override
	public void close() {
		// TODO Auto-generated method stub
		super.close();
		dao = null;
		runtimeDao = null;
	}
	
}
