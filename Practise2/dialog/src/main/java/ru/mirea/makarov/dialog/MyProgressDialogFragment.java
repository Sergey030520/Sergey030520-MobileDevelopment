package ru.mirea.makarov.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;

public class MyProgressDialogFragment {
   public MyProgressDialogFragment(Context context){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        progressDialog.setTitle("Mirea");
        progressDialog.setMessage("Подождите чуть чуть!");
        progressDialog.show();
    }
}
