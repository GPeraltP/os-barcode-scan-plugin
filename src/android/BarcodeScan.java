package cordova.plugin.barcodescan;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import android.os.Build;

import android.Manifest;

import android.app.Activity;
import android.preference.PreferenceManager;
import android.util.AndroidRuntimeException;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.client.android.Intents;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
//import com.mikepenz.aboutlibraries.LibsBuilder;

import androidx.core.app.ActivityCompat;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

/**
 * This class echoes a string called from JavaScript.
 */
public class BarcodeScan extends CordovaPlugin {

    private PluginResult pluginResultOK = new PluginResult(PluginResult.Status.OK,"1");
    private PluginResult pluginResultERROR = new PluginResult(PluginResult.Status.ERROR,"0");
    private PluginResult pluginResultNORESULT = new  PluginResult(PluginResult.Status.NO_RESULT);

    private static final String ACTION_GET_BARCODE_SCAN = "getBarcodeScan";

    private CallbackContext newCallbackContext = null;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        
        if (ACTION_GET_BARCODE_SCAN.equals(action)) {
            newCallbackContext = callbackContext;

            activity.runOnUiThread(new Runnable(){
                public void run(){
                    final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
                    result -> {
                        if(result.getContents() == null) {
                            Toast.makeText(cordova.getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
                            newCallbackContext.sendPluginResult(pluginResultOK);
                            return true;
                        } else {
                            Toast.makeText(cordova.getActivity(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                            newCallbackContext.sendPluginResult(pluginResultOK);
                            return true;
                        }
                    });
                    pluginResultNORESULT.setKeepCallback(true);
                    barcodeLauncher.launch(new ScanOptions());
                }
            });           
        }

        return false;
    }

}