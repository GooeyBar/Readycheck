package gtsioni.dtsioni.crejaud.readycheck;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends ActionBarActivity {
    private ListView accountList;
    private ArrayAdapter<String> adapter;
    private ActionBarDrawerToggle accountDrawerToggle;
    private DrawerLayout accountDrawerLayout;
    private String accountActivityTitle;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accountActivityTitle = "Settings";
        accountDrawerLayout = (DrawerLayout)findViewById(R.id.account_drawer_layout);
        accountActivityTitle = getTitle().toString();

        accountList = (ListView)findViewById(R.id.accountList);
        addDrawerItems();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void addDrawerItems(){
        String[] items = {"Do Not Disturb","Sign Out"};
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        accountList.setAdapter(adapter);
    }

    private void setUpDrawer(){
        accountDrawerToggle = new ActionBarDrawerToggle(this, accountDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(R.string.account_settings);
                invalidateOptionsMenu();
            }
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(R.string.app_name);
                invalidateOptionsMenu();
            }
        };

        accountDrawerToggle.setDrawerIndicatorEnabled(true);
        accountDrawerLayout.setDrawerListener(accountDrawerToggle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (accountDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}











