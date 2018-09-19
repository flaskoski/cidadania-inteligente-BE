package app;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import app.fileManagement.FileStorageProperties;
//import MissionsRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import java.io.FileInputStream;


@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
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