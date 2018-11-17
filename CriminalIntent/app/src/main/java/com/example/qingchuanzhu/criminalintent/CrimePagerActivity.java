package com.example.qingchuanzhu.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity {

    private static final String EXTRA_START_CRIME_ID = "com.example.qingchuanzhu.criminalintent.start_crime_id";

    private ViewPager mViewPager;
    private List<Crime> mCrimes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        mCrimes = CrimeLab.get(this).getCrimes();
        mViewPager = findViewById(R.id.crime_view_pager);
        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new CrimePagerAdapter(fm));
        UUID start_id = (UUID) getIntent().getSerializableExtra(EXTRA_START_CRIME_ID);
        mViewPager.setCurrentItem(CrimeLab.get(this).getCrimeIndexById(start_id), true);
    }

    public static Intent newIntent(Context context, UUID crimeID) {
        Intent intent = new Intent(context, CrimePagerActivity.class);
        intent.putExtra(EXTRA_START_CRIME_ID,crimeID );
        return intent;
    }

    // PagerAdapter Inner class
    private class CrimePagerAdapter extends FragmentPagerAdapter {

        public CrimePagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Crime crime = mCrimes.get(i);
            return CrimeFragment.newInstance(crime.getID());
        }

        @Override
        public int getCount() {
            return mCrimes.size();
        }
    }
}
