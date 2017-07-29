package extinctspecie.com.zantetravel.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import extinctspecie.com.zantetravel.R;

/**
 * Created by WorkSpace on 27-Jul-17.
 */

public class AboutUsDialogFragment extends DialogFragment
{
    public static AboutUsDialogFragment newInstance ()
    {
        AboutUsDialogFragment dialogFragment = new AboutUsDialogFragment();

        return dialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_dialog_about_us, container, false);

        return v;

    }
}
