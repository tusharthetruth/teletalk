package com.chatapp.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.FileUtils;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.android.volley.VolleyError;
import com.chatapp.Settings;
import com.chatapp.adapters.RecentUpdateAdapter;
import com.chatapp.network.VolleyApi;
import com.chatapp.network.VolleyListener;
import com.chatapp.pixly.pix.Options;
import com.chatapp.pixly.pix.Pix;
import com.chatapp.sip.utils.Log;
import com.chatapp.util.LocalContactItem;
import com.chatapp.util.LocalContactsHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import im.vector.R;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShareFragment extends Fragment implements View.OnClickListener, VolleyListener, RecentUpdateAdapter.IShareAdapterClickListner {

    private Activity context;
    private RecyclerView rv;
    private ConstraintLayout statusContainer;
    private RecentUpdateAdapter adapter;
    List<LocalContactItem> localContactItemList;
    private VolleyApi volleyApi;
    private String CONTACT_STATUS = "CONTACT STATUS";
    SharedPreferences settings;

    public ShareFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        volleyApi = new VolleyApi(requireActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_share, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        localContactItemList = new LocalContactsHandler(getContext()).GetLocalContacts();

        rv = view.findViewById(R.id.rvList);
        statusContainer = view.findViewById(R.id.statusContainer);
        rv.setLayoutManager(new LinearLayoutManager(context));
        statusContainer.setOnClickListener(this);
        setData();
        getOtherContacts();

    }

    private void getOtherContacts() {
        try {
            JSONObject object = new JSONObject();
            ArrayList<String> list = new ArrayList<>();
            for (LocalContactItem item : localContactItemList) {
                list.add(item.Phone);
            }
            object.put("list", list.toArray().toString());
            volleyApi.post(this, "", object, CONTACT_STATUS);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void setData() {
        ArrayList<RecentModel> list = new ArrayList<>();
        list.add(new RecentModel());
        list.add(new RecentModel());
        list.add(new RecentModel());
        list.add(new RecentModel());
        list.add(new RecentModel());
        list.add(new RecentModel());
        list.add(new RecentModel());
        list.add(new RecentModel());
        list.add(new RecentModel());
        list.add(new RecentModel());
        adapter = new RecentUpdateAdapter(list, context);
        adapter.setListner(this);
        rv.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (100): {
                if (resultCode == Activity.RESULT_OK) {
                    ArrayList<String> returnValue = new ArrayList<>();
                    returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
                    uploadFiles(returnValue);
                }
            }
            break;
        }
    }

    private void uploadFiles(ArrayList<String> returnValue) {
        File file = new File(returnValue.get(0));
        settings = PreferenceManager.getDefaultSharedPreferences(requireActivity());

        String userName = settings.getString("Username", "");
        userName = Settings.asHex(Settings.encrypt(userName, Settings.ENC_KEY).getBytes());
        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);

        StatusApi getResponse = StatusApiClient.getRetrofit().create(StatusApi.class);

        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("image", file.getName(), mFile);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        Call<FileUploadResponse> call = getResponse.upload(userName, fileToUpload);
        call.enqueue(new Callback<FileUploadResponse>() {
            @Override
            public void onResponse(Call<FileUploadResponse> call, Response<FileUploadResponse> response) {
                FileUploadResponse serverResponse = (FileUploadResponse) response.body();
                if (serverResponse != null) {
                    if (serverResponse.getResult().equalsIgnoreCase("success")) {
                        Toast.makeText(requireActivity(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireActivity(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    assert serverResponse != null;
                    Log.v("Response", serverResponse.toString());
                }
            }

            @Override
            public void onFailure(Call<FileUploadResponse> call, Throwable t) {
                Log.d("response", t.toString());

            }
        });
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.statusContainer) {
            Pix.start(this, Options.init().
                    setCount(3).
                    setExcludeVideos(true).
                    setRequestCode(100));
        }
    }

    @Override
    public void onResponse(JSONObject jsonObject, String tag) {
        try {
            Log.d("res", tag);
        } catch (Exception e) {
        }
    }

    @Override
    public void onError(VolleyError error, String tag) {
        try {
            Log.d("err", tag);

        } catch (Exception e) {
        }
    }

    @Override
    public void onStatusClick() {
        Pix.start(this, Options.init().
                setCount(3).
                setExcludeVideos(true).
                setRequestCode(100));
    }
}
