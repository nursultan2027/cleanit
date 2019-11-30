package com.cleanitkz.nurs.hscroll;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cleanitkz.nurs.hscroll.databinding.SecondBinding;

import java.util.ArrayList;

public class Second extends AppCompatActivity {

    private Toolbar mToolbar;
    private ConstraintLayout fm;
    private DatabaseHelper databaseHelper;
    private TabLayout mTabLayout;
    private fHelper fHelper;
    private SecondBinding binding;
    private TextView footer, logo;
    private ViewPager mPager;
    private Intent BasketIntent, myAccount, MyOrdersIntent;
    private MyPagerAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        databaseHelper = new DatabaseHelper(this);
        Basket.cleanit = new Cleanit(String.valueOf(databaseHelper.getScore())+" ₸");
        binding = DataBindingUtil.setContentView(this, R.layout.second);
        binding.setCleanit(Basket.cleanit);
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        footer = (TextView) findViewById(R.id.texttext);
        fm = (ConstraintLayout) findViewById(R.id.basket_footer);
        fm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBasketClick();
            }
        });
        ImageView img = (ImageView) findViewById(R.id.imageView20);
        TextView txt = (TextView) findViewById(R.id.txtxt);
        TextView txx = (TextView) findViewById(R.id.basket_score);
        if (databaseHelper.getScore()>0)
        {
            txx.setVisibility(View.GONE);
            img.setVisibility(View.VISIBLE);
            txt.setVisibility(View.VISIBLE);
            footer.setVisibility(View.VISIBLE);
            footer.setText(String.valueOf(databaseHelper.getScore())+" ₸");
        }
        else {
            img.setVisibility(View.GONE);
            txt.setVisibility(View.GONE);
            footer.setVisibility(View.GONE);
            txx.setVisibility(View.VISIBLE);
            txx.setText("Пропустить выбор товаров");
        }
        logo= (TextView)findViewById(R.id.imageView19);
        Typeface face= Typeface.createFromAsset(getAssets(), "KGHAPPY.ttf");
        logo.setTypeface(face);
        fHelper = new fHelper(this);
        BasketIntent = new Intent(this, BasketActivity.class);
        myAccount = new Intent(this, AccountActivity.class);
        MyOrdersIntent = new Intent(this, myOrders.class);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);

        ImageView accountImg = (ImageView) findViewById(R.id.accountImg);
        ImageView orders = (ImageView) findViewById(R.id.orders);
        accountImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAccountClick();
            }
        });
        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnected())
                {
                    startActivity(MyOrdersIntent);
                    finish();
                }
                else {
                    Intent noConn = new Intent(Second.this, NoConnectionActivity.class);
                    startActivity(noConn);
                }

            }
        });
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mPager);
        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.getTabAt(0).setIcon(R.drawable.home_tab2);
        mTabLayout.getTabAt(1).setIcon(R.drawable.suits_tab2);
        mTabLayout.getTabAt(2).setIcon(R.drawable.tops_tab2);
        mTabLayout.getTabAt(3).setIcon(R.drawable.trousers_icon22);
        mTabLayout.getTabAt(4).setIcon(R.drawable.knitwear_tab2);
        mTabLayout.getTabAt(5).setIcon(R.drawable.dresses_tab2);
        mTabLayout.getTabAt(6).setIcon(R.drawable.outdoor_tab2);
        mTabLayout.getTabAt(7).setIcon(R.drawable.accessories_tab2);
        mTabLayout.getTabAt(8).setIcon(R.drawable.business_tab2);
        int tabIconColor = ContextCompat.getColor(Second.this, R.color.tab);
        mTabLayout.getTabAt(0).getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
        mTabLayout.getTabAt(1).getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
        mTabLayout.getTabAt(2).getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
        mTabLayout.getTabAt(3).getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
        mTabLayout.getTabAt(4).getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
        mTabLayout.getTabAt(5).getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
        mTabLayout.getTabAt(6).getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
        mTabLayout.getTabAt(7).getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
        mTabLayout.getTabAt(8).getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);


        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabIconColor = ContextCompat.getColor(Second.this, R.color.colorAccent);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int tabIconColor = ContextCompat.getColor(Second.this, R.color.tab);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        wrapTabIndicatorToTitle(mTabLayout, 30, 30);
        mPager.setCurrentItem(Basket.pageNumber);

    }

    public void wrapTabIndicatorToTitle(TabLayout tabLayout, int externalMargin, int internalMargin) {
        View tabStrip = tabLayout.getChildAt(0);
        if (tabStrip instanceof ViewGroup) {
            ViewGroup tabStripGroup = (ViewGroup) tabStrip;
            int childCount = ((ViewGroup) tabStrip).getChildCount();
            for (int i = 0; i < childCount; i++) {
                View tabView = tabStripGroup.getChildAt(i);
                tabView.setMinimumWidth(0);
                tabView.setPadding(0, tabView.getPaddingTop(), 0, tabView.getPaddingBottom());
                if (tabView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) tabView.getLayoutParams();
                    if (i == 0) {
                        settingMargin(layoutParams, externalMargin, internalMargin);
                    } else if (i == childCount - 1) {
                        settingMargin(layoutParams, internalMargin, externalMargin);
                    } else {
                        settingMargin(layoutParams, internalMargin, internalMargin);
                    }
                }
            }

            tabLayout.requestLayout();
        }
    }

    private void settingMargin(ViewGroup.MarginLayoutParams layoutParams, int start, int end) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            layoutParams.setMarginStart(start);
            layoutParams.setMarginEnd(end);
            layoutParams.leftMargin = start;
            layoutParams.rightMargin = end;
        } else {
            layoutParams.leftMargin = start;
            layoutParams.rightMargin = end;
        }
    }
    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
        }
        return connected;
    }

    public void onBasketClick() {
        this.startActivity(BasketIntent);
        finish();
    }
    public void onAccountClick() {
        this.startActivity(myAccount);
        finish();
    }

    public static class MyFragment extends Fragment {
        public static final java.lang.String ARG_PAGE = "arg_page";
        private DatabaseHelper databaseHelper;
        private fHelper fHelper;
        public MyFragment() {}

        public static MyFragment newInstance(int pageNumber) {
            MyFragment myFragment = new MyFragment();
            Bundle arguments = new Bundle();
            arguments.putInt(ARG_PAGE, pageNumber);
            myFragment.setArguments(arguments);
            return myFragment;
        }
        public void fin()
        {
            getActivity().finish();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            Bundle arguments = getArguments();
            final int pageNumber = arguments.getInt(ARG_PAGE);
            ListView lst = new ListView(getActivity());
            databaseHelper = new DatabaseHelper(this.getContext());
            fHelper = new fHelper(this.getContext());
            ArrayList<Category> categories = databaseHelper.getAllCategories();
            ArrayList<Catalog> catalogs = databaseHelper.getAllCatalogs();
            ArrayList<Product> products = databaseHelper.getAllProducts();


            categories = sortCategory(categories);
            catalogs = sortCatalog(catalogs);
            products = sortProduct(products);

            for (int k=0; k<catalogs.size();k++) {
                ArrayList<Product> products1 = new ArrayList<>();
                for (int n = 0; n < products.size(); n++) {
                    if (catalogs.get(k).getName().equals(products.get(n).getCatalogName())) {
                        products1.add(products.get(n));
                    }
                }
                catalogs.get(k).products = products1;
            }

            for(int i=0; i<categories.size();i++)
            {
                ArrayList<Catalog> catalogs1 = new ArrayList<>();
                for (int j=0; j<catalogs.size();j++)
                {
                    if (categories.get(i).getName().equals(catalogs.get(j).getCategoryName()))
                    {
                        catalogs1.add(catalogs.get(j));
                    }
                }
                categories.get(i).catalogs = catalogs1;
            }

            ArrayList<Category> categoryList = categories;
            CatalogAdapter adap = new CatalogAdapter( this.getContext(), R.layout.item, categoryList.get(pageNumber).getCatalogs());
            lst.setAdapter(adap);
            AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    Catalog selectedCatalog = (Catalog) parent.getItemAtPosition(position);
                    Intent intent = new Intent(getContext(), ShowCatalog.class);
                    intent.putExtra(Catalog.class.getCanonicalName(), selectedCatalog);
                    startActivity(intent);
                    Basket.pageNumber = pageNumber;
                    fin();
                }
            };
            lst.setOnItemClickListener(itemListener);
            return lst;
        }


        public ArrayList<Category> sortCategory(ArrayList<Category> categories)
        {
            String [] str3 = new String [] {"dlya_doma", "kostumy", "tekstil", "bruki","trikotazh", "platya", "verhnyaya_odezhda", "aksessuary", "biznessu"};
            ArrayList<Category> catcat = new ArrayList<>();
            for (int p=0; p<categories.size(); p++)
            {
                for (int y=0; y<categories.size(); y++)
                {
                    if (str3[p].equals(categories.get(y).getValue()))
                    {
                        catcat.add(categories.get(y));
                    }
                }
            }
            return catcat;
        }

        public ArrayList<Catalog> sortCatalog(ArrayList<Catalog> catalogs)
        {
            ArrayList<Catalog> catacata = new ArrayList<>();
            String [] str1 = fHelper.readFile1().split(" ");
            for (int i=1; i<=catalogs.size(); i++)
            {
                for (int j = 0; j < catalogs.size(); j++)
                {
                    if (str1[i].equals(catalogs.get(j).getValue()))
                    {
                        catacata.add(catalogs.get(j));
                    }
                }
            }
            return catacata;
        }

        public ArrayList<Product> sortProduct(ArrayList<Product> products)
        {
            ArrayList<Product> catcat = new ArrayList<>();
            String [] str2 = fHelper.readFile2().split(" ");
            for (int i=1; i<=products.size(); i++)
            {
                for (int j = 0; j < products.size(); j++)
                {
                    if (str2[i].equals(products.get(j).getValue()))
                    {
                        catcat.add(products.get(j));
                    }
                }
            }
            return catcat;
        }
    }

}

class MyPagerAdapter extends FragmentStatePagerAdapter {

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Second.MyFragment myFragment = Second.MyFragment.newInstance(position);
        return myFragment;
    }
    @Override
    public int getCount() {
        return 9;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Для дома";
            case 1:
                return "Костюмы";
            case 2:
                return "Текстиль";
            case 3:
                return "Брюки";
            case 4:
                return "Трикотаж";
            case 5:
                return "Платья";
            case 6:
                return "Верхняя";
            case 7:
                return "Аксессуары";
            case 8:
                return "Бинессу";
        }
        return null;
    }
}