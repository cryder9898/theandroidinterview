package com.android.interview;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.interview.model.User;
import com.bumptech.glide.Glide;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.android.interview.model.QA;
import com.android.interview.navigationdrawer.AboutFragment;
import com.android.interview.navigationdrawer.QuestionDetailFragment;
import com.android.interview.navigationdrawer.QuestionsListFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener, NavigationView.OnNavigationItemSelectedListener, QuestionsListFragment.OnListItemClickListener {

    private static final int REQUEST_INVITE = 1;
    private static final String ANONYMOUS = "anonymous";
    private static final String TAG = "MainActivity";
    private static final String LIST = "list";
    private static final String DETAIL = "detail";
    private static final String ABOUT = "about";
    public static final String PUBLISHED = "published";
    public static final String UNDER_REVIEW = "under_review";
    public static boolean isAdmin = false;
    private String listType;
    private static boolean calledAlready = false;


    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private FloatingActionButton fab;
    private NavigationView mNavigationView;

    private String mUsername;
    private String mPhotoUrl;

    private FirebaseAuth mFirebaseAuth;
    private static FirebaseUser mFirebaseUser;
    private FirebaseAnalytics mFirebaseAnalytics;
    private DatabaseReference mFirebaseDatabase;
    private ValueEventListener mValueEventListener;

    private GoogleApiClient mGoogleApiClient;

    private QuestionsListFragment mQuestionsListFragment;
    private QuestionDetailFragment mQuestionDetailFragment;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("type", listType);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        View headerView = mNavigationView.getHeaderView(0);

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
        } else {
            //signed in
            mUsername = mFirebaseUser.getDisplayName();
            mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            TextView usernameTV = (TextView) headerView.findViewById(R.id.username_tv);
            usernameTV.setText(mUsername);
            CircleImageView userImage = (CircleImageView) headerView.findViewById(R.id.user_iv);
            Glide.with(this)
                    .load(mPhotoUrl)
                    .into(userImage);
            mValueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChildren()) {
                        for (DataSnapshot child: dataSnapshot.getChildren()) {
                            User user = child.getValue(User.class);
                            if (user.getUid().equals(mFirebaseUser.getUid())) {
                                isAdmin = true;
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
        }

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container_main);
                String tag = currentFragment.getTag();
                switch (tag) {
                    case LIST:
                        ((QuestionsListFragment) currentFragment).fabOnClick();
                        break;
                    case DETAIL:
                        ((QuestionDetailFragment) currentFragment).fabOnClick();
                        break;
                    case ABOUT:
                        break;
                }
            }
        });

        if (savedInstanceState == null) {
            listType = PUBLISHED;
            mQuestionsListFragment = QuestionsListFragment.newInstance(listType);
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.container_main, mQuestionsListFragment, LIST);
            fragmentTransaction.commit();
        } else {
            listType = savedInstanceState.getString("type");
        }
    }

    @Override
    protected void onResume() {
        // Displaying what FAB icon to display or to HIDE the FAB
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container_main);
        String tag = currentFragment.getTag();
        switch (tag) {
            case LIST:
                break;
            case DETAIL:
                if (isAdmin && listType.equals(UNDER_REVIEW)) {
                    fab.show();
                    setFabIcon(R.drawable.ic_mode_edit_white_24dp);
                } else {
                    fab.hide();
                }
                break;
            case ABOUT:
                fab.hide();
                break;
        }
        super.onStart();
    }

    @Override
    protected void onDestroy() {
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
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container_main);
            String tag = currentFragment.getTag();
            switch (tag) {
                case DETAIL:
                    setFabIcon(R.drawable.ic_add_white_36dp);
                    break;
            }
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
                listType = PUBLISHED;
                mQuestionsListFragment = QuestionsListFragment.newInstance(listType);
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container_main, mQuestionsListFragment, LIST);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_quiz:
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
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container_main, new AboutFragment(), ABOUT);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                mDrawerLayout.closeDrawer(GravityCompat.START);
                fab.hide();
                return true;
            case R.id.nav_review:
                listType = UNDER_REVIEW;
                mQuestionsListFragment = QuestionsListFragment.newInstance(listType);
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container_main, mQuestionsListFragment, LIST);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
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
    public void onQuestionSelected(String listType, String key) {
        mQuestionDetailFragment = QuestionDetailFragment.newInstance(listType);
        mQuestionDetailFragment.initFragObject(key);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_main, mQuestionDetailFragment, DETAIL);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        if (isAdmin && listType.equals(UNDER_REVIEW)) {
            setFabIcon(R.drawable.ic_mode_edit_white_24dp);
        }
    }

    // Hides FAB and shows it with resId image
    private void setFabIcon(final int resId) {
        fab.hide(new FloatingActionButton.OnVisibilityChangedListener() {
            @Override
            public void onHidden(FloatingActionButton fab) {
                fab.setImageResource(resId);
                fab.show();
            }
        });
    }
}

