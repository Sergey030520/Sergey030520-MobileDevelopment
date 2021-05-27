package ru.mirea.makarov.mireaproject1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ru.mirea.makarov.mireaproject1.ui.SimpleWebViewClientImpl;

import androidx.recyclerview.widget.RecyclerView;
public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private TextView resTextView, inputText;
    private TextView urlText, styleText, colorText, edit_background;
    private WebView webView;
    private Button back_WebEl, next_webEl;
    private ImageButton reload_WebEl, load_WebEl;
    private MultiAutoCompleteTextView input_url;
    private MediaPlayer mediaPlayer;
    private AudioRecordFragment audioRecordFragment;
    private SensorStatusFragment sensorStatusFragment;
    private static final int REQUEST_CODE_PERMISSION_CAMERA = 100;
    final String TAG = MainActivity.class.getSimpleName();
    private ImageView createImageView;
    private static final int CAMERA_REQUEST = 0;
    private boolean isWork = false;
    private Uri imageUri;
    private SettingsFragment settingsFragment;
    private ArrayList<History> histories = new ArrayList<History>();
    private HistoryFragment historyFragment;
    private HistoryAdapter adapter;
    private Button btn_playMusic, btn_stopMusic, btn_pauseMusic;
    private  RecyclerView recyclerView;
    private TextView resSetting;
    TextView nameHistory, contentHistory;
    Button   btn_CreateHistory;
    FloatingActionButton btn_addHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_brouser, R.id.nav_calculator,
        R.id.nav_music, R.id.nav_audio_record, R.id.nav_sensor_status, R.id.nav_createImage, R.id.nav_settings, R.id.nav_history)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it) is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void onClickLoadHistory(View view){
        findViewById(R.id.btn_historyStart).setVisibility(View.INVISIBLE); findViewById(R.id.list).setVisibility(View.VISIBLE);
        findViewById(R.id.floatingActionButton).setVisibility(View.VISIBLE);
        historyFragment = new HistoryFragment(this, this, findViewById(R.id.control));
        histories = historyFragment.getHistories();
        recyclerView = (RecyclerView) findViewById(R.id.list);
        adapter = new HistoryAdapter(this, histories);
        recyclerView.setAdapter(adapter);
    }
    public void onClickAddHistory(View view){
        nameHistory  = findViewById(R.id.newNameHistory);
        contentHistory = findViewById(R.id.newContentHistory);
        btn_addHistory = findViewById(R.id.floatingActionButton);
        findViewById(R.id.list).setVisibility(View.INVISIBLE);
        btn_addHistory.setVisibility(View.INVISIBLE);
        nameHistory.setVisibility(View.VISIBLE); contentHistory.setVisibility(View.VISIBLE);
        btn_CreateHistory = findViewById(R.id.btn_createHistory);
        btn_CreateHistory.setVisibility(View.VISIBLE);
    }
    public void onClickCreateHistory(View view){
        historyFragment.addHistory(nameHistory.getText().toString(), contentHistory.getText().toString());
        histories = historyFragment.getHistories();
        btn_addHistory.setVisibility(View.VISIBLE);
        nameHistory.setVisibility(View.INVISIBLE); contentHistory.setVisibility(View.INVISIBLE);
        btn_CreateHistory = findViewById(R.id.btn_createHistory);
        btn_CreateHistory.setVisibility(View.INVISIBLE);
        adapter = new HistoryAdapter(this, histories);
        recyclerView.setAdapter(adapter);
        findViewById(R.id.list).setVisibility(View.VISIBLE);
    }

    public void onClickSetting(View view){
        view.setVisibility(View.INVISIBLE);
        edit_background = (TextView) findViewById(R.id.edit_bacground);
        colorText = (TextView)findViewById(R.id.colorText);
        resSetting = (TextView)findViewById(R.id.resText);
        TextView[] array = new TextView[]{edit_background, colorText, resSetting};
        settingsFragment = new SettingsFragment(array, this, this);
        edit_background.setVisibility(View.VISIBLE); colorText.setVisibility(View.VISIBLE);
        resSetting.setVisibility(View.VISIBLE); findViewById(R.id.txt_bacgroundLabel).setVisibility(View.VISIBLE);
        findViewById(R.id.txt_colorTextLabel).setVisibility(View.VISIBLE);
        findViewById(R.id.txt_Header).setVisibility(View.VISIBLE);
        findViewById(R.id.btn_to_change).setVisibility(View.VISIBLE);
        findViewById(R.id.btn_throw_off).setVisibility(View.VISIBLE);
    }
    public void onClickThrowOff(View view){
        settingsFragment.onLoadText();
    }
    public void onClickChange(View view){
        settingsFragment.onSaveText();
    }

    public void onCreateImage(View view) {
        createImageView = findViewById(R.id.ImageLoadNew);
        int cameraPermissionStatus =
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int storagePermissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (cameraPermissionStatus == PackageManager.PERMISSION_GRANTED && storagePermissionStatus == PackageManager.PERMISSION_GRANTED) {
            isWork = true;
        } else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_PERMISSION_CAMERA);
        }
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null && isWork == true)
        {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // генерирование пути к файлу на основе authorities
            String authorities = getApplicationContext().getPackageName() + ".fileprovider";
            imageUri = FileProvider.getUriForFile(this, authorities, photoFile);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    }
    public void onClickThrowOff(){

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Если приложение камера вернула RESULT_OK, то производится установка изображению imageView
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            createImageView.setImageURI(imageUri);
        }
    }
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMAGE_" + timeStamp + "_";
        File storageDirectory =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDirectory);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
// производится проверка полученного результата от пользователя на запрос разрешения Camera
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION_CAMERA) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission granted
                isWork = true;
            } else {
                isWork = false;
            }
        }
    }





    public void onClickAccelerometr(View view){
        if (sensorStatusFragment == null )  sensorStatusFragment = new SensorStatusFragment();
        else sensorStatusFragment.DestroySensor();
        sensorStatusFragment.CreateSensorStatus(new TextView[]{
                findViewById(R.id.topSensor), findViewById(R.id.centerSensor), findViewById(R.id.bottomSensor)
        }, "Accelerometr", this);

    }public void onClickAmbientTemperature(View view){
        if (sensorStatusFragment == null )  sensorStatusFragment = new SensorStatusFragment();
        else sensorStatusFragment.DestroySensor();
        sensorStatusFragment.CreateSensorStatus(new TextView[]{
                findViewById(R.id.topSensor), findViewById(R.id.centerSensor), findViewById(R.id.bottomSensor)
        }, "AmbientTemperature", this);
    }
    public void onClickHydroscop(View view){
        if (sensorStatusFragment == null )  sensorStatusFragment = new SensorStatusFragment();
        else sensorStatusFragment.DestroySensor();
        sensorStatusFragment.CreateSensorStatus(new TextView[]{
                findViewById(R.id.topSensor), findViewById(R.id.centerSensor), findViewById(R.id.bottomSensor)
        }, "Hydroscop", this);
    }
    public void onClickProximity(View view){
        if (sensorStatusFragment == null )  sensorStatusFragment = new SensorStatusFragment();
        else sensorStatusFragment.DestroySensor();
        sensorStatusFragment.CreateSensorStatus(new TextView[]{
                findViewById(R.id.topSensor), findViewById(R.id.centerSensor), findViewById(R.id.bottomSensor)
        }, "Proximity", this);

    }



    public void onStartRecord(View view){
        Button[] array_btn = new Button[]{
                findViewById(R.id.btn_stopRecord), findViewById(R.id.btn_startRecord),
                findViewById(R.id.btn_startAudioRecord), findViewById(R.id.btn_stopMusicRecord),

        };
        audioRecordFragment = new AudioRecordFragment(array_btn, this, this, MainActivity.this);
        try {
            audioRecordFragment.startRecording();
        } catch (Exception e) {
            Log.e("Record", "Caught io exception " + e.getMessage());
        }
    }

    public void onStopRecording(View view){
        audioRecordFragment.stopRecording();
    }
    public void onPlayMusicRecord(View view){
        audioRecordFragment.onPlayMusic();
    }
    public void onStopRecordMusic(View view){
        audioRecordFragment.onStopMusic();
    }

    public void onStartPlayer(View view){
        btn_pauseMusic = (Button) findViewById(R.id.btn_pauseAudioRecord);
        btn_playMusic = (Button)findViewById(R.id.btn_startAudioRecord);
        btn_stopMusic  = (Button) findViewById(R.id.btn_stopMusicRecord);
        btn_stopMusic.setVisibility(View.VISIBLE); btn_playMusic.setVisibility(View.VISIBLE);
        btn_pauseMusic.setVisibility(View.VISIBLE);
        view.setVisibility(View.INVISIBLE);
        mediaPlayer= MediaPlayer.create(this, R.raw.music);

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public void onPlayMusic(View view){
        mediaPlayer.start();
        /*startService(
                new Intent(MainActivity.this, PlayerService.class));*/
    }
    public void onStopMusic(View view){
        mediaPlayer.stop();
        try {
            mediaPlayer.prepare();
            mediaPlayer.seekTo(0);
        }
        catch (Throwable t) {
            Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
        }
        /*stopService(
                new Intent(MainActivity.this, PlayerService.class));*/
    }
    public void onPauseMusic(View view){
        System.out.println(mediaPlayer.isPlaying());
        if (mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }else{
            mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
        }

    }



    public void onClickStatrBrouser(View view){
        webView = (WebView) findViewById(R.id.webView);
        String copy_path =  "https://yandex.com";
        input_url = (MultiAutoCompleteTextView ) findViewById(R.id.input_url);
        back_WebEl = (Button) findViewById(R.id.btn_back);
        next_webEl = (Button) findViewById(R.id.btn_next);
        reload_WebEl = (ImageButton) findViewById(R.id.img_upload);
        load_WebEl = (ImageButton) findViewById(R.id.img_load);
        view.setVisibility(View.INVISIBLE);
        input_url.setVisibility(View.VISIBLE); back_WebEl.setVisibility(View.VISIBLE);
        next_webEl.setVisibility(View.VISIBLE); reload_WebEl.setVisibility(View.VISIBLE);
        load_WebEl.setVisibility(View.VISIBLE);
        input_url.setText(copy_path);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(copy_path);
        webView.setWebViewClient(new SimpleWebViewClientImpl());
    }
    public void onClickWebEl(View view){
        switch (view.getId()) {
                case R.id.btn_back:
                    onBackPressed();
                    break;
                case R.id.btn_next:
                    onNextPressed();
                    break;
                case R.id.img_upload:
                    webView.loadUrl(webView.getUrl());
                    input_url.setText(webView.getUrl());
                    break;
                case R.id.img_load:
                    onLoadPage();
                default:
                    break;

        }
    }
    public void onLoadPage(){
        webView.loadUrl(input_url.getText().toString());
    }
    public void onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
    public void onNextPressed() {
        if(webView.canGoBackOrForward(1)) {
            webView.goBackOrForward(1);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.webView.canGoBack()) {
            this.webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void onClickClearCalculator(View view){
        resTextView = (TextView) findViewById(R.id.result);
        inputText = (TextView) findViewById(R.id.text_input);
        resTextView.setText("");inputText.setText("");
    }
    public  void addMathSymbol(View view){
        inputText = (TextView) findViewById(R.id.text_input);
        resTextView = (TextView) findViewById(R.id.result);
        switch (view.getId()){
            case R.id.btn_div:
                inputText.setText(inputText.getText().toString().concat("/"));
                break;
            case R.id.btn_minus:
                inputText.setText(inputText.getText().toString().concat("-"));
                break;
            case R.id.btn_per:
                inputText.setText(inputText.getText().toString().concat("%"));
                break;
            case R.id.btn_plus:
                inputText.setText(inputText.getText().toString().concat("+"));
                break;
            case R.id.btn_multiply:
                inputText.setText(inputText.getText().toString().concat( "*"));
                break;
            case R.id.btn_point:
                inputText.setText(inputText.getText().toString().concat("."));
                break;
            case R.id.btn_equal:
                Mathematics mathematics = new Mathematics(inputText.getText().toString());
                resTextView.setText(String.valueOf(mathematics.getRes()));
                break;
            default:
                break;
        }
    }
    public void addNumberPoleVault(View view) {
        inputText = (TextView) findViewById(R.id.text_input);
        switch (view.getId()) {
            case R.id.btn_0:
                inputText.setText(inputText.getText().toString().concat("0"));
                break;
            case R.id.btn_1:
                inputText.setText(inputText.getText().toString().concat("1"));
                break;
            case R.id.btn_2:
                inputText.setText(inputText.getText().toString().concat("2"));
                break;
            case R.id.btn_3:
                inputText.setText(inputText.getText().toString().concat("3"));
                break;
            case R.id.btn_4:
                inputText.setText(inputText.getText().toString().concat("4"));
                break;
            case R.id.btn_5:
                inputText.setText(inputText.getText().toString().concat("5"));
                break;
            case R.id.btn_6:
                inputText.setText(inputText.getText().toString().concat("6"));
                break;
            case R.id.btn_7:
                inputText.setText(inputText.getText().toString().concat("7"));
                break;
            case R.id.btn_8:
                inputText.setText(inputText.getText().toString().concat("8"));
                break;
            case R.id.btn_9:
                inputText.setText(inputText.getText().toString().concat("9"));
                break;
            case R.id.btn_equal:
                Mathematics mathematics = new Mathematics(inputText.getText().toString());
                resTextView.setText(String.valueOf(mathematics.getRes()));
                break;
            default:
                break;
        }
    }
}