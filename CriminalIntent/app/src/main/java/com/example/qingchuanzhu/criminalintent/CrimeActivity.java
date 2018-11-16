package com.example.qingchuanzhu.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {

    public static final String EXTRA_CRIME_ID = "com.example.qingchuanzhu.criminalintent.crime_id";

    @Override
    public Fragment createFragment(){
        return new CrimeFragment();
    }

    public static Intent newIntent(Context context, UUID crimeID) {
        Intent intent = new Intent(context,CrimeActivity.class );
        intent.putExtra(EXTRA_CRIME_ID,crimeID );
        return intent;
    }

}
