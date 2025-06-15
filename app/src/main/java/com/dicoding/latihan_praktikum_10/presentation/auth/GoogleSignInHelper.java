package com.dicoding.latihan_praktikum_10.presentation.auth;

import android.app.Activity;
import android.content.Intent;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;

import com.dicoding.latihan_praktikum_10.R;
import com.google.android.gms.auth.api.signin.*;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;



public class GoogleSignInHelper {

    public interface OnGoogleSignInListener {
        void onResult(FirebaseUser firebaseUser);
    }

    public static void startGoogleSignIn(Activity activity, ActivityResultLauncher<Intent> launcher) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient client = GoogleSignIn.getClient(activity, gso);
        launcher.launch(client.getSignInIntent());
    }

    public static ActivityResultLauncher<Intent> getGoogleSignInLauncher(Fragment fragment, OnGoogleSignInListener listener) {
        return fragment.registerForActivityResult(
                new androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult(),
                result -> {
                    Intent data = result.getData();
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                    try {
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        firebaseAuthWithGoogle(account.getIdToken(), listener);
                    } catch (ApiException e) {
                        listener.onResult(null);
                    }
                });
    }

    private static void firebaseAuthWithGoogle(String idToken, OnGoogleSignInListener listener) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(task -> {
            FirebaseUser user = task.isSuccessful() ? task.getResult().getUser() : null;
            listener.onResult(user);
        });
    }
}
