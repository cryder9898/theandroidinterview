package com.android.interview;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.*;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.interview.model.TestQuestions;
import com.android.interview.model.User;
import com.android.interview.navigationdrawer.AboutActivity;
import com.bumptech.glide.Glide;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.android.interview.navigationdrawer.QuestionDetailFragment;
import com.android.interview.navigationdrawer.QuestionsListFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity implements
        GoogleApiClient.OnConnectionFailedListener, NavigationView.OnNavigationItemSelectedListener, QuestionsListFragment.OnListItemClickListener {

    private static final int REQUEST_INVITE = 1;
    private static final String ANONYMOUS = "anonymous";
    private static final String TAG = "MainActivity";
    private static final String LIST = "list";
    private static final String DETAIL = "detail";
    private static final String ABOUT = "about";
    private static boolean calledAlready = false;

    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private FloatingActionButton mFAB;
    private NavigationView mNavigationView;

    private String mUsername;
    private String mPhotoUrl;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseAnalytics mFirebaseAnalytics;
    private DatabaseReference mFirebaseDatabase;
    private ValueEventListener mValueEventListener;

    private GoogleApiClient mGoogleApiClient;

    private QuestionsListFragment mQuestionsListFragment;
    private QuestionDetailFragment mQuestionDetailFragment;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        if (!calledAlready) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            calledAlready = true;
        }

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseUtils.getBaseRef();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        mUsername = ANONYMOUS;
        if (mFirebaseUser == null) {
            // not signed in
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        }
        //TestQuestions.loadPublished();
        //TestQuestions.loadReviews();

        //initializes Drawer header
        initNavHeader();

        mValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot child: dataSnapshot.getChildren()) {
                        User user = child.getValue(User.class);
                        if (user.getUid().equals(mFirebaseUser.getUid())) {
                            setAdmin(true);
                            MenuItem menuItem = mNavigationView.getMenu().findItem(R.id.admin_menu);
                            menuItem.setVisible(true);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        FirebaseUtils.getAdminsRef().addListenerForSingleValueEvent(mValueEventListener);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();

        mFAB = (FloatingActionButton) findViewById(R.id.fab);
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*String tag = currentFragment.getTag();
                switch (tag) {
                    case LIST:
                        ((QuestionsListFragment) currentFragment).fabOnClick();
                        break;
                    case DETAIL:
                        ((QuestionDetailFragment) currentFragment).fabOnClick();
                        break;
                }*/
            }
        });

        /*if (savedInstanceState == null) {
            mQuestionsListFragment = QuestionsListFragment.newInstance(getListType());
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.container_main, mQuestionsListFragment, LIST);
            fragmentTransaction.commit();
        }*/
    }

    private void initNavHeader() {
        View headerView = mNavigationView.getHeaderView(0);
        mUsername = mFirebaseUser.getDisplayName();
        mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
        TextView usernameTV = (TextView) headerView.findViewById(R.id.username_tv);
        usernameTV.setText(mUsername);
        CircleImageView userImage = (CircleImageView) headerView.findViewById(R.id.user_iv);
        Glide.with(this)
                .load(mPhotoUrl)
                .into(userImage);
    }

    // Hides FAB and shows it with resId image
    private void setFabIcon(final int resId) {
        mFAB.hide(new FloatingActionButton.OnVisibilityChangedListener() {
            @Override
            public void onHidden(FloatingActionButton fab) {
                fab.setImageResource(resId);
                fab.show();
            }
        });
    }

    @Override
    protected void onResume() {
        // Displaying what FAB icon to display or to HIDE the FAB
        /*Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container_main);
        String tag = currentFragment.getTag();
        switch (tag) {
            case LIST:
                break;
            case DETAIL:
                if (isAdmin() && getListType().equals(UNDER_REVIEW)) {
                    mFAB.show();
                    setFabIcon(R.drawable.ic_mode_edit_white_24dp);
                } else {
                    mFAB.hide();
                }
                break;
            case ABOUT:
                setFabIcon(R.drawable.ic_mail_outline_white_24dp);
                break;
        }*/
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mDrawerLayout.removeDrawerListener(mDrawerToggle);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.invite_menu:
                sendInvitation();
                return true;
            case R.id.sign_out_menu:
                mFirebaseAuth.signOut();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                mFirebaseUser = null;
                mUsername = ANONYMOUS;
                mPhotoUrl = null;
                setListType(PUBLISHED);
                setAdmin(false);
                startActivity(new Intent(this, SignInActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            /*Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container_main);
            String tag = currentFragment.getTag();
            switch (tag) {
                case DETAIL:
                    setFabIcon(R.drawable.ic_add_white_36dp);
                    break;
                case ABOUT:
                    setFabIcon(R.drawable.ic_add_white_36dp);
            }*/
        }
        super.onBackPressed();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_questions:
                setListType(PUBLISHED);
                mPagerAdapter.notifyDataSetChanged();
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_favorites:
                return true;
            case R.id.nav_share:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "here goes your share content body";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Android Interview");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_about:
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_review:
                setListType(UNDER_REVIEW);
                mViewPager.setAdapter(mPagerAdapter);
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            default:
                return true;
        }
    }

    private void sendInvitation() {
        Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
                .setMessage(getString(R.string.invitation_message))
                .setCallToActionText(getString(R.string.invitation_cta))
                .build();
        startActivityForResult(intent, REQUEST_INVITE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);

        if (requestCode == REQUEST_INVITE) {
            if (resultCode == RESULT_OK) {
                Bundle payload = new Bundle();
                payload.putString(FirebaseAnalytics.Param.VALUE, "inv_sent");

                // Check how many invitations were sent and log.
                String[] ids = AppInviteInvitation.getInvitationIds(resultCode, data);
                Log.d(TAG, "Invitations sent: " + ids.length);
            } else {
                Bundle payload = new Bundle();
                payload.putString(FirebaseAnalytics.Param.VALUE, "inv_not_sent");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SHARE, payload);
                Log.d(TAG, "Failed to send invitation.");
            }
        }
    }

    @Override
    public void onQuestionSelected(String key) {
        mQuestionDetailFragment = QuestionDetailFragment.newInstance(getListType());
        mQuestionDetailFragment.initFragObject(key);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.attach(mQuestionDetailFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        if (isAdmin() && getListType().equals(UNDER_REVIEW)) {
            setFabIcon(R.drawable.ic_mode_edit_white_24dp);
        }
    }
}

