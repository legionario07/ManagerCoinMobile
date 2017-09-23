package com.example.paulinho.managercoin.Telas;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.paulinho.managercoin.R;


public class ScreenSlidePageFragment2 extends Fragment {

    public ScreenSlidePageFragment2() {
        // Required empty public constructor
    }

    public static ScreenSlidePageFragment2 newInstance(String param1, String param2) {
        ScreenSlidePageFragment2 fragment = new ScreenSlidePageFragment2();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_screen_slide_page2, container, false);

        return rootView;
    }

}
