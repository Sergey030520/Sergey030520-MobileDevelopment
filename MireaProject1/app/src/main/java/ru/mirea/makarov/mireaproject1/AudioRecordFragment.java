package ru.mirea.makarov.mireaproject1;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaExtractor;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AudioRecordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AudioRecordFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private String nameFile = "recorder.3gp";
    private String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + nameFile;

    private Button btn_stopRecord, btn_startRecord, btn_startAudioRecord,
    btn_stopAudioRecord, btn_pauseAudioRecord;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_CODE_PERMISSION = 100;
    private MediaRecorder mediaRecorder;
    private File audioFile;
    private Context context;
    private RecordService recordService;
    private MediaPlayer mediaPlayer;
    private String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
    };
    private MainActivity activity;
    private boolean isWork;

    public AudioRecordFragment() {

    }
    public AudioRecordFragment(Button[] btn, Context copy_context, Activity copy_activity, MainActivity copy_mainActivity){
        mediaPlayer = new MediaPlayer();
        context = copy_context;
        btn_stopRecord = btn[0];btn_startRecord = btn[1];
        btn_startAudioRecord = btn[2]; btn_stopAudioRecord = btn[3];
        activity = copy_mainActivity;
        mediaRecorder = new MediaRecorder();
        isWork = hasPermissions(copy_context, PERMISSIONS);
        if (!isWork) {
            ActivityCompat.requestPermissions(copy_activity, PERMISSIONS,
                    REQUEST_CODE_PERMISSION);
        }
    }
    public static boolean hasPermissions(Context context, String[] permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            isWork = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
    }
    public void startRecording() throws IOException {
        // проверка доступности sd - карты
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            if (audioFile == null) {
                audioFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_MUSIC), nameFile);
            }
            mediaRecorder.setOutputFile(audioFile.getAbsolutePath());
            mediaRecorder.prepare();
            mediaRecorder.start();
            Toast.makeText(context, "Recording started!", Toast.LENGTH_SHORT).show();
            btn_startRecord.setEnabled(false);
        }
    }
    public void stopRecording() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            Toast.makeText(context, "Save record!",
                    Toast.LENGTH_SHORT).show();
        }
        btn_stopAudioRecord.setVisibility(View.VISIBLE);
        btn_startAudioRecord.setVisibility(View.VISIBLE);
        btn_startRecord.setEnabled(true);
        processAudioFile();
    }

    public void onPlayMusic() {
        /*context.startService(
                new Intent( activity, RecordService.class));*/
        try {
            btn_startAudioRecord.setEnabled(false);
            btn_startRecord.setEnabled(false);
            btn_stopRecord.setEnabled(false);
            mediaPlayer.setDataSource(file_path);
            mediaPlayer.prepare();
            mediaPlayer.start();
           /* recordService = new RecordService(mediaPlayer);
            recordService.startService(new Intent(activity, RecordService.class));*/
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void onStopMusic(){
        btn_startRecord.setEnabled(true);
        btn_startAudioRecord.setEnabled(true);
        btn_stopAudioRecord.setEnabled(true);
        btn_stopRecord.setEnabled(true);
        mediaPlayer.stop();
        mediaPlayer.release();
        /*recordService.stopService(new Intent(activity, RecordService.class));*/
        /*context.stopService(
                new Intent(activity, PlayerService.class));*/
    }
    private void processAudioFile() {
        Log.d(TAG, "processAudioFile");
        ContentValues values = new ContentValues(4);
        long current = System.currentTimeMillis();
        // установка meta данных созданному файлу
        values.put(MediaStore.Audio.Media.TITLE, "audio" + audioFile.getName());
        values.put(MediaStore.Audio.Media.DATE_ADDED, (int) (current / 1000));
        values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/3gpp");
        values.put(MediaStore.Audio.Media.DATA, audioFile.getAbsolutePath());
        ContentResolver contentResolver = context.getContentResolver();
        Log.d(TAG, "audioFile: " + audioFile.canRead());
        Uri baseUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Uri newUri = contentResolver.insert(baseUri, values);
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newUri));
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AudioRecordFragment.
     */
    public static AudioRecordFragment newInstance(String param1, String param2) {
        AudioRecordFragment fragment = new AudioRecordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_audio_record, container, false);
    }
}