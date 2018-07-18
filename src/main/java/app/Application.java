package app;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
//import MissionRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.FileInputStream;
import java.util.Arrays;

@SpringBootApplication
public class Application {


    public static void main(String[] args) {

        startFirebase();

     //   addItemToDB();

        SpringApplication.run(Application.class, args);
    }

    private static void startFirebase(){
        FileInputStream serviceAccount = null;
        try {
            serviceAccount = new FileInputStream("resources/cidadania-inteligente-firebase-adminsdk-r64q9-66953f20fc.json");
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