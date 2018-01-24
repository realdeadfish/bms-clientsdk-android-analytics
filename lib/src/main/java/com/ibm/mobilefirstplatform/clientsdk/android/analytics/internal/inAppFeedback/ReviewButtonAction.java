package com.ibm.mobilefirstplatform.clientsdk.android.analytics.internal.inAppFeedback;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

/**
 * Created by mohlogan on 17/01/18.
 */

public class ReviewButtonAction extends AlertDialog{

    /**
     * Clicking "YES,DISMISS"
     */
    private static class Option1 implements OnClickListener {
        private Activity activity;
        private String filename;

        public Option1(Activity activity, String filename) {
            this.activity = activity;
            this.filename = filename;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            Intent intent = new Intent(activity, com.ibm.mobilefirstplatform.clientsdk.android.analytics.internal.inAppFeedback.ReviewPopup.class);
            intent.putExtra("imagename", filename);
            activity.startActivityForResult(intent, 200);
        }
    }

    /**
     * Clicking "NO,DISMISS"
     */
    private static class Option2 implements OnClickListener {
        private Activity activity;
        private String filename;

        public Option2(Activity activity, String filename) {
            this.activity = activity;
            this.filename = filename;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            //Discard the changes and close the feedback activity
            if(filename.equals("ALL")){
                Utility.deleteAllFiles();
            }else {
                Utility.discardFeedbackFiles(filename);
            }
            activity.finish();
        }
    }

    /**
     * Clicking "NO,CANCEL"
     */
    private static class Option3 implements OnClickListener {
        private Activity activity;

        public Option3(Activity activity) {
            this.activity = activity;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            //Do nothing
        }
    }

    /**
     * Clicking "NO,CANCEL"
     */
    private static class Option4 implements OnClickListener {
        private Activity activity;

        public Option4(Activity activity, String filename) {
            this.activity = activity;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            //Do nothing
        }
    }

    public ReviewButtonAction(Activity activity, String filename, boolean isEdited, String from) {
        super(activity);

        String titleString = "Dismiss App Feedback?";
        String alertReasonString = "Do you want to SAVE/DISCARD the changes before Exit?";
        String optionString1 = "DISCARD,DISMISS";
        String optionString2 = "SAVE,DISMISS";
        String optionString3 = "NO,CANCEL";

        if (from.equals("review")) {
            titleString = "Review App Feedback?";
            this.setTitle(titleString);
            if (isEdited) {
                alertReasonString = "All Data & comments made can be reviewed. \n Do you want to continue?";
                optionString1 = "YES, REVIEW";
                optionString2 = "DISCARD, DISMISS";
                optionString3 = "NO, CANCEL";

                this.setMessage(alertReasonString);
                this.setButton(AlertDialog.BUTTON_POSITIVE, optionString1, new Option1(activity, filename));
                this.setButton(AlertDialog.BUTTON_NEUTRAL, optionString2, new Option2(activity,filename));
                this.setButton(AlertDialog.BUTTON_NEGATIVE, optionString3, new Option3(activity));
            } else {
                alertReasonString = "No Comments Made. \n Do you want to Review other feedback?";
                optionString1 = "YES, REVIEW";
                optionString3 = "NO, CANCEL";

                this.setMessage(alertReasonString);
                this.setButton(AlertDialog.BUTTON_POSITIVE, optionString1, new Option2(activity, filename));
                this.setButton(AlertDialog.BUTTON_NEGATIVE, optionString3, new Option3(activity));
            }
        }
    }
}
