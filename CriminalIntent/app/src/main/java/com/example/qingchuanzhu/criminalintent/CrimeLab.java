package com.example.qingchuanzhu.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private List<Crime> mCrimes;
    private Map<UUID, Crime> mDict;
    private Map<UUID, Integer> mIndexDict;

    public static synchronized CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    public void addCrime(Crime c) {
        mIndexDict.put(c.getID(),mCrimes.size());
        mCrimes.add(c);
        mDict.put(c.getID(),c);
    }

    private CrimeLab(Context context) {
        mCrimes = new ArrayList<>();
        mDict = new HashMap<>();
        mIndexDict = new HashMap<>();
    }

    public List<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID id) {
        return mDict.get(id);
    }

    public int getCrimeIndexById(UUID id) {
        return mIndexDict.get(id);
    }
}
