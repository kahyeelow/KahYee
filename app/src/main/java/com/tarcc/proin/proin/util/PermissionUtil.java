package com.tarcc.proin.proin.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by etc03 on 11/01/2018.
 */

public class PermissionUtil {
    public static int REQUEST_CODE = 111;
    public static int PERMISSION_GRANTED = PackageManager.PERMISSION_GRANTED;

    public static String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static String CAMERA = Manifest.permission.CAMERA;
    public static String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;

    public static boolean isPermissionGranted(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermission(Activity activity, String[] permission) {
        ActivityCompat.requestPermissions(activity, permission, REQUEST_CODE);
    }

    public static void requestPermission(Activity activity, String permission) {
        ActivityCompat.requestPermissions(activity, new String[]{permission}, REQUEST_CODE);
    }
}
