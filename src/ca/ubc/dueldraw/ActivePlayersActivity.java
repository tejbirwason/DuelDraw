package ca.ubc.dueldraw;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ActivePlayersActivity extends Activity {
	ListView listView ;
	String[] values;
	SocketApp app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		getActionBar().setTitle("Duel Draw");  
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_active_players);		 
		// Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);
        
        // Defined Array values to show in ListView
        String[] values = new String[] { "Player 1", 
                                         "Player 2",
                                         "Player 3",
                                         "Player 4", 
                                         "Player 5"
                                        };
        app = (SocketApp) getApplicationContext();
        app.setPlayerList(values);
        if(app.playerListReady)	values = app.getPlayerList();
        
        // Setup adapter for ListViews
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
          android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listView.setAdapter(adapter); 
        listView.setOnItemClickListener(new OnItemClickListener()
        {
           @Override
           public void onItemClick(AdapterView<?> adapter, View v, int position,
                 long arg3) 
           {
                 String opponentID = (String)adapter.getItemAtPosition(position); 
                 System.out.println(opponentID);
                 // assuming string and if you want to get the value on click of list item
                 // do what you intend to do on click of listview row
                 
             	Intent intent = new Intent(getApplicationContext(), DrawActivity.class);
             	//intent.putExtra("playerNumber",value);
             	
             	requestChallenge_ProtocolG(opponentID);
 
            	startActivity(intent);
           }
        });
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
 	private void requestChallenge_ProtocolG(String opponentID){
		app = (SocketApp) getApplicationContext();
		app.sendMessage("G");
		app.sendMessage(opponentID);
		app.opponentID = opponentID;
//		app.recvMessage(); //get acknowledgement code 8
	}
}
