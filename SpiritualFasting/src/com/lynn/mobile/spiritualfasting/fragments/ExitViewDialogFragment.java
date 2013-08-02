package com.lynn.mobile.spiritualfasting.fragments;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.lynn.mobile.spiritualfasting.R;
import com.lynn.mobile.spiritualfasting.YourFastDetailActivity;
import com.lynn.mobile.spiritualfasting.util.Resources;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

public class ExitViewDialogFragment extends SherlockDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_exit)
               .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       if(getFragmentManager().findFragmentByTag(Resources.JOURNAL_FRAGMENT) 
                    		   != null) {
                    	   JournalEntryFragment.saveJournalEntry(getSherlockActivity());
                       }
                   }
               })
               .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   if(getFragmentManager().findFragmentByTag(Resources.JOURNAL_FRAGMENT) != null) {
							YourFastDetailActivity activity = (YourFastDetailActivity) getSherlockActivity();
							getFragmentManager().popBackStackImmediate();
	                		activity.toggleNavigationButtons(View.VISIBLE);
                	   }
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
