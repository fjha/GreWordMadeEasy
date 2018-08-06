package application.android.flora.grewordmadeeasy.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
/**
 *  @author flora jha on 27/05/17
 */
public class ViewUtils {

    public static AlertDialog.Builder newBestAlertDialogBuilder(Context context) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_DeviceDefault_Dialog_Alert);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_DeviceDefault_Dialog);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        return builder;
    }
}
