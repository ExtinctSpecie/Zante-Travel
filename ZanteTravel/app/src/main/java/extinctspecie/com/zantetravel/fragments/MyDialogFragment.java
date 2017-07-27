package extinctspecie.com.zantetravel.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by WorkSpace on 27-Jul-17.
 */

public class MyDialogFragment extends DialogFragment
{
    static MyDialogFragment newInstance (int dialogID)
    {
        MyDialogFragment dialogFragment = new MyDialogFragment();

        return dialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
