package com.google.example.games.basegameutils;

import java.io.IOException;
import java.util.Set;
import java.util.HashSet;

import org.json.JSONObject;

import android.util.Log;

import org.apache.http.client.HttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.ClientProtocolException;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.Player;

public class GameHelperToken implements GoogleApiClient.ServerAuthCodeCallbacks {
    static final String TAG = "GameHelperToken";

    // Print debug logs?
    boolean mDebugLog = false;
    JSONObject SERVER_SETTINGS;

    public GameHelperToken(JSONObject serverSettings) {
        SERVER_SETTINGS = serverSettings;
    }


    /** Enables debug logging */
    public void enableDebugLog(boolean enabled) {
        mDebugLog = enabled;
        if (enabled) {
            debugLog("GameHelperToken Debug log enabled.");
        }
    }

    void debugLog(String message) {
        if (mDebugLog) {
            Log.d(TAG, "GameHelperToken: " + message);
        }
    }

    private boolean serverHasTokenFor(String idToken) {
        debugLog("idToken =" + idToken);

/*
        HttpClient httpClient = new DefaultHttpClient();
        String CHECK_TOKEN_URL = "";
        HttpGet httpGet = new HttpGet(CHECK_TOKEN_URL);
        try {
                HttpResponse httpResponse = httpClient.execute(httpGet);
                int responseCode = httpResponse.getStatusLine().getStatusCode();
                String responseBody = EntityUtils.toString(httpResponse.getEntity());

                // Convert the response to set of Scope objects.
                if (responseCode == 200) {
                    return true;
                } else {
                    Log.e(TAG, "Error in getting server scopes: " + responseCode);
                }

            } catch (ClientProtocolException e) {
                Log.e(TAG, "Error in getting server scopes.", e);
            } catch (IOException e) {
                Log.e(TAG, "Error in getting server scopes.", e);
            }
*/
        return false;
    }

    @Override
    public CheckResult onCheckServerAuthorization(String idToken, Set<Scope> scopeSet) {
        Log.i(TAG, "Checking if server is authorized.");

        // Check if the server has a token.  Since this callback executes in a background
        // thread it is OK to do synchronous network access in this check.
        boolean serverHasToken = serverHasTokenFor(idToken);
        Log.i(TAG, "Server has token: " + String.valueOf(serverHasToken));

        HashSet<Scope> serverScopeSet = new HashSet<Scope>();
        serverScopeSet.add(Games.SCOPE_GAMES);
        debugLog("onCheckServerAuthorization");
        debugLog("onCheckServerAuthorization idToken=" + idToken);
        debugLog("onCheckServerAuthorization scopeSet=" + scopeSet);
        debugLog("serverScopeSet=" + serverScopeSet);
        return CheckResult.newAuthRequiredResult(serverScopeSet);
/*
        if (!serverHasToken) {
            // Server does not have a valid refresh token, so request a new
            // auth code which can be exchanged for one.  This will cause the user to see the
            // consent dialog and be prompted to grant offline access.

            // Ask the server which scopes it would like to have for offline access.  This
            // can be distinct from the scopes granted to the client.  By getting these values
            // from the server, you can change your server's permissions without needing to
            // recompile the client application.

            // String[] scopeStrings = [
            // "https://www.googleapis.com/auth/plus.login",
            // "https://www.googleapis.com/auth/plus.me",
            // "https://www.googleapis.com/auth/plus.login",
            // "https://www.googleapis.com/auth/userinfo.email",
            // "https://www.googleapis.com/auth/userinfo.profile"
            // = 'https://www.googleapis.com/auth/plus.stream.write'
            // ES] = 'https://www.googleapis.com/auth/plus.stream.write']


            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(SELECT_SCOPES_URL);
            HashSet<Scope> serverScopeSet = new HashSet<Scope>();

            try {
                HttpResponse httpResponse = httpClient.execute(httpGet);
                int responseCode = httpResponse.getStatusLine().getStatusCode();
                String responseBody = EntityUtils.toString(httpResponse.getEntity());

                // Convert the response to set of Scope objects.
                if (responseCode == 200) {
                    String[] scopeStrings = responseBody.split(" ");
                    for (String scope : scopeStrings) {
                        Log.i(TAG, "Server Scope: " + scope);
                        serverScopeSet.add(new Scope(scope));
                    }
                } else {
                    Log.e(TAG, "Error in getting server scopes: " + responseCode);
                }

            } catch (ClientProtocolException e) {
                Log.e(TAG, "Error in getting server scopes.", e);
            } catch (IOException e) {
                Log.e(TAG, "Error in getting server scopes.", e);
            }

            // This tells GoogleApiClient that the server needs a new serverAuthCode with
            // access to the scopes in serverScopeSet.  Note that we are not asking the server
            // if it already has such a token because this is a sample application.  In reality,
            // you should only do this on the first user sign-in or if the server loses or deletes
            // the refresh token.
            return CheckResult.newAuthRequiredResult(serverScopeSet);
        } else {
            // Server already has a valid refresh token with the correct scopes, no need to
            // ask the user for offline access again.
            return CheckResult.newAuthNotRequiredResult();
        }
*/
    }

    @Override
    public boolean onUploadServerAuthCode(String idToken, String serverAuthCode) {
        // Upload the serverAuthCode to the server, which will attempt to exchange it for
        // a refresh token.  This callback occurs on a background thread, so it is OK
        // to perform synchronous network access.  Returning 'false' will fail the
        // GoogleApiClient.connect() call so if you would like the client to ignore
        // server failures, always return true.
        debugLog("onUploadServerAuthCode");
        debugLog("idToken=" + idToken);
        debugLog("serverAuthCode=" + serverAuthCode);
        return true;
/*
        EXCHANGE_TOKEN_URL =  ""; //TODO
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(EXCHANGE_TOKEN_URL);

        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("idToken", idToken));
            nameValuePairs.add(new BasicNameValuePair("serverAuthCode", serverAuthCode));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            final String responseBody = EntityUtils.toString(response.getEntity());
            Log.i(TAG, "Code: " + statusCode);
            Log.i(TAG, "Resp: " + responseBody);

            // ...
            return (statusCode == 200);
        } catch (ClientProtocolException e) {
            Log.e(TAG, "Error in auth code exchange.", e);
            return false;
        } catch (IOException e) {
            Log.e(TAG, "Error in auth code exchange.", e);
            return false;
        }
*/
    }
}