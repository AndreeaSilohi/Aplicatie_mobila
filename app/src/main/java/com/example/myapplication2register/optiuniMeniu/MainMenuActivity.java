package com.example.myapplication2register.optiuniMeniu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication2register.MeniuZilnic;
import com.example.myapplication2register.InformatiiContActivity;
import com.example.myapplication2register.R;
import com.example.myapplication2register.logare.MainActivity5;
import com.example.myapplication2register.fragmente.HFragment;
import com.example.myapplication2register.produse.ProduseDetoxActivity;
import com.example.myapplication2register.retete.ReteteActivity;

import com.example.myapplication2register.administrator.VideosActivity;
import com.example.myapplication2register.video.ShowVideoActivity;
import com.github.mikephil.charting.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

//import com.example.myapplication2register.databinding.ActivityMainMenuBinding;

import java.util.ArrayList;
import java.util.List;

public class MainMenuActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private FloatingActionButton fabNewP;
    private ActivityResultLauncher<Intent> newMainActivityLauncher;
    private List<Persoana> persons = new ArrayList<>();
    private Fragment currentFragment;
    FirebaseAuth auth;
    //public FloatingActionButton logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        auth=FirebaseAuth.getInstance();
        initialComponents();
       // openDefaultFragment(savedInstanceState);
        navigationView.bringToFront();

    }

    private void initialComponents() {
        configurareNavigare();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(getNavigationItemSelectedListener());
//        navigationView.setNavigationItemSelectedListener(onNavigationItemSelected(onMenuItemSelected(getNavigationItemSelectedListener());
       // fabNewP = findViewById(R.id.fab);
       // fabNewP.setOnClickListener(getNewExpenseClickListener());
        newMainActivityLauncher = getNewMainActivityLauncher();

    }

    private ActivityResultLauncher<Intent> getNewMainActivityLauncher() {
        ActivityResultCallback<ActivityResult> callback = getNewExpenseActivityResultCallback();
        return registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), callback);
    }

    private ActivityResultLauncher<Intent> getNewExpenseActivityLauncher() {
        ActivityResultCallback<ActivityResult> callback = getNewExpenseActivityResultCallback();
        return registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), callback);
    }

    private ActivityResultCallback<ActivityResult> getNewExpenseActivityResultCallback() {

        return new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Persoana expense = result.getData().getParcelableExtra(MainActivity5.NEW_EXPENSE_KEY);
                    if (expense != null) {
                        //openFragment();
                        Toast.makeText(getApplicationContext(), expense.toString(), Toast.LENGTH_SHORT).show();
                      //  persons.add(expense);
                        //notificare adapter
                        if (currentFragment instanceof HFragment) {
                            ((HFragment)currentFragment).notifyAdapter();
                        }
                    }
                }
            }
        };
    }

//    private void openFragment() {
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.main_frame_container, currentFragment)
//                .commit();
//    }

    private View.OnClickListener getNewExpenseClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity5.class);
                newMainActivityLauncher.launch(intent);
            }
        };
    }




    private NavigationView.OnNavigationItemSelectedListener getNavigationItemSelectedListener() {
        return new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                if(item.getItemId()==R.id.nav_home)
//                {
//
//                    Log.i("MainMenuActivity","Home is selected");
//                    //currentFragment = HFragment.newInstance((ArrayList<Expense>) persons);
//                    Intent intent2=new Intent(MainMenuActivity.this, MainMenuActivity.class);
//                    startActivity(intent2);
//                }else
                    if (item.getItemId()==R.id.nav_retete)
                {
                    Log.i("MainMenuActivity","Retete is selected");
                    Intent intent=new Intent(MainMenuActivity.this, ReteteActivity.class);
                    startActivity(intent);

                }
                else if (item.getItemId()==R.id.nav_exercitii)
                {
                    Intent intent2=new Intent(MainMenuActivity.this, ShowVideoActivity.class);
                    startActivity(intent2);

                }
              else if(item.getItemId()==R.id.nav_progres){
                Utils.init(getApplicationContext());
                  Intent intent3=new Intent (MainMenuActivity.this, ProgresActivity.class);
                    startActivity(intent3);
              }
              else if(item.getItemId()==R.id.nav_coordonate){
                  Intent intent4=new Intent(MainMenuActivity.this, CoordonateUserActivity.class);
                    startActivity(intent4);
                }
              else if(item.getItemId()==R.id.nav_meniu_zilnic){
                  Intent intent5=new Intent(MainMenuActivity.this, MeniuZilnic.class);
                  startActivity(intent5);
                }
              else if(item.getItemId()==R.id.nav_produse){
                    Intent intent6=new Intent(MainMenuActivity.this, ProduseDetoxActivity.class);
                    startActivity(intent6);
                }
                else if(item.getItemId()==R.id.nav_info){
                    Intent intent7=new Intent(MainMenuActivity.this, InformatiiContActivity.class);
                    startActivity(intent7);
                }

                Toast.makeText(getApplicationContext(),getString(R.string.main_message,item.getTitle()),Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        };

    }


    private void configurareNavigare(){
        drawerLayout=findViewById(R.id.drawer_layout);

       Toolbar toolbar=findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        //definim toggle prin clasa Action
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        //legare toggle de drawer layout->deseneaza panoul lateral
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();//sincronizeaza starea(inchidere sau deschidere)

    }


    @Override
    public void onBackPressed()
    {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }


public boolean onNavigationItemSelected(@NonNull MenuItem menuItem){

        switch(menuItem.getItemId()){
            case R.id.nav_home:
                break;
            case R.id.nav_retete:
                Intent intent1=new Intent(MainMenuActivity.this,ReteteActivity.class);
                startActivity(intent1);
                break;
            case R.id.nav_exercitii:
                Intent intent2=new Intent(MainMenuActivity.this,ExercitiiFiziceActivity.class);
                startActivity(intent2);
                break;
            case R.id.nav_produse:
                Intent intent3=new Intent(MainMenuActivity.this, ProduseActivity.class);
                startActivity(intent3);
                break;
        }
        return true;
}








}
