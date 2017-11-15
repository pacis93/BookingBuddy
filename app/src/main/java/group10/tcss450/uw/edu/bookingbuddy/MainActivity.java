package group10.tcss450.uw.edu.bookingbuddy;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

/**
 * The main activity of the application. Currently housing ALL fragments.
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FlightSearchFragment.OnSearchSubmitListener, FlightListFragment.OnFragmentInteractionListener,
        StartupFragment.splashFragmentInteractionListener, LoginFragment.loginFragmentInteractionListener, FlightListFragment.OnGraphInteractionListener,
        RegisterFragment.registerFragmentInteractionListener, ForgotPasswordFragment.forgotPasswordInteractionListener{

    ActionBarDrawerToggle toggle;

    /**
     * This method will be called when the activity is created. It will instantiate and create
     * all necessary UI elements as well as open a new StartupFragment fragment that the user will see
     * when the application is started.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if(savedInstanceState == null) {
            if (findViewById(R.id.fragmentContainer) != null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragmentContainer, new StartupFragment())
                        .commit();
            }
        }

        //hides the hamburger button for nav menu until after the user has logged in
        toggle.setDrawerIndicatorEnabled(false);

    }

    /**
     * Defines the behavior when the back button is pressed for the drawer.
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Inflates the menu for when the toolbar menu is created.
     * @param menu The menu.
     * @return Returns true that the menu was inflated.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Defines behavior for when an item is selected.
     * @param item The selected item.
     * @return If the item was found in the selection.
     */
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

        return super.onOptionsItemSelected(item);
    }

    /**
     * Handles the behavior for when a navigation item is selected by the user.
     * @param item The item selected.
     * @return returns true for all current implementations of any item pressed.
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_flight_search) {
            if (findViewById(R.id.fragmentContainer) != null)
            {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, new FlightSearchFragment()).addToBackStack(null)
                        .commit();
            }
        } else if (id == R.id.nav_load_pinned_flights) {

        } else if (id == R.id.nav_search_history) {

        } else if (id == R.id.logout) {
            if (findViewById(R.id.fragmentContainer) != null)
            {
                toggle.setDrawerIndicatorEnabled(false);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, new StartupFragment()).addToBackStack(null)
                        .commit();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Defines the behavior for when the search button is pressed.
     * @param origin
     * @param destination
     */
    @Override
    public void onSearchSubmit(String origin, String destination) {
        FlightListFragment listFrag = new FlightListFragment();
        Bundle args = new Bundle();
        args.putSerializable("ORIGIN", origin);
        args.putSerializable("DESTI", destination);

        listFrag.setArguments(args);
        android.support.v4.app.FragmentTransaction trans = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, listFrag)
                .addToBackStack(null);
        trans.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /**
     * This method will open either a login or register fragment depending
     * on the integer passed into the method.
     * @param selection The selected choice of either login or register.
     */
    @Override
    public void splashFragmentInteraction(int selection) {

        if(selection == 0) {
            LoginFragment loginFragment = new LoginFragment();
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, loginFragment)
                    .addToBackStack(null);
            transaction.commit();

        } else if(selection == 1){
            Log.d("Something","stuff");
            RegisterFragment registerFragment = new RegisterFragment();
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, registerFragment)
                    .addToBackStack(null);
            transaction.commit();

        }



    }

    /**
     * Parameters are currently redundant - TODO: Remove them and update args if needed.
     * This method is to be called by login and register fragment interactions
     * it will create a new display screen.
     */
    public void openDisplayScreen() {
        toggle.setDrawerIndicatorEnabled(true);
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, new FlightSearchFragment())
                .addToBackStack(null);
        transaction.commit();

    }

    @Override
    public void loginFragmentInteraction(Boolean result) {
        if(result) {
            openDisplayScreen();
        } else {
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, new ForgotPasswordFragment())
                    .addToBackStack(null);
            transaction.commit();


        }



    }

    /**
     * After a user has logged in, they will be asked to then register.
     * This will also require the user to verify their email address before they can login.
     * @param username
     * @param password
     */
    @Override
    public void registerFragmentInteraction(String username, String password) {
        //opens the login after you register
        splashFragmentInteraction(0);

    }

    @Override
    public void onGraphSubmit(Integer[] arr) {
        GraphFragment graphFrag = new GraphFragment();
        Bundle args = new Bundle();
        args.putSerializable("ARRAY", arr);
        graphFrag.setArguments(args);
        android.support.v4.app.FragmentTransaction trans = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, graphFrag)
                .addToBackStack(null);
        trans.commit();
    }

    @Override
    public void forgotPasswordInteraction(Uri uri) {

    }
}
