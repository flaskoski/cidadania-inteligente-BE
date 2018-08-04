package app.firebase;

import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

import java.util.concurrent.CountDownLatch;

public class FirebaseValidator {
    public static String validateUser(String idToken){
        CountDownLatch latch = new CountDownLatch(1);
        final String[] playerUid = new String[1];
        playerUid[0] = null;
        ApiFutures.addCallback(FirebaseAuth.getInstance().verifyIdTokenAsync(idToken),
                new ApiFutureCallback<FirebaseToken>() {
                    @Override
                    public void onFailure(Throwable t) {
                        System.out.println("failure");
                        latch.countDown();
                    }
                    @Override
                    public void onSuccess(FirebaseToken decodedToken) {
                        System.out.println(" Token is valid.");
                        playerUid[0] = decodedToken.getUid();

                        //Release thread wait
                        latch.countDown();
                    }
                });
        try {
            latch.await();
            return playerUid[0];
        } catch (InterruptedException | NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }
}
