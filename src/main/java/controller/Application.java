package controller;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {

        startFirebase();


        SpringApplication.run(Application.class, args);
    }
    private static void startFirebase(){
        FileInputStream serviceAccount = null;
        try {
            serviceAccount = new FileInputStream("firebase/cidadania-inteligente-firebase-adminsdk-r64q9-66953f20fc.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://cidadania-inteligente.firebaseio.com/")
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }


    }
}