package com.example.ormlite;

import java.util.List;
import java.util.Random;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.ormlite.data.Data;
import com.example.ormlite.database.DatabaseHelper;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;

public class MainActivity extends OrmLiteBaseActivity<DatabaseHelper> {

	public static final String TAG = "MainActivity";

	TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textView = (TextView) findViewById(R.id.tvText);
		databaseStuff("onCreate", textView);

	}

	public void databaseStuff(String action, TextView tv)
	{
		// get our dao
				RuntimeExceptionDao<Data, Integer> simpleDao = getHelper().getDataDao();
				// query for all of the data objects in the database
				List<Data> list = simpleDao.queryForAll();
				// our string builder for building the content-view
				StringBuilder sb = new StringBuilder();
				sb.append("got ").append(list.size()).append(" entries in ").append(action).append("\n");

				// if we already have items in the database
				int simpleC = 0;
				for (Data simple : list) {
					sb.append("------------------------------------------\n");
					sb.append("[").append(simpleC).append("] = ").append(simple).append("\n");
					simpleC++;
				}
				sb.append("------------------------------------------\n");
				for (Data simple : list) {
					simpleDao.delete(simple);
					sb.append("deleted id ").append(simple.id).append("\n");
					Log.i(TAG, "deleting simple(" + simple.id + ")");
					simpleC++;
				}

				int createNum;
				do {
					createNum = new Random().nextInt(3) + 1;
				} while (createNum == list.size());
				for (int i = 0; i < createNum; i++) {
					// create a new simple object
					long millis = System.currentTimeMillis();
					Data simple = new Data(millis);
					// store it in the database
					simpleDao.create(simple);
					Log.i(TAG, "created simple(" + millis + ")");
					// output it
					sb.append("------------------------------------------\n");
					sb.append("created new entry #").append(i + 1).append(":\n");
					sb.append(simple).append("\n");
					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
						// ignore
					}
				}

				tv.setText(sb.toString());
				Log.i(TAG, "Done with page at " + System.currentTimeMillis());
	}


}
