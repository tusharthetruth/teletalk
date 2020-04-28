/*
 * Copyright (c) 2020 New Vector Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.chatapp;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chatapp.sip.api.SipManager;
import com.chatapp.sip.api.SipProfile;
import com.chatapp.sip.db.DBProvider;
import com.chatapp.sip.models.Filter;
import com.chatapp.sip.service.SipService;
import com.chatapp.sip.utils.PreferencesWrapper;
import com.chatapp.sip.wizards.WizardIface;
import com.chatapp.sip.wizards.impl.Basic;
import com.chatapp.util.ContactsSync;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavArgument;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.matrix.androidsdk.MXSession;
import org.matrix.androidsdk.call.IMXCall;
import org.matrix.androidsdk.core.Log;
import org.matrix.androidsdk.crypto.data.MXDeviceInfo;
import org.matrix.androidsdk.crypto.data.MXUsersDevicesMap;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import im.vector.Matrix;
import im.vector.MyPresenceManager;
import im.vector.R;
import im.vector.VectorApp;
import im.vector.activity.CommonActivityUtils;
import im.vector.activity.MXCActionBarActivity;
import im.vector.activity.VectorAppCompatActivity;
import im.vector.activity.VectorCallViewActivity;
import im.vector.activity.VectorGroupDetailsActivity;
import im.vector.activity.VectorHomeActivity;
import im.vector.activity.VectorMemberDetailsActivity;
import im.vector.activity.VectorSettingsActivity;
import im.vector.fragments.VectorRecentsListFragment;
import im.vector.fragments.signout.SignOutViewModel;
import im.vector.push.PushManager;
import im.vector.receiver.VectorUniversalLinkReceiver;
import im.vector.tools.VectorUncaughtExceptionHandler;
import im.vector.util.BugReporter;
import im.vector.util.CallsManager;
import im.vector.util.PermissionsToolsKt;
import im.vector.util.PreferencesManager;
import im.vector.util.VectorUtils;
import im.vector.view.VectorCircularImageView;
import im.vector.view.VectorPendingCallView;

import static im.vector.activity.VectorHomeActivity.BROADCAST_ACTION_STOP_WAITING_VIEW;


public class ChatMainActivity extends VectorAppCompatActivity {
    public static String RegStatus;
    public static String Username;
    public static String SipUsername;
    public static String SipPassword;
    public static SipProfile account;
    private WizardIface wizard = null;
    private String wizardId;
    private static final String ARG_MATRIX_ID = "MatrixMessageListFragment.ARG_MATRIX_ID";
    MXSession mSession;
    Bundle bundle;

    TextView txtbalance;
    String UserCurrency;
    TextView txtDisplayName;
    VectorCircularImageView profileImge;

    VectorPendingCallView mVectorPendingCallView;
    private static ChatMainActivity sharedInstance = null;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_chat_main;
    }
    @Override
    public void initUiAndData() {
        //super.initUiAndData();

        if (CommonActivityUtils.shouldRestartApp(this)) {
            CommonActivityUtils.restartApp(this);
            return;
        }

        if (CommonActivityUtils.isGoingToSplash(this)) {
            return;
        }

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedInstance = this;
        mVectorPendingCallView = findViewById(R.id.listView_pending_callview);
        mVectorPendingCallView.checkPendingCall();

        mVectorPendingCallView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IMXCall call = CallsManager.getSharedInstance().getActiveCall();
                if (null != call) {
                    final Intent intent = new Intent(ChatMainActivity.this, VectorCallViewActivity.class);
                    intent.putExtra(VectorCallViewActivity.EXTRA_MATRIX_ID, call.getSession().getCredentials().userId);
                    intent.putExtra(VectorCallViewActivity.EXTRA_CALL_ID, call.getCallId());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(intent);
                        }
                    });
                }
            }
        });

        mSession = Matrix.getInstance(this).getDefaultSession();
        bundle = new Bundle();
        bundle.putString(MXCActionBarActivity.EXTRA_MATRIX_ID, mSession.getMyUserId());
        bundle.putString(ARG_MATRIX_ID,mSession.getMyUserId());

        if (PermissionsToolsKt.checkPermissions(PermissionsToolsKt.PERMISSIONS_FOR_MEMBERS_SEARCH, this,PermissionsToolsKt.PERMISSION_REQUEST_CODE)) {
            new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground( Void... voids ) {
                    ContactsSync contactsSync = new ContactsSync(ChatMainActivity.this);
                    contactsSync.SyncContacts(true);
                    return null;
                }
            }.execute();
        }


        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_recent, R.id.navigation_contacts)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);


        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.drawer_menu);
        NavigationUI.setupWithNavController(toolbar,navController,drawer);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.nav_profile){
                    startActivity(new Intent(ChatMainActivity.this, VectorSettingsActivity.class));
                }
                return false;
            }
        });

        NavGraph navGraph = navController.getGraph();
        navGraph.addArgument(MXCActionBarActivity.EXTRA_MATRIX_ID, new NavArgument.Builder().setDefaultValue(mSession.getMyUserId()).build());
        navGraph.addArgument(ARG_MATRIX_ID, new NavArgument.Builder().setDefaultValue(mSession.getMyUserId()).build());
        navGraph.addArgument("VectorRecentsListFragment.ARG_MATRIX_ID",new NavArgument.Builder().setDefaultValue(mSession.getMyUserId()).build());
        navGraph.addArgument("VectorRecentsListFragment.ARG_LAYOUT_ID", new NavArgument.Builder().setDefaultValue(R.layout.fragment_vector_recents_list).build());
        navController.setGraph(navGraph);

        setWizardId();
        startSipService();
        long accountId = 1;
        account = SipProfile.getProfileFromDbId(this, accountId, DBProvider.ACCOUNT_FULL_PROJECTION);
        saveAccount(wizardId);

        View header = navigationView.getHeaderView(0);

        txtbalance = (TextView)header.findViewById(R.id.txtBalance);
        txtDisplayName = (TextView)header.findViewById(R.id.txtDisplayName);
        profileImge = header.findViewById(R.id.imgProfile);
        VectorUtils.loadUserAvatar(this, mSession,profileImge,mSession.getMyUser());
        txtDisplayName.setText(mSession.getMyUser().displayname);
        TextView txtPhoneNo = header.findViewById(R.id.txtPhoneNo);
        String[] tmp = mSession.getMyUserId().split("@");
        tmp = tmp[1].split(":");
        txtPhoneNo.setText(tmp[0]);

        GetBalance();

    }

    public static ChatMainActivity getInstance() {
        return sharedInstance;
    }

    private void checkNotificationPrivacySetting() {


        final PushManager pushManager = Matrix.getInstance(ChatMainActivity.this).getPushManager();

        if (pushManager.useFcm()) {
            if (!PreferencesManager.didMigrateToNotificationRework(this)) {
                PreferencesManager.setDidMigrateToNotificationRework(this);
                //By default we want to move users to NORMAL privacy, but if they were in reduced privacy we let them as is
                boolean backgroundSyncAllowed = pushManager.isBackgroundSyncAllowed();
                boolean contentSendingAllowed = pushManager.isContentSendingAllowed();

                if (contentSendingAllowed && !backgroundSyncAllowed) {
                    //former reduced, so stick with it (call to enforce)
                    pushManager.setNotificationPrivacy(PushManager.NotificationPrivacy.REDUCED, null);
                } else {
                    // default force to normal
                    pushManager.setNotificationPrivacy(PushManager.NotificationPrivacy.NORMAL, null);
                }

            }
        } else {

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                // The "Run in background" permission exists from android 6
                return;
            }

            /*
            if (pushManager.isBackgroundSyncAllowed() && !PreferencesManager.didAskUserToIgnoreBatteryOptimizations(this)) {
                PreferencesManager.setDidAskUserToIgnoreBatteryOptimizations(this);

                if (!SystemUtilsKt.isIgnoringBatteryOptimizations(this)) {
                    new AlertDialog.Builder(this)
                            .setCancelable(false)
                            .setTitle(R.string.startup_notification_fdroid_battery_optim_title)
                            .setMessage(R.string.startup_notification_fdroid_battery_optim_message)
                            .setPositiveButton(R.string.startup_notification_fdroid_battery_optim_button_grant, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.d(LOG_TAG, "checkNotificationPrivacySetting: user wants to grant the IgnoreBatteryOptimizations permission");

                                    // Request the battery optimization cancellation to the user
                                    SystemUtilsKt.requestDisablingBatteryOptimization(VectorHomeActivity.this,
                                            null,
                                            RequestCodesKt.BATTERY_OPTIMIZATION_FDROID_REQUEST_CODE);
                                }
                            })
                            .show();
                }
            }
            */
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyPresenceManager.createPresenceManager(this, Matrix.getInstance(this).getSessions());
        MyPresenceManager.advertiseAllOnline();

        VectorApp.getInstance().getNotificationDrawerManager().homeActivityDidResume(mSession != null ? mSession.getMyUserId() : null);

        mVectorPendingCallView.checkPendingCall();

        checkNotificationPrivacySetting();

    }
    @Override
    public void onDestroy() {
        super.onDestroy();

        // release the static instance if it is the current implementation
        if (sharedInstance == this) {
            sharedInstance = null;
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        CommonActivityUtils.onLowMemory(this);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        CommonActivityUtils.onTrimMemory(this, level);
    }

    public void startCall(String sessionId, String callId, MXUsersDevicesMap<MXDeviceInfo> unknownDevices) {
        // sanity checks
        if ((null != sessionId) && (null != callId)) {
            final Intent intent = new Intent(ChatMainActivity.this, VectorCallViewActivity.class);

            intent.putExtra(VectorCallViewActivity.EXTRA_MATRIX_ID, sessionId);
            intent.putExtra(VectorCallViewActivity.EXTRA_CALL_ID, callId);

            if (null != unknownDevices) {
                intent.putExtra(VectorCallViewActivity.EXTRA_UNKNOWN_DEVICES, unknownDevices);
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    startActivity(intent);
                }
            });
        }
    }

    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSession = Matrix.getInstance(this).getDefaultSession();
        bundle = new Bundle();
        bundle.putString(MXCActionBarActivity.EXTRA_MATRIX_ID, mSession.getMyUserId());
        bundle.putString(ARG_MATRIX_ID,mSession.getMyUserId());


        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_recent, R.id.navigation_contacts)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);


        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.drawer_menu);
        NavigationUI.setupWithNavController(toolbar,navController,drawer);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Toast.makeText(ChatMainActivity.this, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                drawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });

       NavGraph navGraph = navController.getGraph();
       navGraph.addArgument(MXCActionBarActivity.EXTRA_MATRIX_ID, new NavArgument.Builder().setDefaultValue(mSession.getMyUserId()).build());
       navGraph.addArgument(ARG_MATRIX_ID, new NavArgument.Builder().setDefaultValue(mSession.getMyUserId()).build());
        navGraph.addArgument("VectorRecentsListFragment.ARG_MATRIX_ID",new NavArgument.Builder().setDefaultValue(mSession.getMyUserId()).build());
        navGraph.addArgument("VectorRecentsListFragment.ARG_LAYOUT_ID", new NavArgument.Builder().setDefaultValue(R.layout.fragment_vector_recents_list).build());
       navController.setGraph(navGraph);

        setWizardId();
        startSipService();
        long accountId = 1;
        account = SipProfile.getProfileFromDbId(this, accountId, DBProvider.ACCOUNT_FULL_PROJECTION);
        saveAccount(wizardId);

    }
    */

    private void startSipService() {
        Thread t = new Thread("StartSip") {
            public void run() {
                Intent serviceIntent = new Intent(ChatMainActivity.this, SipService.class);
                serviceIntent.putExtra(SipManager.EXTRA_OUTGOING_ACTIVITY, new ComponentName(ChatMainActivity.this, ChatMainActivity.class));
                try {
                    startService(serviceIntent);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }

    private boolean setWizardId() {

        try {
            wizard = Basic.class.newInstance();
        } catch (IllegalAccessException e) {

            return false;
        } catch (InstantiationException e) {

            return false;
        }

        wizardId = "Basic";

        return true;
    }

    private void saveAccount(String wizardId) {
        boolean needRestart = false;

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);

        SipUsername = settings.getString("Username","");
        SipPassword = settings.getString("Password","");
        Username = SipUsername;

        PreferencesWrapper prefs = new PreferencesWrapper(
                getApplicationContext());
        account = wizard.buildAccount(account);
        account.wizard = wizardId;
        if (account.id == SipProfile.INVALID_ID) {
            // This account does not exists yet
            prefs.startEditing();
            wizard.setDefaultParams(prefs);
            prefs.endEditing();
            Uri uri = getContentResolver().insert(SipProfile.ACCOUNT_URI,
                    account.getDbContentValues());

            // After insert, add filters for this wizard
            account.id = ContentUris.parseId(uri);
            List<Filter> filters = wizard.getDefaultFilters(account);
            if (filters != null) {
                for (Filter filter : filters) {
                    // Ensure the correct id if not done by the wizard
                    filter.account = (int) account.id;
                    getContentResolver().insert(SipManager.FILTER_URI,
                            filter.getDbContentValues());
                }
            }
            // Check if we have to restart
            needRestart = wizard.needRestart();

        } else {
            // TODO : should not be done there but if not we should add an
            // option to re-apply default params
            prefs.startEditing();
            wizard.setDefaultParams(prefs);
            prefs.endEditing();
            getContentResolver().update(
                    ContentUris.withAppendedId(SipProfile.ACCOUNT_ID_URI_BASE,
                            account.id), account.getDbContentValues(), null,
                    null);
        }

        // Mainly if global preferences were changed, we have to restart sip
        // stack
        if (needRestart) {
            Intent intent = new Intent(SipManager.ACTION_SIP_REQUEST_RESTART);
            sendBroadcast(intent);
        }
    }

    private void GetBalance() {

        try {
            SharedPreferences settings = android.preference.PreferenceManager.getDefaultSharedPreferences(ChatMainActivity.this);

            final String cust_id = Settings.asHex(Settings.encrypt(settings.getString("Username", ""), Settings.ENC_KEY).getBytes());
            final String cust_pass = Settings.asHex(Settings.encrypt(settings.getString("Password", ""), Settings.ENC_KEY).getBytes());


            String url = Settings.BALANCE_API;

            RequestQueue queue = Volley.newRequestQueue(ChatMainActivity.this);
            StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {

                        response = response.trim();
                        JSONObject json = new JSONObject(response);
                        if (!json.isNull("credit")) {
                            NumberFormat format = NumberFormat.getCurrencyInstance(Locale.getDefault());
                            UserCurrency = json.getString("currency").substring(0,3);
                            format.setCurrency(Currency.getInstance(UserCurrency));
                            final String balance = format.format(json.getLong("credit"));
                            if (ChatMainActivity.this!=null) {
                                ChatMainActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        txtbalance.setText(balance);
                                    }
                                });
                            }
                        } else {
                            if (ChatMainActivity.this!=null) {
                                ChatMainActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ChatMainActivity.this, "An error, please try again later.", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (ChatMainActivity.this!=null) {
                            ChatMainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ChatMainActivity.this, "An Internal error, please try again later.", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    final VolleyError error1 = error;
                    if (ChatMainActivity.this!=null) {
                        ChatMainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ChatMainActivity.this, error1.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("cust_id", cust_id);
                    params.put("cust_pass", cust_pass);
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/x-www-form-urlencoded");
                    return params;
                }

            };
            queue.add(sr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
