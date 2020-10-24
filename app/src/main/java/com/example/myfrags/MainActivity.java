package com.example.myfrags;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements FirstFragment.OnButtonClickListener {

    /*
    private FragmentManager fragmentManager;
    private Fragment fragment1, fragment2, fragment3, fragment4;
    */

    private int[] frames;
    private boolean hiden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            frames = new int[]{R.id.frame1, R.id.frame2, R.id.frame3, R.id.frame4};
            hiden = false;

            Fragment[] fragments = new Fragment[]{
                    new FirstFragment(),
                    new SecondFragment(),
                    new ThirdFragment(),
                    new FourthFragment(),
            };

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            for (int i = 0; i < 4; i++) {
                transaction.add(frames[i], fragments[i]);
            }
            transaction.addToBackStack(null);
            transaction.commit();

        } else {
            frames = savedInstanceState.getIntArray("FRAMES");
            hiden = savedInstanceState.getBoolean("HIDEN");
        }


        /*
        fragment1 = new FirstFragment();
        fragment2 = new SecondFragment();
        fragment3 = new ThirdFragment();
        fragment4 = new FourthFragment();

        fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.frame1, fragment1);
        transaction.add(R.id.frame2, fragment2);
        transaction.add(R.id.frame3, fragment3);
        transaction.add(R.id.frame4, fragment4);
        transaction.addToBackStack(null);
        transaction.commit();
        */
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        //Ctrl+O do otwarcia implementacji metod override

        outState.putIntArray("FRAMES", frames);
        outState.putBoolean("HIDEN", hiden);
    }

    //Po implementacji FirstFragment.onClick.... medody tworzą się same

    @Override
    public void onButtonClickShuffle() {
        Toast.makeText(getApplicationContext(), "Shuffle", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onButtonClickClockwise() {
        Toast.makeText(getApplicationContext(), "Clockwise", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onButtonClickHide() {

        //Listę fragmentów aktualnie osadzonych w aplikacji uzyskujemy wywołując metodę getFragments menadżera fragmentów.
        //Fragment ukrywamy metodą hide.
        //Fragment pokazujemy metoda show.

        if (hiden) return;

        FragmentManager fragmentManager = getSupportFragmentManager();

        for (Fragment f : fragmentManager.getFragments()) {

            if (f instanceof FirstFragment) continue;

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.hide(f);

            transaction.addToBackStack(null);
            transaction.commit();
        }

        hiden = true;
    }

    @Override
    public void onButtonClickRestore() {
        if (!hiden) return;

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        for (Fragment f : fragmentManager.getFragments()) {
            if (f instanceof FirstFragment) continue;
            transaction.show(f);
        }

        transaction.addToBackStack(null);
        transaction.commit();

        hiden = false;
    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);

        if (fragment instanceof FirstFragment) {
            ((FirstFragment) fragment).setOnButtonClickListener(this);
        }
    }
}


