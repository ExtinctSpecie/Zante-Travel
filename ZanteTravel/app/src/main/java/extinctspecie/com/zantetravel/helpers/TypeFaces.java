package extinctspecie.com.zantetravel.helpers;

import android.content.Context;
import android.graphics.Typeface;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import java.io.PrintStream;

/**
 * Created by WorkSpace on 5/3/2017.
 */

public class TypeFaces {
    private static Typeface PUNK_BOY;
    private static Typeface ISABELLA;
    private static Typeface APPLE_BUTTER;
    private static Typeface SF_DIEGO_SANS;
    private static Typeface FAV_FONT;

    public static void initializeFonts(Context context) {
        if (PUNK_BOY == null) {
            PUNK_BOY = Typeface.createFromAsset(context.getAssets(), "fonts/punkboy_tbs.ttf");
            ISABELLA = Typeface.createFromAsset(context.getAssets(), "fonts/Isabella.ttf");
            APPLE_BUTTER = Typeface.createFromAsset(context.getAssets(), "fonts/Apple_Butter.ttf");
            SF_DIEGO_SANS = Typeface.createFromAsset(context.getAssets(), "fonts/SFDiegoSans.ttf");
            FAV_FONT = Typeface.createFromAsset(context.getAssets(), "fonts/CongratsScript.ttf");

        } else {
            Log.v("Fonts : ", "Fonts already initialized remove the second call");
        }

    }

    public static Typeface getPunkBoy() {
        if (PUNK_BOY != null) {
            return PUNK_BOY;
        } else throw new NullPointerException() {
            @Override
            public void printStackTrace() {
                super.printStackTrace();
            }
        };
    }

    public static Typeface getIsabella() {
        if (ISABELLA != null) {
            return ISABELLA;
        } else throw new NullPointerException() {
            @Override
            public void printStackTrace() {
                super.printStackTrace();
            }
        };
    }

    public static Typeface getAppleButter() {
        if (APPLE_BUTTER != null) {
            return APPLE_BUTTER;
        } else throw new NullPointerException() {
            @Override
            public void printStackTrace() {
                super.printStackTrace();
            }
        };
    }

    public static Typeface getSFDiegoSans() {
        if (SF_DIEGO_SANS != null) {
            return SF_DIEGO_SANS;
        } else throw new NullPointerException() {
            @Override
            public void printStackTrace() {
                super.printStackTrace();
            }
        };
    }

    public static Typeface getFavFont() {
        if (FAV_FONT != null) {
            return FAV_FONT;
        } else throw new NullPointerException() {
            @Override
            public void printStackTrace() {
                super.printStackTrace();
            }
        };
    }

}
