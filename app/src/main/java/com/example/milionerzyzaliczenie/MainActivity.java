package com.example.milionerzyzaliczenie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
    //muzyczka
    MediaPlayer mediaPlayer;
    //guziki
Button startGameBtn;
Button A;
Button B;
Button C;
Button D;
Button resetBuytton;
Button nextButton;
TextView trescPytania;
TextView komunikat;
//obrazki
    ImageView zlaOdp;
    ImageView dobraOdp;
    ImageView logo;
    ImageView wTrakcie;
    ImageView milion;
//pytania
    Cursor cursor;
    int ktorePytanie=1;
    int losowanePytanie=0;
    Random rand;
    QuestionModel currentQuestion;
    HashMap<Integer, Integer> PytaniaIKasa;
//db
List<Integer>uzytePytania;
    DbHelper dbHelper;
    SQLiteDatabase db ;
    List<ContentValues> cv;
    SQLiteDatabase dbRead;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
dbHelper = new DbHelper(this);
db = dbHelper.getWritableDatabase();
PytaniaIKasa=  new HashMap<Integer, Integer>();
PytaniaIKasa.put(1,500);
PytaniaIKasa.put(2,1000);
PytaniaIKasa.put(3,5000);
PytaniaIKasa.put(4,10000);
PytaniaIKasa.put(5,20000);
PytaniaIKasa.put(6,40000);
PytaniaIKasa.put(7,80000);
PytaniaIKasa.put(8,160000);
PytaniaIKasa.put(9,250000);
PytaniaIKasa.put(10,400000);
PytaniaIKasa.put(11,500000);
PytaniaIKasa.put(12,1000000);
dbRead=dbHelper.getReadableDatabase();
uzytePytania=new ArrayList<Integer>();
rand=new Random();



//dodawanie pytań do bazy
String queryInsert="INSERT OR REPLACE INTO questions VALUES(1,'Rzeczpospolita Polska jest członkiem założycielem','Rady Europy','ONZ','NATO','Organizacji Współpracy Gospodarczej i Rozwoju','B',0),(2,'Pierwszym Prezydentem II Rzeczypospolitej był','Józef Piłsudski','Gabriel Narutowicz','Stefan Starzyński','Stanisław Wojciechowski','B',0),(3,'Do 1997 roku Hongkong był kolonią','brytyjską','francuską','portugalską','hiszpańską','A',0),(4,'Dyplomata zastępujący ambasadora w kierowaniu placówką to','nuncjusz','plenipotent','chargéd”affaires','liaisonofficer','C',0),(5,'Co oznacza \"Carpe diem\"?','Chwytaj dzień','Nie wszystek umrę','Pamiętaj o śmierci','Nic co ludzkie','A',0),(6,'Kto wcielał się w postać Franka Kimono','Cezary Pazura','Wiktor Zborowski','Piotr Fronczewski','Franciszek Pieczka','C',0),(7,'Dydyński, główny bohater sagi \"Samozwaniec\" miał na imię','Czarek','Jan','Zygmunt','Jacek','D',0),(8,'Autorem \"WładcyPierścieni\" był J.R.R','Tolkin','Tolkun','Tolken','Tolkien','D',0),(9,'Od 1989 roku w Polsce urzędowało','3 prezydentów','6 prezydentów','5 prezydentów','4 prezydentów','B',0),(10,'Postać grana przez Eryka Lubosa w filmie Underdog miała pseudonim','Kosa','Łom','Kolos','Nie miał pseudonimu','A',0),(11,'Pierwsza gala KSW odbyła się w roku','2005','2003','2004','2006','C',0),(12,'Młodzieżowym słowem roku 2019 zostało słowo','Sztos','Ziom','Krindż','Alternatywka','D',0),(13,'Zawołaniem rodu Stark w \"Grze o Tron\" było','Our is the Fury','Fire and Blood','Hear the Roar','Winter is Coming','D',0),(14,'Jak nazywa się zjawisko świetlne obserwowane tylko na biegunie?','Tęcza','Łuna','Zorza','Miraż','C',0),(15,'Płetwą grzbietową nie pruje wody','Długoszpar','Kosogon','Orka','Wal grenlandzki','D',0),(16,'Z gry na jakim instrumencie słynie Czesław Mozil?','Na kornecie','Na akordeonie','Na djembe','Na ksylofonie','B',0),(17,'Rybą nie jest','Świnka','Rozpiór','Krasnopiórka','Kraska','D',0),(18,'Który utwór Juliusza Słowackiego, napisany jest prozą?','Godzina myśli','W Szwajcarii','Anhelli','Arab','C',0),(19,'Który aktor urodził się w roku opatentowania kinematografu braci Lumière?','Rudolph Valentino','Humphrey Bogart','Charlie Chaplin','Fred Astaire','A',0),(20,'Komiksowym \"dzieckiem\" rysownika Boba Kanea jest:','Superman','Batman','Spider-Man','Captain America','B',0)";


        db.execSQL(queryInsert);

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);

        trescPytania=findViewById(R.id.trescPytania);
        dobraOdp=findViewById(R.id.hubertDobraOdp);
        zlaOdp=findViewById(R.id.hubertZlaOdp);
        logo=findViewById(R.id.logo);
        wTrakcie=findViewById(R.id.hubertPodczasPytania);
        milion=findViewById(R.id.milion);
A=findViewById(R.id.A);
B=findViewById(R.id.b);
C=findViewById(R.id.c);
D=findViewById(R.id.d);
nextButton=findViewById(R.id.next);
resetBuytton=findViewById(R.id.reset);
komunikat=findViewById(R.id.textView);
mediaPlayer=MediaPlayer.create(this,R.raw.theme);
mediaPlayer.setLooping(true);
mediaPlayer.start();
    }


public void checkAnswer(String selectedQuestion){


Log.i("correct",currentQuestion.Correct);
Log.i("selected",selectedQuestion);

    mediaPlayer.stop();
    A.setVisibility(View.INVISIBLE);
    B.setVisibility(View.INVISIBLE);
    C.setVisibility(View.INVISIBLE);
    D.setVisibility(View.INVISIBLE);
    trescPytania.setVisibility(View.INVISIBLE);
    resetBuytton.setVisibility(View.VISIBLE);
    if(currentQuestion.Correct.equals(selectedQuestion)){


        wTrakcie.setVisibility(View.INVISIBLE);

if(ktorePytanie<12) {
    komunikat.setText("Gratulacje! Kolejny krok na drodze do miliona. Pozostało jeszcze " + Integer.toString(12 - ktorePytanie) + " pytań do miliona. Obecnie masz " + Integer.toString(PytaniaIKasa.get(ktorePytanie)) + " zł!!!");
    komunikat.setVisibility(View.VISIBLE);
    mediaPlayer=MediaPlayer.create(this,R.raw.dobrze);
    mediaPlayer.start();
    nextButton.setVisibility(View.VISIBLE);
    dobraOdp.setVisibility(View.VISIBLE);

}
if(ktorePytanie==12){
    mediaPlayer=MediaPlayer.create(this,R.raw.theme);
    mediaPlayer.start();
   // nextButton.setVisibility(View.INVISIBLE);
    milion.setVisibility(View.VISIBLE);
    komunikat.setText("Zwycięstwo!!! Milion jest Twój!!! Od dziś jesteś Milionerem!!!");
    komunikat.setVisibility(View.VISIBLE);
    resetBuytton.setVisibility(View.VISIBLE);
}
        ktorePytanie++;

    }
    else{
        komunikat.setText("Niestety, Twoja odpowiedź była zła! Poprawna odpowiedź to "+ currentQuestion.Correct+". Straciłeś wszystkie pieniądze!");
        komunikat.setVisibility(View.VISIBLE);
        mediaPlayer=MediaPlayer.create(this,R.raw.zle);
        mediaPlayer.start();
        wTrakcie.setVisibility(View.INVISIBLE);
        zlaOdp.setVisibility(View.VISIBLE);

    }
}
    public void startGame(View view){

        startGameBtn=findViewById(R.id.startGameButton);
        startGameBtn.setVisibility(View.INVISIBLE);

        this.showAndRandQuestion(view);
    }

    public void aClick(View view){
        checkAnswer("A");
    }
    public void bClick(View view){
        checkAnswer("B");
    }
    public void cClick(View view){
        checkAnswer("C");
    }
    public void dClick(View view){
        checkAnswer("D");
    }


    public void showAndRandQuestion(View view) {
        nextButton.setVisibility(View.INVISIBLE);
        milion.setVisibility(View.INVISIBLE);
        resetBuytton.setVisibility(View.INVISIBLE);
        komunikat.setVisibility(View.INVISIBLE);
        dobraOdp.setVisibility(View.INVISIBLE);
        currentQuestion=new QuestionModel();
        mediaPlayer.stop();

do{
    losowanePytanie=rand.nextInt(20);
    losowanePytanie+=1;
}
while(uzytePytania.contains(losowanePytanie));
        String[]   projection = {
                BaseColumns._ID,
                QuestionVM.Question,
                QuestionVM.A,
                QuestionVM.B,
                QuestionVM.C,
                QuestionVM.D,
                QuestionVM.Correct
        };
//where
        String selection = QuestionVM._ID + " = ?";
        String[] selectionArgs = { Integer.toString(losowanePytanie) };

        cursor = dbRead.query(
                DbHelper.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        while(cursor.moveToNext()) {
            int index;

            index = cursor.getColumnIndexOrThrow("question");
            currentQuestion.Question  = cursor.getString(index);

            index = cursor.getColumnIndexOrThrow("_id");
            currentQuestion.Id  = cursor.getInt(index);

            index = cursor.getColumnIndexOrThrow("a");
            currentQuestion.A  = cursor.getString(index);

            index = cursor.getColumnIndexOrThrow("c");
            currentQuestion.C  = cursor.getString(index);

            index = cursor.getColumnIndexOrThrow("d");
            currentQuestion.D  = cursor.getString(index);
            index = cursor.getColumnIndexOrThrow("b");
            currentQuestion.B  = cursor.getString(index);
            index = cursor.getColumnIndexOrThrow("correct");
            currentQuestion.Correct  = cursor.getString(index);
            uzytePytania.add(currentQuestion.Id);
        }

        logo.setVisibility(View.INVISIBLE);
        wTrakcie.setVisibility(View.VISIBLE);
A.setVisibility(View.VISIBLE);
A.setText(currentQuestion.A);
        trescPytania.setVisibility(View.VISIBLE);
        trescPytania.setText(currentQuestion.Question);
        A.setText(currentQuestion.A);
B.setVisibility(View.VISIBLE);
B.setText(currentQuestion.B);
C.setVisibility(View.VISIBLE);
 C.setText(currentQuestion.C);
D.setVisibility(View.VISIBLE);
D.setText(currentQuestion.D);

mediaPlayer=MediaPlayer.create(this,R.raw.wtrakciepytania);
mediaPlayer.setLooping(true);
mediaPlayer.start();
    }

    public void RestartGame(View view) {

        nextButton.setVisibility(View.INVISIBLE);
        milion.setVisibility(View.INVISIBLE);
        resetBuytton.setVisibility(View.INVISIBLE);
        uzytePytania=new ArrayList<Integer>();
        dobraOdp.setVisibility(View.INVISIBLE);
        zlaOdp.setVisibility(View.INVISIBLE);
ktorePytanie=1;
losowanePytanie=0;
A.setVisibility(View.INVISIBLE);
B.setVisibility(View.INVISIBLE);
C.setVisibility(View.INVISIBLE);
D.setVisibility(View.INVISIBLE);
trescPytania.setVisibility(View.INVISIBLE);
komunikat.setVisibility(View.INVISIBLE);
startGameBtn.setVisibility(View.VISIBLE);
startGame(view);

    }
    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
        mediaPlayer.stop();
    }
}
