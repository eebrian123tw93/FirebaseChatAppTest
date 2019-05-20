package twb.brianlu.com.firebasetest.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.HashMap;
import java.util.Map;

import twb.brianlu.com.firebasetest.R;
import twb.brianlu.com.firebasetest.chat.ChatActivity;
import twb.brianlu.com.firebasetest.login.LoginFragment;
import twb.brianlu.com.firebasetest.pair.PairFragment;
import twb.brianlu.com.firebasetest.profile.ProfileFragment;
import twb.brianlu.com.firebasetest.rooms.RoomsFragment;

public class NavigationActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, NavigationView {


    private BottomNavigationView bottomNavigationView;
    private Map<Integer, Fragment> fragmentHashMap;
    private Fragment focusFragment;

    private NavigationPresenter navigationPresenter;

    private int exitCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        fragmentHashMap = new HashMap<>();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);


//        focusFragment = new PairFragment();
//        fragmentHashMap.put(R.id.pair, focusFragment);
//        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragmentHashMap.get(R.id.pair)).commit();
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setTitle(bottomNavigationView.getMenu().getItem(1).getTitle());
//        }
//            startActivity(new Intent(this, ChatActivity.class));

        navigationPresenter = new NavigationPresenter(this);
        bottomNavigationView.setSelectedItemId(R.id.pair);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = fragmentHashMap.get(menuItem.getItemId());
        if (fragment == null) {
            switch (menuItem.getItemId()) {
                case R.id.room:
                    fragment = new RoomsFragment();

                    break;
                case R.id.profile:
                    if (navigationPresenter.isLogin()) {
                        fragment = new ProfileFragment();
                    } else {
                        fragment = new LoginFragment();
                    }
                    break;
                case R.id.pair:
                    fragment = new PairFragment();
                    break;
                default:
                    fragment = new PairFragment();
                    break;
            }
            fragmentHashMap.put(menuItem.getItemId(), fragment);
        }
        showFragment(fragment);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(menuItem.getTitle());
        }
        return true;
    }

    public void showFragment(Fragment fragment) {
        if (focusFragment == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
            return;
        }
        if (!fragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().hide(focusFragment).add(R.id.frame_layout, fragment).commitAllowingStateLoss();
        } else {
            getSupportFragmentManager().beginTransaction().hide(focusFragment).show(fragment).commitAllowingStateLoss();
        }
        focusFragment = fragment;
    }

    @Override
    public void onLogin() {

    }


    @Override
    public void onLogout() {
        finish();
    }

    @Override
    public void toLoginPage() {

    }


    @Override
    public void onSetMessage(String message, int type) {

        FancyToast.makeText(getBaseContext(), message, FancyToast.LENGTH_SHORT, type, false).show();
    }

    @Override
    public void onBackPressed() {
        exitCount++;
        if (exitCount == 1) {
            onSetMessage("再點擊一下離開", FancyToast.INFO);
            new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(3000);
                        exitCount = 0;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        } else if (exitCount == 2) {
            moveTaskToBack(true);
        }

    }
}
