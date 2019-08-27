package ru.f13.getlayout.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import com.google.android.material.navigation.NavigationView;

import ru.f13.getlayout.GLApp;
import ru.f13.getlayout.R;
import ru.f13.getlayout.util.GLUtils;
import ru.f13.getlayout.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * Период для выхода
     */
    private static final long PERIOD_FOR_EXIT = 2000;

    private MainViewModel mMainViewModel;
    private NavController navController;

    private boolean isNotExitAlert = false;
    private long timeLastPressExit = 0 - PERIOD_FOR_EXIT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        subscribeUi(mMainViewModel);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        ((GLApp)getApplication()).getPreferences().getNotExitAlert().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean value) {
                isNotExitAlert = value;
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        final AppCompatActivity activity = this;
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                //скрыть клавиатуру, когда открыли ящик
                GLUtils.getInstance(getBaseContext()).hideKeyboard(activity);
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();
//            System.out.println("navController.popBackStack() = " + navController.popBackStack());
//
            NavDestination current = navController.getCurrentDestination();
            //если текущий узел, это фрагмент конвертации, то это - точка выхода
            boolean isExit = current != null && current.getId() == R.id.conversionsFragment;

            if (isExit) {
                //если настройка "не уведомлять о выходе" отключена
                if (!isNotExitAlert) {
                    if (timeLastPressExit + PERIOD_FOR_EXIT >= System.currentTimeMillis()) {
                        super.onBackPressed();
                    } else {
                        Toast.makeText(getBaseContext(), getString(R.string.click_exit_application), Toast.LENGTH_SHORT).show();
                        timeLastPressExit = System.currentTimeMillis();
                    }
                } else {
                    super.onBackPressed();
                }
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return false;
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

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_conversions) {
            navigateConversionsFragment();
        } else if (id == R.id.nav_delete_histoty) {
            mMainViewModel.deleteAllConversions();
        } else if (id == R.id.nav_settings) {
            navigateSettingsFragment();
        } else if (id == R.id.nav_about) {
            navigateAboutFragment();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Подписать UI на изменения
     * @param viewModel объект {@link androidx.lifecycle.ViewModel}
     */
    private void subscribeUi(MainViewModel viewModel) {

        // Update the list when the data changes
        viewModel.getNotExitAlert().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean value) {
                isNotExitAlert = value;
            }
        });

    }

    /**
     * Направить к фрагменту конверций
     */
    public void navigateConversionsFragment() {
        navController.navigate(R.id.action_global_conversionsFragment);
    }

    /**
     * Направить к фрагменту настроек
     */
    public void navigateSettingsFragment() {
        navController.navigate(R.id.action_global_settingsFragment);
    }

    /**
     * Направить к фрагменту "о приложении"
     */
    public void navigateAboutFragment() {
        navController.navigate(R.id.action_global_aboutFragment);
    }

}
