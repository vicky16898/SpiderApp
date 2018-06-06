package com.example.vicky.hangman;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.TestLooperManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {



    private ArrayList<String> words;
    public ArrayList<Character> singleword;
    private EditText editText;
    private Random random;
    private int position;
    private TextView word;
    private int flag = 0;
    private int wrong = 0;
    private Button press;
    private String forcheck;
    private String check2;
    private TextView guess;
    private int counter = 0;
    private int guessscore = 80;
    private int Total = 0;
    private TextView yourscore;
    private TextView bestscore;
    private int correctscore = 0;
    private int finalupdate = 0;
    private int best = 0;
    private boolean status;
    private int overallscore = 0;
    private int finallength;
    private int length;
    private String entry = null;
    private ArrayList<Character> chars;
    private ImageView imageView;




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings ) {
            words = new ArrayList<String>();
            chars = new ArrayList<Character>();
            singleword = new ArrayList<Character>();
            random = new Random();
            imageView = (ImageView)findViewById(R.id.imageView);

            imageView.setImageResource(R.drawable.hangman);


            Log.d("initial size" , String.valueOf(words.size()));


            words.add("FISH");
            words.add("PROCRASTINATE");
            words.add("DELIGHT");
            words.add("CREAM");
            words.add("DOG");
            words.add("JELLY");
            words.add("NOTEBOOK");
            words.add("BROTHERHOOD");
            words.add("ORIGINS");
            words.add("ODYSSEY");
            words.add("SPIDER");
            words.add("DELTA");
            words.add("WORLD");


            int count = words.size();
            Log.d("size" , String.valueOf(count));
            Log.d("singlewordSize" , String.valueOf(singleword.size()));

            finalupdate = 0;
            wrong = 0;
            Total = 0;
            correctscore = 0;
            guessscore = 80;
            overallscore = 0;
            counter = 0;
            int pos  = random.nextInt(count);
            Log.d("pos" , String.valueOf(pos));
            finallength= words.get(pos).length();
            length = finallength;
            Log.d("length" , String.valueOf(finallength));
            Log.d("word" , words.get(pos));





            for (int i = 0; i < finallength; i++) {

                singleword.add(words.get(pos).charAt(i));
                Log.d("singleWord" , String.valueOf(singleword.get(i)));


            }

            word.setText("");
            yourscore.setText("Your Score : 0");
            guess.setText("GUESSED LETTERS : ");
            flag = 0;
            while (flag < finallength) {


                word.append("_  ");
                flag++;


            }

            forcheck = null;
            check2 = null;

            forcheck = word.getText().toString();
            Log.d("THE DISPLAY" , forcheck);
            check2 = forcheck.replaceAll("\\s","");
            Log.d("THE TAKEN" , check2);

            chars.clear();
            Log.d("charscontent" , String.valueOf(chars.size()));


            return true;
        }
        return super.onOptionsItemSelected(item);
    }








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1), new InputFilter.AllCaps()});

        word = (TextView) findViewById(R.id.word);
        press = (Button) findViewById(R.id.press);
        guess = (TextView)findViewById(R.id.guess);
        yourscore = (TextView)findViewById(R.id.yourScore);
        bestscore = (TextView)findViewById(R.id.highScore);
        imageView = (ImageView)findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.hangman);

        yourscore.setText("Your Score : 0");
        chars = new ArrayList<Character>();
        random = new Random();

        singleword = new ArrayList<Character>();

        words = new ArrayList<String>();

        words.add("FISH");
        words.add("PROCRASTINATE");
        words.add("DELIGHT");
        words.add("CREAM");
        words.add("DOG");
        words.add("JELLY");
        words.add("NOTEBOOK");
        words.add("BROTHERHOOD");
        words.add("ORIGINS");
        words.add("ODYSSEY");
        words.add("SPIDER");
        words.add("DELTA");
        words.add("WORLD");

        final int count = words.size();


        SharedPreferences prefs = getSharedPreferences("Score Total", MODE_PRIVATE);
        best = prefs.getInt("BestScore" , 0);
        entry = prefs.getString("scoreBoard" , null);
        bestscore.setText(entry);

        Log.d("best" , String.valueOf(best));




        position = random.nextInt(count);
        Log.d("postion" , String.valueOf(position));
        Log.d("word is" , words.get(position));

        length = words.get(position).length();
        Log.d("length of the word" , String.valueOf(length));


        for (int i = 0; i < length; i++) {

            singleword.add(words.get(position).charAt(i));


        }


            while (flag < length) {


            word.append("_  ");
            flag++;


        }


        forcheck = word.getText().toString();
        check2 = forcheck.replaceAll("\\s","");
        Log.d("diplay" , String.valueOf(forcheck.length()));
        Log.d("check2" , check2);














        press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);


                         status = false;


                         if(wrong<8) {

                             if (finalupdate != length) {


                                 if (editText.getText().toString().equals("")) {


                                 } else {

                                     char b = editText.getText().toString().charAt(0);

                                     for (int q = 0; q < chars.size(); q++) {


                                         if (b == chars.get(q)) {


                                             status = true;


                                         }
                                     }

                                 }


                                 if (editText.getText().toString().equals("") || (status == true)) {


                                     if (status == true) {

                                         Toast.makeText(MainActivity.this, "You've already entered this letter!",
                                                 Toast.LENGTH_LONG).show();


                                         Toast.makeText(MainActivity.this, "ENTER ANY OTHER LETTER!",
                                                 Toast.LENGTH_LONG).show();

                                     } else
                                         Toast.makeText(MainActivity.this, "ENTER ANY LETTER!",
                                                 Toast.LENGTH_LONG).show();


                                 } else {
                                     char c = editText.getText().toString().charAt(0);
                                     counter = 0;
                                     finalupdate = 0;

                                     for (int k = 0; k < length; k++) {


                                         if (c == singleword.get(k))


                                         {


                                             counter = 1;
                                             Log.d("entered letter", String.valueOf(singleword.get(k)));
                                             check2 = check2.substring(0, k) + c + check2.substring(k + 1);

                                             Log.d("forcheck", check2);


                                         }


                                     }

                                     if (counter == 1) {

                                         chars.add(c);
                                         correctscore = correctscore + 10;
                                         yourscore.setText("Your Score : " + String.valueOf(correctscore));


                                     }


                                     if (counter == 0) {


                                         wrong++;
                                         guess.append(String.valueOf(c) + " ");
                                         chars.add(c);
                                         guessscore = guessscore - 10;
                                         Log.d("guess score", String.valueOf(guessscore));




                                             if(wrong == 1){

                                                 imageView.setImageResource(R.drawable.state1);

                                             }

                                             else if(wrong==2){

                                                 imageView.setImageResource(R.drawable.state2);


                                             }
                                             else if(wrong==3){

                                                 imageView.setImageResource(R.drawable.state3);


                                             }
                                             else if(wrong==4){

                                                 imageView.setImageResource(R.drawable.state4);


                                             }
                                             else if(wrong==5){

                                                 imageView.setImageResource(R.drawable.state5);


                                             }
                                             else if(wrong==6){

                                                 imageView.setImageResource(R.drawable.state6);


                                             }
                                             else if(wrong==7){

                                                 imageView.setImageResource(R.drawable.state7);


                                             }
                                             else if(wrong==8){

                                                 imageView.setImageResource(R.drawable.state8);


                                             }














                                         if(wrong == 8){

                                             Toast.makeText(MainActivity.this, "GAME OVER!!",
                                                     Toast.LENGTH_LONG).show();

                                         }


                                     }


                                     Total = correctscore + guessscore;
                                     Log.d("Total" , String.valueOf(Total));




                                     StringBuilder result = new StringBuilder();
                                     for (int i = 0; i < check2.length(); i++) {
                                         if (i > 0) {
                                             result.append("  ");
                                         }

                                         result.append(check2.charAt(i));
                                     }

                                     Log.d("displaystring", result.toString());
                                     word.setText(result.toString());

                                     finalupdate = 0;
                                     for (int p = 0; p < length; p++) {

                                         if (check2.charAt(p) != '_') {

                                             finalupdate++;


                                         }


                                     }


                                     if(Total>best){

                                         if((wrong == 8)||(finalupdate == length)) {
                                             bestscore.setText("Best Score : " + String.valueOf(Total));
                                         }
                                         SharedPreferences.Editor editor = getSharedPreferences("Score Total", MODE_PRIVATE).edit();
                                         editor.putString("scoreBoard" , bestscore.getText().toString());
                                         editor.putInt("BestScore", Total);
                                         editor.apply();




                                     }




                                 }

                             } else {


                                 Toast.makeText(MainActivity.this, "You've solved the puzzle!",
                                         Toast.LENGTH_LONG).show();

                             }

                         }

                         else{

                             Toast.makeText(MainActivity.this, "You've failed to solve the puzzle!",
                                     Toast.LENGTH_LONG).show();



                         }












                if(finalupdate == length){


                    overallscore = correctscore + guessscore;

                    yourscore.setText("Your Score : "+(overallscore));
                    Toast.makeText(MainActivity.this, "You've have solved the puzzle!",
                            Toast.LENGTH_LONG).show();














                }



                editText.setText("");








            }
        });









    }

}