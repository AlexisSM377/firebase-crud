package com.example.demo.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @Autowired
    private FirebaseProperties firebaseProperties;

//    En caso de saber la url de una base de datos,se hace uso de estos parametros y se cambia en el setProjectId
//    @Value("${app.urldatabase}")
//    private String urlDatabase;

    @Bean
    public Firestore firestore() {
        try {
            Gson gson = new Gson();
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("type", firebaseProperties.getType());
            jsonObject.addProperty("project_id", firebaseProperties.getProjectId());
            jsonObject.addProperty("private_key_id", firebaseProperties.getPrivateKeyId());
            jsonObject.addProperty("private_key", firebaseProperties.getPrivateKey().replace("\\n", "\n")); // Reemplaza \n por saltos de línea reales
            jsonObject.addProperty("client_email", firebaseProperties.getClientEmail());
            jsonObject.addProperty("client_id", firebaseProperties.getClientId());
            jsonObject.addProperty("auth_uri", firebaseProperties.getAuthUri());
            jsonObject.addProperty("token_uri", firebaseProperties.getTokenUri());
            jsonObject.addProperty("auth_provider_x509_cert_url", firebaseProperties.getAuthProviderX509CertUrl());
            jsonObject.addProperty("client_x509_cert_url", firebaseProperties.getClientX509CertUrl());
            jsonObject.addProperty("universe_domain", firebaseProperties.getUniverseDomain());

            InputStream serviceAccount = new ByteArrayInputStream(gson.toJson(jsonObject).getBytes());

            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(credentials)
                    .setProjectId(firebaseProperties.getProjectId())
                    .build();

            // Inicializar Firebase
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }

            return FirestoreClient.getFirestore();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize Firestore: " + e.getMessage(), e);
        }
    }
}
