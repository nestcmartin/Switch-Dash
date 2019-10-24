package es.ucm.vdm.engine.android;

import android.content.Context;
import android.content.res.AssetManager;
/* API MIN LV 24
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
*/


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import es.ucm.vdm.engine.FileIO;


public class AndroidFileIO implements FileIO {

    private Context context_;
    private AssetManager assets_;
    private String externalStoragePath_;

    public AndroidFileIO(Context context) {
        this.context_ = context;
        this.assets_ = context_.getAssets();
        this.externalStoragePath_ = context_.getExternalFilesDir(null)
                .getAbsolutePath() + File.separator;
    }

    public InputStream readAsset(String fileName) throws IOException {
        return assets_.open(fileName);
    }

    public InputStream readFile(String fileName) throws IOException {
        return new FileInputStream(externalStoragePath_ + fileName);
    }

    public OutputStream writeFile(String fileName) throws IOException {
        return new FileOutputStream(externalStoragePath_ + fileName);
    }

    /* API MIN LV 24
    public SharedPreferences getPreferences() {
        return PreferenceManager.getDefaultSharedPreferencesName(context_);
    }*/

}