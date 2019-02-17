package com.elite.firebaseyt.Main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.elite.firebaseyt.MainActivity;
import com.elite.firebaseyt.R;


public class ButtomNavCategory extends Fragment implements CustomAdaptergrid.OnItemClickListener {

    View view;
    String[] Names = {"Love", "Frienship", "Sad", "DJ", "Dance", "Dialogue"};
    int image[] = {R.drawable.cahands, R.drawable.cafriendship, R.drawable.casad, R.drawable.cadj, R.drawable.caparty, R.drawable.cadialogue};
    RecyclerView recyclerView;
    private CategoryClick categoryClick;
    CustomAdaptergrid adaptergrid;
    public static String onclickposition;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.buttom_nav_category, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.grid);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        adaptergrid = new CustomAdaptergrid(getActivity(), Names, image);
        recyclerView.setAdapter(adaptergrid);

        categoryClick = new CategoryClick();
        adaptergrid.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onItemClick(int position) {
//        Toast.makeText(getContext(),"Click on " +Names[position],Toast.LENGTH_SHORT).show();
        onclickposition = Names[position];
        setFragment(categoryClick);
    }

    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.id_framelayout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

}
