# ProiectPA
# FrontEnd + Backend
- **splashscreen**: this is a simple screen with the logo, a short description and copyright that makes you wait 2 seconds until you reach the login page (if you are not logged in) or the main menu page (if you are logged in) - data saved in sharedPreferences
```
    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        if (pref.getBoolean("isLogged", false)) {
            handler.postDelayed(new Runnable() {
                public void run() {
                    Intent mInHome = new Intent(MainActivity.this, MainMenu.class);
                    MainActivity.this.startActivity(mInHome);
                    MainActivity.this.finish();
                }
            }, 2000);
        } else {
            handler.postDelayed(new Runnable() {
                public void run() {
                    Intent mInHome = new Intent(MainActivity.this, Login.class);
                    MainActivity.this.startActivity(mInHome);
                    MainActivity.this.finish();
                }
            }, 2000);
        }
  ```
- **login**: here you have a logo, a title, 2 edit texts where you write your username/email and password that you created your account with (both are verified in database), a button for logging in (when this will be pressed, the data introduced will be verified in database and there will be toasts if: there is no data introduced or one of the fields are empty or if the data is incorrect - if it is correct, you will be taken to MainMenu page and the username will be taken in sharedPreferences to show up on the MainMenu page) and a button for signing up if you don't have an account yet that will take you to the signup page
- **signup**: here it is a logo, a title, 4 edit texts where will be written an username, an email, a password and a password confirmation: for the passwords, it will be verified if they are the same, elsewhere you will be given a toast; if the passwords match, the data will be written in database in users table and you will be taken to mainMenu page (all the data will be saved in sharedPreferences to further needs); also, it is in the page a button for signing up (that will verify what is written before) and a button for logging in if you already have an account
- **main menu**: here you have multiple choices: on the top, it is shown the settings button that will take you to settings page and a text where you see how many points you have in that moment (0 points by default); going down in the page, you will see the logo and under it the username and then the buttons for different pages: Grile, Probleme, Random Test and Status
- **grile**: in the main page for Grile it is shown a back button(to MainMenu), a title and the points on the top and below them the levels: they are generated from a json file that is parsed using JSONParser class, where the data is taken from the json file:
```
public String loadJSON(AppCompatActivity appCompatActivity) {
        String json = null;
        try {
            InputStream is = appCompatActivity.getAssets().open("gameData.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
 ```
 -> then for every level you can press on them and be taken to the actual exercises; there, you will have a back button, a title (the level number), the number of points, the question for that specific exercise and the 3 answers that it has and also a next button to take you to the next exercise; once the last one is reached, the button will say Finish and, with the verification done for every exercise, you will be taken to the Lost page if you have below 50% of the exercises correct and to the Winner page otherwise, being shown there the number of points and 2 buttons, one that can take you to the menu for exercises and one that will take you to MainMenu
- **probleme**: here you will have a list of problems with their title (also with a back button(to MainMenu), a title and the number of points) that you can press to be taken to the actual problem: there you will have a back button, a title and points on top and below the actual problem and an edit text in a scroll view that you can complete with the answer to the problem; you will also have a button for verification on page, but this will not take you anywhere as the verification is not done
- **random test**: here you will be dirrectly taken to an interface similar to the exercises, but it will have 10 exercises and at the end you will be taken to Lost or Winner page (also with a button back to the mainmenu, but you will receive an alert if you want to go back because you will loose your progres)
- **status**: the status will be saved on grile and probleme pages in a table from database (status): saved are the category (grile or test), the date when it is done and the percent that it is completed; in the actual status page, will be shown a button (to MainMenu), a title and the data from the table will be taken and will be having a nice interface that will show all of them, in the descent order of the date completed; the following piece of code tells you how the data is taken and the addToStatus function makes the relative layouts specific to every line of the status table
```
    AndroidNetworking.get("https://exam-net.herokuapp.com/statuses/statcontroller.php?view=all").build().getAsJSONObject(new JSONObjectRequestListener() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                    int stop = 0;
                    JSONArray users_informations = response.getJSONArray("exams");
                    for (int i = 0; i < users_informations.length(); i++) {
                        JSONObject username = users_informations.getJSONObject(i);
                        if (username.get("username").equals(pref.getString("username",""))) {
                            addToStatus(String.valueOf(username.get("category")), (String) username.get("date"), (String) username.get("procent"));

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ANError anError) {
                System.out.println(anError);
            }
        });
```
- **settings**: here you will find a back button (to MainMenu), a title and 4 buttons: one for reseting the progress (that will erase your points and the data from status table for you), one for updateing the informations of your account (you will be taken to UpdateAccount page), one for deleteing your account (you will have an alert and if you agree, your account will be deleted and you will be taken to signup page) and one for logging out (that will take you to login page)
- **update account**: here you will have a back button (to settings), the icon, the title and 4 edit texts that will let you change the information of your account (the username, email and password - the username and email will just be modified, the old password will be verified and the new password will not be the exact same as the old one, elsewhere you will be notified of that) and you will also have a button that will verify the information and put in database if something is modified and will take you to settings page  
