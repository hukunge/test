/*
 * Copyright Google Inc. All Rights Reserved.
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
package pub.devrel.easypermissions;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.helper.PermissionHelper;

public class EasyPermissions {

    private static final String TAG = "EasyPermissions";

    public static boolean hasPermissions(Context context, @NonNull String... perms) {
        // Always return true for SDK < M, let the system deal with the permissions
        //hasPermissions: API version < M, returning true by default
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        // Null context may be passed if we have detected Low API (less than M) so getting
        // to this point with a null context should not be possible.
        if (context == null) {
            throw new IllegalArgumentException("Can't check permissions for null context");
        }

        for (String perm : perms) {
            if (ContextCompat.checkSelfPermission(context, perm) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }

        return true;
    }

    public static void requestPermissions(@NonNull Activity host, @NonNull String rationale, int requestCode, @NonNull String... perms) {
        requestPermissions(host, rationale, android.R.string.ok, android.R.string.cancel, requestCode, perms);
    }

    public static void requestPermissions(@NonNull Fragment host, @NonNull String rationale, int requestCode, @NonNull String... perms) {
        requestPermissions(host, rationale, android.R.string.ok, android.R.string.cancel, requestCode, perms);
    }

    public static void requestPermissions(@NonNull android.app.Fragment host, @NonNull String rationale, int requestCode, @NonNull String... perms) {
        requestPermissions(host, rationale, android.R.string.ok, android.R.string.cancel, requestCode, perms);
    }

    public static void requestPermissions(@NonNull Activity host, @NonNull String rationale, @StringRes int positiveButton, @StringRes int negativeButton, int requestCode, @NonNull String... perms) {
        requestPermissions(PermissionHelper.newInstance(host), rationale, positiveButton, negativeButton, requestCode, perms);
    }

    public static void requestPermissions(@NonNull Fragment host, @NonNull String rationale, @StringRes int positiveButton, @StringRes int negativeButton, int requestCode, @NonNull String... perms) {
        requestPermissions(PermissionHelper.newInstance(host), rationale, positiveButton, negativeButton, requestCode, perms);
    }

    public static void requestPermissions(@NonNull android.app.Fragment host, @NonNull String rationale, @StringRes int positiveButton, @StringRes int negativeButton, int requestCode, @NonNull String... perms) {
        requestPermissions(PermissionHelper.newInstance(host), rationale, positiveButton, negativeButton, requestCode, perms);
    }

    private static void requestPermissions(@NonNull PermissionHelper helper, @NonNull String rationale, @StringRes int positiveButton, @StringRes int negativeButton, int requestCode, @NonNull String... perms) {
        // Check for permissions before dispatching the request
        if (hasPermissions(helper.getContext(), perms)) {
            notifyAlreadyHasPermissions(helper.getHost(), requestCode, perms);
            return;
        }

        // Request permissions
        helper.requestPermissions(rationale, positiveButton, negativeButton, requestCode, perms);
    }

    public static void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, @NonNull Object... receivers) {
        // Make a collection of granted and denied permissions from the request.
        List<String> granted = new ArrayList<>();
        List<String> denied = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            String perm = permissions[i];
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                granted.add(perm);
            } else {
                denied.add(perm);
            }
        }

        // iterate through all receivers
        for (Object object : receivers) {
            // Report granted permissions, if any.
            if (!granted.isEmpty()) {
                if (object instanceof PermissionCallbacks) {
                    ((PermissionCallbacks) object).onPermissionsGranted(requestCode, granted);
                }
            }

            // Report denied permissions, if any.
            if (!denied.isEmpty()) {
                if (object instanceof PermissionCallbacks) {
                    ((PermissionCallbacks) object).onPermissionsDenied(requestCode, denied);
                }
            }

            // If 100% successful, call annotated methods
            if (!granted.isEmpty() && denied.isEmpty()) {
                runAnnotatedMethods(object, requestCode);
            }
        }
    }

    public static boolean somePermissionPermanentlyDenied(@NonNull Activity host, @NonNull List<String> deniedPermissions) {
        return PermissionHelper.newInstance(host).somePermissionPermanentlyDenied(deniedPermissions);
    }

    public static boolean somePermissionPermanentlyDenied(@NonNull Fragment host, @NonNull List<String> deniedPermissions) {
        return PermissionHelper.newInstance(host).somePermissionPermanentlyDenied(deniedPermissions);
    }

    public static boolean somePermissionPermanentlyDenied(@NonNull android.app.Fragment host, @NonNull List<String> deniedPermissions) {
        return PermissionHelper.newInstance(host).somePermissionPermanentlyDenied(deniedPermissions);
    }

    public static boolean permissionPermanentlyDenied(@NonNull Activity host, @NonNull String deniedPermission) {
        return PermissionHelper.newInstance(host).permissionPermanentlyDenied(deniedPermission);
    }

    public static boolean permissionPermanentlyDenied(@NonNull Fragment host, @NonNull String deniedPermission) {
        return PermissionHelper.newInstance(host).permissionPermanentlyDenied(deniedPermission);
    }

    public static boolean permissionPermanentlyDenied(@NonNull android.app.Fragment host, @NonNull String deniedPermission) {
        return PermissionHelper.newInstance(host).permissionPermanentlyDenied(deniedPermission);
    }

    public static boolean somePermissionDenied(@NonNull Activity host, @NonNull String... perms) {
        return PermissionHelper.newInstance(host).somePermissionDenied(perms);
    }

    public static boolean somePermissionDenied(@NonNull Fragment host, @NonNull String... perms) {
        return PermissionHelper.newInstance(host).somePermissionDenied(perms);
    }

    public static boolean somePermissionDenied(@NonNull android.app.Fragment host, @NonNull String... perms) {
        return PermissionHelper.newInstance(host).somePermissionDenied(perms);
    }

    private static void notifyAlreadyHasPermissions(@NonNull Object object, int requestCode, @NonNull String[] perms) {
        int[] grantResults = new int[perms.length];
        for (int i = 0; i < perms.length; i++) {
            grantResults[i] = PackageManager.PERMISSION_GRANTED;
        }

        onRequestPermissionsResult(requestCode, perms, grantResults, object);
    }

    private static void runAnnotatedMethods(@NonNull Object object, int requestCode) {
        Class clazz = object.getClass();
        if (isUsingAndroidAnnotations(object)) {
            clazz = clazz.getSuperclass();
        }

        while (clazz != null) {
            for (Method method : clazz.getDeclaredMethods()) {
                AfterPermissionGranted ann = method.getAnnotation(AfterPermissionGranted.class);
                if (ann != null) {
                    // Check for annotated methods with matching request code.
                    if (ann.value() == requestCode) {
                        // Method must be void so that we can invoke it
                        if (method.getParameterTypes().length > 0) {
                            throw new RuntimeException(
                                    "Cannot execute method " + method.getName() + " because it is non-void method and/or has input parameters.");
                        }

                        try {
                            // Make method accessible if private
                            if (!method.isAccessible()) {
                                method.setAccessible(true);
                            }
                            method.invoke(object);
                        } catch (IllegalAccessException e) {
                            Log.e(TAG, "runDefaultMethod:IllegalAccessException", e);
                        } catch (InvocationTargetException e) {
                            Log.e(TAG, "runDefaultMethod:InvocationTargetException", e);
                        }
                    }
                }
            }

            clazz = clazz.getSuperclass();
        }
    }

    private static boolean isUsingAndroidAnnotations(@NonNull Object object) {
        if (!object.getClass().getSimpleName().endsWith("_")) {
            return false;
        }
        try {
            Class clazz = Class.forName("org.androidannotations.api.view.HasViews");
            return clazz.isInstance(object);
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
